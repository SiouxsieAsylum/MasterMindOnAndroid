package com.example.mcken.mastermind;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Arrays;

import static com.example.mcken.mastermind.Code.PEGS;


public class MainActivity extends AppCompatActivity {

    protected StringBuilder guess;
    protected static Code winningCode;
    protected String guessString;
    private static final String TAG = "MyActivity";
    private int currentID = 0;
    protected int holes;
    protected int lines;
    public RelativeLayout rl;
    public ArrayList<Integer> linearLayoutList;
    //public Integer[] linearLayoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setClassVars();
        getCurrentViewGroup();
        setClickableHole();

        Log.i(TAG, winningCode.secretCodeString);
    }


    public void setClassVars(){
        winningCode = new Code();
        holes = 4;
        lines = 10;

        rl = (RelativeLayout) findViewById(R.id.activity_main);
        guess = new StringBuilder();

        linearLayoutList = new ArrayList<Integer>();

        for (int i = 0; i <= lines; i++) {

            int ll = rl.getChildAt(i).getId();
            linearLayoutList.add(ll);

        }
    }

    //necessary?


/*So I'm going to just set the view in focus as the argument for all my methods
that will allow them to work without running a bullshit loop that will cause it to collapse
that's probably why it's crashing all of the time.

!!!You have not created a method yet that set the next hole cloicable only after a peg has been selected
!!!Do not forget that the count of holes may affect how the arrays iterate! Make sure that the guesspeg
    holes only affect imageviews 0-3, and feedback views only affect 4-7

Once setting gameplay, I think that gameplay should run as follows:
    -guess
        -set next button clickable up to 4 times
    -feedback
        -displayMatches
        -won? yes no
            -yes = game over display screen
            -no = nextFocusDown, set previous unclickable
    ad infinitum

 */


//
//    //current view group should be the next view group in focus
    protected LinearLayout getCurrentViewGroup() {
        LinearLayout currentLine = (LinearLayout) findViewById(linearLayoutList.get(currentID));
        Log.i(TAG, currentLine.toString());
        return currentLine;
    }

    protected void setClickableHole() {
        LinearLayout currentLine = getCurrentViewGroup();


        for (int j = 0; j < holes; j++) {
            ImageView hole = (ImageView) findViewById(currentLine.getChildAt(j).getId());

            hole.setClickable(true);
            hole.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));

            hole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pegSelect(view);
                }
            });
        }

    }

    //next method: setUnclickableHole: set views in  non-current Layouts unclickable unclickable after

    public void pegSelect(View pegHole) {
        final LinearLayout currentLine = getCurrentViewGroup();
        final int finalId = pegHole.getId();
        final ImageView pegHoleImage = (ImageView) findViewById(finalId);

                PopupMenu pegList = new PopupMenu(MainActivity.this, pegHoleImage);
                pegList.getMenuInflater().inflate(R.menu.peg_menu, pegList.getMenu());
                pegList.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem selectedPeg) {

                        Drawable icon = selectedPeg.getIcon();
//                       set tag in the oncreate and get tag here
                        Integer resource = (Integer) selectedPeg.getTag();

                        String peg = String.valueOf(PEGS.get(icon));
                        Log.i(TAG , PEGS.toString());

                        pegHoleImage.setImageDrawable(icon);
//                        Log.i(TAG, selectedPeg.getTag().toString());

                        int i = currentLine.indexOfChild(pegHoleImage);
                        guess.append(peg);
//                            Toast.makeText(MainActivity.this, selectedPeg.getTitle(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
                pegList.show();
    }


//    protected void setGuessButtonClickable() {
//
//        LinearLayout currentLine = getCurrentViewGroup();
//        Integer drawableCount = 0;
//        Integer stringArrCount = 0;
//
//        for (int j = 0; j < holes; j++) {
//            int i = currentLine.getChildAt(j).getId();
//            ImageView image = (ImageView) findViewById(i);
//            if (image.getDrawable() != null) {
//                drawableCount++;
//            }
//
//            if (guess[j] != null && guess[j] == PEGS.get(image.getDrawable())) {
//                stringArrCount++;
//            }
//
//        }
//
//        Button guessButton = (Button) findViewById(R.id.guessButton);
//        //stringArrCount == drawableCount ? guessButton.setClickable(true) : guessButton;
//        if (stringArrCount.equals(drawableCount)) {
//            guessButton.setClickable(true);
//            guessButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.buttonColor, null));
//
//            guessButton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    guessCheck();
//                }
//            });
//
//        }
//
//    }
//
     public boolean guessCheck(View button) {
        Button guessButton = (Button) findViewById(R.id.guessButton);
        boolean match = false;

        guessString = guess.toString();
        Log.i(TAG, guessString);

        if (winningCode.secretCodeString.equals(guessString)) {
            match = true;
        } else {
            currentID++;
            setClickableHole();
        }
        //displayMatches();
        Log.i(TAG, String.valueOf(match));
        return match;
    }

//    protected void displayMatches() {
//        Code guessCode = new Code(guessString);
//        int exacts = winningCode.countExactMatches(guessCode);
//        int nears = winningCode.countNearMatches(guessCode);
//
//        LinearLayout currentLine = getCurrentViewGroup();
//        GridLayout feedback = (GridLayout) findViewById(currentLine.getChildAt(holes).getId());
//
//        for (int i = 0; i <= exacts; i++) {
//            ImageView exactMatch = new ImageView(this);
//            exactMatch.setLayoutParams(new GridLayout.LayoutParams());
//            exactMatch.setImageResource(R.drawable.whitecircle);
//            feedback.addView(exactMatch);
//
//        }
//
//        for (int i = 0; i <= nears; i++) {
//            ImageView nearMatch = new ImageView(this);
//            nearMatch.setLayoutParams(new GridLayout.LayoutParams());
//            nearMatch.setImageResource(R.drawable.whitecircle);
//            feedback.addView(nearMatch);
//
//        }
//
//
//    }
}

//So now it loads! Yay! Where are we now?
/* are the imageviews clickable only when that linearlayout is in focus?
    Do the drawables translate to strings that can be compared?
    Does the comparison result in the proper win-lose protocol?
 */