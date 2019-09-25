package com.dummy.myerp.consumer.dao.impl.db.helper;

import com.dummy.myerp.consumer.db.helper.ResultSetHelper;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ResultSetHelperTest extends ResultSetHelper{

    @Test
    public void getInteger() throws SQLException {

        // GIVEN
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt(anyString())).thenReturn(5);

        // WHEN
        Integer intTest = getInteger(rs,"numero");

        // THEN
        assertEquals(intTest,5);
    }

    @Test
    public void getLong() throws SQLException {
        // GIVEN
        ResultSet rs = mock(ResultSet.class);
        Long longReturn = new Long(445);
        when(rs.getLong(anyString())).thenReturn(longReturn);

        // WHEN
        Long longTest = getLong(rs,"credit");

        // THEN
        assertEquals(longTest,longReturn);
    }

    @Test
    public void getDate() throws SQLException {

        // GIVEN
        ResultSet rs = mock(ResultSet.class);
        Date date = new java.sql.Date(2019);
        doReturn(date).when(rs).getDate(anyString());
        date = DateUtils.truncate(date, Calendar.DATE);

        // WHEN
        Date dateTest = getDate(rs,"date");

        // THEN
        assertEquals(date,dateTest);
    }

}
