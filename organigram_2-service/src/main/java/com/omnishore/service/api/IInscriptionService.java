package com.omnishore.service.api;

import java.util.Map;

import com.omnishore.dao.CollaborateurDao;
import com.omnishore.vo.CollaborateurVo;
import com.omnishore.vo.CompteVo;

public interface IInscriptionService {
	
	public static final String REGEX = "[a-zA-Z0-9.-_]+[a-zA-Z0-9]+@[A-Za-z]+.[A-Za-z]{2,3}";

	public boolean checkErreurs(CompteVo cmp);
	public CollaborateurDao getCollaborateurDao();
	public void setCollaborateurDao(CollaborateurDao collaborateurDao);
	public Map<String, String> getErreurs();
	public void setErreurs(Map<String, String> erreurs);
	
	
	public void createVisitorAccount(CompteVo compteVo);
//	public Compte trouverCompte(String login);
//	public boolean verifierPass(Compte compte);
	public CollaborateurVo findByCIN(String CIN);
	public void createCollaborateurAccount(CompteVo compteVo , CollaborateurVo collaborateurVo);

	
}
