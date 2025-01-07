package com.avintangu.bizsmat.debts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.Guideline;

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

public class Debtsresoca {
    DecimalFormat formatter = new DecimalFormat("#,###");
    private static Context datresaContext;
    Resocs resources;
    Dbcluzz dbcluzz;
    Debtsresoc debtsresoc;

    public Debtsresoca(Context context) {
        datresaContext = context;

        resources = new Resocs(datresaContext);
        dbcluzz = new Dbcluzz(datresaContext);
        debtsresoc = new Debtsresoc(datresaContext);

    }

    /*  New  edit record dialogue */
    public void recdEditdilog(final String detocs, final String custname) {
        /*  Declarations */
        String title = "";
        ArrayList<String> prdetals = new ArrayList<>();
        WindowManager winmanage = (WindowManager) datresaContext.getSystemService(Context.WINDOW_SERVICE);

        if (detocs.equals("editCustos")) {
            title = custname;

        } else if (detocs.equals("lendCustos")) {
            title = "Lend products";

        } else if (detocs.equals("editDebtrec")) {
            prdetals = debtsresoc.gdebtSingle(custname);
            title = prdetals.get(1);

        }


        /*  Get layout*/
        LayoutInflater inflater = (LayoutInflater) datresaContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View parentView = inflater.inflate(R.layout.debtnwedrecodc, null);

        /* Declarations */
        final double mezeunica = resources.getFontex(winmanage.getDefaultDisplay(), new Point());
        ((TextView) parentView.findViewById(R.id.dlgnwdatsttle)).setTextSize((float) (mezeunica + 6.0));
        ((TextView) parentView.findViewById(R.id.dlgnwdatsttle)).setText(title);
        ((Button) parentView.findViewById(R.id.dlgnwdatBck)).setTextSize((float) mezeunica + 4.5f);

        final ArrayList<Integer> resnwPrdcontidec = new ArrayList<>();
        final ArrayList<Integer> resnwPrdctidec = new ArrayList<>();
        final ArrayList<Integer> resnwPrccidec = new ArrayList<>();
        final ArrayList<Integer> resnwQntyidec = new ArrayList<>();

        final ScrollView scrollViewa = parentView.findViewById(R.id.dlgnwdatscroll);

        /* Adjust dimensions */
        mezAdjustdims(parentView);

        /*  Add body contents */
        Debtsnwed.debtnewactive.setContentView(parentView);
        scrollViewa.removeAllViews();

        if (detocs.equals("editCustos")) {
            final View repotView = inflater.inflate(R.layout.debtnwedrecod, null);
            scrollViewa.addView(repotView);

            /* Initialize arraylists */
            ArrayList<Integer> txvewidec = new ArrayList<>(Arrays.asList(R.id.debtnwtxt, R.id.debtnwtxta, R.id.debtnwtxt));
            ArrayList<Integer> edtxtidec = new ArrayList<>(Arrays.asList(R.id.debtnwedtxt, R.id.debtnwedtxta));
            ArrayList<Integer> btnidec = new ArrayList<>(Arrays.asList(R.id.debtnewbtn, R.id.debtnewbtna));

            /*Set textviews font sizes*/
            TextView[] Txvewarry = new TextView[txvewidec.size()];
            for (int k = 0; k < txvewidec.size(); ++k) {
                (Txvewarry[k] = repotView.findViewById(txvewidec.get(k))).setTextSize((float) mezeunica + 4.5f);
            }

            /*Set Editext font sizes*/
            EditText[] Edtxtarry = new EditText[edtxtidec.size()];
            for (int k = 0; k < edtxtidec.size(); ++k) {
                (Edtxtarry[k] = repotView.findViewById(edtxtidec.get(k))).setTextSize((float) mezeunica + 4.5f);
            }

            /*Set buttons font sizes*/
            Button[] Btnarry = new Button[btnidec.size()];
            for (int k = 0; k < btnidec.size(); ++k) {
                (Btnarry[k] = repotView.findViewById(btnidec.get(k))).setTextSize((float) mezeunica + 4.5f);
            }

            repotView.findViewById(R.id.debtnewbtna).setVisibility(View.VISIBLE);

            /*  Set date */
            repotView.findViewById(R.id.debtnewbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resources.freexam(((Button) repotView.findViewById(R.id.debtnewbtn)));

                }
            });

            /* Delete customer */
            repotView.findViewById(R.id.debtnewbtna).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resources.recdDeltdiloga(detocs, "Active", resources.retuxCuscodes(custname),
                            Debtsnwed.debtnwedttle.getText().toString(), resources.retuxCuscodes(Debtsnwed.debtnwedttle.getText().toString()));

                }
            });

            /*  Set data  */
            ArrayList<String> usrVals = debtsresoc.gcustoSingrecd("custostbl", resources.retuxCuscodes(custname));
            ((Button) repotView.findViewById(R.id.debtnewbtn)).setText(usrVals.get(0));
            ((EditText) repotView.findViewById(R.id.debtnwedtxt)).setText(usrVals.get(1));
            ((EditText) repotView.findViewById(R.id.debtnwedtxta)).setText(usrVals.get(2));
            ((TextView) repotView.findViewById(R.id.debtnewuniqid)).setText(usrVals.get(3));

        } else if (detocs.equals("lendCustos")) {
            /* Headers */
            ArrayList<String> Titles = new ArrayList<>(Arrays.asList("Product", "Price", "Quantity", "Total"));
            ArrayList<Float> Widoth = new ArrayList<>(Arrays.asList(1f, 0.4f, 0.2f, 0.2f, 0.2f));
            LinearLayout lendlincont = (LinearLayout) parentView.findViewById(R.id.lendeshedcont);

            FlexboxLayout lendhedFlex = new FlexboxLayout(datresaContext);
           /* lendhedFlex.setDividerDrawable(datresaContext.getResources().getDrawable(R.drawable.flexcontdivider));
            lendhedFlex.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
            lendhedFlex.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);*/
            lendhedFlex.setBackgroundColor(datresaContext.getResources().getColor(R.color.bluua));

            for (int ghas = 0; ghas < Titles.size(); ghas++) {
                TextView header = new TextView(datresaContext);
                header.setGravity(Gravity.CENTER);
                header.setTextSize((float) mezeunica + 6.0f);
                header.setText(Titles.get(ghas));
                header.setTextColor(Color.rgb(255, 255, 255));
                header.setPadding(0, 10, 0, 10);

                FlexboxLayout.LayoutParams hedlayoutParams = new FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.MATCH_PARENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT);

                hedlayoutParams.setFlexBasisPercent(Widoth.get(ghas + 1));
                hedlayoutParams.setFlexGrow(1.0f);
                lendhedFlex.addView(header, hedlayoutParams);
            }

            lendlincont.removeAllViews();
            lendlincont.addView(lendhedFlex, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            final View repotView = inflater.inflate(R.layout.debtnwedrecodd, null);
            scrollViewa.addView(repotView);
            ((TextView) parentView.findViewById(R.id.debtlenduniqida)).setText(resources.retuxCuscodes(custname));

            final LinearLayout resusrLinear = scrollViewa.findViewById(R.id.debtalendgallinic);
            final TextView restrelis = scrollViewa.findViewById(R.id.debtlendrecodtxtd);

            /* Add products  */
            parentView.findViewById(R.id.newdebtaddbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addlendPrdcts(mezeunica, resusrLinear, resnwPrdcontidec,
                            resnwPrdctidec, resnwPrccidec, resnwQntyidec, restrelis);

                }
            });

            /*  Add products */
            addlendPrdcts(mezeunica, resusrLinear, resnwPrdcontidec,
                    resnwPrdctidec, resnwPrccidec, resnwQntyidec, restrelis);

        } else if (detocs.equals("editDebtrec")) {
            final View repotView = inflater.inflate(R.layout.debtnwedrecode, null);
            scrollViewa.addView(repotView);

            /* Initialize arraylists */
            ArrayList<Integer> txvewidec = new ArrayList<>(Arrays.asList(R.id.debtprdnwtxt, R.id.debtprdnwtxta,
                    R.id.debtprdnwtxtb, R.id.debtprdnwtxtc, R.id.debtprdnwtxtd));
            ArrayList<Integer> edtxtidec = new ArrayList<>(Arrays.asList(R.id.debtprdnwedtxt, R.id.debtprdnwedtxta));
            ArrayList<Integer> btnidec = new ArrayList<>(Arrays.asList(R.id.debtprdnewbtn, R.id.debtprdnewbtna));

            /*Set textviews font sizes*/
            TextView[] Txvewarry = new TextView[txvewidec.size()];
            for (int k = 0; k < txvewidec.size(); ++k) {
                (Txvewarry[k] = repotView.findViewById(txvewidec.get(k))).setTextSize((float) mezeunica + 4.5f);
            }

            /*Set Editext font sizes*/
            EditText[] Edtxtarry = new EditText[edtxtidec.size()];
            for (int k = 0; k < edtxtidec.size(); ++k) {
                (Edtxtarry[k] = repotView.findViewById(edtxtidec.get(k))).setTextSize((float) mezeunica + 4.5f);
            }

            /*Set buttons font sizes*/
            Button[] Btnarry = new Button[btnidec.size()];
            for (int k = 0; k < btnidec.size(); ++k) {
                (Btnarry[k] = repotView.findViewById(btnidec.get(k))).setTextSize((float) mezeunica + 4.5f);
            }

            /*   Products pop up  */
            resources.spinMod(((TextView) repotView.findViewById(R.id.debtprdnwtxtb)), mezeunica, resources.getallPRD(), "");

            /*  Set date */
            repotView.findViewById(R.id.debtprdnewbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resources.freexam(((Button) repotView.findViewById(R.id.debtprdnewbtn)));

                }
            });

            /* Delete debt */
            final ArrayList<String> finalPrdetals = prdetals;
            repotView.findViewById(R.id.debtprdnewbtna).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    resources.recdDeltdiloga(detocs, "Active", finalPrdetals.get(4),
                            Debtsnwed.debtnwedttle.getText().toString(), resources.retuxCuscodes(Debtsnwed.debtnwedttle.getText().toString()));

                }
            });

            /*  Set data  */
            ((Button) repotView.findViewById(R.id.debtprdnewbtn)).setText(finalPrdetals.get(0));
            ((TextView) repotView.findViewById(R.id.debtprdnwtxtb)).setText(finalPrdetals.get(1));
            ((EditText) repotView.findViewById(R.id.debtprdnwedtxt)).setText(finalPrdetals.get(2));
            ((EditText) repotView.findViewById(R.id.debtprdnwedtxta)).setText(finalPrdetals.get(3));
            ((TextView) repotView.findViewById(R.id.debtprdnewuniqid)).setText(finalPrdetals.get(4));


        }

        /*  Roll back */
        parentView.findViewById(R.id.dlgnwdatBck).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dilogactivResat(Debtsnwed.debtnwedttle.getText().toString(), resources.retuxCuscodes(Debtsnwed.debtnwedttle.getText().toString()),
                        "ediDatsum");

            }
        });

        /*   Save update report */
        parentView.findViewById(R.id.dlgnwdatsavbtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ArrayList<String> paramsend = new ArrayList<>();
                String custcode = "";
                String determ = "";

                if (detocs.equals("editCustos")) {
                    String date = resources.gtDpresocsa(((Button) parentView.findViewById(R.id.debtnewbtn)).getText().toString());
                    String name = ((EditText) parentView.findViewById(R.id.debtnwedtxt)).getText().toString();
                    String phone = ((EditText) parentView.findViewById(R.id.debtnwedtxta)).getText().toString();

                    custcode = ((TextView) parentView.findViewById(R.id.debtnewuniqid)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(date, name, phone, custcode));
                    determ = "debtupdatrecd";

                    String respo = debtsresoc.mukDebts(paramsend, detocs, determ, "nada", new ArrayList<String>(),
                            new ArrayList<Integer>(), new ArrayList<Integer>(), new String());

                    if (respo.equals("true")) {
                        dilogactivResat(name, custcode, "ediDatsum");
                    }

                } else if (detocs.equals("lendCustos")) {
                    ArrayList<String> nwPrdctdata = new ArrayList<>();
                    ArrayList<Integer> nwPrccdata = new ArrayList<>();
                    ArrayList<Integer> nwQntydata = new ArrayList<>();
                    custcode = ((TextView) parentView.findViewById(R.id.debtlenduniqida)).getText().toString();

                    for (int mega = 0; mega < resnwPrdctidec.size(); mega++) {
                        if (!((TextView) scrollViewa.findViewById(resnwPrdctidec.get(mega))).getText().toString().equals("") &&
                                !((TextView) scrollViewa.findViewById(resnwPrccidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "").equals("") &&
                                !((EditText) scrollViewa.findViewById(resnwQntyidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "").equals("")) {

                            nwPrdctdata.add(((TextView) scrollViewa.findViewById(resnwPrdctidec.get(mega))).getText().toString());
                            nwPrccdata.add(Integer.valueOf(((TextView) scrollViewa.findViewById(resnwPrccidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "")));
                            nwQntydata.add(Integer.valueOf(((EditText) scrollViewa.findViewById(resnwQntyidec.get(mega))).getText().toString().replaceAll("[^.0-9]", "")));

                        }
                    }
                    determ = "debtsavrecda";

                    String respo = debtsresoc.mukDebts(paramsend, detocs, determ, "nada", nwPrdctdata, nwPrccdata, nwQntydata,
                            custcode);

                    if (respo.equals("true")) {
                        dilogactivResat(custname, custcode, "ediDatsum");
                    }

                } else if (detocs.equals("editDebtrec")) {
                    String date = resources.gtDpresocsa(((Button) parentView.findViewById(R.id.debtprdnewbtn)).getText().toString());
                    String product = resources.retuxPrdcode(((TextView) parentView.findViewById(R.id.debtprdnwtxtb)).getText().toString());
                    String price = ((EditText) parentView.findViewById(R.id.debtprdnwedtxt)).getText().toString().replaceAll("[^.0-9]", "");
                    String quantity = ((EditText) parentView.findViewById(R.id.debtprdnwedtxta)).getText().toString().replaceAll("[^.0-9]", "");

                    custcode = ((TextView) parentView.findViewById(R.id.debtprdnewuniqid)).getText().toString();

                    paramsend = new ArrayList<>(Arrays.asList(date, product, price, quantity, custcode));
                    determ = "debtupdatrecd";

                    String respo = debtsresoc.mukDebts(paramsend, detocs, determ, "nada", new ArrayList<String>(),
                            new ArrayList<Integer>(), new ArrayList<Integer>(), new String());

                    if (respo.equals("true")) {
                        dilogactivResat(Debtsnwed.debtnwedttle.getText().toString(), resources.retuxCuscodes(Debtsnwed.debtnwedttle.getText().toString()),
                                "ediDatsum");
                    }

                }

            }
        });

    }

    /*  Restart activity */
    public void dilogactivResat(String trelis, String treliscode, String nwprdeterm) {
        Debtsnwed.debtnewactive.finish();
        com.avintangu.bizsmat.debts.Debtsui duis = new com.avintangu.bizsmat.debts.Debtsui(datresaContext);
        Intent intent = new Intent(datresaContext.getApplicationContext(), Debtsnwed.class);

        Bundle bundle = new Bundle();
        bundle.putString("debtdeterm", nwprdeterm);
        ArrayList<ArrayList<String>> paysumdetal = duis.debtSinglerecd(trelis, treliscode);
        bundle.putSerializable("debtdetal", paysumdetal);
        intent.putExtras(bundle);

        intent.putExtras(bundle);
        datresaContext.startActivity(intent);
    }

    /* Adjust dimensions */
    public void mezAdjustdims(View dialogView) {
        float constimgWidth = 0f, constimgWidtha = 0f, constimgWidthb = 0f, widoth = 0f;
        WindowManager winmanage = (WindowManager) datresaContext.getSystemService(Context.WINDOW_SERVICE);
        ArrayList<Float> srcsize = resources.getSrcsize(winmanage.getDefaultDisplay(), new Point());

        if (datresaContext.getResources().getConfiguration().orientation == 2) {
            constimgWidth = 14f;
            constimgWidtha = 90f;
            constimgWidthb = 100f;
            widoth = srcsize.get(1);

        } else if (datresaContext.getResources().getConfiguration().orientation == 1) {
            constimgWidth = 8f;
            constimgWidtha = 95f;
            widoth = srcsize.get(0);

        }

        Guideline guideline = dialogView.findViewById(R.id.dlgnwdatguida);
        Guideline guidelina = dialogView.findViewById(R.id.dlgnwdatguidb);

        float constsrcWidth = 360;
        float konstant = constsrcWidth / widoth;

        /* Calculated constraints */
        float newimgWidth = (konstant * constimgWidth) / 100f;
        float newimgWidtha = constimgWidtha / 100f;

        guideline.setGuidelinePercent(newimgWidth);
        guidelina.setGuidelinePercent(newimgWidtha);

    }

    /* Add products*/
    public void addlendPrdcts(final double eunicapr, final LinearLayout usrLinear, final ArrayList<Integer> nwPrdcontidec,
                              final ArrayList<Integer> nwPrdctidec, final ArrayList<Integer> nwPrccidec, final ArrayList<Integer> nwQntyidec,
                              final TextView trelis) {
        ArrayList<Integer> uniqid = new ArrayList<>();

        for (int i = 0; i < 7; ++i) {
            uniqid.add(resources.generateViewId());
        }

        final FlexboxLayout usrFlexbox = new FlexboxLayout(datresaContext);
        usrFlexbox.setFlexDirection(FlexDirection.ROW);
        usrFlexbox.setAlignContent(AlignContent.FLEX_START);
        usrFlexbox.setAlignItems(AlignItems.BASELINE);
        usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
        usrFlexbox.setId(Math.abs(uniqid.get(0)));
        nwPrdcontidec.add(Math.abs(uniqid.get(0)));
        usrFlexbox.setPadding(0, 0, 0, 0);
        usrFlexbox.setDividerDrawable(datresaContext.getResources().getDrawable(R.drawable.flexcontdividera));
        usrFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_END);
        usrFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
        usrFlexbox.setBackgroundColor(datresaContext.getResources().getColor(R.color.white));

        /*  Product */
        final TextView listBtnp = new TextView(datresaContext);
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


        /*  Price */
        final TextView listBtn = new TextView(datresaContext);
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
        final EditText listBtna = new EditText(datresaContext);
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
        final EditText listBtnb = new EditText(datresaContext);
        listBtnb.setAllCaps(false);
        listBtnb.setHint("0");
        listBtnb.setGravity(Gravity.CENTER);
        listBtnb.setId(Math.abs(uniqid.get(4)));
        listBtnb.setTextSize((float) eunicapr + 5.0f);
        listBtnb.setBackgroundResource(R.color.trans);
        listBtnb.setPadding(16, 36, 10, 10);
        listBtnb.setTextColor(Color.rgb(77, 77, 77));

        /*  Action Listeners
         *  Price text change action listener */
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
                listBtnb.setText(formatter.format(datrwtrelisCalc(usrLinear, listBtn, listBtna)));
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
                listBtnb.setText(formatter.format(datrwtrelisCalc(usrLinear, listBtn, listBtna)));
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
    public Double datrwtrelisCalc(LinearLayout usrLinear, TextView prcctxtvew, EditText qntyvew) {
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

}
