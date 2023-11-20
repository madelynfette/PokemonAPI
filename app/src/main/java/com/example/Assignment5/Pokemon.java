package com.example.Assignment5;

import com.google.gson.annotations.SerializedName;

public class Pokemon {
    @SerializedName("id")
    Integer id;
    @SerializedName("name")
    String name;
    @SerializedName("weight")
    Integer weight;
    @SerializedName("height")
    Integer height;
    @SerializedName("base_experience")
    Integer base_experience;
    String move;
    String ability;
    String imageURL;

    public Pokemon(String name, Integer weight, Integer height, Integer base_experience,  Integer id, String move, String ability, String imageURL) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.base_experience = base_experience;
        this.move = move;
        this.ability = ability;
        this.id = id;
        this.imageURL = imageURL;

    }
    //get and set name
    public String getName(){
        return name;
    }
    public void setName(String name){
        name = this.name;
    }

    //get and set weight
    public Integer getWeight(){
        return weight;
    }
    public void getWeight(Integer weight){
        weight = this.weight;
    }

    //get and set height
    public Integer getHeight(){
        return height;
    }
    public void setHeight(Integer height){
        height = this.height;
    }


    //get and set baseXP
    public Integer getBase_experience(){
        return base_experience;
    }
    public void setBaseExperience(Integer base_experience){
        base_experience=  this.base_experience;
    }

    //get and set move
    public String getMove(){
        return move;
    }
    public void setMove(String move){
        move = this.move;
    }

    //get and set ability
    public String getAbility(){
        return ability;
    }
    public void setAbility(String ability){
        ability = this.ability;
    }

    //get and set number
    public Integer getID(){
        return id;
    }
    public  void setID(Integer id){
        id = this.id;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }
}
