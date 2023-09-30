package com.example.simplecrudapp;

import android.text.TextUtils;

public class loginController{

    public static boolean isEmpty(String email, String pass){

        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(pass))
            return true;

        return false;

    }

}
