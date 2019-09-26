package com.colsubsidio.salud.auth.models;

import java.util.LinkedHashMap;

/**
 *
 * @author Usuario
 */
public class Service {

    private int serv_id;
    private String serv_name;
    private LinkedHashMap<String, String> serv_headers;
    private String serv_url;
    private String serv_apikey;
    private String serv_authorization;
    private String serv_parameters;
    private String serv_method;
    private String client_secret;
    private String client_id;


    /**
     *
     * @return
     */
    public int getServ_id() {
        return serv_id;
    }

    /**
     *
     * @param serv_id
     */
    public void setServ_id(int serv_id) {
        this.serv_id = serv_id;
    }

    /**
     *
     * @return
     */
    public String getServ_name() {
        return serv_name;
    }

    /**
     *
     * @param serv_name
     */
    public void setServ_name(String serv_name) {
        this.serv_name = serv_name;
    }

    /**
     *
     * @return
     */
    public String getServ_url() {
        return serv_url;
    }

    /**
     *
     * @param serv_url
     */
    public void setServ_url(String serv_url) {
        this.serv_url = serv_url;
    }

    /**
     *
     * @return
     */
    public String getServ_apikey() {
        return serv_apikey;
    }

    /**
     *
     * @param serv_apikey
     */
    public void setServ_apikey(String serv_apikey) {
        this.serv_apikey = serv_apikey;
    }

    /**
     *
     * @return
     */
    public String getServ_authorization() {
        return serv_authorization;
    }

    /**
     *
     * @param serv_authorization
     */
    public void setServ_authorization(String serv_authorization) {
        this.serv_authorization = serv_authorization;
    }

    /**
     *
     * @return
     */
    public String getServ_parameters() {
        return serv_parameters;
    }

    /**
     *
     * @param serv_parameters
     */
    public void setServ_parameters(String serv_parameters) {
        this.serv_parameters = serv_parameters;
    }

    /**
     *
     * @return
     */
    public String getServ_method() {
        return serv_method;
    }

    /**
     *
     * @param serv_method
     */
    public void setServ_method(String serv_method) {
        this.serv_method = serv_method;
    }

    /**
     *
     * @return
     */
    public String getClient_secret() {
        return client_secret;
    }

    /**
     *
     * @param client_secret
     */
    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    /**
     *
     * @return
     */
    public String getClient_id() {
        return client_id;
    }

    /**
     *
     * @param client_id
     */
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public LinkedHashMap<String, String> getServ_headers() {
        return serv_headers;
    }

    public void setServ_headers(LinkedHashMap<String, String> serv_headers) {
        this.serv_headers = serv_headers;
    }

    @Override
    public String toString() {
        return "Services{" + "serv_id=" + serv_id + ", serv_name=" + serv_name + ", serv_url=" + serv_url + ", serv_apikey=" + serv_apikey + ", serv_authorization=" + serv_authorization + ", serv_parameters=" + serv_parameters + ", serv_method=" + serv_method + ", client_secret=" + client_secret + ", client_id=" + client_id + '}';
    }

}
