package com.omnishore.managedBeans;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.omnishore.service.api.IInscriptionService;
import com.omnishore.service.impl.InscriptionServiceImpl;
import com.omnishore.vo.CollaborateurVo;
import com.omnishore.vo.CompteVo;

@ManagedBean
@RequestScoped	
public class InscriptionBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5273967030726224578L;
	private CompteVo compte;
	private IInscriptionService serviceInscription;
	private Map<String,String> erreurs;
	
	@PostConstruct
	private void init() {
		
		serviceInscription = new InscriptionServiceImpl();
		compte = new CompteVo();
		erreurs = serviceInscription.getErreurs();

	}
	
	
	
	public IInscriptionService getServiceInscription() {
		return serviceInscription;
	}


	public void setServiceInscription(IInscriptionService serviceInscription) {
		this.serviceInscription = serviceInscription;
	}


	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	public CompteVo getCompte() {
		return compte;
	}


	public void setCompte(CompteVo compte) {
		this.compte = compte;
	}


	public boolean creerCompte(){
		
		boolean checked = serviceInscription.checkErreurs(compte);
		
		if(checked){
			CollaborateurVo collaborateur = serviceInscription.findByCIN(compte.getCIN());
			if(collaborateur==null){
				serviceInscription.createVisitorAccount(compte);
			}
			else{
				serviceInscription.createCollaborateurAccount(compte, collaborateur);
			}
		}
		else{
			
			System.out.println("There is an error !");
		}
		return checked;
	}
	
	public String combinedFunctions(){
		boolean sansErreur = creerCompte();
		if(sansErreur){
			return "Accueil?faces-redirect=true";
		}
		return "Inscription1?faces-redirect=true";
	}
}










