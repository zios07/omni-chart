package com.omnishore.managedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.json.JSONObject;
import org.scribe.model.Token;
import org.scribe.model.Verifier;

import com.omnishore.oauth.GoogleAuth;
import com.omnishore.oauth.LinkedInAuth;
import com.omnishore.objects.User;

@ManagedBean
@RequestScoped
public class AuthenticationBean implements Serializable {

	private static final long serialVersionUID = -9061114950450687199L;
	private GoogleAuth helper;
	private LinkedInAuth linkedInHelper;
	private String strToInject = "";
	private User authenticatedUser;
	
	ExternalContext external;
	HttpServletRequest req;
	HttpSession session;

	@PostConstruct
	public void init() {
		
		external = FacesContext.getCurrentInstance().getExternalContext();
		
		req = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext()
				.getRequest());
		
		session = req.getSession();

		authenticatedUser = new User();
		helper = new GoogleAuth();
		linkedInHelper = new LinkedInAuth();
		
		try {
			if (checkAuthentication().equals("Google")) {
				getUserInfoGoogle();
				
			} else if (checkAuthentication().equals("LinkedIn")) {
				getUserInfoLinkedIn();
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public String getStrToInject() {
		return strToInject;
	}

	public void setStrToInject(String strToInject) {
		this.strToInject = strToInject;
	}

	public User getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(User authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	// Google oauth logic

	public GoogleAuth getHelper() {
		return helper;
	}

	public void setHelper(GoogleAuth helper) {
		this.helper = helper;
	}

	public void goToGoogle() throws IOException {
		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
		external.redirect(helper.buildLoginUrl());
		external.getSessionMap().put("state", helper.getStateToken());
	}

	public User getUserInfoGoogle() throws IOException {
		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();

		Map<String, String> params = (Map<String, String>) external.getRequestParameterMap();
		String code = params.get("code");
		String state = params.get("state");
		if (code != null && state != null) {

			if (state.equals(external.getSessionMap().get("state"))) {
				String json = helper.getUserInfoJson(code);

				System.out.println(json);

				JSONObject jsonObject = new JSONObject(json);

				// Filling the authenticatedUser from response body ..

				session.setAttribute("fullName",jsonObject.getString("given_name")+" "+jsonObject.getString("family_name"));
				session.setAttribute("photoUrl",jsonObject.getString("picture"));
				session.setAttribute("connected", "-");
				session.setAttribute("connectionType", "Google");
				
				return authenticatedUser;
			}
		}
		return null;
	}

	// LinkedIn oauth logic

	public LinkedInAuth getLinkedInHelper() {
		return linkedInHelper;
	}

	public void setLinkedInHelper(LinkedInAuth linkedInHelper) {
		this.linkedInHelper = linkedInHelper;
	}

	public void goToLinkedIn() throws IOException {
		
		session.setAttribute("requestToken", linkedInHelper.getRequestToken());
		external.redirect(linkedInHelper.buildLoginUrl());
		external.getSessionMap().put("state", linkedInHelper.getStateToken());
	}

	public User getUserInfoLinkedIn() {

		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();

		Map<String, String> params = (Map<String, String>) external.getRequestParameterMap();

		if (params.get("oauth_verifier") != null) {

			Verifier verifier = new Verifier(params.get("oauth_verifier"));

			System.out.println(verifier.getValue());
			HttpServletRequest req = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext()
					.getRequest());
			HttpSession session = req.getSession();
			JSONObject jsonObject = new JSONObject(linkedInHelper.getUserInfoJson(verifier, (Token)session.getAttribute("requestToken")));
			
			// Sending profil infos throught session y ..

			session.setAttribute("fullName",jsonObject.getString("firstName")+" "+jsonObject.getString("lastName"));
			session.setAttribute("photoUrl",jsonObject.getString("pictureUrl"));
			session.setAttribute("connected", "-");
			session.setAttribute("connectionType", "LinkedIn");
			
		}
		
		return authenticatedUser;
	}

	// Checking authentication type (Google or linkedIn)

	public String checkAuthentication() {
		
		String connectionType = null;
		Map<String, String> params = (Map<String, String>) external.getRequestParameterMap();
		String state = params.get("state");
		if (state != null) {

			if (state.equals(external.getSessionMap().get("state"))) {
				if (state.startsWith("google"))
					connectionType = "Google";

			}
		} else {
			connectionType = "LinkedIn";
		}
		return connectionType;
	}
}
