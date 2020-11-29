package com.kurianski.comidinhasbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Getter
    @RequiredArgsConstructor
    private class LoginRequest {
        private final String cpf;
        private final String password;
    }

    private static final String FAKE_CPF = "76370811092";
    private static final String FAKE_PASSWORD = "da@bS122";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserCreationRequest makeValidUser() {
        UserCreationRequest newUser = new UserCreationRequest();
        newUser.setFirstName("Matheus");
        newUser.setLastName("Kurianski");
        newUser.setPassword(FAKE_PASSWORD);
        newUser.setGender(Gender.MALE);
        newUser.setCpf(FAKE_CPF);
        newUser.setEmail("matheuskurianski@usp.br");
        return newUser;
    }

    @Test
    public void createValidUser_thenReturnUserJson() throws Exception {
        UserCreationRequest userCreationRequest = makeValidUser();

        String userJson = objectMapper.writeValueAsString(userCreationRequest);

        mockMvc.perform(
                post("/sign-up")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankAccountNumber").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(userCreationRequest.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userCreationRequest.getLastName()))
                .andExpect(jsonPath("$.cpf").value(userCreationRequest.getCpf()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void login() throws Exception {
        UserCreationRequest userCreationRequest = makeValidUser();

        userService.createUser(userCreationRequest);

        String loginJson = objectMapper.writeValueAsString(new LoginRequest(FAKE_CPF, FAKE_PASSWORD));

        MvcResult result = mockMvc.perform(
                post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
        )
        .andExpect(status().isOk()).andReturn();

        String authorizationHeader = result.getResponse().getHeader("Authorization");
        assertThat(authorizationHeader, new StringContains("Bearer"));
    }
}
