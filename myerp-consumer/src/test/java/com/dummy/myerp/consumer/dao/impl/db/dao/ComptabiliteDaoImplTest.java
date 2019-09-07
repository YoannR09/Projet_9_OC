package com.dummy.myerp.consumer.dao.impl.db.dao;


import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.model.bean.comptabilite.*;


import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import com.dummy.myerp.testconsumer.consumer.SpringRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;



import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComptabiliteDaoImplTest extends ConsumerTestCase {

    private static final Logger LOGGER = LogManager.getLogger(SpringRegistry.class);
    private ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();
    private String sqlRequet = null;

    EcritureComptable ecritureComptable;

    @Test
    public void  getInstance() {
        Assert.assertNotNull(comptabiliteDao.getInstance());
        comptabiliteDao = comptabiliteDao.getInstance();
        Assert.assertEquals(comptabiliteDao,comptabiliteDao.getInstance());
    }

    @Test
    public void consumerTestCase(){
        
    }

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
            assertThrows(new Exception().getClass(), () -> comptabiliteDao.getEcritureComptable(9999));
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
            assertThrows(new Exception().getClass(), () -> comptabiliteDao.getEcritureComptableByRef("PC"));
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
    public void insertEcritureComptable() {
        ecritureComptable = new EcritureComptable();

        String journalCode = "AC";
        String ref = "AC-2019/8884";
        String libelle = "Libelle";

        JournalComptableDaoCache journalComptableDaoCache = Mockito.mock(JournalComptableDaoCache.class);

        JournalComptable journalComptable = new JournalComptable("AC","Libelle");
        Mockito.when(journalComptableDaoCache.getByCode(Mockito.anyString())).thenReturn(journalComptable);

        ecritureComptable.setDate(new Date());
        ecritureComptable.setReference(ref);
        ecritureComptable.setLibelle(libelle);
        ecritureComptable.setJournal(journalComptableDaoCache.getByCode(journalCode));

        comptabiliteDao.insertEcritureComptable(ecritureComptable);
    }

    @Test
    public void insertListLigneEcritureComptable() {

        ecritureComptable = new EcritureComptable();

        String journalCode = "AC";
        String ref = "AC-2019/8884";
        String libelle = "Libelle";

        JournalComptableDaoCache journalComptableDaoCache = Mockito.mock(JournalComptableDaoCache.class);

        JournalComptable journalComptable = new JournalComptable("AC","Libelle");
        Mockito.when(journalComptableDaoCache.getByCode(Mockito.anyString())).thenReturn(journalComptable);

        ecritureComptable.setDate(new Date());
        ecritureComptable.setReference(ref);
        ecritureComptable.setLibelle(libelle);
        ecritureComptable.setJournal(journalComptableDaoCache.getByCode(journalCode));

        comptabiliteDao.insertListLigneEcritureComptable(ecritureComptable);
    }

    @Test
    public void updateEcritureComptable() {

        ecritureComptable = new EcritureComptable();

        String journalCode = "AC";
        String ref = "AC-2019/8884";
        String libelle = "Libelle";

        JournalComptableDaoCache journalComptableDaoCache = Mockito.mock(JournalComptableDaoCache.class);

        JournalComptable journalComptable = new JournalComptable("AC","Libelle");
        Mockito.when(journalComptableDaoCache.getByCode(Mockito.anyString())).thenReturn(journalComptable);

        ecritureComptable.setDate(new Date());
        ecritureComptable.setId(new Integer(3));
        ecritureComptable.setReference(ref);
        ecritureComptable.setLibelle(libelle);
        ecritureComptable.setJournal(journalComptableDaoCache.getByCode(journalCode));

        comptabiliteDao.updateEcritureComptable(ecritureComptable);
    }

    @Test
    public void deleteEcritureComptable() {
        ecritureComptable = new EcritureComptable();

        String journalCode = "AC";
        String ref = "AC-2019/8884";
        String libelle = "Libelle";

        JournalComptableDaoCache journalComptableDaoCache = Mockito.mock(JournalComptableDaoCache.class);

        JournalComptable journalComptable = new JournalComptable("AC","Libelle");
        Mockito.when(journalComptableDaoCache.getByCode(Mockito.anyString())).thenReturn(journalComptable);

        ecritureComptable.setDate(new Date());
        ecritureComptable.setId(new Integer(3));
        ecritureComptable.setReference(ref);
        ecritureComptable.setLibelle(libelle);
        ecritureComptable.setJournal(journalComptableDaoCache.getByCode(journalCode));

        comptabiliteDao.deleteEcritureComptable(new Integer(3));
    }

   @Test
    public void deleteListLigneEcritureComptable() {
       ecritureComptable = new EcritureComptable();

       String journalCode = "AC";
       String ref = "AC-2019/8884";
       String libelle = "Libelle";

       JournalComptableDaoCache journalComptableDaoCache = Mockito.mock(JournalComptableDaoCache.class);

       JournalComptable journalComptable = new JournalComptable("AC","Libelle");
       Mockito.when(journalComptableDaoCache.getByCode(Mockito.anyString())).thenReturn(journalComptable);

       ecritureComptable.setDate(new Date());
       ecritureComptable.setId(new Integer(3));
       ecritureComptable.setReference(ref);
       ecritureComptable.setLibelle(libelle);
       ecritureComptable.setJournal(journalComptableDaoCache.getByCode(journalCode));

       comptabiliteDao.deleteListLigneEcritureComptable(new Integer(3));
    }

    @Test
    public void setSQLgetListCompteComptable() throws NoSuchFieldException, IllegalAccessException {
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListCompteComptable");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.compte_comptable\n" +
                "            ");
    }

    @Test
    public void setSQLgetListJournalComptable() throws IllegalAccessException, NoSuchFieldException {
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListJournalComptable");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.journal_comptable\n" +
                "            ");
    }
    @Test
    public void setSQLgetListEcritureComptable() throws IllegalAccessException, NoSuchFieldException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListEcritureComptable");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.ecriture_comptable\n" +
                "            ");
    }

    @Test
    public void setSQLgetEcritureComptable() throws IllegalAccessException, NoSuchFieldException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetEcritureComptable");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.ecriture_comptable\n" +
                "                WHERE id = :id\n" +
                "            ");
    }

    @Test
    public void setSQLgetEcritureComptableByRef() throws NoSuchFieldException, IllegalAccessException {
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetEcritureComptableByRef");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.ecriture_comptable\n" +
                "                WHERE reference = :reference\n" +
                "            ");
    }

    @Test
    public void setSQLloadListLigneEcriture() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLloadListLigneEcriture");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.ligne_ecriture_comptable\n" +
                "                WHERE ecriture_id = :ecriture_id\n" +
                "                ORDER BY ligne_id\n" +
                "            ");
    }

    @Test
    public void setSQLinsertEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertEcritureComptable");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                INSERT INTO myerp.ecriture_comptable (\n" +
                "                    id,\n" +
                "                    journal_code, reference, date, libelle\n" +
                "                ) VALUES (\n" +
                "                    nextval('myerp.ecriture_comptable_id_seq'),\n" +
                "                    :journal_code, :reference, :date, :libelle\n" +
                "                )\n" +
                "            ");
    }

    @Test
    public void setSQLinsertListLigneEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertListLigneEcritureComptable");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                INSERT INTO myerp.ligne_ecriture_comptable (\n" +
                "                    ecriture_id, ligne_id, compte_comptable_numero, libelle, debit\n" +
                "                    credit\n" +
                "                ) VALUES (\n" +
                "                    :ecriture_id, :ligne_id, :compte_comptable_numero, :libelle, :debit,\n" +
                "                    :credit\n" +
                "                )\n" +
                "            ");
    }

    @Test
    public void setSQLupdateEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLupdateEcritureComptable");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                UPDATE myerp.ecriture_comptable SET\n" +
                "                    journal_code = :journal_code,\n" +
                "                    reference = :reference,\n" +
                "                    date = :date,\n" +
                "                    libelle = :libelle\n" +
                "                WHERE\n" +
                "                    id = :id\n" +
                "            ");
    }

    @Test
    public void setSQLdeleteEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteEcritureComptable");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                DELETE FROM myerp.ecriture_comptable\n" +
                "                WHERE id = :id\n" +
                "            ");
    }

    @Test
    public void setSQLdeleteListLigneEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteListLigneEcritureComptable");
        field.setAccessible(true);
        Assert.assertEquals(field.get(sqlRequet),"\n" +
                "                DELETE FROM myerp.ligne_ecriture_comptable\n" +
                "                WHERE ecriture_id = :ecriture_id\n" +
                "            ");
    }

    @AfterClass
    public static void endTestClass(){
        LOGGER.info("Fin du test de la classe ComptabibliteDaoImpl");
    }
}
