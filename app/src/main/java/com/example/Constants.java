package com.example;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Constants {

    public static String UserAuthID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    public static String UserName = "";
    public static int profileNumber = -1;


}
