package com.dummy.myerp.technical.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotFoundExceptionTest {

    Object item = null;

    public void notFoundException() throws NotFoundException {
        if (item == null){
            throw new NotFoundException();
        }
    }

    public void notFoundException_with_string() throws NotFoundException {
        if (item == null){
            throw new NotFoundException("Erreur");
        }
    }

    public void notFoundException_with_cause() throws NotFoundException {
        if (item == null){
            throw new NotFoundException(new Throwable("Cause"));
        }
    }

    public void notFoundException_with_string_and_cause() throws NotFoundException {
        if (item == null){
            throw new NotFoundException("Erreur",new Throwable("Cause"));
        }
    }

    @Test
    public void initAllTestNotFoundException(){
        assertThrows(new NotFoundException().getClass(), () -> notFoundException());
        assertThrows(new NotFoundException("Erreur").getClass(), () -> notFoundException_with_string());
        assertThrows(new NotFoundException(new Throwable("Cause")).getClass(), () -> notFoundException_with_cause());
        assertThrows(new NotFoundException("Erreur",new Throwable("Cause")).getClass(), () -> notFoundException_with_string_and_cause());
    }
}
