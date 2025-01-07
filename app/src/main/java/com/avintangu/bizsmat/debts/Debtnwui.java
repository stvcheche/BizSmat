package com.avintangu.bizsmat.debts;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.resources.Resocs;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Debtnwui {
    DecimalFormat formatter = new DecimalFormat("#,###");
    private static Context datnwuiContext;
    Resocs resources;
    Dbcluzz dbcluzz;
    Debtsresoc debtsresoc;
    Debtsresoca debtsresoca;

    public Debtnwui(Context context) {
        datnwuiContext = context;

        resources = new Resocs(datnwuiContext);
        dbcluzz = new Dbcluzz(datnwuiContext);
        debtsresoc = new Debtsresoc(datnwuiContext);
        debtsresoca = new Debtsresoca(datnwuiContext);
    }

    /* New debt UI */
    public void nwDebtctuiload(ScrollView scrollView, ArrayList<Integer> idecs, double eunica, ProgressBar progbar,
                               String detocs, ArrayList<ArrayList<String>> usrdetails, ArrayList<String> usrdetailsa,
                               LinearLayout debtnwedhedcnt) {
        String param = "nada";
        progbar.setIndeterminate(true);

        new Debtnwui.debtRecodsync(scrollView, idecs, eunica, progbar, detocs, usrdetails, usrdetailsa, debtnwedhedcnt).execute(param);
    }

    public class debtRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scrollViewpr;
        ArrayList<Integer> idecspr;
        double eunicapr;
        ProgressBar progbarpr;
        String detocspr;
        View usrLinear;
        ArrayList<ArrayList<String>> usrdetailspr;
        ArrayList<String> usrdetailsapr;
        LinearLayout debtnwedhedcntpr;
        FlexboxLayout debtnwhedflex;

        public debtRecodsync(ScrollView scrollView, ArrayList<Integer> idecs, final double eunica, ProgressBar progbar,
                             String detocs, ArrayList<ArrayList<String>> usrdetails, ArrayList<String> usrdetailsa,
                             LinearLayout debtnwedhedcnt) {
            this.scrollViewpr = scrollView;
            this.idecspr = idecs;
            this.eunicapr = eunica;
            this.progbarpr = progbar;
            this.detocspr = detocs;
            this.usrdetailspr = usrdetails;
            this.usrdetailsapr = usrdetailsa;
            this.debtnwedhedcntpr = debtnwedhedcnt;

            if (detocspr.equals("newCustomer")) {
                LayoutInflater inflater = (LayoutInflater) datnwuiContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.debtnwedrecod, null);

            } else if (detocspr.equals("ediDatsum")) {
                LayoutInflater inflater = (LayoutInflater) datnwuiContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.debtnwedrecoda, null);

            } else if (detocspr.equals("newPayment") || detocspr.equals("editPayment")) {
                LayoutInflater inflater = (LayoutInflater) datnwuiContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.debtnwedrecodb, null);
            }

        }

        @Override
        protected String doInBackground(String... param) {

            String uniqIdec = resources.uniqueGen().toString();

            /* New customer */
            if (detocspr.equals("newCustomer")) {
                /* Initialize arraylists */
                ArrayList<Integer> txvewidec = new ArrayList<>(Arrays.asList(R.id.debtnwtxt, R.id.debtnwtxta, R.id.debtnwtxt));
                ArrayList<Integer> edtxtidec = new ArrayList<>(Arrays.asList(R.id.debtnwedtxt, R.id.debtnwedtxta));
                ArrayList<Integer> btnidec = new ArrayList<>(Arrays.asList(R.id.debtnewbtn, R.id.debtnewbtna));

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

                /*  Set date */
                usrLinear.findViewById(R.id.debtnewbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resources.freexam(((Button) usrLinear.findViewById(R.id.debtnewbtn)));

                    }
                });

                /* New customer code */
                String usrcode = dbcluzz.getUsrdetal().get(3).replaceAll("[^a-zA-Z0-9]", "");
                StringBuilder sb = new StringBuilder();
                sb.append(usrcode + "CUST" + uniqIdec + "CODE");
                String prdctcode = String.valueOf(sb);

                uniqIdec = prdctcode;

                /*  Customer debts summary */
            } else if (detocspr.equals("ediDatsum")) {
                Integer colnumpr = 4;
                ArrayList<String> Titles = new ArrayList<>(Arrays.asList("Product", "Price", "Quantity", "Total"));
                ArrayList<Float> Widoth = new ArrayList<>(Arrays.asList(1f, 0.4f, 0.2f, 0.2f, 0.2f));
                ArrayList<Integer> Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.LEFT, Gravity.CENTER,
                        Gravity.CENTER, Gravity.CENTER));

                /* Table container */
                LinearLayout linearparent = new LinearLayout(datnwuiContext);
                linearparent.setPadding(0, 0, 0, 160);
                linearparent.setId(R.id.prdctconstprnt);
                linearparent.removeAllViews();
                linearparent.setOrientation(LinearLayout.VERTICAL);

                /* Headers */
                debtnwhedflex = new FlexboxLayout(datnwuiContext);
                debtnwhedflex.setDividerDrawable(datnwuiContext.getResources().getDrawable(R.drawable.flexcontdivider));
                debtnwhedflex.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
                debtnwhedflex.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
                debtnwhedflex.setBackgroundColor(datnwuiContext.getResources().getColor(R.color.bluua));

                for (int ghas = 0; ghas < colnumpr; ghas++) {
                    TextView header = new TextView(datnwuiContext);
                    header.setGravity(Gravity.CENTER);
                    header.setTextSize((float) eunicapr + 6.0f);
                    header.setText(Titles.get(ghas));
                    header.setTextColor(Color.rgb(255, 255, 255));
                    header.setPadding(0, 10, 0, 10);

                    FlexboxLayout.LayoutParams hedlayoutParams = new FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.MATCH_PARENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT);

                    hedlayoutParams.setFlexBasisPercent(Widoth.get(ghas + 1));
                    hedlayoutParams.setFlexGrow(1.0f);
                    debtnwhedflex.addView(header, hedlayoutParams);
                }

                /* Data children */
                ArrayList<Integer> idecs = new ArrayList<>();
                ArrayList<Integer> idecsa = new ArrayList<>();

                if (usrdetailspr.get(0).size() > 0) {
                    for (int bin = 0; bin < usrdetailspr.get(0).size(); bin++) {
                        idecsa.add(Math.abs(resources.generateViewId()));

                        FlexboxLayout usrFlexbox = new FlexboxLayout(datnwuiContext);
                        usrFlexbox.setFlexDirection(FlexDirection.ROW);
                        usrFlexbox.setAlignContent(AlignContent.FLEX_START);
                        usrFlexbox.setAlignItems(AlignItems.BASELINE);
                        usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                        usrFlexbox.setPadding(0, 0, 0, 0);
                        usrFlexbox.setId(idecsa.get(bin));
                        usrFlexbox.setBackgroundResource(R.drawable.listflexcont);

                        /*  Set date row */
                        if (bin < 1) {
                            dateRows(usrdetailspr.get(0).get(bin), eunicapr, linearparent);
                        } else if (bin > 0) {
                            if (!usrdetailspr.get(0).get(bin).equals(usrdetailspr.get(0).get(bin - 1))) {
                                dateRows(usrdetailspr.get(0).get(bin), eunicapr, linearparent);
                            }
                        }

                        for (int bina = 1; bina < colnumpr + 1; bina++) {

                            /*  Date row */
                            FlexboxLayout dateFlexbox = new FlexboxLayout(datnwuiContext);
                            dateFlexbox.setFlexDirection(FlexDirection.ROW);
                            dateFlexbox.setAlignContent(AlignContent.FLEX_START);
                            dateFlexbox.setAlignItems(AlignItems.BASELINE);
                            dateFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                            dateFlexbox.setPadding(0, 0, 0, 0);
                            dateFlexbox.setBackgroundResource(R.drawable.listflexcont);

                            /*  Data views */
                            idecs.add(Math.abs(resources.generateViewId()));

                            TextView listBtn = new TextView(datnwuiContext);
                            listBtn.setText(usrdetailspr.get(bina).get(bin));
                            listBtn.setId(idecs.get(bin));
                            listBtn.setAllCaps(false);
                            listBtn.setGravity(Gravety.get(bina));
                            listBtn.setTextSize((float) eunicapr + 5.0f);
                            listBtn.setBackgroundResource(R.drawable.txtvewlistcomb);
                            listBtn.setPadding(16, 36, 10, 10);
                            listBtn.setTextColor(Color.rgb(77, 77, 77));

                            FlexboxLayout.LayoutParams lfayoutParams = new FlexboxLayout.LayoutParams(
                                    FlexboxLayout.LayoutParams.MATCH_PARENT,
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT);
                            lfayoutParams.setFlexBasisPercent(Widoth.get(bina));
                            lfayoutParams.setFlexGrow(1.0f);
                            usrFlexbox.addView(listBtn, lfayoutParams);

                            if (bina == 1) {
                                final String prdct = usrdetailspr.get(5).get(bin);

                                listBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        /*   Edit single debt input */
                                        debtsresoca.recdEditdilog("editDebtrec", prdct);

                                    }
                                });

                            }
                        }

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, 0);
                        linearparent.addView(usrFlexbox, layoutParams);
                    }
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);

                LinearLayout prntCont = usrLinear.findViewById(R.id.debtrcdsummary);
                prntCont.removeAllViews();
                prntCont.addView(linearparent, layoutParams);

                /* Create new and edit Payments */
            } else if (detocspr.equals("newPayment") || detocspr.equals("editPayment")) {
                /* Initialize arraylists */
                ArrayList<Integer> txvewidec;
                ArrayList<Integer> edtxtidec;
                ArrayList<Integer> btnidec;

                txvewidec = new ArrayList<>(Arrays.asList(R.id.debtanwtxt, R.id.debtanwtxta, R.id.debtanwtxtb, R.id.debtanwtxtc));
                edtxtidec = new ArrayList<>(Arrays.asList(R.id.debtanwedtxt));
                btnidec = new ArrayList<>(Arrays.asList(R.id.debtanewbtn, R.id.debtanewbtna));


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

                /*  Set date */
                usrLinear.findViewById(R.id.debtanewbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resources.freexam(((Button) usrLinear.findViewById(R.id.debtanewbtn)));

                    }
                });

                /* Delete payment */
                usrLinear.findViewById(R.id.debtanewbtna).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resources.recdDeltdilog(detocspr, Debts.datStatus.getText().toString(),
                                ((TextView) usrLinear.findViewById(R.id.debtnewuniqida)).getText().toString());

                    }
                });

                /*  Customer name selection */
                resources.spinMod(((TextView) usrLinear.findViewById(R.id.debtanwtxtb)), eunicapr, resources.getallCustos(), "");

                /* Different text view */
                if (detocspr.equals("newPayment")) {
                    usrLinear.findViewById(R.id.debtanewbtna).setVisibility(View.GONE);

                    /* New payment code */
                    String usrcode = dbcluzz.getUsrdetal().get(3).replaceAll("[^a-zA-Z0-9]", "");
                    StringBuilder sb = new StringBuilder();
                    sb.append(usrcode + "PYMNT" + uniqIdec + "CODE");
                    String prdctcode = String.valueOf(sb);

                    uniqIdec = prdctcode;

                }

                /* Set data */
                if (detocspr.equals("editPayment")) {
                    usrLinear.findViewById(R.id.debtanewbtna).setVisibility(View.VISIBLE);

                    ((Button) usrLinear.findViewById(R.id.debtanewbtn)).setText(usrdetailsapr.get(0));
                    ((TextView) usrLinear.findViewById(R.id.debtanwtxtb)).setText(usrdetailsapr.get(1));
                    ((EditText) usrLinear.findViewById(R.id.debtanwedtxt)).setText(usrdetailsapr.get(2));

                    if (Debts.datStatus.getText().toString().equals("Active")) {
                        ((Button) usrLinear.findViewById(R.id.debtanewbtna)).setText("Delete");

                    } else if (Debts.datStatus.getText().toString().equals("Inactive")) {
                        ((Button) usrLinear.findViewById(R.id.debtanewbtna)).setText("Recover");

                    }

                    uniqIdec = usrdetailsapr.get(3);

                }

                ((TextView) usrLinear.findViewById(R.id.debtnewuniqida)).setText(uniqIdec);

            }


            String response = "Cool";

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progbarpr.setIndeterminate(false);

            /* Add header */
            debtnwedhedcntpr.removeAllViews();

            if (detocspr.equals("ediDatsum")) {
                debtnwedhedcntpr.addView(debtnwhedflex, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }

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

    /* Add the date rows */
    public void dateRows(String dtarow, double eunicapr, LinearLayout usrLinear) {
        FlexboxLayout usrFlexbox = new FlexboxLayout(datnwuiContext);
        usrFlexbox.setFlexDirection(FlexDirection.ROW);
        usrFlexbox.setAlignContent(AlignContent.FLEX_START);
        usrFlexbox.setAlignItems(AlignItems.BASELINE);
        usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
        usrFlexbox.setPadding(0, 0, 0, 0);
        usrFlexbox.setBackgroundResource(R.drawable.listflexconta);

        TextView listBtn = new TextView(datnwuiContext);
        listBtn.setText(dtarow);
        listBtn.setAllCaps(false);
        listBtn.setGravity(Gravity.LEFT);
        listBtn.setTextSize((float) eunicapr + 3.0f);
        listBtn.setBackgroundResource(R.color.trans);
        listBtn.setPadding(16, 3, 10, 3);
        listBtn.setTextColor(Color.rgb(50, 50, 50));

        FlexboxLayout.LayoutParams lfayoutParams = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.MATCH_PARENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT);
        lfayoutParams.setFlexBasisPercent(1f);
        lfayoutParams.setFlexGrow(1f);
        usrFlexbox.addView(listBtn, lfayoutParams);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        usrLinear.addView(usrFlexbox, layoutParams);

    }
}
