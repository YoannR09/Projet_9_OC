package com.dummy.myerp.consumer.dao.impl.db.cache;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;
import com.dummy.myerp.consumer.dao.impl.db.fake.FakeComptabiblieDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompteComptableDaoCacheTest{

    CompteComptableDaoCache compteComptableDaoCache;

    @Test
    public void getByNumero() {

        // GIVEN
        Integer numero = new Integer(2332);

        // WHEN
        CompteComptable compteComptable = compteComptableDaoCache.getByNumero(numero);

        // THEN
        assertNotNull(compteComptable);
        assertEquals(compteComptable.getNumero(),numero);
    }

    @Test
    public void getByNumeroCompte() {

        // WHEN
        compteComptableDaoCache = new CompteComptableDaoCache();
        CompteComptable compteComptable = compteComptableDaoCache.getByNumeroCompte(4);

        // THEN
        assertNull(compteComptable);
    }

    @Test
    public void getListCompteComptable() {

        // WHEN
        List<CompteComptable> vList = compteComptableDaoCache.getListCompteComptable();

        // THEN
        assertNotNull(vList);
    }

    @BeforeEach
    public void initConsumer(){
        compteComptableDaoCache = new CompteComptableDaoCache();
        DaoProxy dao = mock(DaoProxy.class);
        when(dao.getComptabiliteDao()).thenReturn(new FakeComptabiblieDao());
        ConsumerHelper.configure(dao);
    }

}
