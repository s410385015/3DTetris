package com.example.nako.tutorial.Object;
import com.example.nako.tutorial.Object.ObjectBuilder;
import com.example.nako.tutorial.Object.ObjectBuilder.DrawCommand;
import com.example.nako.tutorial.Object.ObjectBuilder.GeneratedData;
import com.example.nako.tutorial.Program.ColorShaderProgram;
import com.example.nako.tutorial.data.Constant;
import com.example.nako.tutorial.data.VertexArray;
import com.example.nako.tutorial.util.Geometry;

import java.util.List;

import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.GL_POINTS;
/**
 * Created by Nako on 2016/11/22.
 */

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;
    public final float radius;
    public final float height;
    private final VertexArray vertexArray;
    private final List<DrawCommand> drawList;
    public Mallet(float radius, float height, int numPointsAroundMallet) {
        GeneratedData generatedData = ObjectBuilder.createMallet(new Geometry.Point(0f, 0f, 0f), radius, height, numPointsAroundMallet);
        this.radius = radius;
        this.height = height;
        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }
    public void draw() {
        for (DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
