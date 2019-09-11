package com.dummy.myerp.consumer.dao.impl.db.dao;


import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import com.dummy.myerp.testconsumer.consumer.SpringRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ComptabiliteDaoImplTest extends ConsumerTestCase {

    private static final Logger LOGGER = LogManager.getLogger(SpringRegistry.class);
    private ComptabiliteDaoImpl comptabiliteDao;
    private String sqlRequet = null;

    EcritureComptable ecritureComptable;

    @Test
    public void  getInstance() {
        assertNotNull(comptabiliteDao.getInstance());
        comptabiliteDao = comptabiliteDao.getInstance();
        assertEquals(comptabiliteDao,comptabiliteDao.getInstance());
    }

    @Test
    public void consumerTestCase(){
        
    }

    /**
     * Test de la méthode pour récupérer une liste de compteComptable
     */
    @Test
    public void getListCompteComptable() {

        // GIVEN
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        CompteComptable compteComptable1 = new CompteComptable(2332,"compte1");
        CompteComptable compteComptable2 = new CompteComptable(4568,"compte2");
        LinkedList<CompteComptable> fakeList = new LinkedList<>();
        fakeList.add(compteComptable1);
        fakeList.add(compteComptable2);
        when(template.query(anyString(),any(RowMapper.class))).thenReturn(fakeList);

        //  WHEN
        List<CompteComptable> vList = comptabiliteDao.getListCompteComptable();

        // THEN
        assertNotNull(vList);
        assertEquals(vList.get(0).getNumero(),new Integer(2332));
        assertEquals(vList.get(1).getNumero(),new Integer(4568));
        assertEquals(vList.get(0).getLibelle(),"compte1");
        assertEquals(vList.get(1).getLibelle(),"compte2");
        assertEquals(vList.size(),2);
    }

    /**
     * Test de la méthode pour récupérer une liste de journalComptable
     */
    @Test
    public void getListJournalComptable() {

        // GIVEN
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        JournalComptable journalComptable1 = new JournalComptable("AC","Achat");
        JournalComptable journalComptable2 = new JournalComptable("RT","Retrait");
        LinkedList<JournalComptable> fakeList = new LinkedList<>();
        fakeList.add(journalComptable1);
        fakeList.add(journalComptable2);
        when(template.query(anyString(),any(RowMapper.class))).thenReturn(fakeList);

        // WHEN
        List<JournalComptable> vList = comptabiliteDao.getListJournalComptable();

        // THEN
        assertNotNull(vList);
        assertEquals(vList.get(0).getCode(),"AC");
        assertEquals(vList.get(1).getCode(),"RT");
        assertEquals(vList.get(0).getLibelle(),"Achat");
        assertEquals(vList.get(1).getLibelle(),"Retrait");
        assertEquals(vList.size(),2);
    }

    /**
     * Test de la méthode pour récupérer une liste d'écritureComptable
     */
    @Test
    public void getListEcritureComptable() {

        // GIVEN
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        EcritureComptable vEcritureComptable2 = new EcritureComptable();
        vEcritureComptable2.setJournal(new JournalComptable("RT", "Retrait"));
        vEcritureComptable2.setDate(new Date());
        vEcritureComptable2.setLibelle("Libelle");
        vEcritureComptable2.setReference("RT-2019/00011");
        vEcritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        LinkedList<EcritureComptable> fakeList = new LinkedList<>();
        fakeList.add(vEcritureComptable1);
        fakeList.add(vEcritureComptable2);
        when(template.query(anyString(),any(RowMapper.class))).thenReturn(fakeList);

        // WHEN
        List<EcritureComptable> vList = comptabiliteDao.getListEcritureComptable();

        // THEN
        assertNotNull(vList);
        assertEquals(vList.get(0).getLibelle(),"Libelle");
        assertEquals(vList.get(0).getReference(),"AC-2019/00001");
        assertEquals(vList.get(0).getJournal().getCode(),"AC");
        assertEquals(vList.get(0).getListLigneEcriture().size(),2);
        assertEquals(vList.get(0).getTotalCredit(),new BigDecimal(123));
        assertEquals(vList.get(0).getTotalDebit(),new BigDecimal(123));
        assertEquals(vList.get(1).getLibelle(),"Libelle");
        assertEquals(vList.get(1).getReference(),"RT-2019/00011");
        assertEquals(vList.get(1).getJournal().getCode(),"RT");
        assertEquals(vList.get(1).getListLigneEcriture().size(),2);
        assertEquals(vList.get(1).getTotalCredit(),new BigDecimal(123));
        assertEquals(vList.get(1).getTotalDebit(),new BigDecimal(123));
        assertEquals(vList.size(),2);
    }

    /**
     * Test de la méthode pour récupérer une ecritureComptable via un id
     */
    @Test
    public void getEcritureComptable() throws NotFoundException {

        // GIVEN
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setId(55);
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        when(namedParameterJdbcTemplate.queryForObject(anyString(),any(MapSqlParameterSource.class),any(RowMapper.class))).thenReturn(vEcritureComptable1);

        // WHEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(12);

        // THEN
        assertNotNull(ecritureComptable);
        assertEquals(ecritureComptable.getLibelle(),"Libelle");
        assertEquals(ecritureComptable.getReference(),"AC-2019/00001");
        assertEquals(ecritureComptable.getJournal().getCode(),"AC");
        assertEquals(ecritureComptable.getListLigneEcriture().size(),2);
        assertEquals(ecritureComptable.getTotalCredit(),new BigDecimal(123));
        assertEquals(ecritureComptable.getTotalDebit(),new BigDecimal(123));

        // THROW
        doThrow(new RuntimeException("EcritureComptable non trouvée : id= 12")).when(namedParameterJdbcTemplate).queryForObject(anyString(),any(MapSqlParameterSource.class),any(RowMapper.class));
        assertThrows(new RuntimeException("EcritureComptable non trouvée : id= 12").getClass(), () -> comptabiliteDao.getEcritureComptable(41));
    }

    /**
     * Test de la méthode pour récupérer une ecritureComptable via une réference
     */
    @Test
    public void getEcritureComptableByRef() throws NotFoundException {
        // GIVEN
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setId(55);
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        when(namedParameterJdbcTemplate.queryForObject(anyString(),any(MapSqlParameterSource.class),any(RowMapper.class))).thenReturn(vEcritureComptable1);

        // WHEN
        EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef("AC-2019/00001");

        // THEN
        assertNotNull(ecritureComptable);
        assertEquals(ecritureComptable.getLibelle(),"Libelle");
        assertEquals(ecritureComptable.getReference(),"AC-2019/00001");
        assertEquals(ecritureComptable.getJournal().getCode(),"AC");
        assertEquals(ecritureComptable.getListLigneEcriture().size(),2);
        assertEquals(ecritureComptable.getTotalCredit(),new BigDecimal(123));
        assertEquals(ecritureComptable.getTotalDebit(),new BigDecimal(123));

        // THROW
        reset(namedParameterJdbcTemplate);
        doThrow(new RuntimeException("EcritureComptable non trouvée : reference=" + "AC-2019/00001")).when(namedParameterJdbcTemplate).queryForObject(anyString(),any(MapSqlParameterSource.class),any(RowMapper.class));
        assertThrows(new RuntimeException("EcritureComptable non trouvée : reference=" + "AC-2019/00001").getClass(), () -> comptabiliteDao.getEcritureComptableByRef("AC-2019/00001"));
    }

    /**
     * Test de la méthode pour initaliser la liste des ligneEcriture de l'ecritureComptable
     */
    @Test
    public void loadListLigneEcriture() {

        // GIVEN
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setId(55);
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
        List<LigneEcritureComptable> fakeList = new LinkedList<>();
        fakeList.add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        fakeList.add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        when(namedParameterJdbcTemplate.query(anyString(),any(MapSqlParameterSource.class),any(RowMapper.class))).thenReturn(fakeList);

        // WHEN
        comptabiliteDao.loadListLigneEcriture(vEcritureComptable1);

        // THEN
        assertNotNull(vEcritureComptable1.getListLigneEcriture());
        assertEquals(vEcritureComptable1.getListLigneEcriture().size(),2);
        assertEquals(vEcritureComptable1.getTotalDebit(),new BigDecimal(123));
        assertEquals(vEcritureComptable1.getTotalCredit(),new BigDecimal(123));
    }

    @Test
    public void insertEcritureComptable() {

        // GIVEN
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setId(55);
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
        List<EcritureComptable> vList = new LinkedList<>();
        when(namedParameterJdbcTemplate.update(anyString(),any(MapSqlParameterSource.class))).then((Answer<Void>) invocationOnMock -> {
            vList.add(vEcritureComptable1);
            return null;
        });
        when(abstractDbConsumer.queryGetSequenceValuePostgreSQL(any(DataSourcesEnum.class),anyString(),anyObject())).thenReturn(12);


        // WHEN
        comptabiliteDao.insertEcritureComptable(vEcritureComptable1);

        // THEN
        assertNotNull(vList);
        assertNotNull(vEcritureComptable1.getId());
        assertEquals(vEcritureComptable1.getId(),12);
        assertEquals(vList.get(0),vEcritureComptable1);
    }

    @Test
    public void insertListLigneEcritureComptable() {

        // GIVEN
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setId(55);
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        final List<LigneEcritureComptable>[] vList = new List[]{new LinkedList<>()};
        when(namedParameterJdbcTemplate.update(anyString(),any(MapSqlParameterSource.class))).then((Answer<Void>) invocationOnMock -> {
            vList[0] = vEcritureComptable1.getListLigneEcriture();
            return null;
        });

        // WHEN
        comptabiliteDao.insertListLigneEcritureComptable(vEcritureComptable1);

        // THEN
        assertEquals(vEcritureComptable1.getListLigneEcriture().get(0).toString(),vList[0].get(0).toString());
        assertEquals(vEcritureComptable1.getListLigneEcriture().get(1).toString(),vList[0].get(1).toString());
    }

    @Test
    public void updateEcritureComptable() {

        // GIVEN
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setId(55);
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
    }

    @Test
    public void deleteEcritureComptable() {
        JdbcTemplate template = mock(JdbcTemplate.class);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        EcritureComptable vEcritureComptable2 = new EcritureComptable();
        List<EcritureComptable> fakeDbList = new LinkedList<>();
        fakeDbList.add(vEcritureComptable1);
        fakeDbList.add(vEcritureComptable2);
        List<LigneEcritureComptable> fakeList = new LinkedList<>();
        fakeList.add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        fakeList.add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        when(namedParameterJdbcTemplate.update(anyString(),any(MapSqlParameterSource.class))).then((Answer<Void>) invocationOnMock -> {
            if (fakeDbList.size() > 1){
                fakeDbList.remove(1);
            }
            if(!fakeList.isEmpty()){
                fakeList.remove(1);
                fakeList.remove(0);
            }
            return null;
        });

        // WHEN
        comptabiliteDao.deleteEcritureComptable(12);

        // THEN
        assertEquals(fakeList.size(),0);
        assertEquals(fakeDbList.size(),1);
    }

   @Test
    public void deleteListLigneEcritureComptable() {
       // GIVEN
       JdbcTemplate template = mock(JdbcTemplate.class);
       NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
       AbstractDbConsumer abstractDbConsumer = mock(AbstractDbConsumer.class);
       comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);
       List<LigneEcritureComptable> fakeList = new LinkedList<>();
       fakeList.add(new LigneEcritureComptable(new CompteComptable(1),
               null, new BigDecimal(123),
               null));
       fakeList.add(new LigneEcritureComptable(new CompteComptable(2),
               null, null,
               new BigDecimal(123)));
       when(namedParameterJdbcTemplate.update(anyString(),any(MapSqlParameterSource.class))).then((Answer<Void>) invocationOnMock -> {
           fakeList.remove(1);
           fakeList.remove(0);
           return null;
       });

       // WHEN
       comptabiliteDao.deleteListLigneEcritureComptable(12);

       // THEN
       assertEquals(fakeList.size(),0);
    }

    @Test
    public void setSQLgetListCompteComptable() throws NoSuchFieldException, IllegalAccessException {
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListCompteComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.compte_comptable\n" +
                "            ");
    }

    @Test
    public void setSQLgetListJournalComptable() throws IllegalAccessException, NoSuchFieldException {
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListJournalComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.journal_comptable\n" +
                "            ");
    }
    @Test
    public void setSQLgetListEcritureComptable() throws IllegalAccessException, NoSuchFieldException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.ecriture_comptable\n" +
                "            ");
    }

    @Test
    public void setSQLgetEcritureComptable() throws IllegalAccessException, NoSuchFieldException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.ecriture_comptable\n" +
                "                WHERE id = :id\n" +
                "            ");
    }

    @Test
    public void setSQLgetEcritureComptableByRef() throws NoSuchFieldException, IllegalAccessException {
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetEcritureComptableByRef");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.ecriture_comptable\n" +
                "                WHERE reference = :reference\n" +
                "            ");
    }

    @Test
    public void setSQLloadListLigneEcriture() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLloadListLigneEcriture");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
                "                SELECT * FROM myerp.ligne_ecriture_comptable\n" +
                "                WHERE ecriture_id = :ecriture_id\n" +
                "                ORDER BY ligne_id\n" +
                "            ");
    }

    @Test
    public void setSQLinsertEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
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
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertListLigneEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
                "                INSERT INTO myerp.ligne_ecriture_comptable (\n" +
                "                    ecriture_id, ligne_id, compte_comptable_numero, libelle, debit,\n" +
                "                    credit\n" +
                "                ) VALUES (\n" +
                "                    :ecriture_id, :ligne_id, :compte_comptable_numero, :libelle, :debit,\n" +
                "                    :credit\n" +
                "                )\n" +
                "            ");
    }

    @Test
    public void setSQLupdateEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLupdateEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
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
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
                "                DELETE FROM myerp.ecriture_comptable\n" +
                "                WHERE id = :id\n" +
                "            ");
    }

    @Test
    public void setSQLdeleteListLigneEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteListLigneEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"\n" +
                "                DELETE FROM myerp.ligne_ecriture_comptable\n" +
                "                WHERE ecriture_id = :ecriture_id\n" +
                "            ");
    }
}
