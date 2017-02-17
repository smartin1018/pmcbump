package com.shepherdjerred.pmcbump.config;

public interface Config {

    public String getUsername();
    public String getPassword();
    public String getMemberId();
    public String getResourceId();
    public void loadConfig();

}
