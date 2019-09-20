package com.omnishore.dao;

import com.omnishore.entities.Collaborateur;
import com.omnishore.entities.Compte;

public interface CompteDao {
	
	public void createVisitorAccount(Compte compte);
	public Compte trouverCompte(String login);
	public boolean verifierPass(Compte compte);
//	public boolean estAdmin(String login);
	public void createCollaborateurAccount(Compte compte , Collaborateur collaborateur);
}
