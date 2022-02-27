/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sarra
 */
@XmlRootElement(name="librairie")
@XmlAccessorType(XmlAccessType.PROPERTY)

public class Librairie {
    //attributs
    Localisation localisation;
    Livres livres;

    public Librairie() {
    }

    public Librairie(Localisation localisation, Livres livres) {
        this.localisation = localisation;
        this.livres = livres;
    }

    public Localisation getLocalisation() {
        return localisation;
    }
    
    //@XmlElement(name = "localisation")
    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public Livres getLivres() {
        return livres;
    }
    //@XmlElement(name = "livres")
    public void setLivres(Livres livres) {
        this.livres = livres;
    }

    @Override
    public String toString() {
        return "Librairie{\n\n" + "\tlocalisation=" + localisation + ",\n\n\tlivres=" + livres +"\n\n"+'}';
    }
    
    
}
