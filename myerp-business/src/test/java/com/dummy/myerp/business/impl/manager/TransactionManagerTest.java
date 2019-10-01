package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.impl.TransactionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.*;

public class TransactionManagerTest extends TransactionManager {

    private TransactionManager transactionManager;

    @Test
    public void getInstanceTest() {
        TransactionManager tm = getInstance();
        assertNotNull(tm);
    }

    @Test
    public void beginTransactionMyERPTest() {
        TransactionStatus transa = transactionManager.beginTransactionMyERP();
        assertNull(transa);
    }

    @Test
    public void commitMyERP() {
        TransactionStatus ts = mock(TransactionStatus.class);
        transactionManager.commitMyERP(ts);
    }

    @Test
    public void rollbackMyERP() {
        TransactionStatus ts = mock(TransactionStatus.class);
        transactionManager.rollbackMyERP(ts);
    }

    @BeforeEach
    public void init(){
        PlatformTransactionManager platformTransactionManager = mock(PlatformTransactionManager.class);
        when(platformTransactionManager.getTransaction(anyObject())).thenReturn(null);
        transactionManager = new TransactionManager(platformTransactionManager);
    }
}
