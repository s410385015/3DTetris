package com.example.nako.tutorial;


import android.app.ActionBar;
import android.content.Context;
import android.media.Image;
import android.opengl.GLSurfaceView.Renderer;

import com.example.nako.tutorial.Object.Container;
import com.example.nako.tutorial.Object.Cube;
import com.example.nako.tutorial.Object.Mallet;
import com.example.nako.tutorial.Object.Puck;
import com.example.nako.tutorial.Object.Score;
import com.example.nako.tutorial.Object.Table;
import com.example.nako.tutorial.Program.ColorShaderProgram;
import com.example.nako.tutorial.Program.TextureShaderProgram;
import com.example.nako.tutorial.util.Geometry;
import com.example.nako.tutorial.util.MatrixHelp;
import com.example.nako.tutorial.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.R.attr.x;
import static android.R.attr.y;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;

import android.opengl.Matrix;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import 	android.support.v7.app.ActionBarActivity;



/**
 * Created by Nako on 2016/11/20.
 */

public class MyRender implements Renderer{
    public MainActivity myUI;
    private static final String U_MAX="u_Matrix";
    private final Context context;
    private int program;
    private static final String U_COLOR="u_Color";
    private int uColorLoc;
    private static final String A_POS="a_Position";
    private static final String A_COL="a_Color";
    private int aPosLoc;
    private int aColLoc;
    private int uMaxLoc;
    private static final int count=4;
    private  static final int ccolor=3;
    private static final int BPF=4;
    private static final int STRIDE =(count+ccolor)*BPF;
    //private volatile float d=-60f;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;
    private int texture;

    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    private Container container;
    private Cube cube;
    private boolean CubePress=false;
    private int CubePressNum=0;
   // private Puck puck;
    //private Mallet mallet;
    private Table table;
    //private Score score;

    private boolean MoveFlag=false;
    private boolean Pressed = false;
    private float Pre_X=-100f,Pre_Y=-100f;
    private float DY=0;
    private int PressedOut=0;
    private int LR=0;
    private int TurnSpeed=15;
    private Geometry.Point CubePos;
    //private Geometry.Point blueMalletPosition;
    //private Geometry.Point redMalletPosition;
    private final float[] invertedViewProjectionMatrix = new float[16];
    private Geometry.Ray IsPressedRay;
    public Tetris tetris;
    /*
    private final float leftBound = -0.5f;
    private final float rightBound = 0.5f;
    private final float farBound = -0.8f;
    private final float nearBound = 0.8f;
    private Geometry.Point previousBlueMalletPosition;
    private Geometry.Point puckPosition;
    private Geometry.Vector puckVector;
    */

    private final float[] projectMatrix =new float[16];
    private final float[] modelMatrix =new float[16];

    public MyRender(Context context,MainActivity m)
    {
        this.context=context;
        myUI=m;
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config)
    {
        glClearColor(0f,0f,0f,0.0f);
        tetris=new Tetris(0f,0.8f,0f);
        container=new Container();
        cube=new Cube();
        //cube.SetCubeOffset(0,-0.6f,0);
        table = new Table();
        //score=new Score();
       // mallet = new Mallet(0.08f, 0.15f, 32);
        //puck = new Puck(0.06f, 0.02f, 32);


        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);
        texture = TextureHelper.LoadTexture(context, R.drawable.over);

        CubePos=new Geometry.Point(0f,0.6f,0f);
        //blueMalletPosition = new Geometry.Point(0f, mallet.height / 2f, 0.4f);
        //redMalletPosition = new Geometry.Point(0f,mallet.height/2f,-0.4f);
        //puckPosition = new Geometry.Point(0f, puck.height / 2f, 0f);
        //puckVector = new Geometry.Vector(0f, 0f, 0f);


    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height)
    {
        glViewport(0,0,width,height);
        MatrixHelp.perspectiveM(projectMatrix, 45, (float) width / (float) height, 1f, 10f);
        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);

    }
    public int GetScore(){return tetris.score;}
    @Override
    public void onDrawFrame(GL10 glUnused)
    {

       //if(tetris.score>=0)
            //a.setTitle("Score:"+tetris.score);
        //getSupportActionBar().
        glClear(GL_COLOR_BUFFER_BIT);
        multiplyMM(viewProjectionMatrix, 0, projectMatrix, 0, viewMatrix, 0);
        invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0);




        colorProgram.useProgram();
        int x_side=0,z_side=0;

        switch (GetSide())
        {
            case 1:
                z_side=tetris.col;
                x_side=0;
                break;
            case 2:
                z_side=0;
                x_side=0;
                break;
            case 3:
                z_side=0;
                x_side=0;
                break;
            case 4:
                z_side=0;
                x_side=tetris.row;
                break;
        }

        for(int i=tetris.row;i>=0;i--)
            for(int j=tetris.height;j>=0;j--)
                for(int n=tetris.col;n>=0;n--) {
                    if(tetris.TetrisBox[Math.abs(i-x_side)][j][Math.abs(n-z_side)][0]==1)
                    {
                        int typeColor=tetris.TetrisBox[Math.abs(i-x_side)][j][Math.abs(n-z_side)][1];
                        positionChange(0, 0, 0,(Math.abs(i-x_side)-tetris.row/2)*0.2f,
                                                (tetris.height/2-j)*0.2f,
                                                (Math.abs(n-z_side)-tetris.col/2)*0.2f);
                        cube.bindData(colorProgram);
                        colorProgram.setUniforms(modelViewProjectionMatrix, tetris.TetrisColor[typeColor][0]
                                                                            ,tetris.TetrisColor[typeColor][1]
                                                                            ,tetris.TetrisColor[typeColor][2]);
                        cube.draw();
                        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 1f, 1f);
                        cube.Draw_Line();
                    }
                }

        //tetris.CubeTurn(0);
        for(int i=0;i<4;i++) {
            positionChange(0, 0, 0, tetris.cur.x+0.2f*tetris.buffer[i].x,
                                    tetris.cur.y+0.2f*tetris.buffer[i].y,
                                    tetris.cur.z+0.2f*tetris.buffer[i].z);
            cube.bindData(colorProgram);
            colorProgram.setUniforms(modelViewProjectionMatrix, tetris.TetrisColor[tetris.curType][0]
                                                                 ,tetris.TetrisColor[tetris.curType][1]
                                                                 ,tetris.TetrisColor[tetris.curType][2]);
            cube.draw();
            colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 1f, 1f);
            cube.Draw_Line();


        }

        positionTableInScene();
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 1f, 1f);
        container.bindData(colorProgram);
        container.draw();

        positionORGInScene();
        if(!tetris.GameStart) {
            textureProgram.useProgram();
            textureProgram.setUniforms(modelViewProjectionMatrix, texture);
            table.bindData(textureProgram);
            table.draw();
        }

        myUI.myMethod(tetris.score);
        /*
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 1f, 0f);
        container.bindData(colorProgram);
        container.drawX();
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        container.bindData(colorProgram);
        container.drawY();
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 1f);
        container.bindData(colorProgram);
        container.drawZ();
    */


        //score.bindData(textureProgram);
        //score.draw();


        /*
        puckPosition = puckPosition.translate(puckVector);

        if (puckPosition.x < leftBound + puck.radius
                || puckPosition.x > rightBound - puck.radius) {
            puckVector = new Geometry.Vector(-puckVector.x, puckVector.y, puckVector.z);
        }
        if (puckPosition.z < farBound + puck.radius
                || puckPosition.z > nearBound - puck.radius) {
            puckVector = new Geometry.Vector(puckVector.x, puckVector.y, -puckVector.z);
        }
        puckPosition = new Geometry.Point(
                clamp(puckPosition.x, leftBound + puck.radius, rightBound - puck.radius),
                puckPosition.y,
                clamp(puckPosition.z, farBound + puck.radius, nearBound - puck.radius)
        );

        puckVector = puckVector.scale(0.9f);

        multiplyMM(viewProjectionMatrix, 0, projectMatrix, 0, viewMatrix, 0);
        invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0);

        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        positionObjectInScene(redMalletPosition.x,redMalletPosition.y,redMalletPosition.z);
        colorProgram.useProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mallet.bindData(colorProgram);
        mallet.draw();

        positionObjectInScene(blueMalletPosition.x, blueMalletPosition.y, blueMalletPosition.z);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        mallet.draw();

        positionObjectInScene(puckPosition.x, puckPosition.y, puckPosition.z);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        puck.bindData(colorProgram);
        puck.draw();

        */

    }
    private void positionTableInScene() {
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, DY, 0f, 1f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }

    private void positionORGInScene() {
        setIdentityM(modelMatrix, 0);
        //rotateM(modelMatrix, 0, 0, 0f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }
    private void positionObjectInScene(float x, float y, float z) {
        setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, x, y, z);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }
    private void positionChange(float rx,float ry,float rz,float tx,float ty,float tz)
    {
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, DY, 0f, 1f, 0f);
        Matrix.translateM(modelMatrix, 0, tx, ty, tz);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }

    public boolean IfCubePress(float Y)
    {
        if(Math.abs(Y-tetris.cur.y)>=0.3f) return false;
        return true;
    }
    public int c=0;
    public void GameOver()
    {
        c++;
        if(c>=2)
        {
            c=0;
            tetris.TetrisInit(0f,0.8f,0f);
        }
    }
    public void handleTouchPress(float normalizedX, float normalizedY) {


        //Log.d("Touch",":"+normalizedX+":"+normalizedY+"----"+(Math.abs(Pre_X-normalizedX)+Math.abs(Pre_Y-normalizedY))+"/"+PressedOut);
        boolean Click=false;
        boolean XYFlag=false;
        int LRFlag=0;
        if(Math.abs(Pre_X-normalizedX)+Math.abs(Pre_Y-normalizedY)<0.2) {
            Click = true;
            XYFlag=Math.abs(normalizedX)>Math.abs(normalizedY)?true:false;
            if(Math.abs(normalizedX)+Math.abs(normalizedY)<0.3f)
            {
                XYFlag=false;
                PressedOut++;
                LRFlag=3;
            }
            if(XYFlag) {
                if(Math.abs(normalizedX)>0.3f)
                    PressedOut++;
                if (normalizedX > 0)
                    LRFlag = 1;
                else
                    LRFlag = 0;
            }

        }

        Pre_X = normalizedX;
        Pre_Y = normalizedY;

        CubePress=IfCubePress(normalizedY);



            if(Click)
            {

                    if(normalizedY<-0.3f&&Math.abs(normalizedX)<0.3f)
                    {
                        while(!tetris.Drop()) {};
                        return;
                    }



                    if(PressedOut>0)
                        tetris.CubeTurn(LRFlag,GetSide());


            }


    }
    private int GetSide()
    {
        if((DY>=-45&&DY<=45)||DY<-315||DY>315) return 1;
        else if((DY>=135&&DY<=225)||(DY<=-135&&DY>=-225)) return 2;
        else if((DY>45&&DY<135)||(DY<-225&&DY>-315)) return 3;
        else if((DY<-45&&DY>-135)||(DY>225&&DY<315)) return 4;

        return 0;
    }

    public void handleTouchUp() {
        if(MoveFlag) {
            Pre_X = 100f;
            Pre_Y = 100f;
            MoveFlag=false;
        }
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {


        PressedOut=0;
        if(!CubePress)
        {
           float DT=DY;
           if((normalizedX-Pre_X)>0) {
               if((normalizedY-Pre_Y)>0)
                    DY = DY + (normalizedX - Pre_X) * (normalizedY - Pre_Y) * TurnSpeed;
               else
                   DY = DY - (normalizedX - Pre_X) * (normalizedY - Pre_Y) * TurnSpeed;
           }
           else {
               if((normalizedY-Pre_Y)>0)
                   DY = DY + (normalizedX - Pre_X) * (normalizedY - Pre_Y) * TurnSpeed;
               else
                   DY = DY - (normalizedX - Pre_X) * (normalizedY - Pre_Y) * TurnSpeed;
           }
           if(Math.abs(DT-DY)>10)
               MoveFlag=true;
            DY%=360f;

        }
        else
        {


           // Geometry.Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);
            int side=GetSide();
            boolean xflag=(Pre_X-normalizedX)>0 ? true: false;
            boolean zflag=(Pre_Y-normalizedY)>0 ? true: false;
           // Log.w(":", "Cube Move"+(Pre_X-normalizedX)+
                   // ":"+(Pre_Y-normalizedY));
            if(Math.abs(Pre_X-normalizedX)>0.2f)
            {
                CubePressNum=0;


                if(side==1) {

                    if(!xflag) {
                        tetris.SetMoveX(1);
                    }
                    else {
                        tetris.SetMoveX(-1);
                    }
                    //Log.w("+X", DY % 360 +"----"+tetris.cur.x+ "?");
                }
                else if(side==2) {

                    if(xflag) {
                        tetris.SetMoveX(1);
                    }
                    else {
                        tetris.SetMoveX(-1);
                    }
                    //Log.w("-X", DY % 360 + "?");
                }
                else if(side==3) {

                    if(!xflag) {
                        tetris.SetMoveZ(1);
                    }
                    else {
                        tetris.SetMoveZ(-1);
                    }
                    //Log.w("-Z", DY % 360 + "?");
                }
                else if(side==4){

                    if(xflag) {
                        tetris.SetMoveZ(1);
                    }
                    else {
                        tetris.SetMoveZ(-1);
                    }
                    //Log.w("+Z", DY % 360 + "?");
                }
                    //tetris.cur.x=(ray.point.x-IsPressedRay.point.x)>0 ? tetris.cur.x+0.2f : tetris.cur.x-0.2f;

                //if(Math.abs(DY%360)>=45&&)

                Pre_X=normalizedX;
            }

            if(Math.abs(Pre_Y-normalizedY)>0.15f)
            {
                CubePressNum=0;
                if(side==1) {

                    if(zflag) {
                        tetris.SetMoveZ(1);
                    }
                    else {
                        tetris.SetMoveZ(-1);
                    }
                    //Log.w("+X", DY % 360 +"----"+tetris.cur.x+ "?");
                }
                else if(side==2) {

                    if(!zflag) {
                        tetris.SetMoveZ(1);
                    }
                    else {
                        tetris.SetMoveZ(-1);
                    }
                    //Log.w("-X", DY % 360 + "?");
                }
                else if(side==3) {

                    if(zflag) {
                        tetris.SetMoveX(-1);
                    }
                    else {
                        tetris.SetMoveX(1);
                    }
                    //Log.w("-Z", DY % 360 + "?");
                }
                else if(side==4){

                    if(!zflag) {
                        tetris.SetMoveX(-1);
                    }
                    else {
                        tetris.SetMoveX(1);
                    }
                    //Log.w("+Z", DY % 360 + "?");
                }
                //tetris.cur.x=(ray.point.x-IsPressedRay.point.x)>0 ? tetris.cur.x+0.2f : tetris.cur.x-0.2f;

                //if(Math.abs(DY%360)>=45&&)

                Pre_Y=normalizedY;
            }

        }
    }

    private Geometry.Ray convertNormalized2DPointToRay(float normalizedX, float normalizedY) {

        final float[] nearPointNdc = {normalizedX, normalizedY, -1, 1};
        final float[] farPointNdc = {normalizedX, normalizedY, 1, 1};
        final float[] nearPointWorld = new float[4];
        final float[] farPointWorld = new float[4];
        multiplyMV(nearPointWorld, 0, invertedViewProjectionMatrix, 0, nearPointNdc, 0);
        multiplyMV(farPointWorld, 0, invertedViewProjectionMatrix, 0, farPointNdc, 0);

        divideByW(nearPointWorld);
        divideByW(farPointWorld);

        Geometry.Point nearPointRay = new Geometry.Point(nearPointWorld[0], nearPointWorld[1], nearPointWorld[2]);
        Geometry.Point farPointRay = new Geometry.Point(farPointWorld[0], farPointWorld[1], farPointWorld[2]);
        return new Geometry.Ray(nearPointRay, Geometry.vectorBetween(nearPointRay, farPointRay));
    }

    private void divideByW(float[] vector) {
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
    }
    private float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }
    public void SetCubePos(float x,float y,float z)
    {
        if(true) {
            CubePos.x += x;
            CubePos.y += y;
            CubePos.z += z;
            Log.w(":", CubePos.y + "?");
        }
    }
}
