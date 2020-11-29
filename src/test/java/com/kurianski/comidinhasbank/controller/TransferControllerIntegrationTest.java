package com.kurianski.comidinhasbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.model.request.TransferMoneyRequest;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TransferControllerIntegrationTest {

    private final String FIRST_USER_CPF = "32647721084";
    private final String SECOND_USER_CPF = "44428587058";

    private User firstUser;
    private User secondUser;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private User makeValidUser() {
        User newUser = new User();
        newUser.setFirstName("Matheus");
        newUser.setLastName("Teste");
        newUser.setEmail("matheus@teste.com");
        newUser.setCpf(FIRST_USER_CPF);
        newUser.setPassword("aS31@aa#2");
        newUser.setGender(Gender.MALE);
        return newUser;
    }

    @Before
    public void setUp() {
        this.firstUser = makeValidUser();
        this.secondUser = makeValidUser();

        this.secondUser.setEmail("matheus2@teste.com");
        this.secondUser.setCpf(SECOND_USER_CPF);

        this.firstUser.setAmount(BigDecimal.valueOf(50));
        this.secondUser.setAmount(BigDecimal.valueOf(30));

        userRepository.save(this.firstUser);
        userRepository.save(this.secondUser);
    }

    @Test
    @WithMockUser(username = FIRST_USER_CPF)
    public void validOperation_thenReturnSuccess() throws Exception {
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(SECOND_USER_CPF, BigDecimal.TEN);

        String transferMoneyJson = objectMapper.writeValueAsString(transferMoneyRequest);

        mockMvc.perform(
                post("/transfer/cpf")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transferMoneyJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromUser.firstName").value(this.firstUser.getFirstName()))
                .andExpect(jsonPath("$.fromUser.lastName").value(this.firstUser.getLastName()))
                .andExpect(jsonPath("$.fromUser.cpf").value(this.firstUser.getCpf()))
                .andExpect(jsonPath("$.toUser.firstName").value(this.secondUser.getFirstName()))
                .andExpect(jsonPath("$.toUser.lastName").value(this.secondUser.getLastName()))
                .andExpect(jsonPath("$.toUser.cpf").value(this.secondUser.getCpf()))
                .andExpect(jsonPath("$.amount").value(transferMoneyRequest.getAmount()));
    }

    @Test
    @WithMockUser(username = FIRST_USER_CPF)
    public void invalidOperation_thenReturnInsufficientFundsException() throws Exception {
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(SECOND_USER_CPF, BigDecimal.valueOf(200000));

        String transferMoneyJson = objectMapper.writeValueAsString(transferMoneyRequest);

        mockMvc.perform(
                post("/transfer/cpf")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transferMoneyJson)
        )
        .andExpect(status().isBadRequest())
        .andExpect(status().reason("Saldo insuficiente"));
    }
}
