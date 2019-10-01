package com.dummy.myerp.technical.exception;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TechnicalExceptionTest {

    public void technicalException_with_string() throws TechnicalException {
       throw new TechnicalException("Erreur");
    }

    public void technicalException_with_cause() throws TechnicalException {
        throw new TechnicalException(new Throwable("Cause"));
    }

    public void technicalException_with_string_and_cause() throws TechnicalException {
        throw new TechnicalException("Erreur",new Throwable("Cause"));
    }

    @Test
    public void initAllTestTechnicalException(){
        assertThrows(new TechnicalException("Erreur").getClass(), () -> technicalException_with_string());
        assertThrows(new TechnicalException(new Throwable("Cause")).getClass(), () -> technicalException_with_cause());
        assertThrows(new TechnicalException("Erreur",new Throwable("Cause")).getClass(), () -> technicalException_with_string_and_cause());
    }
}
