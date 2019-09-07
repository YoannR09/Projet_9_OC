package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class LigneEcritureComptableTest {

    @Test
    public void getCompteComptable() {
        CompteComptable compteComptable = new CompteComptable();
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(compteComptable,null,null,null);
        ligneEcritureComptable.setCompteComptable(compteComptable);

        Assert.assertEquals(compteComptable,ligneEcritureComptable.getCompteComptable());
    }

    @Test
    public void setCompteComptable() {
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        CompteComptable compteComptable = new CompteComptable();
        ligneEcritureComptable.setCompteComptable(compteComptable);

        Assert.assertEquals(compteComptable,ligneEcritureComptable.getCompteComptable());
    }

    @Test
    public void getLibelle() {
        String libelle = "libelle";
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(null,libelle,null,null);

        Assert.assertEquals(libelle,ligneEcritureComptable.getLibelle());
    }

    @Test
    public void setLibelle() {
        String libelle = "libelle";
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        ligneEcritureComptable.setLibelle(libelle);

        Assert.assertEquals(libelle,ligneEcritureComptable.getLibelle());
    }

    @Test
    public void getDebit() {
        BigDecimal debit = new BigDecimal(1.00);
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(null,null,debit,null);

        Assert.assertEquals(debit,ligneEcritureComptable.getDebit());
    }

    @Test
    public void setDebit() {
        BigDecimal debit = new BigDecimal(1.00);
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        ligneEcritureComptable.setDebit(debit);

        Assert.assertEquals(debit,ligneEcritureComptable.getDebit());
    }

    @Test
    public void getCredit() {
        BigDecimal credit = new BigDecimal(1.00);
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(null,null,null,credit);

        Assert.assertEquals(credit,ligneEcritureComptable.getCredit());
    }

    @Test
    public void setCredit() {
        BigDecimal credit = new BigDecimal(1.00);
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        ligneEcritureComptable.setCredit(credit);

        Assert.assertEquals(credit,ligneEcritureComptable.getCredit());
    }
}
