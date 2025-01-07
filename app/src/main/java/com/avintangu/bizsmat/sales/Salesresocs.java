package com.avintangu.bizsmat.sales;

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

public class Salesresocs {

    private static Context salresContext;
    Resocs resources;
    Dbcluzz dbcluzz;

    DecimalFormat formatter = new DecimalFormat("#,###");

    public Salesresocs(Context context) {
        salresContext = context;

        resources = new Resocs(salresContext);
        dbcluzz = new Dbcluzz(salresContext);

    }

    /* Get all records */
    public ArrayList<ArrayList<String>> ghAllsales(String dbess, String stexa, String steta, ArrayList<String> Sres,
                                                   String srchdeterm, String mims, String frodta, String todta) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String sqlite = "";
        Cursor res = null;

        if (srchdeterm.equals("nada") || srchdeterm.equals("")) {
            if (mims.equals("salSrecod")) {
                sqlite = "SELECT *, (salesrecdprcc*salesrecdqnty) AS salesrecdtotal FROM " + dbess + " debb LEFT JOIN prdctdetaz debby " +
                        "ON debb.salesrecdprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo " +
                        "ON debb.salesrecdshop = debbo.usrshopuniq WHERE " + stexa + "= ?  " +
                        "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                        "AND salesrecddate BETWEEN ? AND ? ORDER BY salesrecddate ASC";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, frodta, todta});

            }

        } else {
            if (mims.equals("salSrecod")) {
                sqlite = "SELECT *, (salesrecdprcc*salesrecdqnty) AS salesrecdtotal FROM " + dbess + " debb LEFT JOIN prdctdetaz debby " +
                        "ON debb.salesrecdprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo debb.salesrecdshop = debbo.usrshopuniq " +
                        "WHERE " + stexa + "= ? AND  " + Sres.get(0) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") AND salesrecddate BETWEEN ? AND ? ORDER BY salesrecddate ASC" +
                        "OR " + stexa + "= ? AND  " + Sres.get(1) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") AND salesrecddate BETWEEN ? AND ? ORDER BY salesrecddate ASC" +
                        "OR " + stexa + "= ? AND  " + Sres.get(2) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") AND salesrecddate BETWEEN ? AND ? ORDER BY salesrecddate ASC" +
                        "OR " + stexa + "= ? AND  " + Sres.get(3) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") AND salesrecddate BETWEEN ? AND ? ORDER BY salesrecddate ASC";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, "%" + srchdeterm + "%", frodta, todta,
                        steta, "%" + srchdeterm + "%", frodta, todta, steta, "%" + srchdeterm + "%" + "%", frodta, todta,
                        steta, "%" + srchdeterm + "%", frodta, todta});

            }
        }


        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("salesrecdprcc") || Sres.get(desca).equals("salesrecdqnty") || Sres.get(desca).equals("salesrecdtotal")) {
                            comblist.get(desca).add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("salesrecddate")) {
                            comblist.get(desca).add(resources.gtDpresocs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else {
                            comblist.get(desca).add(res.getString(res.getColumnIndexOrThrow(Sres.get(desca))));

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

    /* Get single record */
    public ArrayList<String> gtsalSingrecd(String mims, String dbess, String uniqidec, String uniqidecval,
                                           ArrayList<String> Sres) {

        ArrayList<String> comblist = new ArrayList<>();


        String sqlp = "";
        Cursor res = null;

        if (mims.equals("salSrecod")) {
            sqlp = "SELECT *, (salesrecdprcc*salesrecdqnty) AS salesrecdtotal FROM " + dbess + " debb LEFT JOIN prdctdetaz debby " +
                    "ON debb.salesrecdprdcod = debby.prdctdetazcode WHERE " + uniqidec + "= ? ";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{uniqidecval});


        }

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("salesrecddate")) {
                            comblist.add(resources.gtDpresocs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("salesrecdprcc") || Sres.get(desca).equals("salesrecdqnty") || Sres.get(desca).equals("salesrecdtotal")) {
                            comblist.add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sres.get(desca)))));

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
    public void mukSales(ArrayList<String> paramsend, ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata,
                         ArrayList<Integer> nwQntydata, String detocs, String determ, String detocsa) {
        ArrayList<String> svarry = new ArrayList<>();
        ArrayList<String> svarrya = new ArrayList<>();
        ArrayList<String> svarryb = new ArrayList<>();

        switch (detocs) {
            /* New records */
            case "newSale": {
                if (nwPrdctdata.size() > 0) {
                    svarry.add("cool");

                } else {
                    svarry.add("");

                }

                svarrya = new ArrayList<>(Arrays.asList(""));
                svarryb = new ArrayList<>(Arrays.asList("enter at least one record"));


                break;
            }

            /*  Edit record*/
            case "editSale": {
                svarry.addAll(paramsend);
                svarrya = new ArrayList<>(Arrays.asList("nada", "", "", ""));
                svarryb = new ArrayList<>(Arrays.asList("select date", " select product", " enter price", " enter quantity"));


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
            Toast.makeText(salresContext, "Kindly: " + prompt, Toast.LENGTH_LONG).show();

        } else {
            if (determ.equals("salSavrecd")) {
                insrtNwsalswitch(nwPrdctdata, nwPrccdata, nwQntydata, detocs);

            } else if (determ.equals("salUpdatrecd")) {
                updatNwsalswitch(paramsend, detocs, detocsa);

            }

        }
    }

    /* Insert record switch */
    public void insrtNwsalswitch(ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata,
                                 ArrayList<Integer> nwQntydata, String determpr) {
        ArrayList<String> micer = new ArrayList<>();
        String dbess = "";
        switch (determpr) {

            case "newSale": {
                dbess = "salesrecd";
               /* micer = new ArrayList<>(Arrays.asList("salesrecddate", "salesrecdprdcod", "salesrecdprcc",
                        "salesrecdqnty", "salesrecdrecod", "salesrecdstate"));*/

                break;
            }

            default:
                break;
        }

        boolean resp = insrtNwsales(dbess, nwPrdctdata, nwPrccdata, nwQntydata);

        if (resp == true) {
            /* Refresh activity */
            resources.activResat(Sales.salesactiv, Salenew.salnewrecod, Sales.class,
                    Sales.saltabManage.getText().toString(), Sales.salStatus.getText().toString(),
                    "nada");

        } else {
            Toast.makeText(salresContext, "An error occured while saving record", Toast.LENGTH_LONG).show();
        }
    }

    /* Update record switch */
    public void updatNwsalswitch(ArrayList<String> usrarry, String determpr, String detocsa) {
        ArrayList<String> micer = new ArrayList<>();
        String dbess = "";
        String uniqident = "";

        switch (determpr) {

            case "editSale": {
                dbess = "salesrecd";
                micer = new ArrayList<>(Arrays.asList("salesrecddate", "salesrecdprdcod", "salesrecdprcc",
                        "salesrecdqnty"));

                uniqident = "salesrecdrecod";

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Sales", usrarry.get(usrarry.size() - 1));

                break;

            }

            default:
                break;
        }

        boolean resp = updatNwsal(dbess, uniqident, micer, usrarry);

        if (resp == true) {
            /* Refresh activity */
            resources.activResat(Sales.salesactiv, Salenew.salnewrecod, Sales.class,
                    Sales.saltabManage.getText().toString(), Sales.salStatus.getText().toString(),
                    "nada");

        } else {
            Toast.makeText(salresContext, "An error occured while updating record", Toast.LENGTH_LONG).show();
        }
    }

    /* Insert sales */
    public boolean insrtNwsales(String dbess, ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata,
                                ArrayList<Integer> nwQntydata) {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();

        String state = "Active";
        String shopcode = resources.getActivshop().get(3);

        if (nwPrdctdata.size() > 0) {
            for (int i = 0; i < nwPrdctdata.size(); ++i) {
                String uniqcode = resources.instPrdctcd(shopcode, i);

                ContentValues contentValues = new ContentValues();
                contentValues.put("salesrecddate", resources.retStrdate().get(7));
                contentValues.put("salesrecdprdcod", resources.retuxPrdcode(nwPrdctdata.get(i)));
                contentValues.put("salesrecdprcc", nwPrccdata.get(i));
                contentValues.put("salesrecdqnty", nwQntydata.get(i));
                contentValues.put("salesrecdbprc", resources.retuxActbprc(resources.retuxPrdcode(nwPrdctdata.get(i))));
                contentValues.put("salesrecdrecod", uniqcode);
                contentValues.put("salesrecdstate", state);
                contentValues.put("salesrecdshop", resources.gtAshopcode());

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Sales", uniqcode);

                writableDatabase.insert(dbess, null, contentValues);

            }
        }

        return true;
    }

    /* Update record */
    public boolean updatNwsal(String dbess, String uniqident,
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
