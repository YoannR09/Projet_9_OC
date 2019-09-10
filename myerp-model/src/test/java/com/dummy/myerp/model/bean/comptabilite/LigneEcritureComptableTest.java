package com.dummy.myerp.model.bean.comptabilite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class LigneEcritureComptableTest {

    @Test
    public void getCompteComptable() {
        CompteComptable compteComptable = new CompteComptable();
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(compteComptable,null,null,null);
        ligneEcritureComptable.setCompteComptable(compteComptable);

        assertEquals(compteComptable,ligneEcritureComptable.getCompteComptable());
    }

    @Test
    public void setCompteComptable() {
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        CompteComptable compteComptable = new CompteComptable();
        ligneEcritureComptable.setCompteComptable(compteComptable);

        assertEquals(compteComptable,ligneEcritureComptable.getCompteComptable());
    }

    @Test
    public void getLibelle() {
        String libelle = "libelle";
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(null,libelle,null,null);

        assertEquals(libelle,ligneEcritureComptable.getLibelle());
    }

    @Test
    public void setLibelle() {
        String libelle = "libelle";
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        ligneEcritureComptable.setLibelle(libelle);

        assertEquals(libelle,ligneEcritureComptable.getLibelle());
    }

    @Test
    public void getDebit() {
        BigDecimal debit = new BigDecimal(1.00);
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(null,null,debit,null);

        assertEquals(debit,ligneEcritureComptable.getDebit());
    }

    @Test
    public void setDebit() {
        BigDecimal debit = new BigDecimal(1.00);
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        ligneEcritureComptable.setDebit(debit);

        assertEquals(debit,ligneEcritureComptable.getDebit());
    }

    @Test
    public void getCredit() {
        BigDecimal credit = new BigDecimal(1.00);
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable(null,null,null,credit);

        assertEquals(credit,ligneEcritureComptable.getCredit());
    }

    @Test
    public void setCredit() {
        BigDecimal credit = new BigDecimal(1.00);
        LigneEcritureComptable ligneEcritureComptable = new LigneEcritureComptable();
        ligneEcritureComptable.setCredit(credit);

        assertEquals(credit,ligneEcritureComptable.getCredit());
    }
}
