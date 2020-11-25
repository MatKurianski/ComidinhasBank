package com.kurianski.comidinhasbank.model;

import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User makeValidUser() {
        User valid = new User();
        valid.setEmail("teste@teste.com.br");
        valid.setCpf("91449094899");
        valid.setFirstName("Teste");
        valid.setLastName("da Silva");
        valid.setGender(Gender.FEMALE);
        valid.setPassword("78sAs2W2@135");
        return valid;
    }

    @Test
    public void whenFindByCpf_thenReturnUser() {
        User valid = makeValidUser();
        entityManager.persistAndFlush(valid);

        User found = userRepository.getUserByCpf(valid.getCpf());
        assertThat(found, equalTo(valid));
    }
}
