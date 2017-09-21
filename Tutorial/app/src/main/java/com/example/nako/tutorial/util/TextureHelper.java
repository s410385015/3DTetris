package com.example.nako.tutorial.util;

import android.content.Context;


import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
/**
 * Created by Nako on 2016/11/22.
 */

public class TextureHelper {
    private static final String Tag="TextureHelper";
    public static int LoadTexture(Context context,int ResID)
    {
        final int[] textureObjID=new int[1];
        glGenTextures(1,textureObjID,0);

        if (textureObjID[0] == 0) {
            if (LogConfig.ON) {
                Log.w(Tag, "Could not generate a new OpenGL texture object.");
            }
            return 0;
        }

        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inScaled=false;
        final Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(),ResID,options);

        if(bitmap==null) {
            if(LogConfig.ON)
            {
                Log.w(Tag,"Res ID: "+ResID+" could not be decoded.");
            }
            glDeleteTextures(1,textureObjID,0);
            return 0;
        }

        glBindTexture(GL_TEXTURE_2D,textureObjID[0]);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        texImage2D(GL_TEXTURE_2D,0,bitmap,0);
        bitmap.recycle();
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D,0);
        return textureObjID[0];

    }
}
