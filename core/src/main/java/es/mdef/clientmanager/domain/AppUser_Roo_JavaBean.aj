// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.mdef.clientmanager.domain;

privileged aspect AppUser_Roo_JavaBean {
    
    public String AppUser.getLogin() {
        return this.login;
    }
    
    public void AppUser.setLogin(String login) {
        this.login = login;
    }
    
    public Password AppUser.getPassword() {
        return this.password;
    }
    
    public void AppUser.setPassword(Password password) {
        this.password = password;
    }
    
    public String AppUser.getUserName() {
        return this.userName;
    }
    
    public void AppUser.setUserName(String userName) {
        this.userName = userName;
    }
    
    public String AppUser.getEmail() {
        return this.email;
    }
    
    public void AppUser.setEmail(String email) {
        this.email = email;
    }
    
    public Integer AppUser.getVersion() {
        return this.version;
    }
    
    public void AppUser.setVersion(Integer version) {
        this.version = version;
    }
    
    public Long AppUser.getId() {
        return this.id;
    }
    
    public void AppUser.setId(Long id) {
        this.id = id;
    }
    
}
