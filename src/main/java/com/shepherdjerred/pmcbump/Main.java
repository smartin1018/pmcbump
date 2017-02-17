package com.shepherdjerred.pmcbump;

import com.shepherdjerred.pmcbump.config.Config;
import com.shepherdjerred.pmcbump.config.PropertiesConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String args[]) {

        LOGGER.log(Level.FINE, "Loading config.properties");
        Config config = new PropertiesConfig();
        config.loadConfig();

        LOGGER.log(Level.FINE, "Bumping server");
        Bumper bumper = new Bumper(config);
        bumper.bumpServer();

    }

}
