package com.dummy.myerp.testconsumer.consumer.dao;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.model.builder.SequenceBuilder;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SequenceEcritureComptableSQLTest extends ConsumerTestCase {

    private ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();
    private Integer oldDerniereValeur = 49;

    @Test
    @Order(1)
    public void delete_sequence() throws NotFoundException {

        // GIVEN
        SequenceEcritureComptable sequenceEcritureComptable = SequenceBuilder.aSequence().annee(2016).journalCode("AC").build();
        oldDerniereValeur = comptabiliteDao.getSequenceViaCodeAnnee(sequenceEcritureComptable).getDerniereValeur();

        // WHEN
        comptabiliteDao.deleteSequenceEcritureComptable(sequenceEcritureComptable);

        // THEN
        assertThrows(new NotFoundException().getClass(),() -> comptabiliteDao.getSequenceViaCodeAnnee(sequenceEcritureComptable));
    }

    @Test
    @Order(2)
    public void insert_sequence(){

        // GIVEN
        SequenceEcritureComptable sequenceEcritureComptable = SequenceBuilder.aSequence().annee(2016).journalCode("AC").derniereVal(oldDerniereValeur).build();

        // WHEN
        comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable);

        // THEN
       assertEquals(sequenceEcritureComptable.getDerniereValeur(),oldDerniereValeur);
       sequenceEcritureComptable.setDerniereValeur(sequenceEcritureComptable.getDerniereValeur());
       comptabiliteDao.deleteSequenceEcritureComptable(sequenceEcritureComptable);
       comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable);
    }

    @Test
    @Order(3)
    public void select_sequence_with_code_and_annee() throws NotFoundException {

        // GIVEN
        SequenceEcritureComptable sequenceEcritureComptable = SequenceBuilder.aSequence().annee(2016).journalCode("AC").build();
        SequenceEcritureComptable sequenceEcritureComptable1 = SequenceBuilder.aSequence().annee(2019).journalCode("AC").build();

        // WHEN
        sequenceEcritureComptable = comptabiliteDao.getSequenceViaCodeAnnee(sequenceEcritureComptable);
        System.out.println(sequenceEcritureComptable.getDerniereValeur());

        // THEN
        assertNotNull(sequenceEcritureComptable.getDerniereValeur());
        assertThrows(new NotFoundException().getClass(),() -> comptabiliteDao.getSequenceViaCodeAnnee(sequenceEcritureComptable1));
    }
}
