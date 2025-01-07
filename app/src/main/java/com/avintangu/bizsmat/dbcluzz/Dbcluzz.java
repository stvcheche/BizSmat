package com.avintangu.bizsmat.dbcluzz;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.avintangu.bizsmat.launch.Progressbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Dbcluzz extends SQLiteOpenHelper {
    public static String BIZ_DBS = "bizsmat.db";
    private static int DATABASE_VERSION = 12;
    private static Context mContext;
    private static Progressbar progressbar;

    public Dbcluzz(Context context) {
        super(context, BIZ_DBS, null, DATABASE_VERSION);
        mContext = context;

    }

    /*Create tables*/
    public void onCreate(SQLiteDatabase dbm) {
        dbm.execSQL("CREATE TABLE usrprefs" +
                "(usrprefsid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usrprefsname varchar(500)," +
                "usrprefsval varchar(500)," +
                "usrprefsuniq varchar(500))");

        dbm.execSQL("CREATE TABLE updatable" +
                "(updat_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "updat_date TEXT," +
                "updat_rpt VARCHAR(500)," +
                "updat_rptcode VARCHAR(500))");

        dbm.execSQL("CREATE TABLE usrdetal " +
                "(usrdetalid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usrdetalname varchar(500)," +
                "usrdetalphone varchar(500)," +
                "usrdetalmail varchar(500)," +
                "usrdetaluniq varchar(500)," +
                "usrdetalperm varchar(500)," +
                "usrdetallic varchar(500)," +
                "usrupdatval TEXT DEFAULT '1900-01-01')");

        dbm.execSQL("CREATE TABLE usrshops " +
                "(usrshopid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usrshopusr varchar(500)," +
                "usrshopname varchar(500)," +
                "usrshopuniq varchar(500)," +
                "usrshopstate varchar(500))");

        dbm.execSQL("CREATE TABLE prdctdetaz " +
                "(prdctdetazid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "prdctdetazdate varchar(500)," +
                "prdctdetazcateg varchar(500)," +
                "prdctdetazname varchar(500)," +
                "prdctdetazcode varchar(500)," +
                "prdctdetazprcc INTEGER," +
                "prdctdetazrecod varchar(500)," +
                "prdctdetazshop varchar(500)," +
                "prdctdetazstate varchar(500))");

        dbm.execSQL("CREATE TABLE prdctdcateri " +
                "(prdctdcaterid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "prdctdcatego varchar(500)," +
                "prdctdcateriuniq varchar(500)," +
                "prdctdcaterecod varchar(500)," +
                "prdctdcaterishop varchar(500)," +
                "prdctdcateristate varchar(500))");

        dbm.execSQL("CREATE TABLE salesrecd " +
                "(salesrecdid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "salesrecddate text," +
                "salesrecdprdcod varchar(500)," +
                "salesrecdprcc INTEGER," +
                "salesrecdqnty INTEGER," +
                "salesrecdbprc INTEGER," +
                "salesrecdrecod varchar(500)," +
                "salesrecdshop varchar(500)," +
                "salesrecdstate varchar(500))");

        dbm.execSQL("CREATE TABLE inventbl " +
                "(inventblid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inventbldate text," +
                "inventblprdcod varchar(500)," +
                "inventblqnty INTEGER," +
                "inventblbprc INTEGER," +
                "inventblrecod varchar(500)," +
                "inventblshop varchar(500)," +
                "inventblstate varchar(500))");

        dbm.execSQL("CREATE TABLE custostbl " +
                "(custostblid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "custostbldate text," +
                "custostblname varchar(500)," +
                "custostblphone varchar(500)," +
                "custostblrecod varchar(500)," +
                "custostblshop varchar(500)," +
                "custostblstate varchar(500))");

        dbm.execSQL("CREATE TABLE custodates " +
                "(custodatesid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "custodatdate text," +
                "custodatecuscod varchar(500)," +
                "custodatesprdct varchar(500)," +
                "custodatesqnty INTEGER," +
                "custodatesval INTEGER," +
                "custodatesrecod varchar(500)," +
                "custodateshop varchar(500)," +
                "custodatestate varchar(500))");

        dbm.execSQL("CREATE TABLE custodatpay " +
                "(custodatpayid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "custodatpaydate text," +
                "custodatpayccod varchar(500)," +
                "custodatpayamt INTEGER," +
                "custodatpayrecod varchar(500)," +
                "custodatpayshop varchar(500)," +
                "custodatpaystate varchar(500))");

        dbm.execSQL("CREATE TABLE distributors " +
                "(distroscid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "distroscname varchar(500)," +
                "distroscuniq varchar(500)," +
                "distrosctype varchar(500)," +
                "distrosccountry varchar(500)," +
                "distroscreggy varchar(500)," +
                "distrosctown varchar(500)," +
                "distroscpsn varchar(500)," +
                "distroscnum varchar(500)," +
                "distroscmail varchar(500))");

        dbm.execSQL("CREATE TABLE pricelist " +
                "(pricescidec INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pricescprdct varchar(500)," +
                "pricescprdcod varchar(500)," +
                "pricescprcc INTEGER," +
                "pricescdist varchar(500)," +
                "pricescstate varchar(500)," +
                "pricescavail varchar(500))");

        dbm.execSQL("CREATE TABLE ordesdetal " +
                "(ordetalidec INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ordetaldta text," +
                "ordetaltme text," +
                "ordetaloutlt varchar(500)," +
                "ordetalcustos varchar(500)," +
                "ordetalcustnum varchar(500)," +
                "ordetaldistos varchar(500)," +
                "ordetaluniq varchar(500)," +
                "ordetalnotes varchar(500)," +
                "ordetalpymnt varchar(500)," +
                "ordetalcreate varchar(500)," +
                "ordetalapprv varchar(500)," +
                "ordetalstate varchar(500)," +
                "ordetalrecipt varchar(500))");

        dbm.execSQL("CREATE TABLE ordesprdcts " +
                "(ordprdctidec INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ordprdctprd varchar(500)," +
                "ordprdctprcc varchar(500)," +
                "ordprdctqnty INTEGER," +
                "ordprdctuniq varchar(500)," +
                "orddetaluniq varchar(500))");
    }

    /*Upgrade database*/
    public void onUpgrade(SQLiteDatabase dbm, int n, int n2) {
        String zeterm = "upgradeapp";

        ArrayList<String> vals = new ArrayList<>();

        String usrname = "";
        String usrphone = "";
        String usrshop = "";
        String usruniq = "";
        Cursor res = dbm.rawQuery("SELECT * FROM usrdetal", null);

        try {
            res.moveToFirst();
            if (res != null) {
                if (res.moveToFirst()) {
                    do {
                        usrname = res.getString(res.getColumnIndexOrThrow("usrdetalname"));
                        usrphone = res.getString(res.getColumnIndexOrThrow("usrdetalphone"));
                        usrshop = res.getString(res.getColumnIndexOrThrow("usrdetallic"));
                        usruniq = res.getString(res.getColumnIndexOrThrow("usrdetaluniq"));

                    } while (res.moveToNext());
                }
            }
            vals.add(usrname);
            vals.add(usrphone);
            vals.add(usrshop);
            vals.add(usruniq);

        } catch (Exception e) {
            // exception handling
        } finally {
            if (res != null) {
                res.close();
            }
        }

        dbm.execSQL("DROP TABLE IF EXISTS usrprefs");
        dbm.execSQL("DROP TABLE IF EXISTS updatable");
        dbm.execSQL("DROP TABLE IF EXISTS usrdetal");
        dbm.execSQL("DROP TABLE IF EXISTS usrshops");
        dbm.execSQL("DROP TABLE IF EXISTS prdctdetaz");
        dbm.execSQL("DROP TABLE IF EXISTS prdctdcateri");
        dbm.execSQL("DROP TABLE IF EXISTS salesrecd");
        dbm.execSQL("DROP TABLE IF EXISTS inventbl");
        dbm.execSQL("DROP TABLE IF EXISTS custostbl");
        dbm.execSQL("DROP TABLE IF EXISTS custodates");
        dbm.execSQL("DROP TABLE IF EXISTS custodatpay");
        dbm.execSQL("DROP TABLE IF EXISTS distributors");
        dbm.execSQL("DROP TABLE IF EXISTS pricelist");
        dbm.execSQL("DROP TABLE IF EXISTS ordesdetal");
        dbm.execSQL("DROP TABLE IF EXISTS ordesprdcts");

        assignUser(vals, zeterm);
        onCreate(dbm);
    }

    /* Get all the urls */
    public ArrayList<String> UrlGen() {
        ArrayList<String> list = new ArrayList<>();

        String determ = "Bizsmatdemo";

        if (determ.equals("offline")) {
            list.add("http://192.168.43.164/Swivacc/usrauth.php");
            list.add("https://192.168.43.164/AvintHub/Bizsmsat/BizSmat.apk");
            list.add("https://appdebug.avintangu.com/Swipost/Admin/slkgtdata.php");

        } else if (determ.equals("Bizsmat")) {
            list.add("https://bizsmat.avintangu.com/Swivacc/usrauth.php");
            list.add("https://downloads.avintangu.com/AvintHub/Bizsmsat/BizSmat.apk");
            list.add("https://appdebug.avintangu.com/Swipost/Admin/slkgtdata.php");

        } else if (determ.equals("Bizsmatdemo")) {
            list.add("https://bizsmatdemo.avintangu.com/Swivacc/usrauth.php");
            list.add("https://downloads.avintangu.com/AvintHub/Bizsmsat/BizSmat.apk");
            list.add("https://appdebug.avintangu.com/Swipost/Admin/slkgtdata.php");

        }
        return list;
    }


    /*Encode text to base 64*/
    public String strEncodbs(String inptstr) {
        byte[] data = inptstr.getBytes();
        String retStr = Base64.encodeToString(data, Base64.NO_WRAP);

        return retStr;
    }

    /*Decode text to base 64*/
    public String strDecodbs(String inptstr) {
        byte[] data = Base64.decode(inptstr, 0);
        String retStr = new String(data);

        return Jsoup.parse(retStr).text();
    }

    /*Check if a record exists*/
    public boolean checkRecordExist(SQLiteDatabase sqLiteDatabase, String s, String[] array, String[] array2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i]).append("=\"").append(array2[i]).append("\" ");
            if (i < array.length - 1) {
                sb.append("AND ");
            }
        }
        Cursor query = sqLiteDatabase.query(s, null, sb.toString(), null, null, null, null);
        boolean b = query.getCount() > 0;
        query.close();
        return b;
    }

    boolean checkRecordExista(String dbm, String uniqcode, String uniqcodeval) {
        Integer retval = 0;
        boolean resp = false;

        Cursor res = this.getReadableDatabase().rawQuery("SELECT COUNT(*) AS countes FROM " + dbm +
                " WHERE " + uniqcode + "= ?", new String[]{uniqcodeval});


        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {

                do {
                    retval = res.getInt(res.getColumnIndexOrThrow("countes"));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling
        } finally {
            if (res != null) {
                res.close();
            }
        }

        if (retval > 0) {
            resp = true;
        }

        return resp;
    }

    /*Check internet connection*/
    public boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Dbcluzz.mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo != null) {
                for (int i = 0; i < allNetworkInfo.length; ++i) {
                    if (allNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*Assign user*/
    public void assignUser(ArrayList<String> vals, String zeterm) {
        String mssg = "";

        if (zeterm.equals("assignuser")) {
            mssg = "Assigning user, please wait.........";

        } else if (zeterm.equals("upgradeapp")) {
            mssg = "Updating data, please wait.........";

        }

        progressbar = new Progressbar(mContext, mssg, "nan");
        progressbar.showProgress();

        JSONObject postData = new JSONObject();
        ArrayList<String> ureca = UrlGen();

        try {
            JSONArray modvals = new JSONArray();
            for (int recs = 0; recs < vals.size(); recs++) {
                modvals.put(strEncodbs(vals.get(recs)));
            }

            postData.put("determ", strEncodbs(zeterm));
            postData.put("vals", modvals);

            if (isConnectedToInternet()) {
                new assignSync(zeterm).execute(ureca.get(0), postData.toString());

            } else {
                Toast.makeText(mContext, "No internet connection", Toast.LENGTH_LONG).show();
                progressbar.hideProgress();

            }
        } catch (JSONException e) {
        }
    }

    private class assignSync extends AsyncTask<String, Void, String> {
        String determ;

        public assignSync(String determ) {
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

                        insertUsr(usrvals);
                        insertUsrshops(usrshops);
                        insertPrdcdetal(prdcdetal);
                        insertPrdcatego(prdcatego);
                        insertSalesrecos(salesrecos);
                        insertInventrecos(inventrecos);
                        insertCustorecos(custorecos);
                        insertCustodatrecos(custodatrecos);
                        insertCustdatpay(custdatpay);

                        insertDistributors(distributors);
                        insertPricelist(pricelist);
                        insertOrdetails(ordetails);
                        insertOrdprdcts(ordrpdcts);

                        com.avintangu.bizsmat.launch.MainActivity.duks.finish();
                        Dbcluzz.mContext.startActivity(new Intent(Dbcluzz.mContext, com.avintangu.bizsmat.launch.MainActivity.class));

                        if (determ.equals("assignuser")) {
                            Toast.makeText(mContext, "User assigned successfully", Toast.LENGTH_LONG).show();

                        } else if (determ.equals("upgradeapp")) {
                            Toast.makeText(mContext, "App upgraded successfully", Toast.LENGTH_LONG).show();

                        }

                    } else if (detocs.equals("errdefpass")) {
                        Toast.makeText(mContext, "The default password you entered is wrong", Toast.LENGTH_LONG).show();

                    } else if (detocs.equals("null")) {
                        Toast.makeText(mContext, "The user does not exist or is deactivated",
                                Toast.LENGTH_LONG).show();

                    } else if (detocs.equals("s_fail")) {
                        Toast.makeText(mContext, "An error occured while sending request", Toast.LENGTH_LONG).show();

                    } else if (detocs.equals("errkey")) {
                        Toast.makeText(mContext, "The key entered does not exist", Toast.LENGTH_LONG).show();

                    } else if (detocs.equals("errphone")) {
                        Toast.makeText(mContext, "The phone number entered does not exist", Toast.LENGTH_LONG).show();

                    } else if (detocs.equals("errusr")) {
                        Toast.makeText(mContext, "The user phone number exists please assign as a returning user", Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {

                }

            } else {
                progressbar.hideProgress();

            }
        }
    }

    /* Insert user preferences */
    public boolean insertUsrprefs(String langcode) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String Language = "Language";
        String Languniq = "lang45268367AGE";

        contentValues.put("usrprefsname", Language);
        contentValues.put("usrprefsval", langcode);
        contentValues.put("usrprefsuniq", Languniq);

        if (!checkRecordExista("usrprefs", "usrprefsuniq",
                Languniq)) {
            writableDatabase.insert("usrprefs", null, contentValues);
        }

        return true;
    }

    /* Get user preferences */
    public String gtUsrprefs(String prefuniq) {
        String retval = "";

        Cursor res = this.getReadableDatabase().rawQuery("SELECT * FROM usrprefs " +
                " WHERE usrprefsuniq = ?", new String[]{prefuniq});


        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    retval = res.getString(res.getColumnIndexOrThrow("usrprefsval"));

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

    /* Get user details */
    public ArrayList<String> getUsrdetal() {
        ArrayList<String> comblist = new ArrayList<>();
        Cursor res = this.getReadableDatabase().rawQuery("SELECT * FROM usrdetal  ", null);

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrdetalname")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrdetalphone")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrdetalmail")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrdetaluniq")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrdetalperm")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrdetallic")));

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


    /* Insert user details */
    public boolean insertUsr(JSONArray usrdals) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put("usrdetalname", strDecodbs(usrdals.get(0).toString()));
            contentValues.put("usrdetalphone", strDecodbs(usrdals.get(1).toString()));
            contentValues.put("usrdetalmail", strDecodbs(usrdals.get(2).toString()));
            contentValues.put("usrdetaluniq", strDecodbs(usrdals.get(3).toString()));
            contentValues.put("usrdetalperm", strDecodbs(usrdals.get(4).toString()));
            contentValues.put("usrdetallic", strDecodbs(usrdals.get(5).toString()));
            writableDatabase.insert("usrdetal", null, contentValues);
        } catch (JSONException ex) {

        }

        return true;
    }

    /* Insert shops */
    public boolean insertUsrshops(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray shopusr = jsonObject.getJSONArray("shopusr");
            JSONArray shopname = jsonObject.getJSONArray("shopname");
            JSONArray shopuniq = jsonObject.getJSONArray("shopuniq");

            if (shopusr.length() > 0) {
                for (int i = 0; i < shopusr.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("usrshopusr", strDecodbs(shopusr.get(i).toString()));
                    contentValues.put("usrshopname", strDecodbs(shopname.get(i).toString()));
                    contentValues.put("usrshopuniq", strDecodbs(shopuniq.get(i).toString()));

                    if (i == 0) {
                        contentValues.put("usrshopstate", "Active");

                    } else {
                        contentValues.put("usrshopstate", "Inactive");
                    }

                    if (!checkRecordExista("usrshops", "usrshopuniq",
                            strDecodbs(shopuniq.get(i).toString()))) {
                        writableDatabase.insert("usrshops", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert products */
    public boolean insertPrdcdetal(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray prddates = jsonObject.getJSONArray("prddates");
            JSONArray prdcatego = jsonObject.getJSONArray("prdcatego");
            JSONArray prdname = jsonObject.getJSONArray("prdname");
            JSONArray prdcode = jsonObject.getJSONArray("prdcode");
            JSONArray prdprcc = jsonObject.getJSONArray("prdprcc");
            JSONArray prdrecod = jsonObject.getJSONArray("prdrecod");
            JSONArray prdshop = jsonObject.getJSONArray("prdshop");
            JSONArray prdstate = jsonObject.getJSONArray("prdstate");


            if (prdcatego.length() > 0) {
                for (int i = 0; i < prdcatego.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("prdctdetazdate", strDecodbs(prddates.get(i).toString()));
                    contentValues.put("prdctdetazcateg", strDecodbs(prdcatego.get(i).toString()));
                    contentValues.put("prdctdetazname", strDecodbs(prdname.get(i).toString()));
                    contentValues.put("prdctdetazcode", strDecodbs(prdcode.get(i).toString()));
                    contentValues.put("prdctdetazprcc", strDecodbs(prdprcc.get(i).toString()));
                    contentValues.put("prdctdetazrecod", strDecodbs(prdrecod.get(i).toString()));
                    contentValues.put("prdctdetazshop", strDecodbs(prdshop.get(i).toString()));
                    contentValues.put("prdctdetazstate", strDecodbs(prdstate.get(i).toString()));


                    if (!checkRecordExista("prdctdetaz", "prdctdetazcode",
                            strDecodbs(prdcode.get(i).toString()))) {
                        writableDatabase.insert("prdctdetaz", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert products categories */
    public boolean insertPrdcatego(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray prdcategonme = jsonObject.getJSONArray("prdcategonme");
            JSONArray prdcategouniq = jsonObject.getJSONArray("prdcategouniq");
            JSONArray prdcategorecod = jsonObject.getJSONArray("prdcategorecod");
            JSONArray prdcategoshop = jsonObject.getJSONArray("prdctcatshop");
            JSONArray prdcategostate = jsonObject.getJSONArray("prdcategostate");

            if (prdcategonme.length() > 0) {
                for (int i = 0; i < prdcategonme.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("prdctdcatego", strDecodbs(prdcategonme.get(i).toString()));
                    contentValues.put("prdctdcateriuniq", strDecodbs(prdcategouniq.get(i).toString()));
                    contentValues.put("prdctdcaterecod", strDecodbs(prdcategorecod.get(i).toString()));
                    contentValues.put("prdctdcaterishop", strDecodbs(prdcategoshop.get(i).toString()));
                    contentValues.put("prdctdcateristate", strDecodbs(prdcategostate.get(i).toString()));


                    if (!checkRecordExista("prdctdcateri", "prdctdcaterecod",
                            strDecodbs(prdcategorecod.get(i).toString()))) {
                        writableDatabase.insert("prdctdcateri", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert sales records */
    public boolean insertSalesrecos(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray saledate = jsonObject.getJSONArray("saledate");
            JSONArray saleprdct = jsonObject.getJSONArray("saleprdct");
            JSONArray saleprcc = jsonObject.getJSONArray("saleprcc");
            JSONArray saleqnty = jsonObject.getJSONArray("saleqnty");
            JSONArray salebprcc = jsonObject.getJSONArray("salebprcc");
            JSONArray salerecod = jsonObject.getJSONArray("salerecod");
            JSONArray saleshop = jsonObject.getJSONArray("saleshop");
            JSONArray salestate = jsonObject.getJSONArray("salestate");

            if (saledate.length() > 0) {
                for (int i = 0; i < saledate.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("salesrecddate", strDecodbs(saledate.get(i).toString()));
                    contentValues.put("salesrecdprdcod", strDecodbs(saleprdct.get(i).toString()));
                    contentValues.put("salesrecdprcc", strDecodbs(saleprcc.get(i).toString()));
                    contentValues.put("salesrecdqnty", strDecodbs(saleqnty.get(i).toString()));
                    contentValues.put("salesrecdbprc", strDecodbs(salebprcc.get(i).toString()));
                    contentValues.put("salesrecdrecod", strDecodbs(salerecod.get(i).toString()));
                    contentValues.put("salesrecdshop", strDecodbs(saleshop.get(i).toString()));
                    contentValues.put("salesrecdstate", strDecodbs(salestate.get(i).toString()));


                    if (!checkRecordExista("salesrecd", "salesrecdrecod",
                            strDecodbs(salerecod.get(i).toString()))) {
                        writableDatabase.insert("salesrecd", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert inventory */
    public boolean insertInventrecos(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray inventdate = jsonObject.getJSONArray("inventdate");
            JSONArray inventprdct = jsonObject.getJSONArray("inventprdct");
            JSONArray inventqnty = jsonObject.getJSONArray("inventqnty");
            JSONArray inventprcc = jsonObject.getJSONArray("inventprcc");
            JSONArray inventrecod = jsonObject.getJSONArray("inventrecod");
            JSONArray inventshop = jsonObject.getJSONArray("inventshop");
            JSONArray inventstate = jsonObject.getJSONArray("inventstate");

            if (inventdate.length() > 0) {
                for (int i = 0; i < inventdate.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("inventbldate", strDecodbs(inventdate.get(i).toString()));
                    contentValues.put("inventblprdcod", strDecodbs(inventprdct.get(i).toString()));
                    contentValues.put("inventblqnty", strDecodbs(inventqnty.get(i).toString()));
                    contentValues.put("inventblbprc", strDecodbs(inventprcc.get(i).toString()));
                    contentValues.put("inventblrecod", strDecodbs(inventrecod.get(i).toString()));
                    contentValues.put("inventblshop", strDecodbs(inventshop.get(i).toString()));
                    contentValues.put("inventblstate", strDecodbs(inventstate.get(i).toString()));


                    if (!checkRecordExista("inventbl", "inventblrecod",
                            strDecodbs(inventrecod.get(i).toString()))) {
                        writableDatabase.insert("inventbl", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert customers */
    public boolean insertCustorecos(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray custodate = jsonObject.getJSONArray("custodate");
            JSONArray custoname = jsonObject.getJSONArray("custoname");
            JSONArray custophone = jsonObject.getJSONArray("custophone");
            JSONArray custorecod = jsonObject.getJSONArray("custorecod");
            JSONArray custoshop = jsonObject.getJSONArray("custoshop");
            JSONArray custostate = jsonObject.getJSONArray("custostate");

            if (custodate.length() > 0) {
                for (int i = 0; i < custodate.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("custostbldate", strDecodbs(custodate.get(i).toString()));
                    contentValues.put("custostblname", strDecodbs(custoname.get(i).toString()));
                    contentValues.put("custostblphone", strDecodbs(custophone.get(i).toString()));
                    contentValues.put("custostblrecod", strDecodbs(custorecod.get(i).toString()));
                    contentValues.put("custostblshop", strDecodbs(custoshop.get(i).toString()));
                    contentValues.put("custostblstate", strDecodbs(custostate.get(i).toString()));


                    if (!checkRecordExista("custostbl", "custostblrecod",
                            strDecodbs(custorecod.get(i).toString()))) {
                        writableDatabase.insert("custostbl", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert customer dates */
    public boolean insertCustodatrecos(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray datesdate = jsonObject.getJSONArray("datesdate");
            JSONArray datescust = jsonObject.getJSONArray("datescust");
            JSONArray datesprdct = jsonObject.getJSONArray("datesprdct");
            JSONArray datesprdctqnty = jsonObject.getJSONArray("datesprdctqnty");
            JSONArray datesprdctval = jsonObject.getJSONArray("datesprdctval");
            JSONArray datesprdctrecod = jsonObject.getJSONArray("datesprdctrecod");
            JSONArray datesprdshop = jsonObject.getJSONArray("dateshop");
            JSONArray datesprdctstate = jsonObject.getJSONArray("datesprdctsate");

            if (datesdate.length() > 0) {
                for (int i = 0; i < datesdate.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("custodatdate", strDecodbs(datesdate.get(i).toString()));
                    contentValues.put("custodatecuscod", strDecodbs(datescust.get(i).toString()));
                    contentValues.put("custodatesprdct", strDecodbs(datesprdct.get(i).toString()));
                    contentValues.put("custodatesqnty", strDecodbs(datesprdctqnty.get(i).toString()));
                    contentValues.put("custodatesval", strDecodbs(datesprdctval.get(i).toString()));
                    contentValues.put("custodatesrecod", strDecodbs(datesprdctrecod.get(i).toString()));
                    contentValues.put("custodateshop", strDecodbs(datesprdshop.get(i).toString()));
                    contentValues.put("custodatestate", strDecodbs(datesprdctstate.get(i).toString()));


                    if (!checkRecordExista("custodates", "custodatesrecod",
                            strDecodbs(datesprdctrecod.get(i).toString()))) {
                        writableDatabase.insert("custodates", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert customer dates payments */
    public boolean insertCustdatpay(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray datpaydate = jsonObject.getJSONArray("datpaydate");
            JSONArray datpaycuscod = jsonObject.getJSONArray("datpaycuscod");
            JSONArray datepayval = jsonObject.getJSONArray("datepayval");
            JSONArray datpayrecod = jsonObject.getJSONArray("datpayrecod");
            JSONArray datpayshop = jsonObject.getJSONArray("dtapayshop");
            JSONArray datpaystate = jsonObject.getJSONArray("datpaystate");


            if (datpaycuscod.length() > 0) {
                for (int i = 0; i < datpaycuscod.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("custodatpaydate", strDecodbs(datpaydate.get(i).toString()));
                    contentValues.put("custodatpayccod", strDecodbs(datpaycuscod.get(i).toString()));
                    contentValues.put("custodatpayamt", strDecodbs(datepayval.get(i).toString()));
                    contentValues.put("custodatpayrecod", strDecodbs(datpayrecod.get(i).toString()));
                    contentValues.put("custodatpayshop", strDecodbs(datpayshop.get(i).toString()));
                    contentValues.put("custodatpaystate", strDecodbs(datpaystate.get(i).toString()));


                    if (!checkRecordExista("custodatpay", "custodatpayrecod",
                            strDecodbs(datpayrecod.get(i).toString()))) {
                        writableDatabase.insert("custodatpay", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert distributors */
    public boolean insertDistributors(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray distroname = jsonObject.getJSONArray("distroname");
            JSONArray distrouniq = jsonObject.getJSONArray("distrouniq");
            JSONArray distrotype = jsonObject.getJSONArray("distrotype");
            JSONArray distrocountry = jsonObject.getJSONArray("distrocountry");
            JSONArray distroreggy = jsonObject.getJSONArray("distroreggy");
            JSONArray distrotown = jsonObject.getJSONArray("distrotown");
            JSONArray distrocpsn = jsonObject.getJSONArray("distrocpsn");
            JSONArray distrocnum = jsonObject.getJSONArray("distrocnum");
            JSONArray distrocmail = jsonObject.getJSONArray("distrocmail");


            if (distroname.length() > 0) {
                for (int i = 0; i < distroname.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("distroscname", strDecodbs(distroname.get(i).toString()));
                    contentValues.put("distroscuniq", strDecodbs(distrouniq.get(i).toString()));
                    contentValues.put("distrosctype", strDecodbs(distrotype.get(i).toString()));
                    contentValues.put("distrosccountry", strDecodbs(distrocountry.get(i).toString()));
                    contentValues.put("distroscreggy", strDecodbs(distroreggy.get(i).toString()));
                    contentValues.put("distrosctown", strDecodbs(distrotown.get(i).toString()));
                    contentValues.put("distroscpsn", strDecodbs(distrocpsn.get(i).toString()));
                    contentValues.put("distroscnum", strDecodbs(distrocnum.get(i).toString()));
                    contentValues.put("distroscmail", strDecodbs(distrocmail.get(i).toString()));


                    if (!checkRecordExista("distributors", "distroscuniq",
                            strDecodbs(distrouniq.get(i).toString()))) {
                        writableDatabase.insert("distributors", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert price-list */
    public boolean insertPricelist(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray product = jsonObject.getJSONArray("product");
            JSONArray prdcode = jsonObject.getJSONArray("prdcode");
            JSONArray prdctprcc = jsonObject.getJSONArray("prdctprcc");
            JSONArray prdctdist = jsonObject.getJSONArray("prdctdist");
            JSONArray prdctstate = jsonObject.getJSONArray("prdctstate");
            JSONArray prdctavail = jsonObject.getJSONArray("stkavail");


            if (product.length() > 0) {
                for (int i = 0; i < product.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("pricescprdct", strDecodbs(product.get(i).toString()));
                    contentValues.put("pricescprdcod", strDecodbs(prdcode.get(i).toString()));
                    contentValues.put("pricescprcc", strDecodbs(prdctprcc.get(i).toString()));
                    contentValues.put("pricescdist", strDecodbs(prdctdist.get(i).toString()));
                    contentValues.put("pricescstate", strDecodbs(prdctstate.get(i).toString()));
                    contentValues.put("pricescavail", strDecodbs(prdctavail.get(i).toString()));


                    if (!checkRecordExista("pricelist", "pricescprdcod",
                            strDecodbs(prdcode.get(i).toString()))) {
                        writableDatabase.insert("pricelist", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert orders details */
    public boolean insertOrdetails(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray ordta = jsonObject.getJSONArray("ordta");
            JSONArray ordtme = jsonObject.getJSONArray("ordtme");
            JSONArray orduotlt = jsonObject.getJSONArray("orduotlt");
            JSONArray ordcustonme = jsonObject.getJSONArray("ordcustonme");
            JSONArray ordcustonum = jsonObject.getJSONArray("ordcustonum");
            JSONArray ordistro = jsonObject.getJSONArray("ordistro");
            JSONArray orduniq = jsonObject.getJSONArray("orduniq");
            JSONArray ordnotes = jsonObject.getJSONArray("ordnotes");
            JSONArray ordpymnt = jsonObject.getJSONArray("ordpymnt");
            JSONArray ordcrete = jsonObject.getJSONArray("ordcrete");
            JSONArray ordapprv = jsonObject.getJSONArray("ordapprv");
            JSONArray ordstate = jsonObject.getJSONArray("ordstate");
            JSONArray ordreceipt = jsonObject.getJSONArray("ordreceipt");


            if (ordta.length() > 0) {
                for (int i = 0; i < ordta.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ordetaldta", strDecodbs(ordta.get(i).toString()));
                    contentValues.put("ordetaltme", strDecodbs(ordtme.get(i).toString()));
                    contentValues.put("ordetaloutlt", strDecodbs(orduotlt.get(i).toString()));
                    contentValues.put("ordetalcustos", strDecodbs(ordcustonme.get(i).toString()));
                    contentValues.put("ordetalcustnum", strDecodbs(ordcustonum.get(i).toString()));
                    contentValues.put("ordetaldistos", strDecodbs(ordistro.get(i).toString()));
                    contentValues.put("ordetaluniq", strDecodbs(orduniq.get(i).toString()));
                    contentValues.put("ordetalnotes", strDecodbs(ordnotes.get(i).toString()));
                    contentValues.put("ordetalpymnt", strDecodbs(ordpymnt.get(i).toString()));
                    contentValues.put("ordetalcreate", strDecodbs(ordcrete.get(i).toString()));
                    contentValues.put("ordetalapprv", strDecodbs(ordapprv.get(i).toString()));
                    contentValues.put("ordetalstate", strDecodbs(ordstate.get(i).toString()));
                    contentValues.put("ordetalrecipt", strDecodbs(ordreceipt.get(i).toString()));


                    if (!checkRecordExista("ordesdetal", "ordetaluniq",
                            strDecodbs(orduniq.get(i).toString()))) {
                        writableDatabase.insert("ordesdetal", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /* Insert orders products */
    public boolean insertOrdprdcts(JSONObject jsonObject) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            JSONArray ordprdct = jsonObject.getJSONArray("ordprdct");
            JSONArray ordprcc = jsonObject.getJSONArray("ordprcc");
            JSONArray ordqnty = jsonObject.getJSONArray("ordqnty");
            JSONArray orduniq = jsonObject.getJSONArray("orduniq");
            JSONArray ordetaluniq = jsonObject.getJSONArray("ordetaluniq");

            if (ordprdct.length() > 0) {
                for (int i = 0; i < ordprdct.length(); ++i) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ordprdctprd", strDecodbs(ordprdct.get(i).toString()));
                    contentValues.put("ordprdctprcc", strDecodbs(ordprcc.get(i).toString()));
                    contentValues.put("ordprdctqnty", strDecodbs(ordqnty.get(i).toString()));
                    contentValues.put("ordprdctuniq", strDecodbs(orduniq.get(i).toString()));
                    contentValues.put("orddetaluniq", strDecodbs(ordetaluniq.get(i).toString()));


                    if (!checkRecordExista("ordesprdcts", "ordprdctuniq",
                            strDecodbs(orduniq.get(i).toString()))) {
                        writableDatabase.insert("ordesprdcts", null, contentValues);
                    }

                }
            }
        } catch (JSONException ex) {
        }

        return true;
    }

    /*Vacuum tables*/
    public void delall(ArrayList<String> dbm) {

        for (int rex = 0; rex < dbm.size(); rex++) {
            getWritableDatabase().execSQL("DELETE  FROM " + dbm.get(rex));
            getWritableDatabase().delete("SQLITE_SEQUENCE", "NAME=?", new String[]{dbm.get(rex)});
        }
    }

    /*Vacuum tables*/
    public void delheal(ArrayList<String> dbm, ArrayList<String> uniqidec, ArrayList<String> uniqval) {

        for (int rex = 0; rex < dbm.size(); rex++) {
            getWritableDatabase().execSQL("DELETE  FROM " + dbm.get(rex) + " WHERE " + uniqidec.get(rex) + "=?", new String[]{uniqval.get(rex)});
            getWritableDatabase().execSQL("UPDATE sqlite_sequence set seq = 0 WHERE name = ?", new String[]{dbm.get(rex)});
        }
    }

}
