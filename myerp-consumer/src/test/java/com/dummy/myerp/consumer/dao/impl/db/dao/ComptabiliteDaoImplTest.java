package com.dummy.myerp.consumer.dao.impl.db.dao;


import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.dummy.myerp.consumer.dao.impl.db.builder.SequenceBuilder.aSequence;
import static com.dummy.myerp.testconsumer.consumer.builder.EcritureComptableBuilder.aEcritureComptable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ComptabiliteDaoImplTest {

    private ComptabiliteDaoImpl         comptabiliteDao;
    private JdbcTemplate                template;
    private NamedParameterJdbcTemplate  namedParameterJdbcTemplate;
    private AbstractDbConsumer          abstractDbConsumer;
    private String                      sqlRequet = null;

    @Test
    public void getInstance() {
        assertNotNull(comptabiliteDao.getInstance());
        comptabiliteDao = comptabiliteDao.getInstance();
        assertEquals(comptabiliteDao,comptabiliteDao.getInstance());
    }


    @Test
    public void getListCompteComptable() {

        // GIVEN
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

    @Test
    public void getListJournalComptable() {

        // GIVEN
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


    @Test
    public void getListEcritureComptable() {

        // GIVEN
        EcritureComptable vEcritureComptable1 = aEcritureComptable()
                .journal(new JournalComptable("AC", "Achat"))
                .libelle("Libelle")
                .ref("AC-2019/00001")
                .date(new Date()).build();
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        EcritureComptable vEcritureComptable2 = aEcritureComptable()
                .journal(new JournalComptable("RT", "Retrait"))
                .libelle("Libelle")
                .ref("RT-2019/00011")
                .date(new Date()).build();
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


    @Test
    public void getEcritureComptable() throws NotFoundException {

        // GIVEN
        EcritureComptable vEcritureComptable1 = aEcritureComptable()
                .journal(new JournalComptable("AC", "Achat"))
                .libelle("Libelle")
                .ref("AC-2019/00001")
                .date(new Date()).build();
        vEcritureComptable1.setId(55);
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
        reset(namedParameterJdbcTemplate);
        doThrow(new RuntimeException("EcritureComptable non trouvée : id= 12")).when(namedParameterJdbcTemplate).queryForObject(anyString(),any(MapSqlParameterSource.class),any(RowMapper.class));
        assertThrows(new RuntimeException("EcritureComptable non trouvée : id= 12").getClass(), () -> comptabiliteDao.getEcritureComptable(41));
    }

    @Test
    public void getEcritureComptableByRef() throws NotFoundException {

        // GIVEN
        EcritureComptable vEcritureComptable1 = aEcritureComptable()
                .journal(new JournalComptable("AC", "Achat"))
                .libelle("Libelle")
                .ref("AC-2019/00001")
                .date(new Date()).build();
        vEcritureComptable1.setId(55);
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

    @Test
    public void loadListLigneEcriture() {

        // GIVEN
        EcritureComptable vEcritureComptable1 = aEcritureComptable()
                .journal(new JournalComptable("AC", "Achat"))
                .libelle("Libelle")
                .ref("AC-2019/00001")
                .date(new Date()).build();
        vEcritureComptable1.setId(55);
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
        EcritureComptable vEcritureComptable1 = aEcritureComptable()
                .journal(new JournalComptable("AC", "Achat"))
                .libelle("Libelle")
                .ref("AC-2019/00001")
                .date(new Date()).build();
        vEcritureComptable1.setId(55);
        List<EcritureComptable> vList = new LinkedList<>();
        when(namedParameterJdbcTemplate.update(anyString(),any(MapSqlParameterSource.class))).then((Answer<Void>) invocationOnMock -> {
            vList.add(vEcritureComptable1);
            return null;
        });
        when(abstractDbConsumer.queryGetSequenceValuePostgreSQL(any(DataSourcesEnum.class),anyString(),anyObject(),anyObject())).thenReturn(12);


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
        EcritureComptable vEcritureComptable1 = aEcritureComptable()
                .journal(new JournalComptable("AC", "Achat"))
                .libelle("Libelle")
                .ref("AC-2019/00001")
                .date(new Date()).build();
        vEcritureComptable1.setId(55);
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
        EcritureComptable vEcritureComptable1 = aEcritureComptable()
                .journal(new JournalComptable("AC", "Achat"))
                .libelle("Libelle")
                .ref("AC-2019/00001")
                .date(new Date()).build();
        vEcritureComptable1.setId(55);

        // WHEN
        comptabiliteDao.updateEcritureComptable(vEcritureComptable1);
    }

    @Test
    public void deleteEcritureComptable() {

        // GIVEN
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
    public void deleteSequenceEcritureComptable(){

        // GIVEN
        SequenceEcritureComptable sequenceEcritureComptable = aSequence().annee(2019).derniereVal(88).journalCode("AC").build();
        List<SequenceEcritureComptable> fakeList = new ArrayList<>();
        fakeList.add(sequenceEcritureComptable);
        when(namedParameterJdbcTemplate.update(anyString(),any(MapSqlParameterSource.class))).then((Answer<Void>) invocationOnMock -> {
            fakeList.remove(0);
            return null;
        });

        // WHEN
        comptabiliteDao.deleteSequenceEcritureComptable(sequenceEcritureComptable);

        // THEN
        assertEquals(fakeList.size(),0);
    }


    @Test
    public void getSequenceViaCodeAnnee() throws NotFoundException {

        // GIVEN
        List<SequenceEcritureComptable> fakeList = new LinkedList<>();
        SequenceEcritureComptable sequenceEcritureComptable1 = aSequence().derniereVal(55).annee(2019).journalCode("FE").build();

        SequenceEcritureComptable sequenceEcritureComptable2 = aSequence().derniereVal(13).annee(2019).journalCode("AC").build();
        fakeList.add(sequenceEcritureComptable1);
        fakeList.add(sequenceEcritureComptable2);
        when(namedParameterJdbcTemplate.queryForObject(anyString(),any(MapSqlParameterSource.class),any(RowMapper.class))).thenReturn(sequenceEcritureComptable2);
        SequenceEcritureComptable sequence = new SequenceEcritureComptable(new Integer(2019),new Integer(55));
        sequence.setJournalCode("AC");

        // WHEN
        SequenceEcritureComptable sequenceEcritureComptable = comptabiliteDao.getSequenceViaCodeAnnee(sequence);

        // THEN
        assertEquals(sequenceEcritureComptable.getAnnee(),new Integer(2019));
        assertEquals(sequenceEcritureComptable.getDerniereValeur(),new Integer(13));
        assertEquals(sequenceEcritureComptable.getJournalCode(),"AC");
    }

    @Test
    public void insertSequenceEcritureComptable() {
        Integer oldDerniereValeur = new Integer(55);
        SequenceEcritureComptable sequenceEcritureComptable1 = aSequence().derniereVal(55).annee(2019).journalCode("FE").build();
        when(namedParameterJdbcTemplate.update(anyString(),any(MapSqlParameterSource.class))).then((Answer<Void>) invocationOnMock -> {
           sequenceEcritureComptable1.setDerniereValeur(new Integer(56));
            return null;
        });

        // WHEN
        comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable1);

        // THEN
        assertEquals(sequenceEcritureComptable1.getDerniereValeur(),oldDerniereValeur+1);
    }

    @Test
    public void setSQLgetListCompteComptable() throws NoSuchFieldException, IllegalAccessException {
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListCompteComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLgetListCompteComptable");
    }

    @Test
    public void setSQLgetListJournalComptable() throws IllegalAccessException, NoSuchFieldException {
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListJournalComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLgetListJournalComptable");
    }
    @Test
    public void setSQLgetListEcritureComptable() throws IllegalAccessException, NoSuchFieldException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLgetListEcritureComptable");
    }

    @Test
    public void setSQLgetEcritureComptable() throws IllegalAccessException, NoSuchFieldException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLgetEcritureComptable");
    }

    @Test
    public void setSQLgetEcritureComptableByRef() throws NoSuchFieldException, IllegalAccessException {
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetEcritureComptableByRef");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLgetEcritureComptableByRef");
    }

    @Test
    public void setSQLloadListLigneEcriture() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLloadListLigneEcriture");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLloadListLigneEcriture");
    }

    @Test
    public void setSQLinsertEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLinsertEcritureComptable");
    }

    @Test
    public void setSQLinsertListLigneEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertListLigneEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLinsertListLigneEcritureComptable");
    }

    @Test
    public void setSQLupdateEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLupdateEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLupdateEcritureComptable");
    }

    @Test
    public void setSQLdeleteEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLdeleteEcritureComptable");
    }

    @Test
    public void setSQLdeleteListLigneEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteListLigneEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLdeleteListLigneEcritureComptable");
    }

    @Test
    public void setSQLgetSequenceViaCodeAnnee() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetSequenceViaCodeAnnee");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLgetSequenceViaCodeAnnee");
    }

    @Test
    public void setSQLdeleteSequenceEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteSequenceEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLdeleteSequenceEcritureComptable");
    }

    @Test
    public void setSQLinsertSequenceEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        comptabiliteDao = new ComptabiliteDaoImpl();
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertSequenceEcritureComptable");
        field.setAccessible(true);
        assertEquals(field.get(sqlRequet),"SQLinsertSequenceEcritureComptable");
    }

    @BeforeEach
    public void init(){
        template = mock(JdbcTemplate.class);
        namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        abstractDbConsumer = mock(AbstractDbConsumer.class);
        comptabiliteDao = new ComptabiliteDaoImpl(template,namedParameterJdbcTemplate,abstractDbConsumer);

        // Remplace le travail du fichier sqlContext.xml
        comptabiliteDao.setSQLgetSequenceViaCodeAnnee("SQLgetSequenceViaCodeAnnee");
        comptabiliteDao.setSQLgetListCompteComptable("SQLgetListCompteComptable");
        comptabiliteDao.setSQLdeleteEcritureComptable("SQLdeleteEcritureComptable");
        comptabiliteDao.setSQLgetEcritureComptable("SQLgetEcritureComptable");
        comptabiliteDao.setSQLgetListCompteComptable("SQLgetListCompteComptable");
        comptabiliteDao.setSQLdeleteListLigneEcritureComptable("SQLdeleteListLigneEcritureComptable");
        comptabiliteDao.setSQLgetListJournalComptable("SQLgetListJournalComptable");
        comptabiliteDao.setSQLgetEcritureComptableByRef("SQLgetEcritureComptableByRef");
        comptabiliteDao.setSQLinsertSequenceEcritureComptable("SQLinsertSequenceEcritureComptable");
        comptabiliteDao.setSQLdeleteSequenceEcritureComptable("SQLdeleteSequenceEcritureComptable");
        comptabiliteDao.setSQLloadListLigneEcriture("SQLloadListLigneEcriture");
        comptabiliteDao.setSQLgetListEcritureComptable("SQLgetListEcritureComptable");
        comptabiliteDao.setSQLupdateEcritureComptable("SQLupdateEcritureComptable");
        comptabiliteDao.setSQLinsertListLigneEcritureComptable("SQLinsertListLigneEcritureComptable");
        comptabiliteDao.setSQLinsertEcritureComptable("SQLinsertEcritureComptable");
    }
}
