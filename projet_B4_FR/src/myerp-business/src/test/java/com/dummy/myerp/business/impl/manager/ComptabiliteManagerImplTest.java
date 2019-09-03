package com.dummy.myerp.business.impl.manager;


import java.math.BigDecimal;

import java.util.Date;

import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import org.mockito.*;


public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    private EcritureComptable vEcritureComptable;

    @Mock
    private AbstractBusinessManager abstractBusinessManager;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void checkEcritureComptableUnit() throws Exception {

        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("BQ-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);

        Mockito.verify(manager).checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {

        manager = new ComptabiliteManagerImpl();

        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);

        Mockito.verify(manager).checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {

        manager = new ComptabiliteManagerImpl();

        EcritureComptable vEcritureComptable;
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

        manager.checkEcritureComptableUnit(vEcritureComptable);

        Mockito.verify(manager).checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws FunctionalException {

        manager = new ComptabiliteManagerImpl();

        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();

        manager.checkEcritureComptableUnit(vEcritureComptable);

        Mockito.verify(manager).checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test
    public void checkEcritureComptableContextViolation() throws FunctionalException {

        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.setId(null);

        manager.checkEcritureComptableContext(vEcritureComptable);

        Mockito.verify(manager).checkEcritureComptableContext(vEcritureComptable);
    }


    @Test
    public void checkEcritureComptable() throws FunctionalException{

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

        manager.checkEcritureComptable(vEcritureComptable);

        Mockito.verify(manager).checkEcritureComptable(vEcritureComptable);
    }

    @Test
    public void deleteEcritureComptable() {

        manager.deleteEcritureComptable(1);

        Mockito.verify(manager).deleteEcritureComptable(1);

    }

    @Test
    public void updateEcritureComptable() throws FunctionalException {

        manager = Mockito.mock(ComptabiliteManagerImpl.class);

        manager.updateEcritureComptable(vEcritureComptable);

        Mockito.verify(manager).updateEcritureComptable(vEcritureComptable);
    }



    @Test
    public void insertEcritureComptable() throws FunctionalException, NotFoundException {

        ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

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


        Mockito.when(abstractBusinessManager.getDaoProxy()).thenReturn(new FakeDaoProxy());

        Mockito.when(abstractBusinessManager.getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference())).thenReturn(new FakeDaoProxy().getComptabiliteDao().getEcritureComptableByRef(vEcritureComptable.getReference()));

        ArgumentCaptor<EcritureComptable> ecritureComptableArgumentCaptor = ArgumentCaptor.forClass(EcritureComptable.class);

        manager.insertEcritureComptable(vEcritureComptable);

        Mockito.verify(manager).insertEcritureComptable(ecritureComptableArgumentCaptor.capture());

        Assert.assertEquals(vEcritureComptable,ecritureComptableArgumentCaptor.getValue());
    }
}
