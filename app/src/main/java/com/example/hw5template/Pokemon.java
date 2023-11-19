package com.example.hw5template;

import com.google.gson.annotations.SerializedName;

public class Pokemon {
    @SerializedName("name")
    String name;
    @SerializedName("id")
    Integer id;
    @SerializedName("weight")
    Integer weight;
    @SerializedName("height")
    Integer height;
    @SerializedName("base_experience")
    Integer base_experience;
    @SerializedName("move")
    String move;
    @SerializedName("ability")
    String ability;

    public Pokemon(String name, Integer weight, Integer height, Integer base_experience, String move, String ability, Integer id) {
        name = this.name;
        weight = this.weight;
        height = this.height;
        base_experience = this.base_experience;
        move = this.move;
        ability = this.ability;
        id = this.id;

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


    //get and set basexp
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
