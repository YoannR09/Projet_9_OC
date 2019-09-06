package com.dummy.myerp.consumer.dao.impl.db.cache;

import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.Assert;
import org.junit.Test;


public class CompteComptableDaoCacheTest extends ConsumerTestCase {

    @Test
    public void getByNumero() {

        CompteComptableDaoCache compteComptableDaoCache = new CompteComptableDaoCache();

        Integer numero = new Integer(401);
        CompteComptable compteComptable = compteComptableDaoCache.getByNumero(numero);
        Assert.assertNotNull(compteComptable);
        Assert.assertEquals(compteComptable.getNumero(),numero);
        compteComptable = compteComptableDaoCache.getByNumero(new Integer(2));
        Assert.assertNull(compteComptable);
    }
}
