package com.example.flappybird2;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class BaseObject {
    protected float x,y;
    protected int height,width;
    protected Rect rect;
    protected Bitmap bm;

    public BaseObject() {
    }

    public BaseObject(float x, float y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Rect getRect() {
        return new Rect((int)this.x,(int)this.y,(int)this.x+width,(int)this.y+height);
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
