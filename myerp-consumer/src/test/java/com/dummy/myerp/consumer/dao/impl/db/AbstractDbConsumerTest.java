package com.dummy.myerp.consumer.dao.impl.db;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.fake.FakeDataSource;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractDbConsumerTest extends AbstractDbConsumer {


    @Test
    public void getDaoProxyTest() {

        // GIVEN
        ConsumerHelper.configure(mock(DaoProxy.class));

        // WHEN
        DaoProxy dao = getDaoProxy();

        // THEN
        assertNotNull(dao);
    }

    @Test
    public void getDataSourceTest() {

        // GIVEN
        Map<DataSourcesEnum, DataSource> mapDataSource = mock(Map.class);
        when(mapDataSource.get(anyObject())).thenReturn(new FakeDataSource());
        configure(mapDataSource);

        // WHEN
        DataSource data = getDataSource(DataSourcesEnum.MYERP);

        // THEN
        assertNotNull(data);
        assertThrows(new UnsatisfiedLinkError().getClass(),() -> getDataSource(null));
    }
}
