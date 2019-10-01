package com.dummy.myerp.technical.exception;

import com.dummy.myerp.technical.util.spring.NullFactoryBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NullFactoryBeanTest {

    NullFactoryBean nullFactoryBean = new NullFactoryBean(null);

    @Test
    public void getObject() throws Exception {
        assertNull(nullFactoryBean.getObject());
    }

    @Test
    public void getObjectType(){
        assertNull(nullFactoryBean.getObjectType());
    }

    @Test
    public void isSingleton() {
        assertFalse(nullFactoryBean.isSingleton());
    }
}
