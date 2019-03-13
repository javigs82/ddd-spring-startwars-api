package me.javigs82.startwars.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Arrays;
import java.util.List;

public class People {

    private String name;

    private String height;

    private String gender;

    private String skin_color;

    private String edited;

    private String created;

    private String mass;

    private String url;

    private String hair_color;

    private String birth_year;

    private String eye_color;

    private String[] starshipsUrl;

    private List<StartShip> starships;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public void setSkin_color(String skin_color) {
        this.skin_color = skin_color;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getEye_color() {
        return eye_color;
    }

    public void setEye_color(String eye_color) {
        this.eye_color = eye_color;
    }

    public String[] getStarshipsUrl() {
        return starshipsUrl;
    }

    @JsonSetter("starships") //to map from swapi
    public void setStarshipsUrl(String[] starships) {
        this.starshipsUrl = starships;
    }

    @JsonGetter("starships") //to map when it is rendered as json
    public  List<StartShip> getStarships() {
        return starships;
    }

    public void setStarships( List<StartShip> starships) {
        this.starships = starships;
    }
}

