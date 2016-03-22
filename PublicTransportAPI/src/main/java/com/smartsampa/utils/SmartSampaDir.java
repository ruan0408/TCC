package com.smartsampa.utils;

import java.io.File;

/**
 * Created by ruan0408 on 11/03/2016.
 */
public class SmartSampaDir {

    public static final String DIR_NAME = "SmartSampa";
    //TODO make this path platform independent
    private static final String PATH = System.getProperty("user.home")+"/"+ DIR_NAME;

    private static SmartSampaDir ourInstance = new SmartSampaDir();
    private File directory;

    public static SmartSampaDir getInstance() {return ourInstance;}

    private SmartSampaDir() {
        directory = new File(PATH);
        directory.mkdir();
    }

    public boolean hasSubDir(String dirName) {
        return new File(directory.getPath()+"/"+dirName).exists();
    }

    public String getPath() {
        return directory.getPath();
    }
}
