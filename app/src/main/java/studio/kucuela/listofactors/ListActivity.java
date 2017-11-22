package studio.kucuela.listofactors;


import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;



import studio.kucuela.listofactors.db.DataBaseHelper;
import studio.kucuela.listofactors.objects.Beleska;


public class ListActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    ListView listaBeleski;
    List<Beleska> beleske;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_beleski);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*toolbar.setTitle("Actors database");
        toolbar.setSubtitle("unique tastabase actuerz");*/
      //  toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\">" + getString(R.string.app_name) + "</font>"));
       /* getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        // getSupportActionBar().setIcon(R.mipmap.notez);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled( true );

            actionBar.show();
        }

        listaBeleski = (ListView) findViewById(R.id.listaGlumaca);

        try {
            beleske = getDatabaseHelper().getBeleskaDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (beleske != null) {

            ListAdapter adapter = new ArrayAdapter<Beleska>(this, R.layout.list_item, beleske);
            listaBeleski.setAdapter(adapter);
            listaBeleski.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Beleska beleska = (Beleska) listaBeleski.getItemAtPosition(i);
                    Intent intent = new Intent(ListActivity.this, DeatailActivity.class);
                    intent.putExtra("POSITION", beleska.getId());
                    startActivity(intent);
                }
            });


        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.clear();
        getMenuInflater().inflate(R.menu.activity_item_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(ListActivity.this, Settings.class);
                startActivity(intent);
                break;

            case R.id.About:
                /*About about = new About();
                about.show(getSupportFragmentManager(), "ABOUT");
                break;*/

                new MaterialStyledDialog.Builder(this)
                        .setTitle("O aplikaciji")
                        .setDescription("Made by Svetozar Zorbic.\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n")
                        .setIcon(R.drawable.about)
                        .withDialogAnimation(true)
                        //.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_launcher))
                        .show();





        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshGlumac();
    }

    private void refreshGlumac() {

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

    public void showMessage(String text, String newGlumac) {
        SharedPreferences st = PreferenceManager.getDefaultSharedPreferences(ListActivity.this);
        String name = st.getString("message", "Toast");
        if (name.equals("Toast")) {
            Toast.makeText(ListActivity.this, text + "\n"+ newGlumac, Toast.LENGTH_LONG).show();
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(ListActivity.this);
            builder.setSmallIcon(R.drawable.ic_action_name);
            builder.setContentTitle(text);
            builder.setContentText(newGlumac);


            // Shows notification with the notification manager (notification ID is used to update the notification later on)
            NotificationManager manager = (NotificationManager) ListActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, builder.build());
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

    public void onClickButton1 (View view){

        Intent intent1 = new Intent(this, Add_beleska.class);
        startActivity(intent1);




    }


}

