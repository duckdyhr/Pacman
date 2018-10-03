package org.example.pacman;

import android.graphics.Bitmap;

public class Enemy {
    private Bitmap pacBitmap; //or id?
    private int x, y;
    private int speed;
    private Game.Direction direction;
    private boolean isAlive;

    public Enemy(int initialX, int initialY){
        this.x = initialX;
        this.y = initialY;
        isAlive = true;
    }

    public void killEnemy(){
        isAlive = false;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Game.Direction getDirection() {
        return direction;
    }

    public void setDirection(Game.Direction direction) {
        this.direction = direction;
    }
}
