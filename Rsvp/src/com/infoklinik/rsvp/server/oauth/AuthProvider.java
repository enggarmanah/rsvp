package com.infoklinik.rsvp.server.oauth;

public class AuthProvider {
	private String authProviderName;
	private int authProvider;
	private String appId;
	private String appSecret;
	private String redirectUrl;
	private String scope;

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthProviderName() {
		return authProviderName;
	}

	public void setAuthProviderName(String authProviderName) {
		this.authProviderName = authProviderName;
	}

	public int getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(int authProvider) {
		this.authProvider = authProvider;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}
