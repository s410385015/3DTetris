package com.example.nako.tutorial.Program;
import android.content.Context;

import com.example.nako.tutorial.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUniform4f;
/**
 * Created by Nako on 2016/11/22.
 */

public class ColorShaderProgram extends ShaderProgram{
    private final int uMatrixLocation;
    private final int uColorLocation;
    private final int aPositionLocation;
    private final int aColorLocation;
    public ColorShaderProgram(Context context) {
        super(context, R.raw.vertex_shader, R.raw.fragment_shader);

        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
    }
    public void setUniforms(float[] matrix, float r, float g, float b) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        glUniform4f(uColorLocation, r, g, b, 1f);
    }
    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }
    public int getColorAttributeLocation() {return aColorLocation;}
}
