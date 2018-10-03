package org.example.pacman;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public class GameView extends View {

	Game game;
    int h,w; //used for storing our height and width of the view

	public void setGame(Game game)
	{
		this.game = game;
	}


	/* The next 3 constructors are needed for the Android view system,
	when we have a custom view.
	 */
	public GameView(Context context) {
		super(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}


	public GameView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context,attrs,defStyleAttr);
	}

	//In the onDraw we put all our code that should be
	//drawn whenever we update the screen.
    //drawBitmap: Draw the specified bitmap, with its top/left corner at (x,y), using the specified paint, transformed by the current matrix.
	@Override
	protected void onDraw(Canvas canvas) {
		//Here we get the height and weight
		h = canvas.getHeight();
		w = canvas.getWidth();
		//update the size for the canvas to the game.
		game.setSize(h,w);
		Log.d("GAMEVIEW","h = "+h+", w = "+w);
		//Making a new paint object
		Paint paint = new Paint();
		canvas.drawColor(Color.WHITE); //clear entire canvas to white color

        Log.d("GameView", "Gold coins: " + Arrays.toString(game.getCoins().toArray()));
		for(GoldCoin c : game.getCoins()){
		    if(!c.isTaken()){
			    canvas.drawBitmap(game.getCoinBitMap(), c.getCoinx(), c.getCoiny(), paint);
            }
		}
        //draw the pacman

        canvas.drawBitmap(game.getPacBitmap(), game.getPacx(),game.getPacy(), paint);
		super.onDraw(canvas);
	}
}
