package com.avintangu.bizsmat.orders;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.resources.Resocs;
import com.google.android.flexbox.FlexboxLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Orders extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity ordesactive;
    public static double ordeunica;
    String detocs;

    Resocs resources;
    Ordersui ordersui;
    Ordersresoc ordersresoc;
    Dbcluzz dbcluzz;

    public static ProgressBar ordesprogbar;
    public static ScrollView ordscroLayout;
    public static TextView ordtabManage;
    public static TextView ordStatus;
    public static TextView ordFrodta;
    public static TextView ordTodta;
    public static LinearLayout ordeshedcon;

    boolean dateanim;
    boolean isFopen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Orders.ordesactive = this;

        resources = new Resocs(this);
        ordersui = new Ordersui(this);
        ordersresoc = new Ordersresoc(this);
        dbcluzz = new Dbcluzz(this);

        dateanim = false;
        isFopen = false;

        /* Declarations */
        ordeunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.ordestitle_text)).setTextSize((float) ordeunica + 6f);

        ordesprogbar = findViewById(R.id.ordesprogress);
        ordscroLayout = findViewById(R.id.ordescrollview);
        ordtabManage = findViewById(R.id.ordesTabmngment);
        ordStatus = findViewById(R.id.ordeStatus);
        ordFrodta = findViewById(R.id.ordesFrodta);
        ordTodta = findViewById(R.id.ordesTodta);
        ordeshedcon = findViewById(R.id.ordershedcont);

        Bundle bundle = getIntent().getExtras();
        detocs = bundle.getString("determtabmng");
        String statestr = bundle.getString("determstate");
        String loadeterm = bundle.getString("determstata");
        ((TextView) findViewById(R.id.ordestitle_text)).setText(ordersui.setordUitrelis(detocs));
        ((TextView) findViewById(R.id.ordesTabmngment)).setText(detocs);
        ((TextView) findViewById(R.id.ordeStatus)).setText(statestr);

        /* Adjust dimensions */
        resources.adjsDimens((Guideline) findViewById(R.id.ordersconstra));

        /* Set up font sizes */
        /*Set buttons font sizes*/
        ArrayList<Integer> prdbtnidec = new ArrayList<>(Arrays.asList(R.id.ordesBck, R.id.newordesbtn));
        TextView[] Btnarry = new TextView[prdbtnidec.size()];
        for (int k = 0; k < prdbtnidec.size(); ++k) {
            (Btnarry[k] = findViewById(prdbtnidec.get(k))).setTextSize((float) ordeunica + 4.5f);
        }

        /*Set textviews font sizes*/
        ArrayList<Integer> prdtxvewidec = new ArrayList<>();
        TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
        for (int k = 0; k < prdtxvewidec.size(); ++k) {
            (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) ordeunica + 4.5f);
        }

        /*  Set dates */
        ArrayList<String> datearry = resources.retStrdate();
        ((TextView) findViewById(R.id.ordesfabtxt)).setText(datearry.get(1));
        ((TextView) findViewById(R.id.ordesfabtxta)).setText(datearry.get(5));
        ordFrodta.setText(datearry.get(2));
        ordTodta.setText(datearry.get(4));

        /* Set distributors */
        ArrayList<String> distros = ordersresoc.getaAdistros();
        (((TextView) findViewById(R.id.distrospin))).setText(distros.get(0));
        resources.spinMod(((TextView) findViewById(R.id.distrospin)), ordeunica, ordersresoc.getaAdistros(), "zap");

        findViewById(R.id.ordesBck).setOnClickListener(this);
        findViewById(R.id.ordprclist).setOnClickListener(this);
        findViewById(R.id.ordordes).setOnClickListener(this);
        findViewById(R.id.orddatetab).setOnClickListener(this);
        findViewById(R.id.newordesbtn).setOnClickListener(this);
        findViewById(R.id.ordrefreshlst).setOnClickListener(this);

        findViewById(R.id.ordesfltmennu).setOnClickListener(this);
        findViewById(R.id.ordesfltmennua).setOnClickListener(this);
        findViewById(R.id.ordesblurlay).setOnClickListener(this);

        findViewById(R.id.distrospin).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                gtDistdetails((((TextView) findViewById(R.id.distrospin))).getText().toString());

                return true;
            }
        });

        /* Load products summary */
        ordesLodsumary();


    }

    /* Load Orders summary */
    public void ordesLodsumary() {
        ArrayList<Integer> izeds = new ArrayList<>(Arrays.asList(R.id.ordprclist, R.id.ordordes));
        ArrayList<Integer> izedsa = new ArrayList<>(Arrays.asList(R.id.navordprclist, R.id.navordordes));
        ArrayList<String> tabmng = new ArrayList<>(Arrays.asList("ordeSrecod", "ordeSrecoda"));

        ((TextView) findViewById(R.id.ordestitle_text)).setText(ordersui.setordUitrelis(ordtabManage.getText().toString()));

        if (ordtabManage.getText().toString().equals("ordeSrecod")) {
            (((TextView) findViewById(R.id.distrospin))).setVisibility(View.VISIBLE);

        } else if (ordtabManage.getText().toString().equals("ordeSrecoda")) {
            (((TextView) findViewById(R.id.distrospin))).setVisibility(View.GONE);

        }

        for (int m = 0; m < izeds.size(); m++) {
            if (ordtabManage.getText().toString().equals(tabmng.get(m))) {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.bluu));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.white));

            } else {
                ((FlexboxLayout) findViewById(izeds.get(m))).setBackgroundColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(izedsa.get(m))).setTextColor(getResources().getColor(R.color.txtcolor));
            }

        }

        dateAnim();
        ordersui.ordbackrecdlod(ordscroLayout, ordeunica, resources.gtColnum(getWindowManager().getDefaultDisplay(), 4, 6),
                ordtabManage.getText().toString(), ordStatus.getText().toString(), ordFrodta.getText().toString(),
                ordTodta.getText().toString(), ordesprogbar, (((TextView) findViewById(R.id.distrospin))).getText().toString(),
                ordeshedcon);

    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.ordesBck) {
            finish();

            /* Refresh price-list */
        } else if (id == R.id.ordrefreshlst) {/* Refresh price-list with distributors list */
            ArrayList<String> usrdetals = dbcluzz.getUsrdetal();
            JSONArray usrvalsjson = new JSONArray();

            for (int y = 0; y < usrdetals.size(); y++) {
                usrvalsjson.put(dbcluzz.strEncodbs(usrdetals.get(y)));
            }

            JSONObject objdetalz = new JSONObject();
            try {
                objdetalz.put("determ", dbcluzz.strEncodbs("refreshdistlist"));
                objdetalz.put("vals", usrvalsjson);

            } catch (JSONException e) {
            }

            ordersresoc.gtDistprcc(objdetalz, "refreshactive");

            /* New order */
        } else if (id == R.id.newordesbtn) {
            Intent intent = new Intent(getApplicationContext(), Ordesnew.class);

            String nwprdeterm = "";

            if (ordtabManage.getText().toString().equals("ordeSrecod")) {

            } else if (ordtabManage.getText().toString().equals("ordeSrecoda")) {
                nwprdeterm = "newOrder";

            }

            Bundle bundle = new Bundle();
            bundle.putString("ordeterm", nwprdeterm);
            intent.putExtras(bundle);

            startActivity(intent);

            /* Floating buttons On action trigger */
        } else if (id == R.id.orddatetab) {
            fltordNavswitch("cool");

            /*  From */
        } else if (id == R.id.ordesfltmennu) {
            fltordNavswitch("nada");
            ordCkdates(ordFrodta);

            /*  To */
        } else if (id == R.id.ordesfltmennua) {
            fltordNavswitch("nada");
            ordCkdates(ordTodta);

            /* Switch between stocks balances and stocks entry */
        } else if (id == R.id.ordprclist) {
            fltordNavswitch("nada");

            String descoss = "ordeSrecod";
            ordtabManage.setText(descoss);
            ordesLodsumary();
        } else if (id == R.id.ordordes) {
            fltordNavswitch("nada");

            String descoss = "ordeSrecoda";
            ordtabManage.setText(descoss);
            ordesLodsumary();

            /* Blur area onclick */
        } else if (id == R.id.ordesblurlay) {
            fltordNavswitch("nada");
        }
    }

    /* Floating menu switch */
    private void fltordNavswitch(String desco) {
        if (isFopen == false) {
            if (desco.equals("cool")) {

                if (ordtabManage.getText().toString().equals("ordeSrecod")) {

                } else if (ordtabManage.getText().toString().equals("ordeSrecoda")) {
                    isFopen = true;

                    findViewById(R.id.ordesblurlay).setVisibility(View.VISIBLE);
                    findViewById(R.id.ordesblurlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fadein_anim));
                    findViewById(R.id.ordsmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotcloc));

                    findViewById(R.id.ordesfltmennu).setVisibility(View.VISIBLE);
                    findViewById(R.id.ordesfltmennua).setVisibility(View.VISIBLE);

                    findViewById(R.id.ordesfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));
                    findViewById(R.id.ordesfaba).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_open));

                }
            }

        } else if (isFopen == true) {
            if (ordtabManage.getText().toString().equals("ordeSrecod")) {

            } else if (ordtabManage.getText().toString().equals("ordeSrecoda")) {
                isFopen = false;

                findViewById(R.id.ordesfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));
                findViewById(R.id.ordesfaba).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_close));

                findViewById(R.id.ordesfltmennu).setVisibility(View.GONE);
                findViewById(R.id.ordesfltmennua).setVisibility(View.GONE);

            }

            findViewById(R.id.ordesblurlay).setVisibility(View.GONE);
            findViewById(R.id.ordsmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.fab_rotantic));

        }

    }

    /* Date animation */
    public void dateAnim() {
        if (ordtabManage.getText().toString().equals("ordeSrecod")) {
            if (dateanim == true) {
                findViewById(R.id.newordesbtn).setVisibility(View.GONE);
                findViewById(R.id.ordrefreshlst).setVisibility(View.VISIBLE);

                ((FlexboxLayout) findViewById(R.id.orddatetab)).setClickable(false);
                ((FlexboxLayout) findViewById(R.id.orddatetab)).setFocusable(false);

                findViewById(R.id.ordsmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanim));
                findViewById(R.id.navorddate).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanim));
                findViewById(R.id.ordsmnfab).setVisibility(View.INVISIBLE);
                findViewById(R.id.navorddate).setVisibility(View.INVISIBLE);

                ColorDrawable[] colDrw = {new ColorDrawable(getResources().getColor(R.color.white)), new ColorDrawable(getResources().getColor(R.color.colsanim))};
                TransitionDrawable trdraw = new TransitionDrawable(colDrw);
                findViewById(R.id.orddatetab).setBackground(trdraw);
                trdraw.startTransition(300);

                dateanim = false;
            }

        } else if (ordtabManage.getText().toString().equals("ordeSrecoda")) {
            if (dateanim == false) {
                findViewById(R.id.newordesbtn).setVisibility(View.VISIBLE);
                findViewById(R.id.ordrefreshlst).setVisibility(View.GONE);

                ((FlexboxLayout) findViewById(R.id.orddatetab)).setClickable(true);
                ((FlexboxLayout) findViewById(R.id.orddatetab)).setFocusable(true);

                findViewById(R.id.ordsmnfab).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanima));
                findViewById(R.id.navorddate).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.dateanima));
                findViewById(R.id.ordsmnfab).setVisibility(View.VISIBLE);
                findViewById(R.id.navorddate).setVisibility(View.VISIBLE);

                ColorDrawable[] colDrw = {new ColorDrawable(getResources().getColor(R.color.colsanim)), new ColorDrawable(getResources().getColor(R.color.white))};
                TransitionDrawable trdraw = new TransitionDrawable(colDrw);
                findViewById(R.id.orddatetab).setBackground(trdraw);
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

        ordesLodsumary();
    }

    /*  Change dates */
    public void ordCkdates(final TextView textView) {
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
                ordscroLayout.removeAllViews();

                /* Refresh sales summary */
                ordesLodsumary();

                ((TextView) findViewById(R.id.ordesfabtxt)).setText(resources.gtDpresocsd(ordFrodta.getText().toString()));
                ((TextView) findViewById(R.id.ordesfabtxta)).setText(resources.gtDpresocsd(ordTodta.getText().toString()));
            }
        }, year, month, day).show();
    }

    /* Distributor details */
    public void gtDistdetails(String title) {
        ArrayList<String> Sres = new ArrayList<>(Arrays.asList("distroscname", "distrosccountry", "distroscreggy",
                "distrosctown", "distroscpsn", "distroscnum", "distroscmail"));

        ArrayList<String> distrosarry = new ArrayList<>(Arrays.asList(title.split(" -> ")));
        ArrayList<String> singdist = ordersresoc.gtordSingrecdb(distrosarry.get(0), Sres);

        if (singdist.size() > 0) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.distrodetails, null);
            dialog.setView(dialogView);
            dialog.setTitle(distrosarry.get(0));
            Button btncancel = dialogView.findViewById(R.id.cnclButton);

            /*Set buttons font sizes*/
            ArrayList<Integer> prdbtnidec = new ArrayList<>(Arrays.asList(R.id.cnclButton));
            TextView[] Btnarry = new TextView[prdbtnidec.size()];
            for (int k = 0; k < prdbtnidec.size(); ++k) {
                (Btnarry[k] = dialogView.findViewById(prdbtnidec.get(k))).setTextSize((float) ordeunica + 4.5f);
            }

            /*Set textviews font sizes*/
            ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.distsingpop, R.id.distsingpopa,
                    R.id.distsingpopb, R.id.distsingpopc, R.id.distsingpopd, R.id.distsingpope, R.id.distsingpopf,
                    R.id.distsingpopg, R.id.distsingpoph, R.id.distsingpopi, R.id.distsingpopj, R.id.distsingpopk,
                    R.id.distsingpopl, R.id.distsingpopm));

            ArrayList<Integer> prdtxvewideca = new ArrayList<>(Arrays.asList(R.id.distsingpopa, R.id.distsingpopc, R.id.distsingpope,
                    R.id.distsingpopg, R.id.distsingpopi, R.id.distsingpopk, R.id.distsingpopm));

            TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
            for (int k = 0; k < prdtxvewidec.size(); ++k) {
                (Txvewarry[k] = dialogView.findViewById(prdtxvewidec.get(k))).setTextSize((float) ordeunica + 4.5f);
            }


            for (int sok = 0; sok < prdtxvewideca.size(); sok++) {
                ((TextView) dialogView.findViewById(prdtxvewideca.get(sok))).setText(singdist.get(sok));

            }


            dialog.create();
            final AlertDialog show = dialog.show();

            btncancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    show.dismiss();
                }
            });
        }
    }
}