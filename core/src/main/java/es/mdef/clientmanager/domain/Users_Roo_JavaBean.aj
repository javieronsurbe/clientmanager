// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.mdef.clientmanager.domain;

privileged aspect Users_Roo_JavaBean {
    
    public String Users.getIdentifier() {
        return this.identifier;
    }
    
    public void Users.setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public Password Users.getPassword() {
        return this.password;
    }
    
    public void Users.setPassword(Password password) {
        this.password = password;
    }
    
    public UserType Users.getUserType() {
        return this.userType;
    }
    
    public void Users.setUserType(UserType userType) {
        this.userType = userType;
    }
    
    public WebAccount Users.getWebAccount() {
        return this.webAccount;
    }
    
    public void Users.setWebAccount(WebAccount webAccount) {
        this.webAccount = webAccount;
    }
    
    public Long Users.getId() {
        return this.id;
    }
    
    public void Users.setId(Long id) {
        this.id = id;
    }
    
    public Integer Users.getVersion() {
        return this.version;
    }
    
    public void Users.setVersion(Integer version) {
        this.version = version;
    }
    
}
