package com.dummy.myerp.business.impl.manager;


import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class ComptabiliteManagerImplTest extends BusinessTestCase {

    private ComptabiliteManagerImpl manager;

    private EcritureComptable vEcritureComptable;


    @BeforeEach
    public void setUp(){
        manager = new ComptabiliteManagerImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getListCompteComptable() {
        /*
        AbstractBusinessManager abm = spy(AbstractBusinessManager.class);

        manager = spy(new ComptabiliteManagerImpl());

        FakeComptabiblieDao fakeDao = new FakeComptabiblieDao();

        when(abm.getDaoProxy().getComptabiliteDao().getListCompteComptable()).thenReturn(fakeDao.getListCompteComptable());

        when(manager.getDaoProxy().getComptabiliteDao().getListCompteComptable()).thenReturn(fakeDao.getListCompteComptable());

        List<CompteComptable> vList = manager.getDaoProxy().getComptabiliteDao().getListCompteComptable();
        System.out.println(vList);
        */
    }


    @Test
    public void checkEcritureComptableUnit() throws Exception {

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

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test
    public void checkEcritureComptableUnitViolation(){
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

        vEcritureComptable = Mockito.mock(EcritureComptable.class);
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
    public void checkEcritureComptableUnitRG6(){

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
    public void deleteEcritureComptable() {

        manager = Mockito.mock(ComptabiliteManagerImpl.class);

        manager.deleteEcritureComptable(1);

        Mockito.verify(manager).deleteEcritureComptable(1);
    }

    @Test
    public void updateEcritureComptable() {

        manager = Mockito.mock(ComptabiliteManagerImpl.class);

        manager.updateEcritureComptable(vEcritureComptable);

        Mockito.verify(manager).updateEcritureComptable(vEcritureComptable);
    }



    @Test
    public void insertEcritureComptable() throws FunctionalException {

        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(123)));

        TransactionStatus transactionStatus = getTransactionManager().beginTransactionMyERP();
        assertThrows(new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.").getClass(), () -> manager.insertEcritureComptable(vEcritureComptable));
        vEcritureComptable.setReference("BC-2019/01532");
        assertThrows(new FunctionalException("Le code journal dans la référence doit correspondre à celui du code journal.").getClass(), () -> manager.insertEcritureComptable(vEcritureComptable));
        vEcritureComptable.setReference("AC-2019/01532");
        manager.insertEcritureComptable(vEcritureComptable);
        getTransactionManager().rollbackMyERP(transactionStatus);
    }
}
