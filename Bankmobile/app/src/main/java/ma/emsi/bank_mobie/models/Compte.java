package ma.emsi.bank_mobie.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "compte") // Maps to the root element in the XML
public class Compte {

    private Long id;
    private double solde;
    private String dateCreation;
    private String type;

    // Constructor
    public Compte(Long id, double solde, String type) {
        this.id = id;
        this.solde = solde;
        this.type = type;
    }

    // Getters and Setters

    @XmlElement(name = "id")  // Maps to the <id> XML element
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @XmlElement(name = "solde")  // Maps to the <solde> XML element
    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    @XmlElement(name = "dateCreation")
    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }

    @XmlElement(name = "type")  // Maps to the <type> XML element
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
