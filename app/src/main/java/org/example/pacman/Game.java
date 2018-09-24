package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * This class should contain all your game logic
 */

public class Game {
    //context is a reference to the activity
    private Context context;
    private int points = 0; //how points do we have

    //bitmap of the pacman
    private Bitmap pacBitmap;
    private Bitmap coinBitMap;
    //textview reference to points
    private TextView pointsView;
    private int pacx, pacy;
    //the list of goldcoins - initially empty
    private ArrayList<GoldCoin> coins = new ArrayList<>();
    private int numberOfCoins = 10;
    //a reference to the gameview
    private GameView gameView;
    private int h,w; //height and width of screen

    public Game(Context context, TextView view)
    {
        this.context = context;
        this.pointsView = view;
        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacman);
        Bitmap coinBitMapBig = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold_coin_side);
        coinBitMap = Bitmap.createScaledBitmap(coinBitMapBig, 140, 140, true);
        Log.d("Game", "\tpacman size (h, w): " + pacBitmap.getHeight() + ", " + pacBitmap.getWidth());
    }

    public void setGameView(GameView view)
    {
        this.gameView = view;
    }


    public void newGame()
    {
        pacx = 50;
        pacy = 400; //just some starting coordinates
        //reset the points
        points = 0;
        pointsView.setText(context.getResources().getString(R.string.points)+" "+points);

        //TODO initialize h and w better!
        h=1373;
        w=1080;
        coins = generateCoins(numberOfCoins);
        gameView.invalidate(); //redraw screen and set h+w
    }

    public void setSize(int h, int w)
    {
        this.h = h;
        this.w = w;
    }

    public ArrayList<GoldCoin> generateCoins(int size){
        ArrayList<GoldCoin> result = new ArrayList<>();
        Log.d("Game", "generateCoins h, w: " + h + ", " + w);
        Random rand = new Random();
        int x, y, xBound, yBound;
        for(int i=0;i<size;i++){
            xBound = w/coinBitMap.getWidth();
            yBound = h/coinBitMap.getHeight();
            Log.d("Game", "generateCoins i, x1, y1: " + i + ", " + xBound + ", " + yBound);
            x = rand.nextInt(xBound)*coinBitMap.getWidth();
            y = rand.nextInt(yBound)*coinBitMap.getHeight();
            result.add(new GoldCoin(x, y));
            Log.d("Game", "generateCoins i, x, y: " + i + ", " + x + ", " + y);
        }
        return result;
    }

    public void movePacmanRight(int pixels)
    {
        //still within our boundaries?
        if (pacx+pixels+pacBitmap.getWidth()<w) {
            pacx = pacx + pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }
    public void movePacmanLeft(int pixels){
        if(pacx-pixels>0){
            pacx = pacx-pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    public void movePacmanUp(int pixels){
        if(pacy-pixels>0){
            pacy = pacy-pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    public void movePacmanDown(int pixels){
        if(pacy+pixels+pacBitmap.getHeight()<h){
            pacy = pacy+pixels;
            doCollisionCheck();
            gameView.invalidate();
        }
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    //Draws from top-left corner
    public void doCollisionCheck()
    {
        //int[]box = {pacx, pacy, pacx+pacBitmap.getWidth(), pacy+pacBitmap.getHeight()};
        double dist;
        boolean allTaken = true; //or keep coinCounter...
        for(GoldCoin c: coins){
            if(!c.isTaken()) {
                allTaken = false;
                dist = Math.sqrt((c.getCoiny() - pacy) * (c.getCoiny() - pacy) + (c.getCoinx() - pacx) * (c.getCoinx() - pacx));
                if (dist <= (coinBitMap.getWidth() / 2)) {
                    c.setTaken(true);
                    points += 1;
                    pointsView.setText(context.getString(R.string.tv_loot) + points);
                }
            }
        }
        if(allTaken){
            lastCoinTaken();
        }
    }

    public void lastCoinTaken(){
        Toast.makeText(context,"Congratulations! Last coin taken",Toast.LENGTH_LONG).show();
    }

    public int getPacx()
    {
        return pacx;
    }

    public int getPacy()
    {
        return pacy;
    }

    public int getPoints()
    {
        return points;
    }

    public ArrayList<GoldCoin> getCoins()
    {
        return coins;
    }

    public Bitmap getPacBitmap()
    {
        return pacBitmap;
    }

    public Bitmap getCoinBitMap(){
        return coinBitMap;
    }
}
