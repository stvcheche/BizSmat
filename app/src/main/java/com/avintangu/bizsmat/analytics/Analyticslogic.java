package com.avintangu.bizsmat.analytics;

import android.content.Context;
import android.database.Cursor;

import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.resources.Resocs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Analyticslogic {
    private static Context liticslogiContext;
    Resocs resources;
    Dbcluzz dbcluzz;
    DecimalFormat numformat = new DecimalFormat("#,###");
    SimpleDateFormat dtaformat = new SimpleDateFormat("yyyy-MM-dd");

    public Analyticslogic(Context context) {
        liticslogiContext = context;

        resources = new Resocs(liticslogiContext);
        dbcluzz = new Dbcluzz(liticslogiContext);

    }

    /*  Total sales with sales value */
    public ArrayList<ArrayList<String>> gtAllsaleval(ArrayList<String> Sres, String frodta, String todta) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String steta = "Active";
        String sqlite = "";
        Cursor res = null;

        sqlite = "SELECT *, SUM(salesrecdqnty) AS salesum, SUM(salesrecdprcc*salesrecdqnty) AS saleval  " +
                "FROM salesrecd debb LEFT JOIN prdctdetaz debby " +
                "ON debb.salesrecdprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo " +
                "ON debb.salesrecdshop = debbo.usrshopuniq WHERE salesrecdstate= ? " +
                "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ")  " +
                "AND salesrecddate BETWEEN ? AND ? GROUP BY debb.salesrecdprdcod";

        res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, frodta, todta});


        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("salesum") || Sres.get(desca).equals("saleval")) {
                            comblist.get(desca).add(numformat.format(res.getInt(res.getColumnIndex(Sres.get(desca)))));

                        } else {
                            comblist.get(desca).add(res.getString(res.getColumnIndex(Sres.get(desca))));

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

    /*  Total sales with sales value by category */
    public ArrayList<ArrayList<String>> gtAllsalevalcatry(ArrayList<String> Sres, String frodta, String todta) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String steta = "Active";
        String sqlite = "";
        Cursor res = null;

        sqlite = "SELECT *, SUM(salesrecdqnty) AS salesum, SUM(salesrecdprcc*salesrecdqnty) AS saleval  " +
                "FROM salesrecd debb LEFT JOIN prdctdetaz debby " +
                "ON debb.salesrecdprdcod = debby.prdctdetazcode LEFT JOIN prdctdcateri debbo " +
                "ON debby.prdctdetazcateg = debbo.prdctdcateriuniq LEFT JOIN usrshops debbo " +
                "ON debb.salesrecdshop = debbo.usrshopuniq WHERE salesrecdstate= ? " +
                "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                "AND salesrecddate BETWEEN ? AND ? GROUP BY debbo.prdctdcateriuniq";

        res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, frodta, todta});


        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("salesum") || Sres.get(desca).equals("saleval")) {
                            comblist.get(desca).add(numformat.format(res.getInt(res.getColumnIndex(Sres.get(desca)))));

                        } else {
                            comblist.get(desca).add(res.getString(res.getColumnIndex(Sres.get(desca))));

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

    /*  Total sales profit */
    public ArrayList<ArrayList<String>> gtAllsalprofit(ArrayList<String> Sres, String frodta, String todta) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String steta = "Active";
        String sqlite = "";
        Cursor res = null;

        sqlite = "SELECT *, SUM(salesrecdprcc*salesrecdqnty) AS saleval, SUM((salesrecdprcc-salesrecdbprc)*salesrecdqnty) AS profitrelis  " +
                "FROM salesrecd debb LEFT JOIN prdctdetaz debby " +
                "ON debb.salesrecdprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo " +
                "ON debb.salesrecdshop = debbo.usrshopuniq WHERE salesrecdstate= ? " +
                "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") " +
                "AND salesrecddate BETWEEN ? AND ? GROUP BY debb.salesrecdprdcod";

        res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, frodta, todta});


        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("saleval") || Sres.get(desca).equals("profitrelis")) {
                            comblist.get(desca).add(numformat.format(res.getInt(res.getColumnIndex(Sres.get(desca)))));

                        } else {
                            comblist.get(desca).add(res.getString(res.getColumnIndex(Sres.get(desca))));

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

    /* Total sales */
    public Float genSales(Date frodta, Date todta, String paramt) {
        Float mnthsales = 0f;
        String state = "Active";

        String sql = "SELECT *, SUM(salesrecdqnty) AS salesum FROM salesrecd debb LEFT JOIN prdctdetaz debby " +
                "ON debb.salesrecdprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo " +
                "ON debb.salesrecdshop = debbo.usrshopuniq WHERE salesrecdstate= ? " +
                "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ") AND salesrecddate BETWEEN ? AND ? ";

        Cursor res = dbcluzz.getReadableDatabase().rawQuery(sql, new String[]{state, dtaformat.format(frodta), dtaformat.format(todta)});


        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    mnthsales = res.getFloat(res.getColumnIndex("salesum"));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling
        } finally {
            if (res != null) {
                res.close();
            }
        }


        return mnthsales;
    }

    /* Total revenue */
    public Float genReve(Date frodta, Date todta, String paramt) {
        Float mnthsales = 0f;
        String state = "Active";

        String sql = "SELECT *, SUM(salesrecdprcc*salesrecdqnty) AS revenuesum FROM salesrecd debb LEFT JOIN prdctdetaz debby " +
                "ON debb.salesrecdprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo  " +
                "ON debb.salesrecdshop = debbo.usrshopuniq WHERE salesrecdstate= ? " +
                "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ")  AND salesrecddate BETWEEN ? AND ? ";

        Cursor res = dbcluzz.getReadableDatabase().rawQuery(sql, new String[]{state, dtaformat.format(frodta), dtaformat.format(todta)});


        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    mnthsales = res.getFloat(res.getColumnIndex("revenuesum"));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling
        } finally {
            if (res != null) {
                res.close();
            }
        }


        return mnthsales;
    }

    /* Total profit */
    public Float genProfit(Date frodta, Date todta, String paramt) {
        Float mnthsales = 0f;
        String state = "Active";

        String sql = "SELECT *, SUM((salesrecdprcc-salesrecdbprc)*salesrecdqnty) AS profitsum FROM salesrecd debb LEFT JOIN prdctdetaz debby " +
                "ON debb.salesrecdprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo  " +
                "ON debb.salesrecdshop = debbo.usrshopuniq WHERE salesrecdstate= ? " +
                "AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ")  AND salesrecddate BETWEEN ? AND ? ";

        Cursor res = dbcluzz.getReadableDatabase().rawQuery(sql, new String[]{state, dtaformat.format(frodta), dtaformat.format(todta)});


        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    mnthsales = res.getFloat(res.getColumnIndex("profitsum"));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling
        } finally {
            if (res != null) {
                res.close();
            }
        }


        return mnthsales;
    }

    /* Total stocks */
    public Float stkSum(Date frodta, Date todta, String paramt) {
        Float mnthsales = 0f;
        String state = "Active";

        String sql = "SELECT *, SUM(inventblqnty) AS stksum FROM inventbl debb LEFT JOIN prdctdetaz debby " +
                "ON debb.inventblprdcod = debby.prdctdetazcode LEFT JOIN usrshops debbo ON debb.inventblshop = debbo.usrshopuniq " +
                "WHERE inventblstate= ? AND debbo.usrshopuniq IN(" + resources.gtCombshcode() + ")  " +
                "AND inventbldate BETWEEN ? AND ? ";

        Cursor res = dbcluzz.getReadableDatabase().rawQuery(sql, new String[]{state, dtaformat.format(frodta), dtaformat.format(todta)});


        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    mnthsales = res.getFloat(res.getColumnIndex("stksum"));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling
        } finally {
            if (res != null) {
                res.close();
            }
        }


        return mnthsales;
    }

    /*  Stocks balance */
    public Float genStocks(Date frodta, Date todta, String paramt) {
        Float stkbal = stkSum(frodta, todta, paramt) - genSales(frodta, todta, paramt);

        return stkbal;
    }

}
