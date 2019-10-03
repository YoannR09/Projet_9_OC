package com.dummy.myerp.testbusiness.business.manager;

import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.dummy.myerp.testbusiness.business.builder.EcritureComptableBuilder.aEcritureComptable;
import static com.dummy.myerp.testbusiness.business.builder.SequenceBuilder.aSequence;
import static org.junit.jupiter.api.Assertions.*;

public class ManagerToDbTest extends BusinessTestCase {

    ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    TransactionManager ts = new TransactionManager();
    private static Integer id;



    @Test
    @Order(1)
    public void create_ecritureComptable() throws FunctionalException {

        // GIVEN
        EcritureComptable ecritureComptable = aEcritureComptable()
                .journal(new JournalComptable("AC","Achat"))
                .ref("AC-2019/00450")
                .libelle("Achat")
                .date(new Date())
                .build();
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(123),
                null));
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(123)));

        TransactionStatus vTS = ts.beginTransactionMyERP();
        manager.getDaoProxy().getComptabiliteDao().insertEcritureComptable(ecritureComptable);
        ts.rollbackMyERP(vTS);
    }


    @Test
    @Order(2)
    public void select_list_ecritureComptable(){

        // WHEN
        List<EcritureComptable> vList = manager.getDaoProxy().getComptabiliteDao().getListEcritureComptable();

        // THEN
        assertNotNull(vList);
        assertTrue(vList.size() != 0);
    }

    @Test
    @Order(3)
    public void delete_ecritureComptable(){

        // GIVEN
        TransactionStatus vTS = ts.beginTransactionMyERP();
        List<EcritureComptable> vList = manager.getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        Integer id = vList.get(0).getId();

        // WHEN
        manager.deleteEcritureComptable(id);
        List<EcritureComptable> vList2 = manager.getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        // THEN

        assertNotEquals(vList2.get(0).getId(),id);
        ts.rollbackMyERP(vTS);
    }

    @Test
    @Order(4)
    public void create_sequenceEcritureComptable() throws NotFoundException {

        // GIVEN
        TransactionStatus vTS = ts.beginTransactionMyERP();
        SequenceEcritureComptable sequenceEcritureComptable = aSequence().annee(2016).journalCode("AC").build();
        manager.getDaoProxy().getComptabiliteDao().deleteSequenceEcritureComptable(sequenceEcritureComptable);
        sequenceEcritureComptable.setDerniereValeur(49);
        // WHEN
        manager.getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(sequenceEcritureComptable);

        // THEN
        assertNotNull(manager.getDaoProxy().getComptabiliteDao().getSequenceViaCodeAnnee(sequenceEcritureComptable));
        ts.rollbackMyERP(vTS);

    }

    @Test
    @Order(5)
    public void select_list_compteComptable(){

        // WHEN
        List<CompteComptable> vList = manager.getDaoProxy().getComptabiliteDao().getListCompteComptable();

        // THEN
        assertNotNull(vList);
        assertNotEquals(vList.size(),0);
    }
}
