package com.adit.valuter;

import java.io.File;

public class DbChecker {
    private static final String DB_PATH = "vaulter.db";

    public static boolean doesDatabaseExist(){
        return new File(DB_PATH).exists();
    }
}