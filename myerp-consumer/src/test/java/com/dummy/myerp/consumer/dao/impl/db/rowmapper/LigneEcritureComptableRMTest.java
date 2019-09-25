package com.dummy.myerp.consumer.dao.impl.db.rowmapper;


import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.LigneEcritureComptableRM;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LigneEcritureComptableRMTest {

    @Test
    public void mapRow(){

        LigneEcritureComptableRM rm = new LigneEcritureComptableRMFake();

        final LigneEcritureComptable result = new LigneEcritureComptable();

        final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

        Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(RowMapper.class)))
                .thenAnswer((Answer<LigneEcritureComptable>) invocation -> {

                    ResultSet rs = Mockito.mock(ResultSet.class);
                    String libelle = "libelle";
                    BigDecimal debit = new BigDecimal(110.00);
                    BigDecimal credit = new BigDecimal(110.00);
                    Integer compteNumero = new Integer(401);

                    Mockito.when(rs.getString("libelle")).thenReturn(libelle);
                    Mockito.when(rs.getBigDecimal("credit")).thenReturn(credit);
                    Mockito.when(rs.getBigDecimal("debit")).thenReturn(debit);
                    Mockito.when(rs.getObject("compte_comptable_numero",Integer.class)).thenReturn(compteNumero);

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
        assertEquals(ligneEcritureComptable.getCompteComptable().getNumero(),new Integer(401));
    }

    private class LigneEcritureComptableRMFake extends LigneEcritureComptableRM {
        @Override
        protected CompteComptable getCompteComptableNumero(ResultSet pRS) {
            return new CompteComptable(401,"Libelle");
        }
    }
}
