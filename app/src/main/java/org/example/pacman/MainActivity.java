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

        gameView = findViewById(R.id.gameView);
        TextView pointsView = findViewById(R.id.tv_points);
        TextView timerView = findViewById(R.id.tv_timer);

        game = new Game(this, pointsView, timerView);
        game.setGameView(gameView);
        gameView.setGame(game);

        Button btnRight = findViewById(R.id.btn_moveRight);
        //listener of our pacman, when somebody clicks it
        btnRight.setOnClickListener(
                (View view) -> game.movePacmanRight(stepsize));

        Button btnLeft = findViewById(R.id.btn_moveLeft);
        btnLeft.setOnClickListener(
                view -> game.movePacmanLeft(stepsize));

        Button btnUp = findViewById(R.id.btn_moveUp);
        btnUp.setOnClickListener(
                view -> game.movePacmanUp(stepsize)
        );

        Button btnDown = findViewById(R.id.btn_moveDown);
        btnDown.setOnClickListener(
                view -> game.movePacmanDown(stepsize)
        );

        game.newGame();
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
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_newGame) {
            //Toast.makeText(this,"New Game clicked",Toast.LENGTH_LONG).show();
            game.newGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickNewGame(View view) {
        game.newGame();
    }

    public void onClickResume(View view) {
        game.resumeGame();
    }

    public void onClickPause(View view) {
        game.pauseGame();
    }
}
