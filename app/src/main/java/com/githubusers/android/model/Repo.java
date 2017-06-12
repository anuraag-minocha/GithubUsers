package com.githubusers.android.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by test01 on 10-May-17.
 */

public class Repo implements Serializable {
    @SerializedName("name")
    String name="";
    @SerializedName("owner")
    Owner owner;

    public class Owner implements  Serializable{
        @SerializedName("type")
        String type="";

        public String getType(){
            return type;
        }
    }

    public String getName(){
        return name;
    }
    public Owner getOwner(){
        return owner;
    }

}
