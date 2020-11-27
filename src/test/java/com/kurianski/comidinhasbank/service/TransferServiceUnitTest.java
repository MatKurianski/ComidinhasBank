package com.kurianski.comidinhasbank.service;

import com.kurianski.comidinhasbank.exception.InsufficientFundsException;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.model.request.TransferMoneyRequest;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceUnitTest {

    private final String FIRST_USER_CPF = "1812681054";

    private final String SECOND_USER_CPF = "00639863027";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransferMoneyService transferMoneyService;

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
        User me = makeValidUser();
        User other = makeValidUser();

        other.setEmail("matheus2@teste.com");
        other.setCpf(SECOND_USER_CPF);

        me.setAmount(BigDecimal.valueOf(50));
        other.setAmount(BigDecimal.valueOf(30));

        when(userRepository.getUserByCpf(FIRST_USER_CPF)).thenReturn(me);
        when(userRepository.getUserByCpf(SECOND_USER_CPF)).thenReturn(other);
    }

    @Test
    public void makeValidTransfer() {
        transferMoneyService.transferMoneyWithCpf(FIRST_USER_CPF, new TransferMoneyRequest(SECOND_USER_CPF, BigDecimal.TEN));

        User me = userRepository.getUserByCpf(FIRST_USER_CPF);
        User other = userRepository.getUserByCpf(SECOND_USER_CPF);

        assertThat(me.getAmount(), equalTo(BigDecimal.valueOf(40)));
        assertThat(other.getAmount(), equalTo(BigDecimal.valueOf(40)));
    }

    @Test
    public void makeInvalidTransfer() {
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            transferMoneyService.transferMoneyWithCpf(FIRST_USER_CPF, new TransferMoneyRequest(SECOND_USER_CPF, BigDecimal.valueOf(2000)));
        });
        assertThat(exception.getMessage(), equalTo("Saldo insuficiente"));
    }
}
