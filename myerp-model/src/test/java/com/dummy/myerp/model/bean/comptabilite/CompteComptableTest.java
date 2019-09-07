package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;



public class CompteComptableTest {

    @Test
    public void getNumero() {
        Integer numero = new Integer(1);
        CompteComptable compteComptable = new CompteComptable(numero,"libelle");

        Assert.assertEquals(numero,compteComptable.getNumero());
    }

    @Test
    public void setNumero() {
        Integer numero = new Integer(1);
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setNumero(numero);

        Assert.assertEquals(numero,compteComptable.getNumero());
    }

    @Test
    public void getLibelle() {
        String libelle = "libelle";
        CompteComptable compteComptable = new CompteComptable(1,libelle);

        Assert.assertEquals(libelle,compteComptable.getLibelle());
    }

    @Test
    public void setLibelle() {
        String libelle = "libelle";
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setLibelle(libelle);

        Assert.assertEquals(libelle,compteComptable.getLibelle());
    }

    @Test
    public void getByNumero() {

        CompteComptable compteComptable = new CompteComptable(7,"libelle");
        List<CompteComptable> vList = new ArrayList<>();
        vList.add(compteComptable);
        vList.add(new CompteComptable(new Integer(5),"libelle"));
        vList.add(new CompteComptable(new Integer(1),"libelle"));
        vList.add(new CompteComptable(new Integer(2),"libelle"));

        Assert.assertEquals(compteComptable,compteComptable.getByNumero(vList,7));
    }


}
