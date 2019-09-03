package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

public class SequenceEcritureComptableTest {

    @Test
    public void getAnnee() {
        Integer annee = new Integer(2019);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(annee,null);

        Assert.assertEquals(annee,sequenceEcritureComptable.getAnnee());
    }

    @Test
    public void setAnnee() {
        Integer annee = new Integer(2019);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setAnnee(annee);

        Assert.assertEquals(annee,sequenceEcritureComptable.getAnnee());
    }

    @Test
    public void getDerniereValeur() {
        Integer derniereValeur = new Integer(10);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(null,derniereValeur);

        Assert.assertEquals(derniereValeur,sequenceEcritureComptable.getDerniereValeur());
    }

    @Test
    public void setDerniereValeur() {
        Integer derniereValeur = new Integer(10);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setDerniereValeur(derniereValeur);

        Assert.assertEquals(derniereValeur,sequenceEcritureComptable.getDerniereValeur());
    }

    @Test
    public void toStringTest(){
        String toString = "SequenceEcritureComptable{annee=2019, derniereValeur=10}";
        Integer annee = new Integer(2019);
        Integer derniereValeur = new Integer(10);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(annee,derniereValeur);
        Assert.assertEquals(toString,sequenceEcritureComptable.toString());
    }
}
