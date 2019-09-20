package com.omnishore.oauth;

import java.security.SecureRandom;

import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Person;

public class LinkedInAuth_test {
	
	private static final String CLIENT_ID = "77hchwvxsz00pk";
	
	private static final String CLIENT_SECRET = "VFwL1H7F6fyIWBnC";
	
	private static final String CALLBACK_URI = "http://localhost:8080/Organigramme/Accueil.jsf";
	

	
	private String stateToken;
	
	private LinkedInOAuthService oauthService;
	
	private LinkedInRequestToken requestToken;
	
	public LinkedInAuth_test(){
		
		oauthService = LinkedInOAuthServiceFactory
						.getInstance()
						.createLinkedInOAuthService(CLIENT_ID, CLIENT_SECRET);
		
		requestToken = oauthService.getOAuthRequestToken(CALLBACK_URI);
		
		generateStateToken();
	}
	
	
	public void generateStateToken(){
		
		SecureRandom secure = new SecureRandom();
		
		stateToken = "LinkedIn"+secure.nextInt();
		
	}
	
	public String buildLoginUrl(){
		
		return requestToken.getAuthorizationUrl();
		
	}
	
	public void getUserInfos(String verifierCode){
		
		final LinkedInAccessToken accessToken = oauthService.getOAuthAccessToken(requestToken, verifierCode);
		
		final LinkedInApiClientFactory factory = LinkedInApiClientFactory.newInstance(CLIENT_ID, CLIENT_SECRET);
		final LinkedInApiClient client = factory.createLinkedInApiClient(accessToken);
		
		Person person = client.getProfileForCurrentUser();
		
	}
	
	
}
