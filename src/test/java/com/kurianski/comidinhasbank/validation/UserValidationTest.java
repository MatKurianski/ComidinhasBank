package com.kurianski.comidinhasbank.validation;

import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidationTest {
    private Validator validator;

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

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUser() {
        User user = makeValidUser();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutFirstName() {
        User user = makeValidUser();
        user.setFirstName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithBlankFirstName() {
        User user = makeValidUser();
        user.setFirstName("   ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutLastName() {
        User user = makeValidUser();
        user.setLastName(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithBlankLastName() {
        User user = makeValidUser();
        user.setLastName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutCpf() {
        User user = makeValidUser();
        user.setCpf(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithBlankCpf() {
        User user = makeValidUser();
        user.setCpf("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithInvalidCpf() {
        User user = makeValidUser();
        user.setCpf("216666");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutEmail() {
        User user = makeValidUser();
        user.setEmail(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithBlankEmail() {
        User user = makeValidUser();
        user.setEmail("   ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithInvalidEmail() {
        User user = makeValidUser();
        user.setEmail("216666");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutPassword() {
        User user = makeValidUser();
        user.setPassword(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithBlankPassword() {
        User user = makeValidUser();
        user.setPassword("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
}
