package com.dummy.myerp.model.bean.comptabilite;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SequenceEcritureComptableTest {

    @Test
    public void getAnnee() {
        Integer annee = new Integer(2019);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(annee,null);

        assertEquals(annee,sequenceEcritureComptable.getAnnee());
    }

    @Test
    public void setAnnee() {
        Integer annee = new Integer(2019);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setAnnee(annee);

        assertEquals(annee,sequenceEcritureComptable.getAnnee());
    }

    @Test
    public void getDerniereValeur() {
        Integer derniereValeur = new Integer(10);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(null,derniereValeur);

        assertEquals(derniereValeur,sequenceEcritureComptable.getDerniereValeur());
    }

    @Test
    public void setDerniereValeur() {
        Integer derniereValeur = new Integer(10);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setDerniereValeur(derniereValeur);

        assertEquals(derniereValeur,sequenceEcritureComptable.getDerniereValeur());
    }

    @Test
    public void getJournalCode() {
        JournalComptable journalComptable = new JournalComptable("AC","libelle");
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setJournalCode(journalComptable.getCode());

        assertEquals(sequenceEcritureComptable.getJournalCode(),"AC");
    }

    @Test
    public void setJournalCode() {
        JournalComptable journalComptable = new JournalComptable("AC","libelle");
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setJournalCode(journalComptable.getCode());

        assertEquals(sequenceEcritureComptable.getJournalCode(),"AC");
    }
}
