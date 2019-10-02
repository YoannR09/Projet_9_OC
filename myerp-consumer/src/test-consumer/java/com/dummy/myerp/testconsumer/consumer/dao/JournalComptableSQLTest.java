package com.dummy.myerp.testconsumer.consumer.dao;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JournalComptableSQLTest extends ConsumerTestCase {

    ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();

    @Test
    @Order(1)
    public void select_list_journalComptable_test(){

        // WHEN
        List<JournalComptable> journalComptables = comptabiliteDao.getListJournalComptable();

        // THEN
        assertNotNull(journalComptables);
    }
}
