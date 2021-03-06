package com.dummy.myerp.testconsumer.consumer.dao;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static com.dummy.myerp.testconsumer.consumer.builder.EcritureComptableBuilder.aEcritureComptable;
import static org.junit.jupiter.api.Assertions.*;

public class EcritureComptableSQLTest extends ConsumerTestCase {

    ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();

    private static String REF = "AC-2019/00111";


    public void create_ecritureComptable_test() throws NotFoundException {

        // GIVEN
        EcritureComptable ecritureComptable = aEcritureComptable()
                .journal(new JournalComptable("AC","Achat"))
                .ref(REF)
                .libelle("Achat")
                .date(new Date(2019))
                .build();

        // WHEN
        comptabiliteDao.insertEcritureComptable(ecritureComptable);
        EcritureComptable ecriture = comptabiliteDao.getEcritureComptableByRef(ecritureComptable.getReference());

        // THEN
        assertEquals(ecriture.getJournal().getCode(),"AC");
        assertEquals(ecriture.getJournal().getLibelle(),"Achat");
        assertEquals(ecriture.getLibelle(),"Achat");
    }

    public void select_ecritureComptable_by_id_test() throws NotFoundException {

        // GIVEN
        Integer id = comptabiliteDao.getEcritureComptableByRef(REF).getId();

        // WHEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);

        // THEN
        assertNotNull(ecritureComptable);
        assertEquals(ecritureComptable.getId(),id);
        assertEquals(ecritureComptable.getReference(),REF);
        assertEquals(ecritureComptable.getLibelle(),"Achat");
    }

    public void select_ecritureComptable_by_ref_test() throws NotFoundException {

        // WHEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(REF);

        // THEN
        assertNotNull(ecritureComptable);
        assertEquals(ecritureComptable.getLibelle(),"Achat");
    }

    public void insert_ligneEcriture_test() throws NotFoundException {

        // GIVEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(REF);
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(123),
                null));
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(123)));

        // WHEN
        comptabiliteDao.insertListLigneEcritureComptable(ecritureComptable);
        EcritureComptable ecritureComptable2 =  comptabiliteDao.getEcritureComptable(ecritureComptable.getId());
        comptabiliteDao.loadListLigneEcriture(ecritureComptable2);

        // THEN
        assertEquals(ecritureComptable2.getListLigneEcriture().size(),2);
    }

    public void load_ligneEcriture_test() throws NotFoundException {

        // GIVEN
        Integer id = comptabiliteDao.getEcritureComptableByRef(REF).getId();

        // GIVEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);

        // WHEN
        comptabiliteDao.loadListLigneEcriture(ecritureComptable);
    }

    public void select_list_ecritureComptable_test(){

        // WHEN
        List<EcritureComptable> vList = comptabiliteDao.getListEcritureComptable();

        // THEN
        assertNotNull(vList);
        assertTrue(vList.size() != 0);
    }

    public void delete_ligne_ecritureComptable_test() throws NotFoundException {

        // GIVEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(REF);

        // WHEN
        comptabiliteDao.deleteListLigneEcritureComptable(ecritureComptable.getId());

        // THEN
        EcritureComptable ecritureComptable1 = comptabiliteDao.getEcritureComptableByRef(REF);
        assertEquals(ecritureComptable1.getListLigneEcriture().size(),0);
    }


    public void delete_ecritureComptable_test() throws NotFoundException {

        // GIVEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(REF);

        // WHEN
        comptabiliteDao.deleteEcritureComptable(ecritureComptable.getId());

        // THEN
        assertThrows(new NotFoundException().getClass(), () -> comptabiliteDao.getEcritureComptableByRef(REF));
    }

    @Test
    public void initTest() throws NotFoundException {
        create_ecritureComptable_test();
        select_ecritureComptable_by_id_test();
        select_ecritureComptable_by_ref_test();
        insert_ligneEcriture_test();
        load_ligneEcriture_test();
        select_list_ecritureComptable_test();
        delete_ligne_ecritureComptable_test();
        delete_ecritureComptable_test();
    }
}
