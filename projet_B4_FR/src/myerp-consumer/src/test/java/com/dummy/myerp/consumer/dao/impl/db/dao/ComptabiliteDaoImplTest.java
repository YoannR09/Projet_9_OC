package com.dummy.myerp.consumer.dao.impl.db.dao;


import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;

import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import com.dummy.myerp.testconsumer.consumer.SpringRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


import java.math.BigDecimal;
import java.util.List;

public class ComptabiliteDaoImplTest extends ConsumerTestCase {

    private static final Logger LOGGER = LogManager.getLogger(SpringRegistry.class);
    private ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();

    EcritureComptable ecritureComptable;

    /**
     * Test de la méthode pour récupérer une liste de compteComptable
     */
    @Test
    public void getListCompteComptable() {
        try {
            List<CompteComptable> vList = comptabiliteDao.getListCompteComptable();

            Assert.assertNotNull(vList);
        }catch (Exception e){
            LOGGER.error(e);
            Assert.assertNull(e);
        }
    }

    /**
     * Test de la méthode pour récupérer une liste de journalComptable
     */
    @Test
    public void getListJournalComptable() {
        try {

            List<JournalComptable> vList = comptabiliteDao.getListJournalComptable();

            Assert.assertNotNull(vList);
        }catch (Exception e){
            LOGGER.error(e);
            Assert.assertNull(e);
        }
    }

    /**
     * Test de la méthode pour récupérer une liste d'écritureComptable
     */
    @Test
    public void getListEcritureComptable() {
        try {
            List<EcritureComptable> vList = comptabiliteDao.getListEcritureComptable();

            Assert.assertNotNull(vList);
        }catch (Exception e){
            LOGGER.error(e);
            Assert.assertNull(e);
        }
    }

    /**
     * Test de la méthode pour récupérer une ecritureComptable via un id
     */
    @Test
    public void getEcritureComptable(){
        try {
            ecritureComptable = comptabiliteDao.getEcritureComptable(-2);

            Assert.assertNotNull(ecritureComptable);
        }catch (Exception e){
            LOGGER.error(e);
            Assert.assertNull(e);
        }
    }

    /**
     * Test de la méthode pour récupérer une ecritureComptable via une réference
     */
    @Test
    public void getEcritureComptableByRef(){
        try {
            List<EcritureComptable> vList = comptabiliteDao.getListEcritureComptable();

            ecritureComptable = comptabiliteDao.getEcritureComptableByRef(vList.get(2).getReference());
            Assert.assertNotNull(ecritureComptable);
        }catch (Exception e){
            LOGGER.error(e);
            Assert.assertNull(e);
        }
    }

    /**
     * Test de la méthode pour initaliser la liste des ligneEcriture de l'ecritureComptable
     */
    @Test
    public void loadListLigneEcriture() {
        try {
            EcritureComptable ecritureComptableTest = new EcritureComptable();
            ecritureComptableTest.setId(-2);
            comptabiliteDao.loadListLigneEcriture(ecritureComptableTest);
            Assert.assertNotNull(ecritureComptableTest.getListLigneEcriture());
            Assert.assertEquals(comptabiliteDao.getEcritureComptable(-2).getListLigneEcriture().toString(),ecritureComptableTest.getListLigneEcriture().toString());
        }catch (Exception e){
            LOGGER.error(e);
            Assert.assertNull(e);
        }
    }

    @Test
    public void deleteEcritureComptable() {

        final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

        Mockito.when(template.update(Mockito.anyObject(), Mockito.any(MapSqlParameterSource.class))).thenReturn(1);

        comptabiliteDao.deleteEcritureComptable(5);
    }

    @Test
    public void deleteListLigneEcritureComptable() {

        final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

        Mockito.when(template.update(Mockito.anyObject(), Mockito.any(MapSqlParameterSource.class))).thenReturn(1);

        comptabiliteDao.deleteListLigneEcritureComptable(5);
    }



    @BeforeEach
    public void initVar(){
        ecritureComptable = new EcritureComptable();
    }

    @AfterClass
    public static void endTestClass(){
        LOGGER.info("Fin du test de la classe ComptabibliteDaoImpl");
    }

    /*
    public void catchInfo(Exception e){
        LOGGER.error(e);
        Assert.assertNull(e);
    }
    */
}
