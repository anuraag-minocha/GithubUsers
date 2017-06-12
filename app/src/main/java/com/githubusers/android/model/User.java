package com.githubusers.android.model;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by test01 on 09-May-17.
 */
@DatabaseTable(tableName = "user_table")
public class User implements Serializable {

    @DatabaseField
    @SerializedName("id")
    String id="";
    @DatabaseField
    @SerializedName("login")
    String login="";
    @DatabaseField
    @SerializedName("avatar_url")
    String avatarUrl="";

    public String getId(){
        return  id;
    }
    public String getLogin(){
        return  login;
    }
    public String getAvatarUrl(){
        return  avatarUrl;
    }

    public User() {
        // ORMLite needs a no-arg constructor
    }
    public User(String id, String login, String avatarUrl) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
    }

}
