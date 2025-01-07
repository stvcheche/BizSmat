package com.avintangu.bizsmat.sales;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
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

public class Salenewui {
    private static Context salnewContext;
    Resocs resources;
    Salesresocs salesresocs;
    Dbcluzz dbcluzz;
    DecimalFormat formatter = new DecimalFormat("#,###");

    public Salenewui(Context context) {
        salnewContext = context;

        resources = new Resocs(salnewContext);
        salesresocs = new Salesresocs(salnewContext);
        dbcluzz = new Dbcluzz(salnewContext);

    }

    /* New products UI */
    public void nwSalctuiload(ScrollView scrollView, ArrayList<Integer> idecs, double eunica, ProgressBar progbar,
                              String detocs, ArrayList<String> usrdetails, ArrayList<Integer> nwPrdcontidec,
                              ArrayList<Integer> nwPrdctidec, ArrayList<Integer> nwPrccidec, ArrayList<Integer> nwQntyidec,
                              LinearLayout salenwhedcont) {
        String param = "nada";
        progbar.setIndeterminate(true);

        new Salenewui.salRecodsync(scrollView, idecs, eunica, progbar, detocs, usrdetails,
                nwPrdcontidec, nwPrdctidec, nwPrccidec, nwQntyidec, salenwhedcont).execute(param);
    }

    public class salRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scrollViewpr;
        ArrayList<Integer> idecspr;
        double eunicapr;
        ProgressBar progbarpr;
        String detocspr;
        View usrLinear;
        ArrayList<String> usrdetailspr;
        ArrayList<Integer> nwPrdcontidecpr;
        ArrayList<Integer> nwPrdctidecpr;
        ArrayList<Integer> nwPrccidecpr;
        ArrayList<Integer> nwQntyidecpr;
        LinearLayout salenwhedcontpr;
        FlexboxLayout salnwhedflex;

        public salRecodsync(ScrollView scrollView, ArrayList<Integer> idecs, final double eunica, ProgressBar progbar,
                            String detocs, ArrayList<String> usrdetails, ArrayList<Integer> nwPrdcontidec,
                            ArrayList<Integer> nwPrdctidec, ArrayList<Integer> nwPrccidec, ArrayList<Integer> nwQntyidec,
                            LinearLayout salenwhedcont) {
            this.scrollViewpr = scrollView;
            this.idecspr = idecs;
            this.eunicapr = eunica;
            this.progbarpr = progbar;
            this.detocspr = detocs;
            this.usrdetailspr = usrdetails;
            this.nwPrdcontidecpr = nwPrdcontidec;
            this.nwPrdctidecpr = nwPrdctidec;
            this.nwPrccidecpr = nwPrccidec;
            this.nwQntyidecpr = nwQntyidec;
            this.salenwhedcontpr = salenwhedcont;

            if (detocspr.equals("newSale")) {
                LayoutInflater inflater = (LayoutInflater) salnewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.salnwedrecod, null);

            } else if (detocspr.equals("editSale")) {
                LayoutInflater inflater = (LayoutInflater) salnewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.salnwedrecoda, null);

            }

        }

        @Override
        protected String doInBackground(String... param) {

            String uniqIdec = "";

            /* New and edit Products */
            if (detocspr.equals("newSale") || detocspr.equals("editSale")) {

                /* Different text view */
                if (detocspr.equals("newSale")) {
                    /* Headers */
                    ArrayList<String> Titles = new ArrayList<>(Arrays.asList("Product", "Price", "Quantity", "Total"));
                    ArrayList<Float> Widoth = new ArrayList<>(Arrays.asList(0.40f, 0.2f, 0.2f, 0.2f));

                    salnwhedflex = new FlexboxLayout(salnewContext);
                  /*  invenwhedFlexbox.setDividerDrawable(invnwuiContext.getResources().getDrawable(R.drawable.flexcontdividera));
                    invenwhedFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
                    invenwhedFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);*/
                    salnwhedflex.setBackgroundColor(salnewContext.getResources().getColor(R.color.bluua));

                    for (int ghas = 0; ghas < Titles.size(); ghas++) {
                        TextView header = new TextView(salnewContext);
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
                        salnwhedflex.addView(header, hedlayoutParams);
                    }

                    usrLinear.findViewById(R.id.newsaladdbtn).setVisibility(View.VISIBLE);

                    /*  Set up font sizes */
                    ArrayList<Integer> txvewidec = new ArrayList<>(Arrays.asList(R.id.salenwrecodtxtc, R.id.salenwrecodtxtd,
                            R.id.salenwrecodtxte, R.id.salenwrecodtxtf, R.id.salenwrecodtxtg));
                    ArrayList<Integer> edtxtidec = new ArrayList<>(Arrays.asList(R.id.salenwDitxt));

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

                    /*Add products*/
                    addPrdcts(eunicapr, (LinearLayout) usrLinear.findViewById(R.id.salenwgallinic), nwPrdcontidecpr, nwPrdctidecpr,
                            nwPrccidecpr, nwQntyidecpr, (TextView) usrLinear.findViewById(R.id.salenwrecodtxtd));

                    /*  Calculate change on amount paid change*/
                    ((EditText) usrLinear.findViewById(R.id.salenwDitxt)).addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            Ckcalc(((EditText) usrLinear.findViewById(R.id.salenwDitxt)), ((TextView) usrLinear.findViewById(R.id.salenwrecodtxtd)),
                                    ((TextView) usrLinear.findViewById(R.id.salenwrecodtxtg)));
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });

                    /*  Calculate change on total cost change*/
                    ((TextView) usrLinear.findViewById(R.id.salenwrecodtxtd)).addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            Ckcalc(((EditText) usrLinear.findViewById(R.id.salenwDitxt)), ((TextView) usrLinear.findViewById(R.id.salenwrecodtxtd)),
                                    ((TextView) usrLinear.findViewById(R.id.salenwrecodtxtg)));
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });

                    /* Add products */
                    usrLinear.findViewById(R.id.newsaladdbtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addPrdcts(eunicapr, (LinearLayout) usrLinear.findViewById(R.id.salenwgallinic), nwPrdcontidecpr,
                                    nwPrdctidecpr, nwPrccidecpr, nwQntyidecpr, (TextView) usrLinear.findViewById(R.id.salenwrecodtxtd));


                        }
                    });

                }

                if (detocspr.equals("editSale")) {
                    /* Initialize arraylists */
                    ArrayList<Integer> txvewidec = new ArrayList<>(Arrays.asList(R.id.saleDtxtvew, R.id.saleDtxtvewa, R.id.saleDtxtvewb,
                            R.id.saleDtxtvewc, R.id.saleDtxtvewd, R.id.saleDtxtvewe, R.id.saleDtxtvewf));
                    ArrayList<Integer> edtxtidec = new ArrayList<>(Arrays.asList(R.id.saleDitxt, R.id.saleDitxta));
                    ArrayList<Integer> btnidec = new ArrayList<>(Arrays.asList(R.id.saleDbtn, R.id.saleDbtna));

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

                    usrLinear.findViewById(R.id.saleDbtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            resources.freexam(((Button) usrLinear.findViewById(R.id.saleDbtn)));

                        }
                    });

                    /* Delete expense */
                    usrLinear.findViewById(R.id.saleDbtna).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            resources.recdDeltdilog(detocspr, Sales.salStatus.getText().toString(),
                                    ((TextView) usrLinear.findViewById(R.id.salenewuniqida)).getText().toString());


                        }
                    });


                    ((Button) usrLinear.findViewById(R.id.saleDbtn)).setText(usrdetailspr.get(0));
                    ((TextView) usrLinear.findViewById(R.id.saleDtxtvewb)).setText(usrdetailspr.get(1));
                    resources.spinMod(((TextView) usrLinear.findViewById(R.id.saleDtxtvewb)), eunicapr,
                            resources.getallPRD(), "");
                    ((EditText) usrLinear.findViewById(R.id.saleDitxt)).setText(usrdetailspr.get(2));
                    ((EditText) usrLinear.findViewById(R.id.saleDitxta)).setText(usrdetailspr.get(3));
                    ((TextView) usrLinear.findViewById(R.id.saleDtxtvewf)).setText(usrdetailspr.get(4));

                    if (Sales.salStatus.getText().toString().equals("Active")) {
                        ((Button) usrLinear.findViewById(R.id.saleDbtna)).setText("Delete");

                    } else if (Sales.salStatus.getText().toString().equals("Inactive")) {
                        ((Button) usrLinear.findViewById(R.id.saleDbtna)).setText("Recover");

                    }

                    uniqIdec = usrdetailspr.get(5);
                    ((TextView) usrLinear.findViewById(R.id.salenewuniqida)).setText(uniqIdec);
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
            salenwhedcontpr.removeAllViews();

            if (detocspr.equals("newSale")) {
                salenwhedcontpr.addView(salnwhedflex, new LinearLayout.LayoutParams(
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

    /* Add products*/
    public void addPrdcts(final double eunicapr, final LinearLayout usrLinear, final ArrayList<Integer> nwPrdcontidec,
                          final ArrayList<Integer> nwPrdctidec, final ArrayList<Integer> nwPrccidec, final ArrayList<Integer> nwQntyidec,
                          final TextView trelis) {
        ArrayList<Integer> uniqid = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            uniqid.add(resources.generateViewId());
        }

        final FlexboxLayout usrFlexbox = new FlexboxLayout(salnewContext);
        usrFlexbox.setFlexDirection(FlexDirection.ROW);
        usrFlexbox.setAlignContent(AlignContent.FLEX_START);
        usrFlexbox.setAlignItems(AlignItems.BASELINE);
        usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
        usrFlexbox.setId(Math.abs(uniqid.get(0)));
        nwPrdcontidec.add(Math.abs(uniqid.get(0)));
        usrFlexbox.setPadding(0, 0, 0, 0);
        usrFlexbox.setDividerDrawable(salnewContext.getResources().getDrawable(R.drawable.flexcontdividera));
        usrFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
        usrFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
        usrFlexbox.setBackgroundColor(salnewContext.getResources().getColor(R.color.white));

        /*  Product */
        final TextView listBtnp = new TextView(salnewContext);
        listBtnp.setAllCaps(false);
        listBtnp.setGravity(Gravity.LEFT);
        listBtnp.setHint("Product");
        listBtnp.setId(Math.abs(uniqid.get(1)));
        nwPrdctidec.add(Math.abs(uniqid.get(1)));
        listBtnp.setTextSize((float) eunicapr + 5.0f);
        listBtnp.setBackgroundResource(R.color.trans);
        listBtnp.setPadding(16, 36, 10, 10);
        listBtnp.setTextColor(Color.rgb(77, 77, 77));
        resources.spinMod(listBtnp, eunicapr, resources.getallPRD(), "kap");


        /*  Price */
        final TextView listBtn = new TextView(salnewContext);
        listBtn.setAllCaps(false);
        listBtn.setGravity(Gravity.CENTER);
        listBtn.setHint("0");
        listBtn.setId(Math.abs(uniqid.get(2)));
        nwPrccidec.add(Math.abs(uniqid.get(2)));
        listBtn.setTextSize((float) eunicapr + 5.0f);
        listBtn.setBackgroundResource(R.color.trans);
        listBtn.setPadding(16, 36, 10, 10);
        listBtn.setTextColor(Color.rgb(77, 77, 77));


        /*  Quantity */
        final EditText listBtna = new EditText(salnewContext);
        listBtna.setAllCaps(false);
        listBtna.setHint("0");
        listBtna.setGravity(Gravity.CENTER);
        listBtna.setId(Math.abs(uniqid.get(3)));
        nwQntyidec.add(Math.abs(uniqid.get(3)));
        listBtna.setTextSize((float) eunicapr + 5.0f);
        listBtna.setBackgroundResource(R.color.trans);
        listBtna.setPadding(16, 36, 10, 10);
        listBtna.setTextColor(Color.rgb(77, 77, 77));

        /*  Total */
        final TextView listBtnb = new TextView(salnewContext);
        listBtnb.setAllCaps(false);
        listBtnb.setHint("0");
        listBtnb.setGravity(Gravity.CENTER);
        listBtnb.setId(Math.abs(uniqid.get(4)));
        listBtnb.setTextSize((float) eunicapr + 5.0f);
        listBtnb.setBackgroundResource(R.color.trans);
        listBtnb.setPadding(16, 36, 10, 10);
        listBtnb.setTextColor(Color.rgb(77, 77, 77));

        /*  Action Listeners
         *  Set price */
        listBtnp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listBtn.setText(formatter.format(resources.retuxPrdprcc(listBtnp.getText().toString())));

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        /*  Product  long press action listener */
        listBtnp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                resources.remvRow(usrLinear, usrFlexbox, nwPrdcontidec, nwPrdctidec, nwPrccidec,
                        nwQntyidec, usrFlexbox.getId(), listBtnp.getId(), listBtn.getId(), listBtna.getId(),
                        usrLinear, trelis);

                return true;
            }
        });

        /*  Price text change action listener */
        listBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listBtnb.setText(formatter.format(salrwtrelisCalc(usrLinear, listBtn, listBtna)));
                trelis.setText(formatter.format(trelisCalc(usrLinear, nwPrccidec, nwQntyidec)));

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        /*  Quantity on text change action listener */
        listBtna.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listBtnb.setText(formatter.format(salrwtrelisCalc(usrLinear, listBtn, listBtna)));
                trelis.setText(formatter.format(trelisCalc(usrLinear, nwPrccidec, nwQntyidec)));

            }

            @Override
            public void afterTextChanged(Editable editable) {
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
        lfayoutParamsa.setFlexBasisPercent(0.2f);
        lfayoutParamsa.setFlexGrow(1f);

        usrFlexbox.addView(listBtnp, lfayoutParams);
        usrFlexbox.addView(listBtn, lfayoutParamsa);
        usrFlexbox.addView(listBtna, lfayoutParamsa);
        usrFlexbox.addView(listBtnb, lfayoutParamsa);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        usrLinear.addView(usrFlexbox, layoutParams);

    }

    /*  Get row  total */
    public Double salrwtrelisCalc(LinearLayout usrLinear, TextView prcctxtvew, EditText qntyvew) {
        Double total = 0.0;
        if (!prcctxtvew.getText().toString().replaceAll("[^.0-9]", "").equals("") &&
                !qntyvew.getText().toString().replaceAll("[^.0-9]", "").equals("")) {
            total = Double.valueOf(prcctxtvew.getText().toString().replaceAll("[^.0-9]", "")) *
                    Double.valueOf(qntyvew.getText().toString().replaceAll("[^.0-9]", ""));

        }

        return total;
    }

    /*  Get  total cost */
    public Double trelisCalc(LinearLayout usrLinear, ArrayList<Integer> prccidec, ArrayList<Integer> qntyidec) {
        Double total = 0.0;

        for (int kem = 0; kem < prccidec.size(); kem++) {
            if (!((TextView) usrLinear.findViewById(prccidec.get(kem))).getText().toString().replaceAll("[^.0-9]", "").equals("") &&
                    !((EditText) usrLinear.findViewById(qntyidec.get(kem))).getText().toString().replaceAll("[^.0-9]", "").equals("")) {
                total += Double.valueOf(((TextView) usrLinear.findViewById(prccidec.get(kem))).getText().toString().replaceAll("[^.0-9]", "")) *
                        Double.valueOf(((EditText) usrLinear.findViewById(qntyidec.get(kem))).getText().toString().replaceAll("[^.0-9]", ""));

            }
        }

        return total;
    }

    /*  Calculate change */
    public void Ckcalc(final EditText ampaid, final TextView trelis, final TextView chxtvew) {
        Double change = 0.0;

        if (!(ampaid.getText().toString().replaceAll("[^.0-9]", "").equals("")) &&
                !(trelis.getText().toString().replaceAll("[^.0-9]", "").equals(""))) {
            change = Double.valueOf(ampaid.getText().toString().replaceAll("[^.0-9]", "")) -
                    Double.valueOf(trelis.getText().toString().replaceAll("[^.0-9]", ""));

        }

        chxtvew.setText(formatter.format(change));
    }

}
