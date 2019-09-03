package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;


public class FakeDaoProxy implements DaoProxy {

    FakeComptabiblieDao fakeComptabiblieDao = new FakeComptabiblieDao();

    @Override
    public ComptabiliteDao getComptabiliteDao() {
        return fakeComptabiblieDao;
    }

}
