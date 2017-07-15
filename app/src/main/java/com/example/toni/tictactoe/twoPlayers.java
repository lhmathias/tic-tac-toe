package com.example.toni.tictactoe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by TONI on 5/12/2017.
 */

public class twoPlayers extends AppCompatActivity implements View.OnClickListener{

    //varibala qe percjllet fituesiin
    public int fituesi=0;


    //deklarimi
    static Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    int turn_count=0;
    boolean turn = true; // standds for O;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.table_layout_activity);



        //

        //thirret algoritmi mas pari

        btn1 = (Button) findViewById(R.id.A1);
        btn2 = (Button) findViewById(R.id.A2);
        btn3 = (Button) findViewById(R.id.A3);
        btn4 = (Button) findViewById(R.id.B1);
        btn5 = (Button) findViewById(R.id.B2);
        btn6 = (Button) findViewById(R.id.B3);
        btn7 = (Button) findViewById(R.id.C1);
        btn8 = (Button) findViewById(R.id.C2);
        btn9 = (Button) findViewById(R.id.C3);



        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);







    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        if (turn == true) {
            b.setText("O");
            turn = false;
        } else {
            b.setText("X");
            turn = true;
        }
        b.setEnabled(false);


         turn_count++;

        check_for_winner();

    }



    public void check_for_winner() {
        boolean _thewinner = false;

        //kontrollimi horizontal
        if (btn1.getText() == btn2.getText() && btn2.getText() == btn3.getText() && !btn1.isEnabled()) {
            _thewinner = true;
        } else if (btn4.getText() == btn5.getText() && btn5.getText() == btn6.getText() && !btn4.isEnabled()) {
            _thewinner = true;
        } else if (btn7.getText() == btn8.getText() && btn8.getText() == btn9.getText() && !btn7.isEnabled()) {
            _thewinner = true;
        }
        //kontrollimi vertikal

        else if (btn1.getText() == btn4.getText() && btn4.getText() == btn7.getText() && !btn1.isEnabled()) {
            _thewinner = true;
        } else if (btn2.getText() == btn5.getText() && btn5.getText() == btn8.getText() && !btn2.isEnabled()) {
            _thewinner = true;
        } else if (btn3.getText() == btn6.getText() && btn6.getText() == btn9.getText() && !btn3.isEnabled()) {
            _thewinner = true;
        }


        //kontrollimi diagonal

        else if (btn1.getText() == btn5.getText() && btn5.getText() == btn9.getText() && !btn1.isEnabled()) {
            _thewinner = true;
        } else if (btn3.getText() == btn5.getText() && btn5.getText() == btn7.getText() && !btn3.isEnabled()) {
            _thewinner = true;
        }

        if (_thewinner == true)
        {
           // display_button();
            if (turn == false) {
                fituesi=1;// per O

            } else if (turn == true) {
                fituesi=2;
            }



        } else if (turn_count == 9) {

            fituesi=3;
        }
        if(_thewinner==true || turn_count==9)
        alert_dialog();

    }

    public void enable_button() {
        Button v1[]={(Button)findViewById(R.id.A1),(Button)findViewById(R.id.A2),(Button)findViewById(R.id.A3),
                (Button)findViewById(R.id.B1),(Button)findViewById(R.id.B2),(Button)findViewById(R.id.B3),
                (Button)findViewById(R.id.C1),(Button)findViewById(R.id.C2),(Button)findViewById(R.id.C3)};

        for (int i = 0; i < v1.length; i++) {
            {
                v1[i].setEnabled(true);
                v1[i].setText("");
            }
        }
    }


    public void alert_dialog()
    {
        AlertDialog.Builder a_builder=new AlertDialog.Builder(this);// me mainacitivty
        if(fituesi==1) {
            a_builder.setMessage("Player with O win, do you want to play again!");
        }
        else if(fituesi==2)
        {
            a_builder.setMessage("Player with x win, do you want to play again!");

        }
        else if(fituesi==3)
        {
            a_builder.setMessage("It's a draw, do you want to play again!");
        }

        a_builder.setCancelable(false);
        a_builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enable_button();
                turn=true;
                turn_count=0;
                fituesi=0;

                Intent obj_intent=new Intent(getApplicationContext(),home.class);
                startActivity(obj_intent);

            }
        });
        a_builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                enable_button();
                turn=true;
                turn_count=0;
                fituesi=0;

            }
        });

        AlertDialog alert=a_builder.create();
        alert.setTitle("");
        alert.show();

    }
}