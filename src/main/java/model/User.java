package edu.unl.cc.jbrew.vitalnet.model;

public class User {
    private String username;
    private String rol;

    public User(String username, String rol) {
        this.username = username;
        this.rol = rol;
    }

    public String getUsername() { return username; }
    public String getRol() { return rol; }
}
