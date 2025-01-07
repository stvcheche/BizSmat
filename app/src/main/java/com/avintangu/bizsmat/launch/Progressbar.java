package com.avintangu.bizsmat.launch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.avintangu.bizsmat.R;

public class Progressbar {
    private static Context pbContext;

    AlertDialog.Builder dialog;
    AlertDialog actdilog;

    public Progressbar(Context context, String message, String type) {
        pbContext = context;

        dialog = new AlertDialog.Builder(pbContext);
        LayoutInflater inflater = (LayoutInflater) pbContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custo_progress, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        ((TextView) dialogView.findViewById(R.id.textDialog)).setText(message);
        ImageView btnok = dialogView.findViewById(R.id.icoclose);

        if (!type.equals("can")) {
            btnok.setVisibility(View.INVISIBLE);

        }

        dialog.create();
        actdilog = dialog.create();


        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    actdilog.dismiss();

                } catch (Exception ex) {

                }
            }
        });

    }

    public void showProgress() {

        try {
            actdilog.show();
            actdilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(0, 25, 34, 100)));

        } catch (Exception ex) {

        }

    }

    public void hideProgress() {
        try {
            actdilog.dismiss();

        } catch (Exception ex) {

        }

    }
}
