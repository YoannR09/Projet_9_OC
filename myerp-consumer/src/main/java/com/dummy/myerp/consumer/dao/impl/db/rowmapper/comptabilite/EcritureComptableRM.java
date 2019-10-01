package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.springframework.jdbc.core.RowMapper;
import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;


/**
 * {@link RowMapper} de {@link EcritureComptable}
 */
public class EcritureComptableRM implements RowMapper<EcritureComptable> {

    /** JournalComptableDaoCache */
    private static JournalComptableDaoCache journalComptableDaoCache = new JournalComptableDaoCache();

    public EcritureComptableRM(){}

    @Override
    public EcritureComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        EcritureComptable vBean = new EcritureComptable();
        vBean.setId(pRS.getInt("id"));
        vBean.setJournal(getJournalCode(pRS));
        vBean.setReference(pRS.getString("reference"));
        vBean.setDate(pRS.getDate("date"));
        vBean.setLibelle(pRS.getString("libelle"));

        // Chargement des lignes d'écriture
        loadListLigneEcriture(vBean);

        return vBean;
    }

    public JournalComptable getJournalCode(ResultSet pRS) throws SQLException {
        return journalComptableDaoCache.getByCode(pRS.getString("journal_code"));
    }

    public void loadListLigneEcriture(EcritureComptable vBean) {
        ConsumerHelper.getDaoProxy().getComptabiliteDao().loadListLigneEcriture(vBean);
    }
}
