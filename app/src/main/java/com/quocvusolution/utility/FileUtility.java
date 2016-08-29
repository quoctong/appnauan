package com.quocvusolution.utility;

import java.io.File;

public class FileUtility {
    public static File getOutputMediaFile(String fileName, String dirName){
        File mediaStorageDir = getOutputMediaDir(dirName);
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return mediaFile;
    }

    public static File getOutputMediaDir(String dirName) {
        File mediaStorageDir = new File(dirName);
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        return mediaStorageDir;
    }
}
