package com.example.nako.tutorial.Object;
import com.example.nako.tutorial.Program.TextureShaderProgram;
import com.example.nako.tutorial.data.Constant;
import com.example.nako.tutorial.data.VertexArray;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by Nako on 2016/11/23.
 */

public class Table {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constant.Byte_Per_Float;

    private static final float[] VERTEX_DATA = {
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.4f, 0f, 0.9f,
            0.5f, -0.4f, 1f, 0.9f,
            0.5f, 0.4f, 1f, 0.1f,
            -0.5f, 0.4f, 0f, 0.1f,
            -0.5f, -0.4f, 0f, 0.9f };
    private final VertexArray vertexArray;
    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }
    public void bindData(TextureShaderProgram textureProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }
    public void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
}
