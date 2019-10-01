package com.dummy.myerp.consumer.dao.impl.db.fake;


import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FakeComptabiblieDao implements ComptabiliteDao {

    List<EcritureComptable> fakeListEcriture  = new ArrayList<>();
    List<CompteComptable> fakeListCompte = new ArrayList<>();
    List<JournalComptable> fakeListJournal = new ArrayList<>();
    List<SequenceEcritureComptable> fakeListSequence = new ArrayList<>();

    public FakeComptabiblieDao(){
        initFakeDb();
    }



    @Override
    public List<CompteComptable> getListCompteComptable() {
        return fakeListCompte;
    }

    @Override
    public List<JournalComptable> getListJournalComptable() {
        return fakeListJournal;
    }

    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return fakeListEcriture;
    }

    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        EcritureComptable vEcritureComptable = null;
        for(EcritureComptable ecritureComptable:fakeListEcriture){
            if (ecritureComptable.getId() == pId){
                vEcritureComptable = ecritureComptable;
            }
        }
        if (vEcritureComptable == null){
            throw new NotFoundException();
        }
        return vEcritureComptable;
    }

    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        EcritureComptable vEcritureComptable = null;
        for(EcritureComptable ecritureComptable:fakeListEcriture){
            if (ecritureComptable.getReference() == pReference){
                vEcritureComptable = ecritureComptable;
            }
        }
        if (vEcritureComptable == null){
            throw new NotFoundException();
        }
        return vEcritureComptable;
    }

    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable());
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable());
    }

    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        fakeListEcriture.add(pEcritureComptable);
    }

    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void deleteEcritureComptable(Integer pId) {
        for(EcritureComptable ecritureComptable:fakeListEcriture){
            if (ecritureComptable.getId() == pId){
                fakeListEcriture.remove(ecritureComptable);
            }
        }
    }

    @Override
    public SequenceEcritureComptable getSequenceViaCodeAnnee(SequenceEcritureComptable pSequence) throws NotFoundException {
        SequenceEcritureComptable vReturn = null;
        for (SequenceEcritureComptable sequenceEcritureComptable:fakeListSequence){
            if (sequenceEcritureComptable.getAnnee().equals(pSequence.getAnnee()) && sequenceEcritureComptable.getJournalCode().equals(pSequence.getJournalCode())){
                vReturn = sequenceEcritureComptable;
            }
        }
        if(vReturn == null){
            throw new NotFoundException("SequenceEcritureComptable non trouvée : journal_code=" + pSequence.getJournalCode() + ", annee=" + pSequence.getAnnee());
        }
        return vReturn;
    }

    @Override
    public void upsertSequenceEcritureComptable(SequenceEcritureComptable pSequence) {
        fakeListSequence.get(0).setJournalCode(pSequence.getJournalCode());
        fakeListSequence.get(0).setDerniereValeur(pSequence.getDerniereValeur());
        fakeListSequence.get(0).setAnnee(pSequence.getAnnee());
    }

    public void initFakeDb(){
        CompteComptable compteComptable1 = new CompteComptable(2332,"compte1");
        CompteComptable compteComptable2 = new CompteComptable(4568,"compte2");
        fakeListCompte.add(compteComptable1);
        fakeListCompte.add(compteComptable2);
        EcritureComptable vEcritureComptable1 = new EcritureComptable();
        vEcritureComptable1.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable1.setDate(new Date());
        vEcritureComptable1.setId(45);
        vEcritureComptable1.setLibelle("Libelle");
        vEcritureComptable1.setReference("AC-2019/00001");
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable1.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        EcritureComptable vEcritureComptable2 = new EcritureComptable();
        vEcritureComptable2.setJournal(new JournalComptable("RT", "Retrait"));
        vEcritureComptable2.setDate(new Date());
        vEcritureComptable2.setId(12);
        vEcritureComptable2.setLibelle("Libelle");
        vEcritureComptable2.setReference("RT-2019/00011");
        vEcritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable2.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        fakeListEcriture.add(vEcritureComptable1);
        fakeListEcriture.add(vEcritureComptable2);
        JournalComptable journalComptable1 = new JournalComptable("AC","Achat");
        JournalComptable journalComptable2 = new JournalComptable("RT","Retrait");
        fakeListJournal.add(journalComptable1);
        fakeListJournal.add(journalComptable2);
        SequenceEcritureComptable sequenceEcritureComptable1 = new SequenceEcritureComptable(new Integer(2019),new Integer(55));
        sequenceEcritureComptable1.setJournalCode("AC");
        SequenceEcritureComptable sequenceEcritureComptable2 = new SequenceEcritureComptable(new Integer(2019),new Integer(12));
        sequenceEcritureComptable2.setJournalCode("RT");
        fakeListSequence.add(sequenceEcritureComptable1);
        fakeListSequence.add(sequenceEcritureComptable2);
    }
}
