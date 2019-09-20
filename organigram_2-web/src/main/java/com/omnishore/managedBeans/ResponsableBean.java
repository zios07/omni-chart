package com.omnishore.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.omnishore.service.api.ICollaborateurService;
import com.omnishore.service.api.IEntiteService;
import com.omnishore.service.impl.CollaborateurServiceImpl;
import com.omnishore.service.impl.EntiteServiceImpl;
import com.omnishore.vo.CollaborateurVo;

@ManagedBean
@SessionScoped
public class ResponsableBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -227856600193124967L;
	private String connectedCollaborateurName;
	private List<CollaborateurVo> theColls;
	private ICollaborateurService collaborateurService;

	@PostConstruct
	private void init() {
		collaborateurService  = new CollaborateurServiceImpl();	
	}
	
	public List<CollaborateurVo> getTheColls() {
		return theColls;
	}

	public void setTheColls(List<CollaborateurVo> theColls) {
		this.theColls = theColls;
	}

	public String getConnectedCollaborateurName() {
		return connectedCollaborateurName;
	}
	public void setConnectedCollaborateurName(String connectedCollaborateurName) {
		this.connectedCollaborateurName = connectedCollaborateurName;
	}
	
	public void fillList(){
		if(connectedCollaborateurName!=null){
			theColls = collaborateurService.getOnlyCollaborateursOfEntite(collaborateurService.findCollaborateur(connectedCollaborateurName).getEntite().getNom());
		}
	}
	
}
