package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.sql.Types;
import java.util.List;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.*;
import com.dummy.myerp.model.bean.comptabilite.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {

    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final ComptabiliteDaoImpl INSTANCE = new ComptabiliteDaoImpl();
    /** Logger Log4j pour la classe */
    private static final Logger LOGGER = LogManager.getLogger();

    JdbcTemplate vJdbcTemplate;
    NamedParameterJdbcTemplate namedJdbcTemplate;
    AbstractDbConsumer abstractDbConsumer;

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link ComptabiliteDaoImpl}
     */
    public static ComptabiliteDaoImpl getInstance() {
        return ComptabiliteDaoImpl.INSTANCE;
    }

    /**
     * Constructeur.
     */
    public ComptabiliteDaoImpl() {
        super();
    }

    public ComptabiliteDaoImpl(JdbcTemplate template,NamedParameterJdbcTemplate namedTemplate,AbstractDbConsumer adc) {
        vJdbcTemplate = template;
        namedJdbcTemplate = namedTemplate;
        abstractDbConsumer = adc;
    }


    // ==================== Méthodes ====================
    /** SQLgetListCompteComptable */
    private static String SQLgetListCompteComptable;
    public void setSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
        SQLgetListCompteComptable = pSQLgetListCompteComptable;
    }

    @Override
    public List<CompteComptable> getListCompteComptable() {
        if (vJdbcTemplate == null) {
            vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        }
        CompteComptableRM vRM = new CompteComptableRM();
        List<CompteComptable> vList = vJdbcTemplate.query(SQLgetListCompteComptable, vRM);
        LOGGER.trace("Méthode : getListCompteComptable(), sql : "+ SQLgetListCompteComptable);
        return vList;
    }


    /** SQLgetListJournalComptable */
    private static String SQLgetListJournalComptable;
    public void setSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
        SQLgetListJournalComptable = pSQLgetListJournalComptable;
    }
    @Override
    public List<JournalComptable> getListJournalComptable() {
        if (vJdbcTemplate == null) {
            vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        }
        JournalComptableRM vRM = new JournalComptableRM();
        List<JournalComptable> vList = vJdbcTemplate.query(SQLgetListJournalComptable, vRM);
        LOGGER.trace("Méthode : getListJournalComptable(), sql : "+ SQLgetListJournalComptable);
        return vList;
    }

    // ==================== EcritureComptable - GET ====================

    /** SQLgetListEcritureComptable */
    private static String SQLgetListEcritureComptable;
    public void setSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
        SQLgetListEcritureComptable = pSQLgetListEcritureComptable;
    }
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        if (vJdbcTemplate == null) {
            vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        }
        EcritureComptableRM vRM = new EcritureComptableRM();
        List<EcritureComptable> vList = vJdbcTemplate.query(SQLgetListEcritureComptable, vRM);
        LOGGER.trace("Méthode : getListEcritureComptable(), sql : "+ SQLgetListEcritureComptable);
        return vList;
    }


    /** SQLgetEcritureComptable */
    private static String SQLgetEcritureComptable;
    public void setSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
        SQLgetEcritureComptable = pSQLgetEcritureComptable;
    }
    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = namedJdbcTemplate.queryForObject(SQLgetEcritureComptable, vSqlParams, vRM);
            LOGGER.trace("Méthode : getEcritureComptable(), sql : "+ SQLgetEcritureComptable);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
        }
        return vBean;
    }


    /** SQLgetEcritureComptableByRef */
    private static String SQLgetEcritureComptableByRef;
    public void setSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
        SQLgetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
    }
    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("reference", pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = namedJdbcTemplate.queryForObject(SQLgetEcritureComptableByRef, vSqlParams, vRM);
            LOGGER.trace("Méthode : getEcritureComptableByRef(), sql : "+ SQLgetEcritureComptableByRef);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }


    /** SQLloadListLigneEcriture */
    private static String SQLloadListLigneEcriture;
    public void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        SQLloadListLigneEcriture = pSQLloadListLigneEcriture;
    }
    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        List<LigneEcritureComptable> vList = namedJdbcTemplate.query(SQLloadListLigneEcriture, vSqlParams, vRM);
        LOGGER.trace("Méthode : loadListLigneEcriture(), sql : "+ SQLloadListLigneEcriture);
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);

    }


    // ==================== EcritureComptable - INSERT ====================

    /** SQLinsertEcritureComptable */
    private static String SQLinsertEcritureComptable;
    public void setSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
        SQLinsertEcritureComptable = pSQLinsertEcritureComptable;
    }
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue("reference", pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

        namedJdbcTemplate.update(SQLinsertEcritureComptable, vSqlParams);
        LOGGER.trace("Méthode : insertEcritureComptable(), sql : "+ SQLinsertEcritureComptable);
        // ----- Récupération de l'id
        Integer vId;
        if(abstractDbConsumer == null) {
            vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                    Integer.class,null);
        }
        else {
            vId = abstractDbConsumer.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                    Integer.class,null);
        }
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);

    }

    /** SQLinsertListLigneEcritureComptable */
    private static String SQLinsertListLigneEcritureComptable;
    public void setSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
        SQLinsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
    }

    /**
     * Insert les lignes d'écriture de l'écriture comptable
     * @param pEcritureComptable l'écriture comptable
     */
    // CORRECTED
    public void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());

        int vLigneId = 0;
        for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
            vLigneId++;
            vSqlParams.addValue("ligne_id", vLigneId);
            vSqlParams.addValue("compte_comptable_numero", vLigne.getCompteComptable().getNumero());
            vSqlParams.addValue("libelle", vLigne.getLibelle());
            vSqlParams.addValue("debit", vLigne.getDebit());

            vSqlParams.addValue("credit", vLigne.getCredit());

            namedJdbcTemplate.update(SQLinsertListLigneEcritureComptable, vSqlParams);
            LOGGER.trace("Méthode : insertListLigneEcritureComptable(), sql : "+ SQLinsertListLigneEcritureComptable);
        }
    }


    // ==================== EcritureComptable - UPDATE ====================

    /** SQLupdateEcritureComptable */
    private static String SQLupdateEcritureComptable;
    public void setSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
        SQLupdateEcritureComptable = pSQLupdateEcritureComptable;
    }
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pEcritureComptable.getId());
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue("reference", pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

        namedJdbcTemplate.update(SQLupdateEcritureComptable, vSqlParams);
        LOGGER.trace("Méthode : updateEcritureComptable(), sql : "+ SQLupdateEcritureComptable);
        // ===== Liste des lignes d'écriture
        this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }


    // ==================== EcritureComptable - DELETE ====================

    /** SQLdeleteEcritureComptable */
    private static String SQLdeleteEcritureComptable;
    public void setSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
        SQLdeleteEcritureComptable = pSQLdeleteEcritureComptable;
    }
    @Override
    public void deleteEcritureComptable(Integer pId) {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        namedJdbcTemplate.update(SQLdeleteEcritureComptable, vSqlParams);
        LOGGER.trace("Méthode : deleteEcritureComptable(), sql : "+ SQLdeleteEcritureComptable);
    }

    /** SQLdeleteListLigneEcritureComptable */
    private static String SQLdeleteListLigneEcritureComptable;
    public void setSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        SQLdeleteListLigneEcritureComptable = pSQLdeleteListLigneEcritureComptable;
    }
    /**
     * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
     * @param pEcritureId id de l'écriture comptable
     */
    public void deleteListLigneEcritureComptable(Integer pEcritureId) {
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureId);
        namedJdbcTemplate.update(SQLdeleteListLigneEcritureComptable, vSqlParams);
        LOGGER.trace("Méthode : deleteListLigneEcritureComptable(), sql : "+ SQLdeleteListLigneEcritureComptable);
    }

    /** getSequenceByCodeAndAnneeCourante */
    private static String SQLgetSequenceViaCodeAnnee;
    public void setSQLgetSequenceViaCodeAnnee(String pSQLgetSequenceByCodeAndAnneeCourante) {
        SQLgetSequenceViaCodeAnnee = pSQLgetSequenceByCodeAndAnneeCourante;
    }

    @Override
    public SequenceEcritureComptable getSequenceViaCodeAnnee(SequenceEcritureComptable pSequence) throws NotFoundException {
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        SequenceEcritureComptableRM vRM = new SequenceEcritureComptableRM();
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", pSequence.getJournalCode());
        vSqlParams.addValue("annee", pSequence.getAnnee());
        try {
            LOGGER.trace("Méthode : getSequenceViaCodeAnnee(), sql : "+ SQLgetSequenceViaCodeAnnee);
            return namedJdbcTemplate.queryForObject(SQLgetSequenceViaCodeAnnee, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("SequenceEcritureComptable non trouvée : journal_code=" + pSequence.getJournalCode() + ", annee=" + pSequence.getAnnee());
        }
    }

    private static String SQLdeleteSequenceEcritureComptable;
    public void setSQLdeleteSequenceEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        SQLdeleteSequenceEcritureComptable = pSQLdeleteListLigneEcritureComptable;
    }
    @Override
    public void deleteSequenceEcritureComptable(SequenceEcritureComptable pSequence) {
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", pSequence.getJournalCode());
        vSqlParams.addValue("annee", pSequence.getAnnee());
        namedJdbcTemplate.update(SQLdeleteSequenceEcritureComptable, vSqlParams);
        LOGGER.trace("Méthode : deleteSequenceEcritureComptable(), sql : "+ SQLdeleteSequenceEcritureComptable);
    }

    /** SQLupsertSequenceEcritureComptable */
    private static String SQLinsertSequenceEcritureComptable;
    public void setSQLinsertSequenceEcritureComptable(String pSQLupsertSequenceEcritureComptable) {
        SQLinsertSequenceEcritureComptable = pSQLupsertSequenceEcritureComptable;
    }
    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable pSequence) {
        if(namedJdbcTemplate == null) {
            namedJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        }
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", pSequence.getJournalCode());
        vSqlParams.addValue("annee", pSequence.getAnnee());
        vSqlParams.addValue("derniere_valeur", pSequence.getDerniereValeur());
        namedJdbcTemplate.update(SQLinsertSequenceEcritureComptable, vSqlParams);
        LOGGER.trace("Méthode : insertSequenceEcritureComptable(), sql : "+ SQLinsertSequenceEcritureComptable);
    }
}
