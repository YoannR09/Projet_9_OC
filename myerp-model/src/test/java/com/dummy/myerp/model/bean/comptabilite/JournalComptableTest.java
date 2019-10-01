package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class JournalComptableTest {

    @Test
    public void getCode() {
        String code = "235";
        JournalComptable journalComptable = new JournalComptable(code,"libelle");

        assertEquals(code,journalComptable.getCode());
    }

    @Test
    public void setCode() {
        String code = "235";
        JournalComptable journalComptable = new JournalComptable();
        journalComptable.setCode(code);

        assertEquals(code,journalComptable.getCode());
    }

    @Test
    public void getLibelle() {
        String libelle = "libelle";
        JournalComptable journalComptable = new JournalComptable("235",libelle);

        assertEquals(libelle,journalComptable.getLibelle());
    }

    @Test
    public void setLibelle() {
        String libelle = "libelle";
        JournalComptable journalComptable = new JournalComptable();
        journalComptable.setLibelle(libelle);

        assertEquals(libelle,journalComptable.getLibelle());
    }

    @Test
    public void getByCode() {

        JournalComptable journalComptable = new JournalComptable("235","libelle");
        List<JournalComptable> vList = new ArrayList<>();
        vList.add(new JournalComptable("134","libelle"));
        vList.add(new JournalComptable("784","libelle"));
        vList.add(new JournalComptable("126","libelle"));
        vList.add(journalComptable);

        assertEquals(journalComptable,JournalComptable.getByCode(vList,"235"));
    }
}
