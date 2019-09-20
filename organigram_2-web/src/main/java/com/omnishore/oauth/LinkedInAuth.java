package com.omnishore.oauth;

import java.io.Serializable;
import java.security.SecureRandom;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;


public class LinkedInAuth implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5529152346512296223L;

	public static final String linkedinKey = "77hchwvxsz00pk";
	
	public static final String linkedinSecret = "VFwL1H7F6fyIWBnC";
	
	public static final String callbackUri = "http://localhost:8080/Organigramme/Accueil.jsf";
	
	
	private String stateToken;
	private OAuthService service;
	
	Token requestToken = null;
	
	
	public LinkedInAuth(){

		service = new ServiceBuilder()
				.provider(LinkedInApi.class)
				.apiKey(linkedinKey)
				.apiSecret(linkedinSecret)
				.callback(callbackUri)
				.build();
		
		requestToken = service.getRequestToken();
		generateStateToken();
	}
	
	
	
	public Token getRequestToken(){
		return requestToken;
	}
	
	public String getStateToken(){
		
		return stateToken;
		
	}
	
	/**
	 * Generates a secure state token 
	 */
	private void generateStateToken(){
		
		SecureRandom secure = new SecureRandom();
		stateToken = "LinkedIn"+secure.nextInt();
		
	}
	
	public String buildLoginUrl(){	
		
		generateStateToken();
		return service.getAuthorizationUrl(requestToken);
		
	}
	
	
	
	/** Return LinkedIn profile informations
	 * @param verifier
	 * @return String
	 */
	public String getUserInfoJson(Verifier verifier, Token requestToken){
		
		Token accessToken = service.getAccessToken(requestToken, verifier);
				
		String url = "http://api.linkedin.com/v1/people/~:(first-name,last-name,picture-url)";
		OAuthRequest request = new OAuthRequest(Verb.GET, url);
		request.addHeader("x-li-format", "json");
		service.signRequest(accessToken, request);
		Response response = request.send();
		
		return response.getBody();
	}
	
}
