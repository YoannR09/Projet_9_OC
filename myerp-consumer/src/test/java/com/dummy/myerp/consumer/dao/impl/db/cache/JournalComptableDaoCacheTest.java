package com.dummy.myerp.consumer.dao.impl.db.cache;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.consumer.dao.impl.db.fake.FakeComptabiblieDao;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JournalComptableDaoCacheTest{

    JournalComptableDaoCache journalComptableDaoCache;


    @Test
    public void getByCode() {

       // GIVEN
        String code = "AC";

        // WHEN
        JournalComptable journalComptable = journalComptableDaoCache.getByCode("AC");

        //THEN
        assertEquals(journalComptable.getCode(),code);
    }

    @Test
    public void getByCodeJournalTest() {

        // THEN
        JournalComptable journalComptable = journalComptableDaoCache.getByCodeJournal("AC");
        JournalComptable journalComptable2 = journalComptableDaoCache.getByCodeJournal("TC");

        // THEN
        assertNotNull(journalComptable);
        assertNull(journalComptable2);
    }

    @Test
    public void getListJournalComptableTest() {

        // WHEN
        List<JournalComptable> vList = journalComptableDaoCache.getListJournalComptable();

        // THEN
        assertNotNull(vList);
    }

    @BeforeEach
    public void initConsumer(){
        journalComptableDaoCache = new JournalComptableDaoCache();
        DaoProxy dao = mock(DaoProxy.class);
        when(dao.getComptabiliteDao()).thenReturn(new FakeComptabiblieDao());
        ConsumerHelper.configure(dao);
    }
}
