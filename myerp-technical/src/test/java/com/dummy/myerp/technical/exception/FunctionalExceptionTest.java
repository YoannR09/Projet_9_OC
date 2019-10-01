package com.dummy.myerp.technical.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunctionalExceptionTest {

    int i = 3 ;

    public void functionalException_with_string() throws FunctionalException {
        if (i != 2){
            throw new FunctionalException("Erreur");
        }
    }

    public void functionalException_with_cause() throws FunctionalException {
        if (i != 2){
            throw new FunctionalException(new Throwable("Cause"));
        }
    }

    public void functionalException_with_string_and_cause() throws FunctionalException {
        if (i != 2){
            throw new FunctionalException("Erreur",new Throwable("Cause"));
        }
    }

    @Test
    public void initAllTestFunctionalException(){
        assertThrows(new FunctionalException("Erreur").getClass(), () -> functionalException_with_string());
        assertThrows(new FunctionalException(new Throwable("Cause")).getClass(), () -> functionalException_with_cause());
        assertThrows(new FunctionalException("Erreur",new Throwable("Cause")).getClass(), () -> functionalException_with_string_and_cause());
    }
}
