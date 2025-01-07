package com.avintangu.bizsmat.products;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.resources.Resocs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Prdctresocs {
    private static Context prdresoContext;
    Dbcluzz dbcluzz;
    Resocs resources;

    DecimalFormat formatter = new DecimalFormat("#,###");

    public Prdctresocs(Context context) {
        prdresoContext = context;
        dbcluzz = new Dbcluzz(prdresoContext);
        resources = new Resocs(prdresoContext);

    }

    /* Get all records */
    public ArrayList<ArrayList<String>> ghAllprdcts(String dbess, String stexa, String steta, ArrayList<String> Sres,
                                                    String srchdeterm, String mims) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String sqlite = "";
        Cursor res = null;

        if (srchdeterm.equals("nada") || srchdeterm.equals("")) {
            if (mims.equals("prdSrecod")) {
                sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN prdctdcateri debby " +
                        "ON debb.prdctdetazcateg = debby.prdctdcateriuniq LEFT JOIN usrshops debbo " +
                        "ON debb.prdctdetazshop = debbo.usrshopuniq WHERE " + stexa + "= ? " +
                        "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ")";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta});

            } else if (mims.equals("prdSrecoda")) {
                sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN usrshops debbo " +
                        "ON debb.prdctdcaterishop = debbo.usrshopuniq WHERE " + stexa + "= ? " +
                        "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ")";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta});
            }


        } else {
            if (mims.equals("prdSrecod")) {
                sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN prdctdcateri debby " +
                        "ON debb.prdctdetazcateg = debby.prdctdcateriuniq LEFT JOIN usrshops debbo ON debb.prdctdetazshop = debbo.usrshopuniq " +
                        "WHERE " + stexa + "= ? AND  " + Sres.get(0) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                        "OR " + stexa + "= ? AND  " + Sres.get(1) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                        "OR " + stexa + "= ? AND  " + Sres.get(2) + " LIKE ?  AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                        "OR " + stexa + "= ? AND  " + Sres.get(3) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") ";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, "%" + srchdeterm + "%",
                        steta, "%" + srchdeterm + "%", steta, "%" + srchdeterm + "%" + "%", steta, "%" + srchdeterm + "%"});

            } else if (mims.equals("prdSrecoda")) {
                sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN usrshops debbo  ON debb.prdctdcaterishop = debbo.usrshopuniq " +
                        "WHERE " + stexa + "= ? AND  " + Sres.get(0) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                        "OR " + stexa + "= ? AND  " + Sres.get(1) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ")";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, "%" + srchdeterm + "%",
                        steta, "%" + srchdeterm + "%"});

            }
        }


        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("prdctdetazprcc")) {
                            comblist.get(desca).add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("prdctdetazdate")) {
                            comblist.get(desca).add(resources.gtDpresocs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("prdctdetazproft")) {
                            comblist.get(desca).add(formatter.format(res.getDouble(res.getColumnIndexOrThrow("prdctdetazprcc")) -
                                    resources.retuxActbprc(res.getString(res.getColumnIndexOrThrow("prdctdetazcode")))));

                        } else {
                            comblist.get(desca).add(res.getString(res.getColumnIndexOrThrow(Sres.get(desca))));

                        }

                    }

                } while (res.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            if (res != null) {
                res.close();
            }
        }

        return comblist;
    }

    /* Get single record */
    public ArrayList<String> gtaSingrecd(String mims, String dbess, String uniqidec, String uniqidecval,
                                         ArrayList<String> Sres) {

        ArrayList<String> comblist = new ArrayList<>();


        String sqlp = "";
        Cursor res = null;

        if (mims.equals("prdSrecod")) {
            sqlp = "SELECT * FROM " + dbess + " debb LEFT JOIN prdctdcateri debby " +
                    "ON debb.prdctdetazcateg = debby.prdctdcateriuniq WHERE " + uniqidec + "= ? ";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{uniqidecval});


        } else if (mims.equals("prdSrecoda")) {
            sqlp = "SELECT * FROM " + dbess + " WHERE " + uniqidec + "= ? ";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{uniqidecval});

        }


        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("prdctdetazdate")) {
                            comblist.add(resources.gtDpresocs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("prdctdetazprcc")) {
                            comblist.add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sres.get(desca)))));


                        } else if (Sres.get(desca).equals("prdctdetazprcalc")) {
                            comblist.add(formatter.format(resources.retuxActbprc(uniqidecval)));


                        } else if (Sres.get(desca).equals("prdctdetazproft")) {
                            comblist.add(formatter.format(res.getDouble(res.getColumnIndexOrThrow("prdctdetazprcc")) - resources.retuxActbprc(uniqidecval)));


                        } else {
                            comblist.add(res.getString(res.getColumnIndexOrThrow(Sres.get(desca))));

                        }

                    }

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling
        } finally {
            if (res != null) {
                res.close();
            }
        }


        return comblist;
    }


    /* Save and edit report */
    public void mukPrdcts(ArrayList<String> paramsend, String detocs, String determ, String detocsa) {
        String uniqIdec = resources.uniqueGen().toString();

        ArrayList<String> svarry = new ArrayList<>();
        ArrayList<String> svarrya = new ArrayList<>();
        ArrayList<String> svarryb = new ArrayList<>();

        String state = "Active";

        switch (detocs) {
            /* New records */
            case "newProduct":
            case "editProduct": {
                svarry.addAll(paramsend);
                svarrya = new ArrayList<>(Arrays.asList("nada", "nada", "", "", ""));
                svarryb = new ArrayList<>(Arrays.asList("set date", "select category", "enter name",
                        "enter price"));


                break;
            }

            case "newCategory":
            case "editCategory": {
                svarry.addAll(paramsend);
                svarrya = new ArrayList<>(Arrays.asList(""));
                svarryb = new ArrayList<>(Arrays.asList("enter category"));


                break;
            }

            default:
                break;
        }

        ArrayList<String> svarryc = new ArrayList<>();

        for (int rec = 0; rec < svarrya.size(); rec++) {
            if (svarry.get(rec).equals(svarrya.get(rec))) {
                svarryc.add(svarryb.get(rec));

            }
        }

        if (svarryc.size() > 0) {
            String prompt = resources.Implodes(svarryc);
            Toast.makeText(prdresoContext, "Kindly: " + prompt, Toast.LENGTH_LONG).show();

        } else {
            if (determ.equals("prdctsavrecd")) {
                /* User code */
                String usrcode = dbcluzz.getUsrdetal().get(3).replaceAll("[^a-zA-Z0-9]", "");

                /* New record code */
                StringBuilder sba = new StringBuilder();
                sba.append(usrcode + "PRD" + uniqIdec + "CODE");
                String reptcode = String.valueOf(sba);

                paramsend.add(reptcode);
                paramsend.add(state);

                insrtNwprdswitch(paramsend, detocs, detocsa);


            } else if (determ.equals("prdctupdatrecd")) {

                updatNwprdcatswitch(paramsend, detocs, detocsa);
            }

        }
    }

    /* Insert record switch */
    public void insrtNwprdswitch(ArrayList<String> usrarry, String determpr, String detocsa) {
        ArrayList<String> micer = new ArrayList<>();
        String dbess = "";
        switch (determpr) {

            case "newProduct": {
                dbess = "prdctdetaz";
                micer = new ArrayList<>(Arrays.asList("prdctdetazdate", "prdctdetazcateg", "prdctdetazname",
                        "prdctdetazprcc", "prdctdetazcode", "prdctdetazrecod", "prdctdetazstate", "prdctdetazshop"));

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Products", usrarry.get(4));

                break;
            }

            case "newCategory": {
                dbess = "prdctdcateri";
                micer = new ArrayList<>(Arrays.asList("prdctdcatego", "prdctdcateriuniq", "prdctdcaterecod",
                        "prdctdcateristate", "prdctdcaterishop"));

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "ProductsCategories", usrarry.get(1));

                break;
            }

            default:
                break;
        }

        /* Add shop code */
        usrarry.add(resources.gtAshopcode());

        boolean resp = insrtNwprdct(dbess, micer, usrarry);

        if (resp == true) {
            /* Refresh activity */
            resources.activResat(Products.propsactiv, Prdctnew.prdcnewrecod, Products.class,
                    Products.prdtabManage.getText().toString(), Products.prdStatus.getText().toString(),
                    "nada");

        } else {
            Toast.makeText(prdresoContext, "An error occured while saving record", Toast.LENGTH_LONG).show();
        }
    }

    /* Update record switch */
    public void updatNwprdcatswitch(ArrayList<String> usrarry, String determpr, String detocsa) {
        ArrayList<String> micer = new ArrayList<>();
        String dbess = "";
        String uniqident = "";

        switch (determpr) {

            case "editProduct": {
                dbess = "prdctdetaz";
                micer = new ArrayList<>(Arrays.asList("prdctdetazdate", "prdctdetazcateg", "prdctdetazname",
                        "prdctdetazprcc"));

                uniqident = "prdctdetazcode";

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Products", usrarry.get(usrarry.size() - 1));

                break;

            }

            case "editCategory": {
                dbess = "prdctdcateri";
                micer = new ArrayList<>(Arrays.asList("prdctdcatego"));

                uniqident = "prdctdcateriuniq";

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "ProductsCategories", usrarry.get(usrarry.size() - 1));

                break;

            }

            default:
                break;
        }

        boolean resp = updatNwprdct(dbess, uniqident, micer, usrarry);

        if (resp == true) {
            /* Refresh activity */
            resources.activResat(Products.propsactiv, Prdctnew.prdcnewrecod, Products.class,
                    Products.prdtabManage.getText().toString(), Products.prdStatus.getText().toString(),
                    "nada");

        } else {
            Toast.makeText(prdresoContext, "An error occured while updating record", Toast.LENGTH_LONG).show();
        }
    }

    /* Insert record */
    public boolean insrtNwprdct(String dbess, ArrayList<String> micer, ArrayList<String> usrarry) {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = false;

        if (usrarry.size() > 0) {
            ContentValues contentValues = new ContentValues();

            for (int ghes = 0; ghes < micer.size(); ghes++) {
                contentValues.put(micer.get(ghes), usrarry.get(ghes));
            }

            writableDatabase.insert(dbess, null, contentValues);
            trace = true;

        }

        return trace;
    }

    /* Update record */
    public boolean updatNwprdct(String dbess, String uniqident,
                                ArrayList<String> micer, ArrayList<String> usrarry) {

        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = false;

        String entyuniq = usrarry.get(usrarry.size() - 1);
        usrarry.remove(usrarry.size() - 1);


        if (usrarry.size() > 0) {
            ContentValues contentValues = new ContentValues();

            for (int ghes = 0; ghes < micer.size(); ghes++) {
                contentValues.put(micer.get(ghes), usrarry.get(ghes));
            }

            writableDatabase.update(dbess, contentValues, uniqident + " = ?", new String[]{entyuniq});
            trace = true;

        }

        return trace;
    }

}
