package com.shepherdjerred.pmcbump;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
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
        System.out.println("Server bumped");
    }

    private void login() throws IOException {
        Connection.Response response = Jsoup.connect("http://www.planetminecraft.com/forums/ucp.php?mode=login_from_main")
                .data("username", email)
                .data("password", password)
                .method(Connection.Method.POST)
                .execute();

        cookies = response.cookies();
        System.out.println(cookies);
    }

    private void bump() throws IOException {

        Connection.Response response = Jsoup.connect("http://www.planetminecraft.com/qajax.php?resource_id=" + resourceId + "&member_id=" + memberId + "&action=bump")
                .cookies(cookies)
                .method(Connection.Method.GET)
                .execute();

        System.out.println(response.body());

    }


}
