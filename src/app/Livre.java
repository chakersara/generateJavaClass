/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sarra
 */

@XmlRootElement(name = "livre")
@XmlAccessorType(XmlAccessType.PROPERTY)
class Livre {
    //attributs
    private String nom;
    private String autheur;

    public Livre() {
    }

    public Livre(String nom, String autheur) {
        this.nom = nom;
        this.autheur = autheur;
    }

    public String getNom() {
        return nom;
    }
//@XmlAttribute(name="nom")
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAutheur() {
        return autheur;
    }
//@XmlAttribute(name="autheur")
    public void setAutheur(String autheur) {
        this.autheur = autheur;
    }

    @Override
    public String toString() {
        return "\n\t\t{" + "nom=" + nom + ", \n\t\tautheur=" + autheur + '}';
    }
    
}
