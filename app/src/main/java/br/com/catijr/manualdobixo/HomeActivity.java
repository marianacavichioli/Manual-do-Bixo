package br.com.catijr.manualdobixo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    private DrawerLayout mDrawerLayout;

    private Button btnEmailCati;
    private Button btnSiteCati;
    private Button btnTelefoneCati;
    private Button btnPaginaCati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TrackerService.getInstance().track("HomeActivity opened", getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHideOffset(0);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                if (getSupportActionBar()!= null ) {
                    TrackerService.getInstance().track("Left menu opened", getApplicationContext());
                    getSupportActionBar().setTitle(R.string.openDrawer);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (getSupportActionBar()!= null ) {
                    TrackerService.getInstance().track("Left menu closed", getApplicationContext());
                    getSupportActionBar().setTitle(R.string.closeDrawer);
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        btnSiteCati = (Button) findViewById(R.id.btnSiteCati);
        btnSiteCati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String YourPageURL = "http://www.catijr.ufscar.br";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

                startActivity(browserIntent);
                TrackerService.getInstance().track("Button: Site Cati - Event: Clicked", getApplicationContext());
            }
        });

        btnEmailCati = (Button) findViewById(R.id.btnEmailCati);
        btnEmailCati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrackerService.getInstance().track("Button: Email Cati - Event: Clicked", getApplicationContext());
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "cati@catijr.ufscar.br", null));
                startActivity(Intent.createChooser(intent, "Escolha um cliente de email:"));
            }
        });

        btnTelefoneCati = (Button) findViewById(R.id.btnTelefoneCati);
        btnTelefoneCati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrackerService.getInstance().track("Button: Telefone Cati - Event: Clicked", getApplicationContext());
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestTelefone();
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:1633519495"));
                    startActivity(callIntent);
                }
            }
        });

        btnPaginaCati = (Button) findViewById(R.id.btnPaginaCati);
        btnPaginaCati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrackerService.getInstance().track("Button: Página Cati - Event: Clicked", getApplicationContext());
                String YourPageURL = "https://www.facebook.com/catijr/";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

                startActivity(browserIntent);
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_pager);

        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void requestTelefone() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:1633519495"));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);

                }
                return;
            }
        }
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ManualFragment();
                case 1:
                    return new AgendaFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Manual";
                case 1:
                    return "Programação";
                default:
                    return "";
            }
        }
    }

}
