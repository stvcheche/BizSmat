package com.avintangu.bizsmat.inventory;

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

public class Inventresoc {
    DecimalFormat formatter = new DecimalFormat("#,###");
    private static Context invresContext;
    Resocs resources;
    Dbcluzz dbcluzz;

    public Inventresoc(Context context) {
        invresContext = context;

        resources = new Resocs(invresContext);
        dbcluzz = new Dbcluzz(invresContext);

    }

    /*  Get stocks levels */
    public ArrayList<ArrayList<String>> gtStkBal(String srchdeterm, String steta) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < 4; nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String sqlite = "";
        Cursor res = null;

        if (srchdeterm.equals("nada") || srchdeterm.equals("")) {
            sqlite = "SELECT * FROM prdctdetaz debb LEFT JOIN usrshops debbe  ON debb.prdctdetazshop = debbe.usrshopuniq LEFT JOIN " +
                    "(SELECT *, IFNULL(SUM(salesrecdqnty),0) AS salsum FROM salesrecd WHERE salesrecdstate= ? GROUP BY salesrecdprdcod)  AS debby ON debb.prdctdetazcode = debby.salesrecdprdcod LEFT JOIN " +
                    "(SELECT *, IFNULL(SUM(inventblqnty),0) AS invsum FROM inventbl WHERE inventblstate = ? GROUP BY inventblprdcod)  AS debbo ON debb.prdctdetazcode = debbo.inventblprdcod LEFT JOIN " +
                    "(SELECT *, IFNULL(SUM(custodatesqnty),0) AS debtsum FROM custodates WHERE custodatestate = ? GROUP BY custodatesprdct) AS debbi ON debb.prdctdetazcode = debbi.custodatesprdct " +
                    "WHERE debb.prdctdetazstate = ? AND debbe.usrshopuniq IN(" + resources.gtCombshcode() + ") ORDER BY debb.prdctdetazid";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, steta, steta, steta});

        } else {
            sqlite = "SELECT * FROM prdctdetaz debb LEFT JOIN usrshops debbe ON debb.prdctdetazshop = debbe.usrshopuniq  LEFT JOIN " +
                    "(SELECT *, IFNULL(SUM(salesrecdqnty),0) AS salsum FROM salesrecd WHERE salesrecdstate= ? GROUP BY salesrecdprdcod)  AS debby ON debb.prdctdetazcode = debby.salesrecdprdcod LEFT JOIN " +
                    "(SELECT *, IFNULL(SUM(inventblqnty),0) AS invsum FROM inventbl WHERE inventblstate = ? GROUP BY inventblprdcod)  AS debbo ON debb.prdctdetazcode = debbo.inventblprdcod LEFT JOIN " +
                    "(SELECT *, IFNULL(SUM(custodatesqnty),0) AS debtsum FROM custodates WHERE custodatestate = ? GROUP BY custodatesprdct) AS debbi ON debb.prdctdetazcode = debbi.custodatesprdct " +
                    "WHERE debb.prdctdetazstate = ? AND debbe.usrshopuniq IN(" + resources.gtCombshcode() + ") ORDER BY debb.prdctdetazid";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, steta, steta, steta, "%" + srchdeterm + "%"});

        }

        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    comblist.get(0).add(res.getString(res.getColumnIndexOrThrow("prdctdetazname")));
                    comblist.get(1).add(formatter.format(res.getInt(res.getColumnIndexOrThrow("salsum"))));
                    comblist.get(2).add(formatter.format(res.getInt(res.getColumnIndexOrThrow("debtsum"))));
                    comblist.get(3).add(formatter.format(res.getInt(res.getColumnIndexOrThrow("invsum")) -
                            (res.getInt(res.getColumnIndexOrThrow("salsum")) + res.getInt(res.getColumnIndexOrThrow("debtsum")))));

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

    /* Get all stocks entered*/
    public ArrayList<ArrayList<String>> ghAllinvent(String dbess, String stexa, String steta, ArrayList<String> Sres,
                                                    String srchdeterm, String frodta, String todta) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String sqlite = "";
        Cursor res = null;

        if (srchdeterm.equals("nada") || srchdeterm.equals("")) {
            sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN prdctdetaz debby " +
                    "ON debb.inventblprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo " +
                    "ON debb.inventblshop = debbo.usrshopuniq WHERE " + stexa + "= ? " +
                    "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                    "AND inventbldate BETWEEN ? AND ? ORDER BY inventbldate ASC";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, frodta, todta});

        } else {
            sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN prdctdetaz debby " +
                    "ON debb.inventblprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo  ON debb.inventblshop = debbo.usrshopuniq " +
                    "WHERE " + stexa + "= ? AND  " + Sres.get(0) + " LIKE ?  AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") AND inventbldate BETWEEN ? AND ?  " +
                    "OR " + stexa + "= ? AND  " + Sres.get(1) + " LIKE ?  AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") AND inventbldate BETWEEN ? AND ? " +
                    "OR " + stexa + "= ? AND  " + Sres.get(2) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") AND inventbldate BETWEEN ? AND ?  " +
                    "OR " + stexa + "= ? AND  " + Sres.get(3) + " LIKE ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") AND inventbldate BETWEEN ? AND ? ";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, "%" + srchdeterm + "%", frodta, todta,
                    steta, "%" + srchdeterm + "%", frodta, todta,
                    steta, "%" + srchdeterm + "%" + "%", frodta, todta,
                    steta, "%" + srchdeterm + "%", frodta, todta});

        }


        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("inventblqnty") || Sres.get(desca).equals("inventblbprc")) {
                            comblist.get(desca).add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("inventbldate")) {
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
    public ArrayList<String> gtinvSingrecd(String mims, String dbess, String uniqidec, String uniqidecval,
                                           ArrayList<String> Sres) {

        ArrayList<String> comblist = new ArrayList<>();


        String sqlp = "";
        Cursor res = null;

        if (mims.equals("inventSrecoda")) {
            sqlp = "SELECT * FROM " + dbess + " debb LEFT JOIN prdctdetaz debby " +
                    "ON debb.inventblprdcod = debby.prdctdetazcode WHERE " + uniqidec + "= ? ";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{uniqidecval});

        }

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("inventbldate")) {
                            comblist.add(resources.gtDpresocs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("inventblqnty") || Sres.get(desca).equals("inventblbprc")) {
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
    public void mukInvent(ArrayList<String> paramsend, ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata,
                          ArrayList<Integer> nwSlprcdata, ArrayList<Integer> nwQntydata, String detocs, String determ,
                          String detocsa) {
        ArrayList<String> svarry = new ArrayList<>();
        ArrayList<String> svarrya = new ArrayList<>();
        ArrayList<String> svarryb = new ArrayList<>();

        switch (detocs) {
            /* New records */
            case "newStocks": {
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
            case "editStocks": {
                svarry.addAll(paramsend);
                svarrya = new ArrayList<>(Arrays.asList("nada", "", "", "", ""));
                svarryb = new ArrayList<>(Arrays.asList("select date", " select product", " enter buying price",
                        " enter selling price", " enter quantity"));


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
            Toast.makeText(invresContext, "Kindly: " + prompt, Toast.LENGTH_LONG).show();

        } else {
            if (determ.equals("stoksavrecd")) {
                insrtNwinvswitch(nwPrdctdata, nwPrccdata, nwSlprcdata, nwQntydata, detocs);

            } else if (determ.equals("stokupdatrecd")) {
                updatNwinvswitch(paramsend, detocs, detocsa);

            }

        }
    }

    /* Insert record switch */
    public void insrtNwinvswitch(ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata,
                                 ArrayList<Integer> nwSlprcdata, ArrayList<Integer> nwQntydata,
                                 String determpr) {
        ArrayList<String> micer = new ArrayList<>();
        String dbess = "";
        switch (determpr) {

            case "newStocks": {
                dbess = "inventbl";
               /* micer = new ArrayList<>(Arrays.asList("salesrecddate", "salesrecdprdcod", "salesrecdprcc",
                        "salesrecdqnty", "salesrecdrecod", "salesrecdstate"));*/

                break;
            }

            default:
                break;
        }

        boolean resp = insrtNwinvent(dbess, nwPrdctdata, nwPrccdata, nwSlprcdata, nwQntydata);

        if (resp == true) {
            /* Refresh activity */
            resources.activResat(Inventory.inventactiv, Inventnwed.inventnewrecod, Inventory.class,
                    Inventory.invtabManage.getText().toString(), Inventory.invStatus.getText().toString(),
                    "nada");

        } else {
            Toast.makeText(invresContext, "An error occured while saving record", Toast.LENGTH_LONG).show();
        }
    }

    /* Update record switch */
    public void updatNwinvswitch(ArrayList<String> usrarry, String determpr, String detocsa) {
        ArrayList<String> micer = new ArrayList<>();
        String dbess = "";
        String uniqident = "";

        switch (determpr) {

            case "editStocks": {
                dbess = "inventbl";
                micer = new ArrayList<>(Arrays.asList("inventbldate", "inventblprdcod", "inventblbprc",
                        "inventblqnty"));

                uniqident = "inventblrecod";

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Inventory", usrarry.get(usrarry.size() - 1));

                break;

            }

            default:
                break;
        }

        boolean resp = updatNwinvent(dbess, uniqident, micer, usrarry);

        if (resp == true) {
            /* Refresh activity */
            resources.activResat(Inventory.inventactiv, Inventnwed.inventnewrecod, Inventory.class,
                    Inventory.invtabManage.getText().toString(), Inventory.invStatus.getText().toString(),
                    "nada");

        } else {
            Toast.makeText(invresContext, "An error occured while updating record", Toast.LENGTH_LONG).show();
        }
    }

    /* Insert inventory */
    public boolean insrtNwinvent(String dbess, ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata,
                                 ArrayList<Integer> nwSlprcdata, ArrayList<Integer> nwQntydata) {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();

        String state = "Active";
        String shopcode = resources.getActivshop().get(3);
        com.avintangu.bizsmat.inventory.Inventresoca invresoca = new com.avintangu.bizsmat.inventory.Inventresoca(invresContext);
        invresoca.updatSlprcc(nwPrdctdata, nwSlprcdata);

        if (nwPrdctdata.size() > 0) {
            for (int i = 0; i < nwPrdctdata.size(); ++i) {
                String uniqcode = instInvctcd(shopcode, i);
                ContentValues contentValues = new ContentValues();
                contentValues.put("inventbldate", resources.retStrdate().get(7));
                contentValues.put("inventblprdcod", resources.retuxPrdcode(nwPrdctdata.get(i)));
                contentValues.put("inventblbprc", nwPrccdata.get(i));
                contentValues.put("inventblqnty", nwQntydata.get(i));
                contentValues.put("inventblrecod", uniqcode);
                contentValues.put("inventblstate", state);
                contentValues.put("inventblshop", resources.gtAshopcode());

                /* Insert updated record inuque id */
                resources.insertUpdatcodes(resources.retStrdate().get(7), "Inventory", uniqcode);

                writableDatabase.insert(dbess, null, contentValues);

            }
        }

        return true;
    }

    /* Update record */
    public boolean updatNwinvent(String dbess, String uniqident, ArrayList<String> micer, ArrayList<String> usrarry) {
        com.avintangu.bizsmat.inventory.Inventresoca invresoca = new com.avintangu.bizsmat.inventory.Inventresoca(invresContext);

        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = false;

        String slprcc = usrarry.get(3);
        String entyuniq = usrarry.get(usrarry.size() - 1);

        usrarry.remove(3);
        usrarry.remove(usrarry.size() - 1);
        invresoca.updatSlprcca(usrarry.get(1), slprcc);

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

    /*Generate product unique code*/
    public String instInvctcd(String shopcode, Integer n) {
        Long uniqueGen = resources.uniqueGen();
        StringBuilder sb = new StringBuilder();

        String[] uniqCover = new String[]{"Ainv", "Binv", "Cinv", "Dinv", "Einv", "Finv", "Ginv", "Hinv", "Iinv", "Jinv",
                "Kinv", "Linv", "Minv", "Ninv", "Oinv", "Pinv", "Qinv", "Rinv", "Sinv", "Tinv", "Uinv", "Vinv", "Winv", "Xinv", "Yinv", "Zinv", "AAinv",
                "ABinv", "ACinv", "ADinv", "AEinv", "AFinv", "AGinv", "AHinv", "AIinv", "AJinv", "AKinv", "ALinv", "AMinv", "ANinv", "AOinv",
                "APinv", "AQinv", "ARinv", "ASinv", "ATinv", "AUinv", "AVinv", "AWinv", "AXinv", "AYinv", "AZinv", "BAinv", "BBinv", "BCinv",
                "BDinv", "BEinv", "BFinv", "BGinv", "BHinv", "BIinv", "BJinv", "BKinv", "BLinv", "BMinv", "BNinv", "BOinv", "BPinv", "BQinv", "BRinv",
                "BSinv", "BTinv", "BUinv", "BVinv", "BWinv", "BXinv", "BYinv", "BZinv", "CAinv", "CBinv", "CCinv", "CDinv", "CEinv", "CFinv", "CGinv",
                "CHinv", "CIinv", "CJinv", "CKinv", "CLinv", "CMinv", "CNinv", "COinv", "CPinv", "CQinv", "CRinv", "CSinv", "CTinv", "CUinv", "CVinv",
                "CWinv", "CXinv", "CYinv", "CZinv", "DAinv", "DBinv", "DCinv", "DDinv", "DEinv", "DFinv", "DGinv", "DHinv", "DIinv", "DJinv", "DKinv",
                "DLinv", "DMinv", "DNinv", "DOinv", "DPinv", "DQinv", "DRinv", "DSinv", "DTinv", "DUinv", "DVinv", "DWinv", "DXinv", "DYinv", "DZinv",
                "EAinv", "EBinv", "ECinv", "EDinv", "EEinv", "EFinv", "EGinv", "EHinv", "EIinv", "EJinv", "EKinv", "ELinv", "EMinv", "ENinv", "EOinv", "EPinv", "EQinv",
                "ERinv", "ESinv", "ETinv", "EUinv", "EVinv", "EWinv", "EXinv", "EYinv", "EZinv", "FAinv", "FBinv", "FCinv", "FDinv", "FEinv", "FFinv", "FGinv", "FHinv",
                "FIinv", "FJinv", "FKinv", "FLinv", "FMinv", "FNinv", "FOinv", "FPinv", "FQinv", "FRinv", "FSinv", "FTinv", "FUinv", "FVinv", "FWinv", "FXinv", "FYinv",
                "FZinv", "GAinv", "GBinv", "GCinv", "GDinv", "GEinv", "GFinv", "GGinv", "GHinv", "GIinv", "GJinv", "GKinv", "GLinv", "GMinv", "GNinv", "GOinv",
                "GPinv", "GQinv", "GRinv", "GSinv", "GTinv", "GUinv", "GVinv", "GWinv", "GXinv", "GYinv", "GZinv"};

        sb.append(shopcode + uniqueGen + uniqCover[n]);

        return String.valueOf(sb);
    }


}
