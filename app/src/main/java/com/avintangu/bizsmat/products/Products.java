package com.avintangu.bizsmat.products;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.resources.Resocs;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class Products extends AppCompatActivity implements View.OnClickListener {

    public static AppCompatActivity propsactiv;
    public static ScrollView scroLayout;
    public static double eunica;

    Resocs resources;
    Productsui productsui;

    public static ProgressBar progbar;
    String detocs;
    boolean isFopen;

    public static TextView prdtabManage;
    public static TextView prdStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Products.propsactiv = this;
        resources = new Resocs(this);
        productsui = new Productsui(this);
        isFopen = false;

        /* Declarations */
        eunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.prdcttitle_text)).setTextSize((float) eunica + 6f);

        progbar = findViewById(R.id.prdctprogress);
        scroLayout = findViewById(R.id.prdctscrollview);
        prdtabManage = findViewById(R.id.prdctTabmngment);
        prdStatus = findViewById(R.id.prdctStatus);

        Bundle bundle = getIntent().getExtras();
        detocs = bundle.getString("determtabmng");
        String statestr = bundle.getString("determstate");
        ((TextView) findViewById(R.id.prdctTabmngment)).setText(detocs);
        ((TextView) findViewById(R.id.prdctStatus)).setText(statestr);

        /* Adjust dimensions */
        resources.adjsDimens((Guideline) findViewById(R.id.prdctactopconsa));

        /* Set up font sizes */
        ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.navlistxt, R.id.navcatext));
        ((Button) findViewById(R.id.prodctBck)).setTextSize((float) eunica + 4.5f);

        /*Set textviews font sizes*/
        TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
        for (int k = 0; k < prdtxvewidec.size(); ++k) {
            (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) eunica + 4.5f);
        }

        /* Set action listeners */
        findViewById(R.id.prodctBck).setOnClickListener(this);
        findViewById(R.id.newprdbtn).setOnClickListener(this);
        findViewById(R.id.prdlistcont).setOnClickListener(this);
        findViewById(R.id.prdcatcont).setOnClickListener(this);

        /* Load products summary */
        prdLodsumary();

    }

    /* Load products summary */
    public void prdLodsumary() {
        ArrayList<Integer> izeds = new ArrayList<>(Arrays.asList(R.id.prdlistcont, R.id.prdcatcont));
        ArrayList<Integer> izedsa = new ArrayList<>(Arrays.asList(R.id.navlistxt, R.id.navcatext));
        ArrayList<String> tabmng = new ArrayList<>(Arrays.asList("prdSrecod", "prdSrecoda"));

        for (int m = 0; m < izeds.size(); m++) {
            if (prdtabManage.getText().toString().equals(tabmng.get(m))) {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.bluu));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.white));

            } else {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.txtcolor));
            }

        }

        productsui.prdctbackrecdlod(scroLayout, eunica, resources.gtColnum(getWindowManager().getDefaultDisplay(), 3, 6),
                prdtabManage.getText().toString(), prdStatus.getText().toString(), progbar, (LinearLayout) findViewById(R.id.prdcthedcont));

    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.prodctBck) {
            finish();

            /* New product */
        } else if (id == R.id.newprdbtn) {
            Intent intent = new Intent(getApplicationContext(), Prdctnew.class);

            String nwprdeterm = "";

            if (prdtabManage.getText().toString().equals("prdSrecod")) {
                nwprdeterm = "newProduct";

            } else if (prdtabManage.getText().toString().equals("prdSrecoda")) {
                nwprdeterm = "newCategory";

            }

            Bundle bundle = new Bundle();
            bundle.putString("prdeterm", nwprdeterm);
            intent.putExtras(bundle);

            startActivity(intent);


            /* Switch between products and categories */
        } else if (id == R.id.prdlistcont) {
            String descoss = "prdSrecod";
            prdtabManage.setText(descoss);
            prdLodsumary();
        } else if (id == R.id.prdcatcont) {
            String descoss = "prdSrecoda";
            prdtabManage.setText(descoss);
            prdLodsumary();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        /* Checks the orientation of the screen */
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }

        prdLodsumary();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }


}