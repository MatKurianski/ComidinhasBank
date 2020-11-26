package com.kurianski.comidinhasbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.repository.UserRepository;
import com.kurianski.comidinhasbank.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    private UserCreationRequest makeValidUser() {
        UserCreationRequest newUser = new UserCreationRequest();
        newUser.setFirstName("Matheus");
        newUser.setLastName("Kurianski");
        newUser.setPassword("da@bS122");
        newUser.setGender(Gender.MALE);
        newUser.setCpf("76370811092");
        newUser.setEmail("matheuskurianski@usp.br");
        return newUser;
    }

    @Before
    public void setUp() {
        UserCreationRequest userCreationRequest = makeValidUser();
        User user = new User();
        BeanUtils.copyProperties(userCreationRequest, user);
        when(userService.createUser(userCreationRequest)).thenReturn(user);
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
}
