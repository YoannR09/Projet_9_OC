package com.dummy.myerp.testconsumer.consumer.dao;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.builder.EcritureComptableBuilder;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EcritureComptableSQLTest extends ConsumerTestCase {

    ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();
    private static Integer id;

    @Test
    @Order(1)
    public void create_ecritureComptable_test() throws NotFoundException {

        // GIVEN
        EcritureComptable ecritureComptable = EcritureComptableBuilder.aEcritureComptable()
                .journal(new JournalComptable("AC","Achat"))
                .ref("AC-2019/00048")
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
        id = ecriture.getId();
    }

    @Test
    @Order(2)
    public void select_ecritureComptable_by_id_test() throws NotFoundException {

        // WHEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);

        // THEN
        assertNotNull(ecritureComptable);
        assertEquals(ecritureComptable.getId(),id);
        assertEquals(ecritureComptable.getReference(),"AC-2019/00048");
        assertEquals(ecritureComptable.getLibelle(),"Achat");
    }

    @Test
    @Order(3)
    public void select_ecritureComptable_by_ref_test() throws NotFoundException {

        // WHEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef("AC-2019/00048");

        // THEN
        assertNotNull(ecritureComptable);
        assertEquals(ecritureComptable.getId(),id);
        assertEquals(ecritureComptable.getLibelle(),"Achat");
    }

    @Test
    @Order(4)
    public void insert_ligneEcriture_test() throws NotFoundException {

        // GIVEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(123),
                null));
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(123)));

        // WHEN
        comptabiliteDao.insertListLigneEcritureComptable(ecritureComptable);
        EcritureComptable ecritureComptable2 =  comptabiliteDao.getEcritureComptable(id);
        comptabiliteDao.loadListLigneEcriture(ecritureComptable2);

        // THEN
        assertEquals(ecritureComptable2.getListLigneEcriture().size(),2);
    }

    @Test
    @Order(5)
    public void load_ligneEcriture_test() throws NotFoundException {

        // GIVEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);

        // WHEN
        comptabiliteDao.loadListLigneEcriture(ecritureComptable);
    }

    @Test
    @Order(6)
    public void select_list_ecritureComptable_test(){

        // WHEN
        List<EcritureComptable> vList = comptabiliteDao.getListEcritureComptable();

        // THEN
        assertNotNull(vList);
        assertTrue(vList.size() != 0);
    }

    @Test
    @Order(7)
    public void delete_ligne_ecritureComptable_test() throws NotFoundException {

        // WHEN
        comptabiliteDao.deleteListLigneEcritureComptable(id);
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);

        // THEN
        assertEquals(ecritureComptable.getListLigneEcriture().size(),0);
    }

    @Test
    @Order(8)
    public void delete_ecritureComptable_test(){

        // WHEN
        comptabiliteDao.deleteEcritureComptable(id);

        // THEN
        assertThrows(new NotFoundException().getClass(), () -> comptabiliteDao.getEcritureComptable(id));
    }
}
