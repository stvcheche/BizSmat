package com.avintangu.bizsmat.resources;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.Guideline;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.admin.Admin;
import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.debts.Debts;
import com.avintangu.bizsmat.debts.Debtsnwed;
import com.avintangu.bizsmat.inventory.Inventnwed;
import com.avintangu.bizsmat.inventory.Inventory;
import com.avintangu.bizsmat.launch.Progressindc;
import com.avintangu.bizsmat.orders.Orders;
import com.avintangu.bizsmat.orders.Ordesnew;
import com.avintangu.bizsmat.products.Prdctnew;
import com.avintangu.bizsmat.products.Products;
import com.avintangu.bizsmat.sales.Salenew;
import com.avintangu.bizsmat.sales.Sales;
import com.google.android.flexbox.FlexboxLayout;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Resocs {
    private static Context gContext;
    Dbcluzz dbcluzz;
    String currentLanguage = "en", currentLang;
    DecimalFormat rformatter = new DecimalFormat("#,###");
    Progressindc progressindc;

    public Resocs(Context context) {
        gContext = context;
        dbcluzz = new Dbcluzz(gContext);

    }

    /* Set language */
    public void stLanguage() {
        String lang = dbcluzz.gtUsrprefs("lang45268367AGE");
        Locale myLocale;

        if (lang.equalsIgnoreCase(""))
            return;

        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        gContext.getResources().updateConfiguration(config,
                gContext.getResources().getDisplayMetrics());

    }

    /* Change language */
    public void ckLanguage(String localeName, Intent refresh) {
        updatUsrpref("usrprefsval", localeName, "usrprefsuniq", "lang45268367AGE");
        String lang = dbcluzz.gtUsrprefs("lang45268367AGE");
        Locale myLocale;

        if (lang.equalsIgnoreCase(""))
            return;

        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        gContext.getResources().updateConfiguration(config,
                gContext.getResources().getDisplayMetrics());

        refresh.putExtra(currentLang, localeName);
        gContext.startActivity(refresh);


    }

    /* Update language */
    public boolean updatUsrpref(String prefid, String prefval, String uniqident, String uniqidentval) {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = true;

        ContentValues contentValues = new ContentValues();
        contentValues.put(prefid, prefval);
        writableDatabase.update("usrprefs", contentValues, uniqident + " = ?", new String[]{uniqidentval});


        return trace;

    }

    public void activResat(Activity activeRestat, Activity fineto, Class nwIntent,
                           String determtabmng, String determstate, String determstata) {
        activeRestat.finish();

        try {
            Thread.sleep(50);

        } catch (InterruptedException e) {
        }

        fineto.finish();
        Intent intent = new Intent(gContext.getApplicationContext(), nwIntent);

        Bundle bundle = new Bundle();
        bundle.putString("determtabmng", determtabmng);
        bundle.putString("determstate", determstate);
        bundle.putString("determstata", determstata);
        intent.putExtras(bundle);
        gContext.startActivity(intent);
    }

    /*Get font sizes*/
    public double getFontex(Display display, Point point) {
        display.getSize(point);
        int x = point.x;
        int y = point.y;
        return (x + y) / (int) Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0)) * 10.0;
    }

    /*Implode arraylist as a formatted string*/
    public String Implodes(ArrayList list) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); ++i) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public String Implodesa(ArrayList list) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); ++i) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append("','");
            }
        }
        return "'" + sb.toString() + "'";
    }

    public String Implodesb(ArrayList list) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); ++i) {
            sb.append(list.get(i));

            if (i != list.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /* Return Language */
    public ArrayList<String> retuxLang() {
        String defstr = gContext.getResources().getString(R.string.defstr);
        return new ArrayList<>(Arrays.asList("English"));

    }

    /* Get screen size */
    public ArrayList<Float> getSrcsize(Display display, Point point) {
        ArrayList<Float> srcsize = new ArrayList<>();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        Float width = Float.valueOf(display.getWidth());
        Float height = Float.valueOf(display.getHeight());

        int scaleFactor = (int) displayMetrics.density;

        srcsize.add(width / scaleFactor);
        srcsize.add(height / scaleFactor);

        return srcsize;
    }

    /* Adjust dimensions */
    public void adjsDimens(Guideline guidelina) {
        float constimgWidtha = 0f;

        if (gContext.getResources().getConfiguration().orientation == 2) {
            constimgWidtha = 100f;

        } else if (gContext.getResources().getConfiguration().orientation == 1) {
            constimgWidtha = 100f;

        }

        /* Calculated constraints */
        float newimgWidtha = constimgWidtha / 100f;

        guidelina.setGuidelinePercent(newimgWidtha);

    }

    /* Set column number */
    public Integer gtColnum(Display display, Integer minval, Integer maxval) {
        Integer retColnum;
        Float konstWidth = 1f;
        ArrayList<Float> srcsize = getSrcsize(display, new Point());

        if (gContext.getResources().getConfiguration().orientation == 2) {
            konstWidth = srcsize.get(0);

        } else if (gContext.getResources().getConfiguration().orientation == 1) {
            konstWidth = srcsize.get(0);

        }

        float constsrcWidth = 150;
        Double colNum = Math.floor(konstWidth / constsrcWidth);

        if (colNum.intValue() < minval) {
            retColnum = minval;

        } else {
            retColnum = colNum.intValue();
        }

        if (gContext.getResources().getConfiguration().orientation == 1) {
            if (colNum.intValue() > minval) {
                retColnum = minval;

            }

        } else {
            if (gContext.getResources().getConfiguration().orientation == 2) {
                if (colNum.intValue() > maxval) {
                    retColnum = maxval;

                }

            }
        }

        return retColnum;

    }

    /* Date functions */
    public String gtDpresocs(String dta) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formattera = new SimpleDateFormat("dd-MMMM-yyyy");

        String mimo = "nada";
        try {
            Date date = formatter.parse(dta);
            mimo = formattera.format(date);
        } catch (ParseException localParseException) {
        }

        return mimo;
    }

    public String gtDpresocsa(String dta) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
        SimpleDateFormat formattera = new SimpleDateFormat("yyyy-MM-dd");

        String mimo = "nada";
        try {
            Date date = formatter.parse(dta);
            mimo = formattera.format(date);
        } catch (ParseException localParseException) {
        }

        return mimo;
    }

    public String gtDpresocsb(String dta) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formattera = new SimpleDateFormat("MMMM-yyyy");

        String mimo = "nada";
        try {
            Date date = formatter.parse(dta);
            mimo = formattera.format(date);
        } catch (ParseException localParseException) {
        }

        return mimo;
    }

    public String gtDpresocsc(String dta, String dtb) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formattera = new SimpleDateFormat("dd-MMM-yyyy");

        String mimo = "nada";
        try {
            Date date = formatter.parse(dta);
            Date dateb = formatter.parse(dtb);

            mimo = "From: " + formattera.format(date) + "  To: " + formattera.format(dateb);
        } catch (ParseException localParseException) {
        }

        return mimo;
    }

    public String gtDpresocsd(String dta) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formattera = new SimpleDateFormat("dd-MMM-yyyy");

        String mimo = "nada";
        try {
            Date date = formatter.parse(dta);

            mimo = formattera.format(date);
        } catch (ParseException localParseException) {
        }

        return mimo;
    }

    public String gtTmpresocsb(String tme) {
        SimpleDateFormat formatter = new SimpleDateFormat("H:m:s");
        SimpleDateFormat formattera = new SimpleDateFormat("H:m");

        String mimo = "nada";
        try {
            Date date = formatter.parse(tme);
            mimo = formattera.format(date);
        } catch (ParseException localParseException) {
        }

        return mimo;
    }

    /*Generate unique ids*/
    public static int generateViewId() {
        AtomicInteger sNextGeneratedId = new AtomicInteger(1);

        if (Build.VERSION.SDK_INT < 17) {
            for (; ; ) {
                int result = sNextGeneratedId.get();

                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

    /*Generate unique codes*/
    public Long uniqueGen() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        try {
            return date.getTime() - simpleDateFormat.parse("01-06-2017").getTime();
        } catch (ParseException ex) {
            return null;
        }
    }

    /* Return products categories */
    public ArrayList<String> retuxPrdcatry() {

        ArrayList<String> managers = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM prdctdcateri debb LEFT JOIN usrshops debbo " +
                "ON debb.prdctdcaterishop = debbo.usrshopuniq WHERE prdctdcateristate = ? " +
                "AND debbo.usrshopuniq IN(" + gtCombshcode() + ")", new String[]{"Active"});

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    managers.add(res.getString(res.getColumnIndexOrThrow("prdctdcatego")));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }

        if (managers.size() > 0) {
            Collections.sort(managers, String.CASE_INSENSITIVE_ORDER);

        } else {
            managers.add("There are no categories");
        }

        return managers;
    }

    /* Return products categories code*/
    public String retuxPrdcatrycode(String product) {
        String prdcode = "nada";
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM prdctdcateri WHERE " +
                "prdctdcatego = ?", new String[]{product});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    prdcode = (res.getString(res.getColumnIndexOrThrow("prdctdcateriuniq")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return prdcode;
    }

    /* Return products  price*/
    public Double retuxPrdprcc(String product) {
        Double prdprcc = 0.0;
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM prdctdetaz WHERE " +
                "prdctdetazname = ?", new String[]{product});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    prdprcc = (res.getDouble(res.getColumnIndexOrThrow("prdctdetazprcc")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return prdprcc;
    }

    /* Return products  by distributor*/
    public ArrayList<String> retuxPrdbdist(String distributor) {
        ArrayList<String> prdctbdist = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM pricelist debb LEFT JOIN distributors debbe " +
                        "ON debb.pricescdist = debbe.distroscuniq WHERE distroscname = ?",
                new String[]{distributor});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    prdctbdist.add(res.getString(res.getColumnIndexOrThrow("pricescprdct")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }

        if (prdctbdist.size() < 1) {
            prdctbdist.add("No products found");

        } else {
            Collections.sort(prdctbdist, String.CASE_INSENSITIVE_ORDER);

        }


        return prdctbdist;
    }

    /* Return products  price*/
    public Double retuxPrdprcca(String product, String distributor) {
        Double prdprcc = 0.0;
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM pricelist debb LEFT JOIN distributors debbe " +
                        "ON debb.pricescdist = debbe.distroscuniq WHERE pricescprdct = ? AND distroscname = ?",
                new String[]{product, distributor});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    prdprcc = (res.getDouble(res.getColumnIndexOrThrow("pricescprcc")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return prdprcc;
    }

    /* Return products  code */
    public String retuxPrdcodecs(String product, String distributor) {
        String prdprcc = "";
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM pricelist WHERE pricescprdct = ? " +
                        "AND pricescdist = ?",
                new String[]{product, distributor});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    prdprcc = (res.getString(res.getColumnIndexOrThrow("pricescprdcod")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return prdprcc;
    }

    /* Get all distributors */
    public ArrayList<String> getAlldisctros() {
        ArrayList<String> comblist = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM distributors", null);

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    comblist.add(res.getString(res.getColumnIndexOrThrow("distroscname")));

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

    /* Get all distributor code */
    public String getDistcode(String distname) {
        String distcode = "nada";
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM distributors " +
                "WHERE distroscname = ?", new String[]{distname});

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    distcode = res.getString(res.getColumnIndexOrThrow("distroscuniq"));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling
        } finally {
            if (res != null) {
                res.close();
            }
        }

        return distcode;
    }

    /* Return payment modes */
    public ArrayList<String> retPayment() {
        ArrayList<String> paymnt = new ArrayList<>(Arrays.asList("Cash", "Cheque", "M-pesa", "On account", "Cash on delivery"));

        return paymnt;
    }

    /* Return products  code*/
    public String retuxPrdcode(String product) {
        String prdcode = "";
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM prdctdetaz WHERE " +
                "prdctdetazname = ?", new String[]{product});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    prdcode = (res.getString(res.getColumnIndexOrThrow("prdctdetazcode")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return prdcode;
    }

    /* Set date */
    public void freexam(final Button button) {
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH);
        int day = instance.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(gContext, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat formattera = new SimpleDateFormat("dd-MMMM-yyyy");

                String mimo = null;
                try {
                    Date date = formatter.parse(day + "-" + (month + 1) + "-" + year);
                    mimo = formattera.format(date);
                } catch (ParseException localParseException) {
                }

                button.setText(mimo);
            }
        }, year, month, day).show();
    }

    /* Records delete and recovery dialog */
    public void recdDeltdilog(final String detocs, final String stateDetermpr, final String uniqident) {
        String title = "";
        String body = "";

        if (stateDetermpr.equals("Active") || stateDetermpr.equals("Pending")) {
            title = "Deleting prompt";
            body = "Are you sure you want to delete this record ?";

        } else if (stateDetermpr.equals("Inactive") || stateDetermpr.equals("Cancelled")) {
            title = "Recovering  prompt";
            body = "Do you want to recover this record ?";

        }

        final AlertDialog.Builder dialog = new AlertDialog.Builder(gContext);
        LayoutInflater inflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custo_dilogue, null);
        dialog.setView(dialogView);
        dialog.setTitle(title);
        ((TextView) dialogView.findViewById(R.id.textDialoga)).setText(body);
        ((ImageView) dialogView.findViewById(R.id.imageDialog)).setImageResource(R.drawable.alert);

        Button btnok = dialogView.findViewById(R.id.accept);
        Button btncancel = dialogView.findViewById(R.id.cnclButton);

        dialog.create();
        final AlertDialog show = dialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();

                if (detocs.equals("editProduct")) {
                    delRecd("prdctdetaz", "prdctdetazstate", "prdctdetazcode", uniqident, stateDetermpr);

                    /* Refresh activity */
                    activResat(Products.propsactiv, Prdctnew.prdcnewrecod, Products.class,
                            Products.prdtabManage.getText().toString(), Products.prdStatus.getText().toString(),
                            "nada");

                    /* Insert updated record inuque id */
                    insertUpdatcodes(retStrdate().get(7), "Products", uniqident);

                } else if (detocs.equals("editCategory")) {
                    delRecd("prdctdcateri", "prdctdcateristate", "prdctdcateriuniq", uniqident, stateDetermpr);

                    /* Refresh activity */
                    activResat(Products.propsactiv, Prdctnew.prdcnewrecod, Products.class,
                            Products.prdtabManage.getText().toString(), Products.prdStatus.getText().toString(),
                            "nada");

                    /* Insert updated record inuque id */
                    insertUpdatcodes(retStrdate().get(7), "ProductsCategory", uniqident);

                } else if (detocs.equals("editSale")) {
                    delRecd("salesrecd", "salesrecdstate", "salesrecdrecod", uniqident, stateDetermpr);

                    /* Refresh activity */
                    activResat(Sales.salesactiv, Salenew.salnewrecod, Sales.class,
                            Sales.saltabManage.getText().toString(), Sales.salStatus.getText().toString(),
                            "nada");

                    /* Insert updated record inuque id */
                    insertUpdatcodes(retStrdate().get(7), "Sales", uniqident);

                } else if (detocs.equals("editStocks")) {
                    delRecd("inventbl", "inventblstate", "inventblrecod", uniqident, stateDetermpr);

                    /* Refresh activity */
                    activResat(Inventory.inventactiv, Inventnwed.inventnewrecod, Inventory.class,
                            Inventory.invtabManage.getText().toString(), Inventory.invStatus.getText().toString(),
                            "nada");

                    /* Insert updated record inuque id */
                    insertUpdatcodes(retStrdate().get(7), "Stocks", uniqident);

                } else if (detocs.equals("editPayment")) {
                    delRecd("custodatpay", "custodatpaystate", "custodatpayrecod", uniqident, stateDetermpr);

                    /* Refresh activity */
                    activResat(Debts.debtactiv, Debtsnwed.debtnewactive, Debts.class,
                            Debts.databManage.getText().toString(), Debts.datStatus.getText().toString(),
                            "nada");

                    /* Insert updated record inuque id */
                    insertUpdatcodes(retStrdate().get(7), "Stocks", uniqident);

                } else if (detocs.equals("editord")) {
                    Ordesnew.ordessavBtn.performClick();

                }

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
            }
        });

    }

    public void recdDeltdiloga(final String detocs, final String stateDetermpr, final String uniqident,
                               final String trelis, final String treliscode) {
        String title = "Deleting prompt";
        String body = "Are you sure you want to delete this record ?";


        final AlertDialog.Builder dialog = new AlertDialog.Builder(gContext);
        LayoutInflater inflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custo_dilogue, null);
        dialog.setView(dialogView);
        dialog.setTitle(title);
        ((TextView) dialogView.findViewById(R.id.textDialoga)).setText(body);
        ((ImageView) dialogView.findViewById(R.id.imageDialog)).setImageResource(R.drawable.alert);

        Button btnok = dialogView.findViewById(R.id.accept);
        Button btncancel = dialogView.findViewById(R.id.cnclButton);

        dialog.create();
        final AlertDialog show = dialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
                if (detocs.equals("editCustos")) {
                    delRecd("custostbl", "custostblstate", "custostblrecod", uniqident, stateDetermpr);

                    /* Insert updated record inuque id */
                    insertUpdatcodes(retStrdate().get(7), "Customers", uniqident);

                    Debts.debtactiv.finish();
                    Debtsnwed.debtnewactive.finish();

                    Intent intent = new Intent(gContext.getApplicationContext(), Debts.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("determtabmng", "debtSrecod");
                    bundle.putString("determstate", "Active");
                    intent.putExtras(bundle);
                    gContext.startActivity(intent);

                } else if (detocs.equals("editDebtrec")) {
                    delRecd("custodates", "custodatestate", "custodatesrecod", uniqident, stateDetermpr);

                    /* Insert updated record inuque id */
                    insertUpdatcodes(retStrdate().get(7), "Debt", uniqident);

                    Debtsnwed.debtnewactive.finish();
                    com.avintangu.bizsmat.debts.Debtsui duis = new com.avintangu.bizsmat.debts.Debtsui(gContext);
                    Intent intent = new Intent(gContext.getApplicationContext(), Debtsnwed.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("debtdeterm", "ediDatsum");
                    ArrayList<ArrayList<String>> paysumdetal = duis.debtSinglerecd(trelis, treliscode);
                    bundle.putSerializable("debtdetal", paysumdetal);
                    intent.putExtras(bundle);

                    intent.putExtras(bundle);
                    gContext.startActivity(intent);

                }

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
            }
        });

    }

    public void recdDeltdilogb(final String dbess, final String stateidec, final String uniqident, final String uniqidentval) {
        String title = "Recovery prompt";
        String body = "Are you sure you want to recover this record ?";


        final AlertDialog.Builder dialog = new AlertDialog.Builder(gContext);
        LayoutInflater inflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custo_dilogue, null);
        dialog.setView(dialogView);
        dialog.setTitle(title);
        ((TextView) dialogView.findViewById(R.id.textDialoga)).setText(body);
        ((ImageView) dialogView.findViewById(R.id.imageDialog)).setImageResource(R.drawable.fabactmenu);

        Button btnok = dialogView.findViewById(R.id.accept);
        Button btncancel = dialogView.findViewById(R.id.cnclButton);

        dialog.create();
        final AlertDialog show = dialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();

                String steeyks = "";
                if (dbess.equals("ordesdetal")) {
                    steeyks = "Cancelled";
                } else {
                    steeyks = "Inactive";
                }

                delRecd(dbess, stateidec, uniqident, uniqidentval, steeyks);

                /* Insert updated record inuque id */
                insertUpdatcodes(retStrdate().get(7), "RecovredRecd", uniqidentval);

                Admin.adminactiv.finish();

                Intent intent = new Intent(gContext.getApplicationContext(), Admin.class);

                Bundle bundle = new Bundle();
                bundle.putString("determtabmng", "adminSrecoda");
                bundle.putString("determstate", "Active");
                intent.putExtras(bundle);
                gContext.startActivity(intent);

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
            }
        });

    }

    /* Delete record */
    public boolean delRecd(String dbm, String micer, String uniqident, String uniqidentval, String stateDetermpr) {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = true;
        String state = "Inactive";

        if (stateDetermpr.equals("Active")) {
            state = "Inactive";

        } else if (stateDetermpr.equals("Inactive")) {
            state = "Active";

        } else if (stateDetermpr.equals("Pending")) {
            state = "Cancelled";

        } else if (stateDetermpr.equals("Cancelled")) {
            state = "Pending";

        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(micer, state);
        writableDatabase.update(dbm, contentValues, uniqident + " = ?", new String[]{uniqidentval});


        return trace;

    }


    /* Get stocks balance */
    public Double retuxStkbal(String prdcode) {
        Double tInvent = 0.0;
        String steta = "Active";

        String sqlite = "SELECT * FROM prdctdetaz debb LEFT JOIN " +
                "(SELECT *, IFNULL(SUM(salesrecdqnty),0) AS salsum FROM salesrecd WHERE salesrecdstate= ? GROUP BY salesrecdprdcod)  AS debby ON debb.prdctdetazcode = debby.salesrecdprdcod LEFT JOIN " +
                "(SELECT *, IFNULL(SUM(inventblqnty),0) AS invsum FROM inventbl WHERE inventblstate = ? GROUP BY inventblprdcod)  AS debbo ON debb.prdctdetazcode = debbo.inventblprdcod LEFT JOIN " +
                "(SELECT *, IFNULL(SUM(custodatesqnty),0) AS debtsum FROM custodates WHERE custodatestate = ? GROUP BY custodatesprdct) AS debbi ON debb.prdctdetazcode = debbi.custodatesprdct " +
                "WHERE debb.prdctdetazstate = ? AND prdctdetazcode = ? ORDER BY debb.prdctdetazid";

        Cursor res = dbcluzz.getReadableDatabase().rawQuery(sqlite, new String[]{steta, steta, steta, steta, prdcode});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    tInvent = (res.getDouble(res.getColumnIndexOrThrow("invsum")) - (res.getDouble(res.getColumnIndexOrThrow("salsum")) + res.getDouble(res.getColumnIndexOrThrow("debtsum"))));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return tInvent;
    }

    /* Get the date bounds for available stocks */
    public String gtDatebounds(String prdCode) {
        String stkIdec = "";
        Double sktsum = 0.0;
        String state = "Active";
        Double stkBal = retuxStkbal(prdCode);

        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM inventbl " +
                "WHERE inventblstate = ? AND inventblprdcod = ? ORDER BY inventblid DESC", new String[]{state, prdCode});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    sktsum += (res.getDouble(res.getColumnIndexOrThrow("inventblqnty")));


                    if (stkBal.intValue() <= sktsum.intValue()) {
                        stkIdec = (res.getString(res.getColumnIndexOrThrow("inventblid")));

                        break;
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

        return stkIdec;

    }

    /* Get the average buying price
    public Double retuxAvsalp(String prdcode) {
        String idecBound = gtDatebounds(prdcode);
        String state = "Active";


        Double avSalpr = 0.0;
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT *, AVG(inventblbprc) AS inventav " +
                "FROM inventbl WHERE  inventblid >= ? AND inventblstate = ? AND inventblprdcod = ?  AND " +
                "inventblbprc>0", new String[]{idecBound, state, prdcode});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    avSalpr = (res.getDouble(res.getColumnIndexOrThrow("inventav")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return avSalpr;
    }*/

    /* Get the actual buying price */
    public Double retuxActbprc(String prdcode) {
        String idecBound = gtDatebounds(prdcode);
        String state = "Active";


        Double avSalpr = 0.0;
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM inventbl " +
                "WHERE  inventblid = ? AND inventblstate = ? AND inventblprdcod = ?  AND " +
                "inventblbprc>0 ORDER BY inventblid ASC", new String[]{idecBound, state, prdcode});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    avSalpr = (res.getDouble(res.getColumnIndexOrThrow("inventblbprc")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return avSalpr;
    }

    /* Return current String date */
    public ArrayList<String> retStrdate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        SimpleDateFormat dateFormata = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatb = new SimpleDateFormat("H:mm");
        SimpleDateFormat dateFormatc = new SimpleDateFormat("dd-MMM-yyyy");

        ArrayList<String> dates = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fstMnthday = cal.getTime();

        Calendar pal = Calendar.getInstance();
        Date currDates = pal.getTime();

        Calendar cala = Calendar.getInstance();
        cala.set(Calendar.DATE, cala.getActualMaximum(Calendar.DATE));
        Date lstdayofmnth = cala.getTime();

        Calendar zal = Calendar.getInstance();
        zal.set(Calendar.DAY_OF_MONTH, 1);
        zal.add(Calendar.MONTH, -4);
        Date lstSixmnths = zal.getTime();

        dates.add(dateFormat.format(fstMnthday));
        dates.add(dateFormatc.format(fstMnthday));
        dates.add(dateFormata.format(fstMnthday));

        dates.add(dateFormat.format(lstdayofmnth));
        dates.add(dateFormata.format(lstdayofmnth));
        dates.add(dateFormatc.format(lstdayofmnth));

        dates.add(dateFormat.format(currDates));
        dates.add(dateFormata.format(currDates));
        dates.add(dateFormatb.format(currDates));

        dates.add(dateFormat.format(lstSixmnths));
        dates.add(dateFormata.format(lstSixmnths));
        dates.add(dateFormatc.format(lstSixmnths));


        return dates;

    }

    /*Get all products*/
    public ArrayList<String> getallPRD() {
        ArrayList<String> list = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM prdctdetaz debb LEFT JOIN usrshops debbo " +
                "ON debb.prdctdetazshop = debbo.usrshopuniq WHERE  prdctdetazstate = ? " +
                "AND debbo.usrshopuniq IN(" + gtCombshcode() + ")", new String[]{"Active"});

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    list.add(res.getString(res.getColumnIndexOrThrow("prdctdetazname")));
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
            list.add(0, "Select");

        } else {
            list.add(0, "You have no products");


        }

        return list;
    }

    /*Get all customers*/
    public ArrayList<String> getallCustos() {
        ArrayList<String> list = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM custostbl debb LEFT JOIN usrshops debbo " +
                "ON debb.custostblshop = debbo.usrshopuniq WHERE custostblstate = ? " +
                "AND debbo.usrshopuniq IN(" + gtCombshcode() + ")", new String[]{"Active"});

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    list.add(res.getString(res.getColumnIndexOrThrow("custostblname")));
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
            list.add(0, "Select");

        } else {
            list.add(0, "You have no customers");


        }

        return list;
    }

    /*  Get customers codes */
    public String retuxCuscodes(String custname) {
        String prdcode = "nada";
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM custostbl WHERE " +
                "custostblname = ?", new String[]{custname});

        try {

            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    prdcode = (res.getString(res.getColumnIndexOrThrow("custostblrecod")));
                } while (res.moveToNext());
            }

        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }


        return prdcode;
    }

    /*  Dynamic spinner */
    public void spinMod(final TextView txtmod, final double eunica, final ArrayList txtlist,
                        final String capdeterm) {

        final String detocs = "";

        txtmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtmod.setClickable(false);
                txtmod.setFocusable(false);

                progressindc = new Progressindc(gContext);
                progressindc.showProgress();

                /*Build dialogue*/
                AlertDialog.Builder dialogbld = new AlertDialog.Builder(gContext);
                LayoutInflater inflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.spinsearch, null);
                LinearLayout lnlay = dialogView.findViewById(R.id.cotactlay);
                dialogbld.setView(dialogView);

                ImageView btncancel = dialogView.findViewById(R.id.cnclButton);
                final EditText inpocs = dialogView.findViewById(R.id.ttlehead);
                final AlertDialog dilog = dialogbld.create();
                dilog.setCancelable(false);
                dilog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


                btncancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dilog.dismiss();
                        txtmod.setClickable(true);
                        txtmod.setFocusable(true);

                    }
                });

                /*Build dialogue*/
                new Resocs.srchSpin(txtmod, eunica, lnlay, inpocs, dilog, txtlist, capdeterm).execute(detocs);

            }
        });
    }

    /*Dynamic spinner display*/
    public void shContent(LinearLayout tbl, final String title, final double eunica, final AlertDialog dilog,
                          final TextView txtmod, final ArrayList items, final String capdeterm) {
        ArrayList<Integer> uniqid = new ArrayList<>();

        for (int i = 0; i < 6; ++i) {
            uniqid.add(generateViewId());
        }

        LayoutInflater inflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cont = inflater.inflate(R.layout.spnsrchresult, null);

        View child = ((ViewGroup) cont).getChildAt(0);
        final TextView itemz = (TextView) child;
        itemz.setId(Math.abs(uniqid.get(0)));
        itemz.setText(title);
        itemz.setTextSize((float) eunica + 5.0f);
        itemz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtmod.setClickable(true);
                txtmod.setFocusable(true);

                if (capdeterm.equals("kap")) {
                    if (retuxStkbal(retuxPrdcode(title)).intValue() > 0) {
                        txtmod.setText(title);
                        dilog.dismiss();
                    } else {
                        dilog.dismiss();
                        Toast.makeText(gContext, title + "  is out of stock, please add some stocks " +
                                "from the inventory module", Toast.LENGTH_LONG).show();
                    }
                } else if (capdeterm.equals("zap")) {
                    txtmod.setText(title);
                    WindowManager wm = (WindowManager) gContext.getSystemService(gContext.WINDOW_SERVICE);
                    com.avintangu.bizsmat.orders.Ordersui ordersui = new com.avintangu.bizsmat.orders.Ordersui(gContext);
                    ordersui.ordbackrecdlod(Orders.ordscroLayout, eunica, gtColnum(wm.getDefaultDisplay(), 4, 6),
                            Orders.ordtabManage.getText().toString(), Orders.ordStatus.getText().toString(), Orders.ordFrodta.getText().toString(),
                            Orders.ordTodta.getText().toString(), Orders.ordesprogbar, title, Orders.ordeshedcon);
                    dilog.dismiss();

                } else if (capdeterm.equals("shoppa")) {
                    com.avintangu.bizsmat.launch.Mainresoc mainresoc = new com.avintangu.bizsmat.launch.Mainresoc(gContext);
                    txtmod.setText(title);
                    mainresoc.updatAshop(title);
                    dilog.dismiss();

                } else if (capdeterm.equals("ordeskes")) {
                    com.avintangu.bizsmat.orders.Ordersresoc ordresok = new com.avintangu.bizsmat.orders.Ordersresoc(gContext);
                    String stecks = ordresok.retuxStkstate(title);

                    if (stecks.equals("In stock")) {
                        txtmod.setText(title);
                        dilog.dismiss();

                    } else {
                        dilog.dismiss();
                        Toast.makeText(gContext, title + "  is out of stock, please contact the " +
                                "selected distributor", Toast.LENGTH_LONG).show();
                    }

                } else {
                    txtmod.setText(title);
                    dilog.dismiss();
                }
            }
        });

        tbl.addView(cont, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private class srchSpin extends AsyncTask<String, Void, String> {
        final TextView param;
        final double parama;
        final LinearLayout paramb;
        final EditText paramc;
        final AlertDialog paramd;
        final ArrayList<String> txtlistpr;
        final String capdetermpr;

        public srchSpin(final TextView txtmod, final double eunica, LinearLayout lnlay, EditText inpocs, AlertDialog dilog,
                        ArrayList txtlist, String capdeterm) {
            this.param = txtmod;
            this.parama = eunica;
            this.paramb = lnlay;
            this.paramc = inpocs;
            this.paramd = dilog;
            this.txtlistpr = txtlist;
            this.capdetermpr = capdeterm;

        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";

            if (txtlistpr.size() > 0) {
                if (txtlistpr.get(0).equals("Select")) {
                    txtlistpr.remove(0);
                }
            }

            /*Search*/
            paramc.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    String stSrch = paramc.getText().toString();
                    ArrayList<String> nwArray = new ArrayList<>();

                    for (int i = 0; i < txtlistpr.size(); i++) {
                        if (txtlistpr.get(i).toLowerCase().contains(stSrch.toLowerCase())) {
                            nwArray.add(txtlistpr.get(i));
                        }
                    }

                    paramb.removeAllViews();
                    for (int deg = 0; deg < nwArray.size(); deg++) {
                        shContent(paramb, nwArray.get(deg), parama, paramd, param, txtlistpr,
                                capdetermpr);
                    }
                }
            });
            /*Search*/
            for (int meg = 0; meg < txtlistpr.size(); meg++) {
                shContent(paramb, txtlistpr.get(meg), parama, paramd, param, txtlistpr,
                        capdetermpr);
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            paramd.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        progressindc.hideProgress();
                    } catch (Exception ex) {

                    }
                }
            }, 100);

        }
    }

    /* Remove sales entry  layout row*/
    public void remvRow(final LinearLayout prntLinear, final FlexboxLayout usrFlexbox, final ArrayList<Integer> nwPrdcontidec,
                        final ArrayList<Integer> nwPrdctidec, final ArrayList<Integer> nwPrccidec, final ArrayList<Integer> nwQntyidec,
                        final Integer Contidec, final Integer prdctId, final Integer prccid, final Integer qntyid, final LinearLayout usrLinear,
                        final TextView trelis) {
        String title = "Entries prompt";
        String body = "Are you sure you want to remove this entry?";


        final AlertDialog.Builder dialog = new AlertDialog.Builder(gContext);
        LayoutInflater inflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custo_dilogue, null);
        dialog.setView(dialogView);
        dialog.setTitle(title);
        ((TextView) dialogView.findViewById(R.id.textDialoga)).setText(body);
        ((ImageView) dialogView.findViewById(R.id.imageDialog)).setImageResource(R.drawable.alert);

        Button btnok = dialogView.findViewById(R.id.accept);
        Button btncancel = dialogView.findViewById(R.id.cnclButton);

        dialog.create();
        final AlertDialog show = dialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
                prntLinear.removeView(usrFlexbox);
                nwPrdcontidec.remove(Contidec);
                nwPrdctidec.remove(prdctId);
                nwPrccidec.remove(prccid);
                nwQntyidec.remove(qntyid);

                com.avintangu.bizsmat.sales.Salenewui SalUI = new com.avintangu.bizsmat.sales.Salenewui(gContext);
                trelis.setText(rformatter.format(SalUI.trelisCalc(usrLinear, nwPrccidec, nwQntyidec)));

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
            }
        });

    }

    /* Remove new stocks entry  layout row*/
    public void remvinvRow(final LinearLayout prntLinear, final FlexboxLayout usrFlexbox, final ArrayList<Integer> nwPrdcontidec,
                           final ArrayList<Integer> nwPrdctidec, final ArrayList<Integer> nwPrccidec, final ArrayList<Integer> nwPrccideca,
                           final ArrayList<Integer> nwQntyidec, final Integer Contidec, final Integer prdctId,
                           final Integer prccid, final Integer prccida, final Integer qntyid) {
        String title = "Entries prompt";
        String body = "Are you sure you want to remove this entry?";


        final AlertDialog.Builder dialog = new AlertDialog.Builder(gContext);
        LayoutInflater inflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custo_dilogue, null);
        dialog.setView(dialogView);
        dialog.setTitle(title);
        ((TextView) dialogView.findViewById(R.id.textDialoga)).setText(body);
        ((ImageView) dialogView.findViewById(R.id.imageDialog)).setImageResource(R.drawable.alert);

        Button btnok = dialogView.findViewById(R.id.accept);
        Button btncancel = dialogView.findViewById(R.id.cnclButton);

        dialog.create();
        final AlertDialog show = dialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
                prntLinear.removeView(usrFlexbox);
                nwPrdcontidec.remove(Contidec);
                nwPrdctidec.remove(prdctId);
                nwPrccidec.remove(prccid);
                nwPrccideca.remove(prccida);
                nwQntyidec.remove(qntyid);
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
            }
        });

    }

    /* Remove orders entry  layout row*/
    public void remvOrdrows(final LinearLayout prntLinear, final FlexboxLayout usrFlexbox, final ArrayList<Integer> nwPrdcontidec,
                            final ArrayList<Integer> nwPrdctidec, final ArrayList<Integer> nwPrccidec, final ArrayList<Integer> nwQntyidec,
                            final Integer Contidec, final Integer prdctId, final Integer prccid, final Integer qntyid,
                            final ArrayList<String> nwUniqidec, final TextView trelis) {
        String title = "Entries prompt";
        String body = "Are you sure you want to remove this entry?";


        final AlertDialog.Builder dialog = new AlertDialog.Builder(gContext);
        LayoutInflater inflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custo_dilogue, null);
        dialog.setView(dialogView);
        dialog.setTitle(title);
        ((TextView) dialogView.findViewById(R.id.textDialoga)).setText(body);
        ((ImageView) dialogView.findViewById(R.id.imageDialog)).setImageResource(R.drawable.alert);

        Button btnok = dialogView.findViewById(R.id.accept);
        Button btncancel = dialogView.findViewById(R.id.cnclButton);

        dialog.create();
        final AlertDialog show = dialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
                int ides = nwPrdcontidec.indexOf(Contidec);

                prntLinear.removeView(usrFlexbox);
                nwPrdcontidec.remove(Contidec);
                nwPrdctidec.remove(prdctId);
                nwPrccidec.remove(prccid);
                nwQntyidec.remove(qntyid);
                nwUniqidec.remove(ides);

                com.avintangu.bizsmat.orders.Ordesnewui OrdUI = new com.avintangu.bizsmat.orders.Ordesnewui(gContext);
                trelis.setText(rformatter.format(OrdUI.ordtrelisCalc(prntLinear, nwPrccidec, nwQntyidec)));

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                show.dismiss();
            }
        });

    }

    /*Generate product unique code*/
    public String instPrdctcd(String shopcode, Integer n) {
        Long uniqueGen = uniqueGen();
        StringBuilder sb = new StringBuilder();

        String[] uniqCover = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA",
                "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO",
                "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ", "BA", "BB", "BC",
                "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR",
                "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG",
                "CH", "CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV",
                "CW", "CX", "CY", "CZ", "DA", "DB", "DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK",
                "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "DV", "DW", "DX", "DY", "DZ",
                "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ", "EK", "EL", "EM", "EN", "EO", "EP", "EQ",
                "ER", "ES", "ET", "EU", "EV", "EW", "EX", "EY", "EZ", "FA", "FB", "FC", "FD", "FE", "FF", "FG", "FH",
                "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV", "FW", "FX", "FY",
                "FZ", "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN", "GO",
                "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX", "GY", "GZ"};

        sb.append(shopcode + uniqueGen + uniqCover[n]);

        return String.valueOf(sb);
    }

    /* Check the remaining days for app usage */
    public String ckUsagedayz() {
        String switocs = "pass";

        if (dbcluzz.getUsrdetal().size() > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            long mod = 0L;

            try {
                mod = (simpleDateFormat.parse(dbcluzz.getUsrdetal().get(5)).getTime() - date.getTime()) / (24 * 60 * 60 * 1000);

            } catch (android.net.ParseException | java.text.ParseException ex) {

            }

            if (mod > 0) {
                switocs = "pass";
            } else {
                switocs = "nanpass";
            }
        }

        return switocs;
    }

    /* Update app-lic */
    public boolean updatApplic() {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = true;
        String prefval = "PAP";

        ContentValues contentValues = new ContentValues();
        contentValues.put("usrdetallic", prefval);
        writableDatabase.update("usrdetal", contentValues, "usrdetallic" + " <> ?", new String[]{""});


        return trace;

    }

    /* Get all shops */
    public ArrayList<String> getAllshops() {
        ArrayList<String> comblist = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM usrshops", null);

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrshopusr")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrshopname")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrshopuniq")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrshopstate")));

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

    public ArrayList<String> getAllshopsa() {
        ArrayList<String> comblist = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM usrshops", null);

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrshopname")));

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

    /* Get all active shops */
    public ArrayList<String> getActivshop() {
        ArrayList<String> comblist = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM usrshops WHERE usrshopstate=?  ", new String[]{"Active"});

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrshopusr")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrshopname")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrshopuniq")));
                    comblist.add(res.getString(res.getColumnIndexOrThrow("usrshopstate")));

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


    /* Get active shop code */
    public String gtAshopcode() {
        String shop = "";

        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM usrshops " +
                "WHERE usrshopstate = ?", new String[]{"Active"});

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    shop = res.getString(res.getColumnIndexOrThrow("usrshopuniq"));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }

        return shop;
    }

    /* Get all shops codes */
    public String gtCombshcode() {
        ArrayList<String> shop = new ArrayList<>();

        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM usrshops WHERE usrshopstate = ?",
                new String[]{"Active"});

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    shop.add(res.getString(res.getColumnIndexOrThrow("usrshopuniq")));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }

        return Implodesa(shop);
    }

    /* Get single shop code */
    public String gtshopcode(String shopname) {
        String shop = "nada";

        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM usrshops " +
                "WHERE usrshopname = ?", new String[]{shopname});

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    shop = res.getString(res.getColumnIndexOrThrow("usrshopuniq"));

                } while (res.moveToNext());
            }
        } catch (Exception e) {
            // exception handling

        } finally {
            if (res != null) {
                res.close();
            }
        }

        return shop;
    }

    /* Insert update codes */
    public boolean insertUpdatcodes(String instdate, String rptname, String reptcode) {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("updat_date", instdate);
        contentValues.put("updat_rpt", rptname);
        contentValues.put("updat_rptcode", reptcode);
        writableDatabase.insert("updatable", null, contentValues);


        return true;
    }

    /* Get updatable records*/
    public ArrayList<String> getUprecods() {
        ArrayList<String> comblist = new ArrayList<>();
        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM usrdetal debb LEFT JOIN updatable debby " +
                "ON debb.usrupdatval<=debby.updat_date", null);

        try {
            res.moveToFirst();
            if (res != null && res.moveToFirst()) {
                do {

                    if (!res.getString(res.getColumnIndexOrThrow("updat_rptcode")).equals("")) {
                        comblist.add(res.getString(res.getColumnIndexOrThrow("updat_rptcode")));
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

}
