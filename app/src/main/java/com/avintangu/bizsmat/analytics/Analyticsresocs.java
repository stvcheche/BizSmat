package com.avintangu.bizsmat.analytics;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.resources.Resocs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Analyticsresocs {
    private static Context liticsresContext;
    Resocs resources;
    Dbcluzz dbcluzz;

    public Analyticsresocs(Context context) {
        liticsresContext = context;

        resources = new Resocs(liticsresContext);
        dbcluzz = new Dbcluzz(liticsresContext);

    }

    public String dateFomac(String dta) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yy");
        SimpleDateFormat formatterb = new SimpleDateFormat("MMM-yy");

        String mimo = null;
        try {
            Date date = formatter.parse(dta);
            mimo = formatterb.format(date);
        } catch (ParseException localParseException) {
        }

        return mimo;
    }

    public Date dateCrux(String dta) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(dta);
        } catch (ParseException localParseException) {
        }

        return date;
    }

    public Date gtFstdate(String dta) {
        Date fromDate = null;
        try {
            fromDate = new SimpleDateFormat("dd-MMMM-yy").parse(dta);
        } catch (ParseException localParseException) {
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(2, 0);
        calendar.set(5, 1);

        Date firstDayOfMonth = calendar.getTime();

        return firstDayOfMonth;
    }

    public Date gtLstdate(String dta) {
        Date fromDate = null;
        try {
            fromDate = new SimpleDateFormat("dd-MMMM-yy").parse(dta);

        } catch (ParseException localParseException) {
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(2, 1);
        calendar.set(5, 1);
        calendar.add(5, -1);

        Date lastDayOfMonth = calendar.getTime();

        return lastDayOfMonth;
    }

    public Date creadzoomix(String dta) {
        String daye = "" + dta + "-01-01";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyy-M-dd").parse(daye);
        } catch (ParseException localParseException) {
        }

        return date;
    }

    public Date creadzoomixa(String dta) {
        String daye = "" + dta + "-12-31";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyy-M-dd").parse(daye);
        } catch (ParseException localParseException) {
        }

        return date;
    }

    /* Get date differences */
    public Long gtDtadif(String frodta, String todta) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Date data = null;
        Date dove = null;
        try {
            data = new SimpleDateFormat("yyyy-MM-dd").parse(frodta);
            dove = new SimpleDateFormat("yyyy-MM-dd").parse(todta);
        } catch (ParseException localParseException) {
        }

        start.setTime(data);
        end.setTime(dove);
        end.add(5, 1);

        Date startDate = start.getTime();
        Date endDate = end.getTime();

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / 86400000L;

        start.add(5, (int) diffDays);
        while (start.before(end)) {
            start.add(5, 1);
            diffDays += 1L;
        }

        while (start.after(end)) {
            start.add(5, -1);
            diffDays -= 1L;
        }

        return diffDays;
    }

    /* Cluster dates */
    public ArrayList<ArrayList> datesGap(String frodta, String todta) {
        ArrayList<ArrayList> combisto = new ArrayList();

        ArrayList<String> dates = new ArrayList();
        ArrayList<Date> datesa = new ArrayList();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatt = new SimpleDateFormat("dd-MMM-yy");

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        try {
            beginCalendar.setTime(formatter.parse(frodta));
            finishCalendar.setTime(formatter.parse(todta));
            finishCalendar.add(Calendar.DATE, 1);
        } catch (ParseException localParseException) {
        }

        while (beginCalendar.before(finishCalendar)) {
            String monta = formatt.format(beginCalendar.getTime());
            datesa.add(beginCalendar.getTime());
            dates.add(monta);
            beginCalendar.add(Calendar.DATE, 1);
        }

        combisto.add(dates);
        combisto.add(datesa);

        return combisto;
    }

    /* Cluster months */
    public ArrayList<String> datesGapa(String frodta, String todta) {
        ArrayList<String> dates = new ArrayList();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatt = new SimpleDateFormat("dd-MMMM-yy");

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();
        try {
            beginCalendar.setTime(formatter.parse(frodta));
            finishCalendar.setTime(formatter.parse(todta));
        } catch (ParseException localParseException) {
        }

        while (beginCalendar.before(finishCalendar)) {
            String monta = formatt.format(beginCalendar.getTime());
            dates.add(monta);
            beginCalendar.add(Calendar.MONTH, 1);
        }

        return dates;
    }

    /* Year cluster */
    public ArrayList<String> datesYapa(String frodta, String todta) {
        ArrayList<String> dates = new ArrayList();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatt = new SimpleDateFormat("yyyy");

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();
        try {
            beginCalendar.setTime(formatter.parse(frodta));
            finishCalendar.setTime(formatter.parse(todta));
        } catch (ParseException localParseException) {
        }

        while (beginCalendar.before(finishCalendar)) {
            String monta = formatt.format(beginCalendar.getTime());
            dates.add(monta);
            beginCalendar.add(2, 12);
        }

        return dates;
    }

    /* Get column number */
    public ArrayList<Integer> liticsDimens() {
        Integer retColnum;
        Float konstWidth = 1f;
        Float konstHeight = 1f;

        float idealWidth = 290;
        float idealHeight = 280;

        ArrayList<Integer> retDimens = new ArrayList<>();
        WindowManager winmanage = (WindowManager) liticsresContext.getSystemService(Context.WINDOW_SERVICE);
        ArrayList<Float> srcsize = resources.getSrcsize(winmanage.getDefaultDisplay(), new Point());

        if (liticsresContext.getResources().getConfiguration().orientation == 2) {
            konstWidth = srcsize.get(1);
            konstHeight = srcsize.get(0);

            idealWidth = 300;
            idealHeight = 210;

        } else if (liticsresContext.getResources().getConfiguration().orientation == 1) {
            konstWidth = srcsize.get(0);
            konstHeight = srcsize.get(1);

            idealWidth = 360;
            idealHeight = 280;

        }

        float constsrcWidth = 360;
        float constsrcHeight = 672;

        float widthFactor = konstWidth / constsrcWidth;
        float heightFactor = konstHeight / constsrcHeight;

        final float scale = liticsresContext.getResources().getDisplayMetrics().density;
        retDimens.add((int) ((1 * idealWidth) * (scale + 0.0001)));
        retDimens.add((int) ((1 * idealHeight) * (scale + 0.0001)));

        return retDimens;

    }

}


