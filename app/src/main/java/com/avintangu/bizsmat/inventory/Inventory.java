package com.avintangu.bizsmat.inventory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
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
import com.avintangu.bizsmat.resources.Resocs;
import com.google.android.flexbox.FlexboxLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Inventory extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity inventactiv;
    public static ScrollView invscroLayout;
    public static double inveunica;

    Resocs resources;
    Inventui inventui;

    public static ProgressBar invprogbar;
    String detocs;
    boolean isFopen;
    boolean dateanim;

    public static TextView invtabManage;
    public static TextView invStatus;
    public static TextView invFrodta;
    public static TextView invTodta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Inventory.inventactiv = this;
        resources = new Resocs(this);
        inventui = new Inventui(this);
        isFopen = false;
        dateanim = false;

        /* Declarations */
        inveunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.inventitle_text)).setTextSize((float) inveunica + 6f);

        invprogbar = findViewById(R.id.inventprogress);
        invscroLayout = findViewById(R.id.inventscrollview);
        invtabManage = findViewById(R.id.inventTabmngment);
        invStatus = findViewById(R.id.inventStatus);
        invFrodta = findViewById(R.id.invFrodta);
        invTodta = findViewById(R.id.invTodta);

        Bundle bundle = getIntent().getExtras();
        detocs = bundle.getString("determtabmng");
        String statestr = bundle.getString("determstate");
        ((TextView) findViewById(R.id.inventitle_text)).setText(inventui.setinvUitrelis(detocs));
        ((TextView) findViewById(R.id.inventTabmngment)).setText(detocs);
        ((TextView) findViewById(R.id.inventStatus)).setText(statestr);

        /* Adjust dimensions */
        resources.adjsDimens((Guideline) findViewById(R.id.inventactopconsa));

        /* Set up font sizes */
        ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.inventfabtxtb,
                R.id.inventfabtxtc));
        ((Button) findViewById(R.id.inventBck)).setTextSize((float) inveunica + 4.5f);

        /*Set textviews font sizes*/
        TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
        for (int k = 0; k < prdtxvewidec.size(); ++k) {
            (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) inveunica + 4.5f);
        }

        /*  Set dates */
        ArrayList<String> datearry = resources.retStrdate();
        ((TextView) findViewById(R.id.inventfabtxtb)).setText(datearry.get(1));
        ((TextView) findViewById(R.id.inventfabtxtc)).setText(datearry.get(5));
        invFrodta.setText(datearry.get(2));
        invTodta.setText(datearry.get(4));

        /* Set action listeners */
        findViewById(R.id.inventBck).setOnClickListener(this);
        findViewById(R.id.invstkbal).setOnClickListener(this);
        findViewById(R.id.invstkentry).setOnClickListener(this);
        findViewById(R.id.invdatetab).setOnClickListener(this);
        findViewById(R.id.newstkbtn).setOnClickListener(this);

        findViewById(R.id.inventfltmennub).setOnClickListener(this);
        findViewById(R.id.inventfltmennuc).setOnClickListener(this);
        findViewById(R.id.inventblurlay).setOnClickListener(this);

        /* Load products summary */
        inventLodsumary();

    }

    /* Load inventory summary */
    public void inventLodsumary() {
        ArrayList<Integer> izeds = new ArrayList<>(Arrays.asList(R.id.invstkbal, R.id.invstkentry));
        ArrayList<Integer> izedsa = new ArrayList<>(Arrays.asList(R.id.navstkbal, R.id.navstkentry));
        ArrayList<String> tabmng = new ArrayList<>(Arrays.asList("inventSrecod", "inventSrecoda"));

        for (int m = 0; m < izeds.size(); m++) {
            if (invtabManage.getText().toString().equals(tabmng.get(m))) {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.bluu));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.white));

            } else {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.txtcolor));
            }

        }

        dateAnim();
        inventui.inventbackrecdlod(invscroLayout, inveunica, resources.gtColnum(getWindowManager().getDefaultDisplay(), 4, 4),
                invtabManage.getText().toString(), invStatus.getText().toString(), invFrodta.getText().toString(), invTodta.getText().toString(), invprogbar,
                (LinearLayout) findViewById(R.id.inventhedcont));

    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.inventBck) {
            finish();

            /* Floating buttons On action trigger */
        } else if (id == R.id.invdatetab) {
            floatNavswitch("cool");

            /*  From */
        } else if (id == R.id.inventfltmennub) {
            floatNavswitch("nada");
            invCkdates(invFrodta);

            /*  To */
        } else if (id == R.id.inventfltmennuc) {
            floatNavswitch("nada");
            invCkdates(invTodta);

            /*  New stocks */
        } else if (id == R.id.newstkbtn) {
            floatNavswitch("nada");
            Intent intent = new Intent(getApplicationContext(), Inventnwed.class);

            String nwprdeterm = "";

            if (invtabManage.getText().toString().equals("inventSrecoda")) {
                nwprdeterm = "newStocks";

            }

            Bundle bundle = new Bundle();
            bundle.putString("inventdeterm", nwprdeterm);
            intent.putExtras(bundle);

            startActivity(intent);

            /* Switch between stocks balances and stocks entry */
        } else if (id == R.id.invstkbal) {
            floatNavswitch("nada");

            String descoss = "inventSrecod";
            invtabManage.setText(descoss);
            inventLodsumary();
        } else if (id == R.id.invstkentry) {
            floatNavswitch("nada");

            String descoss = "inventSrecoda";
            invtabManage.setText(descoss);
            inventLodsumary();

            /* Blur area onclick */
        } else if (id == R.id.inventblurlay) {
            floatNavswitch("nada");
        }
    }

    /* Floating menu switch */
    private void floatNavswitch(String desco) {
        if (isFopen == false) {
            if (desco.equals("cool")) {

                if (invtabManage.getText().toString().equals("inventSrecod")) {

                } else if (invtabManage.getText().toString().equals("inventSrecoda")) {
                    isFopen = true;

                    findViewById(R.id.inventblurlay).setVisibility(View.VISIBLE);
                    findViewById(R.id.inventblurlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fadein_anim));
                    findViewById(R.id.inventmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotcloc));

                    findViewById(R.id.inventfltmennub).setVisibility(View.VISIBLE);
                    findViewById(R.id.inventfltmennuc).setVisibility(View.VISIBLE);

                    findViewById(R.id.inventfabb).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));
                    findViewById(R.id.inventfabc).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));

                }
            }

        } else if (isFopen == true) {
            if (invtabManage.getText().toString().equals("inventSrecod")) {

            } else if (invtabManage.getText().toString().equals("inventSrecoda")) {
                isFopen = false;

                findViewById(R.id.inventfabb).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));
                findViewById(R.id.inventfabc).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));

                findViewById(R.id.inventfltmennub).setVisibility(View.GONE);
                findViewById(R.id.inventfltmennuc).setVisibility(View.GONE);

            }

            findViewById(R.id.inventblurlay).setVisibility(View.GONE);
            findViewById(R.id.inventmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotantic));

        }

    }

    /* Date animation */
    public void dateAnim() {
        if (invtabManage.getText().toString().equals("inventSrecod")) {
            if (dateanim == true) {
                findViewById(R.id.newstkbtn).setVisibility(View.INVISIBLE);

                ((FlexboxLayout) findViewById(R.id.invdatetab)).setClickable(false);
                ((FlexboxLayout) findViewById(R.id.invdatetab)).setFocusable(false);

                findViewById(R.id.inventmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanim));
                findViewById(R.id.navstkdate).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanim));
                findViewById(R.id.inventmnfab).setVisibility(View.INVISIBLE);
                findViewById(R.id.navstkdate).setVisibility(View.INVISIBLE);

                ColorDrawable[] colDrw = {new ColorDrawable(getResources().getColor(R.color.white)), new ColorDrawable(getResources().getColor(R.color.colsanim))};
                TransitionDrawable trdraw = new TransitionDrawable(colDrw);
                findViewById(R.id.invdatetab).setBackground(trdraw);
                trdraw.startTransition(300);

                dateanim = false;
            }

        } else if (invtabManage.getText().toString().equals("inventSrecoda")) {
            if (dateanim == false) {
                findViewById(R.id.newstkbtn).setVisibility(View.VISIBLE);

                ((FlexboxLayout) findViewById(R.id.invdatetab)).setClickable(true);
                ((FlexboxLayout) findViewById(R.id.invdatetab)).setFocusable(true);

                findViewById(R.id.inventmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanima));
                findViewById(R.id.navstkdate).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanima));
                findViewById(R.id.inventmnfab).setVisibility(View.VISIBLE);
                findViewById(R.id.navstkdate).setVisibility(View.VISIBLE);

                ColorDrawable[] colDrw = {new ColorDrawable(getResources().getColor(R.color.colsanim)), new ColorDrawable(getResources().getColor(R.color.white))};
                TransitionDrawable trdraw = new TransitionDrawable(colDrw);
                findViewById(R.id.invdatetab).setBackground(trdraw);
                trdraw.startTransition(300);

                dateanim = true;
            }
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        /* Checks the orientation of the screen */
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }

        inventLodsumary();
    }

    /*  Change dates */
    public void invCkdates(final TextView textView) {
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
                invscroLayout.removeAllViews();

                /* Refresh sales summary */
                inventLodsumary();

                ((TextView) findViewById(R.id.inventfabtxtb)).setText(resources.gtDpresocsd(invFrodta.getText().toString()));
                ((TextView) findViewById(R.id.inventfabtxtc)).setText(resources.gtDpresocsd(invTodta.getText().toString()));
            }
        }, year, month, day).show();
    }
}