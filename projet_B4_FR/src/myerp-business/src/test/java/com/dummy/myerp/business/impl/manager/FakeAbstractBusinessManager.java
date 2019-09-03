package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.mockito.Mockito;

import javax.validation.Validator;

public class FakeAbstractBusinessManager extends AbstractBusinessManager {

    @Override
    protected BusinessProxy getBusinessProxy() {
        return Mockito.mock(BusinessProxy.class);
    }

    @Override
    protected TransactionManager getTransactionManager() {
        return Mockito.mock(TransactionManager.class);
    }

    @Override
    public DaoProxy getDaoProxy() {
        return Mockito.mock(DaoProxy.class);
    }

    @Override
    protected Validator getConstraintValidator() {
        return Mockito.mock(Validator.class);
    }
}
