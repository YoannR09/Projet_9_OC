package com.dummy.myerp.consumer.dao.impl.db.cache;


import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableDaoCacheTest extends ConsumerTestCase {

    @Test
    public void getByCode() {

        JournalComptableDaoCache journalComptableDaoCache = new JournalComptableDaoCache();

        ConsumerHelper consumerHelper = Mockito.mock(ConsumerHelper.class);

        List<JournalComptable> vList = new ArrayList<>();
        vList.add(new JournalComptable("AC","Libelle"));
        vList.add(new JournalComptable("EC","Libelle"));

        Mockito.when(consumerHelper.getDaoProxy().getComptabiliteDao().getListJournalComptable()).thenReturn(vList);

        JournalComptable journalComptable = journalComptableDaoCache.getByCode("AC");
        Assert.assertEquals(journalComptable.getCode(),"AC");
        Assert.assertNotNull(journalComptable);

        journalComptable = journalComptableDaoCache.getByCode("EFAS");
        Assert.assertNull(journalComptable);
    }
}
