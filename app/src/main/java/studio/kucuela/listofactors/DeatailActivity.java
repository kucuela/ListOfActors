package studio.kucuela.listofactors;


import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import studio.kucuela.listofactors.db.DataBaseHelper;
import studio.kucuela.listofactors.objects.Beleska;


public class DeatailActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    Beleska beleska;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podaci);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        //toolbar.setTitle("Details");
        //toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);

        int position = getIntent().getIntExtra("POSITION", 0);
        izmena(position);
    }



    public void showMessage(String text, String newGlumac) {
        SharedPreferences st = PreferenceManager.getDefaultSharedPreferences(this);
        String name = st.getString("message", "Toast");
        if (name.equals("Toast")) {
            Toast.makeText(this, text + "\n"+ newGlumac, Toast.LENGTH_LONG).show();
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.ic_action_name);
            builder.setContentTitle(text);
            builder.setContentText(newGlumac);


            // Shows notification with the notification manager (notification ID is used to update the notification later on)
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, builder.build());
        }
    }


    private void refreshBeleska() {

        ListView listview = (ListView) findViewById(R.id.listaGlumaca);

        if (listview != null) {
            ArrayAdapter<Beleska> adapter = (ArrayAdapter<Beleska>) listview.getAdapter();

            if (adapter != null) {
                try {
                    adapter.clear();
                    List<Beleska> list = getDatabaseHelper().getBeleskaDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void izmena(int position) {
        try {
            beleska = getDatabaseHelper().getBeleskaDao().queryForId(position);

            Log.d("ZIL", "YYYYYYYYYYYYY");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (beleska != null) {
            TextView naslov = (TextView) findViewById(R.id.naslov);
            naslov.setText(beleska.getNaslov());

            TextView opis = (TextView) findViewById(R.id.opis);
            opis.setText(beleska.getOpis());



            TextView datum = (TextView) findViewById(R.id.datum);
            datum.setText(beleska.getDatum());

            TextView ocena = (TextView) findViewById(R.id.ocena);
            ocena.setText(beleska.getOcena());


        }

    }


    public DataBaseHelper getDatabaseHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }
        return dataBaseHelper;
    }

    @Override
    public void onDestroy() {
        Log.d("XXX", "Main onDESTROY");
        super.onDestroy();

        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }

    public void delete (View view){
        try {
            if (beleska != null) {

                getDatabaseHelper().getBeleskaDao().delete(beleska);

                showMessage("", "Actor "+beleska.getNaslov()+" is deleted!\n");

                finish();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void edit (View view){

        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Izmeni");
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_beleska);

        final EditText naslov = (EditText) dialog.findViewById(R.id.naslov);
        final EditText opis = (EditText) dialog.findViewById(R.id.opis);
        final EditText datum = (EditText) dialog.findViewById(R.id.datum);
       final EditText ocena = (EditText) dialog.findViewById(R.id.ocena);


        naslov.setText(beleska.getNaslov());
        opis.setText(beleska.getOpis());
        datum.setText(beleska.getDatum());
       ocena.setText(beleska.getOcena());



        Button ok = (Button) dialog.findViewById(R.id.save);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                beleska.setNaslov(naslov.getText().toString());
                beleska.setOpis(opis.getText().toString());
                beleska.setDatum(datum.getText().toString());
                beleska.setOcena(ocena.getText().toString());



                try {
                    getDatabaseHelper().getBeleskaDao().update(beleska);
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                showMessage("Izmenjena je beleska", beleska.getNaslov());
                refreshBeleska();
                izmena(beleska.getId());
                dialog.dismiss();


            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showMessage("Nije izmenjena beleska", beleska.getNaslov());
            }

        });
        dialog.show();
        if( dialog.isShowing()) {dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimaryDark);}


    }
}
