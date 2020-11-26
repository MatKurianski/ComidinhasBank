package com.kurianski.comidinhasbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurianski.comidinhasbank.ComidinhasbankApplication;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.repository.UserRepository;
import com.kurianski.comidinhasbank.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hamcrest.core.StringContains;
import org.hibernate.validator.constraints.br.CPF;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Getter
    @RequiredArgsConstructor
    private class LoginRequest {
        private final String cpf;
        private final String password;
    }

    private final String FAKE_CPF = "76370811092";
    private final String FAKE_PASSWORD = "da@bS122";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

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

    @Before
    public void setUp() {
        UserCreationRequest userCreationRequest = makeValidUser();
        User user = new User();
        BeanUtils.copyProperties(userCreationRequest, user);
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
                .andExpect(jsonPath("$.firstName").value("Matheus"))
                .andExpect(jsonPath("$.lastName").value("Kurianski"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.email").value("matheuskurianski@usp.br"));
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
