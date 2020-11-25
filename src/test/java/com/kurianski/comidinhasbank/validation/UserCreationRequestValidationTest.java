package com.kurianski.comidinhasbank.validation;

import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import org.junit.Before;
import org.junit.Test;

import javax.validation.*;
import java.util.Set;

import static org.junit.Assert.*;

public class UserCreationRequestValidationTest {
    private Validator validator;

    private UserCreationRequest makeValidUser() {
        UserCreationRequest valid = new UserCreationRequest();
        valid.setEmail("teste@teste.com.br");
        valid.setCpf("91449094899");
        valid.setFirstName("Teste");
        valid.setLastName("da Silva");
        valid.setGender(Gender.FEMALE);
        valid.setPassword("78sAs@135");
        return valid;
    }

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUser() {
        UserCreationRequest user = makeValidUser();
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutFirstName() {
        UserCreationRequest user = makeValidUser();
        user.setFirstName(null);
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithBlankFirstName() {
        UserCreationRequest user = makeValidUser();
        user.setFirstName("   ");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutLastName() {
        UserCreationRequest user = makeValidUser();
        user.setLastName(null);
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithBlankLastName() {
        UserCreationRequest user = makeValidUser();
        user.setLastName("");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutCpf() {
        UserCreationRequest user = makeValidUser();
        user.setCpf(null);
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithBlankCpf() {
        UserCreationRequest user = makeValidUser();
        user.setCpf("");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithInvalidCpf() {
        UserCreationRequest user = makeValidUser();
        user.setCpf("216666");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutEmail() {
        UserCreationRequest user = makeValidUser();
        user.setEmail(null);
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithBlankEmail() {
        UserCreationRequest user = makeValidUser();
        user.setEmail("   ");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithInvalidEmail() {
        UserCreationRequest user = makeValidUser();
        user.setEmail("216666");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithoutPassword() {
        UserCreationRequest user = makeValidUser();
        user.setPassword(null);
        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void testInvalidUserWithBlankPassword() {
        UserCreationRequest user = makeValidUser();
        user.setPassword("");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithInvalidPasswordWithoutNumbers() {
        UserCreationRequest user = makeValidUser();
        user.setPassword("fsdS@fs");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithInvalidPasswordWithoutLetters() {
        UserCreationRequest user = makeValidUser();
        user.setPassword("41234234@");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithInvalidPasswordWithoutSpecial() {
        UserCreationRequest user = makeValidUser();
        user.setPassword("4123dsSsaS");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithInvalidPasswordWithLessThanSixChar() {
        UserCreationRequest user = makeValidUser();
        user.setPassword("42@aS");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidUserWithInvalidPasswordWithMoreThanTenChar() {
        UserCreationRequest user = makeValidUser();
        user.setPassword("42@aSs#21Dsd3Awq332");
        Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
}
