package com.omnishore.vo;

import java.util.ArrayList;
import java.util.Collection;

public class FonctionVo {
	
	private int codeFonction;
	private String libelleFonction;
	private Collection<CollaborateurVo> collaborateurs = new ArrayList<CollaborateurVo>();
	
	
	
	
	public FonctionVo(int codeFonction, String libelleFonction) {
		this.codeFonction = codeFonction;
		this.libelleFonction = libelleFonction;
	}
	
	public Collection<CollaborateurVo> getCollaborateurs() {
		return collaborateurs;
	}
	public void setCollaborateurs(Collection<CollaborateurVo> collaborateurs) {
		this.collaborateurs = collaborateurs;
	}
	public int getCodeFonction() {
		return codeFonction;
	}
	public void setCodeFonction(int codeFonction) {
		this.codeFonction = codeFonction;
	}
	public String getLibelleFonction() {
		return libelleFonction;
	}
	public void setLibelleFonction(String libelleFonction) {
		this.libelleFonction = libelleFonction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codeFonction;
		result = prime * result + ((collaborateurs == null) ? 0 : collaborateurs.hashCode());
		result = prime * result + ((libelleFonction == null) ? 0 : libelleFonction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FonctionVo other = (FonctionVo) obj;
		if (codeFonction != other.codeFonction)
			return false;
		if (collaborateurs == null) {
			if (other.collaborateurs != null)
				return false;
		} else if (!collaborateurs.equals(other.collaborateurs))
			return false;
		if (libelleFonction == null) {
			if (other.libelleFonction != null)
				return false;
		} else if (!libelleFonction.equals(other.libelleFonction))
			return false;
		return true;
	}
	
}
