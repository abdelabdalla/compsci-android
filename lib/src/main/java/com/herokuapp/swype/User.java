package com.herokuapp.swype;


/**
 *@author Abdel-Rahim Abdalla
 *@version 1.0
 *@since 1.0
 */
public class User {

    private String id;
    private String pass;
    private String email;

    /**
     * Used by {@link com.google.gson.Gson}
     */
    public User() {
    }

    /**
     * Constructor for logging in
     *
     * @param id Account username
     * @param pass Account password
     */
    public User(String id, String pass) {
        this.id = id;
        this.pass = pass;
    }

    /**
     * Constructor for registering and general use
     * @param id Account username
     * @param pass Account password
     * @param email Account email
     */
    public User(String id, String pass, String email) {
        this.id = id;
        this.pass = pass;
        this.email = email;
    }

    /**
     *
     * @return Get username
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id Set username value for object
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return Get password
     */
    public String getPass() {
        return pass;
    }

    /**
     *
     * @param pass Set password value for object
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     *
     * @return Get email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email Set email value for object
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
