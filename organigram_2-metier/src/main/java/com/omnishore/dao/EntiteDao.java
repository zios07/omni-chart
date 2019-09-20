package com.omnishore.dao;

import java.util.List;

import com.omnishore.entities.Entite;

public interface EntiteDao {
	
	public boolean enregistrerEntite(Entite entite);
	public Entite trouverEntite(int idEntite);
	public Entite trouverEntite(String nomEntite);
	public void setResponsableToNull(int responsableID);
	public List<Entite> getEntites();
	public List<Entite> getEntitesSansResponsable();
	public boolean supprimerEntite(int idEntite);
	public boolean verifierNom(String nomEntite);
	public void modifierEntite(Entite entite);
	public List<Entite> getEntiteSansEntiteMere();
	public List<Entite> getSubEntities(Entite entite);
	public List<Entite> getEntitesFilles();
	public Entite getEntiteByNom(String nomEntite);
	public Entite getRaindomEntity();
	public boolean estEntiteMere(int idEntite);
}
