package studio.kucuela.listofactors;


import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import studio.kucuela.listofactors.db.DataBaseHelper;
import studio.kucuela.listofactors.objects.Beleska;





public class Add_beleska extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    Beleska beleska;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_beleska);

        final EditText naslov = (EditText) findViewById(R.id.naslov);
        naslov.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        //toolbar.setTitle("New note");
        //toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        //setSupportActionBar(toolbar);

        /*Button button = (Button) findViewById(R.id.cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                showMessage("Nije sacuvana beleska", " ");
            }
        });

        Button button1 = (Button) findViewById(R.id.save);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                napravi();
                finish();
                *//*Intent intent = new Intent(Add_beleska.this, android.app.ListActivity.class);
                startActivity(intent);*//*
            }
        });*/


    }


    /*@Override
    *//*public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.detail_fragment_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       return super.onOptionsItemSelected(item);
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






    public void napravi() {


        final EditText naslov = (EditText) findViewById(R.id.naslov);
        final EditText opis = (EditText) findViewById(R.id.opis);
        final EditText datum = (EditText) findViewById(R.id.datum);
        final EditText ocena = (EditText) findViewById(R.id.ocena);

        Beleska newBeleska = new Beleska();

                newBeleska.setNaslov(naslov.getText().toString());
                newBeleska.setOpis(opis.getText().toString());
                newBeleska.setDatum(datum.getText().toString());
               newBeleska.setOcena(ocena.getText().toString());


                try {
                    getDatabaseHelper().getBeleskaDao().create(newBeleska);
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                showMessage("Actor saved as: ", newBeleska.getNaslov());




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

    public void onclickdugme (View view){

        napravi();
        finish();
    }

    @Override
    public void onBackPressed (){

        finish();


        showMessage("Actor not saved", "");



    }
}
