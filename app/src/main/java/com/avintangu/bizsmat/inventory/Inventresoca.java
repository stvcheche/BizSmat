package com.avintangu.bizsmat.inventory;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.resources.Resocs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Inventresoca {
    DecimalFormat formatter = new DecimalFormat("#,###");
    DecimalFormat formattera = new DecimalFormat("#,###.##");
    private static Context invresaContext;
    Resocs resources;
    Dbcluzz dbcluzz;

    public Inventresoca(Context context) {
        invresaContext = context;

        resources = new Resocs(invresaContext);
        dbcluzz = new Dbcluzz(invresaContext);

    }

    /* Update selling price */
    public boolean updatSlprcc(ArrayList<String> allProducts, ArrayList<Integer> bPrcc) {

        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = false;

        if (allProducts.size() > 0) {
            for (int ghes = 0; ghes < bPrcc.size(); ghes++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("prdctdetazprcc", bPrcc.get(ghes));

                writableDatabase.update("prdctdetaz", contentValues, "prdctdetazname = ?", new String[]{allProducts.get(ghes)});
                trace = true;

            }

        }

        return trace;
    }

    public boolean updatSlprcca(String prdctcode, String bPrcc) {

        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = false;

        ContentValues contentValues = new ContentValues();
        contentValues.put("prdctdetazprcc", bPrcc);

        writableDatabase.update("prdctdetaz", contentValues, "prdctdetazcode = ?", new String[]{prdctcode});
        trace = true;


        return trace;
    }

    /* Batch calculator */
    public void invebatchCalc(double inveunica) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(invresaContext);
        LayoutInflater inflater = (LayoutInflater) invresaContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.batchcalculator, null);
        dialog.setView(dialogView);

        ImageView btncloze = dialogView.findViewById(R.id.bchcalcbtn);
        final TextView trelis = dialogView.findViewById(R.id.bchcalctxtvew);
        final LinearLayout batchprnt = dialogView.findViewById(R.id.batchcalcparent);
        trelis.setTextSize((float) inveunica + 5f);

        final EditText bpeditxt = dialogView.findViewById(R.id.bchcalcedtxt);
        final EditText cnteditxt = dialogView.findViewById(R.id.bchcalcedtxta);
        final TextView slpeditxt = dialogView.findViewById(R.id.bchcalctxtvewd);

        /* Set up font sizes */
        ArrayList<Integer> invtxvewidec = new ArrayList<>(Arrays.asList(R.id.bchcalctxtvewa, R.id.bchcalctxtvewb,
                R.id.bchcalctxtvewc, R.id.bchcalctxtvewd));

        /*Set textviews font sizes*/
        TextView[] Txvewarry = new TextView[invtxvewidec.size()];
        for (int k = 0; k < invtxvewidec.size(); ++k) {
            (Txvewarry[k] = dialogView.findViewById(invtxvewidec.get(k))).setTextSize((float) inveunica + 4.5f);
        }

        ArrayList<Integer> invedita = new ArrayList<>(Arrays.asList(R.id.bchcalcedtxt, R.id.bchcalcedtxta));

        /*Set textviews font sizes*/
        EditText[] Editxtarry = new EditText[invedita.size()];
        for (int k = 0; k < invedita.size(); ++k) {
            (Editxtarry[k] = dialogView.findViewById(invedita.get(k))).setTextSize((float) inveunica + 4.5f);
        }

        dialog.create();
        final AlertDialog show = dialog.show();
        show.getWindow().setBackgroundDrawableResource(R.color.trans);
        batchprnt.startAnimation(AnimationUtils.loadAnimation(invresaContext, R.anim.drawopanim));
        btncloze.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
            }
        });

        /*  On text changed listeners */
        bpeditxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calcBatch(bpeditxt, cnteditxt, slpeditxt);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        cnteditxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calcBatch(bpeditxt, cnteditxt, slpeditxt);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    /*  Calculate unit selling price */
    public void calcBatch(EditText bpeditxt, EditText cnteditxt, TextView slpeditxt) {
        if (!bpeditxt.getText().toString().replaceAll("[^.0-9]", "").equals("") &&
                !cnteditxt.getText().toString().replaceAll("[^.0-9]", "").equals("")) {
            Double unitprcc = Double.valueOf(bpeditxt.getText().toString().replaceAll("[^.0-9]", "")) /
                    Double.valueOf(cnteditxt.getText().toString().replaceAll("[^.0-9]", ""));

            slpeditxt.setText(formattera.format(unitprcc));

        }
    }
}
