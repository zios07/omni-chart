package com.omnishore.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.omnishore.service.api.ICollaborateurService;
import com.omnishore.service.impl.CollaborateurServiceImpl;
import com.omnishore.service.impl.EntiteServiceImpl;
import com.omnishore.vo.CollaborateurVo;

@ManagedBean
@SessionScoped
public class OrganigrammeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9167434863958567799L;
	
	private ICollaborateurService collaborateurService;
	private EntiteServiceImpl entiteService; 
	private String clickedEntityName;

	@PostConstruct
	public void init() {
		collaborateurService = new CollaborateurServiceImpl();
		entiteService = new EntiteServiceImpl();
		clickedEntityName = entiteService.getRaindomEntity().getNom();
	}
	
	public String getClickedEntityName() {
		return clickedEntityName;
	}

	public void setClickedEntityName(String clickedEntityName) {
		this.clickedEntityName = clickedEntityName;
	}

	public List<CollaborateurVo> getCollaborateursOfClickedEntite(){
		return collaborateurService.getCollaborateursOfEntite(entiteService.trouverEntite(clickedEntityName));
	}

}
