package com.example.nako.tutorial.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;
/**
 * Created by Nako on 2016/11/22.
 */

public class VertexArray {
    private  final FloatBuffer floatBuffer;
    public VertexArray(float[] vertexData)
    {
        floatBuffer= ByteBuffer.allocateDirect(vertexData.length*Constant.Byte_Per_Float)
                              .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset,int attributeLoc,int count,int stride)
    {
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLoc,count,GL_FLOAT,false,stride,floatBuffer);
        glEnableVertexAttribArray(attributeLoc);

        floatBuffer.position(0);

    }
}
