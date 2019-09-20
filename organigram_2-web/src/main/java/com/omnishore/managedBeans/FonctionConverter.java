package com.omnishore.managedBeans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.omnishore.service.api.ICollaborateurService;
import com.omnishore.service.impl.CollaborateurServiceImpl;
import com.omnishore.vo.FonctionVo;

@FacesConverter("fonctionConverter")
public class FonctionConverter implements Converter{

	protected ICollaborateurService collaborateurService = new CollaborateurServiceImpl();
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		return collaborateurService.getFunctionByLabel(value);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null || value == "") {
			return null;
		}
		if (!(value instanceof FonctionVo)) {
			throw new ConverterException("La valeur n'est pas valide: " + value);
		}

		String libelleFonction = ((FonctionVo) value).getLibelleFonction();
		return (libelleFonction != null) ? String.valueOf(libelleFonction) : null;
	}

}
