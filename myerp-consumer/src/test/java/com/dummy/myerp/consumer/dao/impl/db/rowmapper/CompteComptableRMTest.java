package com.dummy.myerp.consumer.dao.impl.db.rowmapper;


import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.CompteComptableRM;


import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import org.mockito.Mockito;

import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;




public class CompteComptableRMTest{

    @Test
    public void mapRow(){

        CompteComptableRM rm = new CompteComptableRM();

        final CompteComptable result = new CompteComptable();

        final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

        Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(RowMapper.class)))
                .thenAnswer((Answer<CompteComptable>) invocation -> {

                    ResultSet rs = Mockito.mock(ResultSet.class);
                    String libelle = "libelle";
                    Integer numero = new Integer(25446);
                    Mockito.when(rs.getString("libelle")).thenReturn(libelle);
                    Mockito.when(rs.getInt("numero")).thenReturn(numero);

                    CompteComptable actual = rm.mapRow(rs, 0);

                    result.setNumero(actual.getNumero());
                    result.setLibelle(actual.getLibelle());

                    return result;
                });

        CompteComptable compteComptable = template.queryForObject("SELECT * FROM compte_comptable WHERE id = -3",rm);

        assertEquals(compteComptable.getNumero(),new Integer(25446));
        assertEquals(compteComptable.getLibelle(),"libelle");
    }
}
