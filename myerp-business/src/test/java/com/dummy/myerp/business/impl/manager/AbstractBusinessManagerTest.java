package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class AbstractBusinessManagerTest extends AbstractBusinessManager{

    @Test
    public void getConstraintValidatorTest() {
        Validator vValidator = getConstraintValidator();
        assertNotNull(vValidator);
    }

    @Test
    public void getBusinessProxyTest() {
       BusinessProxy businessProxy = getBusinessProxy();
       assertNotNull(businessProxy);
    }

    @Test
    public void getDaoProxyTest() {
        DaoProxy daoProxy = getDaoProxy();
        assertNotNull(daoProxy);
    }

    @Test
    public void getTransactionManagerTest() {
        TransactionManager transactionManager = getTransactionManager();
        assertNotNull(transactionManager);
    }

    @BeforeEach
    public void init(){
        BusinessProxy businessProxy = mock(BusinessProxy.class);
        DaoProxy daoProxy = () -> new FakeComptabiblieDao();
        TransactionManager transactionManager = mock(TransactionManager.class);
        configure(businessProxy,daoProxy,transactionManager);
    }

}
