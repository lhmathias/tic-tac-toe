package com.example.toni.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by TONI on 5/12/2017.
 */

public class home extends AppCompatActivity {

    Button btnOnePlayer,btnTwoPlayer,btnBluetooth;
    DatabaseHelper db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        db = new DatabaseHelper(this);


        btnOnePlayer = (Button) findViewById(R.id.btnOnePlayer);
        btnTwoPlayer = (Button) findViewById(R.id.btnTwoPlayer);
        btnBluetooth = (Button) findViewById(R.id.btnBluetooth);


        //krijimi ngjarjev per buttonat


        btnOnePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //me na shfaq se kush me lujt i pari
                final Dialog dialog = new Dialog(home.this);//context
                dialog.setContentView(R.layout.computer_select);

                // set the custom dialog components - text, image and button

                Button btnCommputervsPlayer = (Button) dialog.findViewById(R.id.btnCommputervsPlayer);
                Button btnPlayervsComputer = (Button) dialog.findViewById(R.id.btnPlayervsComputer);

                // if button is clicked, close the custom dialog
                btnCommputervsPlayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent_onePlayer = new Intent(getApplicationContext(), onePlayer.class);
                        intent_onePlayer.putExtra("radha_lojtari", "2");
                        startActivity(intent_onePlayer);
                    }
                });
                btnPlayervsComputer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent_onePlayer = new Intent(getApplicationContext(), onePlayer.class);
                        intent_onePlayer.putExtra("radha_lojtari", "1");
                        startActivity(intent_onePlayer);

                    }
                });
                dialog.show();
            }
        });

        btnTwoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_twoPlayers = new Intent(getApplicationContext(), twoPlayers.class);
                startActivity(intent_twoPlayers);
            }
        });

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_bluetooth = new Intent(getApplicationContext(), activity_main.class);
                startActivity(intent_bluetooth);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflater=getMenuInflater();
        menuinflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId())
        {
            case R.id.item1:
                Intent intent_databaza=new Intent(getApplicationContext(),listviewDatabaza.class);
                startActivity(intent_databaza);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}