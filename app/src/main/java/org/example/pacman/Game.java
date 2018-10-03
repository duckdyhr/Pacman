package org.example.pacman;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
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
    private TextView timerView;

    private int pacx, pacy;
    //the list of goldcoins - initially empty

    private Random rand = new Random();
    private ArrayList<GoldCoin> coins = new ArrayList<>();
    private int numberOfCoins = 10;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private int numberOfEnemies = 5;

    //a reference to the gameview
    private GameView gameView;
    private int h, w; //height and width of screen

    enum Direction {LEFT, RIGHT, UP, DOWN}

    private Timer movementTimer;
    private Timer gameTimer;
    private Direction direction;
    private boolean running;
    private int counter;
    private final int timeLimit = 60;

    public Game(Context context, TextView pointsView, TextView timerView) {
        this.context = context;
        this.pointsView = pointsView;
        this.timerView = timerView;
        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacman);
        Bitmap coinBitMapBig = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold_coin_shiny);
        coinBitMap = Bitmap.createScaledBitmap(coinBitMapBig, 140, 140, true);
        //Log.d("Game", "\tpacman size (h, w): " + pacBitmap.getHeight() + ", " + pacBitmap.getWidth());

        running = false;
        counter = timeLimit;
        direction = Direction.RIGHT;
        movementTimer = new Timer();
        movementTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                movementTimerMethod();
            }
        }, 0, 400); //0 indicates we start now, 200 is the number of miliseconds between each call

        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameTimerMethod();
            }
        }, 0, 1000); //going for no delay and 1 second
    }

    public void setGameView(GameView view) {
        this.gameView = view;
    }

    public void newGame() {
        running = false;
        pacx = 50;
        pacy = 400; //just some starting coordinates
        //reset the points
        points = 0;
        pointsView.setText(context.getResources().getString(R.string.points) + " " + points);
        counter = timeLimit;
        timerView.setText(context.getResources().getString(R.string.tv_timer) + " " + counter);

        //TODO initialize h and w better! In GameView? not pretty, it should only draw...
        h = 1373;
        w = 1080;
        coins = generateCoins(numberOfCoins);
        enemies = generateEnemies(numberOfEnemies);
        gameView.invalidate(); //redraw screen and set h+w
    }

    public void setSize(int h, int w) {
        this.h = h;
        this.w = w;
    }

    public ArrayList<GoldCoin> generateCoins(int size) {
        ArrayList<GoldCoin> result = new ArrayList<>();
        Log.d("Game", "generateCoins h, w: " + h + ", " + w);
        //Random rand = new Random();
        int x, y, xBound, yBound;
        for (int i = 0; i < size; i++) {
            xBound = w / coinBitMap.getWidth();
            yBound = h / coinBitMap.getHeight();
            Log.d("Game", "generateCoins i, x1, y1: " + i + ", " + xBound + ", " + yBound);
            x = rand.nextInt(xBound) * coinBitMap.getWidth();
            y = rand.nextInt(yBound) * coinBitMap.getHeight();
            result.add(new GoldCoin(x, y));
            Log.d("Game", "generateCoins i, x, y: " + i + ", " + x + ", " + y);
        }
        return result;
    }

    public ArrayList<Enemy> generateEnemies(int size) {
        ArrayList<Enemy> result = new ArrayList<>();
        int x, y, xBound, yBound;
        for (int i = 0; i < size; i++) {
            xBound = w / coinBitMap.getWidth();
            yBound = h / coinBitMap.getHeight();
            Log.d("Game", "generateCoins i, x1, y1: " + i + ", " + xBound + ", " + yBound);
            x = rand.nextInt(xBound) * coinBitMap.getWidth();
            y = rand.nextInt(yBound) * coinBitMap.getHeight();
            result.add(new Enemy(x, y));
            Log.d("Game", "generateCoins i, x, y: " + i + ", " + x + ", " + y);
        }
        return result;
    }

    private void movementTimerMethod() {
        Activity mainActivity = (AppCompatActivity) context;
        mainActivity.runOnUiThread(() -> {

            //This method runs in the same thread as the UI.
            // so we can draw
            if (running) {
                //counter++;
                //update the counter - notice this is NOT seconds in this example
                //you need TWO counters - one for the time and one for the pacman
                //textView.setText("Timer value: "+counter);
                //gameView.move(20); //move the pacman - you
                switch (direction) {
                    case LEFT:
                        movePacmanLeft(40);
                        break;
                    case RIGHT:
                        movePacmanRight(40);
                        break;
                    case UP:
                        movePacmanUp(40);
                        break;
                    case DOWN:
                        movePacmanDown(40);
                }
                //should call a method on your game class to move
                //the pacman instead of this
            }
        });
    }

    private void gameTimerMethod() {
        Activity mainActivity = (AppCompatActivity) context;
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    if(counter<=0){
                        timesUp();
                    }else{
                        counter--;
                        timerView.setText(context.getResources().getString(R.string.tv_timer) + " " + counter);
                    }
                }

            }
        });
    }

    public void timesUp(){
        //TODO other useful feedback/new game...
        Toast.makeText(context, "Times up!", Toast.LENGTH_LONG).show();
    }

    public void pauseGame() {
        running = false;
    }

    public void resumeGame() {
        running = true;
    }

    public void movePacmanRight(int pixels) {
        //still within our boundaries?
        if (pacx + pixels + pacBitmap.getWidth() < w) {
            direction = Direction.RIGHT;
            pacx = pacx + pixels;
            doCollisionCheck();
            gameView.invalidate();
            running = true;
        }
    }

    public void movePacmanLeft(int pixels) {
        direction = Direction.LEFT;
        if (pacx - pixels > 0) {
            pacx = pacx - pixels;
            doCollisionCheck();
            gameView.invalidate();
            running = true;
        }
    }

    public void movePacmanUp(int pixels) {
        direction = Direction.UP;
        if (pacy - pixels > 0) {
            pacy = pacy - pixels;
            doCollisionCheck();
            gameView.invalidate();
            running = true;
        }
    }

    public void movePacmanDown(int pixels) {
        direction = Direction.DOWN;
        if (pacy + pixels + pacBitmap.getHeight() < h) {
            pacy = pacy + pixels;
            doCollisionCheck();
            gameView.invalidate();
            running = true;
        }
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    //Draws from top-left corner
    public void doCollisionCheck() {
        //int[]box = {pacx, pacy, pacx+pacBitmap.getWidth(), pacy+pacBitmap.getHeight()};
        double dist;
        boolean allTaken = true; //or keep coinCounter...
        for (GoldCoin c : coins) {
            if (!c.isTaken()) {
                allTaken = false;
                dist = Math.sqrt((c.getCoiny() - pacy) * (c.getCoiny() - pacy) + (c.getCoinx() - pacx) * (c.getCoinx() - pacx));
                if (dist <= (coinBitMap.getWidth() / 2)) {
                    c.setTaken(true);
                    points += 1;
                    pointsView.setText(context.getString(R.string.tv_loot) + points);
                }
            }
        }
        if (allTaken) {
            lastCoinTaken();
        }
    }

    public void lastCoinTaken() {
        Toast.makeText(context, "Congratulations! Last coin taken", Toast.LENGTH_LONG).show();
    }

    public int getPacx() {
        return pacx;
    }

    public int getPacy() {
        return pacy;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<GoldCoin> getCoins() {
        return coins;
    }

    public Bitmap getPacBitmap() {
        return pacBitmap;
    }

    public Bitmap getCoinBitMap() {
        return coinBitMap;
    }
}
