package com.omnishore.managedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.omnishore.oauth.GoogleAuth;
import com.omnishore.service.api.IConnectionService;
import com.omnishore.service.impl.ConnectionServiceImpl;
import com.omnishore.vo.CollaborateurVo;
import com.omnishore.vo.CompteVo;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1893802795923896266L;

	private IConnectionService connectionService;
	private CompteVo compteVo;
	private String erreurMessage;
	private CollaborateurVo connectedCollaborateur;
	
	// Google+ Authentication 
	private GoogleAuth helper;
	private String imageURL;

	@PostConstruct
	private void init() {
		connectionService = new ConnectionServiceImpl();
		compteVo = new CompteVo();
		erreurMessage = new String();
		helper = new GoogleAuth();
		
	}

	
	public CollaborateurVo getConnectedCollaborateur() {
		return connectedCollaborateur;
	}


	public void setConnectedCollaborateur(CollaborateurVo connectedCollaborateur) {
		this.connectedCollaborateur = connectedCollaborateur;
	}


	public String getErreurMessage() {
		return erreurMessage;
	}

	public void setErreurMessage(String erreurMessage) {
		this.erreurMessage = erreurMessage;
	}

	public CompteVo getCompteVo() {
		return compteVo;
	}

	public void setCompteVo(CompteVo compteVo) {
		this.compteVo = compteVo;
	}

	public IConnectionService getConnectionService() {
		return connectionService;
	}

	public void setConnectionService(IConnectionService connectionService) {
		this.connectionService = connectionService;
	}
	
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public GoogleAuth getHelper() {
		return helper;
	}

	public void setHelper(GoogleAuth helper) {
		this.helper = helper;
	}

	
	public String afficherPage() {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String btnId = params.get("btnId");
		System.out.println(btnId);
		if (btnId.equals("connexion")) {
			System.out.println("Le bouton cliqué est " + btnId);
			return "Accueil";
		} else {
			System.out.println("Le bouton cliqué est " + btnId);
			return "Inscription1";

		}
	}

	public boolean seConnecter() {
		if (!compteVo.getLogin().equals("") && !compteVo.getMotDePasse().equals("")) {
			if (connectionService.verifierPass(compteVo)) {
				compteVo = connectionService.trouvercompteVo(compteVo.getLogin());
				compteVo.setLoggedIn(true);


				HttpServletRequest req = (HttpServletRequest) (FacesContext
						.getCurrentInstance().getExternalContext().getRequest());
				HttpSession session = req.getSession();
				session.setMaxInactiveInterval(240);
				session.setAttribute("connectionType", "Basic");
				session.setAttribute("connected", "Connected");

				setConnectedCollaborateur(compteVo.getCollaborateur());
				return true;
			} else {
				erreurMessage = "Login ou mot de passe incorrect";
			}
		} else if (compteVo.getLogin().equals("")
				|| compteVo.getLogin().equals(null)) {
			erreurMessage = "Merci de saisir un login et un mot de passe";
		}
		return false;

	}
	
	public String combinedFunction(){
		seConnecter();
		return "Accueil?faces-redirect=true";
	}
	
	public void seDeconnecter() {
		HttpServletRequest req = (HttpServletRequest) (FacesContext
				.getCurrentInstance().getExternalContext().getRequest());
		HttpSession session = req.getSession();
		session.invalidate();
	}
	
	public String getClickedBtn(){
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String btnId = params.get("btnId");
		System.out.println("Kovaciç = "+btnId);
		if (btnId.equals("googleAuth")) {
			return "google";
		} else {
			return "basic";
		}
	}
	
						// *****  OAUTH 2.0 THINGS  ******* //
	
	// Redirect the user to google login page
	
	public void goTo() throws IOException {
		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
		external.redirect(helper.buildLoginUrl());
		external.getSessionMap().put("state", helper.getStateToken());
	}
	
	public void checkAuth(){
		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, String> params = (Map<String, String>) external.getRequestParameterMap();
		String code = params.get("code");
		String state = params.get("state");
		if (code != null && state != null) {

			if (state.equals(external.getSessionMap().get("state"))) {
				System.out.println("State are well compared");
				compteVo.setLoggedIn(true);
			}
		}
	}
	
	
	// Retrieve profil photo url from the JSON response
	
	public String getImageURL() throws IOException {
		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();

		Map<String, String> params = (Map<String, String>) external.getRequestParameterMap();
		String code = params.get("code");
		String state = params.get("state");
		if (code != null && state != null) {

			if (state.equals(external.getSessionMap().get("state"))) {
				String json = helper.getUserInfoJson(code);

				if (json != null) {
					System.out.println(json);
					String[] splitedJson = json.split("\"");
					String imageURL = "";
					int position = -1;

					for (int i = 0; i < splitedJson.length; i++) {
						if (splitedJson[i].equals("picture")) {
							position = i;
							break;
						}
					}
					imageURL = splitedJson[position + 2];
					setImageURL(imageURL);
					compteVo.setLoggedIn(true);
					return imageURL;

				}
			}
		}
		return null;
	}

	
	
}
