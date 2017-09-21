package com.example.nako.tutorial.Object;

import com.example.nako.tutorial.Program.ColorShaderProgram;
import com.example.nako.tutorial.data.VertexArray;
import com.example.nako.tutorial.util.Geometry;

import java.util.List;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glLineWidth;

/**
 * Created by Nako on 2016/11/29.
 */

public class Container {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private final VertexArray vertexArray;
    private static final float[] VERTEX_DATA = {
            0.5f,0.7f,-0.5f,
            -0.5f,0.7f,-0.5f,

            -0.5f,-0.7f,-0.5f,
            0.5f,-0.7f,-0.5f,
            0.5f,0.7f,-0.5f,

            0.5f,0.7f,-0.5f,
            0.5f,0.7f,0.5f,
            -0.5f,0.7f,-0.5f,
            -0.5f,0.7f,0.5f,
            -0.5f,-0.7f,-0.5f,
            -0.5f,-0.7f,0.5f,
            0.5f,-0.7f,-0.5f,
            0.5f,-0.7f,0.5f,

            0.5f,0.7f,0.5f,
            -0.5f,0.7f,0.5f,
            -0.5f,-0.7f,0.5f,
            0.5f,-0.7f,0.5f,
            0.5f,0.7f,0.5f,

            0.1f,0.0f,0.0f,
            -0.1f,0.0f,0.0f,

            0.0f,0.1f,0.0f,
            0.0f,-0.1f,0.0f,

            0.0f,0.0f,0.1f,
            0.0f,0.0f,-0.1f,

            0.3f,-0.7f,0.5f,
            0.3f,-0.7f,-0.5f,

            0.1f,-0.7f,0.5f,
            0.1f,-0.7f,-0.5f,

            -0.1f,-0.7f,0.5f,
            -0.1f,-0.7f,-0.5f,

            -0.3f,-0.7f,0.5f,
            -0.3f,-0.7f,-0.5f,

             0.5f,-0.7f, 0.1f,
            -0.5f,-0.7f, 0.1f,

            0.5f,-0.7f, -0.1f,
            -0.5f,-0.7f, -0.1f,

            0.5f,-0.7f, -0.3f,
            -0.5f,-0.7f, -0.3f,

            0.5f,-0.7f, 0.3f,
            -0.5f,-0.7f, 0.3f



    };
    public Container() {vertexArray = new VertexArray(VERTEX_DATA);}
    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }
    public void draw() {
        glLineWidth(10);
        for(int i=0;i<4;i++) glDrawArrays(GL_LINES, i, 2);
       for(int i=5;i<12;i+=2) glDrawArrays(GL_LINES, i, 2);
        for(int i=13;i<17;i++) glDrawArrays(GL_LINES, i, 2);
        for(int i=24;i<39;i+=2)glDrawArrays(GL_LINES, i, 2);
    }
    public void drawX()
    {
        glDrawArrays(GL_LINES, 18, 2);
    }
    public void drawY()
    {
        glDrawArrays(GL_LINES, 20, 2);
    }
    public void drawZ()
    {
        glDrawArrays(GL_LINES, 22, 2);
    }
}
