// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.mdef.clientmanager.domain;

import java.util.Date;

privileged aspect WebDomain_Roo_JavaBean {
    
    public String WebDomain.getDomainName() {
        return this.domainName;
    }
    
    public void WebDomain.setDomainName(String domainName) {
        this.domainName = domainName;
    }
    
    public String WebDomain.getAdminUrl() {
        return this.adminUrl;
    }
    
    public void WebDomain.setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }
    
    public Boolean WebDomain.getManagedDomain() {
        return this.managedDomain;
    }
    
    public void WebDomain.setManagedDomain(Boolean managedDomain) {
        this.managedDomain = managedDomain;
    }
    
    public String WebDomain.getTemplate() {
        return this.template;
    }
    
    public void WebDomain.setTemplate(String template) {
        this.template = template;
    }
    
    public Hosting WebDomain.getHosting() {
        return this.hosting;
    }
    
    public void WebDomain.setHosting(Hosting hosting) {
        this.hosting = hosting;
    }
    
    public Date WebDomain.getExpirationDate() {
        return this.expirationDate;
    }
    
    public void WebDomain.setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public String WebDomain.getDatabase() {
        return this.database;
    }
    
    public void WebDomain.setDatabase(String database) {
        this.database = database;
    }
    
    public WebAccount WebDomain.getWebAccount() {
        return this.webAccount;
    }
    
    public void WebDomain.setWebAccount(WebAccount webAccount) {
        this.webAccount = webAccount;
    }
    
    public Long WebDomain.getId() {
        return this.id;
    }
    
    public void WebDomain.setId(Long id) {
        this.id = id;
    }
    
    public Integer WebDomain.getVersion() {
        return this.version;
    }
    
    public void WebDomain.setVersion(Integer version) {
        this.version = version;
    }
    
}