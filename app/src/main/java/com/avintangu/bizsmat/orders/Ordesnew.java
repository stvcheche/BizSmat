package com.avintangu.bizsmat.orders;

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

public class Ordesnew extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity ordnewrecod;
    Resocs resources;
    Ordesnewui ordesnewui;
    Ordersresoc ordersresoc;

    double odneunica;
    String ordetocs;
    String ordetocsa;

    ArrayList<Integer> nwPrdcontidec;
    ArrayList<Integer> nwPrdctidec;
    ArrayList<Integer> nwPrccidec;
    ArrayList<Integer> nwQntyidec;
    ArrayList<String> nwUniqidec;
    public static Button ordessavBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordesnew);

        Ordesnew.ordnewrecod = this;

        resources = new Resocs(this);
        ordesnewui = new Ordesnewui(this);
        ordersresoc = new Ordersresoc(this);

        /* Declarations */
        odneunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.nwordnwsttle)).setTextSize((float) (this.odneunica + 6.0));

        ProgressBar progbar = findViewById(R.id.newordnwprogress);
        ScrollView scrollView = findViewById(R.id.newordnwscroll);
        ordessavBtn = findViewById(R.id.newordnwsavbtn);

        nwPrdcontidec = new ArrayList<>();
        nwPrdctidec = new ArrayList<>();
        nwPrccidec = new ArrayList<>();
        nwQntyidec = new ArrayList<>();
        nwUniqidec = new ArrayList<>();

        /* Adjust dimensions */
        ordnwDimensis();

        /* Get parameter */
        Bundle bundle = getIntent().getExtras();
        ordetocs = "";
        ArrayList<Integer> idecs = new ArrayList<>();
        ArrayList obj = new ArrayList();

        if (bundle != null) {
            ordetocs = bundle.getString("ordeterm");
            ((Button) findViewById(R.id.nwordnwBck)).setTextSize((float) odneunica + 4.5f);

            switch (ordetocs) {

                /* New records */
                case "newProduct": {
                    ordetocsa = "ordeSrecod";
                    ((TextView) findViewById(R.id.nwordnwsttle)).setText("New product");

                    break;
                }

                case "newOrder": {
                    ordetocsa = "ordeSrecoda";
                    ((TextView) findViewById(R.id.nwordnwsttle)).setText("New order");
                    ordesnewui.nwOrduiload(scrollView, idecs, odneunica, progbar, ordetocs, obj,
                            nwPrdcontidec, nwPrdctidec, nwPrccidec, nwQntyidec, nwUniqidec);
                    ((TextView) findViewById(R.id.nwordnwBck)).setText(getResources().getString(R.string.ordestr));

                    break;
                }

                /* Edit records */
                case "editprcc": {
                    ordetocsa = "ordeSrecoda";
                    ((TextView) findViewById(R.id.nwordnwsttle)).setText("Edit product");

                    break;
                }

                case "editord": {
                    obj = bundle.getStringArrayList("ordesdetal");

                    ordetocsa = "ordeSrecoda";
                    ((TextView) findViewById(R.id.nwordnwsttle)).setText("Edit order");
                    ordesnewui.nwOrduiload(scrollView, idecs, odneunica, progbar, ordetocs, obj,
                            nwPrdcontidec, nwPrdctidec, nwPrccidec, nwQntyidec, nwUniqidec);
                    ((TextView) findViewById(R.id.nwordnwBck)).setText(getResources().getString(R.string.ordestr));

                    break;
                }

                default:
                    break;
            }
        }

        /* Set action listeners */
        findViewById(R.id.nwordnwBck).setOnClickListener(this);
        ordessavBtn.setOnClickListener(this);
    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.nwordnwBck) {
            resources.activResat(Orders.ordesactive, Ordesnew.ordnewrecod, Orders.class,
                    Orders.ordtabManage.getText().toString(), Orders.ordStatus.getText().toString(),
                    "nada");
        } else if (id == R.id.newordnwsavbtn) {
            ArrayList<String> paramsend = new ArrayList<>();
            ArrayList<String> nwPrdctdata = new ArrayList<>();
            ArrayList<Integer> nwPrccdata = new ArrayList<>();
            ArrayList<Integer> nwQntydata = new ArrayList<>();
            String determ = "";
            String uniqgen = "";
            String ordsapprv = "";
            String ordstekcs = "";
            String ordsreceipt = "";

            switch (ordetocs) {

                /* New and edit orders */
                case "newOrder":
                case "editord": {
                    String date = resources.gtDpresocsa(((Button) findViewById(R.id.ordnwbtnres)).getText().toString());
                    String time = ((Button) findViewById(R.id.ordnwbtnresa)).getText().toString();
                    String outlet = resources.gtshopcode(((TextView) findViewById(R.id.ordnwtxtrfc)).getText().toString());
                    String distributor = resources.getDistcode(((TextView) findViewById(R.id.ordnwtxtrfe)).getText().toString());
                    String payment = ((TextView) findViewById(R.id.ordnwtxtrfg)).getText().toString();
                    String notes = ((EditText) findViewById(R.id.ordnwedtxt)).getText().toString();
                    uniqgen = ((TextView) findViewById(R.id.ordnewuniqid)).getText().toString();

                    ordsapprv = ((TextView) findViewById(R.id.ordapprvidec)).getText().toString();
                    ordstekcs = ((TextView) findViewById(R.id.ordstateidec)).getText().toString();
                    ordsreceipt = ((TextView) findViewById(R.id.ordsreceipidec)).getText().toString();

                    for (int mega = 0; mega < nwPrdctidec.size(); mega++) {
                        if (!((TextView) findViewById(nwPrdctidec.get(mega))).getText().toString().equals("") &&
                                !((TextView) findViewById(nwPrccidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "").equals("") &&
                                !((EditText) findViewById(nwQntyidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "").equals("")) {

                            nwPrdctdata.add(((TextView) findViewById(nwPrdctidec.get(mega))).getText().toString());
                            nwPrccdata.add(Integer.valueOf(((TextView) findViewById(nwPrccidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "")));
                            nwQntydata.add(Integer.valueOf(((EditText) findViewById(nwQntyidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "")));

                        }

                    }

                    paramsend = new ArrayList<>(Arrays.asList(date, time, outlet, distributor, payment, notes, uniqgen, ordsapprv, ordstekcs, ordsreceipt));
                    determ = "ordSavrecd";

                    break;
                }


                default:
                    break;
            }

            ordersresoc.mukOrders(paramsend, nwPrdctdata, nwPrccdata, nwQntydata, ordetocs, determ, ordetocsa, nwUniqidec);
        }
    }

    /* Adjust dimensions */
    public void ordnwDimensis() {
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

        Guideline guideline = findViewById(R.id.newordnwguida);
        Guideline guidelina = findViewById(R.id.newordnwguidb);

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
        ordnwDimensis();
    }
}