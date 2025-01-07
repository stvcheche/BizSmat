package com.avintangu.bizsmat.admin;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.resources.Resocs;
import com.avintangu.bizsmat.resources.Swipeaction;

import java.util.ArrayList;
import java.util.Arrays;

public class Admin extends AppCompatActivity implements View.OnClickListener {
    public static AppCompatActivity adminactiv;
    public static ScrollView admscroLayout;
    public static double admeunica;

    Resocs resources;
    Adminui adminui;
    boolean isDrwpen;

    public static ProgressBar admprogbar;
    String detocs;
    public static TextView admtabManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Admin.adminactiv = this;
        resources = new Resocs(this);
        adminui = new Adminui(this);
        isDrwpen = false;

        /* Declarations */
        admeunica = resources.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        ((TextView) findViewById(R.id.admintitle_text)).setTextSize((float) admeunica + 6f);
        ((Button) findViewById(R.id.adminBck)).setTextSize((float) admeunica + 4.5f);

        admprogbar = findViewById(R.id.adminprogress);
        admscroLayout = findViewById(R.id.adminscrollview);
        admtabManage = findViewById(R.id.adminTabmngment);

        Bundle bundle = getIntent().getExtras();
        detocs = bundle.getString("determtabmng");
        String statestr = bundle.getString("determstate");
        ((TextView) findViewById(R.id.admintitle_text)).setText(adminui.setadmUitrelis(detocs));
        admtabManage.setText(detocs);

        /* Adjust dimensions */
        resources.adjsDimens((Guideline) findViewById(R.id.adminactopconsa));

        if (admtabManage.getText().toString().equals("adminSrecoda")) {
            admprogbar.setBackgroundColor(getResources().getColor(R.color.bluu));

        } else {
            admprogbar.setBackgroundColor(getResources().getColor(R.color.globbackground));

        }

        /* Set action listeners */
        findViewById(R.id.adminBck).setOnClickListener(this);
        findViewById(R.id.adminavmenu).setOnClickListener(this);

        /*  Drawer items */
        findViewById(R.id.admndrawclose).setOnClickListener(this);
        findViewById(R.id.admintxtCont).setOnClickListener(this);
        findViewById(R.id.admintxtCont).setBackgroundColor(getResources().getColor(R.color.orangelight));
        findViewById(R.id.admintxtConta).setOnClickListener(this);
        findViewById(R.id.admintxtContb).setOnClickListener(this);
        findViewById(R.id.admintxtContc).setOnClickListener(this);
        findViewById(R.id.admintxtContd).setOnClickListener(this);
        findViewById(R.id.admintxtConte).setOnClickListener(this);
        findViewById(R.id.admintxtContf).setOnClickListener(this);
        findViewById(R.id.admintxtContg).setOnClickListener(this);
        findViewById(R.id.admintxtConth).setOnClickListener(this);
        findViewById(R.id.admintxtConti).setOnClickListener(this);
        findViewById(R.id.admintxtContga).setOnClickListener(this);

        /*  Activity swipe listener */
        findViewById(R.id.adminscrollview).setOnTouchListener(new Swipeaction(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if (isDrwpen) {
                    admcloseDrawa();

                }
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if (!isDrwpen) {
                    admopenDraw();

                }
            }
        });

        /* Load report details*/
        prdLodadmin();

    }

    /* Load report details*/
    public void prdLodadmin() {
        adminui.admbackrecdlod(admscroLayout, admeunica, resources.gtColnum(getWindowManager().getDefaultDisplay(), 3, 6),
                admtabManage.getText().toString(), admprogbar);

    }

    /*  Onclick listener */
    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.adminBck) {
            finish();
        } else if (id == R.id.adminavmenu) {
            if (!isDrwpen) {
                admopenDraw();

            } else {
                admcloseDraw();

            }
        } else if (id == R.id.admndrawclose) {
            admcloseDraw();
        } else if (id == R.id.admintxtCont || id == R.id.admintxtConta || id == R.id.admintxtContb || id == R.id.admintxtContc || id == R.id.admintxtContd || id == R.id.admintxtConte || id == R.id.admintxtContf || id == R.id.admintxtContg || id == R.id.admintxtConth || id == R.id.admintxtConti || id == R.id.admintxtContga) {
            adondrwmenuClick(view.getId());
        }
    }

    /*  Open drawer */
    public void admopenDraw() {
        isDrwpen = true;

        /* Set up font sizes */
        ArrayList<Integer> prdtxvewidec = new ArrayList<>(Arrays.asList(R.id.admndrwtxtvew,
                R.id.admndrwtxtvewa, R.id.admndrwtxtvewb, R.id.admndrwtxtvewc, R.id.admndrwtxtvewd,
                R.id.admndrwtxtvewe, R.id.admndrwtxtvewf, R.id.admndrwtxtvewg, R.id.admndrwtxtvewh,
                R.id.admndrwtxtvewi, R.id.admndrwtxtvewj, R.id.admndrwtxtvewk));

        /*Set textviews font sizes*/
        TextView[] Txvewarry = new TextView[prdtxvewidec.size()];
        for (int k = 0; k < prdtxvewidec.size(); ++k) {
            (Txvewarry[k] = findViewById(prdtxvewidec.get(k))).setTextSize((float) admeunica + 4f);
        }

        findViewById(R.id.admdrawblurlay).setVisibility(View.VISIBLE);
        findViewById(R.id.admdrawerlay).setVisibility(View.VISIBLE);

        findViewById(R.id.admdrawerlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.drawopanim));

    }

    /*  Close drawer */
    public void admcloseDraw() {
        isDrwpen = false;
        //findViewById(R.id.admdrawerlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.drawclanim));

        findViewById(R.id.admdrawblurlay).setVisibility(View.GONE);
        findViewById(R.id.admdrawerlay).setVisibility(View.GONE);

    }

    public void admcloseDrawa() {
        isDrwpen = false;
        findViewById(R.id.admdrawerlay).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.drawclanim));

        findViewById(R.id.admdrawblurlay).setVisibility(View.GONE);
        findViewById(R.id.admdrawerlay).setVisibility(View.GONE);

    }

    /*  Side menu logic */
    public void adondrwmenuClick(Integer idec) {
        ArrayList<Integer> txtCintidec = new ArrayList<>(Arrays.asList(R.id.admintxtCont,
                R.id.admintxtConta, R.id.admintxtContb, R.id.admintxtContc, R.id.admintxtContd,
                R.id.admintxtConte, R.id.admintxtContf, R.id.admintxtContg, R.id.admintxtConth,
                R.id.admintxtConti, R.id.admintxtContga));

        for (int dew = 0; dew < txtCintidec.size(); dew++) {
            if (txtCintidec.get(dew).toString().equals(idec.toString())) {
                findViewById(idec).setBackgroundColor(getResources().getColor(R.color.orangelight));

            } else {
                findViewById(txtCintidec.get(dew)).setBackgroundColor(getResources().getColor(R.color.trans));

            }
        }

        admcloseDraw();

        /* Side navigation */
        if (idec == R.id.admintxtCont) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_str));
            admtabManage.setText("adminSrecod");
        } else if (idec == R.id.admintxtConta) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_stra));
            admtabManage.setText("adminSrecoda");
        } else if (idec == R.id.admintxtContb) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_strb));
            admtabManage.setText("adminSrecodb");
        } else if (idec == R.id.admintxtContc) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_strc));
            admtabManage.setText("adminSrecodc");
        } else if (idec == R.id.admintxtContd) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_strd));
            admtabManage.setText("adminSrecodd");
        } else if (idec == R.id.admintxtContf) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_strf));
            admtabManage.setText("adminSrecodf");
        } else if (idec == R.id.admintxtConte) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_stre));
            admtabManage.setText("adminSrecode");
        } else if (idec == R.id.admintxtConth) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_strh));
            admtabManage.setText("adminSrecodh");
        } else if (idec == R.id.admintxtContg) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_strg));
            admtabManage.setText("adminSrecodg");
        } else if (idec == R.id.admintxtContga) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.ordestr));
            admtabManage.setText("adminSrecodga");
        } else if (idec == R.id.admintxtConti) {
            ((TextView) findViewById(R.id.admintitle_text)).setText(this.getResources().getString(R.string.admin_stri));
            admtabManage.setText("adminSrecodi");
        }

        if (idec == R.id.admintxtConta) {
            admprogbar.setBackgroundColor(getResources().getColor(R.color.bluu));

        } else {
            admprogbar.setBackgroundColor(getResources().getColor(R.color.globbackground));

        }


        /*  Load admin summary */
        prdLodadmin();
    }
}