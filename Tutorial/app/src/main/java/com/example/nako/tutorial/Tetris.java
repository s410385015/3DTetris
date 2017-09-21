package com.example.nako.tutorial;

import android.util.Log;

import com.example.nako.tutorial.util.Geometry;

/**
 * Created by Nako on 2016/11/30.
 */

public class Tetris {
    public int score=0;
    public final int height=7;
    public final int row=5;
    public final int col=5;
    private final int data=4;
    private final int CubeType=5;
    private final int CubeSize=4;
    private final float CubeLength=0.2f;
    public boolean GameStart=false;
    public Index[] buffer=new Index[4];
    public int[][][][] TetrisBox=new int[row+1][height+1][col+1][data+1];
    public final float[][] TetrisColor={
            {0f,1f,1f},
            {0.3f,1f,0.3f},
            {0.9f,0.4f,0.95f},
            {1f,0.8f,0f},
            {1f,0.1f,0.05f}
    };
    public final int TetrisTypeCenter[]={1,2,0,0,1};

    public Geometry.Point cur=new Geometry.Point();
    private Geometry.Point init_Pos;
    public int curType;
    public Index[][] TetrisType=new Index[CubeType][CubeSize];
    public Index index=new Index(2,-1,2);;
    private float DropSpeed=0.1f;
    private Boolean stage=true;

    public static class Index
    {
        public int x,y,z;
        public Index(){}
        public Index(int x,int y,int z)
        {
            this.x=x;
            this.y=y;
            this.z=z;
        }
        public boolean CheckRange()
        {
            Tetris t=new Tetris();
            if(x<0||y<0||z<0)
                return false;
            if(x>(t.row-1)||y>(t.height-1)||(z>t.col-1))
                return false;
            return true;
        }

        public void SetPoint(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    public Tetris()
    {

    }
    public Tetris(float x,float y,float z){
        TetrisInit(x,y,z);
    };
    public void TetrisInit(float x,float y,float z)
    {
        GameStart=true;
        inti_Box();
        for(int i=0;i<CubeSize;i++)
            buffer[i]=new Index();
        init_Pos=new Geometry.Point(x,y,z);
        SetTetrisType();
        GenerateNewCube();
        score=0;
    }

    public boolean CubeTurn(int n,int side)
    {
        //Log.d("Here", n+": "+side);
        if(n>1&&side>2)
            n+=2;
        switch (n)
        {
            case -1:
                for(int i=0;i<CubeSize;i++)
                    buffer[i].SetPoint(TetrisType[curType][i].x,TetrisType[curType][i].y,TetrisType[curType][i].z);
                break;
            case 0:
                //Log.d("?","--------------");
                for(int i=0;i<CubeSize;i++) {
                    int Re_y=index.y-buffer[i].y;
                    if(Re_y<0){ Re_y=0;}
                    Index p = new Index(index.x-buffer[i].z,Re_y,index.z+buffer[i].x);
                    Log.d("?",p.x+"/"+p.y+"/"+p.z);
                    if(!p.CheckRange()||TetrisBox[p.x][p.y][p.z][0]==1)
                        return false;
                    if(p.y==height) return false;
                }
                for(int i=0;i<CubeSize;i++)
                    buffer[i].SetPoint(-buffer[i].z,buffer[i].y,buffer[i].x);
                break;
            case 1:
                for(int i=0;i<CubeSize;i++) {
                    int Re_y=index.y-buffer[i].y;
                    if(Re_y<0){ Re_y=0;}
                    Index p = new Index(index.x+buffer[i].z,Re_y,index.z-buffer[i].x);
                    if(!p.CheckRange()||(TetrisBox[p.x][p.y][p.z][0]==1))
                        return false;
                    if(p.y==height) return false;
                }
                for(int i=0;i<CubeSize;i++)
                    buffer[i].SetPoint(buffer[i].z,buffer[i].y,-buffer[i].x);
                break;
            case 2:
                for(int i=0;i<CubeSize;i++) {
                    int Re_y=index.y-buffer[i].x;
                    if(Re_y<0){ Re_y=0;}
                    Index p = new Index(index.x-buffer[i].y,Re_y,index.z+buffer[i].z);
                    if(!p.CheckRange()||(TetrisBox[p.x][p.y][p.z][0]==1))
                        return false;
                    if(p.y==height) return false;
                }
                for(int i=0;i<CubeSize;i++)
                    buffer[i].SetPoint(-buffer[i].y,buffer[i].x,buffer[i].z);
                break;
            case 3:
                for(int i=0;i<CubeSize;i++) {
                    int Re_y=index.y+buffer[i].x;
                    if(Re_y<0){ Re_y=0;}
                    Index p = new Index(index.x+buffer[i].y,Re_y,index.z+buffer[i].z);
                    if(!p.CheckRange()||(TetrisBox[p.x][p.y][p.z][0]==1))
                        return false;
                    if(p.y==height) return false;
                }
                for(int i=0;i<CubeSize;i++)
                    buffer[i].SetPoint(buffer[i].y,-buffer[i].x,buffer[i].z);
                break;
            case 4:
                for(int i=0;i<CubeSize;i++) {
                    int Re_y=index.y+buffer[i].z;
                    if(Re_y<0){ Re_y=0;}
                    Index p = new Index(index.x+buffer[i].x,Re_y,index.z+buffer[i].y);
                    if(!p.CheckRange()||(TetrisBox[p.x][p.y][p.z][0]==1))
                        return false;
                    if(p.y==height) return false;
                }
                for(int i=0;i<CubeSize;i++)
                    buffer[i].SetPoint(buffer[i].x,-buffer[i].z,buffer[i].y);
                break;
            case 5:
                for(int i=0;i<CubeSize;i++) {
                    int Re_y=index.y-buffer[i].z;
                    if(Re_y<0){ Re_y=0;}
                    Index p = new Index(index.x+buffer[i].x,Re_y,index.z-buffer[i].y);
                    if(!p.CheckRange()||(TetrisBox[p.x][p.y][p.z][0]==1))
                        return false;
                    if(p.y==height) return false;
                }
                for(int i=0;i<CubeSize;i++)
                    buffer[i].SetPoint(buffer[i].x,buffer[i].z,-buffer[i].y);
                break;
        }
        return true;
    }

    private boolean checkBottom()
    {

        for(int i=0;i<CubeSize;i++)
        {
            Index p=new Index(index.x+buffer[i].x,
                              index.y-buffer[i].y+1,
                              index.z+buffer[i].z);
            //Log.v("fuc:", p.x + "/" + p.y + "/" + p.z);
            if(p.CheckRange()&&(TetrisBox[p.x][p.y][p.z][0]==1)) {

                return false;
            }
            if(p.y==height) {

                return false;
            }
        }
        return true;
    }
    public boolean Drop()
    {
        if(!GameStart) {return false;}
        //Log.w(curType+":",index.x+"/"+index.y+"/"+index.z);
        boolean hit=false;
        if(stage) {
            //Log.w("fuc:","----------------------------");
            if (!checkBottom()) {

                hit = true;
                int CubeSet=0;
                for (int i = 0; i < CubeSize; i++) {
                    Index p = new Index(index.x + buffer[i].x,
                            index.y - buffer[i].y,
                            index.z + buffer[i].z);
                    //Log.w("fuc:", p.x + "/" + p.y + "/" + p.z);
                    if (p.CheckRange()) {
                        CubeSet++;
                        TetrisBox[p.x][p.y][p.z][0] = 1;
                        TetrisBox[p.x][p.y][p.z][1] = curType;
                    }

                }

                if(CubeSet!=CubeSize)
                {
                    Log.v("fuc:", "Dead!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11");
                    GameStart=false;
                }
                //Log.w("fuc:", "------------------------");

            }
            index.y++;
        }
        if (!hit) {
            cur.y -= DropSpeed;
            stage = !stage;
        } else {
            CheckDelete();
            GenerateNewCube();
        }

        return hit;
    }
    private void CheckDelete()
    {
        for(int j=height-1;j>=0;j--) {
            int cube_total=0;
            for (int i = 0; i < row; i++) {
                for (int n = 0; n < col; n++) {
                    if(TetrisBox[i][j][n][0]==1)
                        cube_total++;
                }
            }
            if(cube_total==col*row)
            {
                score++;


                for(int a=j;a>0;a--)
                    for (int b = 0; b < row; b++)
                        for (int c = 0; c < col; c++) {
                            TetrisBox[b][a][c][0] = TetrisBox[b][a - 1][c][0];
                            TetrisBox[b][a - 1][c][0] = 0;
                        }
                j=height-1;
            }
        }

    }




    public void inti_Box()
    {
        for(int i=0;i<=row;i++)
            for(int j=0;j<=height;j++)
                for(int n=0;n<=col;n++)
                    for(int m=0;m<=data;m++)
                        TetrisBox[i][j][n][m]=0;
    }

    public void GenerateNewCube()
    {
        //curType=1;
        curType=(int)(Math.random()*5);
        cur.x=init_Pos.x;
        cur.y=init_Pos.y;
        cur.z=init_Pos.z;
        index.x=2;
        index.y=-1;
        index.z=2;
        CubeTurn(-1,1);
        //Log.w(curType + ":","Generate");
    }

    public void SetMoveX(int x)
    {
        if(x>0)
        {
            //Log.w("Right" + ":","Go");
            for(int i=0;i<CubeSize;i++)
            {
                int Re_y=index.y-buffer[i].y;
                if(Re_y<0){ Re_y=0;}

                Index temp=new Index(index.x+1+buffer[i].x,
                        Re_y,
                        index.z+buffer[i].z);
                if(temp.CheckRange()&&TetrisBox[temp.x][temp.y][temp.z][0]==0)
                    continue;
                return;
            }
            index.x++;
            cur.x+=CubeLength;
        }
        else
        {
            for(int i=0;i<CubeSize;i++)
            {
                int Re_y=index.y-buffer[i].y;
                if(Re_y<0){ Re_y=0;}
                Index temp=new Index(index.x-1+buffer[i].x,
                                      Re_y,
                                      index.z+buffer[i].z);
                if(temp.CheckRange()&&TetrisBox[temp.x][temp.y][temp.z][0]==0)
                    continue;
                return;
            }
            index.x--;
            cur.x-=CubeLength;
        }
    }

    public void SetMoveZ(int z)
    {
        if(z>0)
        {
            for(int i=0;i<CubeSize;i++)
            {
                int Re_y=index.y-buffer[i].y;
                if(Re_y<0){ Re_y=0;}
                Index temp=new Index(index.x+buffer[i].x,
                                     Re_y,
                                     index.z+1+buffer[i].z);
                if(temp.CheckRange()&&TetrisBox[temp.x][temp.y][temp.z][0]==0)
                    continue;
                return;
            }
            index.z++;
            cur.z+=CubeLength;
        }
        else
        {
            for(int i=0;i<CubeSize;i++)
            {
                int Re_y=index.y-buffer[i].y;
                if(Re_y<0){ Re_y=0;}
                Index temp=new Index(index.x+buffer[i].x,
                        Re_y,
                        index.z-1+buffer[i].z);
                if(temp.CheckRange()&&TetrisBox[temp.x][temp.y][temp.z][0]==0)
                    continue;
                return;
            }
            index.z--;
            cur.z-=CubeLength;
        }
    }
    private void SetTetrisType()
    {
        TetrisType[0][0]=new Index(0,0,0);
        TetrisType[0][1]=new Index(0,1,0);
        TetrisType[0][2]=new Index(-1,1,0);
        TetrisType[0][3]=new Index(-1,2,0);

        TetrisType[1][0]=new Index(0,0,0);
        TetrisType[1][1]=new Index(1,0,0);
        TetrisType[1][2]=new Index(0,1,0);
        TetrisType[1][3]=new Index(0,2,0);

        TetrisType[2][0]=new Index(0,0,0);
        TetrisType[2][1]=new Index(1,0,0);
        TetrisType[2][2]=new Index(-1,0,0);
        TetrisType[2][3]=new Index(0,1,0);

        TetrisType[3][0]=new Index(0,0,0);
        TetrisType[3][1]=new Index(1,0,0);
        TetrisType[3][2]=new Index(1,1,0);
        TetrisType[3][3]=new Index(0,1,0);

        TetrisType[4][0]=new Index(0,0,0);
        TetrisType[4][1]=new Index(0,1,0);
        TetrisType[4][2]=new Index(0,2,0);
        TetrisType[4][3]=new Index(0,3,0);
        /*


        TetrisType[2][0].x=0f;TetrisType[2][0].y=0f;TetrisType[2][0].z=0f;
        TetrisType[2][1].x=0.1f;TetrisType[2][1].y=0f;TetrisType[2][1].z=0f;
        TetrisType[2][2].x=-0.1f;TetrisType[2][2].y=0f;TetrisType[2][2].z=0f;
        TetrisType[2][3].x=0f;TetrisType[2][3].y=0.1f;TetrisType[2][3].z=0f;

        TetrisType[3][0].x=0f;TetrisType[3][0].y=0f;TetrisType[3][0].z=0f;
        TetrisType[3][1].x=0f;TetrisType[3][1].y=0.1f;TetrisType[3][1].z=0f;
        TetrisType[3][2].x=0.1f;TetrisType[3][2].y=0.1f;TetrisType[3][2].z=0f;
        TetrisType[3][3].x=0.1f;TetrisType[3][3].y=0f;TetrisType[3][3].z=0f;

        TetrisType[4][0].x=0f;TetrisType[4][0].y=0f;TetrisType[4][0].z=0f;
        TetrisType[4][1].x=0f;TetrisType[4][1].y=0.1f;TetrisType[4][1].z=0f;
        TetrisType[4][2].x=0f;TetrisType[4][2].y=0.2f;TetrisType[4][2].z=0f;
        TetrisType[4][3].x=0f;TetrisType[4][3].y=-0.1f;TetrisType[4][3].z=0f;
        */
    }
}
