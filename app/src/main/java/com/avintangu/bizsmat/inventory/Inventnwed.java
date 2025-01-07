package com.avintangu.bizsmat.inventory;

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
import com.avintangu.bizsmat.products.Prdctnew;
import com.avintangu.bizsmat.resources.Resocs;

import java.util.ArrayList;
import java.util.Arrays;

public class Inventnwed extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity inventnewrecod;
    Resocs resources;
    Inventnwedui inventnwedui;
    Inventresoc inventresoc;

    double eunica;
    String detocs;
    String detocsa;

    ArrayList<Integer> nwStokcontidec;
    ArrayList<Integer> nwPrdctidec;
    ArrayList<Integer> nwBprcidec;
    ArrayList<Integer> nwSlprcidec;
    ArrayList<Integer> nwQntyidec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventnwed);

        Inventnwed.inventnewrecod = this;
        Prdctnew.prdcnewrecod = this;
        resources = new Resocs(this);
        inventnwedui = new Inventnwedui(this);
        inventresoc = new Inventresoc(this);

        /* Declarations */
        eunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.nwinvntsttle)).setTextSize((float) (this.eunica + 6.0));

        ProgressBar progbar = findViewById(R.id.newinvprogress);
        ScrollView scrollView = findViewById(R.id.newinvntscroll);

        nwStokcontidec = new ArrayList<>();
        nwPrdctidec = new ArrayList<>();
        nwBprcidec = new ArrayList<>();
        nwSlprcidec = new ArrayList<>();
        nwQntyidec = new ArrayList<>();

        /* Adjust dimensions */
        invdjsDimensis();

        /* Get parameter */
        Bundle bundle = getIntent().getExtras();
        detocs = "";
        ArrayList<Integer> idecs = new ArrayList<>();
        ArrayList<String> usrdetails = new ArrayList<>();

        if (bundle != null) {
            detocs = bundle.getString("inventdeterm");
            ((Button) findViewById(R.id.nwinvntBck)).setTextSize((float) eunica + 4.5f);

            switch (detocs) {

                /* New records */
                case "newStocks": {
                    detocsa = "inventSrecoda";
                    ((TextView) findViewById(R.id.nwinvntsttle)).setText("New stock");
                    inventnwedui.nwinVctuiload(scrollView, idecs, eunica, progbar, detocs, usrdetails,
                            nwStokcontidec, nwPrdctidec, nwBprcidec, nwSlprcidec, nwQntyidec,
                            (LinearLayout) findViewById(R.id.inventhedcont));
                    ((Button) findViewById(R.id.nwinvntBck)).setText(getResources().getString(R.string.invent_straa));

                    break;
                }

                /* Edit records */
                case "editStocks": {
                    usrdetails = bundle.getStringArrayList("inventdetal");

                    detocsa = "inventSrecoda";
                    ((TextView) findViewById(R.id.nwinvntsttle)).setText(usrdetails.get(1));
                    inventnwedui.nwinVctuiload(scrollView, idecs, eunica, progbar, detocs, usrdetails,
                            nwStokcontidec, nwPrdctidec, nwBprcidec, nwSlprcidec, nwQntyidec,
                            (LinearLayout) findViewById(R.id.inventhedcont));
                    ((Button) findViewById(R.id.nwinvntBck)).setText(getResources().getString(R.string.invent_straa));

                    break;
                }

                default:
                    break;
            }
        }

        /* Set action listeners */
        findViewById(R.id.nwinvntBck).setOnClickListener(this);
        findViewById(R.id.newinvntsavbtn).setOnClickListener(this);
    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.nwinvntBck) {
            resources.activResat(Inventory.inventactiv, Inventnwed.inventnewrecod, Inventory.class,
                    Inventory.invtabManage.getText().toString(), Inventory.invStatus.getText().toString(),
                    "nada");
        } else if (id == R.id.newinvntsavbtn) {
            ArrayList<String> paramsend = new ArrayList<>();
            ArrayList<String> nwPrdctdata = new ArrayList<>();
            ArrayList<Integer> nwBprcdata = new ArrayList<>();
            ArrayList<Integer> nwSlprcdata = new ArrayList<>();
            ArrayList<Integer> nwQntydata = new ArrayList<>();
            String determ = "";
            String uniqgen = "";

            switch (detocs) {

                /* New records */
                case "newStocks": {
                    for (int mega = 0; mega < nwPrdctidec.size(); mega++) {
                        if (!((TextView) findViewById(nwPrdctidec.get(mega))).getText().toString().equals("") &&
                                !((EditText) findViewById(nwBprcidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "").equals("") &&
                                !((EditText) findViewById(nwSlprcidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "").equals("") &&
                                !((EditText) findViewById(nwQntyidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "").equals("")) {

                            nwPrdctdata.add(((TextView) findViewById(nwPrdctidec.get(mega))).getText().toString());
                            nwBprcdata.add(Integer.valueOf(((EditText) findViewById(nwBprcidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "")));
                            nwSlprcdata.add(Integer.valueOf(((EditText) findViewById(nwSlprcidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "")));
                            nwQntydata.add(Integer.valueOf(((EditText) findViewById(nwQntyidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "")));

                        }

                    }
                    determ = "stoksavrecd";

                    break;
                }

                /* Edit records */
                case "editStocks": {
                    String date = resources.gtDpresocsa(((Button) findViewById(R.id.invDbtn)).getText().toString());
                    String product = resources.retuxPrdcode(((TextView) findViewById(R.id.invDtxtvewb)).getText().toString());
                    String bprice = ((EditText) findViewById(R.id.inveDitxt)).getText().toString().replaceAll("[^.0-9]", "");
                    String slprice = ((EditText) findViewById(R.id.inveDitxta)).getText().toString().replaceAll("[^.0-9]", "");
                    String quantity = ((EditText) findViewById(R.id.inveDitxtb)).getText().toString().replaceAll("[^.0-9]", "");

                    uniqgen = ((TextView) findViewById(R.id.invnewuniqida)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(date, product, bprice, slprice, quantity, uniqgen));
                    determ = "stokupdatrecd";


                    break;
                }

                default:
                    break;
            }

            inventresoc.mukInvent(paramsend, nwPrdctdata, nwBprcdata, nwSlprcdata, nwQntydata, detocs, determ, detocsa);
        }
    }

    /* Adjust dimensions */
    public void invdjsDimensis() {
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

        Guideline guideline = findViewById(R.id.newinvntguida);
        Guideline guidelina = findViewById(R.id.newinvntguidb);

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
        invdjsDimensis();
    }
}