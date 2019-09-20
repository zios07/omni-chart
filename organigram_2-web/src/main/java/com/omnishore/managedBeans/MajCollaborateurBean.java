package com.omnishore.managedBeans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.omnishore.service.api.ICollaborateurService;
import com.omnishore.service.impl.CollaborateurServiceImpl;
import com.omnishore.service.impl.EntiteServiceImpl;
import com.omnishore.vo.CollaborateurVo;
import com.omnishore.vo.EntiteVo;
import com.omnishore.vo.FonctionVo;

@ManagedBean
@ViewScoped
public class MajCollaborateurBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1695627561477414200L;
	public EntiteServiceImpl entiteService = new EntiteServiceImpl(); 

	private List<CollaborateurVo> collaborateursList;
	private List<CollaborateurVo> responsablesList;
	private Map<String, String> erreurs;
	private CollaborateurVo collaborateur;
	private List<CollaborateurVo> availableResponsableList;
	private ICollaborateurService collaborateurService;
	private CollaborateurVo selectedCollaborateur;
	private EntiteVo clickedEntite;
	private List<CollaborateurVo> collaborateurOfEntite;
	private String clickedEntityName;
	private List<FonctionVo> listFonctions;
	
	@PostConstruct
	public void init() {

		collaborateurService = new CollaborateurServiceImpl();
		availableResponsableList = collaborateurService
				.getAvailableResponsables();
		collaborateursList = collaborateurService.getCollaborateurs();
		responsablesList = collaborateurService.getResponsables();
		collaborateur = new CollaborateurVo();
		erreurs = collaborateurService.getErreurs();
		listFonctions = collaborateurService.getListFonctions();
		
	}

	// SETTERS && GETTERS	
	
	public List<CollaborateurVo> getCollaborateursList() {
		return collaborateursList;
	}

	public List<FonctionVo> getListFonctions() {
		return listFonctions;
	}

	public void setListFonctions(List<FonctionVo> listFonctions) {
		this.listFonctions = listFonctions;
	}

	public String getClickedEntityName() {
		return clickedEntityName;
	}

	public void setClickedEntityName(String clickedEntityName) {
		this.clickedEntityName = clickedEntityName;
	}

	public List<CollaborateurVo> getCollaborateurOfEntite() {
		return collaborateurOfEntite;
	}

	public void setCollaborateurOfEntite(
			List<CollaborateurVo> collaborateurOfEntite) {
		this.collaborateurOfEntite = collaborateurOfEntite;
	}

	public EntiteVo getClickedEntite() {
		return clickedEntite;
	}

	public void setClickedEntite(EntiteVo clickedEntite) {
		this.clickedEntite = clickedEntite;
	}
	

	public CollaborateurVo getSelectedCollaborateur() {
		return selectedCollaborateur;
	}

	public void setSelectedCollaborateur(CollaborateurVo selectedCollaborateur) {
		this.selectedCollaborateur = selectedCollaborateur;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	public void setCollaborateursList(List<CollaborateurVo> collaborateursList) {
		this.collaborateursList = collaborateursList;
	}

	public List<CollaborateurVo> getResponsablesList() {
		return responsablesList;
	}

	public void setResponsablesList(List<CollaborateurVo> responsablesList) {
		this.responsablesList = responsablesList;
	}

	public CollaborateurVo getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(CollaborateurVo collaborateur) {
		this.collaborateur = collaborateur;
	}

	public List<CollaborateurVo> getAvailableResponsableList() {
		return availableResponsableList;
	}

	public void setAvailableResponsableList(
			List<CollaborateurVo> availableResponsableList) {
		this.availableResponsableList = availableResponsableList;
	}

	public ICollaborateurService getCollaborateurService() {
		return collaborateurService;
	}

	public void setCollaborateurService(
			ICollaborateurService collaborateurService) {
		this.collaborateurService = collaborateurService;
	}

	public String creerCollaborateur() {
		if (collaborateurService.checkErreurs(collaborateur)) {
			
			collaborateurService.createCollaborateur(collaborateur);
			return "MajCollaborateur?faces-redirect=true";
		}
		
		return "AjoutCollaborateur?faces-redirect=true";
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			CollaborateurVo collaborateur = (CollaborateurVo) event.getObject();

			collaborateurService.updateCollaborateur(collaborateur);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		FacesMessage msg = new FacesMessage("Collaborateur Modifié",
				((CollaborateurVo) event.getObject()).getIdCollaborateur() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Modification annulée",
				((CollaborateurVo) event.getObject()).getIdCollaborateur() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void removeCollaborateur(CollaborateurVo collaborateur) {
		boolean isDeleted = collaborateurService.deleteCollaborateur(collaborateur
				.getIdCollaborateur());
		if(isDeleted){
			collaborateursList.remove(collaborateur);
			addMessage("Succès de la suppression", "Collaborateur supprimé avec succès");
		}
		else{
			addMessage("Echec de la suppression", "Collaborateur n'a pas pu être supprimé !");
		}
	}

	public List<CollaborateurVo> getCollaborateursOfEntite(EntiteVo entite) {
		return collaborateurService.getCollaborateursOfEntite(entite);
	}

	public List<CollaborateurVo> getOnlyCollaborateursOfEntite(EntiteVo entite) {
		return collaborateurService.getOnlyCollaborateursOfEntite(entite);
	}
	
	public List<CollaborateurVo> getOnlyCollaborateursOfEntite(String nom){
		return collaborateurService.getOnlyCollaborateursOfEntite(nom);
	}
	
	public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public List<CollaborateurVo> getCollaborateursOfClickedEntite(){
		return collaborateurService.getCollaborateursOfEntite(entiteService.trouverEntite(clickedEntityName));
	}
	
}
