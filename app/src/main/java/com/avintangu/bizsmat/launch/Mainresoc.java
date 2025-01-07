package com.avintangu.bizsmat.launch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.avintangu.bizsmat.dbcluzz.Dbcluzz;

import java.util.ArrayList;


public class Mainresoc {
    private static Context mrContext;
    Dbcluzz dbcluzz;

    public Mainresoc(Context context) {
        mrContext = context;
        dbcluzz = new Dbcluzz(mrContext);

    }

    /* Get active shop */
    public String gtActiveshop() {
        String shop = "";

        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM usrshops " +
                "WHERE usrshopstate = ?", new String[]{"Active"});

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    shop = res.getString(res.getColumnIndexOrThrow("usrshopname"));

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

    /* Get all shops */
    public ArrayList<String> gtAshops() {
        ArrayList<String> shop = new ArrayList<>();

        Cursor res = dbcluzz.getReadableDatabase().rawQuery("SELECT * FROM usrshops", null);

        try {
            res.moveToFirst();

            if (res != null && res.moveToFirst()) {
                do {
                    shop.add(res.getString(res.getColumnIndexOrThrow("usrshopname")));

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

    /* Update active shop */
    public boolean updatAshop(String shopname) {
        SQLiteDatabase writableDatabase = dbcluzz.getWritableDatabase();
        boolean trace = true;

        ContentValues contentValues = new ContentValues();
        contentValues.put("usrshopstate", "Active");
        writableDatabase.update("usrshops", contentValues, "usrshopname = ?", new String[]{shopname});


        SQLiteDatabase writableDatabasa = dbcluzz.getWritableDatabase();

        ContentValues contentValuesa = new ContentValues();
        contentValuesa.put("usrshopstate", "Inactive");
        writableDatabasa.update("usrshops", contentValuesa, "usrshopname <> ?", new String[]{shopname});


        return trace;

    }
}
