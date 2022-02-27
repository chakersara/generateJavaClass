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
@XmlRootElement(name = "livres")
@XmlAccessorType(XmlAccessType.PROPERTY)
class Livres {
    //attributs
    private Livre[] livre;

    public Livres() {
    }

    public Livres(Livre[] livre) {
        this.livre = livre;
    }

    public Livre[] getLivre() {
        return livre;
    }
//@XmlElement(name="livre")
    public void setLivre(Livre[] livre) {
        this.livre = livre;
    }

    @Override
    public String toString() {
        String ch="";
        for(Livre elem:livre){
            ch+=elem.toString()+"\n";
        }
        
        return "\n\t{" + ch +"\t"+ '}';
    }
    
}
