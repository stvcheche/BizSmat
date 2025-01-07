package com.avintangu.bizsmat.products;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.resources.Resocs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Prdctnewui {
    private static Context prdnewContext;
    Resocs resources;
    Prdctresocs prdctresocs;
    Dbcluzz dbcluzz;
    DecimalFormat formatter = new DecimalFormat("#,###");

    public Prdctnewui(Context context) {
        prdnewContext = context;

        resources = new Resocs(prdnewContext);
        prdctresocs = new Prdctresocs(prdnewContext);
        dbcluzz = new Dbcluzz(prdnewContext);

    }

    /* New products UI */
    public void nwPrdctuiload(ScrollView scrollView, ArrayList<Integer> idecs, double eunica, ProgressBar progbar,
                              String detocs, ArrayList<String> usrdetails) {
        String param = "nada";
        progbar.setIndeterminate(true);

        new Prdctnewui.prdRecodsync(scrollView, idecs, eunica, progbar, detocs, usrdetails).execute(param);
    }

    public class prdRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scrollViewpr;
        ArrayList<Integer> idecspr;
        double eunicapr;
        ProgressBar progbarpr;
        String detocspr;
        View usrLinear;
        ArrayList<String> usrdetailspr;

        public prdRecodsync(ScrollView scrollView, ArrayList<Integer> idecs, final double eunica, ProgressBar progbar,
                            String detocs, ArrayList<String> usrdetails) {
            this.scrollViewpr = scrollView;
            this.idecspr = idecs;
            this.eunicapr = eunica;
            this.progbarpr = progbar;
            this.detocspr = detocs;
            this.usrdetailspr = usrdetails;

            if (detocspr.equals("newProduct") || detocspr.equals("editProduct")) {
                LayoutInflater inflater = (LayoutInflater) prdnewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.prdnwedrecod, null);

            } else if (detocspr.equals("newCategory") || detocspr.equals("editCategory")) {
                LayoutInflater inflater = (LayoutInflater) prdnewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.prdnwedrecoda, null);
            }

        }

        @Override
        protected String doInBackground(String... param) {

            String uniqIdec = resources.uniqueGen().toString();

            /* New and edit Products */
            if (detocspr.equals("newProduct") || detocspr.equals("editProduct")) {
                /* Initialize arraylists */
                ArrayList<Integer> txvewidec;
                ArrayList<Integer> edtxtidec;
                ArrayList<Integer> btnidec;

                txvewidec = new ArrayList<>(Arrays.asList(R.id.prdnwtxt, R.id.prdnwtxta, R.id.prdnwtxtb,
                        R.id.prdnwtxtd, R.id.prdnwtxte, R.id.prdnwtxtspin, R.id.prdnwtxtf));
                edtxtidec = new ArrayList<>(Arrays.asList(R.id.prdnwedtxt, R.id.prdnwedtxtb,
                        R.id.prdnwedtxtc, R.id.prdnwedtxtd));
                btnidec = new ArrayList<>(Arrays.asList(R.id.prdnewbtn, R.id.prdnewbtna));

                /*Set textviews font sizes*/
                TextView[] Txvewarry = new TextView[txvewidec.size()];
                for (int k = 0; k < txvewidec.size(); ++k) {
                    (Txvewarry[k] = usrLinear.findViewById(txvewidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                }

                /*Set Editext font sizes*/
                EditText[] Edtxtarry = new EditText[edtxtidec.size()];
                for (int k = 0; k < edtxtidec.size(); ++k) {
                    (Edtxtarry[k] = usrLinear.findViewById(edtxtidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                }

                /*Set buttons font sizes*/
                Button[] Btnarry = new Button[btnidec.size()];
                for (int k = 0; k < btnidec.size(); ++k) {
                    (Btnarry[k] = usrLinear.findViewById(btnidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                }

                usrLinear.findViewById(R.id.prdnewbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resources.freexam(((Button) usrLinear.findViewById(R.id.prdnewbtn)));

                    }
                });

                /* Delete product */
                usrLinear.findViewById(R.id.prdnewbtna).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resources.recdDeltdilog(detocspr, Products.prdStatus.getText().toString(),
                                ((TextView) usrLinear.findViewById(R.id.prdctnewuniqid)).getText().toString());

                    }
                });

                /*  Dynamic text view*/
                resources.spinMod(((TextView) usrLinear.findViewById(R.id.prdnwtxtspin)), eunicapr, resources.retuxPrdcatry(), "");

                /* Different text view */
                if (detocspr.equals("newProduct")) {
                    usrLinear.findViewById(R.id.prdnewbtna).setVisibility(View.GONE);
                    usrLinear.findViewById(R.id.prdnwcalcprcc).setVisibility(View.GONE);
                    usrLinear.findViewById(R.id.prdnwcalcprcd).setVisibility(View.GONE);

                    /* New product code */
                    String usrcode = dbcluzz.getUsrdetal().get(3).replaceAll("[^a-zA-Z0-9]", "");
                    StringBuilder sb = new StringBuilder();
                    sb.append(usrcode + "PRD" + uniqIdec + "CODE");
                    String prdctcode = String.valueOf(sb);

                    uniqIdec = prdctcode;

                }

                if (detocspr.equals("editProduct")) {
                    usrLinear.findViewById(R.id.prdnewbtna).setVisibility(View.VISIBLE);

                    ((Button) usrLinear.findViewById(R.id.prdnewbtn)).setText(usrdetailspr.get(0));
                    ((TextView) usrLinear.findViewById(R.id.prdnwtxtspin)).setText(usrdetailspr.get(1));
                    ((EditText) usrLinear.findViewById(R.id.prdnwedtxt)).setText(usrdetailspr.get(2));
                    ((EditText) usrLinear.findViewById(R.id.prdnwedtxtb)).setText(usrdetailspr.get(3));
                    ((EditText) usrLinear.findViewById(R.id.prdnwedtxtc)).setText(usrdetailspr.get(4));
                    ((EditText) usrLinear.findViewById(R.id.prdnwedtxtd)).setText(usrdetailspr.get(5));

                    if (Products.prdStatus.getText().toString().equals("Active")) {
                        ((Button) usrLinear.findViewById(R.id.prdnewbtna)).setText("Delete");

                    } else if (Products.prdStatus.getText().toString().equals("Inactive")) {
                        ((Button) usrLinear.findViewById(R.id.prdnewbtna)).setText("Recover");

                    }

                    uniqIdec = usrdetailspr.get(6);
                }

                ((TextView) usrLinear.findViewById(R.id.prdctnewuniqid)).setText(uniqIdec);

                /* Create new and edit products categories*/
            } else if (detocspr.equals("newCategory") || detocspr.equals("editCategory")) {
                /* Initialize arraylists */
                ArrayList<Integer> txvewidec;
                ArrayList<Integer> edtxtidec;
                ArrayList<Integer> btnidec;

                txvewidec = new ArrayList<>(Arrays.asList(R.id.prdcatnwtxt, R.id.prdcatnwtxta));
                edtxtidec = new ArrayList<>(Arrays.asList(R.id.prdcatnwedtxt, R.id.prdcatnwedtxta));
                btnidec = new ArrayList<>(Arrays.asList(R.id.prdcatdeletbtn));


                /*Set textviews font sizes*/
                TextView[] Txvewarry = new TextView[txvewidec.size()];
                for (int k = 0; k < txvewidec.size(); ++k) {
                    (Txvewarry[k] = usrLinear.findViewById(txvewidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                }

                /*Set Editext font sizes*/
                EditText[] Edtxtarry = new EditText[edtxtidec.size()];
                for (int k = 0; k < edtxtidec.size(); ++k) {
                    (Edtxtarry[k] = usrLinear.findViewById(edtxtidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                }

                /*Set buttons font sizes*/
                Button[] Btnarry = new Button[btnidec.size()];
                for (int k = 0; k < btnidec.size(); ++k) {
                    (Btnarry[k] = usrLinear.findViewById(btnidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                }

                /* Delete expense category */
                usrLinear.findViewById(R.id.prdcatdeletbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resources.recdDeltdilog(detocspr, Products.prdStatus.getText().toString(),
                                ((TextView) usrLinear.findViewById(R.id.prdctcatnewuniqid)).getText().toString());

                    }
                });

                /* Different text view */
                if (detocspr.equals("newCategory")) {
                    usrLinear.findViewById(R.id.prdcatidecont).setVisibility(View.GONE);
                    usrLinear.findViewById(R.id.prdcatdeletbtn).setVisibility(View.GONE);

                    /* New product code */
                    String usrcode = dbcluzz.getUsrdetal().get(3).replaceAll("[^a-zA-Z0-9]", "");
                    StringBuilder sb = new StringBuilder();
                    sb.append(usrcode + "PRDCAT" + uniqIdec + "CODE");
                    String prdctcode = String.valueOf(sb);

                    uniqIdec = prdctcode;

                }

                /* Set data */
                if (detocspr.equals("editCategory")) {
                    ((EditText) usrLinear.findViewById(R.id.prdcatnwedtxt)).setText(usrdetailspr.get(0));
                    ((EditText) usrLinear.findViewById(R.id.prdcatnwedtxta)).setText(usrdetailspr.get(1));

                    if (Products.prdStatus.getText().toString().equals("Active")) {
                        ((Button) usrLinear.findViewById(R.id.prdcatdeletbtn)).setText("Delete");

                    } else if (Products.prdStatus.getText().toString().equals("Inactive")) {
                        ((Button) usrLinear.findViewById(R.id.prdcatdeletbtn)).setText("Recover");

                    }

                    uniqIdec = usrdetailspr.get(2);

                }

                ((TextView) usrLinear.findViewById(R.id.prdctcatnewuniqid)).setText(uniqIdec);

            }


            String response = "Cool";

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progbarpr.setIndeterminate(false);

            /* Add to ancestor */
            scrollViewpr.removeAllViews();
            scrollViewpr.addView(usrLinear, new ScrollView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

        }

        @Override
        protected void onProgressUpdate(Integer... array) {
        }
    }
}

