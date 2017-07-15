package com.example.toni.tictactoe;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class listviewDatabaza extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_databaza);

        clsMbushja objmbushja=new clsMbushja(this);
        objmbushja.execute();

        ListView listview = (ListView)findViewById(R.id.display_lista);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tagText = (TextView)view.findViewById(R.id.txtRreshti);
                String tag = tagText.getText().toString();
                Intent intent_databaza = new Intent(getApplicationContext(), listviewDatabaza2.class);
                intent_databaza.putExtra("nameSelected",tag);
                startActivity(intent_databaza);

            }
        });




    }


    public class cls_listview {
        String player_2;

        public cls_listview(String player2)
        {
            this.setPlayer_2(player2);
        }


        public String getPlayer_2() {
            return player_2;
        }

        public void setPlayer_2(String player_2) {
            this.player_2 = player_2;
        }

    }

    public static class clsHolder2 {

        TextView txtRreshti;

    }

    public class AdapteriLista extends ArrayAdapter {

        List list=new ArrayList();

        public void add(cls_listview object) {
            list.add(object);
            super.add(object);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row=convertView;
            clsHolder2 objholder;

            if(row==null)
            {
                LayoutInflater layoutinflater=(LayoutInflater)this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                row=layoutinflater.inflate(R.layout.listviewdatabaza_row,parent,false);
                objholder=new clsHolder2();
                objholder.txtRreshti=(TextView)row.findViewById(R.id.txtRreshti);
                row.setTag(objholder);
            }
            else
            {
                objholder=(clsHolder2)row.getTag();
            }

            cls_listview objlista=(cls_listview)getItem(position);
            objholder.txtRreshti.setText(objlista.getPlayer_2().toString());

            return row;
        }



        @Nullable
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        public AdapteriLista(Context context, int resource) {
            super(context, resource);

        }

    }
    public class clsMbushja extends AsyncTask<Void,cls_listview,Void> {
        Context ctx;
        Activity activity;
        ListView listview;
        AdapteriLista objadapteri;

        String[] projections={DatabaseHelper.COL_1,DatabaseHelper.COL_2,DatabaseHelper.COL_3,DatabaseHelper.COL_4};
        SQLiteDatabase objDb = (new DatabaseHelper(getApplicationContext())).getReadableDatabase();
        Cursor objKursori = objDb.query(DatabaseHelper.TABLE_NAME,projections,null,null,DatabaseHelper.COL_3,null,null);


        public clsMbushja(Context ctx)
        {
            this.ctx=ctx;
            activity=(Activity)ctx;
        }
        @Override
        protected Void doInBackground(Void... params) {
            listview =(ListView)activity.findViewById(R.id.display_lista);

            objadapteri=new AdapteriLista(ctx,R.layout.listviewdatabaza_row);

            String Player2;
            while (objKursori.moveToNext()) {
                Player2=objKursori.getString(objKursori.getColumnIndex(DatabaseHelper.COL_3));
                cls_listview objlista=new cls_listview(Player2);
                publishProgress(objlista);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void  params){

            listview.setAdapter(objadapteri);
        }

        @Override
        protected void onProgressUpdate(cls_listview... values) {
            objadapteri.add(values[0]);
        }
    }
}
