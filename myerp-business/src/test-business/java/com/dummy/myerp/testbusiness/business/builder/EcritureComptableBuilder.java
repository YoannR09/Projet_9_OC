package com.dummy.myerp.testbusiness.business.builder;

import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EcritureComptableBuilder {

    private Integer id;
    private JournalComptable journal = new JournalComptable();
    private String codeJournal;
    private String ref;
    private Date date;
    private String libelle;

    private final List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();


    public static EcritureComptableBuilder aEcritureComptable(){
        return new EcritureComptableBuilder();
    }

    public EcritureComptableBuilder journal(JournalComptable journalComptable){
        journal = journalComptable;
        return this;
    }

    public EcritureComptableBuilder ref(String reference){
        ref = reference;
        return this;
    }

    public EcritureComptableBuilder libelle(String lib){
        libelle = lib;
        return this;
    }

    public EcritureComptableBuilder date(Date d){
        date = d;
        return this;
    }

    public EcritureComptable build(){
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setJournal(journal);
        ecritureComptable.setReference(ref);
        ecritureComptable.setLibelle(libelle);
        ecritureComptable.setDate(date);
        return ecritureComptable;
    }

}
