package org.example.pacman;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    //reference to the main view
    GameView gameView;
    //reference to the game class.
    Game game;
    private final int stepsize = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //saying we want the game to run in one mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        gameView =  findViewById(R.id.gameView);
        TextView tv_points = findViewById(R.id.points);

        game = new Game(this,tv_points);
        game.setGameView(gameView);
        gameView.setGame(game);

        game.newGame();

        Button btnRight = findViewById(R.id.moveRight);
        //listener of our pacman, when somebody clicks it
        btnRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        game.movePacmanRight(stepsize);
                    }
            });

        Button btnLeft = findViewById(R.id.moveLeft);
        btnLeft.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        game.movePacmanLeft(stepsize);
                    }
                });

        Button btnUp = findViewById(R.id.moveUp);
        btnUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        game.movePacmanUp(stepsize);
                    }
                }
        );

        Button btnDown = findViewById(R.id.moveDown);
        btnDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        game.movePacmanDown(stepsize);
                    }
                }
        );
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this,"Settings clicked",Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_newGame) {
            Toast.makeText(this,"New Game clicked",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickNewGame(View view) {
        game.newGame();
    }
}
