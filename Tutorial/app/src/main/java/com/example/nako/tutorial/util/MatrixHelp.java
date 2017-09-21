package com.example.nako.tutorial.util;

/**
 * Created by Nako on 2016/11/22.
 */

public class MatrixHelp {

    public static void perspectiveM(float[] m,float yFovInDegree,float aspect,float n,float f)
    {
        final float angleInR=(float)(yFovInDegree*Math.PI/180.0);
        final float a=(float)(1.0/Math.tan(angleInR/2));
        m[0] = a / aspect;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;
        m[4] = 0f;
        m[5] = a;
        m[6] = 0f;
        m[7] = 0f;
        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((f + n) / (f - n));
        m[11] = -1f;
        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * f * n) / (f - n));
        m[15] = 0f;
    }
}
