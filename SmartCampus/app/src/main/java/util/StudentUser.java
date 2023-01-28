package util;

import android.app.Application;
// Singleton classes are used for logging, driver objects, caching and thread pool, database connections.

public class StudentUser extends Application {
    private String username;
    private  String userid;

    private static StudentUser obj;

    public static StudentUser getInstance(){
        if(obj==null){
            obj = new StudentUser();

        }
        return obj;
    }
    public StudentUser(){
        //Empty Constructor
    }
    //getter
    public String getUsername(){
        return username;
    }
    public String getUserid(){
        return userid;
    }
    //setter
    public void setUsername(String username_setter){
        this.username = username_setter;
    }
    public void setUserid(String userid_setter){
        this.userid = userid_setter;
    }

}
