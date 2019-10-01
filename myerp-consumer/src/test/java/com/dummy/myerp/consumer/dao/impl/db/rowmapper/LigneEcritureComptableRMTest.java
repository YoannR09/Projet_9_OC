package com.dummy.myerp.consumer.dao.impl.db.rowmapper;


import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.impl.db.fake.FakeComptabiblieDao;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.LigneEcritureComptableRM;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class LigneEcritureComptableRMTest {

    LigneEcritureComptableRM rm = new LigneEcritureComptableRM();

    @Test
    public void mapRow(){

        final LigneEcritureComptable result = new LigneEcritureComptable();
        ConsumerHelper.configure(() -> new FakeComptabiblieDao());
        final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

        Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(RowMapper.class)))
                .thenAnswer((Answer<LigneEcritureComptable>) invocation -> {

                    ResultSet rs = Mockito.mock(ResultSet.class);
                    String libelle = "libelle";
                    BigDecimal debit = new BigDecimal(110.00);
                    BigDecimal credit = new BigDecimal(110.00);
                    Integer compteNumero = new Integer(401);

                    when(rs.getString("libelle")).thenReturn(libelle);
                    when(rs.getBigDecimal("credit")).thenReturn(credit);
                    when(rs.getBigDecimal("debit")).thenReturn(debit);
                    when(rs.getObject("compte_comptable_numero",Integer.class)).thenReturn(compteNumero);

                    LigneEcritureComptable actual = rm.mapRow(rs, 0);

                    result.setCredit(actual.getCredit());
                    result.setDebit(actual.getDebit());
                    result.setLibelle(actual.getLibelle());
                    result.setCompteComptable(actual.getCompteComptable());

                    return result;
                });

        LigneEcritureComptable ligneEcritureComptable = template.queryForObject("SELECT * FROM ligne_ecriture_comptable WHERE id = -3",rm);

        assertEquals(ligneEcritureComptable.getLibelle(),"libelle");
        assertEquals(ligneEcritureComptable.getCredit(), new BigDecimal(110.00));
        assertEquals(ligneEcritureComptable.getDebit(), new BigDecimal(110.00));
    }

    @Test
    public void getCompteComptableNumero() throws SQLException {

        // GIVEN
        ConsumerHelper.configure(() -> new FakeComptabiblieDao());
        ResultSet rs = mock(ResultSet.class);
        when(rs.getObject(anyString(),eq(Integer.class))).thenReturn(new Integer(4568));

        // WHEN
        CompteComptable compteComptable = rm.getCompteComptableNumero(rs);

        // THEN
        assertNotNull(compteComptable);
    }
}
