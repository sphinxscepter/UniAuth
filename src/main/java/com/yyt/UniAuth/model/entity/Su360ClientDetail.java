package com.yyt.UniAuth.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

public class Su360ClientDetail implements ClientDetails {

	private String appId; 
    private String secret; 
    private Set<String> scope; 
    private Set<String> types; 
    private Set<String> autoApproveScopes; 
    private Set<String> resourceIds; 
    private Map<String, Object> additionalInformation; 
    private Set<String> registeredRedirectUris; 
    private List<GrantedAuthority> authorities; 
    public String getAppId() {
        return appId;
    } 
    public void setAppId(String appId) {
        this.appId = appId;
    } 
    public String getSecret() {
        return secret;
    } 
    public void setSecret(String secret) {
        this.secret = secret;
    } 
    public String getClientId() {
        return appId;
    } 
    public String getClientSecret() {
        return secret;
    } 
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    } 
    /**
     * new SimpleGrantedAuthority(role)
     * @param authorities
     */
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = new ArrayList(authorities);
    } 
    public Set<String> getAutoApproveScopes() {
        return autoApproveScopes;
    } 
    /**
     * values ["authorization_code", "password", "refresh_token", "implicit"]
     * @return
     */
    public Set<String> getAuthorizedGrantTypes() {
        return this.types;
    } 
    public void setAuthorizedGrantTypes(Set<String> types){
        this.types = types;
    } 
    public Integer getAccessTokenValiditySeconds() {
        return 7200;
    } 
    public Integer getRefreshTokenValiditySeconds() {
        return 7200;
    } 
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    } 
    public void addAdditionalInformation(String key, Object value) {
        this.additionalInformation.put(key, value);
    } 
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUris;
    } 
    public void setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris == null?null:new LinkedHashSet(registeredRedirectUris);
    } 
    public Set<String> getResourceIds() {
        return this.resourceIds;
    } 
    /**
     * values ["read", "write"]
     * @return
     */
    public Set<String> getScope() {
        return this.scope;
    } 
    public void setScope(Set<String> scope){
        this.scope = scope;
    } 
    public boolean isAutoApprove(String scope) {
        if(this.autoApproveScopes == null) {
            return false;
        } else {
            Iterator var2 = this.autoApproveScopes.iterator(); 
            String auto;
            do {
                if(!var2.hasNext()) {
                    return false;
                } 
                auto = (String)var2.next();
            } while(!auto.equals("true") && !scope.matches(auto)); 
            return true;
        }
    } 
    public boolean isSecretRequired() {
        return this.secret != null;
    } 
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

}
