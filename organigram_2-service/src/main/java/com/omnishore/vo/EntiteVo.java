package com.omnishore.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class EntiteVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9193828424545931894L;
	private int idEntite;
	private String nom;
	private String description;
	
	private CollaborateurVo responsable;
	
	private Collection<CollaborateurVo> collaborateurs = new ArrayList<CollaborateurVo>();
	
	private EntiteVo entiteMere;
	
	
	public EntiteVo(){
		
	}
	

	public EntiteVo(int idEntite, String nom, String description,
			CollaborateurVo responsable, EntiteVo entiteMere) {
		super();
		this.idEntite = idEntite;
		this.nom = nom;
		this.description = description;
		this.responsable = responsable;
		this.entiteMere = entiteMere;
	}


	public EntiteVo(int idEntite, String nom, String description, EntiteVo entiteMere) {
		super();
		this.idEntite = idEntite;
		this.nom = nom;
		this.description = description;
		this.entiteMere = entiteMere;
	}
	
	public CollaborateurVo getResponsable() {
		return responsable;
	}

	public void setResponsable(CollaborateurVo responsable) {
		this.responsable = responsable;
	}

	public EntiteVo getEntiteMere() {
		return entiteMere;
	}

	public void setEntiteMere(EntiteVo entiteMere) {
		this.entiteMere = entiteMere;
	}

	public Collection<CollaborateurVo> getCollaborateurs() {
		return collaborateurs;
	}

	public void setCollaborateurs(Collection<CollaborateurVo> collaborateurs) {
		this.collaborateurs = collaborateurs;
	}

	public int getIdEntite() {
		return idEntite;
	}

	public void setIdEntite(int idEntite) {
		this.idEntite = idEntite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EntiteVo that = (EntiteVo) o;
		if (idEntite != 0 ? idEntite != (that.idEntite) : that.idEntite != 0)
			return false;
		if (description != null ? !description.equals(that.description)
				: that.description != null)
			return false;
		if (!nom.equals(that.nom))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = nom.hashCode();
		result = 31 * result
				+ (description != null ? description.hashCode() : 0);
		return result;
	}

}
