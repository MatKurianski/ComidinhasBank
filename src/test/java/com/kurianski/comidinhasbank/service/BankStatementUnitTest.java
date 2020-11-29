package com.kurianski.comidinhasbank.service;

import com.kurianski.comidinhasbank.model.Transaction;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.repository.TransactionRepository;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankStatementUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private BankStatementService bankStatementService;

    private static final String FIRST_USER_CPF = "06158517704";
    private static final String SECOND_USER_CPF = "06158517704";

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
        User from = makeValidUser();
        User to = makeValidUser();

        to.setCpf(SECOND_USER_CPF);
        to.setEmail("matheus2@teste.com");

        when(userRepository.findByCpf(from.getCpf())).thenReturn(from);
        when(userRepository.findByCpf(to.getCpf())).thenReturn(to);

        List<Transaction> transactionList = makeTransactions(from, to);

        // Authentication Mock
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(FIRST_USER_CPF);

        when(transactionRepository.getAllByFromUserOrToUserAndCreatedAtAfterOrderByCreatedAtDesc(any(User.class), any(User.class), any(Date.class))).thenReturn(transactionList);
    }

    private List<Transaction> makeTransactions(User from, User to) {
        double[] examples = {2.12, 3.14, 15.21, 300.12};
        List<Transaction> examplesTransactions = new LinkedList<>();

        for (double example : examples) {
            Transaction transaction = new Transaction();
            transaction.setFromUser(from);
            transaction.setToUser(to);
            transaction.setAmount(BigDecimal.valueOf(example));
            examplesTransactions.add(transaction);
        }
        return examplesTransactions;
    }

    @Test
    public void getAllUserTransactions() {
        String cpf = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByCpf(cpf);

        List<Transaction> transactions = bankStatementService.getUserExtract(30);

        for(Transaction transaction : transactions)
            assertThat(user, anyOf(equalTo(transaction.getFromUser()), equalTo(transaction.getToUser())));
    }
}
