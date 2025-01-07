package com.avintangu.bizsmat.inventory;

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

public class Inventui {
    private static Context invuiContext;
    Resocs resources;
    Dbcluzz dbcluzz;
    Inventresoc inventresoc;

    public Inventui(Context context) {
        invuiContext = context;

        resources = new Resocs(invuiContext);
        dbcluzz = new Dbcluzz(invuiContext);
        inventresoc = new Inventresoc(invuiContext);

    }

    /* Set title */
    public String setinvUitrelis(String tredetocs) {
        String title = "";

        switch (tredetocs) {

            case "inventSrecod": {
                title = invuiContext.getResources().getString(R.string.invent_str);

                break;
            }

            case "inventSrecoda": {
                title = invuiContext.getResources().getString(R.string.invent_stra);

                break;
            }

            default:
                break;
        }

        return title;
    }

    /* Get summary */
    public void inventbackrecdlod(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                                  String frodta, String todta, ProgressBar progbar, LinearLayout inventhedcont) {
        String param = "nada";
        progbar.setIndeterminate(true);
        scroLayout.scrollTo(0, 0);

        new Inventui.inventRecodsync(scroLayout, eunica, colnum, mims, steta, frodta, todta, progbar, inventhedcont).execute(param);
    }

    /* Properties backrecd UI */
    public class inventRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scroLayoutpr;
        double eunicapr;
        Integer colnumpr;
        String mimspr;
        String stetapr;
        ProgressBar progbarpr;
        LinearLayout usrLinear;
        String frodtapr;
        String todtapr;
        LinearLayout inventhedcontpr;
        FlexboxLayout inventhedFlexbox;

        public inventRecodsync(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                               String frodta, String todta, ProgressBar progbar, LinearLayout inventhedcont) {
            this.scroLayoutpr = scroLayout;
            this.eunicapr = eunica;
            this.colnumpr = colnum;
            this.mimspr = mims;
            this.stetapr = steta;
            this.progbarpr = progbar;
            this.frodtapr = frodta;
            this.todtapr = todta;
            this.inventhedcontpr = inventhedcont;

        }

        protected String doInBackground(String... param) {
            String response = "nada";

            String dbess = "";
            String stexa = "";
            String srchdeterm = "";

            ArrayList<String> Sres = new ArrayList<>();
            ArrayList<ArrayList<String>> inventVals = new ArrayList<>();
            ArrayList<String> Titles = null;
            ArrayList<Integer> Gravety = null;
            ArrayList<Float> Widoth = null;

            switch (mimspr) {

                case "inventSrecod": {
                    Sres.add("");
                    Sres.add("");
                    Sres.add("");
                    Sres.add("");
                    Sres.add("");
                    Sres.add("");

                    Titles = new ArrayList<>(Arrays.asList("Product", "Overal sales", "Total debts", "Stocks balance"));

                    Widoth = new ArrayList<>(Arrays.asList(0.3f, 0.23f, 0.23f, 0.24f));
                    Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER,
                            Gravity.CENTER, Gravity.CENTER));

                    inventVals = inventresoc.gtStkBal(srchdeterm, "Active");

                    break;
                }

                case "inventSrecoda": {
                    dbess = "inventbl";
                    stexa = "inventblstate";

                    Sres.add("inventbldate");
                    Sres.add("prdctdetazname");
                    Sres.add("inventblbprc");
                    Sres.add("inventblqnty");
                    Sres.add("inventblrecod");

                    Titles = new ArrayList<>(Arrays.asList("Product", "Buying price", "Quantity"));

                    Widoth = new ArrayList<>(Arrays.asList(0.4f, 0.3f, 0.3f, 0.3f));
                    Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.LEFT, Gravity.CENTER, Gravity.CENTER));

                    inventVals = inventresoc.ghAllinvent(dbess, stexa, stetapr, Sres, srchdeterm, frodtapr, todtapr);

                    break;
                }

                default:
                    break;
            }

            Integer ghasdos = Sres.size() - 2;

            if (colnumpr > ghasdos) {
                colnumpr = ghasdos;
            }


            /* Table container */
            usrLinear = new LinearLayout(invuiContext);
            usrLinear.setPadding(0, 0, 0, 160);
            usrLinear.setId(R.id.prdctconstprnt);
            usrLinear.removeAllViews();
            usrLinear.setOrientation(LinearLayout.VERTICAL);

            /* Headers */
            inventhedFlexbox = new FlexboxLayout(invuiContext);
            inventhedFlexbox.setDividerDrawable(invuiContext.getResources().getDrawable(R.drawable.flexcontdivider));
            inventhedFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
            inventhedFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
            inventhedFlexbox.setBackgroundColor(invuiContext.getResources().getColor(R.color.bluu));

            for (int ghas = 0; ghas < colnumpr; ghas++) {
                TextView header = new TextView(invuiContext);
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
                inventhedFlexbox.addView(header, hedlayoutParams);
            }


            /* Data children */
            ArrayList<Integer> idecs = new ArrayList<>();
            ArrayList<Integer> idecsa = new ArrayList<>();
            Integer mininvcap = 0;
            Integer invcap = 0;

            if (inventVals.get(0).size() > 0) {
                for (int bin = 0; bin < inventVals.get(0).size(); bin++) {
                    idecsa.add(Math.abs(resources.generateViewId()));

                    FlexboxLayout usrFlexbox = new FlexboxLayout(invuiContext);
                    usrFlexbox.setFlexDirection(FlexDirection.ROW);
                    usrFlexbox.setAlignContent(AlignContent.FLEX_START);
                    usrFlexbox.setAlignItems(AlignItems.BASELINE);
                    usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                    usrFlexbox.setPadding(0, 0, 0, 0);
                    usrFlexbox.setId(idecsa.get(bin));
                    usrFlexbox.setBackgroundResource(R.drawable.listflexcont);

                    /*  Set date row for stocks entry */
                    if (mimspr.equals("inventSrecoda")) {
                        mininvcap = 1;
                        invcap = colnumpr + 1;

                        if (bin < 1) {
                            invdateRows(inventVals.get(0).get(bin), eunicapr, usrLinear);
                        } else if (bin > 0) {
                            if (!inventVals.get(0).get(bin).equals(inventVals.get(0).get(bin - 1))) {
                                invdateRows(inventVals.get(0).get(bin), eunicapr, usrLinear);
                            }
                        }
                    } else {
                        invcap = colnumpr;
                    }

                    for (int bina = mininvcap; bina < invcap; bina++) {
                        idecs.add(Math.abs(resources.generateViewId()));

                        TextView listBtn = new TextView(invuiContext);
                        listBtn.setText(inventVals.get(bina).get(bin));
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

                            if (mimspr.equals("inventSrecod")) {
                            } else if (mimspr.equals("inventSrecoda")) {
                                sweol = "editStocks";
                                pruniqidec = inventVals.get(4).get(bin);


                                final String finalSweol = sweol;
                                final String uniqidec = pruniqidec;
                                listBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(invuiContext.getApplicationContext(), Inventnwed.class);
                                        ArrayList<String> invntdetal = inventSinglerecd(mimspr, uniqidec);

                                        Bundle bundle = new Bundle();
                                        bundle.putString("inventdeterm", finalSweol);
                                        bundle.putStringArrayList("inventdetal", invntdetal);
                                        intent.putExtras(bundle);

                                        invuiContext.startActivity(intent);

                                    }
                                });
                            }

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
            inventhedcontpr.removeAllViews();
            inventhedcontpr.addView(inventhedFlexbox, new LinearLayout.LayoutParams(
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
    public void invdateRows(String dtarow, double eunicapr, LinearLayout usrLinear) {
        FlexboxLayout usrFlexbox = new FlexboxLayout(invuiContext);
        usrFlexbox.setFlexDirection(FlexDirection.ROW);
        usrFlexbox.setAlignContent(AlignContent.FLEX_START);
        usrFlexbox.setAlignItems(AlignItems.BASELINE);
        usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
        usrFlexbox.setPadding(0, 0, 0, 0);
        usrFlexbox.setBackgroundResource(R.drawable.listflexconta);

        TextView listBtn = new TextView(invuiContext);
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
    public ArrayList<String> inventSinglerecd(String mims, String uniqidecval) {
        ArrayList<String> inventdetal = new ArrayList<>();

        String dbess = "";
        String uniqidec = "";
        ArrayList<String> Sres = new ArrayList<>();

        switch (mims) {
            case "inventSrecoda": {
                dbess = "inventbl";
                uniqidec = "inventblrecod";

                Sres.add("inventbldate");
                Sres.add("prdctdetazname");
                Sres.add("inventblbprc");
                Sres.add("prdctdetazprcc");
                Sres.add("inventblqnty");
                Sres.add("inventblrecod");

                inventdetal = inventresoc.gtinvSingrecd(mims, dbess, uniqidec, uniqidecval, Sres);

                break;
            }

            default:
                break;
        }

        return inventdetal;
    }


}
