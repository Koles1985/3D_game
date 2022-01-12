package com.koles.a3dgame;

import com.koles.io.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Settings {
    public static boolean soundEnabled = true;
    public static boolean touchEnabled = true;
    public static final String file = ".droidgame";


    public  static void load(FileIO files){
        BufferedReader in = null;
        try{
            in = new BufferedReader(new InputStreamReader(files.readFile(file)));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            touchEnabled = Boolean.parseBoolean(in.readLine());
        }catch (IOException e){
            //есть значения по умолчанию
        }catch (NumberFormatException e){
            //есть значения по умолчанию.
        }finally{
            if(in != null)
                try{
                    in.close();
                }catch (IOException e){

                }
        }
    }

    public static void save(FileIO files){
        BufferedWriter out = null;
        try{
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));
            out.write(Boolean.toString(soundEnabled));
            out.write("\n");
            out.write(Boolean.toString(touchEnabled));
        }catch (IOException e){

        }finally {
            try{
                if(out != null)
                    out.close();
            }catch (IOException e){

            }
        }
    }
}
