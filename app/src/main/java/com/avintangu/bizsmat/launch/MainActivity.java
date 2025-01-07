package com.avintangu.bizsmat.launch;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.app.ActivityCompat;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.admin.Admin;
import com.avintangu.bizsmat.admin.Adminresocs;
import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.debts.Debts;
import com.avintangu.bizsmat.inventory.Inventory;
import com.avintangu.bizsmat.orders.Ordersresoc;
import com.avintangu.bizsmat.products.Products;
import com.avintangu.bizsmat.resources.Resocs;
import com.avintangu.bizsmat.sales.Sales;
import com.google.android.flexbox.FlexboxLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static AppCompatActivity duks;
    public static String detocs;
    Mainresoc mainresoc;
    Dbcluzz dbcluzz;
    Resocs resocs;
    Adminresocs adminresocs;
    Ordersresoc ordersresoc;

    double eunica;
    int check;
    public ArrayList<Float> srcsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainresoc = new Mainresoc(this);
        dbcluzz = new Dbcluzz(this);
        resocs = new Resocs(this);
        adminresocs = new Adminresocs(this);
        ordersresoc = new Ordersresoc(this);
        eunica = resocs.getFontex(getWindowManager().getDefaultDisplay(), new Point());
        checkPermissions();

        MainActivity.duks = this;
        check = 0;

        /* User exist test */
        ArrayList<String> usrdetal = dbcluzz.getUsrdetal();

        /* Switch layouts */
        if (usrdetal.isEmpty()) {
            /* Set language */
            dbcluzz.insertUsrprefs("en");
            resocs.stLanguage();getSupportActionBar().hide();

            detocs = "usr_assign";

            setContentView(R.layout.usr_assign);
            getSupportActionBar().hide();
            int[] btnidec = {R.id.btnassn};

            Button[] Btnarry = new Button[btnidec.length];
            for (int j = 0; j < btnidec.length; ++j) {
                (Btnarry[j] = findViewById(btnidec[j])).setTextSize((float) eunica + 4.5f);

            }

            /*Set display font sizes*/
            int[] edtxtidec = {R.id.edtxtasn, R.id.edtxtasna, R.id.edtxtasnb, R.id.edtxtasnc, R.id.edtxtasnd};

            EditText[] Edtxtarry = new EditText[edtxtidec.length];
            for (int k = 0; k < edtxtidec.length; ++k) {
                (Edtxtarry[k] = findViewById(edtxtidec[k])).setTextSize((float) eunica + 4.5f);
            }

            /* Spinner */
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinresoc, resocs.retuxLang()) {
                public View getDropDownView(int v, View view, ViewGroup viewGroup) {
                    TextView textView = (TextView) super.getDropDownView(v, view, viewGroup);
                    textView.setTextSize((float) eunica + 5.0f);
                    return textView;
                }

                public View getView(int v, View view, ViewGroup viewGroup) {
                    TextView textView = (TextView) super.getView(v, view, viewGroup);
                    textView.setTextSize((float) eunica + 5.0f);
                    return textView;
                }
            };

            ((Spinner) findViewById(R.id.spinassgnusr)).setAdapter(adapter);

            /* Set up constraints */
            float[] percy = {0.05f, 0.90f};
            schGuides(percy);

            /* Set on actions listener */
            findViewById(R.id.btnassn).setOnClickListener(this);
            findViewById(R.id.usrtype).setOnClickListener(this);
            ((Spinner) findViewById(R.id.spinassgnusr)).setOnItemSelectedListener(this);
            getWindow().setSoftInputMode(3);


        } else {
            String switocs = "";

            if (usrdetal.get(5).equals(this.getResources().getString(R.string.defcreola))) {
                switocs = "pass";
            } else {
                switocs = resocs.ckUsagedayz();

            }

            if (switocs.equals("pass")) {
                /* Set language */
                resocs.stLanguage();
                detocs = "activity_main";

                setContentView(R.layout.activity_main);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.custom_ttlemain);
                //((TextView) findViewById(R.id.title_text)).setTextSize((float) (eunica + 10.0));
                ((TextView) findViewById(R.id.activshop)).setTextSize((float) (eunica + 7.0));
                ((TextView) findViewById(R.id.activshop)).setText(mainresoc.gtActiveshop());
                resocs.spinMod(((TextView) findViewById(R.id.activshop)), eunica, mainresoc.gtAshops(), "shoppa");
                stHsrcImg();

                /* Set display font sizes */
                int[] txtisec = {R.id.texoca, R.id.texocb, R.id.texocc, R.id.texocd, R.id.texoce};

                TextView[] Txtxtarry = new TextView[txtisec.length];
                for (int k = 0; k < txtisec.length; ++k) {
                    (Txtxtarry[k] = findViewById(txtisec[k])).setTextSize((float) eunica + 3.7f);
                }

                /* Scale picture */
                setHomepic();

                findViewById(R.id.btnhmsrc).setOnClickListener(this);
                findViewById(R.id.btnhmsrca).setOnClickListener(this);
                findViewById(R.id.btnhmsrcb).setOnClickListener(this);
                findViewById(R.id.btnhmsrcc).setOnClickListener(this);
                findViewById(R.id.btnhmsrcd).setOnClickListener(this);
                findViewById(R.id.btnhmsrce).setOnClickListener(this);
            } else {
                /* Set language */
                resocs.stLanguage();
                detocs = "permupdate";

                setContentView(R.layout.permupdate);
                getSupportActionBar().hide();

                /* Set display font sizes */
                int[] btntxt = {R.id.permupbtn, R.id.permupbtna};

                Button[] Btntxtarry = new Button[btntxt.length];
                for (int k = 0; k < btntxt.length; ++k) {
                    (Btntxtarry[k] = findViewById(btntxt[k])).setTextSize((float) eunica + 6f);
                }

                /* Set up constraints */
                float[] percy = {0.1f, 0.45f, 0.55f, 0.65f, 0.75f};
                schGuidesa(percy);

                findViewById(R.id.permupbtn).setOnClickListener(this);
                findViewById(R.id.permupbtna).setOnClickListener(this);

            }

        }
    }

    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.usrtype) {
            String usrtype = ((Button) findViewById(R.id.usrtype)).getText().toString();

            if (usrtype.equals(this.getResources().getText(R.string.assign_usrf))) {
                ((Button) findViewById(R.id.usrtype)).setText(this.getResources().getText(R.string.assign_usrg));

            } else if (usrtype.equals(this.getResources().getText(R.string.assign_usrg))) {
                ((Button) findViewById(R.id.usrtype)).setText(this.getResources().getText(R.string.assign_usrf));

            }
        } else if (id == R.id.btnassn) {
            EditText shopname = findViewById(R.id.edtxtasn);
            EditText fullnames = findViewById(R.id.edtxtasna);
            EditText password = findViewById(R.id.edtxtasnb);
            EditText phonenum = findViewById(R.id.edtxtasnc);
            EditText acticode = findViewById(R.id.edtxtasnd);
            Button usrtype = findViewById(R.id.usrtype);

            ArrayList<String> detals = new ArrayList<>();
            detals.add(shopname.getText().toString());
            detals.add(fullnames.getText().toString());
            detals.add(password.getText().toString());
            detals.add(phonenum.getText().toString().replaceAll("\\s+", ""));
            detals.add(acticode.getText().toString().replaceAll("\\s+", ""));
            detals.add(usrtype.getText().toString());

            ArrayList<String> interim = new ArrayList<>();
            ArrayList<String> prompt = new ArrayList<>(Arrays.asList("your shop name",
                    "your full names", "your e-mail", "your password", "your phone number",
                    "the activation code"));

            for (int dec = 0; dec < detals.size(); dec++) {
                if (detals.get(dec).equals("")) {
                    interim.add(prompt.get(dec));
                }
            }

            if (interim.size() > 0) {
                String prmpt = resocs.Implodes(interim);
                Toast.makeText(this.getApplicationContext(), "Enter:  " + prmpt, Toast.LENGTH_LONG).show();

            } else {
                String zeterm = "assignuser";
                dbcluzz.assignUser(detals, zeterm);

            }

            /* Back-up data */
        } else if (id == R.id.permupbtn) {
            adminresocs.bckData("bckdata");

            /* Activate app */
        } else if (id == R.id.permupbtna) {
            adminresocs.activateApp(dbcluzz.getUsrdetal(), "mainActivate");
        } else if (id == R.id.btnhmsrc) {
            Intent intent = new Intent(this.getApplicationContext(), Products.class);

            Bundle bundle = new Bundle();
            bundle.putString("determtabmng", "prdSrecod");
            bundle.putString("determstate", "Active");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.btnhmsrca) {
            Intent intent = new Intent(this.getApplicationContext(), Inventory.class);

            Bundle bundle = new Bundle();
            bundle.putString("determtabmng", "inventSrecod");
            bundle.putString("determstate", "Active");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.btnhmsrcb) {
            Intent intent = new Intent(this.getApplicationContext(), Sales.class);

            Bundle bundle = new Bundle();
            bundle.putString("determtabmng", "salSrecod");
            bundle.putString("determstate", "Active");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.btnhmsrcc) {
            Intent intent = new Intent(this.getApplicationContext(), Debts.class);

            Bundle bundle = new Bundle();
            bundle.putString("determtabmng", "debtSrecod");
            bundle.putString("determstate", "Active");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.btnhmsrcd) {
            if (dbcluzz.getUsrdetal().get(5).equals(this.getResources().getString(R.string.defcreola))) {

                /* Refresh price-list with distributors list */
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

                ordersresoc.gtDistprcc(objdetalz, "loadActive");

            } else {
                Toast.makeText(this, "This module cannot be accessed using the trial version", Toast.LENGTH_LONG).show();

            }
        } else if (id == R.id.btnhmsrce) {
            Intent intent = new Intent(this.getApplicationContext(), Admin.class);

            Bundle bundle = new Bundle();
            bundle.putString("determtabmng", "adminSrecod");
            bundle.putString("determstate", "Active");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    /*Check and assign permissions */
    public boolean checkPermissions() {
        boolean resp = false;

        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String[] perms = {
                        Manifest.permission.VIBRATE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET};

                ActivityCompat.requestPermissions(this, perms, 7);
            }

            resp = true;

        } catch (Exception ex) {

        }

        return resp;

    }

    /* User assign set guidelines */
    public void schGuides(float[] percy) {
        int[] guideidec = {R.id.usasstopcons, R.id.assusbottomconst};

        Guideline[] Guideidec = new Guideline[guideidec.length];

        for (int recs = 0; recs < guideidec.length; recs++) {
            (Guideidec[recs] = findViewById(guideidec[recs])).setGuidelinePercent(percy[recs]);

        }
    }

    public void schGuidesa(float[] percy) {
        int[] guideidec = {R.id.usasstopcons, R.id.assusbottomconst, R.id.assusbottomconsta};

        Guideline[] Guideidec = new Guideline[guideidec.length];

        for (int recs = 0; recs < guideidec.length; recs++) {
            (Guideidec[recs] = findViewById(guideidec[recs])).setGuidelinePercent(percy[recs]);

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (++check > 1) {
            if (adapterView.getId() == R.id.spinassgnusr) {
                String langstr = "en";

                String langslct = ((Spinner) findViewById(R.id.spinassgnusr)).getSelectedItem().toString();

                if (langslct.equals("English")) {
                    langstr = "en";

                } else if (langslct.equals("Kiswahili")) {
                    langstr = "sw";

                }

                resocs.ckLanguage(langstr, new Intent(getApplicationContext(), MainActivity.class));
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /* Set home screen icons dimensions */
    public void stHsrcImg() {
        srcsize = resocs.getSrcsize(getWindowManager().getDefaultDisplay(), new Point());

        float constsrcWidth = 360;
        float constimgWidth = 27;

        if (this.getResources().getConfiguration().orientation == 1) {
            constimgWidth = 25;

        } else if (this.getResources().getConfiguration().orientation == 2) {
            constimgWidth = 12;

        }


        float newimgWidth = constimgWidth / 100f;


        int[] fleconteidec = {R.id.flecshmcont, R.id.flecshmconta, R.id.flecshmcontb, R.id.flecshmcontc,
                R.id.flecshmcontd, R.id.flecshmconte};

        FlexboxLayout[] Flecsidec = new FlexboxLayout[fleconteidec.length];

        for (int recs = 0; recs < fleconteidec.length; recs++) {

            FlexboxLayout.LayoutParams layoutParams =
                    new FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setFlexBasisPercent(newimgWidth);
            (Flecsidec[recs] = findViewById(fleconteidec[recs])).setLayoutParams(layoutParams);

        }

    }

    /* Set home screen picture */
    public void setHomepic() {
        srcsize = resocs.getSrcsize(getWindowManager().getDefaultDisplay(), new Point());

        Guideline guideline = findViewById(R.id.mainactivoguida);
        Float constArea = 360f * 672f;
        Float actArea = srcsize.get(0) * srcsize.get(1);

        Float guidelvl = (actArea / constArea) * (50f / 100f);

        if (srcsize.get(0) > 360) {
            if (this.getResources().getConfiguration().orientation == 1) {
                if (guidelvl >= 0.33) {
                    guidelvl = 0.50f;

                }
            } else if (this.getResources().getConfiguration().orientation == 2) {
                if (guidelvl >= 0.23) {
                    guidelvl = 0.23f;

                }
            }
        }

        guideline.setGuidelinePercent(guidelvl);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (detocs.equals("usr_assign")) {
                float[] percy = {0.1f, 0.90f};
                schGuides(percy);

            } else if (detocs.equals("activity_main")) {
                getSupportActionBar().hide();
                stHsrcImg();

            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (detocs.equals("usr_assign")) {
                float[] percy = {0.05f, 0.90f};
                schGuides(percy);

            } else if (detocs.equals("activity_main")) {
                getSupportActionBar().show();
                stHsrcImg();

            }

        }

        setHomepic();
    }

    @Override
    public void onResume() {
        super.onResume();
        resocs = new Resocs(this);

        if (dbcluzz.getUsrdetal().size() > 0) {
            if (!dbcluzz.getUsrdetal().get(5).equals(this.getResources().getString(R.string.defcreola))) {
                String switocs = resocs.ckUsagedayz();

                if (switocs.equals("nanpass")) {
                    /* Set language */
                    resocs.stLanguage();
                    detocs = "permupdate";

                    setContentView(R.layout.permupdate);
                    getSupportActionBar().hide();

                    /* Set up constraints */
                    float[] percy = {0.1f, 0.45f, 0.55f, 0.65f, 0.75f};
                    schGuidesa(percy);

                    /* Set up action listeners */
                    findViewById(R.id.permupbtn).setOnClickListener(this);
                    findViewById(R.id.permupbtna).setOnClickListener(this);

                } else {
                    ((TextView) findViewById(R.id.activshop)).setText(mainresoc.gtActiveshop());
                    resocs.spinMod(((TextView) findViewById(R.id.activshop)), eunica, mainresoc.gtAshops(), "shoppa");
                }
            }
        }
    }
}