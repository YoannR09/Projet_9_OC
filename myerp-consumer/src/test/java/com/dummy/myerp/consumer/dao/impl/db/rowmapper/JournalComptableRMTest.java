package com.dummy.myerp.consumer.dao.impl.db.rowmapper;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.CompteComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.JournalComptableRM;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class JournalComptableRMTest {

    @Test
    public void mapRow(){

        JournalComptableRM rm = new JournalComptableRM();

        final JournalComptable result = new JournalComptable();

        final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

        Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(RowMapper.class)))
                .thenAnswer((Answer<JournalComptable>) invocation -> {

                    ResultSet rs = Mockito.mock(ResultSet.class);
                    String libelle = "libelle";
                    String code = "AC";
                    Mockito.when(rs.getString("libelle")).thenReturn(libelle);
                    Mockito.when(rs.getString("code")).thenReturn(code);

                    JournalComptable actual = rm.mapRow(rs, 0);

                    result.setLibelle(actual.getLibelle());
                    result.setCode(actual.getCode());

                    return result;
                });

        JournalComptable journalComptable = template.queryForObject("SELECT * FROM compte_comptable WHERE id = -3",rm);

        Assert.assertEquals(journalComptable.getCode(),"AC");
        Assert.assertEquals(journalComptable.getLibelle(),"libelle");
    }

}
