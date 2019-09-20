package com.omnishore.vo;

import java.io.Serializable;

public class CompteVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8644199035702631049L;

	private int idCompte;

	private String login;
	private String email;
	private String motDePasse;
	private String nom;
	private String prenom;

	private CollaborateurVo collaborateur;
	private String confirmationPWD;
	private boolean loggedIn = false;

	private String CIN;

	public CompteVo() {

	}

	public CompteVo(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public CompteVo(int idCompte, String login, String email,
			String motDePasse, String nom, String prenom,
			CollaborateurVo collaborateur, String cIN) {
		super();
		this.idCompte = idCompte;
		this.login = login;
		this.email = email;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
		this.collaborateur = collaborateur;
		CIN = cIN;
	}

	public int getIdCompte() {
		return idCompte;
	}

	public void setIdCompte(int idCompte) {
		this.idCompte = idCompte;
	}

	public String getCIN() {
		return CIN;
	}

	public void setCIN(String cIN) {
		CIN = cIN;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getConfirmationPWD() {
		return confirmationPWD;
	}

	public void setConfirmationPWD(String confirmationPWD) {
		this.confirmationPWD = confirmationPWD;
	}

	public CollaborateurVo getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(CollaborateurVo collaborateur) {
		this.collaborateur = collaborateur;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

}
