package com.dummy.myerp.consumer.dao.impl.db;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ConsumerHelperTest extends ConsumerHelper {

    @Test
    public void getDaoProxyTest() {
        configure(mock(DaoProxy.class));
        DaoProxy dao = getDaoProxy();
        assertNotNull(dao);
    }
}
