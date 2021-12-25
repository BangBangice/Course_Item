package com.jnu.course_item.model;

import static java.lang.Math.random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.jnu.course_item.R;

public class Gopher {
    float x,y,radius;
    double direction;
    float maxWidth,maxHeight;
    Bitmap bitmap;

    public Gopher(float x, float y, float radius, float maxWidth, float maxHeight, Bitmap bitmap)
    {

        this.x=x;
        this.y=y;
        this.radius=radius;
        this.direction= random();
        this.maxHeight=maxHeight;
        this.maxWidth=maxWidth;
        this.bitmap=bitmap;
    }
    public void draw(Canvas canvas)
    {
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        canvas.drawBitmap(bitmap,x,y,paint);
       // canvas.drawCircle(x,y,radius,paint);
    }
    public void move()
    {
        this.x= (float) random()*maxWidth;
        this.y=(float) random()*maxHeight;
    }

    public boolean isShot(float touchedX, float touchedY) {
        double distance=(touchedX-this.x)*(touchedX-this.x)+(touchedY-this.y)*(touchedY-this.y);
        return distance<radius*radius;
    }
}
