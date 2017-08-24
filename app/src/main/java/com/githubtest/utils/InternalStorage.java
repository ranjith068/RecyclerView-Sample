package com.githubtest.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by mithilesh on 24/8/17.
 */

public final class InternalStorage {

    private InternalStorage() {
    }
    //Write Data to Intenal storage

    public static void writeObject(Context context, String key, Object object) throws IOException {

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        File cacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
            // cacheDir = getExternalCacheDir(); // Write files to the extrnal
            // sdcard
            cacheDir = context.getExternalFilesDir(".Mobapper");


        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
            cacheDir = context.getCacheDir();// Write files to the insideapp


        }
        final File suspend_f = new File(cacheDir, key);
        FileOutputStream fos = new FileOutputStream(suspend_f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();


    }

    //Read Data fom Intenal storage


    public static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        File cacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
            // cacheDir = getExternalCacheDir(); // Write files to the extrnal
            // sdcard
            cacheDir = context.getExternalFilesDir(".Mobapper");


        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
            cacheDir = context.getCacheDir();// Write files to the insideapp


        }
        final File suspend_f = new File(cacheDir, key);
        FileInputStream fis = new FileInputStream(suspend_f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();

        return object;
    }

    //Clear cache
//	    public static void clear(){
//	        File[] files=MainMobapper.cacheDir.listFiles();
//	        if(files==null)
//	            return;
//	        for(File f:files)
//	            f.delete();
//	    }
}