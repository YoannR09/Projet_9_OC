package com.dummy.myerp.model.bean.comptabilite;


import java.math.BigDecimal;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.ObjectUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class EcritureComptableTest {

    EcritureComptable vEcriture = new EcritureComptable();

    Date date = new Date();
    JournalComptable journal = new JournalComptable("code","libelle");

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void isEquilibree() {

        assertTrue(vEcriture.isEquilibree(), vEcriture.toString());
    }

    @Test
    public void getTotalDebit() {

        assertEquals(new BigDecimal("341"),vEcriture.getTotalDebit());
    }

    @Test
    public void getTotalCredit() {

        assertEquals(new BigDecimal("341"),vEcriture.getTotalCredit());
    }

    @Test
    public void getId() {

        assertTrue(1 == vEcriture.getId());
    }

    @Test
    public void setId() {

        assertNotNull(vEcriture.getId());
    }

    @Test
    public void getJournal() {

        assertEquals(journal,vEcriture.getJournal());
    }

    @Test
    public void setJournal() {

        assertNotNull(vEcriture.getJournal());
    }

    @Test
    public void getReference() {

        assertEquals("reference",vEcriture.getReference());
    }

    @Test
    public void setReference() {

        assertNotNull(vEcriture.getReference());
    }

    @Test
    public void getDate() {

        assertEquals(date,vEcriture.getDate());
    }

    @Test
    public void setDate() {

        assertNotNull(vEcriture.getDate());
    }

    @Test
    public void getLibelle() {

        assertEquals("Equilibrée",vEcriture.getLibelle());
    }

    @Test
    public void setLibelle() {

        assertNotNull(vEcriture.getLibelle());
    }

    @Test
    public void getListLigneEcriture() {

        assertNotNull(vEcriture.getListLigneEcriture());
    }

    @BeforeEach
    public void initEcritureComptable(){
        vEcriture.setId(new Integer(1));
        vEcriture.setDate(date);
        vEcriture.setJournal(journal);
        vEcriture.setReference("reference");
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "101", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
    }

}
