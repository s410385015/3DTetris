package com.example.nako.tutorial.Program;
import android.content.Context;
import com.example.nako.tutorial.util.ShaderHelper;
import com.example.nako.tutorial.util.TextResReader;
import static android.opengl.GLES20.glUseProgram;
/**
 * Created by Nako on 2016/11/22.
 */

public class ShaderProgram {
    protected static final String U_COLOR = "u_Color";
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected final int program;
    protected ShaderProgram(Context context, int vertexShaderResourceId,int fragmentShaderResourceId) {

        program = ShaderHelper.buildProgram(TextResReader.readTextFileFromRes(context, vertexShaderResourceId),
                TextResReader.readTextFileFromRes(context, fragmentShaderResourceId));
    }
    public void useProgram() {

        glUseProgram(program);
    }
}
