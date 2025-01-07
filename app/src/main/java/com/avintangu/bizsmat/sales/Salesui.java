package com.avintangu.bizsmat.sales;

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

public class Salesui {
    private static Context salesContext;

    Resocs resources;
    Salesresocs salesresocs;
    Dbcluzz dbcluzz;

    public Salesui(Context context) {
        salesContext = context;

        resources = new Resocs(salesContext);
        salesresocs = new Salesresocs(salesContext);
        dbcluzz = new Dbcluzz(salesContext);

    }

    /* Set title */
    public String setsalUitrelis(String tredetocs) {
        String title = "";

        switch (tredetocs) {

            case "salSrecod": {
                title = salesContext.getResources().getString(R.string.sale_str);

                break;
            }

            case "liticSrecod": {
                title = salesContext.getResources().getString(R.string.litics_stri);

                break;
            }

            case "liticSrecoda": {
                title = salesContext.getResources().getString(R.string.litics_stra);

                break;
            }

            case "liticSrecodb": {
                title = salesContext.getResources().getString(R.string.litics_strb);

                break;
            }

            case "liticSrecodc": {
                title = salesContext.getResources().getString(R.string.litics_strj);

                break;
            }

            case "liticSrecodd": {
                title = salesContext.getResources().getString(R.string.litics_stre);

                break;
            }

            default:
                break;
        }

        return title;
    }

    /* Get summary */
    public void salebackrecdlod(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                                String frodta, String todta, ProgressBar progbar, LinearLayout hedcont) {
        String param = "nada";
        progbar.setIndeterminate(true);
        scroLayout.scrollTo(0, 0);

        new Salesui.saleRecodsync(scroLayout, eunica, colnum, mims, steta, frodta, todta, progbar, hedcont).execute(param);
    }

    /* Properties backrecd UI */
    public class saleRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scroLayoutpr;
        double eunicapr;
        Integer colnumpr;
        String mimspr;
        String stetapr;
        String frodtapr;
        String todtapr;
        ProgressBar progbarpr;
        LinearLayout usrLinear;
        LinearLayout hedcontpr;
        FlexboxLayout hedFlexbox;

        public saleRecodsync(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                             String frodta, String todta, ProgressBar progbar, LinearLayout hedcont) {
            this.scroLayoutpr = scroLayout;
            this.eunicapr = eunica;
            this.colnumpr = colnum;
            this.mimspr = mims;
            this.stetapr = steta;
            this.progbarpr = progbar;
            this.frodtapr = frodta;
            this.todtapr = todta;
            this.hedcontpr = hedcont;

        }

        protected String doInBackground(String... param) {
            String response = "nada";

            String dbess = "";
            String stexa = "";
            String srchdeterm = "";

            ArrayList<String> Sres = new ArrayList<>();
            ArrayList<String> Titles = null;
            ArrayList<Integer> Gravety = null;
            ArrayList<Float> Widoth = null;

            switch (mimspr) {

                case "salSrecod": {
                    dbess = "salesrecd";
                    stexa = "salesrecdstate";

                    Sres.add("salesrecddate");
                    Sres.add("prdctdetazname");
                    Sres.add("salesrecdprcc");
                    Sres.add("salesrecdqnty");
                    Sres.add("salesrecdtotal");
                    Sres.add("salesrecdrecod");

                    Titles = new ArrayList<>(Arrays.asList("Product", "Price", "Quantity", "Total"));

                    Widoth = new ArrayList<>(Arrays.asList(1f, 0.4f, 0.2f, 0.2f, 0.2f));
                    Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.LEFT, Gravity.CENTER,
                            Gravity.CENTER, Gravity.CENTER));

                    break;
                }

                default:
                    break;
            }

            Integer ghasdos = Sres.size() - 1;

            if (colnumpr > ghasdos) {
                colnumpr = ghasdos;
            }

            ArrayList<ArrayList<String>> salVals = salesresocs.ghAllsales(dbess, stexa,
                    stetapr, Sres, srchdeterm, mimspr, frodtapr, todtapr);


            /* Table container */
            usrLinear = new LinearLayout(salesContext);
            usrLinear.setPadding(0, 0, 0, 160);
            usrLinear.setId(R.id.prdctconstprnt);
            usrLinear.removeAllViews();
            usrLinear.setOrientation(LinearLayout.VERTICAL);

            /* Headers */
            hedFlexbox = new FlexboxLayout(salesContext);
            hedFlexbox.setDividerDrawable(salesContext.getResources().getDrawable(R.drawable.flexcontdivider));
            hedFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
            hedFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
            hedFlexbox.setBackgroundColor(salesContext.getResources().getColor(R.color.bluu));

            for (int ghas = 0; ghas < colnumpr; ghas++) {
                TextView header = new TextView(salesContext);
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
                hedFlexbox.addView(header, hedlayoutParams);
            }

            /* Data children */
            ArrayList<Integer> idecs = new ArrayList<>();
            ArrayList<Integer> idecsa = new ArrayList<>();

            if (salVals.get(0).size() > 0) {
                for (int bin = 0; bin < salVals.get(0).size(); bin++) {
                    idecsa.add(Math.abs(resources.generateViewId()));

                    FlexboxLayout usrFlexbox = new FlexboxLayout(salesContext);
                    usrFlexbox.setFlexDirection(FlexDirection.ROW);
                    usrFlexbox.setAlignContent(AlignContent.FLEX_START);
                    usrFlexbox.setAlignItems(AlignItems.BASELINE);
                    usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                    usrFlexbox.setPadding(0, 0, 0, 0);
                    usrFlexbox.setId(idecsa.get(bin));
                    usrFlexbox.setBackgroundResource(R.drawable.listflexcont);

                    /*  Set date row */
                    if (bin < 1) {
                        dateRows(salVals.get(0).get(bin), eunicapr, usrLinear);
                    } else if (bin > 0) {
                        if (!salVals.get(0).get(bin).equals(salVals.get(0).get(bin - 1))) {
                            dateRows(salVals.get(0).get(bin), eunicapr, usrLinear);
                        }
                    }

                    for (int bina = 1; bina < colnumpr + 1; bina++) {

                        /*  Date row */
                        FlexboxLayout dateFlexbox = new FlexboxLayout(salesContext);
                        dateFlexbox.setFlexDirection(FlexDirection.ROW);
                        dateFlexbox.setAlignContent(AlignContent.FLEX_START);
                        dateFlexbox.setAlignItems(AlignItems.BASELINE);
                        dateFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                        dateFlexbox.setPadding(0, 0, 0, 0);
                        dateFlexbox.setBackgroundResource(R.drawable.listflexcont);

                        /*  Data views */
                        idecs.add(Math.abs(resources.generateViewId()));

                        TextView listBtn = new TextView(salesContext);
                        listBtn.setText(salVals.get(bina).get(bin));
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
                            String sweol = "";
                            String pruniqidec = "";

                            if (mimspr.equals("salSrecod")) {
                                sweol = "editSale";
                                pruniqidec = salVals.get(5).get(bin);

                            }

                            final String finalSweol = sweol;
                            final String uniqidec = pruniqidec;
                            listBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(salesContext.getApplicationContext(), Salenew.class);
                                    ArrayList<String> prdctdetal = saleSinglerecd(mimspr, uniqidec);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("saldeterm", finalSweol);
                                    bundle.putStringArrayList("salesdetal", prdctdetal);
                                    intent.putExtras(bundle);

                                    salesContext.startActivity(intent);

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
            hedcontpr.removeAllViews();
            hedcontpr.addView(hedFlexbox, new LinearLayout.LayoutParams(
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

    /* Add the date rows */
    public void dateRows(String dtarow, double eunicapr, LinearLayout usrLinear) {
        FlexboxLayout usrFlexbox = new FlexboxLayout(salesContext);
        usrFlexbox.setFlexDirection(FlexDirection.ROW);
        usrFlexbox.setAlignContent(AlignContent.FLEX_START);
        usrFlexbox.setAlignItems(AlignItems.BASELINE);
        usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
        usrFlexbox.setPadding(0, 0, 0, 0);
        usrFlexbox.setBackgroundResource(R.drawable.listflexconta);

        TextView listBtn = new TextView(salesContext);
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

    /* View single record */
    public ArrayList<String> saleSinglerecd(String mims, String uniqidecval) {
        ArrayList<String> prdctsdetal = new ArrayList<>();

        String dbess = "";
        String uniqidec = "";
        ArrayList<String> Sres = new ArrayList<>();

        switch (mims) {

            case "salSrecod": {
                dbess = "salesrecd";
                uniqidec = "salesrecdrecod";

                Sres.add("salesrecddate");
                Sres.add("prdctdetazname");
                Sres.add("salesrecdprcc");
                Sres.add("salesrecdqnty");
                Sres.add("salesrecdtotal");
                Sres.add("salesrecdrecod");

                prdctsdetal = salesresocs.gtsalSingrecd(mims, dbess, uniqidec, uniqidecval, Sres);

                break;
            }

            default:
                break;
        }

        return prdctsdetal;

    }
}
