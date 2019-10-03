package com.dummy.myerp.testconsumer.consumer.builder;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;

public class SequenceBuilder {

    private Integer annee;
    private Integer derniereValeur;
    private String journalCode;


    public static SequenceBuilder aSequence(){
        return new SequenceBuilder();
    }

    public SequenceBuilder annee(Integer a){
        annee = a;
        return this;
    }

    public SequenceBuilder derniereVal(Integer val){
        derniereValeur = val;
        return this;
    }

    public SequenceBuilder journalCode(String code){
        journalCode = code;
        return this;
    }

    public SequenceEcritureComptable build(){
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setJournalCode(journalCode);
        sequenceEcritureComptable.setAnnee(annee);
        if (derniereValeur != null){
            sequenceEcritureComptable.setDerniereValeur(derniereValeur);
        }
        return sequenceEcritureComptable;
    }
}
