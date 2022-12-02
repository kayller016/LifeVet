package com.vo;

import java.util.Random;

public class PetInfo {
    private String petId;
    private String owner;
    private String petName;
    private String age;
    private String gender;
    private String dateOfBirth;
    private String species;
    private String breed;

    public PetInfo(){}

    public PetInfo(String petName, String ownerName, String age, String gender, String dateOfBirth, String species, String breed){
        this.petName = petName;
        this.owner = ownerName;
        this.age = age;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.species = species;
        this.breed = breed;

        Random rand = new Random();
        int n = rand.nextInt(1000);
        this.petId = String.valueOf(n); //Temporary Value

    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
