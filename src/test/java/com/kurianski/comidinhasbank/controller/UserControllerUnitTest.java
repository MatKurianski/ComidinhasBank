package com.kurianski.comidinhasbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.repository.UserRepository;
import com.kurianski.comidinhasbank.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createValidUser_thenReturnUserJson() throws Exception {
        UserCreationRequest newUser = new UserCreationRequest();
        newUser.setFirstName("Matheus");
        newUser.setLastName("Kurianski");
        newUser.setPassword("da@bS122");
        newUser.setGender(Gender.MALE);
        newUser.setCpf("76370811092");
        newUser.setEmail("matheuskurianski@usp.br");

        when(userService.createUser(any(UserCreationRequest.class))).thenReturn(any(User.class));

        String userJson = objectMapper.writeValueAsString(newUser);

        MvcResult result = mockMvc.perform(
                post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                ).andExpect(status().isOk())
                .andReturn();

    }
}
