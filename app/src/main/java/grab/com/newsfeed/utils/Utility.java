package grab.com.newsfeed.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import grab.com.newsfeed.App;
import grab.com.newsfeed.constants.IntegerConstants;
import grab.com.newsfeed.constants.StringConstants;

public class Utility {


    public static String dateFormatter(String date){

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        Date d = null;
        try
        {
            d = input.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return d != null ? output.format(d) : output.format(new Date(System.currentTimeMillis()));
    }

    public static String getEmptyFilePath(int fileType) {
        String filePath = null;
        try {
            switch (fileType) {
                case IntegerConstants.IMAGE:
                    filePath = getFolderPath(IntegerConstants.IMAGE) + "/i_" + System.currentTimeMillis() % 100000000 + ".jpeg";
                    break;
            }
            if (filePath != null) {
                File f = new File(filePath);
                if (f.exists()) {
                    f.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static String getFolderPath(int type) {
        File clipFolder = new File(Environment.getExternalStorageDirectory(), StringConstants.FOLDER_NAME);

        boolean directoryCreated = true;
        if (!clipFolder.exists()) {
            directoryCreated = clipFolder.mkdirs();
        }

        if (directoryCreated) {
            if (type == IntegerConstants.IMAGE) {
                File images = new File(clipFolder, StringConstants.IMAGE_FOLDER);
                if (!images.exists()) {
                    directoryCreated = images.mkdirs();
                    File output = new File(images, ".nomedia");
                    try {
                        output.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (directoryCreated) {
                    return images.getAbsolutePath();
                }
            } else {
                return clipFolder.getAbsolutePath();
            }
        }
        return "";
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) App.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
