package com.avintangu.bizsmat.admin;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.launch.MainActivity;
import com.avintangu.bizsmat.launch.Progressbar;
import com.avintangu.bizsmat.resources.Resocs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Adminresocs {
    private static Context admresContext;
    Dbcluzz dbcluzz;
    Resocs resources;
    private static Progressbar progressbar;

    DecimalFormat formatter = new DecimalFormat("#,###");

    public Adminresocs(Context context) {
        admresContext = context;
        dbcluzz = new Dbcluzz(admresContext);
        resources = new Resocs(admresContext);

    }

    /*  Get all data to back-up */
    public JSONObject gtBackupdata(String determ) {
        /*  Databases */
        ArrayList<String> dbesses = new ArrayList<>(Arrays.asList("prdctdetaz", "prdctdcateri", "salesrecd",
                "inventbl", "custostbl", "custodates", "custodatpay", "ordesdetal", "ordesprdcts"));

        /*  Database parameters */
        ArrayList<String> products = new ArrayList<>(Arrays.asList("prdctdetazdate", "prdctdetazcateg", "prdctdetazname",
                "prdctdetazcode", "prdctdetazprcc", "prdctdetazrecod", "prdctdetazstate", "prdctdetazshop"));
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("prdctdcatego", "prdctdcateriuniq", "prdctdcaterecod",
                "prdctdcateristate", "prdctdcaterishop"));
        ArrayList<String> sales = new ArrayList<>(Arrays.asList("salesrecddate", "salesrecdprdcod", "salesrecdprcc",
                "salesrecdqnty", "salesrecdbprc", "salesrecdrecod", "salesrecdstate", "salesrecdshop"));
        ArrayList<String> inventory = new ArrayList<>(Arrays.asList("inventbldate", "inventblprdcod", "inventblqnty",
                "inventblbprc", "inventblrecod", "inventblstate", "inventblshop"));
        ArrayList<String> customers = new ArrayList<>(Arrays.asList("custostbldate", "custostblname", "custostblphone",
                "custostblrecod", "custostblstate", "custostblshop"));
        ArrayList<String> debts = new ArrayList<>(Arrays.asList("custodatdate", "custodatecuscod", "custodatesprdct",
                "custodatesqnty", "custodatesval", "custodatesrecod", "custodatestate", "custodateshop"));
        ArrayList<String> payments = new ArrayList<>(Arrays.asList("custodatpaydate", "custodatpayccod", "custodatpayamt",
                "custodatpayrecod", "custodatpaystate", "custodatpayshop"));
        ArrayList<String> ordetails = new ArrayList<>(Arrays.asList("ordetaldta", "ordetaltme", "ordetaloutlt",
                "ordetalcustos", "ordetalcustnum", "ordetaldistos", "ordetaluniq", "ordetalnotes", "ordetalpymnt",
                "ordetalcreate", "ordetalapprv", "ordetalstate", "ordetalrecipt"));
        ArrayList<String> ordprdcts = new ArrayList<>(Arrays.asList("ordprdctprd", "ordprdctprcc", "ordprdctqnty",
                "ordprdctuniq", "orddetaluniq"));

        ArrayList<ArrayList<String>> Srescomb = new ArrayList<>();
        Srescomb.add(products);
        Srescomb.add(categories);
        Srescomb.add(sales);
        Srescomb.add(inventory);
        Srescomb.add(customers);
        Srescomb.add(debts);
        Srescomb.add(payments);
        Srescomb.add(ordetails);
        Srescomb.add(ordprdcts);

        /*  Object names */
        ArrayList<String> objnme = new ArrayList<>(Arrays.asList("prdctarry", "cateriarry", "salearry",
                "inventarry", "custoarry", "datesarry", "paymentarry", "ordetailsarry", "ordprdctarry"));

        /*  Object unique codes */
        ArrayList<String> objunique = new ArrayList<>(Arrays.asList("prdctdetazcode", "prdctdcateriuniq", "salesrecdrecod",
                "inventblrecod", "custostblrecod", "custodatesrecod", "custodatpayrecod", "ordetaluniq", "ordprdctuniq"));

        JSONObject postData = new JSONObject();
        try {
            postData.put("determ", dbcluzz.strEncodbs(determ));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*  User values */
        ArrayList<String> usrvals = dbcluzz.getUsrdetal();
        JSONArray usrvalsjson = new JSONArray();

        for (int y = 0; y < usrvals.size(); y++) {
            usrvalsjson.put(dbcluzz.strEncodbs(usrvals.get(y)));
        }

        try {
            postData.put("vals", usrvalsjson);

        } catch (JSONException e) {
        }

        /*  Get all records */
        String increcods = resources.Implodesa(resources.getUprecods());
        for (int ru = 0; ru < dbesses.size(); ru++) {
            try {
                postData.put(objnme.get(ru), ghRecode(dbesses.get(ru), Srescomb.get(ru), objunique.get(ru), increcods));

            } catch (JSONException e) {

            }

        }

        return postData;
    }

    /*  Get all records */
    public JSONArray ghRecode(String dbess, ArrayList<String> Sres, String unique, String increcods) {

        JSONArray comblist = new JSONArray();

        String sqlite = "";
        Cursor res = null;

        sqlite = "SELECT * FROM " + dbess + " WHERE " + unique + "  IN(" + increcods + ")";
        res = dbcluzz.getReadableDatabase().rawQuery(sqlite, null);

        try {

            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {

                    JSONArray data = new JSONArray();
                    for (Integer desca = 0; desca < Sres.size(); desca++) {
                        data.put(dbcluzz.strEncodbs(res.getString(res.getColumnIndexOrThrow(Sres.get(desca)))));

                    }

                    comblist.put(data);

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

    /*  Back-up data */
    public void bckData(String determ) {
        String mssg = "Synchronizing data, please wait.....";

        progressbar = new Progressbar(admresContext, mssg, "nan");
        progressbar.showProgress();

        ArrayList<String> ureca = dbcluzz.UrlGen();

        if (dbcluzz.isConnectedToInternet()) {
            new bkDatasync(determ).execute(ureca.get(0));

        } else {
            Toast.makeText(admresContext, "No internet connection", Toast.LENGTH_LONG).show();
            progressbar.hideProgress();

        }
    }

    private class bkDatasync extends AsyncTask<String, Void, String> {
        String determ;

        public bkDatasync(String determ) {
            this.determ = determ;
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            JSONObject postData = null;

            if (determ.equals("bckdata") || determ.equals("upgradeapp")) {
                postData = gtBackupdata(determ);

            }

            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + postData);
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

                        if (determ.equals("bckdata")) {
                            JSONArray usrvals = repocse.getJSONArray("usrvals");
                            JSONObject usrshops = repocse.getJSONObject("usrshops");
                            JSONObject prdcdetal = repocse.getJSONObject("productz");
                            JSONObject prdcatego = repocse.getJSONObject("producategory");
                            JSONObject salesrecos = repocse.getJSONObject("salesrecod");
                            JSONObject inventrecos = repocse.getJSONObject("inventrecod");
                            JSONObject custorecos = repocse.getJSONObject("custosrecd");
                            JSONObject custodatrecos = repocse.getJSONObject("custodatesrecd");
                            JSONObject custdatpay = repocse.getJSONObject("custodatpayrecd");

                            JSONObject distributors = repocse.getJSONObject("distributors");
                            JSONObject pricelist = repocse.getJSONObject("pricelist");
                            JSONObject ordetails = repocse.getJSONObject("ordetails");
                            JSONObject ordrpdcts = repocse.getJSONObject("ordrpdcts");

                            ArrayList<String> dbm = new ArrayList<>(Arrays.asList("usrdetal", "usrshops", "prdctdetaz",
                                    "prdctdcateri", "salesrecd", "inventbl", "custostbl", "custodates", "custodatpay",
                                    "distributors", "pricelist", "ordesdetal", "ordesprdcts"));
                            dbcluzz.delall(dbm);

                            dbcluzz.insertUsr(usrvals);
                            dbcluzz.insertUsrshops(usrshops);
                            dbcluzz.insertPrdcdetal(prdcdetal);
                            dbcluzz.insertPrdcatego(prdcatego);
                            dbcluzz.insertSalesrecos(salesrecos);
                            dbcluzz.insertInventrecos(inventrecos);
                            dbcluzz.insertCustorecos(custorecos);
                            dbcluzz.insertCustodatrecos(custodatrecos);
                            dbcluzz.insertCustdatpay(custdatpay);

                            dbcluzz.insertDistributors(distributors);
                            dbcluzz.insertPricelist(pricelist);
                            dbcluzz.insertOrdetails(ordetails);
                            dbcluzz.insertOrdprdcts(ordrpdcts);

                            /* Update sync date */
                            updatSync();

                            Toast.makeText(admresContext, "Data sync success!", Toast.LENGTH_LONG).show();

                        } else if (determ.equals("upgradeapp")) {
                            UpdateAppsynclod();
                        }

                    } else if (detocs.equals("null")) {
                        Toast.makeText(admresContext, "Data sync failed",
                                Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {

                }

            } else {
                progressbar.hideProgress();

            }
        }
    }

    /* Update sync date*/
    public boolean updatSync() {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = true;

        ContentValues contentValues = new ContentValues();
        contentValues.put("usrupdatval", resources.retStrdate().get(7));
        writableDatabase.update("usrdetal", contentValues, null, null);


        return trace;

    }

    /* Upgrade app */
    public void UpdateAppsynclod() {
        progressbar = new Progressbar(admresContext, "Downloading app..", "nan");
        progressbar.showProgress();

        if (dbcluzz.isConnectedToInternet()) {
            new Adminresocs.UpdateAppsync().execute();

        } else {
            Toast.makeText(admresContext, "Activate your Internet", Toast.LENGTH_LONG).show();
            progressbar.hideProgress();

        }

    }

    /* Update app sync */
    public class UpdateAppsync extends AsyncTask<String, Integer, Double> {
        protected Double doInBackground(String... array) {
            try {
                /* Create destination uri */
                String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
                final String fileName = "update.apk";
                destination += fileName;
                final Uri uri = Uri.parse("file://" + destination);

                /* Delete file if it exists */
                final File file = new File(destination);
                if (file.exists())
                    file.delete();

                /* Download url */
                String url = dbcluzz.UrlGen().get(1);

                /* set downloadmanager */
                String notification_description = "App download";
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setDescription(notification_description);
                request.setTitle("Update app");

                /* Set destination */
                request.setDestinationUri(uri);

                /* get download service and enqueue file */
                final DownloadManager manager = (DownloadManager) admresContext.getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadId = manager.enqueue(request);

                /* Install app by broadcast */
                BroadcastReceiver onComplete = new BroadcastReceiver() {
                    public void onReceive(Context ctxt, Intent intent) {

                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri apkURI = FileProvider.getUriForFile(admresContext.getApplicationContext(),
                                    admresContext.getApplicationContext().getPackageName() + ".provider", file);
                            install.setDataAndType(apkURI, manager.getMimeTypeForDownloadedFile(downloadId));
                            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            admresContext.startActivity(install);

                        } else {
                            String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
                            destination += fileName;
                            Uri uri = Uri.parse("file://" + destination);

                            Intent install = new Intent(Intent.ACTION_VIEW);
                            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            install.setDataAndType(uri,
                                    manager.getMimeTypeForDownloadedFile(downloadId));
                            admresContext.startActivity(install);
                        }

                        admresContext.unregisterReceiver(this);
                        MainActivity.duks.finish();
                    }
                };

                /* Register receivers */
                admresContext.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


            } catch (Exception ex) {

            }
            return null;
        }

        protected void onPostExecute(Double n) {
            progressbar.hideProgress();
        }

        protected void onProgressUpdate(Integer... array) {
        }
    }

    /*Refresh data*/
    public void refreshData(ArrayList<String> vals) {
        progressbar = new Progressbar(admresContext, "Getting data.....", "nan");
        progressbar.showProgress();

        JSONObject postData = new JSONObject();
        ArrayList<String> ureca = dbcluzz.UrlGen();

        try {
            JSONArray modvals = new JSONArray();
            for (int recs = 0; recs < vals.size(); recs++) {
                modvals.put(dbcluzz.strEncodbs(vals.get(recs)));
            }

            postData.put("determ", dbcluzz.strEncodbs("refreshdata"));
            postData.put("vals", modvals);

            if (dbcluzz.isConnectedToInternet()) {
                new Adminresocs.refreshdatasync(vals.get(0)).execute(ureca.get(0), postData.toString());

            } else {
                Toast.makeText(admresContext, "No internet connection", Toast.LENGTH_LONG).show();
                progressbar.hideProgress();

            }
        } catch (JSONException e) {
        }
    }

    /* Refresh data sync */
    private class refreshdatasync extends AsyncTask<String, Void, String> {
        String determ;

        public refreshdatasync(String determ) {
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
                        JSONArray usrvals = repocse.getJSONArray("usrvals");
                        ArrayList<String> dbm = new ArrayList<>(Arrays.asList("usrdetal"));

                        dbcluzz.delall(dbm);
                        dbcluzz.insertUsr(usrvals);

                        Admin.adminactiv.finish();

                        Intent intent = new Intent(admresContext, Admin.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("determtabmng", "adminSrecod");
                        bundle.putString("determstate", "Active");
                        intent.putExtras(bundle);
                        admresContext.startActivity(intent);


                        Toast.makeText(admresContext, "The user data has been updated", Toast.LENGTH_LONG).show();


                    } else if (detocs.equals("null")) {
                        Toast.makeText(admresContext, "The user trying to refresh data does not exist or is deactivated",
                                Toast.LENGTH_LONG).show();

                    } else if (detocs.equals("s_fail")) {
                        Toast.makeText(admresContext, "An error occured while sending request", Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {

                }

            } else {
                progressbar.hideProgress();

            }
        }
    }

    /* Activate app */
    public void activateApp(ArrayList<String> vals, String determ) {
        progressbar = new Progressbar(admresContext, "Activating app.....", "nan");
        progressbar.showProgress();

        JSONObject postData = new JSONObject();
        ArrayList<String> ureca = dbcluzz.UrlGen();

        try {
            JSONArray modvals = new JSONArray();
            for (int recs = 0; recs < vals.size(); recs++) {
                modvals.put(dbcluzz.strEncodbs(vals.get(recs)));
            }

            postData.put("determ", dbcluzz.strEncodbs("activateapp"));
            postData.put("vals", modvals);

            if (dbcluzz.isConnectedToInternet()) {
                new Adminresocs.activateappsync(determ).execute(ureca.get(0), postData.toString());

            } else {
                Toast.makeText(admresContext, "No internet connection", Toast.LENGTH_LONG).show();
                progressbar.hideProgress();

            }
        } catch (JSONException e) {
        }
    }

    /* Activateapp data sync */
    private class activateappsync extends AsyncTask<String, Void, String> {
        String determ;

        public activateappsync(String determ) {
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
                        String responc = repocse.getString("responc");

                        if (responc.equals("actcool")) {

                            if (determ.equals("adminActivate")) {
                                resources.updatApplic();
                                Admin.adminactiv.finish();

                                Intent intent = new Intent(admresContext, Admin.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("determtabmng", "adminSrecod");
                                bundle.putString("determstate", "Active");
                                intent.putExtras(bundle);
                                admresContext.startActivity(intent);

                                Toast.makeText(admresContext, "The app has been successfully activated", Toast.LENGTH_LONG).show();

                            } else if (determ.equals("mainActivate")) {
                                MainActivity.duks.finish();

                                Intent intent = new Intent(admresContext, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("mainactivity", "mainSrecod");
                                intent.putExtras(bundle);
                                admresContext.startActivity(intent);

                                Toast.makeText(admresContext, "The app has been successfully activated", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(admresContext, "The app could not be activated, contact the system admin for " +
                                    "more information", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        Toast.makeText(admresContext, "An error occured while activating app", Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {

                }

            } else {
                progressbar.hideProgress();

            }
        }
    }

}
