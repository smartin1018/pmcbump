package com.shepherdjerred.pmcbump;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bumper {

    private Map<String, String> cookies;
    private final String email;
    private final String password;
    private final String resourceId;
    private final String memberId;

    public Bumper(String email, String password, String resourceId, String memberId) {
        this.email = email;
        this.password = password;
        this.resourceId = resourceId;
        this.memberId = memberId;
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
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .method(Connection.Method.GET)
                .execute();

        Connection.Response loginPost = Jsoup.connect("http://www.planetminecraft.com/forums/ucp.php?mode=login_from_main")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .data("username", email)
                .data("password", password)
                .data("login", "Sign In")
                .data("autologin", "1")
                .data("from_main_site", "1")
                .cookies(loginForm.cookies())
                .method(Connection.Method.POST)
                .execute();

        if (loginPost.body().contains("Sign Out")) {
            System.out.println("Login success");
        } else {
            System.out.println("Login failure");
        }

        Map<String, String> combinedCookies = new HashMap<>();
        combinedCookies.putAll(loginForm.cookies());
        combinedCookies.putAll(loginPost.cookies());

        cookies = combinedCookies;
    }

    private void bump() throws IOException {

        System.out.println(cookies);

        Connection.Response response = Jsoup.connect("http://www.planetminecraft.com/qajax.php")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .cookies(cookies)
                .data("resource_id", resourceId)
                .data("member_id", memberId)
                .data("action", "bump")
                .method(Connection.Method.GET)
                .execute();

        System.out.println("Bump: " + response.body());

    }


}
