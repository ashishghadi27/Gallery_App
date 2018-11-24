package com.kaamchore.rootdevs.ashish.atg;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;

public class Navi_drawer {
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    String pic="";
    SharedPreferences preferences;

    public void nav(DrawerLayout mDrawerLayout, NavigationView navigationView) {

        final Context context = mDrawerLayout.getContext();

        navigationView.bringToFront();
        final DrawerLayout finalMDrawerLayout = mDrawerLayout;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    // Respond to the action bar's Up/Home button
                    case R.id.recent:
                        Intent a = new Intent(context, MainActivity.class);
                        context.startActivity(a);
                        item.setChecked(false);
                        finalMDrawerLayout.closeDrawer(Gravity.START);
                        return true;

                    case R.id.search:
                        Intent x = new Intent(context, Main2Activity.class);
                        context.startActivity(x);
                        finalMDrawerLayout.closeDrawer(Gravity.START);
                        return true;



                }
                return false;
            }
        });



    }
}
