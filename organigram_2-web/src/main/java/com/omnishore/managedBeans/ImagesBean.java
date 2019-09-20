package com.omnishore.managedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.omnishore.service.api.ICollaborateurService;
import com.omnishore.service.impl.CollaborateurServiceImpl;

@ManagedBean
@RequestScoped
public class ImagesBean implements Serializable{

	private static final long serialVersionUID = 4385368345730170238L;
	private ICollaborateurService collaborateurService;
	
	@PostConstruct
	private void init() {
		collaborateurService = new CollaborateurServiceImpl(); 
	}
	
    public StreamedContent getImage(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            return collaborateurService.findCollaborateur(Integer.parseInt(id)).getPhoto();
        }
    }

}
