package com.avintangu.bizsmat.debts;

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

public class Debts extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity debtactiv;
    public static ScrollView datscroLayout;
    public static double dateunica;

    Resocs resources;
    Debtsui debtsui;

    public static ProgressBar datprogbar;
    String detocs;
    boolean isFopen;
    boolean dateanim;

    public static TextView databManage;
    public static TextView datStatus;
    public static TextView datFrodta;
    public static TextView daTodta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debts);

        Debts.debtactiv = this;
        resources = new Resocs(this);
        debtsui = new Debtsui(this);
        isFopen = false;
        dateanim = false;

        /* Declarations */
        dateunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.debttitle_text)).setTextSize((float) dateunica + 6f);

        datprogbar = findViewById(R.id.debtprogress);
        datscroLayout = findViewById(R.id.debtscrollview);
        databManage = findViewById(R.id.debtTabmngment);
        datStatus = findViewById(R.id.debtStatus);
        datFrodta = findViewById(R.id.debtFrodta);
        daTodta = findViewById(R.id.debtTodta);

        Bundle bundle = getIntent().getExtras();
        detocs = bundle.getString("determtabmng");
        String statestr = bundle.getString("determstate");
        ((TextView) findViewById(R.id.debttitle_text)).setText(debtsui.setdebtUitrelis(detocs));
        ((TextView) findViewById(R.id.debtTabmngment)).setText(detocs);
        ((TextView) findViewById(R.id.debtStatus)).setText(statestr);

        /* Adjust dimensions */
        resources.adjsDimens((Guideline) findViewById(R.id.debtactopconsa));

        /* Set up font sizes */
        ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.debtfabtxtb,
                R.id.debtfabtxtc));
        ((Button) findViewById(R.id.debtBck)).setTextSize((float) dateunica + 4.5f);

        /*Set textviews font sizes*/
        TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
        for (int k = 0; k < prdtxvewidec.size(); ++k) {
            (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) dateunica + 4.5f);
        }

        /*  Set dates */
        ArrayList<String> datearry = resources.retStrdate();
        ((TextView) findViewById(R.id.debtfabtxtb)).setText(datearry.get(1));
        ((TextView) findViewById(R.id.debtfabtxtc)).setText(datearry.get(5));
        datFrodta.setText(datearry.get(2));
        daTodta.setText(datearry.get(4));


        /* Set action listeners */
        findViewById(R.id.debtBck).setOnClickListener(this);
        findViewById(R.id.newdatbtn).setOnClickListener(this);
        findViewById(R.id.debtsumary).setOnClickListener(this);
        findViewById(R.id.debtpaymnt).setOnClickListener(this);

        findViewById(R.id.datdatetab).setOnClickListener(this);
        findViewById(R.id.debtfltmennub).setOnClickListener(this);
        findViewById(R.id.debtfltmennuc).setOnClickListener(this);
        findViewById(R.id.debtblurlay).setOnClickListener(this);

        /* Load  summary */
        debtLodsumary();
    }

    /*  Load debts summary */
    public void debtLodsumary() {
        Integer minval = 3;
        Integer maxval = 3;

        ArrayList<Integer> izeds = new ArrayList<>(Arrays.asList(R.id.debtsumary, R.id.debtpaymnt));
        ArrayList<Integer> izedsa = new ArrayList<>(Arrays.asList(R.id.navdebtsumry, R.id.navdatpaymnt));
        ArrayList<String> tabmng = new ArrayList<>(Arrays.asList("debtSrecod", "debtSrecoda"));

        for (int m = 0; m < izeds.size(); m++) {
            if (databManage.getText().toString().equals(tabmng.get(m))) {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.bluu));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.white));

            } else {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.txtcolor));
            }

        }

        debtdateAnim();
        debtsui.debtsbackrecdlod(datscroLayout, dateunica, resources.gtColnum(getWindowManager().getDefaultDisplay(), minval, maxval),
                databManage.getText().toString(), datStatus.getText().toString(), datFrodta.getText().toString(), daTodta.getText().toString(), datprogbar,
                (LinearLayout) findViewById(R.id.debtshedcont));

    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.debtBck) {
            finish();

            /* Floating buttons On action trigger */
        } else if (id == R.id.datdatetab) {
            datflNavswitch("cool");

            /*  From */
        } else if (id == R.id.debtfltmennub) {
            datflNavswitch("nada");
            debtsCkdates(datFrodta);

            /*  To */
        } else if (id == R.id.debtfltmennuc) {
            datflNavswitch("nada");
            debtsCkdates(daTodta);

            /*  New  customer */
        } else if (id == R.id.newdatbtn) {
            datflNavswitch("nada");
            String descos = "";

            if (databManage.getText().toString().equals("debtSrecod")) {
                descos = "newCustomer";

            } else if (databManage.getText().toString().equals("debtSrecoda")) {
                descos = "newPayment";

            }

            Intent intent = new Intent(getApplicationContext(), Debtsnwed.class);

            Bundle bundle = new Bundle();
            bundle.putString("debtdeterm", descos);
            intent.putExtras(bundle);

            startActivity(intent);

            /* Switch between debts and payments*/
        } else if (id == R.id.debtsumary) {
            datflNavswitch("nada");

            String descoss = "debtSrecod";
            databManage.setText(descoss);
            debtLodsumary();
        } else if (id == R.id.debtpaymnt) {
            datflNavswitch("nada");

            String descoss = "debtSrecoda";
            databManage.setText(descoss);
            debtLodsumary();

            /* Blur area onclick */
        } else if (id == R.id.debtblurlay) {
            datflNavswitch("nada");
        }
    }


    /* Floating menu switch */
    private void datflNavswitch(String desco) {
        if (isFopen == false) {
            if (desco.equals("cool")) {

                if (databManage.getText().toString().equals("debtSrecod")) {

                } else if (databManage.getText().toString().equals("debtSrecoda")) {
                    isFopen = true;

                    findViewById(R.id.debtblurlay).setVisibility(View.VISIBLE);
                    findViewById(R.id.debtblurlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fadein_anim));
                    findViewById(R.id.debtsmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotcloc));

                    findViewById(R.id.debtfltmennub).setVisibility(View.VISIBLE);
                    findViewById(R.id.debtfltmennuc).setVisibility(View.VISIBLE);

                    findViewById(R.id.debtfabb).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));
                    findViewById(R.id.debtfabc).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));

                }
            }

        } else if (isFopen == true) {
            if (databManage.getText().toString().equals("debtSrecod")) {

            } else if (databManage.getText().toString().equals("debtSrecoda")) {
                isFopen = false;

                findViewById(R.id.debtfabb).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));
                findViewById(R.id.debtfabc).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));

                findViewById(R.id.debtfltmennub).setVisibility(View.GONE);
                findViewById(R.id.debtfltmennuc).setVisibility(View.GONE);

            }

            findViewById(R.id.debtblurlay).setVisibility(View.GONE);
            findViewById(R.id.debtsmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotantic));

        }

    }

    /* Date animation */
    public void debtdateAnim() {
        if (databManage.getText().toString().equals("debtSrecod")) {
            if (dateanim == true) {
                ((FlexboxLayout) findViewById(R.id.datdatetab)).setClickable(false);
                ((FlexboxLayout) findViewById(R.id.datdatetab)).setFocusable(false);

                findViewById(R.id.debtsmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanim));
                findViewById(R.id.navdatdate).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanim));
                findViewById(R.id.debtsmnfab).setVisibility(View.INVISIBLE);
                findViewById(R.id.navdatdate).setVisibility(View.INVISIBLE);

                ColorDrawable[] colDrw = {new ColorDrawable(getResources().getColor(R.color.white)), new ColorDrawable(getResources().getColor(R.color.colsanim))};
                TransitionDrawable trdraw = new TransitionDrawable(colDrw);
                findViewById(R.id.datdatetab).setBackground(trdraw);
                trdraw.startTransition(300);

                dateanim = false;
            }

        } else if (databManage.getText().toString().equals("debtSrecoda")) {
            if (dateanim == false) {
                ((FlexboxLayout) findViewById(R.id.datdatetab)).setClickable(true);
                ((FlexboxLayout) findViewById(R.id.datdatetab)).setFocusable(true);

                findViewById(R.id.debtsmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanima));
                findViewById(R.id.navdatdate).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanima));
                findViewById(R.id.debtsmnfab).setVisibility(View.VISIBLE);
                findViewById(R.id.navdatdate).setVisibility(View.VISIBLE);

                ColorDrawable[] colDrw = {new ColorDrawable(getResources().getColor(R.color.colsanim)), new ColorDrawable(getResources().getColor(R.color.white))};
                TransitionDrawable trdraw = new TransitionDrawable(colDrw);
                findViewById(R.id.datdatetab).setBackground(trdraw);
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

        debtLodsumary();
    }

    /*  Change dates */
    public void debtsCkdates(final TextView textView) {
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
                datscroLayout.removeAllViews();

                /* Refresh sales summary */
                debtLodsumary();

                ((TextView) findViewById(R.id.debtfabtxtb)).setText(resources.gtDpresocsd(datFrodta.getText().toString()));
                ((TextView) findViewById(R.id.debtfabtxtc)).setText(resources.gtDpresocsd(daTodta.getText().toString()));
            }
        }, year, month, day).show();
    }

}