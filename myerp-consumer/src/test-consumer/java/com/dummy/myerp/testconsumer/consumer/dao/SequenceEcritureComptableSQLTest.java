package com.dummy.myerp.testconsumer.consumer.dao;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.dummy.myerp.testconsumer.consumer.builder.SequenceBuilder.aSequence;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SequenceEcritureComptableSQLTest extends ConsumerTestCase {

    private ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();

    public void delete_sequence(){

        // GIVEN
        SequenceEcritureComptable sequenceEcritureComptable = aSequence().annee(2016).journalCode("AC").build();

        // WHEN
        comptabiliteDao.deleteSequenceEcritureComptable(sequenceEcritureComptable);

        // THEN
        assertThrows(new NotFoundException().getClass(),() -> comptabiliteDao.getSequenceViaCodeAnnee(sequenceEcritureComptable));
    }

    public void insert_sequence(){

        // GIVEN
        SequenceEcritureComptable sequenceEcritureComptable = aSequence().annee(2016).journalCode("AC").derniereVal(49).build();

        // WHEN
        comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable);

        // THEN
        assertEquals(sequenceEcritureComptable.getDerniereValeur(),49);
        sequenceEcritureComptable.setDerniereValeur(sequenceEcritureComptable.getDerniereValeur());
        comptabiliteDao.deleteSequenceEcritureComptable(sequenceEcritureComptable);
        comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable);
    }

    public void select_sequence_with_code_and_annee() throws NotFoundException {

        // GIVEN
        SequenceEcritureComptable sequenceEcritureComptable = aSequence().annee(2016).journalCode("AC").build();
        SequenceEcritureComptable sequenceEcritureComptable1 = aSequence().annee(2019).journalCode("AC").build();

        // WHEN
        sequenceEcritureComptable = comptabiliteDao.getSequenceViaCodeAnnee(sequenceEcritureComptable);

        // THEN
        assertNotNull(sequenceEcritureComptable.getDerniereValeur());
        assertThrows(new NotFoundException().getClass(),() -> comptabiliteDao.getSequenceViaCodeAnnee(sequenceEcritureComptable1));
    }

    @Test
    public void initTest() throws NotFoundException {
        delete_sequence();
        insert_sequence();
        select_sequence_with_code_and_annee();
    }
}
