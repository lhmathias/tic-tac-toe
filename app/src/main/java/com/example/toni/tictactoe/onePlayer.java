package com.example.toni.tictactoe;

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

public class onePlayer extends AppCompatActivity {


    //
    //


    //deklarimi
    static Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    TextView rezultati,rezultati1;
    static int turn_count = 1;
    static boolean turn = true; // standds for O;
    String[] varguFIELLESTAR = {"1", "2", "3", "4", "5", "6", "7", "8", "9"}; //ky veq sa per fillim ma nuk perdoreet
    List<String> listaVargut_Gjeneralja = new ArrayList<String>();
    //variabel globale per numer teke cp per qift njeriu;
    static int lojtariRadha;
    public int fituesi=0;

    Compute objCompute;
    public static String _xORo;
    public static String _vlera2;

    Button[] btn123 = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};


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


        Button[] buttonat={btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9};
       objCompute=new Compute(buttonat);







        //me i marr te dhenat ekstra
        Bundle lojtari_r=getIntent().getExtras();
        String lojtari_radha=lojtari_r.getString("radha_lojtari");
        //rezultati.setText(lojtari_radha);
        lojtariRadha=Integer.parseInt(lojtari_radha);



        Collections.addAll(listaVargut_Gjeneralja, varguFIELLESTAR);


        if(lojtariRadha==2)
        {
            setOX();
            _xORo="O";
            _vlera2="X";
        }
        else
        {
           _xORo="X";
            _vlera2="O";
        }
            //aksionat per butonat...
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;

                    button_click1(b);

                    if (listaVargut_Gjeneralja.isEmpty()) {

                    } else {
                     objCompute.oneMoveCompute(_xORo,_vlera2);
                    }
                }

            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Button b = (Button) v;

                    button_click1(b);

                    if (listaVargut_Gjeneralja.isEmpty()) {

                    } else {
                        objCompute.oneMoveCompute(_xORo,_vlera2);
                    }

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Button b = (Button) v;

                    button_click1(b);

                    if (listaVargut_Gjeneralja.isEmpty()) {

                    } else {
                        objCompute.oneMoveCompute(_xORo,_vlera2);
                    }

                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Button b = (Button) v;

                    button_click1(b);

                    if (listaVargut_Gjeneralja.isEmpty()) {

                    } else {
                        objCompute.oneMoveCompute(_xORo,_vlera2);
                    }


                }
            });
            btn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Button b = (Button) v;

                    button_click1(b);

                    if (listaVargut_Gjeneralja.isEmpty()) {

                    } else {
                        objCompute.oneMoveCompute(_xORo,_vlera2);
                    }

                }
            });
            btn6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Button b = (Button) v;

                    button_click1(b);

                    if (listaVargut_Gjeneralja.isEmpty()) {

                    } else {
                        objCompute.oneMoveCompute(_xORo,_vlera2);
                    }

                }
            });
            btn7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Button b = (Button) v;

                    button_click1(b);

                    if (listaVargut_Gjeneralja.isEmpty()) {

                    } else {
                        objCompute.oneMoveCompute(_xORo,_vlera2);
                    }

                }
            });
            btn8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Button b = (Button) v;

                    button_click1(b);

                    if (listaVargut_Gjeneralja.isEmpty()) {

                    } else {
                        objCompute.oneMoveCompute(_xORo,_vlera2);
                    }

                }
            });
            btn9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Button b = (Button) v;

                    button_click1(b);

                    if (listaVargut_Gjeneralja.isEmpty()) {

                    } else {
                        objCompute.oneMoveCompute(_xORo,_vlera2);
                    }

                }
            });

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        turn_count=1;
        listaVargut_Gjeneralja.clear();
        turn=true;
        finish();
    }


    public void button_click1(View v) {
        Button b = (Button) v;

        if (turn == true) {
            b.setText("O");
            turn = false;
        } else {
            b.setText("X");
            turn = true;
        }
        b.setEnabled(false);

        //merre ID qe e ka, shtine ne switch case 1 9 remove from gjeneralja qat nr
        Fonia(v);
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
            listaVargut_Gjeneralja.clear();
            if (turn == false) {
                //turni duhet te jeta false
                fituesi=1;
            } else if (turn == true) {
                fituesi=2;
            }
        } else if (turn_count > 9) {
            fituesi=3;
        }
        if(_thewinner==true || turn_count >9) {
            alert_dialog();
        }


    }






    //ne Main nese lojtari i pare ka lujt ateher turn=false, bene edhe simboli =x
    //if(turn) simboli="X";
    //else simboli="o";
    //Random


    public void setOX() {

        // me e kontrollu a u shtyp ky button.


        Random r = new Random();
        //me i fshi vlerat me buttonat e klikuar

        String c1 = listaVargut_Gjeneralja.get(r.nextInt(listaVargut_Gjeneralja.size()));
        listaVargut_Gjeneralja.remove(c1);

        switch (c1) {
            case "1":
                if (turn == true) {
                    btn1.setText("O");
                    turn = false;
                } else {
                    btn1.setText("X");
                    turn = true;
                }
                btn1.setEnabled(false);
                turn_count++;
                break;
            case "2":
                if (turn == true) {
                    btn2.setText("O");
                    turn = false;
                } else {
                    btn2.setText("X");
                    turn = true;
                }
                btn2.setEnabled(false);
                turn_count++;
                break;
            case "3":
                if (turn == true) {
                    btn3.setText("O");
                    turn = false;
                } else {
                    btn3.setText("X");
                    turn = true;
                }
                btn3.setEnabled(false);
                turn_count++;
                break;
            case "4":
                if (turn == true) {
                    btn4.setText("O");
                    turn = false;
                } else {
                    btn4.setText("X");
                    turn = true;
                }
                btn4.setEnabled(false);
                turn_count++;
                break;
            case "5":
                if (turn == true) {
                    btn5.setText("O");
                    turn = false;
                } else {
                    btn5.setText("X");
                    turn = true;
                }
                btn5.setEnabled(false);
                turn_count++;
                break;

            case "6":
                if (turn == true) {
                    btn6.setText("O");
                    turn = false;
                } else {
                    btn6.setText("X");
                    turn = true;
                }
                btn6.setEnabled(false);
                turn_count++;
                break;

            case "7":
                if (turn == true) {
                    btn7.setText("O");
                    turn = false;
                } else {
                    btn7.setText("X");
                    turn = true;
                }
                btn7.setEnabled(false);
                turn_count++;
                break;
            case "8":
                if (turn == true) {
                    btn8.setText("O");
                    turn = false;
                } else {
                    btn8.setText("X");
                    turn = true;
                }
                btn8.setEnabled(false);
                turn_count++;
                break;
            case "9":
                if (turn == true) {
                    btn9.setText("O");
                    turn = false;
                } else {
                    btn9.setText("X");
                    turn = true;
                }
                btn9.setEnabled(false);
                turn_count++;
                break;

            default:

                break;
        }
        //check_for_winner();

    }

    public void Fonia(View v) {
        switch (v.getId()) {
            case R.id.A1:
                listaVargut_Gjeneralja.remove("1");
                break;
            case R.id.A2:
                listaVargut_Gjeneralja.remove("2");
                break;
            case R.id.A3:
                listaVargut_Gjeneralja.remove("3");
                break;
            case R.id.B1:
                listaVargut_Gjeneralja.remove("4");
                break;
           case R.id.B2:
                listaVargut_Gjeneralja.remove("5");
                break;
            case R.id.B3:
                listaVargut_Gjeneralja.remove("6");
                break;
            case R.id.C1:
                listaVargut_Gjeneralja.remove("7");
                break;
            case R.id.C2:
                listaVargut_Gjeneralja.remove("8");
                break;
            case R.id.C3:
                listaVargut_Gjeneralja.remove("9");
                break;
            default:

                break;
        }
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
        if(fituesi==1 && lojtariRadha==1) {
            a_builder.setMessage("You win, do you want to play again");
        }
        else if(fituesi==2 && lojtariRadha==1)
        {
            a_builder.setMessage("You lose,do you want to play again");

        }
        if(fituesi==1 && lojtariRadha==2) {
            a_builder.setMessage("You lose, do you want to play again");
        }
        else if(fituesi==2 && lojtariRadha==2)
        {
            a_builder.setMessage("You win,do you want to play again");

        }
        else if(fituesi==3)
        {
            a_builder.setMessage("It's a draw,do you want to play again");
        }

        a_builder.setCancelable(false);
        a_builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enable_button();
                turn=true;
                turn_count=1;
                fituesi=0;
                Intent obj_intent=new Intent(getApplicationContext(),home.class);
                startActivity(obj_intent);

            }
        });
        a_builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enable_button();
                Collections.addAll(listaVargut_Gjeneralja, varguFIELLESTAR);
                turn=true;
                turn_count=1;
                fituesi=0;
                if(lojtariRadha==2)
                {
                    lojtariRadha=1;
                    _xORo="X";
                    _vlera2="O";
                }
                else
                {
                    lojtariRadha=2;
                    _xORo="O";
                    _vlera2="X";
                    setOX();

                }


            }
        });

        AlertDialog alert=a_builder.create();
        alert.setTitle("");
        alert.show();

    }

   class Compute
    {
        String mbajtesi;
        boolean uGjeneura=false;
        Button[] buttonat;
        public Compute(Button[] _buttonat)
        {
            buttonat=_buttonat;
        }

        public void oneMoveCompute(String xORo,String vlera2) {
            for(int i=0;i<=1;i++) {
                if ((buttonat[0].getText().equals(xORo)) && (buttonat[1].getText().equals(xORo)) && (buttonat[2].getText().equals(""))) {
                   if(i!=1) {
                       buttonat[2].setText(xORo);
                   }
                    else
                   {
                       buttonat[2].setText(vlera2);
                   }
                    buttonat[2].setEnabled(false);
                    uGjeneura=true;
                    listaVargut_Gjeneralja.remove("3");
                } else if ((buttonat[3].getText().equals(xORo)) && (buttonat[4].getText().equals(xORo)) && (buttonat[5].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[5].setText(xORo);
                    }
                    else
                    {
                        buttonat[5].setText(vlera2);
                    }
                    buttonat[5].setEnabled(false);
                    listaVargut_Gjeneralja.remove("6");
                    uGjeneura=true;
                } else if ((buttonat[6].getText().equals(xORo)) && (buttonat[7].getText().equals(xORo)) && (buttonat[8].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[8].setText(xORo);
                    }
                    else
                    {
                        buttonat[8].setText(vlera2);
                    }
                    buttonat[8].setEnabled(false);
                    listaVargut_Gjeneralja.remove("9");
                    uGjeneura=true;
                } else if ((buttonat[1].getText().equals(xORo)) && (buttonat[2].getText().equals(xORo)) && (buttonat[0].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[0].setText(xORo);
                    }
                    else
                    {
                        buttonat[0].setText(vlera2);
                    }
                    buttonat[0].setEnabled(false);
                    listaVargut_Gjeneralja.remove("1");
                    uGjeneura=true;
                } else if ((buttonat[4].getText().equals(xORo)) && (buttonat[5].getText().equals(xORo)) && (buttonat[3].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[3].setText(xORo);
                    }
                    else
                    {
                        buttonat[3].setText(vlera2);
                    }
                    buttonat[3].setEnabled(false);
                    listaVargut_Gjeneralja.remove("4");
                    uGjeneura=true;
                } else if ((buttonat[7].getText().equals(xORo)) && (buttonat[8].getText().equals(xORo)) && (buttonat[6].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[6].setText(xORo);
                    }
                    else
                    {
                        buttonat[6].setText(vlera2);
                    }
                    buttonat[6].setEnabled(false);
                    listaVargut_Gjeneralja.remove("7");
                    uGjeneura=true;
                } else if ((buttonat[0].getText().equals(xORo)) && (buttonat[2].getText().equals(xORo)) && (buttonat[1].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[1].setText(xORo);
                    }
                    else
                    {
                        buttonat[1].setText(vlera2);
                    }
                    buttonat[1].setEnabled(false);
                    listaVargut_Gjeneralja.remove("2");
                    uGjeneura=true;
                } else if ((buttonat[3].getText().equals(xORo)) && (buttonat[5].getText().equals(xORo)) && (buttonat[4].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[4].setText(xORo);
                    }
                    else
                    {
                        buttonat[4].setText(vlera2);
                    }
                    buttonat[4].setEnabled(false);
                    listaVargut_Gjeneralja.remove("5");
                    uGjeneura=true;
                } else if ((buttonat[6].getText().equals(xORo)) && (buttonat[8].getText().equals(xORo)) && (buttonat[7].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[7].setText(xORo);
                    }
                    else
                    {
                        buttonat[7].setText(vlera2);
                    }
                    buttonat[7].setEnabled(false);
                    listaVargut_Gjeneralja.remove("8");
                    uGjeneura=true;
                } else if ((buttonat[0].getText().equals(xORo)) && (buttonat[3].getText().equals(xORo)) && (buttonat[6].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[6].setText(xORo);
                    }
                    else
                    {
                        buttonat[6].setText(vlera2);
                    }
                    buttonat[6].setEnabled(false);
                    listaVargut_Gjeneralja.remove("7");
                    uGjeneura=true;
                } else if ((buttonat[1].getText().equals(xORo)) && (buttonat[4].getText().equals(xORo)) && (buttonat[7].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[7].setText(xORo);
                    }
                    else
                    {
                        buttonat[7].setText(vlera2);
                    }
                    buttonat[7].setEnabled(false);
                    listaVargut_Gjeneralja.remove("8");
                    uGjeneura=true;
                } else if ((buttonat[2].getText().equals(xORo)) && (buttonat[5].getText().equals(xORo)) && (buttonat[8].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[8].setText(xORo);
                    }
                    else
                    {
                        buttonat[8].setText(vlera2);
                    }
                    buttonat[8].setEnabled(false);
                    listaVargut_Gjeneralja.remove("9");
                    uGjeneura=true;
                } else if ((buttonat[3].getText().equals(xORo)) && (buttonat[6].getText().equals(xORo)) && (buttonat[0].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[0].setText(xORo);
                    }
                    else
                    {
                        buttonat[0].setText(vlera2);
                    }
                    buttonat[0].setEnabled(false);
                    listaVargut_Gjeneralja.remove("1");
                    uGjeneura=true;
                } else if ((buttonat[4].getText().equals(xORo)) && (buttonat[7].getText().equals(xORo)) && (buttonat[1].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[1].setText(xORo);
                    }
                    else
                    {
                        buttonat[1].setText(vlera2);
                    }
                    buttonat[1].setEnabled(false);
                    listaVargut_Gjeneralja.remove("2");
                    uGjeneura=true;
                } else if ((buttonat[5].getText().equals(xORo)) && (buttonat[8].getText().equals(xORo)) && (buttonat[2].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[2].setText(xORo);
                    }
                    else
                    {
                        buttonat[2].setText(vlera2);
                    }
                    buttonat[2].setEnabled(false);
                    listaVargut_Gjeneralja.remove("3");
                    uGjeneura=true;
                } else if ((buttonat[0].getText().equals(xORo)) && (buttonat[6].getText().equals(xORo)) && (buttonat[3].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[3].setText(xORo);
                    }
                    else
                    {
                        buttonat[3].setText(vlera2);
                    }
                    buttonat[3].setEnabled(false);
                    listaVargut_Gjeneralja.remove("4");
                    uGjeneura=true;
                } else if ((buttonat[1].getText().equals(xORo)) && (buttonat[7].getText().equals(xORo)) && (buttonat[4].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[4].setText(xORo);
                    }
                    else
                    {
                        buttonat[4].setText(vlera2);
                    }
                    buttonat[4].setEnabled(false);
                    listaVargut_Gjeneralja.remove("5");
                    uGjeneura=true;
                } else if ((buttonat[2].getText().equals(xORo)) && (buttonat[8].getText().equals(xORo)) && (buttonat[5].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[5].setText(xORo);
                    }
                    else
                    {
                        buttonat[5].setText(vlera2);
                    }
                    buttonat[5].setEnabled(false);
                    listaVargut_Gjeneralja.remove("6");
                    uGjeneura=true;
                } else if ((buttonat[0].getText().equals(xORo)) && (buttonat[4].getText().equals(xORo)) && (buttonat[8].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[8].setText(xORo);
                    }
                    else
                    {
                        buttonat[8].setText(vlera2);
                    }
                    buttonat[8].setEnabled(false);
                    listaVargut_Gjeneralja.remove("9");
                    uGjeneura=true;
                } else if ((buttonat[4].getText().equals(xORo)) && (buttonat[8].getText().equals(xORo)) && (buttonat[0].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[0].setText(xORo);
                    }
                    else
                    {
                        buttonat[0].setText(vlera2);
                    }
                    buttonat[0].setEnabled(false);
                    listaVargut_Gjeneralja.remove("1");
                    uGjeneura=true;
                } else if ((buttonat[0].getText().equals(xORo)) && (buttonat[8].getText().equals(xORo)) && (buttonat[4].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[4].setText(xORo);
                    }
                    else
                    {
                        buttonat[4].setText(vlera2);
                    }
                    buttonat[4].setEnabled(false);
                    listaVargut_Gjeneralja.remove("5");
                    uGjeneura=true;
                } else if ((buttonat[2].getText().equals(xORo)) && (buttonat[4].getText().equals(xORo)) && (buttonat[6].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[6].setText(xORo);
                    }
                    else
                    {
                        buttonat[6].setText(vlera2);
                    }
                    buttonat[6].setEnabled(false);
                    listaVargut_Gjeneralja.remove("7");
                    uGjeneura=true;
                } else if ((buttonat[6].getText().equals(xORo)) && (buttonat[4].getText().equals(xORo)) && (buttonat[2].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[2].setText(xORo);
                    }
                    else
                    {
                        buttonat[2].setText(vlera2);
                    }
                    buttonat[2].setEnabled(false);
                    listaVargut_Gjeneralja.remove("3");
                    uGjeneura=true;
                } else if ((buttonat[6].getText().equals(xORo)) && (buttonat[2].getText().equals(xORo)) && (buttonat[4].getText().equals(""))) {
                    if(i!=1) {
                        buttonat[4].setText(xORo);
                    }
                    else
                    {
                        buttonat[4].setText(vlera2);
                    }
                    buttonat[4].setEnabled(false);
                    listaVargut_Gjeneralja.remove("5");
                    uGjeneura=true;
                }


                if(uGjeneura==true)
                {
                    if (turn == true) {
                        turn = false;
                    } else {
                        turn = true;
                    }
                    break;
                }
                else {
                    mbajtesi = xORo;
                    xORo = vlera2;
                    vlera2 = mbajtesi;
                }

            }
            if(uGjeneura==false) {
                setOX();
            }
            else {
                uGjeneura=false;
                turn_count++;
            }


            check_for_winner();

        }

    }
}