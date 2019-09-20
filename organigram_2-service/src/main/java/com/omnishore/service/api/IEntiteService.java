package com.omnishore.service.api;

import java.util.List;

import com.omnishore.entities.Entite;
import com.omnishore.vo.EntiteVo;

public interface IEntiteService {
	
	public boolean enregistrerEntite(EntiteVo entiteVo);
	public EntiteVo trouverEntite(int idEntite);
	public EntiteVo trouverEntite(String nomEntite);
	public void setResponsableToNull(int responsableID);
	public List<EntiteVo> getEntites();
	public List<EntiteVo> getEntitesSansResponsable();
	public boolean supprimerEntite(int idEntite);
	public boolean verifierNom(String nomEntite);
	public void modifierEntite(EntiteVo entiteVo);
	public List<EntiteVo> getEntiteSansEntiteMere();
	public List<EntiteVo> getSubEntities(EntiteVo entite);
	public List<EntiteVo> getEntitesFille();
	public EntiteVo getRaindomEntity();
}
