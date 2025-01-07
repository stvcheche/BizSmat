package com.avintangu.bizsmat.inventory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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

public class Inventnwedui {
    private static Context invnwuiContext;
    Resocs resources;
    Dbcluzz dbcluzz;
    Inventresoc inventresoc;
    Inventresoca inventresoca;
    DecimalFormat formatter = new DecimalFormat("#,###");

    public Inventnwedui(Context context) {
        invnwuiContext = context;

        resources = new Resocs(invnwuiContext);
        dbcluzz = new Dbcluzz(invnwuiContext);
        inventresoc = new Inventresoc(invnwuiContext);
        inventresoca = new Inventresoca(invnwuiContext);

    }

    /* New inventory UI */
    public void nwinVctuiload(ScrollView scrollView, ArrayList<Integer> idecs, double eunica, ProgressBar progbar,
                              String detocs, ArrayList<String> usrdetails, ArrayList<Integer> nwStokcontidec,
                              ArrayList<Integer> nwPrdctidec, ArrayList<Integer> nwBprcidec, ArrayList<Integer> nwSlprcidec,
                              ArrayList<Integer> nwQntyidec, LinearLayout inventnwhedcont) {
        String param = "nada";
        progbar.setIndeterminate(true);

        new Inventnwedui.invRecodsync(scrollView, idecs, eunica, progbar, detocs, usrdetails,
                nwStokcontidec, nwPrdctidec, nwBprcidec, nwSlprcidec, nwQntyidec, inventnwhedcont).execute(param);
    }

    public class invRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scrollViewpr;
        ArrayList<Integer> idecspr;
        double eunicapr;
        ProgressBar progbarpr;
        String detocspr;
        View usrLinear;
        ArrayList<String> usrdetailspr;
        ArrayList<Integer> nwStokcontidecpr;
        ArrayList<Integer> nwPrdctidecpr;
        ArrayList<Integer> nwBprcidecpr;
        ArrayList<Integer> nwSlprcidecpr;
        ArrayList<Integer> nwQntyidecpr;
        LinearLayout inventnwhedcontpr;
        FlexboxLayout invenwhedFlexbox;

        public invRecodsync(ScrollView scrollView, ArrayList<Integer> idecs, final double eunica, ProgressBar progbar,
                            String detocs, ArrayList<String> usrdetails, ArrayList<Integer> nwStokcontidec,
                            ArrayList<Integer> nwPrdctidec, ArrayList<Integer> nwBprcidec, ArrayList<Integer> nwSlprcidec,
                            ArrayList<Integer> nwQntyidec, LinearLayout inventnwhedcont) {
            this.scrollViewpr = scrollView;
            this.idecspr = idecs;
            this.eunicapr = eunica;
            this.progbarpr = progbar;
            this.detocspr = detocs;
            this.usrdetailspr = usrdetails;
            this.nwStokcontidecpr = nwStokcontidec;
            this.nwPrdctidecpr = nwPrdctidec;
            this.nwBprcidecpr = nwBprcidec;
            this.nwSlprcidecpr = nwSlprcidec;
            this.nwQntyidecpr = nwQntyidec;
            this.inventnwhedcontpr = inventnwhedcont;

            if (detocspr.equals("newStocks")) {
                LayoutInflater inflater = (LayoutInflater) invnwuiContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.invnwrecod, null);

            } else if (detocspr.equals("editStocks")) {
                LayoutInflater inflater = (LayoutInflater) invnwuiContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.invnwrecoda, null);

            }

        }

        @Override
        protected String doInBackground(String... param) {

            String uniqIdec = "";

            /* New and edit Products */
            if (detocspr.equals("newStocks") || detocspr.equals("editStocks")) {

                /* Different text view */
                if (detocspr.equals("newStocks")) {
                    /* Headers */
                    ArrayList<String> Titles = new ArrayList<>(Arrays.asList("Product", "Unit buying price", "Unit selling price",
                            "Quantity"));
                    ArrayList<Float> Widoth = new ArrayList<>(Arrays.asList(0.30f, 0.25f, 0.25f, 0.20f));

                    invenwhedFlexbox = new FlexboxLayout(invnwuiContext);
                  /*  invenwhedFlexbox.setDividerDrawable(invnwuiContext.getResources().getDrawable(R.drawable.flexcontdividera));
                    invenwhedFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
                    invenwhedFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);*/
                    invenwhedFlexbox.setBackgroundColor(invnwuiContext.getResources().getColor(R.color.bluua));

                    for (int ghas = 0; ghas < Titles.size(); ghas++) {
                        TextView header = new TextView(invnwuiContext);
                        header.setGravity(Gravity.CENTER);
                        header.setTextSize((float) eunicapr + 4.5f);
                        header.setTypeface(null, Typeface.BOLD);
                        header.setText(Titles.get(ghas));
                        header.setTextColor(Color.rgb(255, 255, 255));
                        header.setPadding(5, 10, 5, 10);

                        FlexboxLayout.LayoutParams hedlayoutParams = new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.MATCH_PARENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);

                        hedlayoutParams.setFlexBasisPercent(Widoth.get(ghas));
                        hedlayoutParams.setFlexGrow(1.0f);
                        invenwhedFlexbox.addView(header, hedlayoutParams);
                    }


                    /*  Set up font sizes */
                    ArrayList<Integer> txvewidec = new ArrayList<>();

                    /*Set textviews font sizes*/
                    TextView[] Txvewarry = new TextView[txvewidec.size()];
                    for (int k = 0; k < txvewidec.size(); ++k) {
                        (Txvewarry[k] = usrLinear.findViewById(txvewidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                    }

                    /*Add products*/
                    addStoks(eunicapr, (LinearLayout) usrLinear.findViewById(R.id.invnwgallinic), nwStokcontidecpr, nwPrdctidecpr,
                            nwBprcidecpr, nwSlprcidecpr, nwQntyidecpr);

                    /*  Add stocks */
                    usrLinear.findViewById(R.id.newinvaddbtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addStoks(eunicapr, (LinearLayout) usrLinear.findViewById(R.id.invnwgallinic), nwStokcontidecpr,
                                    nwPrdctidecpr, nwBprcidecpr, nwSlprcidecpr, nwQntyidecpr);

                        }
                    });

                    /*  Batch calculator */
                    usrLinear.findViewById(R.id.calcnvaddbtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inventresoca.invebatchCalc(eunicapr);

                        }
                    });

                }

                if (detocspr.equals("editStocks")) {
                    /* Initialize arraylists */
                    ArrayList<Integer> txvewidec = new ArrayList<>(Arrays.asList(R.id.invDtxtvew, R.id.invDtxtvewa, R.id.invDtxtvewb,
                            R.id.invDtxtvewc, R.id.invDtxtvewd, R.id.invDtxtvewe));
                    ArrayList<Integer> edtxtidec = new ArrayList<>(Arrays.asList(R.id.inveDitxt, R.id.inveDitxta, R.id.inveDitxtb));
                    ArrayList<Integer> btnidec = new ArrayList<>(Arrays.asList(R.id.invDbtn, R.id.invDbtna));

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

                    usrLinear.findViewById(R.id.invDbtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            resources.freexam(((Button) usrLinear.findViewById(R.id.invDbtn)));

                        }
                    });

                    /* Delete expense */
                    usrLinear.findViewById(R.id.invDbtna).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            resources.recdDeltdilog(detocspr, Inventory.invStatus.getText().toString(),
                                    ((TextView) usrLinear.findViewById(R.id.invnewuniqida)).getText().toString());

                        }
                    });


                    ((Button) usrLinear.findViewById(R.id.invDbtn)).setText(usrdetailspr.get(0));
                    ((TextView) usrLinear.findViewById(R.id.invDtxtvewb)).setText(usrdetailspr.get(1));
                    resources.spinMod(((TextView) usrLinear.findViewById(R.id.invDtxtvewb)), eunicapr,
                            resources.getallPRD(), "");
                    ((EditText) usrLinear.findViewById(R.id.inveDitxt)).setText(usrdetailspr.get(2));
                    ((EditText) usrLinear.findViewById(R.id.inveDitxta)).setText(usrdetailspr.get(3));
                    ((EditText) usrLinear.findViewById(R.id.inveDitxtb)).setText(usrdetailspr.get(4));

                    if (Inventory.invStatus.getText().toString().equals("Active")) {
                        ((Button) usrLinear.findViewById(R.id.invDbtna)).setText("Delete");

                    } else if (Inventory.invStatus.getText().toString().equals("Inactive")) {
                        ((Button) usrLinear.findViewById(R.id.invDbtna)).setText("Recover");

                    }

                    uniqIdec = usrdetailspr.get(5);
                    ((TextView) usrLinear.findViewById(R.id.invnewuniqida)).setText(uniqIdec);
                }

            }


            String response = "Cool";

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progbarpr.setIndeterminate(false);

            /* Add header */
            inventnwhedcontpr.removeAllViews();

            if (detocspr.equals("newStocks")) {
                inventnwhedcontpr.addView(invenwhedFlexbox, new LinearLayout.LayoutParams(
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

    /* Add stocks*/
    public void addStoks(final double eunicapr, final LinearLayout usrLinear, final ArrayList<Integer> nwPrdcontidec, final ArrayList<Integer> nwPrdctidec,
                         final ArrayList<Integer> nwPrccidec, final ArrayList<Integer> nwPrccideca, final ArrayList<Integer> nwQntyidec) {
        ArrayList<Integer> uniqid = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            uniqid.add(resources.generateViewId());
        }

        final FlexboxLayout usrFlexbox = new FlexboxLayout(invnwuiContext);
        usrFlexbox.setFlexDirection(FlexDirection.ROW);
        usrFlexbox.setAlignContent(AlignContent.FLEX_START);
        usrFlexbox.setAlignItems(AlignItems.BASELINE);
        usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
        usrFlexbox.setId(Math.abs(uniqid.get(0)));
        nwPrdcontidec.add(Math.abs(uniqid.get(0)));
        usrFlexbox.setPadding(0, 0, 0, 0);
        usrFlexbox.setDividerDrawable(invnwuiContext.getResources().getDrawable(R.drawable.flexcontdividera));
        usrFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
        usrFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
        usrFlexbox.setBackgroundColor(invnwuiContext.getResources().getColor(R.color.white));

        /*  Product */
        final TextView listBtnp = new TextView(invnwuiContext);
        listBtnp.setAllCaps(false);
        listBtnp.setGravity(Gravity.LEFT);
        listBtnp.setHint("Product");
        listBtnp.setId(Math.abs(uniqid.get(1)));
        nwPrdctidec.add(Math.abs(uniqid.get(1)));
        listBtnp.setTextSize((float) eunicapr + 5.0f);
        listBtnp.setBackgroundResource(R.color.trans);
        listBtnp.setPadding(16, 36, 10, 10);
        listBtnp.setTextColor(Color.rgb(77, 77, 77));
        resources.spinMod(listBtnp, eunicapr, resources.getallPRD(), "");

        /*  Buying price */
        final EditText listBtn = new EditText(invnwuiContext);
        listBtn.setAllCaps(false);
        listBtn.setGravity(Gravity.CENTER);
        listBtn.setHint("0");
        listBtn.setId(Math.abs(uniqid.get(2)));
        nwPrccidec.add(Math.abs(uniqid.get(2)));
        listBtn.setTextSize((float) eunicapr + 5.0f);
        listBtn.setBackgroundResource(R.color.trans);
        listBtn.setPadding(16, 36, 10, 10);
        listBtn.setTextColor(Color.rgb(77, 77, 77));

        /*  Selling price */
        final EditText klistBtn = new EditText(invnwuiContext);
        klistBtn.setAllCaps(false);
        klistBtn.setGravity(Gravity.CENTER);
        klistBtn.setHint("0");
        klistBtn.setId(Math.abs(uniqid.get(3)));
        nwPrccideca.add(Math.abs(uniqid.get(3)));
        klistBtn.setTextSize((float) eunicapr + 5.0f);
        klistBtn.setBackgroundResource(R.color.trans);
        klistBtn.setPadding(16, 36, 10, 10);
        klistBtn.setTextColor(Color.rgb(77, 77, 77));


        /*  Quantity */
        final EditText listBtna = new EditText(invnwuiContext);
        listBtna.setAllCaps(false);
        listBtna.setHint("0");
        listBtna.setGravity(Gravity.CENTER);
        listBtna.setId(Math.abs(uniqid.get(4)));
        nwQntyidec.add(Math.abs(uniqid.get(4)));
        listBtna.setTextSize((float) eunicapr + 5.0f);
        listBtna.setBackgroundResource(R.color.trans);
        listBtna.setPadding(16, 36, 10, 10);
        listBtna.setTextColor(Color.rgb(77, 77, 77));

        /*  Action Listeners */
        /*  Product  long press action listener */
        listBtnp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                resources.remvinvRow(usrLinear, usrFlexbox, nwPrdcontidec, nwPrdctidec, nwPrccidec, nwPrccideca,
                        nwQntyidec, usrFlexbox.getId(), listBtnp.getId(), listBtn.getId(), klistBtn.getId(),
                        listBtna.getId());

                return true;
            }
        });

        FlexboxLayout.LayoutParams lfayoutParams = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.MATCH_PARENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT);
        lfayoutParams.setFlexBasisPercent(0.4f);
        lfayoutParams.setFlexGrow(1f);

        FlexboxLayout.LayoutParams lfayoutParamsa = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.MATCH_PARENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT);
        lfayoutParamsa.setFlexBasisPercent(0.3f);
        lfayoutParamsa.setFlexGrow(1f);

        usrFlexbox.addView(listBtnp, lfayoutParams);
        usrFlexbox.addView(listBtn, lfayoutParamsa);
        usrFlexbox.addView(klistBtn, lfayoutParamsa);
        usrFlexbox.addView(listBtna, lfayoutParamsa);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        usrLinear.addView(usrFlexbox, layoutParams);

    }

}
