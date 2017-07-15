package com.example.toni.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class activity_main extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";

    DatabaseHelper myDB;


    String deviceName;
    //variblatt per lojee
    static Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    static int turn_count = 1;
    static boolean turn = true; // standds for O;
    //radha me kliku ...
    static boolean radha_me_kliku=true;

    public int fituesi=0;


    //variablat per bluetooth




    BluetoothAdapter mBluetoothAdapter;
    Button btnEnableDisable_Discoverable;

    BluetoothConnectionService mBluetoothConnection;

    Button btnStartConnection;
    Button btnSend;

    EditText etSend;

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    BluetoothDevice mBTDevice;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();

    public DeviceListAdapter mDeviceListAdapter;

    ListView lvNewDevices;


    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");

                        break;
                }

            }
        }
    };


    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;


                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
        //unregisterReceiver(mBroadcastReceiver1);
        //unregisterReceiver(mBroadcastReceiver2);
        //unregisterReceiver(mBroadcastReceiver3);
      //  unregisterReceiver(mBroadcastReceiver4);
        //mBluetoothAdapter.cancelDiscovery();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        turn_count=1;
        turn=true;
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v1);

        myDB=new DatabaseHelper(this);
        //pjesa e kodit per insertim


        final Button btnONOFF = (Button) findViewById(R.id.btnONOFF);
        btnEnableDisable_Discoverable = (Button) findViewById(R.id.btnDiscoverable_on_off);
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();

        btnStartConnection = (Button) findViewById(R.id.btnStartConnection);



        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("incomingMessage"));

        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lvNewDevices.setOnItemClickListener(activity_main.this);


        btnONOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: enabling/disabling bluetooth.");
                enableDisableBT();
            }
        });

        btnStartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    startConnection();

                    try {
                        View v= findViewById(R.id.Linear_layout1);// change id here
                        v.setVisibility(View.GONE);


                        View v1= findViewById(R.id.table_layout);// change id here
                        v1.setVisibility(View.VISIBLE);


                    } catch (Exception e1)
                    {

                        Toast toast=Toast.makeText(getApplicationContext(),"Something goes wrong in opening game.",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                catch (Exception ex)
                {
                    Toast toast=Toast.makeText(getApplicationContext(),"Something goes wrong! Did you select any player?",Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });




       /////////////////////////// //variablatt per lojeee/////////////////

        btn1 = (Button) findViewById(R.id.A1);
        btn2 = (Button) findViewById(R.id.A2);
        btn3 = (Button) findViewById(R.id.A3);
        btn4 = (Button) findViewById(R.id.B1);
        btn5 = (Button) findViewById(R.id.B2);
        btn6 = (Button) findViewById(R.id.B3);
        btn7 = (Button) findViewById(R.id.C1);
        btn8 = (Button) findViewById(R.id.C2);
        btn9 = (Button) findViewById(R.id.C3);






            //aksionat per butonat...
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radha_me_kliku == true)
                    {
                    Button b = (Button) v;
                    button_click1(b);
                    String vlera = "null";
                    if (turn == true) {
                        vlera = "1,o";
                    } else if (turn == false) {
                        vlera = "1,x";
                    }

                    byte[] bytes = vlera.getBytes();
                    mBluetoothConnection.write(bytes);
                        radha_me_kliku=false;
                }
                    else
                    {
                    }
                }
            });


            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radha_me_kliku == true) {

                        Button b = (Button) v;
                        button_click1(b);
                        String vlera = "null";
                        if (turn == true) {
                            vlera = "2,o";
                        } else if (turn == false) {
                            vlera = "2,x";
                        }
                        byte[] bytes = vlera.getBytes();
                        mBluetoothConnection.write(bytes);
                        radha_me_kliku=false;
                    }
                    else {
                    }

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radha_me_kliku == true) {
                        Button b = (Button) v;
                        button_click1(b);

                        String vlera = "null";
                        if (turn == true) {
                            vlera = "3,o";
                        } else if (turn == false) {
                            vlera = "3,x";
                        }
                        byte[] bytes = vlera.getBytes();
                        mBluetoothConnection.write(bytes);
                        radha_me_kliku=false;
                    }
                    else {
                    }


                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radha_me_kliku == true) {
                        Button b = (Button) v;
                        button_click1(b);

                        String vlera = "null";
                        if (turn == true) {
                            vlera = "4,o";
                        } else if (turn == false) {
                            vlera = "4,x";
                        }
                        byte[] bytes = vlera.getBytes();
                        mBluetoothConnection.write(bytes);
                        radha_me_kliku=false;
                    }
                    else {
                    }


                }
            });
            btn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radha_me_kliku == true) {
                        Button b = (Button) v;
                        button_click1(b);

                        String vlera = "null";
                        if (turn == true) {
                            vlera = "5,o";
                        } else if (turn == false) {
                            vlera = "5,x";
                        }
                        byte[] bytes = vlera.getBytes();
                        mBluetoothConnection.write(bytes);
                        radha_me_kliku=false;
                    }
                    else {
                    }


                }
            });
            btn6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radha_me_kliku == true) {
                        Button b = (Button) v;
                        button_click1(b);

                        String vlera = "null";
                        if (turn == true) {
                            vlera = "6,o";
                        } else if (turn == false) {
                            vlera = "6,x";
                        }
                        byte[] bytes = vlera.getBytes();
                        mBluetoothConnection.write(bytes);
                        radha_me_kliku=false;
                    }
                    else {
                    }

                }
            });
            btn7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radha_me_kliku == true) {
                        Button b = (Button) v;
                        button_click1(b);

                        String vlera = "null";
                        if (turn == true) {
                            vlera = "7,o";
                        } else if (turn == false) {
                            vlera = "7,x";
                        }
                        byte[] bytes = vlera.getBytes();
                        mBluetoothConnection.write(bytes);
                        radha_me_kliku=false;
                    }
                    else {
                    }


                }
            });
            btn8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radha_me_kliku == true) {
                        Button b = (Button) v;
                        button_click1(b);

                        String vlera = "null";
                        if (turn == true) {
                            vlera = "8,o";
                        } else if (turn == false) {
                            vlera = "8,x";
                        }
                        byte[] bytes = vlera.getBytes();
                        mBluetoothConnection.write(bytes);
                        radha_me_kliku=false;
                    }
                    else {
                    }
                }
            });
            btn9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radha_me_kliku == true) {
                        Button b = (Button) v;
                        button_click1(b);

                        String vlera = "null";
                        if (turn == true) {
                            vlera = "9,o";
                        } else if (turn == false) {
                            vlera = "9,x";
                        }
                        byte[] bytes = vlera.getBytes();
                        mBluetoothConnection.write(bytes);
                        radha_me_kliku=false;
                    }
                    else{

                    }


                }
            });





    }


    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String text1 = intent.getStringExtra("theMessage");

            String new_text[]=text1.split(",");

            String text=new_text[0];
            String turni=new_text[1];

            switch (text) {
                case "1":
                    if(turni.equals("o")) {
                        btn1.setText("O");
                        turn=false;


                    }
                    else if(turni.equals("x"))
                    {
                        btn1.setText("X");
                        turn=true;

                    }
                    btn1.setEnabled(false);
                    break;

                case "2":

                    if(turni.equals("o")) {
                        btn2.setText("O");
                        turn=false;
                    }
                    else if(turni.equals("x"))
                    {
                        btn2.setText("X");
                        turn=true;

                    }
                    btn2.setEnabled(false);
                    break;
                case "3":

                    if(turni.equals("o")) {
                        btn3.setText("O");
                        turn=false;


                    }
                    else if(turni.equals("x"))
                    {
                        btn3.setText("X");
                        turn=true;

                    }
                    btn3.setEnabled(false);
                    break;

                case "4":

                    if(turni.equals("o")) {
                        btn4.setText("O");
                        turn=false;


                    }
                    else if(turni.equals("x"))
                    {
                        btn4.setText("X");
                        turn=true;

                    }
                    btn4.setEnabled(false);
                    break;
                case "5":

                    if(turni.equals("o")) {
                        btn5.setText("O");
                        turn=false;


                    }
                    else if(turni.equals("x"))
                    {
                        btn5.setText("X");
                        turn=true;

                    }
                    btn5.setEnabled(false);
                    break;

                case "6":
                    if(turni.equals("o")) {
                        btn6.setText("O");
                        turn=false;


                    }
                    else if(turni.equals("x"))
                    {
                        btn6.setText("X");
                        turn=true;

                    }
                    btn6.setEnabled(false);
                    break;
                case "7":

                    if(turni.equals("o")) {
                        btn7.setText("O");
                        turn=false;


                    }
                    else if(turni.equals("x"))
                    {
                        btn7.setText("X");
                        turn=true;

                    }
                    btn7.setEnabled(false);
                    break;
                case "8":
                    if(turni.equals("o")) {
                        btn8.setText("O");
                        turn=false;


                    }
                    else if(turni.equals("x"))
                    {
                        btn8.setText("X");
                        turn=true;

                    }
                    btn8.setEnabled(false);
                    break;
                case "9":

                    if(turni.equals("o")) {
                        btn9.setText("O");
                        turn=false;


                    }
                    else if(turni.equals("x"))
                    {
                        btn9.setText("X");
                        turn=true;

                    }
                    btn9.setEnabled(false);
                    break;

            }
            turn_count++;
            check_for_winner();
            radha_me_kliku=true;

        }
    };



    //create method for starting connection
//***remember the conncction will fail and app will crash if you haven't paired first
    public void startConnection() {
        startBTConnection(mBTDevice, MY_UUID_INSECURE);
    }

    /**
     * starting chat service method
     */
    public void startBTConnection(BluetoothDevice device, UUID uuid) {
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection.startClient(device, uuid);
    }


    public void enableDisableBT() {
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
        if (mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: disabling BT.");
            mBluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }

    }


    public void btnEnableDisable_Discoverable(View view) {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2, intentFilter);

    }

    public void btnDiscover(View view) {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if (!mBluetoothAdapter.isDiscovering()) {

            //check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     * <p>
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //first cancel discovery because its very memory intensive.
        mBluetoothAdapter.cancelDiscovery();

        Log.d(TAG, "onItemClick: You Clicked on a device.");
        deviceName = mBTDevices.get(i).getName();
        String deviceAddress = mBTDevices.get(i).getAddress();

        Log.d(TAG, "onItemClick: deviceName = " + deviceName);
        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d(TAG, "Trying to pair with " + deviceName);
            mBTDevices.get(i).createBond();

            mBTDevice = mBTDevices.get(i);
            mBluetoothConnection = new BluetoothConnectionService(activity_main.this);

            //per me startu klasa e ree

        }
    }




    //metodat per lojeee

    public void button_click1(View v) {
        Button b = (Button) v;

        if (turn == true) {
            b.setText("O");
        } else {
            b.setText("X");
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
            if (turn == true) {
                fituesi=1;// per O

            } else if (turn == false) {
                fituesi=2;
            }

        } else if (turn_count > 9) {

            fituesi=3;
        }
        if(_thewinner==true || turn_count >9) {
           alert_dialog();

        }


    }

    public void alert_dialog()
    {
        String db_fituesi=null;
        AlertDialog.Builder a_builder=new AlertDialog.Builder(this);// me mainacitivty
        if(fituesi==1) {
            a_builder.setMessage("You won, do you want to play again");
            db_fituesi="Won";

        }
        else if(fituesi==2)
        {
            a_builder.setMessage("You lose,do you want to play again");
            db_fituesi="Lose";

        }
        else if(fituesi==3)
        {
            a_builder.setMessage("It's a draw,do you want to play again");
            db_fituesi="Draw";
        }

        a_builder.setCancelable(false);
        a_builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enable_button();
                turn=true;
                turn_count=1;
                fituesi=0;
                radha_me_kliku=true;

                Intent obj_intent=new Intent(getApplicationContext(),home.class);
                startActivity(obj_intent);

            }
        });
        a_builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                enable_button();
                turn=true;
                turn_count=1;
                fituesi=0;
                radha_me_kliku=true;

            }
        });

        AlertDialog alert=a_builder.create();
        alert.setTitle("");
        alert.show();

        myDB=new DatabaseHelper(this);
        boolean inserted=myDB.insertData(mBluetoothAdapter.getName(),deviceName,db_fituesi);
        if(inserted)
        {
            Toast.makeText(activity_main.this,mBluetoothAdapter.getName()+" "+deviceName,Toast.LENGTH_LONG);
        }
        else
        {
            Toast.makeText(activity_main.this,mBluetoothAdapter.getName()+" "+deviceName,Toast.LENGTH_LONG);
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





}