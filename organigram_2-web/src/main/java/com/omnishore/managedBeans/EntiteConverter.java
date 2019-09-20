package com.omnishore.managedBeans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.omnishore.service.api.IEntiteService;
import com.omnishore.service.impl.EntiteServiceImpl;
import com.omnishore.vo.EntiteVo;

@FacesConverter("entiteConverter")
public class EntiteConverter implements Converter {

	public IEntiteService entiteService = new EntiteServiceImpl();

	@Override
	public EntiteVo getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		return entiteService.trouverEntite(value);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null || value == "") {
			return null;
		}
		if (!(value instanceof EntiteVo)) {
			throw new ConverterException("La valeur n'est pas valide: " + value);
		}

		String nomEntite = ((EntiteVo) value).getNom();
		return (nomEntite != null) ? String.valueOf(nomEntite) : null;
	}

}
