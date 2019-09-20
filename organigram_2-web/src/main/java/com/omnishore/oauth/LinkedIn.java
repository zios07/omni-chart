package com.omnishore.oauth;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class LinkedIn {

	public static void main(String[] args) throws IOException {

		final String linkedinKey = "77hchwvxsz00pk";
		final String linkedinSecret = "VFwL1H7F6fyIWBnC";
		final String callbackUri = "http://localhost:8080/Organigramme/Accueil.jsf";
		
		Scanner s = null;
		
		try {

			// Create a state to send with request
			SecureRandom secure = new SecureRandom();
			String state = "LinkedIn" + secure.nextInt();

			OAuthService service = new ServiceBuilder().provider(LinkedInApi.class).apiKey(linkedinKey)
					.apiSecret(linkedinSecret).callback(callbackUri).build();

			Token requestToken = service.getRequestToken();

			System.out.println(
					"Copy link below and retrive verifier code : \n " + service.getAuthorizationUrl(requestToken));

			s = new Scanner(System.in);

			Verifier verifier = new Verifier(s.nextLine());

			Token accessToken = service.getAccessToken(requestToken, verifier);

			// Get request to retrieve profil informations

			String url = "http://api.linkedin.com/v1/people/~:(first-name,last-name,picture-url)";
			OAuthRequest request = new OAuthRequest(Verb.GET, url);
			request.addHeader("x-li-format", "json");
			request.addQuerystringParameter("state", state);
			service.signRequest(accessToken, request);
			Response response = request.send();

			System.out.println(
					"My state : " + state + " - returned state - " + request.getQueryStringParams().get("state"));
			System.out.println(" -  - -- - - - - - - - - - -");
			System.out.println(response.getHeaders() + "\n");
			System.out.println(response.getBody());

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			s.close();
		}

	}

}
