package com.dummy.myerp.business.impl.manager;


import java.math.BigDecimal;

import java.util.Date;

import com.dummy.myerp.technical.exception.NotFoundException;

import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.*;


public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager;

    private EcritureComptable vEcritureComptable;


    @Before
    public void setUp(){
        manager = new ComptabiliteManagerImpl();
        MockitoAnnotations.initMocks(this);
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
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));

        manager.checkEcritureComptable(vEcritureComptable);


        Mockito.verify(manager).checkEcritureComptable(vEcritureComptable);
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
    public void insertEcritureComptable() throws FunctionalException, NotFoundException {

        manager = Mockito.mock(ComptabiliteManagerImpl.class);

        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("BQ-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));

        ArgumentCaptor<EcritureComptable> ecritureComptableArgumentCaptor = ArgumentCaptor.forClass(EcritureComptable.class);

        Mockito.when(manager.getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference())).thenReturn(vEcritureComptable);

        manager.insertEcritureComptable(vEcritureComptable);

        Mockito.verify(manager).insertEcritureComptable(ecritureComptableArgumentCaptor.capture());

        Assert.assertEquals(vEcritureComptable,ecritureComptableArgumentCaptor.getValue());
    }
}
