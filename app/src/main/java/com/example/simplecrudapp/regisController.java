package com.example.simplecrudapp;

import android.text.TextUtils;

public class regisController{

    public static boolean isEmpty(String email, String pass, String passConf){

        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(pass) && TextUtils.isEmpty(passConf))
            return true;

        return false;

    }

    public static boolean isPassNotMatch(String pass, String passConf){

        if(!pass.equals(passConf))
            return true;

        return false;

    }


}
