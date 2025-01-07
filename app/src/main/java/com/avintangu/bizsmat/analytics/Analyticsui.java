package com.avintangu.bizsmat.analytics;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.resources.Resocs;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Analyticsui {
    private static Context prdnewContext;
    Resocs resources;
    Analyticsresocs analyticsresocs;
    Analyticslogic analyticslogic;
    Dbcluzz dbcluzz;
    Dchartswitch dchartswitch;
    Mpchartres mpchartres;
    DecimalFormat formatter = new DecimalFormat("#,###");

    public Analyticsui(Context context) {
        prdnewContext = context;

        resources = new Resocs(prdnewContext);
        analyticsresocs = new Analyticsresocs(prdnewContext);
        dbcluzz = new Dbcluzz(prdnewContext);
        analyticslogic = new Analyticslogic(prdnewContext);
        dchartswitch = new Dchartswitch(prdnewContext);
        mpchartres = new Mpchartres(prdnewContext);


    }

    /* Set title */
    public String setliticUitrelis(String tredetocs) {
        String title = "";

        switch (tredetocs) {

            case "liticSrecod": {
                title = prdnewContext.getResources().getString(R.string.litics_stri);

                break;
            }

            case "liticSrecoda": {
                title = prdnewContext.getResources().getString(R.string.litics_stra);

                break;
            }

            case "liticSrecodb": {
                title = prdnewContext.getResources().getString(R.string.litics_strb);

                break;
            }

            case "liticSrecodc": {
                title = prdnewContext.getResources().getString(R.string.litics_strj);

                break;
            }

            case "liticSrecodd": {
                title = prdnewContext.getResources().getString(R.string.litics_stre);

                break;
            }

            default:
                break;
        }

        return title;
    }

    /* Data switch */
    public void liticsbackrecdlod(ScrollView scroLayout, double eunica, String tabcode, String frodta, String todta, ProgressBar progbar,
                                  LinearLayout liticshedcont) {
        String param = "nada";
        progbar.setIndeterminate(true);
        scroLayout.scrollTo(0, 0);

        new Analyticsui.debtRecodsync(scroLayout, eunica, tabcode, frodta, todta, progbar, liticshedcont).execute(param);
    }

    /* Properties backrecd UI */
    public class debtRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scroLayoutpr;
        double eunicapr;
        String tabcodepr;
        ProgressBar progbarpr;
        LinearLayout usrLinear;
        FlexboxLayout usrFlexbox;
        String frodtapr;
        String todtapr;
        LinearLayout liticshedcontpr;
        FlexboxLayout liticshedFlexbox;

        public debtRecodsync(ScrollView scroLayout, double eunica, String tabcode,
                             String frodta, String todta, ProgressBar progbar, LinearLayout liticshedcont) {
            this.scroLayoutpr = scroLayout;
            this.eunicapr = eunica;
            this.tabcodepr = tabcode;
            this.progbarpr = progbar;
            this.frodtapr = frodta;
            this.todtapr = todta;
            this.liticshedcontpr = liticshedcont;


            if (tabcode.equals("liticSrecodc")) {
                LayoutInflater inflater = (LayoutInflater) prdnewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View Linview = inflater.inflate(R.layout.liticscharts, null);
                usrFlexbox = Linview.findViewById(R.id.liticschartparent);

            } else if (tabcode.equals("liticSrecodd")) {
                LayoutInflater inflater = (LayoutInflater) prdnewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View Linview = inflater.inflate(R.layout.liticschartsa, null);
                usrFlexbox = Linview.findViewById(R.id.liticschartparent);

            }

        }

        protected String doInBackground(String... param) {
            String response = "cool";

            switch (tabcodepr) {

                case "liticSrecod": {
                    usrLinear = new LinearLayout(prdnewContext);

                    ArrayList<String> Sres = new ArrayList<>(Arrays.asList("prdctdetazname", "salesum", "saleval"));
                    ArrayList<String> Titles = new ArrayList<>(Arrays.asList("Product", "Total sales", "Sales value"));

                    ArrayList<Float> Widoth = new ArrayList<>(Arrays.asList(0.4f, 0.3f, 0.3f));
                    ArrayList<Integer> Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER, Gravity.CENTER));

                    ArrayList<ArrayList<String>> combvals = analyticslogic.gtAllsaleval(Sres, frodtapr, todtapr);
                    liticshedFlexbox = genTables(usrLinear, prdnewContext, eunicapr, Titles, Widoth, combvals, Gravety);

                    break;
                }

                case "liticSrecoda": {
                    usrLinear = new LinearLayout(prdnewContext);

                    ArrayList<String> Sres = new ArrayList<>(Arrays.asList("prdctdcatego", "salesum", "saleval"));
                    ArrayList<String> Titles = new ArrayList<>(Arrays.asList("Product category", "Total sales", "Sales value"));

                    ArrayList<Float> Widoth = new ArrayList<>(Arrays.asList(0.4f, 0.3f, 0.3f));
                    ArrayList<Integer> Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER, Gravity.CENTER));

                    ArrayList<ArrayList<String>> combvals = analyticslogic.gtAllsalevalcatry(Sres, frodtapr, todtapr);
                    liticshedFlexbox = genTables(usrLinear, prdnewContext, eunicapr, Titles, Widoth, combvals, Gravety);

                    break;
                }

                case "liticSrecodb": {
                    usrLinear = new LinearLayout(prdnewContext);

                    ArrayList<String> Sres = new ArrayList<>(Arrays.asList("prdctdetazname", "saleval", "profitrelis"));
                    ArrayList<String> Titles = new ArrayList<>(Arrays.asList("Product", "Sales value", "Profit"));

                    ArrayList<Float> Widoth = new ArrayList<>(Arrays.asList(0.4f, 0.3f, 0.3f));
                    ArrayList<Integer> Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER, Gravity.CENTER));

                    ArrayList<ArrayList<String>> combvals = analyticslogic.gtAllsalprofit(Sres, frodtapr, todtapr);
                    liticshedFlexbox = genTables(usrLinear, prdnewContext, eunicapr, Titles, Widoth, combvals, Gravety);

                    break;
                }

                default:
                    break;
            }

            return response;
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            switch (tabcodepr) {

                case "liticSrecodc": {
                    String rcvtyPacht = "dtatypa";
                    String determ = "brcht";

                    /* Total sales*/
                    String rcvbarFnc = "genSales";
                    usrFlexbox.findViewById(R.id.gendashflexchrt).setLayoutParams(new
                            FlexboxLayout.LayoutParams(analyticsresocs.liticsDimens().get(0), analyticsresocs.liticsDimens().get(1)));
                    BarChart barChart = usrFlexbox.findViewById(R.id.genChart);
                    ArrayList<ArrayList> ttlerev = dchartswitch.linebackrt(rcvtyPacht, frodtapr, todtapr, rcvbarFnc, determ);
                    ArrayList<String> mssg = (ArrayList) ttlerev.get(2);
                    ttlerev.remove(ttlerev.size() - 1);


                    if (mssg.get(0).equals("cool")) {
                        mpchartres.barChart(ttlerev, barChart, Color.rgb(61, 148, 246));
                    }

                    /* Total revenue*/
                    String rcvbarFnca = "genReve";
                    usrFlexbox.findViewById(R.id.gendashflexchrta).setLayoutParams(new
                            FlexboxLayout.LayoutParams(analyticsresocs.liticsDimens().get(0), analyticsresocs.liticsDimens().get(1)));
                    BarChart barCharta = usrFlexbox.findViewById(R.id.genCharta);
                    ArrayList<ArrayList> ttlereva = dchartswitch.linebackrt(rcvtyPacht, frodtapr, todtapr, rcvbarFnca, determ);
                    ArrayList<String> mssga = (ArrayList) ttlereva.get(2);
                    ttlereva.remove(ttlereva.size() - 1);


                    if (mssga.get(0).equals("cool")) {
                        mpchartres.barChart(ttlereva, barCharta, Color.rgb(128, 0, 128));
                    }

                    /* Total profit*/
                    String rcvbarFncb = "genProfit";
                    usrFlexbox.findViewById(R.id.gendashflexchrtb).setLayoutParams(new
                            FlexboxLayout.LayoutParams(analyticsresocs.liticsDimens().get(0), analyticsresocs.liticsDimens().get(1)));
                    BarChart barChartb = usrFlexbox.findViewById(R.id.genChartb);
                    ArrayList<ArrayList> ttlerevb = dchartswitch.linebackrt(rcvtyPacht, frodtapr, todtapr, rcvbarFncb, determ);
                    ArrayList<String> mssgb = (ArrayList) ttlerevb.get(2);
                    ttlerevb.remove(ttlerevb.size() - 1);


                    if (mssgb.get(0).equals("cool")) {
                        mpchartres.barChart(ttlerevb, barChartb, Color.rgb(0, 128, 0));
                    }

                    break;
                }

                case "liticSrecodd": {
                    String rcvtyPacht = "dtatypa";
                    String determ = "brcht";

                    /* Total stocks*/
                    String rcvbarFnc = "genStocks";
                    usrFlexbox.findViewById(R.id.gendashflexchrt).setLayoutParams(new
                            FlexboxLayout.LayoutParams(analyticsresocs.liticsDimens().get(0), analyticsresocs.liticsDimens().get(1)));
                    BarChart barChart = usrFlexbox.findViewById(R.id.genChart);
                    ArrayList<ArrayList> ttlerev = dchartswitch.linebackrt(rcvtyPacht, frodtapr, todtapr, rcvbarFnc, determ);
                    ArrayList<String> mssg = (ArrayList) ttlerev.get(2);
                    ttlerev.remove(ttlerev.size() - 1);

                    if (mssg.get(0).equals("cool")) {
                        mpchartres.barChart(ttlerev, barChart, Color.rgb(61, 148, 246));
                    }

                    break;
                }

                default:
                    break;
            }

            if (tabcodepr.equals("liticSrecod") || tabcodepr.equals("liticSrecoda") || tabcodepr.equals("liticSrecodb")) {
                /* Add header */
                liticshedcontpr.setVisibility(View.VISIBLE);
                liticshedcontpr.removeAllViews();
                liticshedcontpr.addView(liticshedFlexbox, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                /* Add to parent */
                scroLayoutpr.removeAllViews();
                scroLayoutpr.addView(usrLinear, new ScrollView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                progbarpr.setIndeterminate(false);

            } else if (tabcodepr.equals("liticSrecodc") || tabcodepr.equals("liticSrecodd")) {
                liticshedcontpr.setVisibility(View.GONE);
                scroLayoutpr.removeAllViews();
                scroLayoutpr.addView(usrFlexbox, new ScrollView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                progbarpr.setIndeterminate(false);

            }


        }

        protected void onProgressUpdate(Integer... array) {
        }
    }

    /*  Generate tables */
    public FlexboxLayout genTables(LinearLayout usrLinear, Context currContext, double eunicas, ArrayList<String> Titles,
                                   ArrayList<Float> Widoth, ArrayList<ArrayList<String>> combvals, ArrayList<Integer> Gravety) {

        /* Table container */
        usrLinear.setPadding(0, 0, 0, 160);
        usrLinear.setId(R.id.prdctconstprnt);
        usrLinear.removeAllViews();
        usrLinear.setOrientation(LinearLayout.VERTICAL);

        /* Headers */
        FlexboxLayout hedFlexbox = new FlexboxLayout(currContext);
        hedFlexbox.setDividerDrawable(currContext.getResources().getDrawable(R.drawable.flexcontdivider));
        hedFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
        hedFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
        hedFlexbox.setBackgroundColor(currContext.getResources().getColor(R.color.bluu));

        for (int ghas = 0; ghas < Titles.size(); ghas++) {
            TextView header = new TextView(currContext);
            header.setGravity(Gravity.CENTER);
            header.setTextSize((float) eunicas + 6.0f);
            header.setText(Titles.get(ghas));
            header.setTextColor(Color.rgb(255, 255, 255));
            header.setPadding(5, 10, 5, 10);

            FlexboxLayout.LayoutParams hedlayoutParams = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.MATCH_PARENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT);

            hedlayoutParams.setFlexBasisPercent(Widoth.get(ghas));
            hedlayoutParams.setFlexGrow(1.0f);
            hedFlexbox.addView(header, hedlayoutParams);
        }


        /* Data children */
        ArrayList<Integer> idecs = new ArrayList<>();
        ArrayList<Integer> idecsa = new ArrayList<>();

        if (combvals.get(0).size() > 0) {
            for (int bin = 0; bin < combvals.get(0).size(); bin++) {
                idecsa.add(Math.abs(resources.generateViewId()));

                FlexboxLayout usrFlexbox = new FlexboxLayout(currContext);
                usrFlexbox.setFlexDirection(FlexDirection.ROW);
                usrFlexbox.setAlignContent(AlignContent.FLEX_START);
                usrFlexbox.setAlignItems(AlignItems.BASELINE);
                usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                usrFlexbox.setPadding(0, 0, 0, 0);
                usrFlexbox.setId(idecsa.get(bin));
                usrFlexbox.setBackgroundResource(R.drawable.listflexcont);

                for (int bina = 0; bina < combvals.size(); bina++) {
                    idecs.add(Math.abs(resources.generateViewId()));

                    TextView listBtn = new TextView(currContext);
                    listBtn.setText(combvals.get(bina).get(bin));
                    listBtn.setId(idecs.get(bin));
                    listBtn.setAllCaps(false);
                    listBtn.setGravity(Gravety.get(bina));
                    listBtn.setTextSize((float) eunicas + 5.0f);
                    listBtn.setBackgroundResource(R.drawable.txtvewlistcomb);
                    listBtn.setPadding(16, 36, 10, 10);
                    listBtn.setTextColor(Color.rgb(77, 77, 77));

                    FlexboxLayout.LayoutParams lfayoutParams = new FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.MATCH_PARENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    lfayoutParams.setFlexBasisPercent(Widoth.get(bina));
                    lfayoutParams.setFlexGrow(1.0f);
                    usrFlexbox.addView(listBtn, lfayoutParams);

                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);
                usrLinear.addView(usrFlexbox, layoutParams);

            }
        }

        return hedFlexbox;
    }

}
