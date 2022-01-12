package com.koles.io;

import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class KolesFileIO implements FileIO{
    private AssetManager assetManager;
    private String externalStoragePath;
    private String externalState;

    public KolesFileIO(AssetManager assetManager){
        this.assetManager = assetManager;
        this.externalState = Environment.getExternalStorageState();
        if(externalState.equals(Environment.getExternalStorageState())) {
            this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator;
            System.out.println("External path = " + externalStoragePath);
        }else{
            this.externalStoragePath = null;
            System.out.println("External state = " + externalState);
            System.out.println("External path = " + externalStoragePath);
        }
    }

    @Override
    public InputStream readAssets(String fileName) throws IOException {
        return assetManager.open(fileName);
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(externalStoragePath + fileName);
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(externalStoragePath + fileName);
    }
}
