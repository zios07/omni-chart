	package com.omnishore.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.omnishore.dao.CollaborateurDao;
import com.omnishore.dao.CompteDao;
import com.omnishore.dao.impl.CollaborateurDaoImpl;
import com.omnishore.dao.impl.CompteDaoImpl;
import com.omnishore.service.api.IInscriptionService;
import com.omnishore.vo.CollaborateurVo;
import com.omnishore.vo.CompteVo;

public class InscriptionServiceImpl implements IInscriptionService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6288379926765776696L;
	private Map<String, String> erreurs = new HashMap<String, String>();
	private CollaborateurDao collaborateurDao = new CollaborateurDaoImpl();
	private CompteDao compteDao = new CompteDaoImpl();
	private Converter converter = new Converter();
	

	public CompteDao getCompteDao() {
		return compteDao;
	}

	public void setCompteDao(CompteDao compteDao) {
		this.compteDao = compteDao;
	}

	public Converter getConverter() {
		return converter;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	public CollaborateurDao getCollaborateurDao() {
		return collaborateurDao;
	}

	public void setCollaborateurDao(CollaborateurDao collaborateurDao) {
		this.collaborateurDao = collaborateurDao;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	/**
	 * Cette méthode permet de savoir si il y'avait des erreurs lors de la
	 * création du compte
	 * 
	 * @param cmp
	 * @return boolean
	 */
	public boolean checkErreurs(CompteVo cmp) {
		boolean exist = false;

		checkEmail(cmp.getEmail());
		checkLength(cmp.getNom(), "nom");
		checkLength(cmp.getPrenom(), "prenom");
		checkLogin(cmp.getLogin());
		checkPass(cmp.getMotDePasse(), cmp.getConfirmationPWD());
		checkCIN(cmp.getCIN());
		
		if (erreurs.isEmpty()) {
			checkExistence(cmp);
			if (erreurs.isEmpty()) {
				exist = true;
			}
		}

		return exist;
	}

	

	// ------------------------------------------------------------------------------------------------------------------------------//

	// METHODES DE VERIFICATION :

	private void checkLength(String str, String champ) {
		if (str != null && str.trim().length() != 0) {
			if (str.trim().length() < 3) {
				erreurs.put(champ,
						"Ce champ doit contenir au moins 3 caractères");
			}
		}
	}
	
	public void checkCIN(String CIN) {
		if (CIN != null && CIN.trim().length() != 0) {
			if (CIN.trim().length() < 4) {
				erreurs.put("CIN",
						"CIN doit contenir 4 caractères minimum!");
			}
		} else {
			erreurs.put("CIN", "Merci de saisir une CIN ! ");
		}
	}

	public void checkLogin(String login) {
		if (login != null && login.trim().length() != 0) {
			if (login.trim().length() < 4) {
				erreurs.put("login",
						"Le login doit contenir 4 caractères minimum!");
			}
		} else {
			erreurs.put("login", "Merci de saisir un login ! ");
		}
	}

	private void checkEmail(String email) {
		if (email != null && email.trim().length() != 0) {
			if (!email.matches(REGEX)) {
				erreurs.put("email", "Merci de saisir une adresse mail valide");
			}
		} else {
			erreurs.put("email", "Merci de saisir une adresse mail");
		}
	}

	private void checkPass(String pass, String conf) {
		if (pass != null && pass.trim().length() != 0 && conf != null
				&& conf.trim().length() != 0) {
			if (!pass.equals(conf)) {
				erreurs.put("pass",
						"Le mot de passe et la confirmation doivent être identique !");

			}
		} else {
			erreurs.put("pass",
					"Merci de saisir un mot de passe et une confirmation");
		}
	}

	private void checkExistence(CompteVo cmp) {

		if (erreurs.isEmpty()) {
			CompteDaoImpl cdi = new CompteDaoImpl();
			if (cdi.trouverCompte(cmp.getLogin()) != null) {
				erreurs.put("login", "Un compte existe déja avec ce login");
			}
		}
	}

	@Override
	public void createVisitorAccount(CompteVo compteVo) {
		compteDao.createVisitorAccount(converter.populateCompte(compteVo));
	}

	@Override
	public CollaborateurVo findByCIN(String CIN) {
		return converter.populateCollaborateurVo(collaborateurDao.findByCIN(CIN));
	}

	@Override
	public void createCollaborateurAccount(CompteVo compteVo,
			CollaborateurVo collaborateurVo) {

		compteDao.createCollaborateurAccount(converter.populateCompte(compteVo), converter.populateCollaborateur(collaborateurVo));
	}
	
	
	// -- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	

}
