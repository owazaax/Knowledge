package com.redesky.knowledge.shared.objects;

public class User {

    private String name;
    private int joined;
    private int left;

    public User(String name, int joined, int left) {
        this.name = name;
        this.joined = joined;
        this.left = left;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getJoined() {
        return joined;
    }

    public void setJoined(int joined) {
        this.joined = joined;
    }

    public void addJoined(int joined) {
        setJoined(getJoined() + joined);
    }

    public void remJoined(int joined) {
        setJoined(getJoined() - joined);
    }

    public void resetJoined() {
        setJoined(0);
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void addLeft(int left) {
        setLeft(getLeft() + left);
    }

    public void remLeft(int left) {
        setLeft(getLeft() - left);
    }

    public void resetLeft() {
        setLeft(0);
    }

}