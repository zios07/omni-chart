package com.omnishore.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Fonction {
	
	
	@Id
	private int codeFonction;
	private String libelleFonction;
	
	public Fonction(int codeFonction, String libelleFonction) {
		super();
		this.codeFonction = codeFonction;
		this.libelleFonction = libelleFonction;
	}
	
	public Fonction(){}
	
	
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
		result = prime * result
				+ ((libelleFonction == null) ? 0 : libelleFonction.hashCode());
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
		Fonction other = (Fonction) obj;
		if (codeFonction != other.codeFonction)
			return false;
		if (libelleFonction == null) {
			if (other.libelleFonction != null)
				return false;
		} else if (!libelleFonction.equals(other.libelleFonction))
			return false;
		return true;
	}
	
}
