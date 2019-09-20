package com.omnishore.managedBeans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.omnishore.service.api.ICollaborateurService;
import com.omnishore.service.impl.CollaborateurServiceImpl;
import com.omnishore.vo.CollaborateurVo;

@FacesConverter("collaborateurConverter")
public class CollaborateurConverter implements Converter {

	public ICollaborateurService collaborateurService = new CollaborateurServiceImpl();
	
	@Override
	public CollaborateurVo getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String[] values = value.split(" ");
		return collaborateurService.findCollaborateur(values[0]);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {

		if (value == null || value == "") {
			return null;
		}
		if (!(value instanceof CollaborateurVo)) {
			throw new ConverterException("La valeur n'est pas valide: " + value);
		}

		String nomCollaborateur = ((CollaborateurVo) value).getNom();
		return (nomCollaborateur != null) ? String.valueOf(nomCollaborateur) : null;
	}

}
