package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class DaoProxyImplTest{

    @Test
    public void getComptabiliteDao() {

        // GIVEN
        DaoProxyImpl daoProxy = new DaoProxyImpl();
        daoProxy.setComptabiliteDao(mock(ComptabiliteDao.class));

        // WHEN
        ComptabiliteDao comptabiliteDao = daoProxy.getComptabiliteDao();

        // THEN
        assertNotNull(comptabiliteDao);
    }
}
