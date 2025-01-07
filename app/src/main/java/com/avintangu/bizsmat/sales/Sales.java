package com.avintangu.bizsmat.sales;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.analytics.Analyticsui;
import com.avintangu.bizsmat.resources.Resocs;
import com.google.android.flexbox.FlexboxLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Sales extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity salesactiv;
    public static ScrollView scroLayout;
    public static double eunica;

    Resocs resources;
    Salesui salesui;
    Analyticsui analyticsui;
    public static ProgressBar progbar;

    String detocs;
    boolean isFopen;
    boolean isDrwpen;

    public static TextView saltabManage;
    public static TextView salStatus;
    public static TextView salFrodta;
    public static TextView salTodta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        Sales.salesactiv = this;

        resources = new Resocs(this);
        salesui = new Salesui(this);
        analyticsui = new Analyticsui(this);

        isFopen = false;
        isDrwpen = false;

        /* Declarations */
        eunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.salestitle_text)).setTextSize((float) (this.eunica + 6.0));

        progbar = findViewById(R.id.salesprogress);
        scroLayout = findViewById(R.id.salescrollview);
        saltabManage = findViewById(R.id.saleTabmngment);
        salStatus = findViewById(R.id.saleStatus);
        salFrodta = findViewById(R.id.saleFrodta);
        salTodta = findViewById(R.id.saleTodta);

        Bundle bundle = getIntent().getExtras();
        detocs = bundle.getString("determtabmng");
        String statestr = bundle.getString("determstate");
        ((TextView) findViewById(R.id.salestitle_text)).setText(salesui.setsalUitrelis(detocs));
        ((TextView) findViewById(R.id.saleTabmngment)).setText(detocs);
        ((TextView) findViewById(R.id.saleStatus)).setText(statestr);

        /* Adjust dimensions */
        resources.adjsDimens((Guideline) findViewById(R.id.salesactopconsa));

        /* Set up font sizes */
        ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.salctfabtxtb,
                R.id.salctfabtxtc));
        ((Button) findViewById(R.id.salesBck)).setTextSize((float) eunica + 4.5f);

        /*Set textviews font sizes*/
        TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
        for (int k = 0; k < prdtxvewidec.size(); ++k) {
            (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) eunica + 4.5f);
        }

        /*  Set dates */
        ArrayList<String> datearry = resources.retStrdate();
        ((TextView) findViewById(R.id.salctfabtxtb)).setText(datearry.get(1));
        ((TextView) findViewById(R.id.salctfabtxtc)).setText(datearry.get(5));
        salFrodta.setText(datearry.get(2));
        salTodta.setText(datearry.get(4));

        /* Set action listeners */
        findViewById(R.id.salesBck).setOnClickListener(this);
        findViewById(R.id.newssalbtn).setOnClickListener(this);
        findViewById(R.id.salestab).setOnClickListener(this);
        findViewById(R.id.liticstab).setOnClickListener(this);
        findViewById(R.id.salenavmenu).setOnClickListener(this);
        findViewById(R.id.liticsdrawclose).setOnClickListener(this);

        findViewById(R.id.liticstxtCont).setOnClickListener(this);
        findViewById(R.id.liticstxtConta).setOnClickListener(this);
        findViewById(R.id.liticstxtContb).setOnClickListener(this);
        findViewById(R.id.liticstxtContc).setOnClickListener(this);
        findViewById(R.id.liticstxtContd).setOnClickListener(this);

        findViewById(R.id.saldatetab).setOnClickListener(this);
        findViewById(R.id.salesfltmennucb).setOnClickListener(this);
        findViewById(R.id.salesfltmennucc).setOnClickListener(this);
        findViewById(R.id.salesblurlay).setOnClickListener(this);


        /* Load sales summary */
        salLoadsumry();
    }

    /* Load sales summary */
    public void salLoadsumry() {
        String liticstr = "";
        ArrayList<String> liticsarry = new ArrayList<>(Arrays.asList("liticSrecod", "liticSrecoda", "liticSrecodb",
                "liticSrecodc", "liticSrecodd"));

        if (liticsarry.contains(saltabManage.getText().toString())) {
            liticstr = "liticSrecod";

        }

        ArrayList<Integer> izeds = new ArrayList<>(Arrays.asList(R.id.salestab, R.id.liticstab));
        ArrayList<Integer> izedsa = new ArrayList<>(Arrays.asList(R.id.navsaltab, R.id.navliticstab));
        ArrayList<String> tabmng = new ArrayList<>(Arrays.asList("salSrecod", liticstr));

        for (int m = 0; m < izeds.size(); m++) {
            if (saltabManage.getText().toString().equals(tabmng.get(m))) {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.bluu));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.white));

            } else {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.txtcolor));
            }

        }

        if (saltabManage.getText().toString().equals("salSrecod")) {
            salesui.salebackrecdlod(scroLayout, eunica, resources.gtColnum(getWindowManager().getDefaultDisplay(), 4, 4),
                    saltabManage.getText().toString(), salStatus.getText().toString(), salFrodta.getText().toString(),
                    salTodta.getText().toString(), progbar, (LinearLayout) findViewById(R.id.saleshedcont));

        } else if (liticsarry.contains(saltabManage.getText().toString())) {
            analyticsui.liticsbackrecdlod(scroLayout, eunica, saltabManage.getText().toString(),
                    salFrodta.getText().toString(), salTodta.getText().toString(), progbar, (LinearLayout) findViewById(R.id.saleshedcont));

        }

        ((TextView) findViewById(R.id.salestitle_text)).setText(salesui.setsalUitrelis(saltabManage.getText().toString()));

    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.salesBck) {
            finish();

            /* Floating buttons On action trigger */
        } else if (id == R.id.saldatetab) {
            drawMenuswich("nada");
            salflNavswitch("cool");

            /* From date*/
        } else if (id == R.id.salesfltmennucb) {
            salflNavswitch("nada");
            freexama(salFrodta);

            /* To date */
        } else if (id == R.id.salesfltmennucc) {
            salflNavswitch("nada");
            freexama(salTodta);

            /* New sale */
        } else if (id == R.id.newssalbtn) {
            salflNavswitch("nada");

            Intent intent = new Intent(getApplicationContext(), Salenew.class);
            String nwsaldeterm = "";

            if (saltabManage.getText().toString().equals("salSrecod")) {
                nwsaldeterm = "newSale";
            }

            Bundle bundle = new Bundle();
            bundle.putString("saldeterm", nwsaldeterm);
            intent.putExtras(bundle);

            startActivity(intent);

            /* Drawer menu */
        } else if (id == R.id.salenavmenu) {
            drawMenuswich("cool");
        } else if (id == R.id.liticsdrawclose) {
            drawMenuswich("nada");
        } else if (id == R.id.liticstxtCont || id == R.id.liticstxtConta || id == R.id.liticstxtContb || id == R.id.liticstxtContc || id == R.id.liticstxtContd) {
            ondrwmenuClick(view.getId());

            /* Switch between sales entry and analytics*/
        } else if (id == R.id.salestab) {
            salflNavswitch("nada");
            drawMenuswich("nada");

            findViewById(R.id.newssalbtn).setVisibility(View.VISIBLE);
            findViewById(R.id.salenavmenu).setVisibility(View.GONE);

            String descoss = "salSrecod";
            saltabManage.setText(descoss);
            salLoadsumry();
        } else if (id == R.id.liticstab) {
            salflNavswitch("nada");
            drawMenuswich("nada");

            findViewById(R.id.newssalbtn).setVisibility(View.GONE);
            findViewById(R.id.salenavmenu).setVisibility(View.VISIBLE);

            String descoss = "liticSrecod";
            saltabManage.setText(descoss);
            salLoadsumry();

            /* Blur area onclick */
        } else if (id == R.id.salesblurlay) {
            salflNavswitch("nada");
            drawMenuswich("nada");
        }
    }


    /* Drawer menu switch */
    private void drawMenuswich(String vescos) {
        if (isDrwpen == false) {
            if (vescos.equals("cool")) {
                isDrwpen = true;

                /* Set up font sizes */
                ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.liticsdrwtxtvew,
                        R.id.liticsdrwtxtvewa, R.id.liticsdrwtxtvewb, R.id.liticsdrwtxtvewc, R.id.liticsdrwtxtvewd,
                        R.id.liticsdrwtxtvewe, R.id.liticsdrwtxtvewf));

                /*Set textviews font sizes*/
                TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
                for (int k = 0; k < prdtxvewidec.size(); ++k) {
                    (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) eunica + 4f);
                }

                findViewById(R.id.salesblurlay).setVisibility(View.VISIBLE);
                findViewById(R.id.drawerlay).setVisibility(View.VISIBLE);

                findViewById(R.id.drawerlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.drawopanim));

            }

        } else if (isDrwpen == true) {
            isDrwpen = false;
            //findViewById(R.id.drawerlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.drawclanim));

            findViewById(R.id.salesblurlay).setVisibility(View.GONE);
            findViewById(R.id.drawerlay).setVisibility(View.GONE);

        }

    }

    /*  Side menu logic */
    public void ondrwmenuClick(Integer idec) {
        ArrayList<Integer> txtCintidec = new ArrayList<>(Arrays.asList(R.id.liticstxtCont,
                R.id.liticstxtConta, R.id.liticstxtContb, R.id.liticstxtContc, R.id.liticstxtContd));

        for (int dew = 0; dew < txtCintidec.size(); dew++) {
            if (txtCintidec.get(dew).toString().equals(idec.toString())) {
                findViewById(idec).setBackgroundColor(getResources().getColor(R.color.orangelight));

            } else {
                findViewById(txtCintidec.get(dew)).setBackgroundColor(getResources().getColor(R.color.trans));

            }
        }

        drawMenuswich("nada");

        /* Side navigation */
        if (idec == R.id.liticstxtCont) {
            progbar.setBackgroundColor(getResources().getColor(R.color.dividerclr));
            ((TextView) findViewById(R.id.salestitle_text)).setText(this.getResources().getString(R.string.litics_stri));
            saltabManage.setText("liticSrecod");
        } else if (idec == R.id.liticstxtConta) {
            progbar.setBackgroundColor(getResources().getColor(R.color.dividerclr));
            ((TextView) findViewById(R.id.salestitle_text)).setText(this.getResources().getString(R.string.litics_stra));
            saltabManage.setText("liticSrecoda");
        } else if (idec == R.id.liticstxtContb) {
            progbar.setBackgroundColor(getResources().getColor(R.color.dividerclr));
            ((TextView) findViewById(R.id.salestitle_text)).setText(this.getResources().getString(R.string.litics_strb));
            saltabManage.setText("liticSrecodb");
        } else if (idec == R.id.liticstxtContc) {
            progbar.setBackgroundColor(getResources().getColor(R.color.globbackground));
            ((TextView) findViewById(R.id.salestitle_text)).setText(this.getResources().getString(R.string.litics_strj));
            saltabManage.setText("liticSrecodc");
        } else if (idec == R.id.liticstxtContd) {
            progbar.setBackgroundColor(getResources().getColor(R.color.globbackground));
            ((TextView) findViewById(R.id.salestitle_text)).setText(this.getResources().getString(R.string.litics_stre));
            saltabManage.setText("liticSrecodd");
        }


        /* Data switch */
        salLoadsumry();

    }

    /* Floating menu switch */
    private void salflNavswitch(String desco) {
        if (isFopen == false) {
            if (desco.equals("cool")) {
                if (saltabManage.getText().toString().equals("salSrecod")) {

                } else if (saltabManage.getText().toString().equals("liticSrecod")) {

                }

                isFopen = true;

                findViewById(R.id.salesblurlay).setVisibility(View.VISIBLE);
                findViewById(R.id.salesblurlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fadein_anim));
                findViewById(R.id.salemnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotcloc));

                findViewById(R.id.salesfltmennucb).setVisibility(View.VISIBLE);
                findViewById(R.id.salesfltmennucc).setVisibility(View.VISIBLE);

                findViewById(R.id.salesfabb).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));
                findViewById(R.id.salesfabc).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));

            }

        } else if (isFopen == true) {
            if (saltabManage.getText().toString().equals("salSrecod")) {

            } else if (saltabManage.getText().toString().equals("liticSrecod")) {


            }

            isFopen = false;

            findViewById(R.id.salesfabb).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));
            findViewById(R.id.salesfabc).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));

            findViewById(R.id.salesfltmennucb).setVisibility(View.GONE);
            findViewById(R.id.salesfltmennucc).setVisibility(View.GONE);

            findViewById(R.id.salesblurlay).setVisibility(View.GONE);
            findViewById(R.id.salemnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotantic));

        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        /* Checks the orientation of the screen */
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }

        /* Load sales summary */
        salLoadsumry();
    }

    /*  Change dates */
    public void freexama(final TextView textView) {
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH);
        int day = instance.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat formattera = new SimpleDateFormat("yyyy-MM-dd");

                String mimo = null;
                try {
                    Date date = formatter.parse(day + "-" + (month + 1) + "-" + year);
                    mimo = formattera.format(date);
                } catch (ParseException localParseException) {
                }

                textView.setText(mimo);
                scroLayout.removeAllViews();

                /* Refresh sales summary */
                salLoadsumry();

                ((TextView) findViewById(R.id.salctfabtxtb)).setText(resources.gtDpresocsd(salFrodta.getText().toString()));
                ((TextView) findViewById(R.id.salctfabtxtc)).setText(resources.gtDpresocsd(salTodta.getText().toString()));
            }
        }, year, month, day).show();
    }
}