package com.omnishore.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omnishore.dao.CollaborateurDao;
import com.omnishore.dao.EntiteDao;
import com.omnishore.dao.impl.CollaborateurDaoImpl;
import com.omnishore.dao.impl.EntiteDaoImpl;
import com.omnishore.entities.Collaborateur;
import com.omnishore.entities.Fonction;
import com.omnishore.service.api.ICollaborateurService;
import com.omnishore.vo.CollaborateurVo;
import com.omnishore.vo.EntiteVo;
import com.omnishore.vo.FonctionVo;

public class CollaborateurServiceImpl implements ICollaborateurService,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2557977171273234841L;
	private CollaborateurDao collaborateurDao = new CollaborateurDaoImpl();
	public Converter converter = new Converter();
	private Map<String, String> erreurs = new HashMap<String, String>();
	private EntiteDao entiteDao = new EntiteDaoImpl();

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	@Override
	public void createCollaborateur(CollaborateurVo collaborateurVo) {

		Collaborateur collaborateur = converter
				.populateCollaborateur(collaborateurVo);
		collaborateur.setCompte(converter.populateCompte(collaborateurVo
				.getCompte()));
		collaborateurDao.createCollaborateur(collaborateur);

	}

	@Override
	public CollaborateurVo findCollaborateur(int collaborateurID) {
		return converter.populateCollaborateurVo(collaborateurDao
				.findCollaborateur(collaborateurID));
	}

	@Override
	public List<CollaborateurVo> getResponsables() {
		List<CollaborateurVo> collaborateursVo = new ArrayList<CollaborateurVo>();
		for (Collaborateur responsable : collaborateurDao.getResponsables()) {
			collaborateursVo
					.add(converter.populateCollaborateurVo(responsable));
		}
		return collaborateursVo;
	}

	@Override
	public List<CollaborateurVo> getCollaborateurs() {
		List<CollaborateurVo> collaborateursVo = new ArrayList<CollaborateurVo>();
		for (Collaborateur collaborateur : collaborateurDao.getCollaborateurs()) {
			collaborateursVo.add(converter
					.populateCollaborateurVo(collaborateur));
		}
		return collaborateursVo;
	}

	@Override
	public void updateCollaborateur(CollaborateurVo collaborateurVo) {
		Collaborateur collaborateur = converter
				.populateCollaborateur(collaborateurVo);
		collaborateur.setCompte(converter.populateCompte(collaborateurVo
				.getCompte()));
		collaborateurDao.updateCollaborateur(collaborateur);
	}

	@Override
	public boolean deleteCollaborateur(int collaborateurID) {
		return collaborateurDao.deleteCollaborateur(collaborateurID);
	}

	@Override
	public List<CollaborateurVo> getAvailableResponsables() {
		List<CollaborateurVo> collaborateursVo = new ArrayList<CollaborateurVo>();
		for (Collaborateur availableResponsable : collaborateurDao
				.getAvailableResponsables()) {
			collaborateursVo.add(converter
					.populateCollaborateurVo(availableResponsable));
		}
		return collaborateursVo;
	}

	@Override
	public CollaborateurVo findCollaborateur(String nomCollaborateur) {
		return converter.populateCollaborateurVo(collaborateurDao
				.findCollaborateur(nomCollaborateur));
	}

	@Override
	public boolean checkErreurs(CollaborateurVo collaborateur) {
		checkLength(collaborateur.getNom(), "nom");
		checkLength(collaborateur.getPrenom(), "prenom");
		checkCIN(collaborateur.getCIN());

		if (erreurs.isEmpty())
			return true;

		return false;
	}

	private void checkCIN(String cin) {
		if (cin != null && cin.trim().length() != 0) {
			if (cin.length() < 4) {
				erreurs.put("CIN",
						"Ce champs doit contenir au moins 3 caractère ! ");
			} else {
				if (collaborateurDao.findByCIN(cin) != null) {
					erreurs.put("CIN", "CIN déja attribuée à un collaborateur");
				}
			}
		} else {
			erreurs.put("CIN", "Merci de remplir ce champs");
		}
	}

	private void checkLength(String str, String champ) {
		if (str != null && str.trim().length() != 0) {
			if (str.length() < 3) {
				erreurs.put(champ,
						"Ce champs doit contenir au moins 3 caractère ! ");
			}
		} else {
			erreurs.put(champ, "Merci de remplir ce champs");
		}
	}

	@Override
	public List<CollaborateurVo> getCollaborateursOfEntite(EntiteVo entite) {

		List<CollaborateurVo> collaborateursOfEntite = new ArrayList<CollaborateurVo>();
		for (Collaborateur collaborateur : collaborateurDao
				.getCollaborateurOfEntite(converter.populateEntite(entite))) {
			collaborateursOfEntite.add(converter
					.populateCollaborateurVo(collaborateur));
		}
		return collaborateursOfEntite;
	}

	@Override
	public List<CollaborateurVo> getOnlyCollaborateursOfEntite(EntiteVo entite) {
		List<CollaborateurVo> collaborateursOfEntite = new ArrayList<CollaborateurVo>();
		for (Collaborateur collaborateur : collaborateurDao
				.getOnlyCollaborateurOfEntite(converter.populateEntite(entite))) {
			collaborateursOfEntite.add(converter
					.populateCollaborateurVo(collaborateur));
		}
		return collaborateursOfEntite;
	}

	@Override
	public List<CollaborateurVo> getOnlyCollaborateursOfEntite(String nom) {
		List<CollaborateurVo> collaborateursOfEntite = new ArrayList<CollaborateurVo>();
		List<Collaborateur> collaborateurs = collaborateurDao
				.getOnlyCollaborateurOfEntite(entiteDao.getEntiteByNom(nom));
		if (!collaborateurs.isEmpty()) {
			for (Collaborateur collaborateur : collaborateurDao
					.getOnlyCollaborateurOfEntite(entiteDao.getEntiteByNom(nom))) {
				collaborateursOfEntite.add(converter
						.populateCollaborateurVo(collaborateur));
			}
		}

		return collaborateursOfEntite;
	}

	// Methode qui retourne une hashMap ayant comme clé le nom de l'entite et une liste de collaborateurs de cette entite comme valeur
	
	@Override
	public HashMap<String, List<CollaborateurVo>> getMapCollaborateursEntites() {
		HashMap<String, List<CollaborateurVo>> collaborateurPerEntite = new HashMap<String, List<CollaborateurVo>>();
		
		HashMap<String, List<Collaborateur>> daoMap = collaborateurDao.getMapCollaborateursEntites();
		
		for(String key : daoMap.keySet()){
			List<CollaborateurVo> collaborateursList = new ArrayList<CollaborateurVo>();
			List<Collaborateur> daoList = daoMap.get(key);
			for(Collaborateur collaborateur : daoList){
				collaborateursList.add(converter.populateCollaborateurVo(collaborateur));
			}
			collaborateurPerEntite.put(key, collaborateursList);
		}
		
		return collaborateurPerEntite;
	}

	@Override
	public List<FonctionVo> getListFonctions() {
		List<FonctionVo> myList = new ArrayList<FonctionVo>();
		for(Fonction fonction : collaborateurDao.getListFonctions()){
			myList.add(converter.populateFonctionVo(fonction));
		}
		return myList;
	}

	@Override
	public FonctionVo getFunctionByLabel(String value) {
		return converter.populateFonctionVo(collaborateurDao.getFunctionByLabel(value));
	}

}
