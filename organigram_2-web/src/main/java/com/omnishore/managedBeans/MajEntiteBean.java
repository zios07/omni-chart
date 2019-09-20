package com.omnishore.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnishore.service.api.IEntiteService;
import com.omnishore.service.impl.CollaborateurServiceImpl;
import com.omnishore.service.impl.EntiteServiceImpl;
import com.omnishore.vo.CollaborateurVo;
import com.omnishore.vo.EntiteVo;

@ManagedBean
@RequestScoped
public class MajEntiteBean implements Serializable {

	private static final long serialVersionUID = -1695627561477414200L;
	private CollaborateurServiceImpl collaborateurService = new CollaborateurServiceImpl();
	private List<EntiteVo> listeEntites;
	private List<EntiteVo> listeEntitesSansResponsable;
	private EntiteVo entiteVo;
	private IEntiteService entiteService;
	private List<EntiteVo> listEntitesMere;
	private List<EntiteVo> listEntitesFilles;
	private List<CollaborateurVo> collaborateurOfEntite;

	// Json

	private String jsonEntitesList;
	private String jsonEntitesMereList;
	private String jsonEntitesFilleList;

	@PostConstruct
	public void init() {
		entiteService = new EntiteServiceImpl();
		entiteVo = new EntiteVo();
		listeEntitesSansResponsable = entiteService.getEntitesSansResponsable();
		listeEntites = entiteService.getEntites();
		listEntitesMere = entiteService.getEntiteSansEntiteMere();
		listEntitesFilles = entiteService.getEntitesFille();
		

		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonEntitesList = mapper.writeValueAsString(listeEntites);
			jsonEntitesMereList = mapper.writeValueAsString(listEntitesMere);
			jsonEntitesFilleList = mapper.writeValueAsString(listEntitesFilles);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Getters && Setters && Methods

	public CollaborateurServiceImpl getCollaborateurService() {
		return collaborateurService;
	}

	public void setCollaborateurService(CollaborateurServiceImpl collaborateurService) {
		this.collaborateurService = collaborateurService;
	}

	public List<CollaborateurVo> getCollaborateurOfEntite() {
		return collaborateurOfEntite;
	}

	public void setCollaborateurOfEntite(List<CollaborateurVo> collaborateurOfEntite) {
		this.collaborateurOfEntite = collaborateurOfEntite;
	}

	public List<EntiteVo> getListEntitesFilles() {
		return listEntitesFilles;
	}

	public String getJsonEntitesFilleList() {
		return jsonEntitesFilleList;
	}

	public void setJsonEntitesFilleList(String jsonEntitesFilleList) {
		this.jsonEntitesFilleList = jsonEntitesFilleList;
	}

	public void setListEntitesFilles(List<EntiteVo> listEntitesFilles) {
		this.listEntitesFilles = listEntitesFilles;
	}

	public String getJsonEntitesList() {
		return jsonEntitesList;
	}

	public void setJsonEntitesList(String jsonEntitesList) {
		this.jsonEntitesList = jsonEntitesList;
	}

	public String getJsonEntitesMereList() {
		return jsonEntitesMereList;
	}

	public void setJsonEntitesMereList(String jsonEntitesMereList) {
		this.jsonEntitesMereList = jsonEntitesMereList;
	}

	public List<EntiteVo> getListEntitesMere() {
		return listEntitesMere;
	}

	public void setListEntitesMere(List<EntiteVo> listEntitesMere) {
		this.listEntitesMere = listEntitesMere;
	}

	public List<EntiteVo> getListeEntites() {
		return listeEntites;
	}

	public void setListeEntites(List<EntiteVo> listeEntites) {
		this.listeEntites = listeEntites;
	}

	public List<EntiteVo> getListeEntitesSansResponsable() {
		return listeEntitesSansResponsable;
	}

	public void setListeEntitesSansResponsable(List<EntiteVo> listeEntitesSansResponsable) {
		this.listeEntitesSansResponsable = listeEntitesSansResponsable;
	}

	public IEntiteService getEntiteService() {
		return entiteService;
	}

	public void setEntiteService(IEntiteService entiteService) {
		this.entiteService = entiteService;
	}

	public EntiteVo getEntiteVo() {
		return entiteVo;
	}

	public void setEntiteVo(EntiteVo entiteVo) {
		this.entiteVo = entiteVo;
	}

	/**
	 * Methode permettant d'ajouter une entite dans la base et dans la liste des entites
	 * 
	 * @return String
	 */
	public String creerEntite() {

		boolean saved = entiteService.enregistrerEntite(entiteVo);
		if (saved == true) {
			listeEntites.add(entiteVo);
		}
		return "MajEntite?faces-redirect=true";
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			EntiteVo entite = (EntiteVo) event.getObject();

			entiteService.modifierEntite(entite);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Entité modifiée", ((EntiteVo) event.getObject()).getIdEntite() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Modification annulée", ((EntiteVo) event.getObject()).getIdEntite() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void supprimerEntite(EntiteVo entite) {
		boolean deleted = entiteService.supprimerEntite(entite.getIdEntite());
		if(deleted){
			listeEntites.remove(entite);
			FacesMessage msg = new FacesMessage("Succès de la suppression");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			FacesMessage msg = new FacesMessage("Echec de la suppression");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String getTrimedNom(EntiteVo entite) {
		return entite.getNom().replace(" ", "").replace(".", "");
	}

	public List<EntiteVo> getEntiteSansEntiteMere() {
		return entiteService.getEntiteSansEntiteMere();
	}

	public List<EntiteVo> getSubEntities(EntiteVo entite) {
		return entiteService.getSubEntities(entite);
	}

}
