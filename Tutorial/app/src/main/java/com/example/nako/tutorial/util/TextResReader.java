package com.example.nako.tutorial.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Created by Nako on 2016/11/20.
 */

public class TextResReader {

    public static String readTextFileFromRes(Context context,int ResID)
    {
        StringBuilder body=new StringBuilder();
        try{
            InputStream inS=context.getResources().openRawResource(ResID);
            InputStreamReader inSR=new InputStreamReader(inS);
            BufferedReader bufferedReader=new BufferedReader(inSR);
            String nextLine;

            while((nextLine=bufferedReader.readLine())!=null)
            {
                body.append((nextLine));
                body.append('\n');
            }
        }catch (IOException e){
            throw  new RuntimeException("Could not open resource:"+ResID,e);
        }catch(Resources.NotFoundException nfe){
            throw  new RuntimeException("Res not found:"+ResID,nfe);
        }
        return  body.toString();
    }
}
