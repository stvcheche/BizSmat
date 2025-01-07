package com.avintangu.bizsmat.debts;

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

public class Debtsresoc {
    DecimalFormat formatter = new DecimalFormat("#,###");
    private static Context datresContext;
    Resocs resources;
    Dbcluzz dbcluzz;

    public Debtsresoc(Context context) {
        datresContext = context;

        resources = new Resocs(datresContext);
        dbcluzz = new Dbcluzz(datresContext);

    }

    /*  Get debts summary */
    public ArrayList<ArrayList<String>> gtDatsummary(ArrayList<String> Sres, String srchdeterm, String steta) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String sqlite = "";
        Cursor res = null;

        if (srchdeterm.equals("nada") || srchdeterm.equals("")) {
            sqlite = "SELECT * FROM custostbl debb LEFT JOIN usrshops debbe ON debb.custostblshop = debbe.usrshopuniq  LEFT JOIN " +
                    "(SELECT *, IFNULL(SUM(custodatesqnty * custodatesval),0) AS datesumes FROM custodates WHERE custodatestate = ? GROUP BY custodatecuscod)  AS debby " +
                    "ON debb.custostblrecod = debby.custodatecuscod LEFT JOIN " +
                    "(SELECT *, IFNULL(SUM(custodatpayamt),0) AS paymntos FROM custodatpay WHERE custodatpaystate = ? GROUP BY custodatpayccod) AS debbo " +
                    "ON debb.custostblrecod = debbo.custodatpayccod WHERE debb.custostblstate = ? AND debbe.usrshopuniq IN(" + resources.gtCombshcode() + ") ORDER BY debb.custostblid";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, steta, steta});

        } else {
            sqlite = "SELECT * FROM custostbl debb LEFT JOIN usrshops debbe ON debb.custostblshop = debbe.usrshopuniq LEFT JOIN " +
                    "(SELECT *, IFNULL(SUM(custodatesqnty*custodatesval),0) AS datesum FROM custodates WHERE custodatestate= ? GROUP BY custodatecuscod)  AS debby " +
                    "ON debb.custostblrecod = debby.custodatecuscod WHERE debb.custostblstate = ? AND custostblname LIKE ? AND debbe.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                    "ORDER BY debb.custostblid";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, steta, "%" + srchdeterm + "%"});

        }

        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (int ghas = 0; ghas < Sres.size(); ghas++) {
                        if (Sres.get(ghas).equals("datesumes")) {
                            comblist.get(ghas).add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sres.get(ghas))) - res.getInt(res.getColumnIndexOrThrow("paymntos"))));

                        } else {
                            comblist.get(ghas).add(res.getString(res.getColumnIndexOrThrow(Sres.get(ghas))));
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

    /* Get all payments*/
    public ArrayList<ArrayList<String>> gtAllpymnts(String dbess, String stexa, String steta, ArrayList<String> Sres,
                                                    String srchdeterm, String frodta, String todta) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String sqlite = "";
        Cursor res = null;

        if (srchdeterm.equals("nada") || srchdeterm.equals("")) {
            sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN custostbl debby " +
                    "ON debb.custodatpayccod = debby.custostblrecod LEFT JOIN usrshops debbo " +
                    "ON debb.custodatpayshop = debbo.usrshopuniq WHERE " + stexa + "= ? " +
                    "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                    "AND custodatpaydate BETWEEN ? AND ? ";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, frodta, todta});

        } else {
            sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN custostbl debby " +
                    "ON debb.custodatpayccod = debby.custostblrecod LEFT JOIN usrshops debbo " +
                    "ON debb.custodatpayshop = debbo.usrshopuniq WHERE " + stexa + "= ? " +
                    "AND  " + Sres.get(0) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                    "AND custodatpaydate BETWEEN ? AND ? ";


            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, "%" + srchdeterm + "%", frodta, todta});

        }


        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("custodatpayamt")) {
                            comblist.get(desca).add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("custodatpaydate")) {
                            comblist.get(desca).add(resources.gtDpresocsd(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

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

    /* Get debts per customer*/
    public ArrayList<ArrayList<String>> gtDatcustos(String dbess, ArrayList<String> Sres, String stexa, String uniqidec, String uniqidecval) {
        String steta = "Active";

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String sqlite = "";
        Cursor res = null;

        if (uniqidecval.equals("nada")) {
            sqlite = "SELECT *, (custodatesqnty * custodatesval) AS debttotal FROM " + dbess + " debb LEFT JOIN custostbl debby " +
                    "ON debb.custodatecuscod = debby.custostblrecod LEFT JOIN prdctdetaz debbo " +
                    "ON debb.custodatesprdct = debbo.prdctdetazcode WHERE " + stexa + "= ?  " +
                    "ORDER BY custodatdate ASC";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{"Inactive"});

        } else {
            sqlite = "SELECT *, (custodatesqnty * custodatesval) AS debttotal FROM " + dbess + " debb LEFT JOIN custostbl debby " +
                    "ON debb.custodatecuscod = debby.custostblrecod LEFT JOIN prdctdetaz debbo " +
                    "ON debb.custodatesprdct = debbo.prdctdetazcode WHERE " + stexa + "= ? " +
                    "AND " + uniqidec + " = ? ORDER BY custodatdate ASC";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, uniqidecval});
        }


        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("custodatdate")) {
                            comblist.get(desca).add(resources.gtDpresocs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("custodatesqnty") || Sres.get(desca).equals("custodatesval") || Sres.get(desca).equals("debttotal")) {
                            comblist.get(desca).add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sres.get(desca)))));

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

    /*  Get single payment */
    public ArrayList<String> gpaySingrecd(String dbess, String uniqidec, String uniqidecval,
                                          ArrayList<String> Sres) {

        ArrayList<String> comblist = new ArrayList<>();


        String sqlp = "";
        Cursor res = null;

        sqlp = "SELECT * FROM " + dbess + " debb LEFT JOIN custostbl debby " +
                "ON debb.custodatpayccod = debby.custostblrecod WHERE " + uniqidec + "= ? ";

        res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{uniqidecval});


        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("custodatpaydate")) {
                            comblist.add(resources.gtDpresocs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("custodatpayamt")) {
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

    /*  Get single customer */
    public ArrayList<String> gcustoSingrecd(String dbess, String uniqidecval) {

        ArrayList<String> Sres = new ArrayList<>(Arrays.asList("custostbldate", "custostblname",
                "custostblphone", "custostblrecod"));
        ArrayList<String> comblist = new ArrayList<>();


        String sqlp = "";
        Cursor res = null;

        sqlp = "SELECT * FROM " + dbess + "   debby WHERE custostblrecod= ? ";

        res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{uniqidecval});


        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("custostbldate")) {
                            comblist.add(resources.gtDpresocs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

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

    /*  Get single debt */
    public ArrayList<String> gdebtSingle(String uniqidecval) {
        ArrayList<String> Sres = new ArrayList<>(Arrays.asList("custodatdate", "prdctdetazname",
                "custodatesval", "custodatesqnty", "custodatesrecod"));
        ArrayList<String> comblist = new ArrayList<>();


        String sqlp = "";
        Cursor res = null;

        sqlp = "SELECT * FROM custodates  debb LEFT JOIN prdctdetaz debby " +
                "ON debb.custodatesprdct = debby.prdctdetazcode WHERE custodatesrecod= ? ";

        res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{uniqidecval});


        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("custodatdate")) {
                            comblist.add(resources.gtDpresocs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("custodatesqnty") || Sres.get(desca).equals("custodatesval")) {
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
    public String mukDebts(ArrayList<String> paramsend, String detocs, String determ, String detocsa,
                           ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata, ArrayList<Integer> nwQntydata,
                           String cuscode) {
        String uniqIdec = resources.uniqueGen().toString();
        String resp = "nada";

        ArrayList<String> svarry = new ArrayList<>();
        ArrayList<String> svarrya = new ArrayList<>();
        ArrayList<String> svarryb = new ArrayList<>();

        String state = "Active";

        switch (detocs) {
            /* New records */
            case "newCustomer": {
                svarry.addAll(paramsend);
                svarrya = new ArrayList<>(Arrays.asList("nada", "", ""));
                svarryb = new ArrayList<>(Arrays.asList(" set date", " enter name", " enter phone"));


                break;
            }

            case "newPayment":
            case "editPayment": {
                svarry.addAll(paramsend);
                svarrya = new ArrayList<>(Arrays.asList("nada", "nada", ""));
                svarryb = new ArrayList<>(Arrays.asList(" set date", " select name", " enter amount paid"));


                break;
            }

            case "editCustos": {
                svarry.addAll(paramsend);
                svarrya = new ArrayList<>(Arrays.asList("nada", "", ""));
                svarryb = new ArrayList<>(Arrays.asList(" set date", "  enter name", "  enter phone"));

                break;
            }

            /* New records */
            case "lendCustos": {
                if (nwPrdctdata.size() > 0) {
                    svarry.add("cool");

                } else {
                    svarry.add("");

                }

                svarrya = new ArrayList<>(Arrays.asList(""));
                svarryb = new ArrayList<>(Arrays.asList("enter at least one record"));


                break;
            }

            case "editDebtrec": {
                svarry.addAll(paramsend);
                svarrya = new ArrayList<>(Arrays.asList("nada", "nada", "", ""));
                svarryb = new ArrayList<>(Arrays.asList(" set date", "  select product", "  enter price", "enter quantity"));

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
            Toast.makeText(datresContext, "Kindly: " + prompt, Toast.LENGTH_LONG).show();

        } else {
            if (determ.equals("debtsavrecd")) {
                /* User code */
                String usrcode = dbcluzz.getUsrdetal().get(3).replaceAll("[^a-zA-Z0-9]", "");

                /* New record code */
                StringBuilder sba = new StringBuilder();
                if (detocs.equals("newCustomer")) {
                    sba.append(usrcode + "CUSTO" + uniqIdec + "RECODE");

                }
                if (detocs.equals("newPayment")) {
                    sba.append(usrcode + "PAYMNT" + uniqIdec + "RECODE");

                }
                String reptcode = String.valueOf(sba);
                paramsend.add(reptcode);
                paramsend.add(state);

                insrtNwdebtswitch(paramsend, detocs, detocsa);
                resp = "true";

            } else if (determ.equals("debtsavrecda")) {
                insrtNwdebtprd(cuscode, nwPrdctdata, nwPrccdata, nwQntydata);
                resp = "true";

            } else if (determ.equals("debtupdatrecd")) {

                updatNwdebtswitch(paramsend, detocs, detocsa);
                resp = "true";
            } else if (determ.equals("debtupdatrecd")) {

                updatNwdebtswitch(paramsend, detocs, detocsa);
                resp = "true";
            }

        }

        return resp;
    }

    /* Insert record switch */
    public void insrtNwdebtswitch(ArrayList<String> usrarry, String determpr, String detocsa) {
        ArrayList<String> micer = new ArrayList<>();
        String dbess = "";
        switch (determpr) {

            case "newCustomer": {
                dbess = "custostbl";
                micer = new ArrayList<>(Arrays.asList("custostbldate", "custostblname", "custostblphone",
                        "custostblrecod", "custostblstate", "custostblshop"));

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Customer", usrarry.get(3));

                break;
            }

            case "newPayment": {
                dbess = "custodatpay";
                micer = new ArrayList<>(Arrays.asList("custodatpaydate", "custodatpayccod", "custodatpayamt",
                        "custodatpayrecod", "custodatpaystate", "custodatpayshop"));

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Payment", usrarry.get(3));

                break;
            }

            default:
                break;
        }

        /* Add shop code */
        usrarry.add(resources.gtAshopcode());

        boolean resp = insrtNwdebt(dbess, micer, usrarry);

        if (resp == true) {
            /* Refresh activity */
            resources.activResat(Debts.debtactiv, Debtsnwed.debtnewactive, Debts.class,
                    Debts.databManage.getText().toString(), Debts.datStatus.getText().toString(),
                    "nada");

        } else {
            Toast.makeText(datresContext, "An error occured while saving record", Toast.LENGTH_LONG).show();
        }
    }

    /* Update record switch */
    public void updatNwdebtswitch(ArrayList<String> usrarry, String determpr, String detocsa) {
        ArrayList<String> micer = new ArrayList<>();
        String dbess = "";
        String uniqident = "";

        switch (determpr) {

            case "editPayment": {
                dbess = "custodatpay";
                micer = new ArrayList<>(Arrays.asList("custodatpaydate", "custodatpayccod", "custodatpayamt"));

                uniqident = "custodatpayrecod";

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Payments", usrarry.get(usrarry.size() - 1));

                break;

            }

            case "editCustos": {
                dbess = "custostbl";
                micer = new ArrayList<>(Arrays.asList("custostbldate", "custostblname", "custostblphone"));

                uniqident = "custostblrecod";

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Customer", usrarry.get(usrarry.size() - 1));

                break;

            }

            case "editDebtrec": {
                dbess = "custodates";
                micer = new ArrayList<>(Arrays.asList("custodatdate", "custodatesprdct", "custodatesval", "custodatesqnty"));

                uniqident = "custodatesrecod";

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Debts", usrarry.get(usrarry.size() - 1));

                break;

            }

            default:
                break;
        }

        boolean resp = updatNwdebt(dbess, uniqident, micer, usrarry);

        if (resp == true) {
            /* Refresh activity */
            if (determpr.equals("editCustos")) {

            } else if (determpr.equals("editDebtrec")) {

            } else {
                resources.activResat(Debts.debtactiv, Debtsnwed.debtnewactive, Debts.class,
                        Debts.databManage.getText().toString(), Debts.datStatus.getText().toString(),
                        "nada");
            }

        } else {
            Toast.makeText(datresContext, "An error occured while updating record", Toast.LENGTH_LONG).show();
        }
    }

    /* Insert record */
    public boolean insrtNwdebt(String dbess, ArrayList<String> micer, ArrayList<String> usrarry) {
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

    /* Insert debt products */
    public boolean insrtNwdebtprd(String cuscode, ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata,
                                  ArrayList<Integer> nwQntydata) {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();

        String state = "Active";
        String shopcode = resources.getActivshop().get(3);

        if (nwPrdctdata.size() > 0) {
            for (int i = 0; i < nwPrdctdata.size(); ++i) {
                String uniqcode = resources.instPrdctcd(shopcode, i);

                ContentValues contentValues = new ContentValues();
                contentValues.put("custodatdate", resources.retStrdate().get(7));
                contentValues.put("custodatecuscod", cuscode);
                contentValues.put("custodatesprdct", resources.retuxPrdcode(nwPrdctdata.get(i)));
                contentValues.put("custodatesval", nwPrccdata.get(i));
                contentValues.put("custodatesqnty", nwQntydata.get(i));
                contentValues.put("custodatesrecod", uniqcode);
                contentValues.put("custodatestate", state);
                contentValues.put("custodateshop", resources.gtAshopcode());

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Debts", uniqcode);

                writableDatabase.insert("custodates", null, contentValues);

            }
        }

        return true;
    }

    /* Update record */
    public boolean updatNwdebt(String dbess, String uniqident,
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
