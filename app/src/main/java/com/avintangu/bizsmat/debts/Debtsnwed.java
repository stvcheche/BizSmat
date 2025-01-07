package com.avintangu.bizsmat.debts;

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

public class Debtsnwed extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity debtnewactive;
    Resocs resources;
    Debtnwui debtnwui;
    Debtsresoc debtsresoc;
    Debtsresoca debtsresoca;

    double datnweunica;
    String detocs;
    String detocsa;
    boolean isFopen;
    ScrollView scrollView;
    public static TextView debtnwedttle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debtsnwed);

        Debtsnwed.debtnewactive = this;
        resources = new Resocs(this);
        debtnwui = new Debtnwui(this);
        debtsresoc = new Debtsresoc(this);
        debtsresoca = new Debtsresoca(this);
        isFopen = false;

        /* Declarations */
        datnweunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());

        debtnwedttle = findViewById(R.id.nwdebttle);
        ProgressBar progbar = findViewById(R.id.newdebtprogress);
        scrollView = findViewById(R.id.newdebtscroll);

        /* Adjust dimensions */
        datdjsDimensis();

        /* Set up font sizes */
        ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.naveditcusto,
                R.id.navlendcusto));
        debtnwedttle.setTextSize((float) (this.datnweunica + 6.0));

        /*Set textviews font sizes*/
        TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
        for (int k = 0; k < prdtxvewidec.size(); ++k) {
            (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) datnweunica + 4.5f);
        }

        /* Get parameter */
        Bundle bundle = getIntent().getExtras();
        detocs = "";
        ArrayList<Integer> idecs = new ArrayList<>();
        ArrayList<ArrayList<String>> usrdetails = new ArrayList<>();
        ArrayList<String> usrdetailsa = new ArrayList<>();

        if (bundle != null) {
            detocs = bundle.getString("debtdeterm");
            ((Button) findViewById(R.id.nwdebtBck)).setTextSize((float) datnweunica + 4.5f);

            switch (detocs) {

                /* New records */
                case "newCustomer": {
                    detocsa = "prdSrecod";
                    ((TextView) findViewById(R.id.nwdebttle)).setText("New customer");
                    debtnwui.nwDebtctuiload(scrollView, idecs, datnweunica, progbar, detocs, usrdetails, usrdetailsa,
                            (LinearLayout) findViewById(R.id.datnwedhedcont));
                    ((Button) findViewById(R.id.nwdebtBck)).setText(getResources().getString(R.string.debt_str));

                    /* Hide Edit and lend buttons */
                    findViewById(R.id.datcusbtmnaview).setVisibility(View.GONE);

                    break;
                }

                case "newPayment": {
                    detocsa = "prdSrecoda";
                    ((TextView) findViewById(R.id.nwdebttle)).setText("New payment");
                    debtnwui.nwDebtctuiload(scrollView, idecs, datnweunica, progbar, detocs, usrdetails, usrdetailsa,
                            (LinearLayout) findViewById(R.id.datnwedhedcont));
                    ((Button) findViewById(R.id.nwdebtBck)).setText(getResources().getString(R.string.debt_stra));

                    /* Hide Edit and lend buttons */
                    findViewById(R.id.datcusbtmnaview).setVisibility(View.GONE);

                    break;
                }

                /* Edit records */
                case "ediDatsum": {
                    findViewById(R.id.newdebtsavbtn).setVisibility(View.GONE);
                    findViewById(R.id.datcusbtmnaview).setVisibility(View.VISIBLE);

                    usrdetails = (ArrayList<ArrayList<String>>) bundle.getSerializable("debtdetal");
                    String custname = usrdetails.get(usrdetails.size() - 1).get(0);
                    usrdetails.remove(usrdetails.size() - 1);

                    detocsa = "prdSrecod";
                    ((TextView) findViewById(R.id.nwdebttle)).setText(custname);
                    debtnwui.nwDebtctuiload(scrollView, idecs, datnweunica, progbar, detocs, usrdetails, usrdetailsa,
                            (LinearLayout) findViewById(R.id.datnwedhedcont));
                    ((Button) findViewById(R.id.nwdebtBck)).setText(getResources().getString(R.string.debt_str));


                    break;
                }

                case "editPayment": {
                    usrdetailsa = bundle.getStringArrayList("debtdetal");

                    detocsa = "prdSrecoda";
                    ((TextView) findViewById(R.id.nwdebttle)).setText(usrdetailsa.get(1));
                    debtnwui.nwDebtctuiload(scrollView, idecs, datnweunica, progbar, detocs, usrdetails, usrdetailsa,
                            (LinearLayout) findViewById(R.id.datnwedhedcont));
                    ((Button) findViewById(R.id.nwdebtBck)).setText(getResources().getString(R.string.debt_stra));

                    /* Hide Edit and lend buttons */
                    findViewById(R.id.datcusbtmnaview).setVisibility(View.GONE);

                    break;
                }

                default:
                    break;
            }
        }

        /* Set action listeners */
        findViewById(R.id.nwdebtBck).setOnClickListener(this);
        findViewById(R.id.newdebtsavbtn).setOnClickListener(this);

        findViewById(R.id.editcusto).setOnClickListener(this);
        findViewById(R.id.lendcusto).setOnClickListener(this);

    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.nwdebtBck) {
            resources.activResat(Debts.debtactiv, Debtsnwed.debtnewactive, Debts.class,
                    Debts.databManage.getText().toString(), Debts.datStatus.getText().toString(),
                    "nada");
        } else if (id == R.id.newdebtsavbtn) {
            ArrayList<String> paramsend = new ArrayList<>();
            String determ = "";
            String uniqgen = "";

            switch (detocs) {

                /* New records */
                case "newCustomer": {
                    String date = resources.gtDpresocsa(((Button) findViewById(R.id.debtnewbtn)).getText().toString());
                    String name = ((EditText) findViewById(R.id.debtnwedtxt)).getText().toString();
                    String phone = ((EditText) findViewById(R.id.debtnwedtxta)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(date, name, phone));
                    determ = "debtsavrecd";


                    break;
                }

                case "newPayment": {
                    String date = resources.gtDpresocsa(((Button) findViewById(R.id.debtanewbtn)).getText().toString());
                    String name = resources.retuxCuscodes(((TextView) findViewById(R.id.debtanwtxtb)).getText().toString());
                    String amntpaid = ((EditText) findViewById(R.id.debtanwedtxt)).getText().toString().replaceAll("[^.0-9]", "");

                    paramsend = new ArrayList<>(Arrays.asList(date, name, amntpaid));
                    determ = "debtsavrecd";

                    break;
                }

                /* Edit records */
                case "editCustomer": {
                    determ = "debtupdatrecd";

                    break;
                }

                case "editPayment": {
                    String date = resources.gtDpresocsa(((Button) findViewById(R.id.debtanewbtn)).getText().toString());
                    String name = resources.retuxCuscodes(((TextView) findViewById(R.id.debtanwtxtb)).getText().toString());
                    String amntpaid = ((EditText) findViewById(R.id.debtanwedtxt)).getText().toString().replaceAll("[^.0-9]", "");

                    uniqgen = ((TextView) findViewById(R.id.debtnewuniqida)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(date, name, amntpaid, uniqgen));
                    determ = "debtupdatrecd";

                    break;
                }

                default:
                    break;
            }

            debtsresoc.mukDebts(paramsend, detocs, determ, detocsa, new ArrayList<String>(),
                    new ArrayList<Integer>(), new ArrayList<Integer>(), new String());

            /*  Edit customer */
        } else if (id == R.id.editcusto) {
            debtsresoca.recdEditdilog("editCustos", ((TextView) findViewById(R.id.nwdebttle)).getText().toString());

            /* Lend to customer */
        } else if (id == R.id.lendcusto) {
            debtsresoca.recdEditdilog("lendCustos", ((TextView) findViewById(R.id.nwdebttle)).getText().toString());
        }
    }

    /* Adjust dimensions */
    public void datdjsDimensis() {
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

        Guideline guideline = findViewById(R.id.newdebtguida);
        Guideline guidelina = findViewById(R.id.newdebtguidb);

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
        datdjsDimensis();
    }
}