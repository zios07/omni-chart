package com.omnishore.entities;

import java.io.File;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Compte implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8644199035702631049L;
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int idCompte;
	
	@Column(unique=true)
	private String login;
	private String email;
	private String motDePasse;
	private String nom;
	private String prenom;
	@OneToOne
	@JoinColumn(name="ID_COLLABORATEUR")
	private Collaborateur collaborateur;
	@Column(unique=true)
	private String CIN;
	
	
	public Compte(){
		
	}

	public Compte(int idCompte, String login, String email, String motDePasse,
			String nom, String prenom, Collaborateur collaborateur, String cIN) {
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
	
	public Collaborateur getCollaborateur(){
		return collaborateur;
	}
	public void setCollaborateur(Collaborateur collaborateur) {
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
