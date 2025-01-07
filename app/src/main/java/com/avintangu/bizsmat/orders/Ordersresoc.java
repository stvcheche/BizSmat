package com.avintangu.bizsmat.orders;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.launch.Progressbar;
import com.avintangu.bizsmat.resources.Resocs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Ordersresoc {
    private static Progressbar progressbar;
    private static Context ordresContext;
    Resocs resources;
    Dbcluzz dbcluzz;

    DecimalFormat formatter = new DecimalFormat("#,###");

    public Ordersresoc(Context context) {
        ordresContext = context;

        resources = new Resocs(ordresContext);
        dbcluzz = new Dbcluzz(ordresContext);

    }

    /*Get all distributors */
    public ArrayList<String> getaAdistros() {
        ArrayList<String> list = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM distributors ", null);

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    list.add(res.getString(res.getColumnIndexOrThrow("distroscname"))
                            + " -> " +
                            res.getString(res.getColumnIndexOrThrow("distrosctown")));
                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling
        } finally {
            if (res != null) {
                res.close();
            }
        }

        if (list.size() > 0) {
            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);

        } else {
            list.add(0, "There are no distributors");


        }

        return list;
    }

    /* Get all records*/
    public ArrayList<ArrayList<String>> gtArecods(String dbess, String stexa, String steta, ArrayList<String> Sres,
                                                  String srchdeterm, String mims, String frodta, String todta,
                                                  String distrospr) {

        ArrayList<ArrayList<String>> comblist = new ArrayList<>();
        for (int nes = 0; nes < Sres.size(); nes++) {
            ArrayList<String> usrdesc = new ArrayList<>();
            comblist.add(usrdesc);

        }

        String sqlite = "";
        Cursor res = null;

        if (srchdeterm.equals("nada") || srchdeterm.equals("")) {
            if (mims.equals("ordeSrecod")) {
                sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN distributors debbe " +
                        "ON debb.pricescdist = debbe.distroscuniq WHERE " + stexa + "= ? AND " +
                        "distroscname = ?";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, distrospr});

            } else if (mims.equals("ordeSrecoda")) {
                sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN " +
                        "(SELECT orddetaluniq, COUNT(orddetaluniq) AS prdtotal FROM ordesprdcts GROUP BY orddetaluniq) AS debbe " +
                        "ON debb.ordetaluniq = debbe.orddetaluniq LEFT JOIN distributors debbo " +
                        "ON debb.ordetaldistos = debbo.distroscuniq WHERE " + stexa + " IN(" + steta + ") ORDER BY datetime(ordetaldta) ASC";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, null);

            }

        } else {
            if (mims.equals("ordeSrecod")) {

                sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN distributors debbe " +
                        "ON debb.pricescdist = debbe.distroscuniq WHERE " + stexa + "= ? AND " +
                        "distroscname = ? AND  " + Sres.get(0) + " LIKE ?";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, distrospr, "%" + srchdeterm + "%" + "%"});

            } else if (mims.equals("ordeSrecoda")) {

                sqlite = "SELECT * FROM " + dbess + " debb LEFT JOIN " +
                        "(SELECT orddetaluniq, COUNT(orddetaluniq) AS prdtotal FROM ordesprdcts GROUP BY orddetaluniq) AS debbe " +
                        "ON debb.ordetaluniq = debbe.orddetaluniq LEFT JOIN distributors debbo " +
                        "ON debb.ordetaldistos = debbo.distroscuniq WHERE " + Sres.get(1) + " LIKE ? AND " + stexa + " IN(" + steta + ") " +
                        "datetime(ordetaldta) ASC";

                res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{"%" + srchdeterm + "%" + "%"});

            }
        }


        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("pricescprcc") || Sres.get(desca).equals("prdtotal")) {
                            comblist.get(desca).add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sres.get(desca)))));

                        } else if (Sres.get(desca).equals("ordetaldta")) {
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

    /* Get single order */
    public ArrayList<String> gtordSingrecd(String mims, String dbess, String uniqidec, String uniqidecval,
                                           ArrayList<String> Sres) {
        ArrayList<String> comblist = new ArrayList<>();


        String sqlp = "";
        Cursor res = null;

        if (mims.equals("ordeSrecoda")) {
            sqlp = "SELECT * FROM " + dbess + " debb LEFT JOIN usrshops debbe " +
                    "ON debb.ordetaloutlt = debbe.usrshopuniq LEFT JOIN distributors debbo " +
                    "ON debb.ordetaldistos = debbo.distroscuniq WHERE " + uniqidec + "= ? ";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{uniqidecval});


        }

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {

                        if (Sres.get(desca).equals("ordetaldta")) {
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

    public ArrayList<ArrayList<String>> gtordSingrecda(String mims, String dbess, String uniqidec, String uniqidecval,
                                                       ArrayList<String> Sresa) {
        ArrayList<ArrayList<String>> comblist = new ArrayList<>();

        for (int bk = 0; bk < Sresa.size(); bk++) {
            ArrayList<String> kip = new ArrayList<>();
            comblist.add(kip);

        }


        String sqlp = "";
        Cursor res = null;

        if (mims.equals("ordeSrecoda")) {
            sqlp = "SELECT * FROM " + dbess + " debb LEFT JOIN ordesdetal debbe " +
                    "ON debb.orddetaluniq = debbe.ordetaluniq LEFT JOIN pricelist zebb " +
                    "ON debb.ordprdctprd = zebb.pricescprdcod WHERE " + uniqidec + "= ? ";

            res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{uniqidecval});


        }

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sresa.size(); desca++) {

                        if (Sresa.get(desca).equals("ordprdctprcc") || Sresa.get(desca).equals("ordprdctqnty")) {
                            comblist.get(desca).add(formatter.format(res.getInt(res.getColumnIndexOrThrow(Sresa.get(desca)))));

                        } else {
                            comblist.get(desca).add(res.getString(res.getColumnIndexOrThrow(Sresa.get(desca))));

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

    public ArrayList<String> gtordSingrecdb(String distname, ArrayList<String> Sres) {
        ArrayList<String> comblist = new ArrayList<>();

        String sqlp = "SELECT * FROM distributors WHERE distroscname = ? ";
        Cursor res = dbcluzz.getReadableDatabase().rawQuery(sqlp, new String[]{distname});


        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    for (Integer desca = 0; desca < Sres.size(); desca++) {
                        comblist.add(res.getString(res.getColumnIndexOrThrow(Sres.get(desca))));

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
    public void mukOrders(ArrayList<String> paramsend, ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata,
                          ArrayList<Integer> nwQntydata, String detocs, String determ, String detocsa,
                          ArrayList<String> nwUniqidec) {
        ArrayList<String> svarry = new ArrayList<>();
        ArrayList<String> svarrya = new ArrayList<>();
        ArrayList<String> svarryb = new ArrayList<>();

        switch (detocs) {
            /* New records */
            case "newOrder":
            case "editord": {
                svarry.add(paramsend.get(2));
                svarry.add(paramsend.get(3));
                svarry.add(paramsend.get(4));

                if (nwPrdctdata.size() > 0) {
                    svarry.add("cool");

                } else {
                    svarry.add("nada");

                }

                svarrya = new ArrayList<>(Arrays.asList("nada", "nada", "Select", "nada"));
                svarryb = new ArrayList<>(Arrays.asList("select outlet", "select distributor", "select payment method",
                        "enter at least one order"));


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
            Toast.makeText(ordresContext, "Kindly: " + prompt, Toast.LENGTH_LONG).show();

        } else {
            if (detocs.equals("newOrder")) {
                insrtupdatNwordswitch(paramsend, nwPrdctdata, nwPrccdata, nwQntydata, detocs, detocs, nwUniqidec);

            } else if (detocs.equals("editord")) {
                insrtupdatNwordswitch(paramsend, nwPrdctdata, nwPrccdata, nwQntydata, detocs, detocsa, nwUniqidec);

            }

        }
    }

    /* Insert record switch */
    public void insrtupdatNwordswitch(ArrayList<String> paramsend, ArrayList<String> nwPrdctdata, ArrayList<Integer> nwPrccdata,
                                      ArrayList<Integer> nwQntydata, String determpr, String determpra, ArrayList<String> nwUniqidec) {
        /* Orders object */
        JSONObject ordetalobj = new JSONObject();

        /* Get user details */
        ArrayList<String> usrdetals = dbcluzz.getUsrdetal();
        JSONArray usrvalsjson = new JSONArray();

        for (int y = 0; y < usrdetals.size(); y++) {
            usrvalsjson.put(dbcluzz.strEncodbs(usrdetals.get(y)));
        }

        /* Order details */
        JSONArray combordetal = new JSONArray();
        JSONArray ordetalarry = new JSONArray();
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(0)));
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(1)));
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(2)));
        ordetalarry.put(dbcluzz.strEncodbs(usrdetals.get(3)));
        ordetalarry.put(dbcluzz.strEncodbs(usrdetals.get(1)));
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(3)));
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(6)));
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(5)));
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(4)));
        ordetalarry.put(dbcluzz.strEncodbs(usrdetals.get(3)));
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(7)));
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(8)));
        ordetalarry.put(dbcluzz.strEncodbs(paramsend.get(9)));

        /* Orders products object */
        JSONArray combprdcts = new JSONArray();
        JSONArray prdctcodes = new JSONArray();
        for (int ghem = 0; ghem < nwPrdctdata.size(); ghem++) {

            JSONArray injarry = new JSONArray();
            injarry.put(dbcluzz.strEncodbs(resources.retuxPrdcodecs(nwPrdctdata.get(ghem), paramsend.get(3))));
            injarry.put(dbcluzz.strEncodbs(nwPrccdata.get(ghem).toString()));
            injarry.put(dbcluzz.strEncodbs(nwQntydata.get(ghem).toString()));
            injarry.put(dbcluzz.strEncodbs(nwUniqidec.get(ghem)));
            injarry.put(dbcluzz.strEncodbs(paramsend.get(6)));
            prdctcodes.put(dbcluzz.strEncodbs(nwUniqidec.get(ghem)));

            combprdcts.put(injarry);
        }

        try {
            combordetal.put(ordetalarry);

            ordetalobj.put("determ", dbcluzz.strEncodbs("postOrder"));
            ordetalobj.put("uniqcode", dbcluzz.strEncodbs(paramsend.get(6)));
            ordetalobj.put("uniqcodearry", prdctcodes);
            ordetalobj.put("vals", usrvalsjson);
            ordetalobj.put("ordetailsarry", combordetal);
            ordetalobj.put("ordprdctarry", combprdcts);

        } catch (JSONException e) {
        }

        /* Post order */
        postOrder(ordetalobj);

    }

    /*  Upload order */
    public void postOrder(JSONObject postData) {
        String mssg = "Posting data, please wait.....";

        progressbar = new Progressbar(ordresContext, mssg, "nan");
        progressbar.showProgress();

        ArrayList<String> ureca = dbcluzz.UrlGen();

        if (dbcluzz.isConnectedToInternet()) {
            new Ordersresoc.postOrdersync().execute(ureca.get(0), postData.toString());

        } else {
            Toast.makeText(ordresContext, "No internet connection", Toast.LENGTH_LONG).show();
            progressbar.hideProgress();

        }
    }

    private class postOrdersync extends AsyncTask<String, Void, String> {
        String determ;

        public postOrdersync() {
            this.determ = determ;
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            HttpURLConnection httpURLConnection = null;

            try {
                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    response += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!result.equals(null)) {
                progressbar.hideProgress();

                try {
                    JSONObject repocse = new JSONObject(result);
                    String detocs = repocse.getString("detocs");

                    if (detocs.equals("cool")) {
                        JSONObject ordetails = repocse.getJSONObject("ordetails");
                        JSONObject ordrpdcts = repocse.getJSONObject("ordrpdcts");
                        String uniqvalcos = dbcluzz.strDecodbs(repocse.getString("uniqcodesc"));

                        /* Delete records */
                        ArrayList<String> dbm = new ArrayList<>(Arrays.asList("ordesdetal", "ordesprdcts"));
                        ArrayList<String> uniqidec = new ArrayList<>(Arrays.asList("ordetaluniq", "orddetaluniq"));
                        ArrayList<String> uniqval = new ArrayList<>(Arrays.asList(uniqvalcos, uniqvalcos));

                        dbcluzz.delheal(dbm, uniqidec, uniqval);

                        /* update records */
                        dbcluzz.insertOrdetails(ordetails);
                        dbcluzz.insertOrdprdcts(ordrpdcts);

                        /* Refresh activity */
                        resources.activResat(Orders.ordesactive, Ordesnew.ordnewrecod, Orders.class,
                                Orders.ordtabManage.getText().toString(), Orders.ordStatus.getText().toString(),
                                "nada");

                        Toast.makeText(ordresContext, "Order posted successfully", Toast.LENGTH_LONG).show();

                    } else if (detocs.equals("null")) {
                        Toast.makeText(ordresContext, "An error occured while posting order",
                                Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {

                }

            } else {
                progressbar.hideProgress();

            }
        }
    }

    /* Referesh distributors list with the price-lists */
    public void gtDistprcc(JSONObject postData, String detremic) {
        String mssg = "Refreshing price-list, please wait.....";

        progressbar = new Progressbar(ordresContext, mssg, "nan");
        progressbar.showProgress();

        ArrayList<String> ureca = dbcluzz.UrlGen();

        if (dbcluzz.isConnectedToInternet()) {
            new Ordersresoc.gtDistprccsync(detremic).execute(ureca.get(0), postData.toString());

        } else {
            Toast.makeText(ordresContext, "Please connect to the internet", Toast.LENGTH_LONG).show();
            progressbar.hideProgress();

        }
    }

    private class gtDistprccsync extends AsyncTask<String, Void, String> {
        String detremicpr;

        public gtDistprccsync(String detremic) {
            this.detremicpr = detremic;
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            HttpURLConnection httpURLConnection = null;

            try {
                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    response += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!result.equals(null)) {

                try {
                    JSONObject repocse = new JSONObject(result);
                    String detocs = repocse.getString("detocs");

                    if (detocs.equals("cool")) {
                        JSONObject distributors = repocse.getJSONObject("distributors");
                        JSONObject pricelist = repocse.getJSONObject("pricelist");

                        ArrayList<String> dbm = new ArrayList<>(Arrays.asList("distributors", "pricelist"));
                        dbcluzz.delall(dbm);

                        dbcluzz.insertDistributors(distributors);
                        dbcluzz.insertPricelist(pricelist);

                        if (detremicpr.equals("loadActive")) {
                            /* Start activity */
                            Intent intent = new Intent(ordresContext.getApplicationContext(), Orders.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("determtabmng", "ordeSrecod");
                            bundle.putString("determstate", "Active");
                            bundle.putString("determstata", "load");
                            intent.putExtras(bundle);
                            ordresContext.startActivity(intent);

                        } else if (detremicpr.equals("refreshactive")) {
                            /* Refresh activity */
                            ordesResat(Orders.ordesactive, Orders.class, Orders.ordtabManage.getText().toString(),
                                    Orders.ordStatus.getText().toString(), "load");

                        }


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressbar.hideProgress();
                            }
                        }, 1000);

                        Toast.makeText(ordresContext, "Update success", Toast.LENGTH_LONG).show();

                    } else if (detocs.equals("null")) {
                        Toast.makeText(ordresContext, "An error occured while posting order",
                                Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {

                }

            } else {
                progressbar.hideProgress();

            }
        }
    }

    public void ordesResat(Activity activeRestat, Class nwIntent,
                           String determtabmng, String determstate, String determstata) {
        activeRestat.finish();

        try {
            Thread.sleep(50);

        } catch (InterruptedException e) {
        }

        Intent intent = new Intent(ordresContext.getApplicationContext(), nwIntent);

        Bundle bundle = new Bundle();
        bundle.putString("determtabmng", determtabmng);
        bundle.putString("determstate", determstate);
        bundle.putString("determstata", determstata);
        intent.putExtras(bundle);
        ordresContext.startActivity(intent);
    }

    /* Receive order */
    public boolean receiveRecd(String dbm, String micer, String uniqident, String uniqidentval) {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = true;
        String state = "Received";

        ContentValues contentValues = new ContentValues();
        contentValues.put(micer, state);
        writableDatabase.update(dbm, contentValues, uniqident + " = ?", new String[]{uniqidentval});

        /* Insert updated record inuque id */
        resources.insertUpdatcodes(resources.retStrdate().get(7), "ordesdetal", uniqident);


        return trace;

    }

    /* Get the actual buying price */
    public String retuxStkstate(String prdct) {
        String retval = "nada";


        Double avSalpr = 0.0;
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM pricelist " +
                "WHERE  pricescprdct = ? ", new String[]{prdct});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    retval = (res.getString(res.getColumnIndexOrThrow("pricescavail")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return retval;
    }

}
