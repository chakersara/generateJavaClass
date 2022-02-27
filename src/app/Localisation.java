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

@XmlRootElement(name = "localisation")
@XmlAccessorType(XmlAccessType.PROPERTY)
class Localisation {
    //attributs
    private String pays;
    private String gouvernorat;
    private String ville;
    private String rue;

    public Localisation() {
    }

    public Localisation(String pays, String gouvernorat, String ville, String rue) {
        this.pays = pays;
        this.gouvernorat = gouvernorat;
        this.ville = ville;
        this.rue = rue;
    }

    public String getPays() {
        return pays;
    }
    //@XmlAttribute(name="pays")
    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }
    //@XmlAttribute(name="gouvernorat")
    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getVille() {
        return ville;
    }
    //@XmlAttribute(name="ville")
    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRue() {
        return rue;
    }
    //@XmlAttribute(name="rue")
    public void setRue(String rue) {
        this.rue = rue;
    }

    @Override
    public String toString() {
        return "{" + "\n\t\tpays=" + pays + ",\n\t\tgouvernorat=" + gouvernorat + ",\n\t\tville=" + ville + ",\n\t\true=" + rue +'}';
    }
    
    
}
