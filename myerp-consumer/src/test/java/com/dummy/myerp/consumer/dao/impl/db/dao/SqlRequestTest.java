package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.CompteComptableRM;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;


public class SqlRequestTest extends AbstractDbConsumer {

    JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
    String sqlRequet = null;
    ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();

    @Test
    public void setSQLgetListCompteComptable() throws NoSuchFieldException, IllegalAccessException {
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListCompteComptable");
        field.setAccessible(true);
        CompteComptableRM compteComptableRM = new CompteComptableRM();
        vJdbcTemplate.query(field.get(sqlRequet).toString(),compteComptableRM);
    }

    @Test
    public void setSQLgetListJournalComptable() throws IllegalAccessException, NoSuchFieldException {
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListJournalComptable");
        field.setAccessible(true);
    }
    @Test
    public void setSQLgetListEcritureComptable() throws IllegalAccessException, NoSuchFieldException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListEcritureComptable");
        field.setAccessible(true);
    }

    @Test
    public void setSQLgetEcritureComptable() throws IllegalAccessException, NoSuchFieldException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetEcritureComptable");
        field.setAccessible(true);
    }

    @Test
    public void setSQLgetEcritureComptableByRef() throws NoSuchFieldException, IllegalAccessException {
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetEcritureComptableByRef");
        field.setAccessible(true);
    }

    @Test
    public void setSQLloadListLigneEcriture() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLloadListLigneEcriture");
        field.setAccessible(true);
    }

    @Test
    public void setSQLinsertEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertEcritureComptable");
        field.setAccessible(true);
    }

    @Test
    public void setSQLinsertListLigneEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertListLigneEcritureComptable");
        field.setAccessible(true);
    }

    @Test
    public void setSQLupdateEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLupdateEcritureComptable");
        field.setAccessible(true);
    }

    @Test
    public void setSQLdeleteEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteEcritureComptable");
        field.setAccessible(true);

    }

    @Test
    public void setSQLdeleteListLigneEcritureComptable() throws NoSuchFieldException, IllegalAccessException{
        final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteListLigneEcritureComptable");
        field.setAccessible(true);
    }
}
