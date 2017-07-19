package com.yamada.biton.healthtogether;

import java.util.List;

/**
 * Created by 優太 on 2017/07/03.
 */

public class FriendInfo {
    long id;
    String mymail;
    String friendmail;
    String allreleaseflag;
    String scheduleflag;

    //String profile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMymail(String mail){
        this.mymail = mail;
    }

    public String getMymail(){
        return mymail;
    }

    public void setFriendmail(String mail){
        friendmail = mail;
    }

    public String getFriendmail(){
        return friendmail;
    }

    public void setAllreleaseflag(String flag){
        this.allreleaseflag = flag;
    }

    public String getAllreleaseflag(){
        return allreleaseflag;
    }

    public void setScheduleflag(String flag){
        this.scheduleflag = flag;
    }

    public String getScheduleflag(){
        return scheduleflag;
    }

}
