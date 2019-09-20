package com.omnishore.service.impl;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import org.primefaces.model.DefaultStreamedContent;

import com.omnishore.entities.Collaborateur;
import com.omnishore.entities.Compte;
import com.omnishore.entities.Entite;
import com.omnishore.entities.Fonction;
import com.omnishore.vo.CollaborateurVo;
import com.omnishore.vo.CompteVo;
import com.omnishore.vo.EntiteVo;
import com.omnishore.vo.FonctionVo;

public class Converter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9054399426103594203L;

	public CollaborateurVo populateCollaborateurVo(Collaborateur collaborateur) {
		if (collaborateur != null) {

			return new CollaborateurVo(collaborateur.getIdCollaborateur(),
					collaborateur.getNom(), collaborateur.getPrenom(),
					collaborateur.getDateNaissance(),
					collaborateur.getDateEmbauche(),
					collaborateur.getDatePremierRec(),
					collaborateur.getActif(),
					populateEntiteVo(collaborateur.getEntite()),
					populateFonctionVo(collaborateur.getFonction()),
//					populateCompteVo(collaborateur.getCompte()),
					collaborateur.getIsResponsable(),
					collaborateur.getIsAdmin(), collaborateur.getCIN(), null, 
					collaborateur.getPhoto()!= null ?new DefaultStreamedContent(new ByteArrayInputStream(collaborateur.getPhoto())):null);
		}
		return null;
	}

	public CompteVo populateCompteVo(Compte compte) {
		if (compte != null) {

			return new CompteVo(compte.getIdCompte(), compte.getLogin(),
					compte.getEmail(), compte.getMotDePasse(), compte.getNom(),
					compte.getPrenom(),
					populateCollaborateurVo(compte.getCollaborateur()),
					compte.getCIN());
		}
		return null;
	}

	public EntiteVo populateEntiteVo(Entite entite) {
		if (entite != null) {

			return new EntiteVo(entite.getIdEntite(), entite.getNom(),
					entite.getDescription(),
//					populateCollaborateurVo(entite.getResponsable()),
					populateEntiteVo(entite.getEntiteMere()));
		}
		return null;
	}

	public Collaborateur populateCollaborateur(CollaborateurVo collaborateurVo) {
		if (collaborateurVo != null) {
			return new Collaborateur(collaborateurVo.getIdCollaborateur(),
					collaborateurVo.getNom(), collaborateurVo.getPrenom(),
					collaborateurVo.getDateNaissance(),
					collaborateurVo.getDateEmbauche(),
					collaborateurVo.getDatePremierRec(),
					collaborateurVo.getActif(),
					populateEntite(collaborateurVo.getEntite()),
					populateFonction(collaborateurVo.getFonction()),
					populateCompte(collaborateurVo.getCompte()),
					collaborateurVo.getIsResponsable(),
					collaborateurVo.getIsAdmin(), collaborateurVo.getCIN(), collaborateurVo.getUploadedPhoto()!=null?collaborateurVo.getUploadedPhoto().getContents():null);
		}
		return null;
	}

	public Compte populateCompte(CompteVo compteVo) {
		if (compteVo != null) {

			return new Compte(compteVo.getIdCompte(), compteVo.getLogin(),
					compteVo.getEmail(), compteVo.getMotDePasse(),
					compteVo.getNom(), compteVo.getPrenom(),
					populateCollaborateur(compteVo.getCollaborateur()),
					compteVo.getCIN());
		}
		return null;
	}

	public Entite populateEntite(EntiteVo entiteVo) {
		if (entiteVo != null) {

			return new Entite(entiteVo.getIdEntite(), entiteVo.getNom(),
					entiteVo.getDescription(),
					populateCollaborateur(entiteVo.getResponsable()),
					populateEntite(entiteVo.getEntiteMere()));
		}
		return null;
	}

	public FonctionVo populateFonctionVo(Fonction fonction) {
		if(fonction != null){
			return new FonctionVo(fonction.getCodeFonction(),fonction.getLibelleFonction());
		}
		return null;
	}
	
	public Fonction populateFonction(FonctionVo fonctionVo) {
		if(fonctionVo != null){
			return new Fonction(fonctionVo.getCodeFonction(),fonctionVo.getLibelleFonction());
		}
		return null;
	}
}
