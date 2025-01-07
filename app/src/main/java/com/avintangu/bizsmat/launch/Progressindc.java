package com.avintangu.bizsmat.launch;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.avintangu.bizsmat.R;

public class Progressindc {
    private static Context pbindContext;
    public ProgressDialog stkDialog;

    public Progressindc(Context context) {
        pbindContext = context;

        stkDialog = new ProgressDialog(pbindContext);
        stkDialog.show();
        stkDialog.setCancelable(false);
        stkDialog.setContentView(R.layout.progressbar);
        stkDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void showProgress() {

        try {
            stkDialog.show();

        } catch (Exception ex) {

        }

    }

    public void hideProgress() {
        try {
            stkDialog.dismiss();

        } catch (Exception ex) {

        }

    }
}
