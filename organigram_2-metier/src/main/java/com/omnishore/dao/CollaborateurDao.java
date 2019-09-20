package com.omnishore.dao;

import java.util.HashMap;
import java.util.List;

import com.omnishore.entities.Collaborateur;
import com.omnishore.entities.Entite;
import com.omnishore.entities.Fonction;

public interface CollaborateurDao {
	
	public void createCollaborateur(Collaborateur collaborateur);
	public Collaborateur findCollaborateur(int collaborateurID);
	public List<Collaborateur> getResponsables();
	public List<Collaborateur> getCollaborateurs();
	public void updateCollaborateur(Collaborateur collaborateur);
	public boolean deleteCollaborateur(int collaborateurID);
	public List<Collaborateur> getAvailableResponsables();
	public Collaborateur findCollaborateur(String nomCollaborateur);
	public Collaborateur findCollaborateur(String prenomCollaborateur, String nomCollaborateur);
	public void setEntiteToNull(int idEntite);
	public Collaborateur findByCIN(String CIN);
	public List<Collaborateur> getCollaborateurOfEntite(Entite entite);
	public List<Collaborateur> getOnlyCollaborateurOfEntite(Entite entite);
	
	public HashMap<String,List<Collaborateur>> getMapCollaborateursEntites();
	public List<Fonction> getListFonctions();
	public Fonction getFunctionByLabel(String value);

}
