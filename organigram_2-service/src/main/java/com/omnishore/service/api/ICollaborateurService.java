package com.omnishore.service.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omnishore.vo.CollaborateurVo;
import com.omnishore.vo.EntiteVo;
import com.omnishore.vo.FonctionVo;

public interface ICollaborateurService {
		
	
	
	public void createCollaborateur(CollaborateurVo collaborateur);
	public CollaborateurVo findCollaborateur(int collaborateurID);
	public List<CollaborateurVo> getResponsables();
	public List<CollaborateurVo> getCollaborateurs();
	public void updateCollaborateur(CollaborateurVo collaborateur);
	public boolean deleteCollaborateur(int collaborateurID);
	public List<CollaborateurVo> getAvailableResponsables();
	public CollaborateurVo findCollaborateur(String nomCollaborateur);
	public boolean checkErreurs(CollaborateurVo collaborateur);
	public Map<String, String> getErreurs();
	public void setErreurs(Map<String, String> erreurs);
	public List<CollaborateurVo> getCollaborateursOfEntite(EntiteVo entite);
	public List<CollaborateurVo> getOnlyCollaborateursOfEntite(EntiteVo entite);
	public List<CollaborateurVo> getOnlyCollaborateursOfEntite(String nom);
	
	/** Methode qui retourne une hashMap ayant comme clé le nom de l'entite 
	 * et une liste de collaborateurs de cette entite comme valeur
	 * @return HashMap<String,List<CollaborateurVo>>
	 */
	public HashMap<String,List<CollaborateurVo>> getMapCollaborateursEntites();
	
	public List<FonctionVo> getListFonctions();
	public FonctionVo getFunctionByLabel(String value); 
}
