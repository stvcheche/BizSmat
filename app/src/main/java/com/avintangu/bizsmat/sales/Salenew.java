package com.avintangu.bizsmat.sales;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.resources.Resocs;

import java.util.ArrayList;
import java.util.Arrays;

public class Salenew extends AppCompatActivity implements View.OnClickListener {

    public static AppCompatActivity salnewrecod;
    Resocs resources;
    Salenewui salenewui;
    Salesresocs salesresocs;

    double eunica;
    String detocs;
    String detocsa;

    ArrayList<Integer> nwPrdcontidec;
    ArrayList<Integer> nwPrdctidec;
    ArrayList<Integer> nwPrccidec;
    ArrayList<Integer> nwQntyidec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salenewrecod);

        Salenew.salnewrecod = this;
        resources = new Resocs(this);
        salenewui = new Salenewui(this);
        salesresocs = new Salesresocs(this);

        /* Declarations */
        eunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.nwsalsttle)).setTextSize((float) (this.eunica + 6.0));

        ProgressBar progbar = findViewById(R.id.newsalrogress);
        ScrollView scrollView = findViewById(R.id.newsalscroll);

        nwPrdcontidec = new ArrayList<>();
        nwPrdctidec = new ArrayList<>();
        nwPrccidec = new ArrayList<>();
        nwQntyidec = new ArrayList<>();

        /* Adjust dimensions */
        saladjsDimensis();

        /* Get parameter */
        Bundle bundle = getIntent().getExtras();
        detocs = "";
        ArrayList<Integer> idecs = new ArrayList<>();
        ArrayList<String> usrdetails = new ArrayList<>();

        if (bundle != null) {
            detocs = bundle.getString("saldeterm");
            ((Button) findViewById(R.id.nwsalBck)).setTextSize((float) eunica + 4.5f);

            switch (detocs) {

                /* New records */
                case "newSale": {
                    detocsa = "salSrecod";
                    ((TextView) findViewById(R.id.nwsalsttle)).setText("New sale");
                    salenewui.nwSalctuiload(scrollView, idecs, eunica, progbar, detocs, usrdetails,
                            nwPrdcontidec, nwPrdctidec, nwPrccidec, nwQntyidec,
                            (LinearLayout) findViewById(R.id.salnewhedcont));

                    ((Button) findViewById(R.id.nwsalBck)).setText(getResources().getString(R.string.sale_str));


                    break;
                }

                /* Edit records */
                case "editSale": {
                    usrdetails = bundle.getStringArrayList("salesdetal");

                    detocsa = "salSrecod";
                    ((TextView) findViewById(R.id.nwsalsttle)).setText(usrdetails.get(1));
                    salenewui.nwSalctuiload(scrollView, idecs, eunica, progbar, detocs, usrdetails,
                            nwPrdcontidec, nwPrdctidec, nwPrccidec, nwQntyidec,
                            (LinearLayout) findViewById(R.id.salnewhedcont));

                    ((Button) findViewById(R.id.nwsalBck)).setText(getResources().getString(R.string.sale_str));


                    break;
                }

                default:
                    break;
            }
        }

        /* Set action listeners */
        findViewById(R.id.nwsalBck).setOnClickListener(this);
        findViewById(R.id.newsalsavbtn).setOnClickListener(this);
    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.nwsalBck) {
            resources.activResat(Sales.salesactiv, Salenew.salnewrecod, Sales.class,
                    Sales.saltabManage.getText().toString(), Sales.salStatus.getText().toString(),
                    "nada");
        } else if (id == R.id.newsalsavbtn) {
            ArrayList<String> paramsend = new ArrayList<>();
            ArrayList<String> nwPrdctdata = new ArrayList<>();
            ArrayList<Integer> nwPrccdata = new ArrayList<>();
            ArrayList<Integer> nwQntydata = new ArrayList<>();
            String determ = "";
            String uniqgen = "";

            switch (detocs) {

                /* New records */
                case "newSale": {

                    for (int mega = 0; mega < nwPrdctidec.size(); mega++) {
                        if (!((TextView) findViewById(nwPrdctidec.get(mega))).getText().toString().equals("") &&
                                !((TextView) findViewById(nwPrccidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "").equals("") &&
                                !((EditText) findViewById(nwQntyidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "").equals("")) {

                            nwPrdctdata.add(((TextView) findViewById(nwPrdctidec.get(mega))).getText().toString());
                            nwPrccdata.add(Integer.valueOf(((TextView) findViewById(nwPrccidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "")));
                            nwQntydata.add(Integer.valueOf(((EditText) findViewById(nwQntyidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "")));

                        }

                    }
                    determ = "salSavrecd";

                    break;
                }

                /* Edit records */
                case "editSale": {
                    String date = resources.gtDpresocsa(((Button) findViewById(R.id.saleDbtn)).getText().toString());
                    String product = resources.retuxPrdcode(((TextView) findViewById(R.id.saleDtxtvewb)).getText().toString());
                    String price = ((EditText) findViewById(R.id.saleDitxt)).getText().toString().replaceAll("[^.0-9]", "");
                    String quantity = ((EditText) findViewById(R.id.saleDitxta)).getText().toString().replaceAll("[^.0-9]", "");

                    uniqgen = ((TextView) findViewById(R.id.salenewuniqida)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(date, product, price, quantity, uniqgen));
                    determ = "salUpdatrecd";


                    break;
                }

                default:
                    break;
            }

            salesresocs.mukSales(paramsend, nwPrdctdata, nwPrccdata, nwQntydata, detocs, determ, detocsa);
        }
    }

    /* Adjust dimensions */
    public void saladjsDimensis() {
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

        Guideline guideline = findViewById(R.id.newsalguida);
        Guideline guidelina = findViewById(R.id.newsalguidb);

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
        saladjsDimensis();
    }

}