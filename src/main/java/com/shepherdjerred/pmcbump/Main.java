package com.shepherdjerred.pmcbump;

public class Main {

    public static void main(String args[]) {
        System.out.println("Bumping server");
        Bumper bumper = new Bumper("jsheph032010@live.com", "password", "3004178", "209278");
        bumper.bumpServer();
    }


}