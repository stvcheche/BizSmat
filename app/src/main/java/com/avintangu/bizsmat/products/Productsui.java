package com.avintangu.bizsmat.products;

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

public class Productsui {
    private static Context prdContext;
    Resocs resources;
    Prdctresocs prdctresocs;
    Dbcluzz dbcluzz;

    public Productsui(Context context) {
        prdContext = context;

        resources = new Resocs(prdContext);
        prdctresocs = new Prdctresocs(prdContext);
        dbcluzz = new Dbcluzz(prdContext);

    }

    /* Set title */
    public String setprdUitrelisx(String tredetocs) {
        String title = "";

        switch (tredetocs) {

            case "prdSrecod": {
                title = prdContext.getResources().getString(R.string.prdct_strc);

                break;
            }

            case "prdSrecoda": {
                title = prdContext.getResources().getString(R.string.prdct_strd);

                break;
            }

            default:
                break;
        }

        return title;
    }

    /* Get summary */
    public void prdctbackrecdlod(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                                 ProgressBar progbar, LinearLayout prdcthedcont) {
        String param = "nada";
        progbar.setIndeterminate(true);
        scroLayout.scrollTo(0, 0);

        new prdctRecodsync(scroLayout, eunica, colnum, mims, steta, progbar, prdcthedcont).execute(param);
    }

    /* Properties backrecd UI */
    public class prdctRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scroLayoutpr;
        double eunicapr;
        Integer colnumpr;
        String mimspr;
        String stetapr;
        ProgressBar progbarpr;
        LinearLayout usrLinear;
        LinearLayout prdcthedcontpr;
        FlexboxLayout prdcthedFlexbox;

        public prdctRecodsync(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                              ProgressBar progbar, LinearLayout prdcthedcont) {
            this.scroLayoutpr = scroLayout;
            this.eunicapr = eunica;
            this.colnumpr = colnum;
            this.mimspr = mims;
            this.stetapr = steta;
            this.progbarpr = progbar;
            this.prdcthedcontpr = prdcthedcont;

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

                case "prdSrecod": {
                    dbess = "prdctdetaz";
                    stexa = "prdctdetazstate";

                    Sres.add("prdctdetazname");
                    Sres.add("prdctdetazprcc");
                    Sres.add("prdctdetazproft");
                    Sres.add("prdctdetazdate");
                    Sres.add("prdctdetazcode");

                    Titles = new ArrayList<>(Arrays.asList("Name", "Driver", "Profit",
                            "Date created"));

                    Widoth = new ArrayList<>(Arrays.asList(0.30f, 0.15f, 0.25f, 0.30f));
                    Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER,
                            Gravity.CENTER, Gravity.LEFT));

                    break;
                }

                case "prdSrecoda": {
                    dbess = "prdctdcateri";
                    stexa = "prdctdcateristate";

                    Sres.add("prdctdcatego");
                    Sres.add("prdctdcaterid");
                    Sres.add("prdctdcateriuniq");

                    Titles = new ArrayList<>(Arrays.asList("Category", "ID"));

                    Widoth = new ArrayList<>(Arrays.asList(0.6f, 0.4f));
                    Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER));

                    break;
                }

                default:
                    break;
            }

            Integer ghasdos = Sres.size() - 1;

            if (colnumpr > ghasdos) {
                colnumpr = ghasdos;
            }

            ArrayList<ArrayList<String>> usrVals = prdctresocs.ghAllprdcts(dbess, stexa,
                    stetapr, Sres, srchdeterm, mimspr);

            /* Table container */
            usrLinear = new LinearLayout(prdContext);
            usrLinear.setPadding(0, 0, 0, 160);
            usrLinear.setId(R.id.prdctconstprnt);
            usrLinear.removeAllViews();
            usrLinear.setOrientation(LinearLayout.VERTICAL);

            /* Headers */
            prdcthedFlexbox = new FlexboxLayout(prdContext);
            prdcthedFlexbox.setDividerDrawable(prdContext.getResources().getDrawable(R.drawable.flexcontdivider));
            prdcthedFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
            prdcthedFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
            prdcthedFlexbox.setBackgroundColor(prdContext.getResources().getColor(R.color.bluu));

            for (int ghas = 0; ghas < colnumpr; ghas++) {
                TextView header = new TextView(prdContext);
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
                prdcthedFlexbox.addView(header, hedlayoutParams);
            }

            /* Data children */
            ArrayList<Integer> idecs = new ArrayList<>();
            ArrayList<Integer> idecsa = new ArrayList<>();

            if (usrVals.get(0).size() > 0) {

                for (int bin = 0; bin < usrVals.get(0).size(); bin++) {
                    idecsa.add(Math.abs(resources.generateViewId()));

                    FlexboxLayout usrFlexbox = new FlexboxLayout(prdContext);
                    usrFlexbox.setFlexDirection(FlexDirection.ROW);
                    usrFlexbox.setAlignContent(AlignContent.FLEX_START);
                    usrFlexbox.setAlignItems(AlignItems.BASELINE);
                    usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                    usrFlexbox.setPadding(0, 0, 0, 0);
                    usrFlexbox.setId(idecsa.get(bin));
                    usrFlexbox.setBackgroundResource(R.drawable.listflexcont);

                    for (int bina = 0; bina < colnumpr; bina++) {
                        idecs.add(Math.abs(resources.generateViewId()));

                        TextView listBtn = new TextView(prdContext);
                        listBtn.setText(usrVals.get(bina).get(bin));
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

                            if (mimspr.equals("prdSrecod")) {
                                sweol = "editProduct";
                                pruniqidec = usrVals.get(4).get(bin);

                            } else if (mimspr.equals("prdSrecoda")) {
                                sweol = "editCategory";
                                pruniqidec = usrVals.get(2).get(bin);

                            }

                            final String finalSweol = sweol;
                            final String uniqidec = pruniqidec;
                            listBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(prdContext.getApplicationContext(), Prdctnew.class);
                                    ArrayList<String> prdctdetal = prdctSinglerecd(mimspr, uniqidec);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("prdeterm", finalSweol);
                                    bundle.putStringArrayList("prdctsdetal", prdctdetal);
                                    intent.putExtras(bundle);

                                    prdContext.startActivity(intent);

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
            prdcthedcontpr.removeAllViews();
            prdcthedcontpr.addView(prdcthedFlexbox, new LinearLayout.LayoutParams(
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

    /* View single record */
    public ArrayList<String> prdctSinglerecd(String mims, String uniqidecval) {
        ArrayList<String> prdctsdetal = new ArrayList<>();

        String dbess = "";
        String uniqidec = "";
        ArrayList<String> Sres = new ArrayList<>();

        switch (mims) {

            case "prdSrecod": {
                dbess = "prdctdetaz";
                uniqidec = "prdctdetazcode";

                Sres.add("prdctdetazdate");
                Sres.add("prdctdcatego");
                Sres.add("prdctdetazname");
                Sres.add("prdctdetazprcc");
                Sres.add("prdctdetazprcalc");
                Sres.add("prdctdetazproft");
                Sres.add("prdctdetazcode");


                prdctsdetal = prdctresocs.gtaSingrecd(mims, dbess, uniqidec, uniqidecval, Sres);

                break;
            }

            case "prdSrecoda": {
                dbess = "prdctdcateri";
                uniqidec = "prdctdcateriuniq";

                Sres.add("prdctdcaterid");
                Sres.add("prdctdcatego");
                Sres.add("prdctdcateriuniq");

                prdctsdetal = prdctresocs.gtaSingrecd(mims, dbess, uniqidec, uniqidecval, Sres);

                break;
            }

            default:
                break;
        }

        return prdctsdetal;

    }
}
