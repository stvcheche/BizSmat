package com.avintangu.bizsmat.analytics;

import android.app.DatePickerDialog;
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
import com.avintangu.bizsmat.resources.Resocs;
import com.avintangu.bizsmat.resources.Swipeaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Analytics extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity liticactiv;
    public static ScrollView liticscroLayout;
    public static double liticeunica;

    Resocs resources;
    Analyticsui analyticsui;

    public static ProgressBar liticprogbar;
    String detocs;
    boolean isFopen;
    boolean isDrwpen;

    public static TextView litictabManage;
    public static TextView liticFrodta;
    public static TextView liticTodta;
    private ArrayList<String> datearry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        Analytics.liticactiv = this;
        resources = new Resocs(this);
        analyticsui = new Analyticsui(this);
        isFopen = false;
        isDrwpen = false;

        /* Declarations */
        liticeunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.litictitle_text)).setTextSize((float) liticeunica + 6f);

        liticprogbar = findViewById(R.id.liticprogress);
        liticscroLayout = findViewById(R.id.liticscrollview);
        litictabManage = findViewById(R.id.liticTabmngment);
        liticFrodta = findViewById(R.id.liticFrodta);
        liticTodta = findViewById(R.id.liticTodta);

        Bundle bundle = getIntent().getExtras();
        detocs = bundle.getString("determtabmng");
        String statestr = bundle.getString("determstate");
        ((TextView) findViewById(R.id.litictitle_text)).setText(analyticsui.setliticUitrelis(detocs));
        litictabManage.setText(detocs);

        /* Adjust dimensions */
        resources.adjsDimens((Guideline) findViewById(R.id.liticactopconsa));

        /* Set up font sizes */
        ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.liticfabtxt,
                R.id.liticfabtxta, R.id.liticfabtxtb));
        ((Button) findViewById(R.id.liticBck)).setTextSize((float) liticeunica + 4.5f);

        /*Set textviews font sizes*/
        TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
        for (int k = 0; k < prdtxvewidec.size(); ++k) {
            (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) liticeunica + 4.5f);
        }

        /*  Set dates */
        datearry = resources.retStrdate();
        String dtaStr = "From: " + datearry.get(1) + "  To: " + datearry.get(5);
        ((TextView) findViewById(R.id.liticfabtxtb)).setText(dtaStr);
        liticFrodta.setText(datearry.get(2));
        liticTodta.setText(datearry.get(4));

        findViewById(R.id.liticBck).setOnClickListener(this);
        findViewById(R.id.liticnavmenu).setOnClickListener(this);
        findViewById(R.id.liticmnfab).setOnClickListener(this);
        findViewById(R.id.liticfab).setOnClickListener(this);
        findViewById(R.id.liticfaba).setOnClickListener(this);

        /*  Drawer items */
        findViewById(R.id.liticsdrawclose).setOnClickListener(this);
        findViewById(R.id.liticstxtCont).setOnClickListener(this);
        findViewById(R.id.liticstxtCont).setBackgroundColor(getResources().getColor(R.color.orangelight));
        findViewById(R.id.liticstxtConta).setOnClickListener(this);
        findViewById(R.id.liticstxtContb).setOnClickListener(this);
        findViewById(R.id.liticstxtContc).setOnClickListener(this);
        findViewById(R.id.liticstxtContd).setOnClickListener(this);
        findViewById(R.id.liticblurlay).setOnClickListener(this);

        /*  Activity swipe listener */
        findViewById(R.id.liticsParentcont).setOnTouchListener(new Swipeaction(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if (isDrwpen) {
                    closeDrawa();

                }
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if (!isDrwpen) {
                    openDraw();

                }
            }
        });

        /* Data switch */
        liticLodsumary();

    }

    /* Data switch */
    public void liticLodsumary() {
        analyticsui.liticsbackrecdlod(liticscroLayout, liticeunica, litictabManage.getText().toString(),
                liticFrodta.getText().toString(), liticTodta.getText().toString(), liticprogbar, (LinearLayout) findViewById(R.id.saleshedcont));

    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.liticBck) {
            finish();
        } else if (id == R.id.liticnavmenu) {
            if (!isDrwpen) {
                openDraw();

            } else {
                closeDraw();

            }
        } else if (id == R.id.liticsdrawclose) {
            closeDraw();
        } else if (id == R.id.liticstxtCont || id == R.id.liticstxtConta || id == R.id.liticstxtContb || id == R.id.liticstxtContc || id == R.id.liticstxtContd) {
            ondrwmenuClick(view.getId());

            /* Floating buttons On action trigger */
        } else if (id == R.id.liticmnfab) {
            if (!isFopen) {
                openliticMenu();

            } else {
                closeliticMenu();

            }

            /*  From */
        } else if (id == R.id.liticfab) {
            closeliticMenu();
            liticCkdates(liticFrodta);

            /*  To */
        } else if (id == R.id.liticfaba) {
            closeliticMenu();
            liticCkdates(liticTodta);

            /* Blur area onclick */
        } else if (id == R.id.liticblurlay) {
            closeliticMenu();
        }
    }

    /*  Open drawer */
    public void openDraw() {
        isDrwpen = true;

        /* Set up font sizes */
        ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.liticsdrwtxtvew,
                R.id.liticsdrwtxtvewa, R.id.liticsdrwtxtvewb, R.id.liticsdrwtxtvewc, R.id.liticsdrwtxtvewd,
                R.id.liticsdrwtxtvewe, R.id.liticsdrwtxtvewf));

        /*Set textviews font sizes*/
        TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
        for (int k = 0; k < prdtxvewidec.size(); ++k) {
            (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) liticeunica + 4f);
        }

        findViewById(R.id.drawblurlay).setVisibility(View.VISIBLE);
        findViewById(R.id.drawerlay).setVisibility(View.VISIBLE);

        findViewById(R.id.drawerlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.drawopanim));

    }

    /*  Close drawer */
    public void closeDraw() {
        isDrwpen = false;
        //findViewById(R.id.drawerlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.drawclanim));

        findViewById(R.id.drawblurlay).setVisibility(View.GONE);
        findViewById(R.id.drawerlay).setVisibility(View.GONE);

    }

    public void closeDrawa() {
        isDrwpen = false;
        findViewById(R.id.drawerlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.drawclanim));

        findViewById(R.id.drawblurlay).setVisibility(View.GONE);
        findViewById(R.id.drawerlay).setVisibility(View.GONE);

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

        closeDraw();

        /* Side navigation */
        if (idec == R.id.liticstxtCont) {
            liticprogbar.setBackgroundColor(getResources().getColor(R.color.dividerclr));
            ((TextView) findViewById(R.id.litictitle_text)).setText(this.getResources().getString(R.string.litics_stri));
            litictabManage.setText("liticSrecod");
        } else if (idec == R.id.liticstxtConta) {
            liticprogbar.setBackgroundColor(getResources().getColor(R.color.dividerclr));
            ((TextView) findViewById(R.id.litictitle_text)).setText(this.getResources().getString(R.string.litics_stra));
            litictabManage.setText("liticSrecoda");
        } else if (idec == R.id.liticstxtContb) {
            liticprogbar.setBackgroundColor(getResources().getColor(R.color.dividerclr));
            ((TextView) findViewById(R.id.litictitle_text)).setText(this.getResources().getString(R.string.litics_strb));
            litictabManage.setText("liticSrecodb");
        } else if (idec == R.id.liticstxtContc) {
            liticprogbar.setBackgroundColor(getResources().getColor(R.color.globbackground));
            ((TextView) findViewById(R.id.litictitle_text)).setText(this.getResources().getString(R.string.litics_strj));
            litictabManage.setText("liticSrecodc");
        } else if (idec == R.id.liticstxtContd) {
            liticprogbar.setBackgroundColor(getResources().getColor(R.color.globbackground));
            ((TextView) findViewById(R.id.litictitle_text)).setText(this.getResources().getString(R.string.litics_stre));
            litictabManage.setText("liticSrecodd");
        }

        if (idec == R.id.liticstxtCont || idec == R.id.liticstxtConta || idec == R.id.liticstxtContb) {
            String dtaStr = "From: " + datearry.get(1) + "  To: " + datearry.get(5);
            ((TextView) findViewById(R.id.liticfabtxtb)).setText(dtaStr);
            liticFrodta.setText(datearry.get(2));
            liticTodta.setText(datearry.get(4));

        } else if (idec == R.id.liticstxtContc || idec == R.id.liticstxtContd) {
            String dtaStr = "From: " + datearry.get(11) + "  To: " + datearry.get(5);
            ((TextView) findViewById(R.id.liticfabtxtb)).setText(dtaStr);
            liticFrodta.setText(datearry.get(10));
            liticTodta.setText(datearry.get(4));

        }

        /* Data switch */
        liticLodsumary();

    }

    /* opening floating menu */
    private void openliticMenu() {
        isFopen = true;

        findViewById(R.id.liticblurlay).setVisibility(View.VISIBLE);
        findViewById(R.id.liticblurlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fadein_anim));

        findViewById(R.id.liticfltmennu).setVisibility(View.VISIBLE);
        findViewById(R.id.liticfltmennua).setVisibility(View.VISIBLE);

        findViewById(R.id.liticfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));
        findViewById(R.id.liticfaba).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));

        findViewById(R.id.liticfab).setClickable(true);
        findViewById(R.id.liticfaba).setClickable(true);

        findViewById(R.id.liticmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotcloc));

    }

    /* close floating menu */
    private void closeliticMenu() {
        isFopen = false;

        findViewById(R.id.liticblurlay).setVisibility(View.GONE);

        findViewById(R.id.liticfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));
        findViewById(R.id.liticfaba).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));

        findViewById(R.id.liticfab).setClickable(false);
        findViewById(R.id.liticfaba).setClickable(false);

        findViewById(R.id.liticfltmennu).setVisibility(View.GONE);
        findViewById(R.id.liticfltmennua).setVisibility(View.GONE);

        findViewById(R.id.liticmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotantic));

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        /* Checks the orientation of the screen */
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }

        /* Data switch */
        liticLodsumary();
    }

    /*  Change dates */
    public void liticCkdates(final TextView textView) {
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
                liticscroLayout.removeAllViews();

                /* Data switch */
                liticLodsumary();

                ((TextView) findViewById(R.id.liticfabtxtb)).setText(resources.gtDpresocsc(liticFrodta.getText().toString(), liticTodta.getText().toString()));
            }
        }, year, month, day).show();
    }

}
