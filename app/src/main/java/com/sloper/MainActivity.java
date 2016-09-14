package com.sloper;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.sloper.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProductFragment.OnListFragmentInteractionListener, ProductFragment2.OnListFragmentInteractionListener {

    public static Context mContext;
    public static int mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Bundle extras = getIntent().getExtras();
        mId = extras.getInt("callId",R.id.nav_camera);
        ProductFragment newFrag= ProductFragment.newInstance(1,mId);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_container,newFrag);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(mId==R.id.nav_camera)
            {super.onBackPressed();}
            else{
                Intent i = new Intent(this,MainActivity.class);
                i.putExtra("callId",R.id.nav_camera);
                finish();
                startActivity(i);
            }
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_about){
            Intent i = new Intent(this,AboutActivity.class);
            startActivity(i);
        }
        else {
            try {
                Intent i = new Intent(this,MainActivity.class);
                i.putExtra("callId",id);
                finish();
                startActivity(i);
            } catch (OutOfMemoryError e) {
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Ran out of memory!")
                        .setMessage(e.getMessage())
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                e.printStackTrace();
            } catch (Exception e) {
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Unexpected error!")
                        .setMessage(e.getMessage())
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                e.printStackTrace();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item, View view) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("PROD_ID",item.id);
        i.putExtra("PROD_COL",item.colors);
        i.putExtra("PROD_PRICE", item.price);
        i.putExtra("PROD_NAME",item.title);
        i.putExtra("IMG", (Parcelable) ((ImageView)view.findViewById(R.id.photo)).getDrawable());
        i.putExtra("PROD_SIZ",item.sizes);
        i.putExtra("PROD_TYP",item.type);
        Pair<View,String> p1 = Pair.create((View)view.findViewById(R.id.photo),"mainImage");
        Pair<View,String> p2 = Pair.create((View)view.findViewById(R.id.title),"product_title");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1,p2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(i, options.toBundle());
        }
        else{
            startActivity(i);
        }
    }
}
