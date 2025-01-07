package com.avintangu.bizsmat.analytics;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class Mpchartcusto extends XAxisRenderer {
    public Mpchartcusto(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer transformer) {
        super(viewPortHandler, xAxis, transformer);

    }

    @Override
    public void drawLabel(Canvas c, String formatedlabel, float x, float y, MPPointF anchor, float angledegrees) {
        String line[] = formatedlabel.split(("\n"));
        Utils.drawXAxisValue(c, line[0], x, y, mAxisLabelPaint, anchor, angledegrees);

        for (int h = 1; h < line.length; h++) {
            Utils.drawXAxisValue(c, line[h], x, y + mAxisLabelPaint.getTextSize() * h, mAxisLabelPaint, anchor, angledegrees);
        }

    }
}
