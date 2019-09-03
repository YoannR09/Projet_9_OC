package com.dummy.myerp.consumer.dao.impl.db.rowmapper;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.CompteComptableRM;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


import java.sql.*;
import java.util.List;


public class CompteComptableRMTest extends AbstractDbConsumer {

    /*
    @Test
    public void mapRow(){

        String vSQL = " SELECT * FROM compte_comptable";
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        CompteComptableRM compteComptableRM = new CompteComptableRM();

        Assert.assertNotNull(compteComptableRM.mapRow(,1)vJdbcTemplate.query(vSQL, compteComptableRM));
    }
    */

    private final Connection connection = mock(Connection.class);
    private final Statement statement = mock(Statement.class);
    private final PreparedStatement preparedStatement = mock(PreparedStatement.class);
    private final ResultSet resultSet = mock(ResultSet.class);
    private final JdbcTemplate template = new JdbcTemplate();

    private final RowMapper<CompteComptable> testRowMapper =
            (rs, rowNum) -> new CompteComptable(Integer.parseInt(rs.getString("1")), rs.getString(2));

    private List<CompteComptable> result;



    @BeforeEach
    public void setUp() throws SQLException {

        given(connection.createStatement()).willReturn(statement);
        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(statement.executeQuery(anyString())).willReturn(resultSet);
        given(preparedStatement.executeQuery()).willReturn(resultSet);
        given(resultSet.next()).willReturn(true, true, false);
        when(resultSet.getString("numero")).thenReturn("353");

        template.setDataSource(new SingleConnectionDataSource(connection, false));
        template.setExceptionTranslator(new SQLStateSQLExceptionTranslator());

        template.afterPropertiesSet();
    }


    @AfterEach
    public void verifyClosed() throws Exception {
        verify(resultSet).close();
        // verify(connection).close();
    }


    @AfterEach
    public void verifyResults() {
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(),1);

        CompteComptable compteComptable = result.get(1);
        Assert.assertEquals(compteComptable.getNumero(),"353");
    }



    @Test

    public void staticQueryWithRowMapper() throws SQLException {

        result = template.query("some SQL", testRowMapper);

        verify(statement).close();
    }



    @Test
    public void preparedStatementCreatorWithRowMapper() throws SQLException {
        result = template.query(con -> preparedStatement, testRowMapper);
        verify(preparedStatement).close();
    }



    @Test
    public void preparedStatementSetterWithRowMapper() throws SQLException {
        result = template.query("some SQL", ps -> ps.setString(1, "test"), testRowMapper);
        verify(preparedStatement).setString(1, "test");
        verify(preparedStatement).close();
    }



    @Test
    public void queryWithArgsAndRowMapper() throws SQLException {
        result = template.query("some SQL", new Object[] { "test1", "test2" }, testRowMapper);
        preparedStatement.setString(1, "test1");
        preparedStatement.setString(2, "test2");
        preparedStatement.close();
    }



    @Test
    public void queryWithArgsAndTypesAndRowMapper() throws SQLException {
        result = template.query("some SQL",

                new Object[] { "test1", "test2" },

                new int[] { Types.VARCHAR, Types.VARCHAR },
                testRowMapper);
        verify(preparedStatement).setString(1, "test1");
        verify(preparedStatement).setString(2, "test2");
        verify(preparedStatement).close();
    }
}
