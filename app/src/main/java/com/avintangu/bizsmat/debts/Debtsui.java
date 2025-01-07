package com.avintangu.bizsmat.debts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Arrays;

public class Debtsui {
    public static Context datuiContext;
    Resocs resources;
    Dbcluzz dbcluzz;
    Debtsresoc debtsresoc;

    public Debtsui(Context context) {
        datuiContext = context;

        resources = new Resocs(datuiContext);
        dbcluzz = new Dbcluzz(datuiContext);
        debtsresoc = new Debtsresoc(datuiContext);

    }

    /* Set title */
    public String setdebtUitrelis(String tredetocs) {
        String title = "";

        switch (tredetocs) {

            case "debtSrecod": {
                title = datuiContext.getResources().getString(R.string.debt_strn);

                break;
            }

            case "debtSrecoda": {
                title = datuiContext.getResources().getString(R.string.debt_strn);

                break;
            }

            default:
                break;
        }

        return title;
    }

    /* Get summary */
    public void debtsbackrecdlod(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                                 String frodta, String todta, ProgressBar progbar, LinearLayout debtshedcont) {
        String param = "nada";
        progbar.setIndeterminate(true);
        scroLayout.scrollTo(0, 0);

        new Debtsui.debtRecodsync(scroLayout, eunica, colnum, mims, steta, frodta, todta, progbar, debtshedcont).execute(param);
    }

    /* Properties backrecd UI */
    public class debtRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scroLayoutpr;
        double eunicapr;
        Integer colnumpr;
        String mimspr;
        String stetapr;
        ProgressBar progbarpr;
        LinearLayout usrLinear;
        String frodtapr;
        String todtapr;
        LinearLayout debtshedcontpr;
        FlexboxLayout debtshedFlexbox;

        public debtRecodsync(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                             String frodta, String todta, ProgressBar progbar, LinearLayout debtshedcont) {
            this.scroLayoutpr = scroLayout;
            this.eunicapr = eunica;
            this.colnumpr = colnum;
            this.mimspr = mims;
            this.stetapr = steta;
            this.progbarpr = progbar;
            this.frodtapr = frodta;
            this.todtapr = todta;
            this.debtshedcontpr = debtshedcont;

        }

        protected String doInBackground(String... param) {
            String response = "nada";

            String dbess = "";
            String stexa = "";
            String srchdeterm = "";

            ArrayList<String> Sres = new ArrayList<>();
            ArrayList<ArrayList<String>> debtVals = new ArrayList<>();
            ArrayList<String> Titles = null;
            ArrayList<Integer> Gravety = null;
            ArrayList<Float> Widoth = null;

            switch (mimspr) {

                case "debtSrecod": {
                    Sres.add("custostblname");
                    Sres.add("custostblphone");
                    Sres.add("datesumes");
                    Sres.add("custostblrecod");

                    Titles = new ArrayList<>(Arrays.asList("Customer", "Phone No.", "Debt"));

                    Widoth = new ArrayList<>(Arrays.asList(0.4f, 0.3f, 0.3f));
                    Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER, Gravity.CENTER));

                    debtVals = debtsresoc.gtDatsummary(Sres, srchdeterm, stetapr);

                    break;
                }

                case "debtSrecoda": {
                    dbess = "custodatpay";
                    stexa = "custodatpaystate";

                    Sres.add("custostblname");
                    Sres.add("custodatpaydate");
                    Sres.add("custodatpayamt");
                    Sres.add("custodatpayrecod");

                    Titles = new ArrayList<>(Arrays.asList("Customer", "Date", "Amount paid"));

                    Widoth = new ArrayList<>(Arrays.asList(0.4f, 0.3f, 0.3f, 0.3f));
                    Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.LEFT, Gravity.CENTER));

                    debtVals = debtsresoc.gtAllpymnts(dbess, stexa, stetapr, Sres, srchdeterm, frodtapr, todtapr);

                    break;
                }

                default:
                    break;
            }

            Integer ghasdos = Sres.size() - 1;

            if (colnumpr > ghasdos) {
                colnumpr = ghasdos;
            }


            /* Table container */
            usrLinear = new LinearLayout(datuiContext);
            usrLinear.setPadding(0, 0, 0, 160);
            usrLinear.setId(R.id.prdctconstprnt);
            usrLinear.removeAllViews();
            usrLinear.setOrientation(LinearLayout.VERTICAL);

            /* Headers */
            debtshedFlexbox = new FlexboxLayout(datuiContext);
            debtshedFlexbox.setDividerDrawable(datuiContext.getResources().getDrawable(R.drawable.flexcontdivider));
            debtshedFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
            debtshedFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
            debtshedFlexbox.setBackgroundColor(datuiContext.getResources().getColor(R.color.bluu));

            for (int ghas = 0; ghas < colnumpr; ghas++) {
                TextView header = new TextView(datuiContext);
                header.setGravity(Gravity.CENTER);
                header.setTextSize((float) eunicapr + 6.0f);
                header.setText(Titles.get(ghas));
                header.setTextColor(Color.rgb(255, 255, 255));
                header.setPadding(5, 10, 5, 10);

                FlexboxLayout.LayoutParams hedlayoutParams = new FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.MATCH_PARENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT);

                hedlayoutParams.setFlexBasisPercent(Widoth.get(ghas));
                hedlayoutParams.setFlexGrow(1.0f);
                debtshedFlexbox.addView(header, hedlayoutParams);
            }


            /* Data children */
            ArrayList<Integer> idecs = new ArrayList<>();
            ArrayList<Integer> idecsa = new ArrayList<>();

            if (debtVals.get(0).size() > 0) {
                for (int bin = 0; bin < debtVals.get(0).size(); bin++) {
                    idecsa.add(Math.abs(resources.generateViewId()));

                    FlexboxLayout usrFlexbox = new FlexboxLayout(datuiContext);
                    usrFlexbox.setFlexDirection(FlexDirection.ROW);
                    usrFlexbox.setAlignContent(AlignContent.FLEX_START);
                    usrFlexbox.setAlignItems(AlignItems.BASELINE);
                    usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                    usrFlexbox.setPadding(0, 0, 0, 0);
                    usrFlexbox.setId(idecsa.get(bin));
                    usrFlexbox.setBackgroundResource(R.drawable.listflexcont);


                    for (int bina = 0; bina < colnumpr; bina++) {
                        idecs.add(Math.abs(resources.generateViewId()));

                        TextView listBtn = new TextView(datuiContext);
                        listBtn.setText(debtVals.get(bina).get(bin));
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

                        if (bina == 0) {
                            String sweol = "";
                            String pruniqidec = "";
                            String custos = "";

                            if (mimspr.equals("debtSrecod")) {
                                sweol = "ediDatsum";
                                pruniqidec = debtVals.get(3).get(bin);
                                custos = debtVals.get(0).get(bin);

                            } else if (mimspr.equals("debtSrecoda")) {
                                sweol = "editPayment";
                                pruniqidec = debtVals.get(3).get(bin);

                            }

                            final String finalSweol = sweol;
                            final String uniqidec = pruniqidec;
                            final String custosfinal = custos;
                            listBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(datuiContext.getApplicationContext(), Debtsnwed.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("debtdeterm", finalSweol);

                                    if (mimspr.equals("debtSrecod")) {
                                        ArrayList<ArrayList<String>> paysumdetal = debtSinglerecd(custosfinal, uniqidec);
                                        bundle.putSerializable("debtdetal", paysumdetal);

                                    } else if (mimspr.equals("debtSrecoda")) {
                                        ArrayList<String> paymntdetal = debtSinglerecda(uniqidec);
                                        bundle.putStringArrayList("debtdetal", paymntdetal);

                                    }

                                    intent.putExtras(bundle);

                                    datuiContext.startActivity(intent);

                                }
                            });

                        }
                    }

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 0);
                    usrLinear.addView(usrFlexbox, layoutParams);

                }
            }
            return response;
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            /* Add header */
            debtshedcontpr.removeAllViews();
            debtshedcontpr.addView(debtshedFlexbox, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            /* Add to parent */
            scroLayoutpr.removeAllViews();
            scroLayoutpr.addView(usrLinear, new ScrollView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            progbarpr.setIndeterminate(false);


        }

        protected void onProgressUpdate(Integer... array) {
        }
    }

    /*  Get single customer debt history */
    public ArrayList<ArrayList<String>> debtSinglerecd(String custosfinal, String uniqidecval) {
        String dbess = "custodates";
        String uniqidec = "custodatecuscod";
        String stexa = "custodatestate";
        ArrayList<String> csname = new ArrayList<>();
        csname.add(custosfinal);

        ArrayList<String> Sres = new ArrayList<>(Arrays.asList("custodatdate", "prdctdetazname",
                "custodatesval", "custodatesqnty", "debttotal", "custodatesrecod"));

        ArrayList<ArrayList<String>> fncResp = debtsresoc.gtDatcustos(dbess, Sres, stexa, uniqidec, uniqidecval);
        fncResp.add(csname);

        return fncResp;

    }

    /*  Get single payment */
    public ArrayList<String> debtSinglerecda(String uniqidecval) {
        String dbess = "custodatpay";
        String uniqidec = "custodatpayrecod";

        ArrayList<String> Sres = new ArrayList<>(Arrays.asList("custodatpaydate", "custostblname",
                "custodatpayamt", "custodatpayrecod"));

        ArrayList<String> fncRespa = debtsresoc.gpaySingrecd(dbess, uniqidec, uniqidecval, Sres);


        return fncRespa;

    }

}
