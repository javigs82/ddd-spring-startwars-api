package me.javigs82.startwars.domain;

import java.util.Arrays;

public class People {

    private String name;

    private String height;

    private String homeworld;

    private String gender;

    private String skin_color;

    private String edited;

    private String created;

    private String mass;

    private String url;

    private String hair_color;

    private String birth_year;

    private String eye_color;

    private String[] films;

    private String[] vehicles;

    private String[] species;

    private String[] starships;


    public String[] getFilms() {
        return films;
    }

    public void setFilms(String[] films) {
        this.films = films;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
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

    public String[] getVehicles() {
        return vehicles;
    }

    public void setVehicles(String[] vehicles) {
        this.vehicles = vehicles;
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

    public String[] getSpecies() {
        return species;
    }

    public void setSpecies(String[] species) {
        this.species = species;
    }

    public String[] getStarships() {
        return starships;
    }

    public void setStarships(String[] starships) {
        this.starships = starships;
    }

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

    @Override
    public String toString() {
        return "ClassPojo [films = " + Arrays.toString(films) + ", homeworld = " + homeworld + ", gender = " + gender + ", skin_color = " + skin_color + ", edited = " + edited + ", created = " + created + ", mass = " + mass + ", vehicles = " + Arrays.toString(vehicles) + ", url = " + url + ", hair_color = " + hair_color + ", birth_year = " + birth_year + ", eye_color = " + eye_color + ", species = " + Arrays.toString(species) + ", starships = " + Arrays.toString(starships) + ", name = " + name + ", height = " + height + "]";
    }
}

