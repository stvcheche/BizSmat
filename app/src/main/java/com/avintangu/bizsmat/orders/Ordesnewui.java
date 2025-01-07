package com.avintangu.bizsmat.orders;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.Toast;

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

public class Ordesnewui {
    private static Context ordnwuiContext;
    Resocs resources;
    Dbcluzz dbcluzz;
    Ordersresoc ordersresoc;
    DecimalFormat formatter = new DecimalFormat("#,###");
    View usrLinear;
    Integer prdkes;

    public Ordesnewui(Context context) {
        ordnwuiContext = context;
        prdkes = 0;

        resources = new Resocs(ordnwuiContext);
        dbcluzz = new Dbcluzz(ordnwuiContext);
        ordersresoc = new Ordersresoc(ordnwuiContext);

    }

    /* New order UI */
    public void nwOrduiload(ScrollView scrollView, ArrayList<Integer> idecs, double eunica, ProgressBar progbar,
                            String detocs, ArrayList obj, ArrayList<Integer> nwPrdcontidec,
                            ArrayList<Integer> nwPrdctidec, ArrayList<Integer> nwPrccidec,
                            ArrayList<Integer> nwQntyidec, ArrayList<String> nwUniqidec) {
        String param = "nada";
        progbar.setIndeterminate(true);

        new Ordesnewui.ordRecodsync(scrollView, idecs, eunica, progbar, detocs, obj,
                nwPrdcontidec, nwPrdctidec, nwPrccidec, nwQntyidec, nwUniqidec).execute(param);
    }

    public class ordRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scrollViewpr;
        ArrayList<Integer> idecspr;
        double eunicapr;
        ProgressBar progbarpr;
        String detocspr;

        ArrayList objpr;

        ArrayList<Integer> nwPrdcontidecpr;
        ArrayList<Integer> nwPrdctidecpr;
        ArrayList<Integer> nwPrccidecpr;
        ArrayList<Integer> nwQntyidecpr;
        ArrayList<String> nwUniqidecpr;

        public ordRecodsync(ScrollView scrollView, ArrayList<Integer> idecs, final double eunica, ProgressBar progbar,
                            String detocs, ArrayList obj, ArrayList<Integer> nwPrdcontidec,
                            ArrayList<Integer> nwPrdctidec, ArrayList<Integer> nwPrccidec,
                            ArrayList<Integer> nwQntyidec, ArrayList<String> nwUniqidec) {
            this.scrollViewpr = scrollView;
            this.idecspr = idecs;
            this.eunicapr = eunica;
            this.progbarpr = progbar;
            this.detocspr = detocs;
            this.objpr = obj;
            this.nwPrdcontidecpr = nwPrdcontidec;
            this.nwPrdctidecpr = nwPrdctidec;
            this.nwPrccidecpr = nwPrccidec;
            this.nwQntyidecpr = nwQntyidec;
            this.nwUniqidecpr = nwUniqidec;

            if (detocspr.equals("newProduct") || detocspr.equals("editProduct")) {

            } else if (detocspr.equals("newOrder") || detocspr.equals("editord")) {
                LayoutInflater inflater = (LayoutInflater) ordnwuiContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                usrLinear = inflater.inflate(R.layout.ordesnworder, null);
            }

        }

        @Override
        protected String doInBackground(String... param) {

            String uniqIdec = resources.uniqueGen().toString();
            String ordsapprv = "";
            String ordstekcs = "";
            String ordsreceipt = "";

            /* New and edit Products */
            if (detocspr.equals("newProduct") || detocspr.equals("editProduct")) {

                /* Create new and edit products categories*/
            } else if (detocspr.equals("newOrder") || detocspr.equals("editord")) {
                /* Initialize arraylists */
                ArrayList<Integer> txvewidec;
                ArrayList<Integer> edtxtidec;
                ArrayList<Integer> btnidec;

                txvewidec = new ArrayList<>(Arrays.asList(R.id.ordnwtxtrf, R.id.ordnwtxtrfa, R.id.ordnwtxtrfb, R.id.ordnwtxtrfc,
                        R.id.ordnwtxtrfd, R.id.ordnwtxtrfe, R.id.ordnwtxtrff, R.id.ordnwtxtrfg, R.id.ordnwtxtrfh, R.id.ordnwtxtrfi,
                        R.id.ordnwtxtrfj, R.id.ordnwtxtrfk, R.id.ordnwtxtrfka, R.id.trelistcosttxt, R.id.trelistcosttxt));
                edtxtidec = new ArrayList<>(Arrays.asList(R.id.ordnwedtxt));
                btnidec = new ArrayList<>(Arrays.asList(R.id.ordnwbtnres, R.id.ordnwbtnresa, R.id.ordnwbtnresb, R.id.ordnwbtnresc));


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

                /* Get outlets */
                resources.spinMod(((TextView) usrLinear.findViewById(R.id.ordnwtxtrfc)), eunicapr,
                        resources.getAllshopsa(), "");

                /* Get distributors */
                resources.spinMod(((TextView) usrLinear.findViewById(R.id.ordnwtxtrfe)), eunicapr,
                        resources.getAlldisctros(), "");

                /* Get payment modes */
                resources.spinMod(((TextView) usrLinear.findViewById(R.id.ordnwtxtrfg)), eunicapr,
                        resources.retPayment(), "");

                /*Add products*/
                ((TextView) usrLinear.findViewById(R.id.ordnwtxtrfe)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        nwPrdcontidecpr.clear();
                        nwPrdctidecpr.clear();
                        nwPrccidecpr.clear();
                        nwQntyidecpr.clear();
                        nwUniqidecpr.clear();

                        ((LinearLayout) usrLinear.findViewById(R.id.ordnwgallinic)).removeAllViews();
                        addPrdcts(eunicapr, (LinearLayout) usrLinear.findViewById(R.id.ordnwgallinic), nwPrdcontidecpr, nwPrdctidecpr,
                                nwPrccidecpr, nwQntyidecpr, (TextView) usrLinear.findViewById(R.id.ordnwtxtrfe),
                                nwUniqidecpr, (TextView) usrLinear.findViewById(R.id.trelistcosttxta));

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });

                /* Add products */
                usrLinear.findViewById(R.id.newordaddbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPrdcts(eunicapr, (LinearLayout) usrLinear.findViewById(R.id.ordnwgallinic), nwPrdcontidecpr,
                                nwPrdctidecpr, nwPrccidecpr, nwQntyidecpr, (TextView) usrLinear.findViewById(R.id.ordnwtxtrfe),
                                nwUniqidecpr, (TextView) usrLinear.findViewById(R.id.trelistcosttxta));


                    }
                });

                /* Different text view */
                if (detocspr.equals("newOrder")) {
                    usrLinear.findViewById(R.id.ordnwbtnresb).setVisibility(View.GONE);
                    usrLinear.findViewById(R.id.ordnwbtnresc).setVisibility(View.GONE);
                    ArrayList<String> datecs = resources.retStrdate();

                    ((Button) usrLinear.findViewById(R.id.ordnwbtnres)).setText(datecs.get(6));
                    ((Button) usrLinear.findViewById(R.id.ordnwbtnresa)).setText(datecs.get(8));
                    ((TextView) usrLinear.findViewById(R.id.ordnwtxtrfc)).setText("Select");
                    ((TextView) usrLinear.findViewById(R.id.ordnwtxtrfe)).setText("Select");
                    ((TextView) usrLinear.findViewById(R.id.ordnwtxtrfg)).setText("Select");

                    /* Set date */
                    usrLinear.findViewById(R.id.ordnwbtnres).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            resources.freexam(((Button) usrLinear.findViewById(R.id.ordnwbtnres)));

                        }
                    });

                    /* New product code */
                    String usrcode = dbcluzz.getUsrdetal().get(3).replaceAll("[^a-zA-Z0-9]", "");
                    StringBuilder sb = new StringBuilder();
                    sb.append(usrcode + "ORDESS" + uniqIdec + "CODE");
                    String prdctcode = String.valueOf(sb);

                    uniqIdec = prdctcode;
                    ordsapprv = "NA";
                    ordstekcs = "Pending";
                    ordsreceipt = "Pending";

                }

                /* Set data */
                if (detocspr.equals("editord")) {
                    ArrayList<String> ordetails = (ArrayList<String>) objpr.get(0);
                    ArrayList<ArrayList<String>> orderprdct = (ArrayList<ArrayList<String>>) objpr.get(1);

                    ((Button) usrLinear.findViewById(R.id.ordnwbtnres)).setText(ordetails.get(0));
                    ((Button) usrLinear.findViewById(R.id.ordnwbtnresa)).setText(ordetails.get(1));
                    ((TextView) usrLinear.findViewById(R.id.ordnwtxtrfc)).setText(ordetails.get(2));
                    ((TextView) usrLinear.findViewById(R.id.ordnwtxtrfe)).setText(ordetails.get(3));
                    ((TextView) usrLinear.findViewById(R.id.ordnwtxtrfg)).setText(ordetails.get(4));
                    ((EditText) usrLinear.findViewById(R.id.ordnwedtxt)).setText(ordetails.get(5));


                    if (Orders.ordStatus.getText().toString().equals("Active")) {
                        ((Button) usrLinear.findViewById(R.id.ordnwbtnresb)).setText("Cancel");

                    } else if (Orders.ordStatus.getText().toString().equals("Inactive")) {
                        ((Button) usrLinear.findViewById(R.id.ordnwbtnresb)).setText("Recover");

                    }
                    uniqIdec = ordetails.get(6);
                    ordsapprv = ordetails.get(7);
                    ordstekcs = ordetails.get(8);
                    ordsreceipt = ordetails.get(9);

                    nwPrdcontidecpr.clear();
                    nwPrdctidecpr.clear();
                    nwPrccidecpr.clear();
                    nwQntyidecpr.clear();
                    nwUniqidecpr.clear();
                    ((LinearLayout) usrLinear.findViewById(R.id.ordnwgallinic)).removeAllViews();

                    for (int yaw = 0; yaw < orderprdct.get(0).size(); yaw++) {
                        addPrdctsa(eunicapr, (LinearLayout) usrLinear.findViewById(R.id.ordnwgallinic), nwPrdcontidecpr, nwPrdctidecpr,
                                nwPrccidecpr, nwQntyidecpr, (TextView) usrLinear.findViewById(R.id.ordnwtxtrfe), orderprdct.get(0).get(yaw),
                                orderprdct.get(1).get(yaw), orderprdct.get(2).get(yaw), nwUniqidecpr, orderprdct.get(3).get(yaw),
                                (TextView) usrLinear.findViewById(R.id.trelistcosttxta));
                    }

                    ((TextView) usrLinear.findViewById(R.id.trelistcosttxta)).setText(formatter.format(ordtrelisCalc((LinearLayout) usrLinear.findViewById(R.id.ordnwgallinic)
                            , nwPrccidecpr, nwQntyidecpr)));
                }

                ((TextView) usrLinear.findViewById(R.id.ordnewuniqid)).setText(uniqIdec);
                ((TextView) usrLinear.findViewById(R.id.ordapprvidec)).setText(ordsapprv);
                ((TextView) usrLinear.findViewById(R.id.ordstateidec)).setText(ordstekcs);
                ((TextView) usrLinear.findViewById(R.id.ordsreceipidec)).setText(ordsreceipt);

                /* Receive order */
                final String finalUniqIdec = uniqIdec;
                usrLinear.findViewById(R.id.ordnwbtnresc).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ordersresoc.receiveRecd("ordesdetal", "ordetalrecipt", "ordetaluniq", finalUniqIdec);
                        Toast.makeText(ordnwuiContext, "Order received", Toast.LENGTH_LONG).show();

                    }
                });

                /* Delete order */
                usrLinear.findViewById(R.id.ordnwbtnresb).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((TextView) usrLinear.findViewById(R.id.ordstateidec)).setText("Cancelled");
                        resources.recdDeltdilog(detocspr, "Pending",
                                ((TextView) usrLinear.findViewById(R.id.ordnewuniqid)).getText().toString());


                    }
                });

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

    /* Add products*/
    public void addPrdcts(final double eunicapr, final LinearLayout usrLineara, final ArrayList<Integer> nwPrdcontidec,
                          final ArrayList<Integer> nwPrdctidec, final ArrayList<Integer> nwPrccidec, final ArrayList<Integer> nwQntyidec,
                          final TextView distros, final ArrayList<String> nwUniqidec, final TextView trelis) {
        ArrayList<Integer> uniqid = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            uniqid.add(resources.generateViewId());
        }

        prdkes += prdkes + 1;
        String usrcode = dbcluzz.getUsrdetal().get(3).replaceAll("[^a-zA-Z0-9]", "");
        String uniqcode = resources.instPrdctcd(usrcode + "PRDCTCODE" + resources.uniqueGen(), prdkes);
        nwUniqidec.add(uniqcode);

        final FlexboxLayout usrFlexbox = new FlexboxLayout(ordnwuiContext);
        usrFlexbox.setFlexDirection(FlexDirection.ROW);
        usrFlexbox.setAlignContent(AlignContent.FLEX_START);
        usrFlexbox.setAlignItems(AlignItems.BASELINE);
        usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
        usrFlexbox.setId(Math.abs(uniqid.get(0)));
        nwPrdcontidec.add(Math.abs(uniqid.get(0)));
        usrFlexbox.setPadding(0, 0, 0, 0);
        usrFlexbox.setDividerDrawable(ordnwuiContext.getResources().getDrawable(R.drawable.flexcontdividera));
        usrFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
        usrFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
        usrFlexbox.setBackgroundColor(ordnwuiContext.getResources().getColor(R.color.white));

        /*  Product */
        final TextView listBtnp = new TextView(ordnwuiContext);
        listBtnp.setAllCaps(false);
        listBtnp.setGravity(Gravity.LEFT);
        listBtnp.setHint("Product");
        listBtnp.setId(Math.abs(uniqid.get(1)));
        nwPrdctidec.add(Math.abs(uniqid.get(1)));
        listBtnp.setTextSize((float) eunicapr + 5.0f);
        listBtnp.setBackgroundResource(R.color.trans);
        listBtnp.setPadding(16, 36, 10, 10);
        listBtnp.setTextColor(Color.rgb(77, 77, 77));
        resources.spinMod(listBtnp, eunicapr, resources.retuxPrdbdist(distros.getText().toString()), "ordeskes");


        /*  Price */
        final TextView listBtn = new TextView(ordnwuiContext);
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
        final EditText listBtna = new EditText(ordnwuiContext);
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
        final TextView listBtnb = new TextView(ordnwuiContext);
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
                if (!distros.getText().toString().equals("Select")) {
                    listBtn.setText(formatter.format(resources.retuxPrdprcca(listBtnp.getText().toString(),
                            distros.getText().toString())));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        /*  Product  long press action listener */
        listBtnp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                resources.remvOrdrows(usrLineara, usrFlexbox, nwPrdcontidec, nwPrdctidec, nwPrccidec,
                        nwQntyidec, usrFlexbox.getId(), listBtnp.getId(), listBtn.getId(), listBtna.getId(),
                        nwUniqidec, trelis);

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
                listBtnb.setText(formatter.format(ordrwtrelisCalc(usrLineara, listBtn, listBtna)));
                trelis.setText(formatter.format(ordtrelisCalc(usrLineara, nwPrccidec, nwQntyidec)));

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
                listBtnb.setText(formatter.format(ordrwtrelisCalc(usrLineara, listBtn, listBtna)));
                trelis.setText(formatter.format(ordtrelisCalc(usrLineara, nwPrccidec, nwQntyidec)));

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
        usrLineara.addView(usrFlexbox, layoutParams);

    }

    /* Add products*/
    public void addPrdctsa(final double eunicapr, final LinearLayout usrLineara, final ArrayList<Integer> nwPrdcontidec,
                           final ArrayList<Integer> nwPrdctidec, final ArrayList<Integer> nwPrccidec, final ArrayList<Integer> nwQntyidec,
                           final TextView distros, final String product, final String price, final String quantity,
                           final ArrayList<String> nwUniqidec, String kuniqcode, final TextView trelis) {
        ArrayList<Integer> uniqid = new ArrayList<>();

        for (int i = 0; i < 7; ++i) {
            uniqid.add(resources.generateViewId());
        }

        nwUniqidec.add(kuniqcode);

        final FlexboxLayout usrFlexbox = new FlexboxLayout(ordnwuiContext);
        usrFlexbox.setFlexDirection(FlexDirection.ROW);
        usrFlexbox.setAlignContent(AlignContent.FLEX_START);
        usrFlexbox.setAlignItems(AlignItems.BASELINE);
        usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
        usrFlexbox.setId(Math.abs(uniqid.get(0)));
        nwPrdcontidec.add(Math.abs(uniqid.get(0)));
        usrFlexbox.setPadding(0, 0, 0, 0);
        usrFlexbox.setDividerDrawable(ordnwuiContext.getResources().getDrawable(R.drawable.flexcontdividera));
        usrFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
        usrFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
        usrFlexbox.setBackgroundColor(ordnwuiContext.getResources().getColor(R.color.white));

        /*  Product */
        final TextView listBtnp = new TextView(ordnwuiContext);
        listBtnp.setAllCaps(false);
        listBtnp.setGravity(Gravity.LEFT);
        listBtnp.setText(product);
        listBtnp.setId(Math.abs(uniqid.get(1)));
        nwPrdctidec.add(Math.abs(uniqid.get(1)));
        listBtnp.setTextSize((float) eunicapr + 5.0f);
        listBtnp.setBackgroundResource(R.color.trans);
        listBtnp.setPadding(16, 36, 10, 10);
        listBtnp.setTextColor(Color.rgb(77, 77, 77));
        resources.spinMod(listBtnp, eunicapr, resources.retuxPrdbdist(distros.getText().toString()), "");


        /*  Price */
        final TextView listBtn = new EditText(ordnwuiContext);
        listBtn.setAllCaps(false);
        listBtn.setGravity(Gravity.CENTER);
        listBtn.setText(price);
        listBtn.setId(Math.abs(uniqid.get(2)));
        nwPrccidec.add(Math.abs(uniqid.get(2)));
        listBtn.setTextSize((float) eunicapr + 5.0f);
        listBtn.setBackgroundResource(R.color.trans);
        listBtn.setPadding(16, 36, 10, 10);
        listBtn.setTextColor(Color.rgb(77, 77, 77));


        /*  Quantity */
        final EditText listBtna = new EditText(ordnwuiContext);
        listBtna.setAllCaps(false);
        listBtna.setText(quantity);
        listBtna.setGravity(Gravity.CENTER);
        listBtna.setId(Math.abs(uniqid.get(3)));
        nwQntyidec.add(Math.abs(uniqid.get(3)));
        listBtna.setTextSize((float) eunicapr + 5.0f);
        listBtna.setBackgroundResource(R.color.trans);
        listBtna.setPadding(16, 36, 10, 10);
        listBtna.setTextColor(Color.rgb(77, 77, 77));

        /*  Total */
        final TextView listBtnb = new TextView(ordnwuiContext);
        listBtnb.setAllCaps(false);
        listBtnb.setHint("0");
        listBtnb.setGravity(Gravity.CENTER);
        listBtnb.setId(Math.abs(uniqid.get(4)));
        listBtnb.setTextSize((float) eunicapr + 5.0f);
        listBtnb.setBackgroundResource(R.color.trans);
        listBtnb.setPadding(16, 36, 10, 10);
        listBtnb.setTextColor(Color.rgb(77, 77, 77));

        /* Initial row total calculation */
        listBtnb.setText(formatter.format(ordrwtrelisCalc(usrLineara, listBtn, listBtna)));

        /*  Action Listeners
         *  Set price */
        listBtnp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!distros.getText().toString().equals("Select")) {
                    listBtn.setText(formatter.format(resources.retuxPrdprcca(listBtnp.getText().toString(),
                            distros.getText().toString())));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        /*  Price text change action listener */
        listBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listBtnb.setText(formatter.format(ordrwtrelisCalc(usrLineara, listBtn, listBtna)));
                trelis.setText(formatter.format(ordtrelisCalc(usrLineara, nwPrccidec, nwQntyidec)));

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
                listBtnb.setText(formatter.format(ordrwtrelisCalc(usrLineara, listBtn, listBtna)));
                trelis.setText(formatter.format(ordtrelisCalc(usrLineara, nwPrccidec, nwQntyidec)));

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        /*  Product  long press action listener */
        listBtnp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                resources.remvOrdrows(usrLineara, usrFlexbox, nwPrdcontidec, nwPrdctidec, nwPrccidec,
                        nwQntyidec, usrFlexbox.getId(), listBtnp.getId(), listBtn.getId(), listBtna.getId(),
                        nwUniqidec, trelis);

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
        usrLineara.addView(usrFlexbox, layoutParams);

    }

    /*  Get row  total */
    public Double ordrwtrelisCalc(LinearLayout usrLinear, TextView prcctxtvew, EditText qntyvew) {
        Double total = 0.0;
        if (!prcctxtvew.getText().toString().replaceAll("[^.0-9]", "").equals("") &&
                !qntyvew.getText().toString().replaceAll("[^.0-9]", "").equals("")) {
            total = Double.valueOf(prcctxtvew.getText().toString().replaceAll("[^.0-9]", "")) *
                    Double.valueOf(qntyvew.getText().toString().replaceAll("[^.0-9]", ""));

        }

        return total;
    }

    /*  Get  total cost */
    public Double ordtrelisCalc(LinearLayout usrLinear, ArrayList<Integer> prccidec, ArrayList<Integer> qntyidec) {
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
}
