package com.avintangu.bizsmat.orders;

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

public class Ordersui {
    private static Context orduiContext;
    Resocs resources;
    Dbcluzz dbcluzz;
    Ordersresoc ordersresoc;

    public Ordersui(Context context) {
        orduiContext = context;

        resources = new Resocs(orduiContext);
        dbcluzz = new Dbcluzz(orduiContext);
        ordersresoc = new Ordersresoc(orduiContext);

    }

    /* Set title */
    public String setordUitrelis(String tredetocs) {
        String title = "";

        switch (tredetocs) {

            case "ordeSrecod": {
                title = orduiContext.getResources().getString(R.string.ordestra);

                break;
            }

            case "ordeSrecoda": {
                title = orduiContext.getResources().getString(R.string.ordestrb);

                break;
            }

            default:
                break;
        }

        return title;
    }

    /* Get summary */
    public void ordbackrecdlod(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                               String frodta, String todta, ProgressBar progbar, String distros, LinearLayout ordeshedcont) {
        String param = "nada";
        progbar.setIndeterminate(true);
        scroLayout.scrollTo(0, 0);

        ArrayList<String> distrosarry = new ArrayList<>(Arrays.asList(distros.split(" -> ")));

        new Ordersui.ordRecodsync(scroLayout, eunica, colnum, mims, steta, frodta, todta, progbar, distrosarry.get(0), ordeshedcont).execute(param);
    }

    /* Properties backrecd UI */
    public class ordRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scroLayoutpr;
        double eunicapr;
        Integer colnumpr;
        String mimspr;
        String stetapr;
        String frodtapr;
        String todtapr;
        ProgressBar progbarpr;
        LinearLayout usrLinear;
        String distrospr;
        LinearLayout ordeshedcontpr;
        FlexboxLayout ordeshedFlexbox;

        public ordRecodsync(ScrollView scroLayout, double eunica, Integer colnum, String mims, String steta,
                            String frodta, String todta, ProgressBar progbar, String distros, LinearLayout ordeshedcont) {
            this.scroLayoutpr = scroLayout;
            this.eunicapr = eunica;
            this.colnumpr = colnum;
            this.mimspr = mims;
            this.stetapr = steta;
            this.progbarpr = progbar;
            this.frodtapr = frodta;
            this.todtapr = todta;
            this.distrospr = distros;
            this.ordeshedcontpr = ordeshedcont;

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

                case "ordeSrecod": {
                    dbess = "pricelist";
                    stexa = "pricescstate";

                    Sres.add("pricescprdct");
                    Sres.add("pricescprcc");
                    Sres.add("pricescavail");
                    Sres.add("pricescidec");

                    Titles = new ArrayList<>(Arrays.asList("Product", "Price", "Availability"));

                    Widoth = new ArrayList<>(Arrays.asList(0.4f, 0.3f, 0.3f));
                    Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER, Gravity.LEFT));

                    break;
                }

                case "ordeSrecoda": {
                    dbess = "ordesdetal";
                    stexa = "ordetalstate";
                    stetapr = resources.Implodesa(new ArrayList(Arrays.asList("Pending", "Dispatched")));

                    Sres.add("ordetaldta");
                    Sres.add("prdtotal");
                    Sres.add("distroscname");
                    Sres.add("ordetalrecipt");
                    Sres.add("ordetaluniq");

                    Titles = new ArrayList<>(Arrays.asList("Date", "Items", "Distributor", "Status"));

                    Widoth = new ArrayList<>(Arrays.asList(0.3f, 0.15f, 0.30f, 0.25f));
                    Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER, Gravity.LEFT, Gravity.LEFT));

                    break;
                }

                default:
                    break;
            }

            Integer ghasdos = Sres.size() - 1;

            if (colnumpr > ghasdos) {
                colnumpr = ghasdos;
            }

            ArrayList<ArrayList<String>> ordVals = ordersresoc.gtArecods(dbess, stexa,
                    stetapr, Sres, srchdeterm, mimspr, frodtapr, todtapr, distrospr);

            /* Table container */
            usrLinear = new LinearLayout(orduiContext);
            usrLinear.setPadding(0, 0, 0, 100);
            usrLinear.setId(R.id.prdctconstprnt);
            usrLinear.removeAllViews();
            usrLinear.setOrientation(LinearLayout.VERTICAL);

            /* Headers */
            ordeshedFlexbox = new FlexboxLayout(orduiContext);
            ordeshedFlexbox.setDividerDrawable(orduiContext.getResources().getDrawable(R.drawable.flexcontdivider));
            ordeshedFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
            ordeshedFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
            ordeshedFlexbox.setBackgroundColor(orduiContext.getResources().getColor(R.color.bluu));

            for (int ghas = 0; ghas < colnumpr; ghas++) {
                TextView header = new TextView(orduiContext);
                header.setGravity(Gravity.CENTER);
                header.setTextSize((float) eunicapr + 6.0f);
                header.setText(Titles.get(ghas));
                header.setTextColor(Color.rgb(255, 255, 255));
                header.setPadding(0, 10, 0, 10);

                FlexboxLayout.LayoutParams hedlayoutParams = new FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.MATCH_PARENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT);

                hedlayoutParams.setFlexBasisPercent(Widoth.get(ghas));
                hedlayoutParams.setFlexGrow(1.0f);
                ordeshedFlexbox.addView(header, hedlayoutParams);
            }

            /* Data children */
            ArrayList<Integer> idecs = new ArrayList<>();
            ArrayList<Integer> idecsa = new ArrayList<>();

            if (ordVals.get(0).size() > 0) {
                for (int bin = 0; bin < ordVals.get(0).size(); bin++) {
                    idecsa.add(Math.abs(resources.generateViewId()));

                    FlexboxLayout usrFlexbox = new FlexboxLayout(orduiContext);
                    usrFlexbox.setFlexDirection(FlexDirection.ROW);
                    usrFlexbox.setAlignContent(AlignContent.FLEX_START);
                    usrFlexbox.setAlignItems(AlignItems.BASELINE);
                    usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                    usrFlexbox.setPadding(0, 0, 0, 0);
                    usrFlexbox.setId(idecsa.get(bin));
                    usrFlexbox.setBackgroundResource(R.drawable.listflexcont);

                    for (int bina = 0; bina < colnumpr; bina++) {

                        /*  Data views */
                        idecs.add(Math.abs(resources.generateViewId()));

                        TextView listBtn = new TextView(orduiContext);
                        listBtn.setText(ordVals.get(bina).get(bin));
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

                            if (mimspr.equals("ordeSrecod")) {
                                sweol = "editprcc";
                                pruniqidec = ordVals.get(3).get(bin);

                            } else if (mimspr.equals("ordeSrecoda")) {
                                sweol = "editord";
                                pruniqidec = ordVals.get(4).get(bin);

                            }

                            final String finalSweol = sweol;
                            final String uniqidec = pruniqidec;

                            if (mimspr.equals("ordeSrecoda")) {
                                listBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(orduiContext.getApplicationContext(), Ordesnew.class);
                                        ArrayList ordesdetal = ordSinglerecd(mimspr, uniqidec);

                                        Bundle bundle = new Bundle();
                                        bundle.putString("ordeterm", finalSweol);
                                        bundle.putStringArrayList("ordesdetal", ordesdetal);
                                        intent.putExtras(bundle);

                                        orduiContext.startActivity(intent);

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
            ordeshedcontpr.removeAllViews();
            ordeshedcontpr.addView(ordeshedFlexbox, new LinearLayout.LayoutParams(
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

    public ArrayList ordSinglerecd(String mims, String uniqidecval) {
        ArrayList<String> ordetails = new ArrayList<>();
        ArrayList<ArrayList<String>> orders = new ArrayList<>();
        ArrayList combobject = new ArrayList();

        String dbess = "";
        String uniqidec = "";
        ArrayList<String> Sres = new ArrayList<>();

        String dbessa = "";
        String uniqideca = "";
        ArrayList<String> Sresa = new ArrayList<>();

        switch (mims) {

            case "ordeSrecoda": {
                dbess = "ordesdetal";
                uniqidec = "ordetaluniq";

                Sres.add("ordetaldta");
                Sres.add("ordetaltme");
                Sres.add("usrshopname");
                Sres.add("distroscname");
                Sres.add("ordetalpymnt");
                Sres.add("ordetalnotes");
                Sres.add("ordetaluniq");
                Sres.add("ordetalapprv");
                Sres.add("ordetalstate");
                Sres.add("ordetalrecipt");

                ordetails = ordersresoc.gtordSingrecd(mims, dbess, uniqidec, uniqidecval, Sres);

                dbessa = "ordesprdcts";
                uniqideca = "ordetaluniq";

                Sresa.add("pricescprdct");
                Sresa.add("ordprdctprcc");
                Sresa.add("ordprdctqnty");
                Sresa.add("ordprdctuniq");

                orders = ordersresoc.gtordSingrecda(mims, dbessa, uniqideca, uniqidecval, Sresa);
                break;
            }

            default:
                break;
        }

        combobject.add(ordetails);
        combobject.add(orders);

        return combobject;
    }


}
