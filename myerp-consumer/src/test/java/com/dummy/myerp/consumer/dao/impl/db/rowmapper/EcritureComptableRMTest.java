package com.dummy.myerp.consumer.dao.impl.db.rowmapper;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.fake.FakeComptabiblieDao;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.EcritureComptableRM;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class EcritureComptableRMTest {

    EcritureComptableRM rm = new EcritureComptableRM();

    @Test
    public void mapRow(){

        final EcritureComptable result = new EcritureComptable();
        ConsumerHelper.configure(() -> new FakeComptabiblieDao());
        final JdbcTemplate template = mock(JdbcTemplate.class);

        Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(RowMapper.class)))
                .thenAnswer((Answer<EcritureComptable>) invocation -> {

                    ResultSet rs = mock(ResultSet.class);

                    Integer id = new Integer(4);
                    String libelle = "libelle";
                    String reference = "BQ-2019/00001";
                    JournalComptable journal = new JournalComptable("AC", "Achat");

                    Mockito.when(rs.getString("libelle")).thenReturn(libelle);
                    Mockito.when(rs.getString("reference")).thenReturn(reference);
                    Mockito.when(rs.getString("journal_code")).thenReturn(journal.getCode());
                    Mockito.when(rs.getInt("id")).thenReturn(id);

                    EcritureComptable actual = rm.mapRow(rs, 0);

                    result.setId(actual.getId());
                    result.setReference(actual.getReference());
                    result.setLibelle(actual.getLibelle());
                    result.setDate(actual.getDate());
                    result.setJournal(actual.getJournal());

                    return result;
                });

        EcritureComptable ecritureComptable = template.queryForObject("SELECT * FROM ecriture_comptable WHERE id = 4",rm);

        assertEquals(ecritureComptable.getLibelle(),"libelle");
        assertEquals(ecritureComptable.getId(),new Integer(4));
        assertEquals(ecritureComptable.getReference(),"BQ-2019/00001");
        assertEquals(ecritureComptable.getJournal().toString(),new JournalComptable("AC", "Achat").toString());
    }

    @Test
    public void getJournalCode() throws SQLException {
        ConsumerHelper.configure(() -> new FakeComptabiblieDao());
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString(anyString())).thenReturn("AC");
        JournalComptable journalComptable = rm.getJournalCode(rs);

        // THEN
        assertNotNull(journalComptable);
    }

    @Test
    public void loadListLigneEcriture() {
        EcritureComptable ecritureComptable = new EcritureComptable();
        rm.loadListLigneEcriture(ecritureComptable);
        assertEquals(2,ecritureComptable.getListLigneEcriture().size());
    }
}
