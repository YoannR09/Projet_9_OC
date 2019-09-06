package com.dummy.myerp.model.bean.comptabilite;


import java.math.BigDecimal;

import java.util.Date;


import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


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

        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());
    }

    @Test
    public void getTotalDebit() {

        Assert.assertEquals(new BigDecimal("341"),vEcriture.getTotalDebit());
    }

    @Test
    public void getTotalCredit() {

        Assert.assertEquals(new BigDecimal("341"),vEcriture.getTotalCredit());
    }

    @Test
    public void getId() {

        Assert.assertTrue(1 == vEcriture.getId());
    }

    @Test
    public void setId() {

        Assert.assertNotNull(vEcriture.getId());
    }

    @Test
    public void getJournal() {

        Assert.assertEquals(journal,vEcriture.getJournal());
    }

    @Test
    public void setJournal() {

        Assert.assertNotNull(vEcriture.getJournal());
    }

    @Test
    public void getReference() {

        Assert.assertEquals("reference",vEcriture.getReference());
    }

    @Test
    public void setReference() {

        Assert.assertNotNull(vEcriture.getReference());
    }

    @Test
    public void getDate() {

        Assert.assertEquals(date,vEcriture.getDate());
    }

    @Test
    public void setDate() {

        Assert.assertNotNull(vEcriture.getDate());
    }

    @Test
    public void getLibelle() {

        Assert.assertEquals("Equilibrée",vEcriture.getLibelle());
    }

    @Test
    public void setLibelle() {

        Assert.assertNotNull(vEcriture.getLibelle());
    }

    @Test
    public void getListLigneEcriture() {

        Assert.assertNotNull(vEcriture.getListLigneEcriture());
    }

    @Before
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
