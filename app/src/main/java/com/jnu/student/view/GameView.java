package com.jnu.student.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.jnu.student.R;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int NOT_A_VALIDATE_POSITION = -1;
    private GameLoopThread gameLoopThread;
    private float touchX= NOT_A_VALIDATE_POSITION;
    private float touchY=NOT_A_VALIDATE_POSITION;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        gameLoopThread = new GameLoopThread();
        gameLoopThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameLoopThread.end();

    }
    public boolean onTouchEvent(MotionEvent event){

        switch ((event.getAction())){
            case MotionEvent.ACTION_DOWN:
                this.touchX=NOT_A_VALIDATE_POSITION;
                this.touchY=NOT_A_VALIDATE_POSITION;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                this.touchX=event.getRawX()/this.getWidth();
                this.touchY=event.getRawY()/this.getHeight();
                break;
        }
        return true;
    }

    private class GameLoopThread extends Thread {
        boolean isLive=true;

        @Override
        public void run() {
            super.run();
            int hitNumber=0;
            ArrayList<GameSpriter>gameSpriters=new ArrayList<GameSpriter>();
            int iloop;
            for(iloop=0; iloop<2; ++iloop)
            {
                gameSpriters.add(new GameSpriter(Math.random(),Math.random(),R.drawable.baicai));
                gameSpriters.add(new GameSpriter(Math.random(),Math.random(), R.drawable.qingjiao));
            }
            Paint paint=new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setTextSize(24);
            paint.setStyle(Paint.Style.FILL);
            while(isLive){
                Canvas canvas=null;
                try{
                    canvas=GameView.this.getHolder().lockCanvas();
                    canvas.drawColor(Color.BLUE);
                    canvas.drawText("你打了"+hitNumber,100,100,paint);
                    for (GameSpriter gameSrpiter:
                         gameSpriters) {
                        if(gameSrpiter.detectCollision())
                            hitNumber++;
                        gameSrpiter.move(canvas);
                    }
                    for (GameSpriter gameSrpiter:
                            gameSpriters) {
                        gameSrpiter.dram(canvas);
                    }


                }finally {
                    if (canvas!=null){
                        GameView.this.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void end() {
            isLive=false;

        }
    }

    private class GameSpriter {
        private double relatedX;
        private double relatedY;
        private double direction;
        private final int imageResurceId;

        public GameSpriter(double relatedX, double relatedY, int imageResurceId) {
            this.relatedX=relatedX;
            this.relatedY=relatedY;
            this.imageResurceId=imageResurceId;
            this.direction=Math.random()*2*Math.PI;
        }

        public void dram(Canvas canvas) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),this.imageResurceId);
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,100,100,true);

            canvas.drawBitmap(scaledBitmap,(int)(canvas.getWidth()*this.relatedX)
                    ,(int)(canvas.getHeight()*this.relatedY),null);
        }

        public void move(Canvas canvas) {
            this.relatedY+=Math.sin(this.direction)*0.05;
            this.relatedX+=Math.cos(this.direction)*0.05;
            if(this.relatedY>1)this.relatedY=0;
            if(this.relatedY<0)this.relatedY=1;
            if(this.relatedX>1)this.relatedX=0;
            if(this.relatedX<0)this.relatedX=1;
            if(Math.random()<0.1)this.direction=Math.random()*2*Math.PI;
        }

        public boolean detectCollision() {
            double distanceX=Math.abs((this.relatedX-GameView.this.touchX));
            double distanceY=Math.abs((this.relatedY-GameView.this.touchY));
            if(distanceX<0.01 && distanceY<0.01)
            {
                return true;
            }
            return false;
        }
    }
}