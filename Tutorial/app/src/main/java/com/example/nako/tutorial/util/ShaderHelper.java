package com.example.nako.tutorial.util;

import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

/**
 * Created by Nako on 2016/11/20.
 */

public class ShaderHelper {
    private static final String Tag="ShaderHelper";

    public static  int compileVertexShader(String shaderCode)
    {
        return  compileShader(GL_VERTEX_SHADER,shaderCode);
    }

    public static  int compileFragmentShader(String shaderCode)
    {
        return  compileShader(GL_FRAGMENT_SHADER,shaderCode);
    }
    private static  int compileShader(int type,String shaderCode)
    {
        final int shaderObjID=glCreateShader(type);
        if(shaderObjID==0) {
            if(LogConfig.ON) {
                Log.w(Tag, "Could not create new shader");
            }
            return 0;
        }
        glShaderSource(shaderObjID,shaderCode);
        glCompileShader(shaderObjID);
        final int[] compileStatus=new int[1];
        glGetShaderiv(shaderObjID,GL_COMPILE_STATUS,compileStatus,0);

        if(LogConfig.ON) {
            Log.v(Tag,"Result of compiling source:"+"\n"+shaderCode+"\n"+glGetShaderInfoLog(shaderObjID));
        }
        if(compileStatus[0]==0) {
            glDeleteShader(shaderObjID);
            if (LogConfig.ON) {
                Log.w(Tag, "Compilation of shader failed.");
            }

            return 0;
        }
        return shaderObjID;
    }

    public static int linkProgram(int vertexShaderID,int fragmentShaderID)
    {
        final int programObjID=glCreateProgram();
        if(programObjID==0)
        {
            if (LogConfig.ON) {
                Log.w(Tag, "Could not create new program");
            }

            return 0;
        }
        glAttachShader(programObjID,vertexShaderID);
        glAttachShader(programObjID,fragmentShaderID);
        glLinkProgram(programObjID);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjID,GL_LINK_STATUS,linkStatus,0);

        if (LogConfig.ON) {

            Log.v(Tag, "Results of linking program:"+"\n"+ glGetProgramInfoLog(programObjID));
        }

        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjID);
            if (LogConfig.ON) {
                Log.w(Tag, "Linking of program failed.");
            }
            return 0;
        }


        return programObjID;

    }
    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(Tag, "Results of validating program: " + validateStatus[0]
                + "\nLog:" + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource,String fragmentShaderSource) {
        int program;
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        program = linkProgram(vertexShader, fragmentShader);
        if (LogConfig.ON) {
            validateProgram(program);
        }
        return program;
    }
}
