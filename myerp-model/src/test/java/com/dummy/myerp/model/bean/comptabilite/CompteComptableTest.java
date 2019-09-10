package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;



public class CompteComptableTest {

    @Test
    public void getNumero() {
        Integer numero = new Integer(1);
        CompteComptable compteComptable = new CompteComptable(numero,"libelle");

        assertEquals(numero,compteComptable.getNumero());
    }

    @Test
    public void setNumero() {
        Integer numero = new Integer(1);
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setNumero(numero);

        assertEquals(numero,compteComptable.getNumero());
    }

    @Test
    public void getLibelle() {
        String libelle = "libelle";
        CompteComptable compteComptable = new CompteComptable(1,libelle);

        assertEquals(libelle,compteComptable.getLibelle());
    }

    @Test
    public void setLibelle() {
        String libelle = "libelle";
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setLibelle(libelle);

        assertEquals(libelle,compteComptable.getLibelle());
    }

    @Test
    public void getByNumero() {

        CompteComptable compteComptable = new CompteComptable(7,"libelle");
        List<CompteComptable> vList = new ArrayList<>();
        vList.add(compteComptable);
        vList.add(new CompteComptable(new Integer(5),"libelle"));
        vList.add(new CompteComptable(new Integer(1),"libelle"));
        vList.add(new CompteComptable(new Integer(2),"libelle"));

        assertEquals(compteComptable,compteComptable.getByNumero(vList,7));
    }


}
