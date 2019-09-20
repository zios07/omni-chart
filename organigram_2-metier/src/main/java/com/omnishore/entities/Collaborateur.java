package com.omnishore.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Collaborateur implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -2081971288084619717L;
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int idCollaborateur;

	private String nom;
	
	private String prenom;
	
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	
	@Temporal(TemporalType.DATE)
	private Date dateEmbauche;
	
	@Temporal(TemporalType.DATE)
	private Date datePremierRec;
	
	private Boolean actif = true;

	@OneToOne
	@JoinColumn(name="idFonction")
	private Fonction fonction;
	
	@OneToOne @JoinColumn(name="ID_Entite")
	private Entite entite;
	
	@OneToOne @JoinColumn(name="ID_COMPTE")
	private Compte compte;
	
	private Boolean isResponsable;
	
	private Boolean isAdmin;
	
	@Column(unique=true)
	private String CIN;
	@Column(columnDefinition = "LONGBLOB")
	private byte[] photo;

	public Collaborateur(int idCollaborateur, String nom, String prenom,
			Date dateNaissance, Date dateEmbauche, Date datePremierRec,
			Boolean actif, Entite entite, Fonction fonction, Compte compte, Boolean isResponsable,
			Boolean isAdmin, String cIN, byte[] photo) {
		super();
		this.idCollaborateur = idCollaborateur;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.dateEmbauche = dateEmbauche;
		this.datePremierRec = datePremierRec;
		this.actif = actif;
		this.entite = entite;
		this.fonction = fonction;
		this.compte = compte;
		this.isResponsable = isResponsable;
		this.isAdmin = isAdmin;
		CIN = cIN;
		this.photo = photo;
	}
	
	public Collaborateur(){}
	
	/////// GETTERS AND SETTERS

	public Compte getCompte() {
		return compte;
	}
	
	public byte[] getPhoto() {
		return photo;
	}

	public Fonction getFonction() {
		return fonction;
	}

	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}


	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public String getCIN() {
		return CIN;
	}

	public void setCIN(String cIN) {
		CIN = cIN;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsResponsable() {
		return isResponsable;
	}

	public void setIsResponsable(Boolean isResponsable) {
		this.isResponsable = isResponsable;
	}

	// public Responsable getResponsable() {
	// return responsable;
	// }
	// public void setResponsable(Responsable responsable) {
	// this.responsable = responsable;
	// }
	// public Titre getTitre() {
	// return titre;
	// }
	// public void setTitre(Titre titre) {
	// this.titre = titre;
	// }
	// public Fonction getFonction() {
	// return fonction;
	// }
	// public void setFonction(Fonction fonction) {
	// this.fonction = fonction;
	// }
	public Entite getEntite() {
		return entite;
	}

	public void setEntite(Entite entite) {
		this.entite = entite;
	}

	// public Compte getCompte() {
	// return compte;
	// }
	// public void setCompte(Compte compte) {
	// this.compte = compte;
	// }
	public int getIdCollaborateur() {
		return idCollaborateur;
	}

	public void setIdCollaborateur(int idCollaborateur) {
		this.idCollaborateur = idCollaborateur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Date getDateEmbauche() {
		return dateEmbauche;
	}

	public void setDateEmbauche(Date dateEmbauche) {
		this.dateEmbauche = dateEmbauche;
	}

	public Date getDatePremierRec() {
		return datePremierRec;
	}

	public void setDatePremierRec(Date datePremierRec) {
		this.datePremierRec = datePremierRec;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Collaborateur that = (Collaborateur) o;
		if (idCollaborateur != 0 ? idCollaborateur != (that.idCollaborateur) : that.idCollaborateur != 0)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = nom.hashCode();
		result = 31 * result
				+ (prenom != null ? prenom.hashCode() : 0);
		return result;
	}
	
}
