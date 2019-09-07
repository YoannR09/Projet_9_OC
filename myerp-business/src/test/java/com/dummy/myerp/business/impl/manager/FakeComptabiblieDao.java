package com.dummy.myerp.business.impl.manager;


import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FakeComptabiblieDao implements ComptabiliteDao {

    @Override
    public List<CompteComptable> getListCompteComptable() {
        return null;
    }

    @Override
    public List<JournalComptable> getListJournalComptable() {
        return null;
    }

    @Override
    public List<EcritureComptable> getListEcritureComptable() {

        return null;
    }

    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("BQ-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        return vEcritureComptable;
    }

    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("BQ-2019/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        return vEcritureComptable;
    }

    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void deleteEcritureComptable(Integer pId) {

    }
}
