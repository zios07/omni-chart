package com.omnishore.vo;

import java.io.Serializable;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

public class CollaborateurVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2081971288084619717L;
	
	private int idCollaborateur;

	private String nom;
	
	private String prenom;
	
	private Date dateNaissance;
	
	private Date dateEmbauche;
	
	private Date datePremierRec;
	
	
	private Boolean actif = true;

	private FonctionVo fonction;
	
	private EntiteVo entite;
	
	private CompteVo compte;
	
	private Boolean isResponsable;
	
	private Boolean isAdmin;
	
	private String CIN;
	
	private UploadedFile uploadedPhoto;
	
	private StreamedContent photo;
	
	
	/////// GETTERS AND SETTERS
	
	
	public CollaborateurVo(int idCollaborateur, String nom, String prenom,
			Date dateNaissance, Date dateEmbauche, Date datePremierRec,
			Boolean actif, EntiteVo entite, FonctionVo fonction,
			Boolean isResponsable, Boolean isAdmin, String cIN, UploadedFile uploadedPhoto, StreamedContent photo) {
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
		this.CIN = cIN;
		this.uploadedPhoto = uploadedPhoto;
		this.photo = photo;
	}

	

	public CollaborateurVo() {
	}

	public CompteVo getCompte() {
		return compte;
	}

	public FonctionVo getFonction() {
		return fonction;
	}


	public void setFonction(FonctionVo fonction) {
		this.fonction = fonction;
	}
	
	public void setCompte(CompteVo compte) {
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

	public EntiteVo getEntite() {
		return entite;
	}

	public void setEntite(EntiteVo entite) {
		this.entite = entite;
	}

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
	
	public UploadedFile getUploadedPhoto() {
		return uploadedPhoto;
	}

	public void setUploadedPhoto(UploadedFile uploadedPhoto) {
		this.uploadedPhoto = uploadedPhoto;
	}

	public StreamedContent getPhoto() {
		FacesContext context = FacesContext.getCurrentInstance();
		if(context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE){
			return new DefaultStreamedContent();
		}
		else{
			return this.photo;
		}
	}



	public void setPhoto(StreamedContent photo) {
		this.photo = photo;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CollaborateurVo that = (CollaborateurVo) o;
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
