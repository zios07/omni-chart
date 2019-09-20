package com.omnishore.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.omnishore.dao.CollaborateurDao;
import com.omnishore.dao.EntiteDao;
import com.omnishore.dao.impl.CollaborateurDaoImpl;
import com.omnishore.dao.impl.EntiteDaoImpl;
import com.omnishore.entities.Collaborateur;
import com.omnishore.entities.Entite;
import com.omnishore.service.api.IEntiteService;
import com.omnishore.vo.EntiteVo;

public class EntiteServiceImpl implements IEntiteService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2919413863499930336L;
	private Converter converter = new Converter();
	private EntiteDao entiteDao = new EntiteDaoImpl();
	private CollaborateurDao collaborateurDao = new CollaborateurDaoImpl();
	
	@Override
	public boolean enregistrerEntite(EntiteVo entiteVo) {
		Entite entite = converter.populateEntite(entiteVo);
		entite.setResponsable(converter.populateCollaborateur(entiteVo.getResponsable()));
		return entiteDao.enregistrerEntite(entite);
	}

	@Override
	public EntiteVo trouverEntite(int idEntite) {
		return converter.populateEntiteVo(entiteDao.trouverEntite(idEntite));
	}

	@Override
	public EntiteVo trouverEntite(String nomEntite) {
		return converter.populateEntiteVo(entiteDao.trouverEntite(nomEntite));
	}

	@Override
	public void setResponsableToNull(int responsableID) {
		entiteDao.setResponsableToNull(responsableID);
	}

	@Override
	public List<EntiteVo> getEntites() {
		List<EntiteVo> entitesVo = new ArrayList<EntiteVo>();
		for(Entite entite: entiteDao.getEntites()){
			EntiteVo entiteVo = converter.populateEntiteVo(entite);
			entiteVo.setResponsable(converter.populateCollaborateurVo(entite.getResponsable()));
			entitesVo.add(entiteVo);
		}
		return entitesVo;
	}

	@Override
	public List<EntiteVo> getEntitesSansResponsable() {
		List<EntiteVo> entitesVoSansResponsable = new ArrayList<EntiteVo>();
		for(Entite entite: entiteDao.getEntitesSansResponsable()){
			entitesVoSansResponsable.add(converter.populateEntiteVo(entite));
		}
		return entitesVoSansResponsable;
	}

	@Override
	public boolean supprimerEntite(int idEntite) {
		return entiteDao.supprimerEntite(idEntite);
	}

	@Override
	public boolean verifierNom(String nomEntite) {
		return entiteDao.verifierNom(nomEntite);
	}

	@Override
	public void modifierEntite(EntiteVo entiteVo) {
		Entite entite = converter.populateEntite(entiteVo);
		Collaborateur resp = entite.getResponsable();

		// IF resp != null ==>Set  id_entite = null To OLD responsable
		
		if(resp!=null){
			collaborateurDao.setEntiteToNull(entite.getIdEntite());
			entite.getResponsable().setEntite(entite);
			
			// null to idResponsable with the idEntite = entite.getId() + update collaborateur
			collaborateurDao.updateCollaborateur(entite.getResponsable());
		}
		else{
			collaborateurDao.setEntiteToNull(entite.getIdEntite());
			entiteDao.modifierEntite(entite);

		}
		
	}

	@Override
	public List<EntiteVo> getEntiteSansEntiteMere() {
		List<EntiteVo> entitesVoSansEntiteMere = new ArrayList<EntiteVo>();
		for(Entite entite: entiteDao.getEntiteSansEntiteMere()){
			entitesVoSansEntiteMere.add(converter.populateEntiteVo(entite));
		}
		return entitesVoSansEntiteMere;
	}

	@Override
	public List<EntiteVo> getSubEntities(EntiteVo entite) {
		List<EntiteVo> subEntities = new ArrayList<EntiteVo>();
		for(Entite e: entiteDao.getSubEntities(converter.populateEntite(entite))){
			subEntities.add(converter.populateEntiteVo(e));
		}
		return subEntities;
	}

	@Override
	public List<EntiteVo> getEntitesFille() {
		List<EntiteVo> entitesFilles = new ArrayList<EntiteVo>();
		for(Entite entite: entiteDao.getEntitesFilles()){
			entitesFilles.add(converter.populateEntiteVo(entite));
		}
		return entitesFilles;
	}

	@Override
	public EntiteVo getRaindomEntity() {
		return converter.populateEntiteVo(entiteDao.getRaindomEntity());
	}

}
