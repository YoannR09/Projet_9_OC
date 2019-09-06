package com.dummy.myerp.consumer.dao.impl.db.rowmapper;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.CompteComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.SequenceEcritureComptableRM;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class SequenceEcritureComptableRMTest {

    @Test
    public void mapRow(){

        SequenceEcritureComptableRM rm = new SequenceEcritureComptableRM();

        final SequenceEcritureComptable result = new SequenceEcritureComptable();

        final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

        Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(RowMapper.class)))
                .thenAnswer((Answer<SequenceEcritureComptable>) invocation -> {

                    ResultSet rs = Mockito.mock(ResultSet.class);
                    Integer annee = new Integer(2019);
                    String journalCode = "VE";
                    Integer derniereValeur = new Integer(25);
                    Mockito.when(rs.getInt("annee")).thenReturn(annee);
                    Mockito.when(rs.getString("journal_code")).thenReturn(journalCode);
                    Mockito.when(rs.getInt("derniere_valeur")).thenReturn(derniereValeur);

                    SequenceEcritureComptable actual = rm.mapRow(rs, 0);

                    result.setJournalCode(actual.getJournalCode());
                    result.setAnnee(actual.getAnnee());
                    result.setDerniereValeur(actual.getDerniereValeur());

                    return result;
                });

        SequenceEcritureComptable sequenceEcritureComptable = template.queryForObject("SELECT * FROM sequence_ecriture_comptable WHERE journal_code = 401",rm);

        Assert.assertEquals(sequenceEcritureComptable.getAnnee(),new Integer(2019));
        Assert.assertEquals(sequenceEcritureComptable.getJournalCode(),"VE");
        Assert.assertEquals(sequenceEcritureComptable.getDerniereValeur(),new Integer(25));
    }

}
