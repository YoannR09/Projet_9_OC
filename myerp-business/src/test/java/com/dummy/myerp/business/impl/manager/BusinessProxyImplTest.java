package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.BusinessProxyImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class BusinessProxyImplTest extends BusinessProxyImpl{

    @Test
    public void getInstanceTest() {
        assertThrows(new UnsatisfiedLinkError("La classe BusinessProxyImpl n'a pas été initialisée.").getClass(), () -> getInstance());
    }

    @Test
    public void getComptabiliteManagerTest() {
        ComptabiliteManager manager = getComptabiliteManager();
        assertNotNull(manager);
    }
}
