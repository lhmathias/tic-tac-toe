package com.example.toni.tictactoe;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class listviewDatabaza2 extends AppCompatActivity {
    String name_selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_databaza2);
        Bundle lojtari_r=getIntent().getExtras();
        name_selected =lojtari_r.getString("nameSelected");

        clsMbushja objmbushja=new clsMbushja(this);
        objmbushja.execute();

    }


    public class cls_listview2 {
        String player_1,player_2,results;

        public cls_listview2(String player1,String player2,String resultss)
        {
            this.setPlayer_1(player1);
            this.setPlayer_2(player2);
            this.setResults(resultss);
        }

        public String getPlayer_1() {
            return player_1;
        }

        public void setPlayer_1(String player_1) {
            this.player_1 = player_1;
        }

        public String getPlayer_2() {
            return player_2;
        }

        public void setPlayer_2(String player_2) {
            this.player_2 = player_2;
        }

        public String getResults() {
            return results;
        }

        public void setResults(String results) {
            this.results = results;
        }
    }

    public static class clsHolder2 {

        TextView txtShtylla1,txtShtylla2,txtShtylla3;

    }

    public class AdapteriLista extends ArrayAdapter {

        List list=new ArrayList();

        public void add(cls_listview2 object) {
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
                row=layoutinflater.inflate(R.layout.listviewdatabaza_row2,parent,false);
                objholder=new clsHolder2();
                objholder.txtShtylla1=(TextView)row.findViewById(R.id.txtShtylla1);
                objholder.txtShtylla2=(TextView)row.findViewById(R.id.txtShtylla2);
                objholder.txtShtylla3=(TextView)row.findViewById(R.id.txtShtylla3);
                row.setTag(objholder);
            }
            else
            {
                objholder=(clsHolder2)row.getTag();
            }

            cls_listview2 objlista=(cls_listview2)getItem(position);
            objholder.txtShtylla1.setText(objlista.getPlayer_1().toString());
            objholder.txtShtylla2.setText(objlista.getPlayer_2().toString());
            objholder.txtShtylla3.setText(objlista.getResults().toString());

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

    public class clsMbushja extends AsyncTask<Void,cls_listview2,Void> {
        Context ctx;
        Activity activity;
        ListView listview;
        AdapteriLista objadapteri;


        String whereClause = DatabaseHelper.COL_3+" =?";
        String[] whereArgs = new String[]{name_selected};

        String[] projections={DatabaseHelper.COL_1,DatabaseHelper.COL_2,DatabaseHelper.COL_3,DatabaseHelper.COL_4};
        SQLiteDatabase objDb = (new DatabaseHelper(getApplicationContext())).getReadableDatabase();
        Cursor objKursori = objDb.query(DatabaseHelper.TABLE_NAME,projections,whereClause,whereArgs,null,null,null);


        public clsMbushja(Context ctx)
        {
            this.ctx=ctx;
            activity=(Activity)ctx;
        }
        @Override
        protected Void doInBackground(Void... params) {
            listview =(ListView)activity.findViewById(R.id.display_listview);

            objadapteri=new AdapteriLista(ctx,R.layout.listviewdatabaza_row2);



            String Player1, Player2, Results;

          /*  objKursori.moveToFirst();
            if (objKursori.getCount() > 0) {
                for (int i = 0; i < objKursori.getCount(); i++) {
                    cls_listview 2 objlista = new cls_listview2(objKursori.getString(1));
                    al_databaza.add(objlista);

                    objKursori.moveToNext();
                }*/
            while (objKursori.moveToNext()) {
                Player1=objKursori.getString(objKursori.getColumnIndex(DatabaseHelper.COL_2));
                Player2=objKursori.getString(objKursori.getColumnIndex(DatabaseHelper.COL_3));
                Results=objKursori.getString(objKursori.getColumnIndex(DatabaseHelper.COL_4));

                cls_listview2 objlista=new cls_listview2(Player1,Player2,Results);
                publishProgress(objlista);


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void  params){

            listview.setAdapter(objadapteri);
        }

        @Override
        protected void onProgressUpdate(cls_listview2... values) {
            objadapteri.add(values[0]);
        }
    }
}
