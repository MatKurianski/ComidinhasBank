package com.kurianski.comidinhasbank.service;

import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserCreationRequest makeValidUserCreationRequest() {
        UserCreationRequest valid = new UserCreationRequest();
        valid.setFirstName("Matheus");
        valid.setLastName("Kurianski");
        valid.setPassword("da@bS122");
        valid.setGender(Gender.MALE);
        valid.setEmail("teste@teste.com.br");
        valid.setCpf("76370811092");
        return valid;
    }

    @Before
    public void setUp() {
        when(passwordEncoder.encode(anyString())).thenReturn("Uma ótima senha!");
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
    }

    @Test
    public void createValidUser() {
        UserCreationRequest userCreationRequest = makeValidUserCreationRequest();
        User created = userService.createUser(userCreationRequest);

        assertSuccesfulCreated(userCreationRequest, created);
    }

    private void assertSuccesfulCreated(UserCreationRequest userCreationRequest, User created) {
        assertNotNull(created);
        assertThat(created.getFirstName(), equalTo(userCreationRequest.getFirstName()));
        assertThat(created.getLastName(), equalTo(userCreationRequest.getLastName()));
        assertThat(created.getPassword(), equalTo("Uma ótima senha!"));
        assertThat(created.getCpf(), equalTo(userCreationRequest.getCpf()));
        assertThat(created.getEmail(), equalTo(userCreationRequest.getEmail()));
        assertThat(created.getGender(), equalTo(userCreationRequest.getGender()));
    }
}
