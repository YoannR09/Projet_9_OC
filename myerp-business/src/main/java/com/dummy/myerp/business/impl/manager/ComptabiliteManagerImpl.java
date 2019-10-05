package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Set;


/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================

    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        List<CompteComptable> vList = getComptabiliteDao().getListCompteComptable();
        return vList;

    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return daoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return daoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    /**
     * {@inheritDoc}
     */
    // CORRECTED
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) throws NotFoundException, FunctionalException {
        TransactionStatus vTS = getvTS();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(pEcritureComptable.getDate());
            Integer dateEcritureComptable = calendar.get(Calendar.YEAR);
            SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
            sequenceEcritureComptable.setJournalCode(pEcritureComptable.getJournal().getCode());
            sequenceEcritureComptable.setAnnee(dateEcritureComptable);
            SequenceEcritureComptable vSequence = daoProxy().getComptabiliteDao().getSequenceViaCodeAnnee(sequenceEcritureComptable);
            Integer numeroSequence;
            if (vSequence == null){
                numeroSequence = 1;
            } else{
                numeroSequence = vSequence.getDerniereValeur() + 1;
            }
            String vReference;
            if (numeroSequence.toString().length() == 1){
                vReference = pEcritureComptable.getJournal().getCode() +"-"+ dateEcritureComptable +"/0000"+ numeroSequence;
            } else if (numeroSequence.toString().length() == 2){
                vReference = pEcritureComptable.getJournal().getCode() +"-"+ dateEcritureComptable +"/000"+ numeroSequence;
            }else if (numeroSequence.toString().length() == 3){
                vReference = pEcritureComptable.getJournal().getCode() +"-"+ dateEcritureComptable +"/00"+ numeroSequence;
            }else if (numeroSequence.toString().length() == 4){
                vReference = pEcritureComptable.getJournal().getCode() +"-"+ dateEcritureComptable +"/0"+ numeroSequence;
            } else  {
                vReference = pEcritureComptable.getJournal().getCode() +"-"+ dateEcritureComptable +"/"+ numeroSequence;
            }
            pEcritureComptable.setReference(vReference);
            SequenceEcritureComptable vNewSequence = new SequenceEcritureComptable();
            vNewSequence.setJournalCode(pEcritureComptable.getJournal().getCode());
            vNewSequence.setAnnee(dateEcritureComptable);
            vNewSequence.setDerniereValeur(numeroSequence);
            this.updateEcritureComptable(pEcritureComptable);
            this.deleteSequenceEcritureComptable(sequenceEcritureComptable);
            this.insertSequenceEcritureComptable(vNewSequence);
            commitMyERP(vTS);
            vTS = null;
        } finally {
            rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    // CORRECTED
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    // CORRECTED
    protected void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        Set<ConstraintViolation<EcritureComptable>> vViolations = getValidate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                    new ConstraintViolationException(
                            "L'écriture comptable ne respecte pas les contraintes de validation",
                            vViolations));
        }

        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        if (!pEcritureComptable.isEquilibree()) {
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }

        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }

        // On test le nombre de lignes car si l'écriture à une seule ligne
        //      avec un montant au débit et un montant au crédit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
                || vNbrCredit < 1
                || vNbrDebit < 1) {
            throw new FunctionalException(
                    "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }

        // CORRECTED ===== RG_Compta_5 : Format et contenu de la référence
        // vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...
        if (pEcritureComptable.getReference() != null){
            String refAnnee = pEcritureComptable.getReference().substring(3, pEcritureComptable.getReference().length() - 6);
            if (!refAnnee.equals(String.valueOf(pEcritureComptable.getDate().getYear()+1900))) {
                throw new FunctionalException("L'année dans la référence doit correspondre à la date de l'écriture.");
            }
            if (!pEcritureComptable.getReference().substring(0,2).equals(pEcritureComptable.getJournal().getCode())){
                throw new FunctionalException("Le code journal dans la référence doit correspondre à celui du code journal.");
            }
        }else {
            throw new FunctionalException("La référence est null");
        }

    }

    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                EcritureComptable vECRef = daoProxy().getComptabiliteDao().getEcritureComptableByRef(
                        pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() == null
                        || !pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getvTS();
        try {
            daoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            commitMyERP(vTS);
            vTS = null;
        } finally {
            rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    // CORRECTED
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getvTS();
        try {
            daoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            commitMyERP(vTS);
            vTS = null;
        } finally {
            rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getvTS();
        try {
            daoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            commitMyERP(vTS);
            vTS = null;
        } finally {
            rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSequenceEcritureComptable(SequenceEcritureComptable pSequence){
        TransactionStatus vTS = getvTS();
        try {
            daoProxy().getComptabiliteDao();
            commitMyERP(vTS);
            vTS = null;
        } finally {
            rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable pSequence) {
        TransactionStatus vTS = getvTS();
        try {
            daoProxy().getComptabiliteDao().insertSequenceEcritureComptable(pSequence);
            commitMyERP(vTS);
            vTS = null;
        } finally {
            rollbackMyERP(vTS);
        }
    }

    private ComptabiliteDao getComptabiliteDao() {
        return daoProxy().getComptabiliteDao();
    }

    protected DaoProxy daoProxy() {
        return getDaoProxy();
    }

    protected TransactionStatus getvTS() {
        return getTransactionManager().beginTransactionMyERP();
    }

    protected void rollbackMyERP(TransactionStatus vTS) {
        getTransactionManager().rollbackMyERP(vTS);
    }

    protected void commitMyERP(TransactionStatus vTS) {
        getTransactionManager().commitMyERP(vTS);
    }

    protected Set<ConstraintViolation<EcritureComptable>> getValidate(EcritureComptable pEcritureComptable) {
        return getConstraintValidator().validate(pEcritureComptable);
    }
}
