package com.avintangu.bizsmat.products;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.resources.Resocs;

import java.util.ArrayList;
import java.util.Arrays;

public class Prdctnew extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity prdcnewrecod;
    Resocs resources;
    Prdctnewui prdctnewui;
    Prdctresocs prdctresocs;

    double eunica;
    String detocs;
    String detocsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prdctnew);

        Prdctnew.prdcnewrecod = this;
        resources = new Resocs(this);
        prdctnewui = new Prdctnewui(this);
        prdctresocs = new Prdctresocs(this);

        /* Declarations */
        eunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.nwprdsttle)).setTextSize((float) (this.eunica + 6.0));

        ProgressBar progbar = findViewById(R.id.newprdrogress);
        ScrollView scrollView = findViewById(R.id.newprdscroll);

        /* Adjust dimensions */
        expdjsDimensis();

        /* Get parameter */
        Bundle bundle = getIntent().getExtras();
        detocs = "";
        ArrayList<Integer> idecs = new ArrayList<>();
        ArrayList<String> usrdetails = new ArrayList<>();

        if (bundle != null) {
            detocs = bundle.getString("prdeterm");
            ((Button) findViewById(R.id.nwprdBck)).setTextSize((float) eunica + 4.5f);

            switch (detocs) {

                /* New records */
                case "newProduct": {
                    detocsa = "prdSrecod";
                    ((TextView) findViewById(R.id.nwprdsttle)).setText("New product");
                    prdctnewui.nwPrdctuiload(scrollView, idecs, eunica, progbar, detocs, usrdetails);
                    ((TextView) findViewById(R.id.nwprdBck)).setText(getResources().getString(R.string.title_activity_products));

                    break;
                }

                case "newCategory": {
                    detocsa = "prdSrecoda";
                    ((TextView) findViewById(R.id.nwprdsttle)).setText("New product category");
                    prdctnewui.nwPrdctuiload(scrollView, idecs, eunica, progbar, detocs, usrdetails);
                    ((TextView) findViewById(R.id.nwprdBck)).setText(getResources().getString(R.string.prdct_strdins));

                    break;
                }

                /* Edit records */
                case "editProduct": {
                    usrdetails = bundle.getStringArrayList("prdctsdetal");

                    detocsa = "prdSrecod";
                    ((TextView) findViewById(R.id.nwprdsttle)).setText(usrdetails.get(2));
                    prdctnewui.nwPrdctuiload(scrollView, idecs, eunica, progbar, detocs, usrdetails);
                    ((TextView) findViewById(R.id.nwprdBck)).setText(getResources().getString(R.string.title_activity_products));


                    break;
                }

                case "editCategory": {
                    usrdetails = bundle.getStringArrayList("prdctsdetal");

                    detocsa = "prdSrecoda";
                    ((TextView) findViewById(R.id.nwprdsttle)).setText(usrdetails.get(1));
                    prdctnewui.nwPrdctuiload(scrollView, idecs, eunica, progbar, detocs, usrdetails);
                    ((TextView) findViewById(R.id.nwprdBck)).setText(getResources().getString(R.string.prdct_strdins));

                    break;
                }

                default:
                    break;
            }
        }

        /* Set action listeners */
        findViewById(R.id.nwprdBck).setOnClickListener(this);
        findViewById(R.id.newprdsavbtn).setOnClickListener(this);
    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.nwprdBck) {
            resources.activResat(Products.propsactiv, Prdctnew.prdcnewrecod, Products.class,
                    Products.prdtabManage.getText().toString(), Products.prdStatus.getText().toString(),
                    "nada");
        } else if (id == R.id.newprdsavbtn) {
            ArrayList<String> paramsend = new ArrayList<>();
            String determ = "";
            String uniqgen = "";

            switch (detocs) {

                /* New records */
                case "newProduct": {
                    String date = resources.gtDpresocsa(((Button) findViewById(R.id.prdnewbtn)).getText().toString());
                    String category = resources.retuxPrdcatrycode(((TextView) findViewById(R.id.prdnwtxtspin)).getText().toString());
                    String name = ((EditText) findViewById(R.id.prdnwedtxt)).getText().toString();
                    String price = ((EditText) findViewById(R.id.prdnwedtxtb)).getText().toString().replaceAll("[^.0-9]", "");
                    uniqgen = ((TextView) findViewById(R.id.prdctnewuniqid)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(date, category, name, price, uniqgen));
                    determ = "prdctsavrecd";


                    break;
                }

                case "newCategory": {
                    String category = ((EditText) findViewById(R.id.prdcatnwedtxta)).getText().toString();
                    uniqgen = ((TextView) findViewById(R.id.prdctcatnewuniqid)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(category, uniqgen));
                    determ = "prdctsavrecd";


                    break;
                }

                /* Edit records */
                case "editProduct": {
                    String date = resources.gtDpresocsa(((Button) findViewById(R.id.prdnewbtn)).getText().toString());
                    String category = resources.retuxPrdcatrycode(((TextView) findViewById(R.id.prdnwtxtspin)).getText().toString());
                    String name = ((EditText) findViewById(R.id.prdnwedtxt)).getText().toString();
                    String price = ((EditText) findViewById(R.id.prdnwedtxtb)).getText().toString().replaceAll("[^.0-9]", "");
                    uniqgen = ((TextView) findViewById(R.id.prdctnewuniqid)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(date, category, name, price, uniqgen));
                    determ = "prdctupdatrecd";


                    break;
                }

                case "editCategory": {
                    String category = ((EditText) findViewById(R.id.prdcatnwedtxta)).getText().toString();
                    uniqgen = ((TextView) findViewById(R.id.prdctcatnewuniqid)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(category, uniqgen));
                    determ = "prdctupdatrecd";


                    break;
                }

                default:
                    break;
            }

            prdctresocs.mukPrdcts(paramsend, detocs, determ, detocsa);
        }
    }


    /* Adjust dimensions */
    public void expdjsDimensis() {
        float constimgWidth = 0f, constimgWidtha = 0f, constimgWidthb = 0f, widoth = 0f;
        ArrayList<Float> srcsize = resources.getSrcsize(getWindowManager().getDefaultDisplay(), new Point());

        if (this.getResources().getConfiguration().orientation == 2) {
            constimgWidth = 14f;
            constimgWidtha = 90f;
            constimgWidthb = 100f;
            widoth = srcsize.get(1);

        } else if (this.getResources().getConfiguration().orientation == 1) {
            constimgWidth = 8f;
            constimgWidtha = 95f;
            widoth = srcsize.get(0);

        }

        Guideline guideline = findViewById(R.id.newprdguida);
        Guideline guidelina = findViewById(R.id.newprdguidb);

        float constsrcWidth = 360;
        float konstant = constsrcWidth / widoth;

        /* Calculated constraints */
        float newimgWidth = 8 / 100f;
        float newimgWidtha = constimgWidtha / 100f;

        guideline.setGuidelinePercent(newimgWidth);
        guidelina.setGuidelinePercent(newimgWidtha);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        /* Checks the orientation of the screen */
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }

        /* Adjust dimensions */
        expdjsDimensis();
    }
}