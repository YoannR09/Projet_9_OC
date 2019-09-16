package com.dummy.myerp.business.impl.manager;


import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.validation.*;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ComptabiliteManagerImplTest extends BusinessTestCase {

    private ComptabiliteManagerImpl manager;
    private FakeComptabiblieDao fakeComptabiblieDao = new FakeComptabiblieDao();
    private EcritureComptable vEcritureComptable;


    @BeforeEach
    public void setUp(){
        manager = new ComptabiliteManagerImpl();
    }

    @Test
    public void addReference() throws NotFoundException, FunctionalException {

        // GIVEN
        manager = spy(new ComptabiliteManagerImpl());
        DaoProxy daoProxy = () -> fakeComptabiblieDao;
        when(manager.getDaoProxy()).thenReturn(daoProxy);
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        String oldRef = "AC-2019/00001";
        vEcritureComptable.setReference(oldRef);

        // WHEN
        manager.addReference(vEcritureComptable);

        // THEN
        assertNotEquals(oldRef,vEcritureComptable.getReference());
        vEcritureComptable.setJournal(new JournalComptable("WW","TestThrow"));
        assertThrows(new NotFoundException().getClass(), () -> manager.addReference(vEcritureComptable));
    }

    @Test
    public void getListCompteComptable() {

        // GIVEN
        manager = spy(new ComptabiliteManagerImpl());
        DaoProxy daoProxy = () -> fakeComptabiblieDao;
        when(manager.getDaoProxy()).thenReturn(daoProxy);

        // WHEN
        List<CompteComptable> vList = manager.getListCompteComptable();

        // THEN
        assertNotNull(vList);
        assertEquals(vList.size(),2);
    }

    @Test
    public void getListJournalComptable() {

        // GIVEN
        manager = spy(new ComptabiliteManagerImpl());
        DaoProxy daoProxy = () -> fakeComptabiblieDao;
        when(manager.getDaoProxy()).thenReturn(daoProxy);

        // WHEN
        List<JournalComptable> vList = manager.getListJournalComptable();

        // THEN
        assertNotNull(vList);
        assertEquals(vList.size(),2);
    }


    @Test
    public void getListEcritureComptable() {
        // GIVEN
        manager = spy(new ComptabiliteManagerImpl());
        DaoProxy daoProxy = () -> fakeComptabiblieDao;
        when(manager.getDaoProxy()).thenReturn(daoProxy);

        // WHEN
        List<EcritureComptable> vList = manager.getListEcritureComptable();

        // THEN
        assertNotNull(vList);
        assertEquals(vList.size(),2);
    }


    @Test
    public void checkEcritureComptableUnit() throws Exception {

        // GIVEN
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        // WHEN
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test
    public void checkEcritureComptableUnitViolation() throws FunctionalException {
        vEcritureComptable = new EcritureComptable();

        assertThrows(new FunctionalException("L'écriture comptable n'est pas équilibrée.").getClass(), () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }


    /**
     * Test si l'écriture comptable est équilibrée
     */
    @Test
    public void checkEcritureComptableUnitRG2(){

        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("BQ-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));

        assertThrows(new FunctionalException("L'écriture comptable n'est pas équilibrée.").getClass(), () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }

    @Test
    public void checkEcritureComptableUnitRG3(){

        vEcritureComptable = mock(EcritureComptable.class);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("BQ-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));

        assertThrows(new FunctionalException("test").getClass(), () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }

    @Test
    public void checkEcritureComptableUnitRG5(){

        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2015/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));

        assertThrows(new FunctionalException("L'année dans la référence doit correspondre à la date de l'écriture.").getClass(), () -> manager.checkEcritureComptableUnit(vEcritureComptable));

        vEcritureComptable.setReference("BQ-2019/00001");

        assertThrows(new FunctionalException("Le code journal dans la référence doit correspondre à celui du code journal").getClass(), () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }

    @Test
    public void checkEcritureComptableUnitRG6() throws FunctionalException {

        // GIVEN
        manager = spy(new ComptabiliteManagerImpl());
        DaoProxy daoProxy = () -> fakeComptabiblieDao;
        when(manager.getDaoProxy()).thenReturn(daoProxy);
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2015/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));

        // WHEN
        manager.checkEcritureComptableContext(vEcritureComptable);

        // THEN
        assertThrows(new FunctionalException("L'année dans la référence doit correspondre à la date de l'écriture.").getClass(), () -> manager.checkEcritureComptableUnit(vEcritureComptable));
        vEcritureComptable.setReference("BQ-2019/00001");
        assertThrows(new FunctionalException("Le code journal dans la référence doit correspondre à celui du code journal").getClass(), () -> manager.checkEcritureComptableUnit(vEcritureComptable));
        vEcritureComptable.setReference("RT-2019/00011");
        assertThrows(new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.").getClass(), () -> manager.checkEcritureComptableUnit(vEcritureComptable));
    }

    @Test
    public void checkEcritureComptable() throws FunctionalException{

        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/02201");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));

        manager.checkEcritureComptable(vEcritureComptable);
    }

    @Test
    public void deleteEcritureComptable() throws FunctionalException {

        // GIVEN
        manager = spy(new ComptabiliteManagerImpl());
        DaoProxy daoProxy = () -> fakeComptabiblieDao;
        when(manager.getDaoProxy()).thenReturn(daoProxy);
        Integer listSize = manager.getListEcritureComptable().size();

        // WHEN
        manager.deleteEcritureComptable(45);

        // THEN
        assertEquals(listSize-1,manager.getListEcritureComptable().size());


        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setId(45);
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.insertEcritureComptable(vEcritureComptable1);
    }


    @Test
    public void updateEcritureComptable() throws FunctionalException {

        // GIVEN
        manager = spy(new ComptabiliteManagerImpl());
        DaoProxy daoProxy = () -> fakeComptabiblieDao;
        when(manager.getDaoProxy()).thenReturn(daoProxy);
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/02201");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));

        // WHEN
        manager.updateEcritureComptable(vEcritureComptable);

        // THEN
        verify(manager).updateEcritureComptable(vEcritureComptable);
    }



    @Test
    public void insertEcritureComptable() throws FunctionalException {

        // GIVEN
        manager = spy(new ComptabiliteManagerImpl());
        DaoProxy daoProxy = () -> fakeComptabiblieDao;
        when(manager.getDaoProxy()).thenReturn(daoProxy);
        Integer listSize = manager.getListEcritureComptable().size();
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setId(45);
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        // WHEN
        manager.insertEcritureComptable(vEcritureComptable1);

        // THEN
        assertEquals(listSize+1,manager.getListEcritureComptable().size());
    }

    @Test
    public void upsertSequenceEcritureComptable() {
        manager = spy(new ComptabiliteManagerImpl());
        DaoProxy daoProxy = () -> fakeComptabiblieDao;
        when(manager.getDaoProxy()).thenReturn(daoProxy);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(new Integer(2019),new Integer(59));
        sequenceEcritureComptable.setJournalCode("AC");

        // WHEN
        manager.upsertSequenceEcritureComptable(sequenceEcritureComptable);

        // THEN
        verify(manager).upsertSequenceEcritureComptable(sequenceEcritureComptable);
    }
}
