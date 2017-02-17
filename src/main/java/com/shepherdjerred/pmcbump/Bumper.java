package com.shepherdjerred.pmcbump;

import com.shepherdjerred.pmcbump.config.Config;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bumper {

    private final static Logger LOGGER = Logger.getLogger(Bumper.class.getName());

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    private Map<String, String> cookies;
    private final String username;
    private final String password;
    private final String resourceId;
    private final String memberId;

    public Bumper(Config config) {
        this.username = config.getUsername();
        this.password = config.getPassword();
        this.resourceId = config.getResourceId();
        this.memberId = config.getMemberId();
    }

    public void bumpServer() {
        try {
            login();
            bump();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() throws IOException {
        Connection.Response loginForm = Jsoup.connect("http://www.planetminecraft.com/account/sign_in/")
                .userAgent(USER_AGENT)
                .method(Connection.Method.GET)
                .execute();

        Connection.Response loginPost = Jsoup.connect("http://www.planetminecraft.com/forums/ucp.php?mode=login_from_main")
                .userAgent(USER_AGENT)
                .data("username", username)
                .data("password", password)
                .data("login", "Sign In")
                .data("autologin", "1")
                .data("from_main_site", "1")
                .cookies(loginForm.cookies())
                .method(Connection.Method.POST)
                .execute();

        LOGGER.log(Level.FINEST, loginPost.body());

        if (loginPost.body().contains("Sign Out")) {
            LOGGER.log(Level.INFO, "Logged in");
        } else {
            LOGGER.log(Level.WARNING, "Error logging in");
            System.exit(1);
        }

        Map<String, String> combinedCookies = new HashMap<>();
        combinedCookies.putAll(loginForm.cookies());
        combinedCookies.putAll(loginPost.cookies());
        cookies = combinedCookies;

        LOGGER.log(Level.FINEST, cookies.toString());

    }

    private void bump() throws IOException {

        Document document = Jsoup.connect("http://www.planetminecraft.com/qajax.php")
                .userAgent(USER_AGENT)
                .cookies(cookies)
                .referrer("http://www.planetminecraft.com/account/manage/servers/" + resourceId)
                .data("resource_id", resourceId)
                .data("member_id", memberId)
                .data("action", "bump")
                .get();

        LOGGER.log(Level.FINEST, document.html());

        if (document.html().contains("Submission bumped")) {
            LOGGER.log(Level.INFO, "Server bumped");
        } else {
            LOGGER.log(Level.WARNING, "Error bumping server");
        }

    }


}
