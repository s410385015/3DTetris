package com.example.nako.tutorial.Object;

import android.util.Log;

import com.example.nako.tutorial.Program.ColorShaderProgram;
import com.example.nako.tutorial.data.VertexArray;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glLineWidth;

/**
 * Created by Nako on 2016/11/29.
 */

public class Cube {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private final VertexArray vertexArray;
    private float[] VERTEX_DATA={
            0.1f,-0.1f,0.1f,
            0.1f,-0.1f,-0.1f,
            -0.1f,-0.1f,0.1f,
            -0.1f,-0.1f,-0.1f,

            0.1f,-0.1f,0.1f,
            0.1f,0.1f,0.1f,
            0.1f,-0.1f,-0.1f,
            0.1f,0.1f,-0.1f,

            0.1f,-0.1f,0.1f,
            0.1f,0.1f,0.1f,
            -0.1f,-0.1f,0.1f,
            -0.1f,0.1f,0.1f,

            0.1f,0.1f,0.1f,
            0.1f,0.1f,-0.1f,
            -0.1f,0.1f,0.1f,
            -0.1f,0.1f,-0.1f,

            -0.1f,-0.1f,0.1f,
            -0.1f,0.1f,0.1f,
            -0.1f,-0.1f,-0.1f,
            -0.1f,0.1f,-0.1f,

            0.1f,-0.1f,-0.1f,
            0.1f,0.1f,-0.1f,
            -0.1f,-0.1f,-0.1f,
            -0.1f,0.1f,-0.1f,

            //24
            -0.1f,0.1f,-0.1f,
            -0.1f,-0.1f,-0.1f,

            -0.1f,0.1f,-0.1f,
            0.1f,0.1f,-0.1f,

            0.1f,-0.1f,-0.1f,
            0.1f,0.1f,-0.1f,

            -0.1f,-0.1f,-0.1f,
            0.1f,-0.1f,-0.1f,

            -0.1f,0.1f,-0.1f,
            -0.1f,0.1f,0.1f,

            -0.1f,-0.1f,-0.1f,
            -0.1f,-0.1f,0.1f,

            0.1f,0.1f,-0.1f,
            0.1f,0.1f,0.1f,

            0.1f,-0.1f,-0.1f,
            0.1f,-0.1f,0.1f,

            -0.1f,0.1f,0.1f,
            -0.1f,-0.1f,0.1f,

            -0.1f,0.1f,0.1f,
            0.1f,0.1f,0.1f,

            0.1f,-0.1f,0.1f,
            0.1f,0.1f,0.1f,

            -0.1f,-0.1f,0.1f,
            0.1f,-0.1f,0.1f,
    };

    public Cube()
    {
        //SetCubeOffset(0,0.6f,0);
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorProgram) {

        vertexArray.setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }
    public void SetCubeOffset(float x,float y,float z)
    {
            for(int i=1;i<48*3;i+=3) {
                VERTEX_DATA[i] += y;
                Log.w(":",VERTEX_DATA[i]+"?");
            }
        //vertexArray = VertexArray(VERTEX_DATA);
    }
    public void draw() {


        for(int i=0;i<6;i++) {
            glDrawArrays(GL_TRIANGLES, i*4, 3);
            glDrawArrays(GL_TRIANGLES, i*4+1, 3);
        }


    }

    public void Draw_Line()
    {
        glLineWidth(10);
        for(int i=24;i<47;i+=2)
            glDrawArrays(GL_LINES,i,2);
    }
}
