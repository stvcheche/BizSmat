package com.avintangu.bizsmat.analytics;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Dchartswitch {
    private static Context dswitchContext;

    Analyticsresocs visto;
    Analyticslogic bisto;
    Class vClass;

    public Dchartswitch(Context context) {
        dswitchContext = context;

        visto = new Analyticsresocs(dswitchContext);
        bisto = new Analyticslogic(dswitchContext);
        vClass = bisto.getClass();

    }

    /* Line, bar and pie charts */
    public ArrayList<ArrayList> linebackrt(String typacht, String dimet, String dimeta, String fncpost, String cape) {
        ArrayList<ArrayList> Combisto = new ArrayList<>();
        ArrayList<String> STTL = new ArrayList<>();
        ArrayList<Float> tab = new ArrayList<>();
        ArrayList<String> mssg = new ArrayList<>();
        String conges = "cool";

        Long diffrnm = visto.gtDtadif(dimet, dimeta);

        if (typacht.equals("dtatypa")) {
            if (diffrnm < 2L) {
                conges = "low";
            } else if (diffrnm > 0L && diffrnm <= 7L) {
                ArrayList<ArrayList> combisto = visto.datesGap(dimet, dimeta);
                ArrayList<String> arDayz = combisto.get(0);
                ArrayList<Date> arDatz = combisto.get(1);
                for (Integer dix = 0; dix < arDayz.size(); ++dix) {
                    STTL.add(arDayz.get(dix));
                }
                for (Integer rend = 0; rend < arDatz.size(); ++rend) {
                    try {
                        Method vostics = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                        Float sss = (Float) vostics.invoke(bisto, arDatz.get(rend), arDatz.get(rend), cape);
                        tab.add(sss);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {

                    }
                }
            } else if (diffrnm > 7L && diffrnm <= 31L) {
                ArrayList<ArrayList> combisto = visto.datesGap(dimet, dimeta);
                ArrayList<String> arDayz = combisto.get(0);
                ArrayList<Date> arDatz = combisto.get(1);
                int chunk = 7;
                List<List<String>> arDayzmod = new LinkedList<>();
                for (int i = 0; i < arDayz.size(); i += chunk) {
                    arDayzmod.add(arDayz.subList(i, Math.min(i + chunk, arDayz.size())));
                }
                List<List<Date>> arDatzmod = new LinkedList<>();
                for (int j = 0; j < arDatz.size(); j += chunk) {
                    arDatzmod.add(arDatz.subList(j, Math.min(j + chunk, arDatz.size())));
                }
                for (int xuly = 0; xuly < arDayzmod.size(); ++xuly) {
                    String frxud = arDayzmod.get(xuly).get(0);
                    String frxuda = arDayzmod.get(xuly).get(arDayzmod.get(xuly).size() - 1);

                    if (cape.equals("brcht") || cape.equals("lnchart")) {
                        STTL.add(frxud + "\nto\n" + frxuda);

                    } else if (cape.equals("piezac") || cape.equals("tableu")) {
                        STTL.add(frxud + "\nto\n" + frxuda);

                    }
                }
                for (int rend2 = 0; rend2 < arDatzmod.size(); ++rend2) {
                    try {
                        Method vostics = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                        Float sss2 = (Float) vostics.invoke(bisto, arDatzmod.get(rend2).get(0), arDatzmod.get(rend2).get(arDatzmod.get(rend2).size() - 1), cape);
                        tab.add(sss2);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex6) {

                    }
                }
            } else if (diffrnm > 31L && diffrnm <= 366L) {
                ArrayList<String> armonthprd = visto.datesGapa(dimet, dimeta);
                Integer flecs = armonthprd.size();
                for (int xuly2 = 0; xuly2 < flecs; ++xuly2) {
                    STTL.add(visto.dateFomac(armonthprd.get(xuly2)));
                }
                Integer decisu = armonthprd.size() - 1;
                try {
                    Method vostics2 = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                    Float bosn = (Float) vostics2.invoke(bisto, visto.dateCrux(dimet), visto.gtLstdate(armonthprd.get(0)), cape);
                    tab.add(bosn);
                    for (int rend3 = 1; rend3 < decisu; ++rend3) {
                        Float bosna = (Float) vostics2.invoke(bisto, visto.gtFstdate(armonthprd.get(rend3)), visto.gtLstdate(armonthprd.get(rend3)), cape);
                        tab.add(bosna);
                    }
                    Float bosnb = (Float) vostics2.invoke(bisto, visto.gtFstdate(armonthprd.get(decisu)), visto.dateCrux(dimeta), cape);
                    tab.add(bosnb);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex11) {

                }
            } else if (diffrnm > 366L) {
                ArrayList<String> armonthprd = visto.datesYapa(dimet, dimeta);
                Integer preflecs = armonthprd.size();
                if (preflecs <= 12) {
                    Integer flecs = preflecs;
                    for (Integer xuly3 = 0; xuly3 < flecs; ++xuly3) {
                        STTL.add(armonthprd.get(xuly3));
                    }
                    Integer decisu2 = flecs - 1;
                    try {
                        Method vostics2 = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                        Float bosn2 = (Float) vostics2.invoke(bisto, visto.dateCrux(dimet), visto.creadzoomixa(armonthprd.get(0)), cape);
                        tab.add(bosn2);
                        for (Integer rend4 = 1; rend4 < decisu2; ++rend4) {
                            Float bosna2 = (Float) vostics2.invoke(bisto, visto.creadzoomix(armonthprd.get(rend4)), visto.creadzoomixa(armonthprd.get(rend4)), cape);
                            tab.add(bosna2);
                        }
                        Float bosnb2 = (Float) vostics2.invoke(bisto, visto.creadzoomix(armonthprd.get(decisu2)), visto.dateCrux(dimeta), cape);
                        tab.add(bosnb2);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex16) {
                    }
                } else {
                    conges = "high";
                }
            }
        } else if (typacht.equals("vartypa")) {
            if (diffrnm < 2L) {
                conges = "low";
            } else if (diffrnm > 0L) {
                try {
                    Method vostics3 = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                    ArrayList<ArrayList> combiArr = (ArrayList<ArrayList>) vostics3.invoke(bisto, visto.dateCrux(dimet), visto.dateCrux(dimeta), cape);
                    STTL = combiArr.get(0);
                    tab = combiArr.get(1);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex21) {
                }
            } else {
                conges = "low";
            }
        }
        mssg.add(conges);
        Combisto.add(STTL);
        Combisto.add(tab);
        Combisto.add(mssg);
        return Combisto;
    }

    /* Tewo bar and line charts */
    public ArrayList<ArrayList> iiLinbrcht(String typacht, String dimet, String dimeta, String fncpost, String cape) {
        ArrayList<ArrayList> Combisto = new ArrayList<>();
        ArrayList<String> STTL = new ArrayList<>();
        ArrayList<Float> tab = new ArrayList<>();
        ArrayList<Float> taba = new ArrayList<>();
        ArrayList<String> valies = new ArrayList<>();
        ArrayList<String> mssg = new ArrayList<>();
        String conges = "cool";

        Long diffrnm = visto.gtDtadif(dimet, dimeta);
        if (typacht.equals("dtatypa")) {
            if (diffrnm < 2L) {
                conges = "low";
            } else if (diffrnm > 0L && diffrnm <= 7L) {
                ArrayList<ArrayList> combisto = visto.datesGap(dimet, dimeta);
                ArrayList<String> arDayz = combisto.get(0);
                ArrayList<Date> arDatz = combisto.get(1);
                for (Integer dix = 0; dix < arDayz.size(); ++dix) {
                    STTL.add(arDayz.get(dix));
                }
                for (Integer rend = 0; rend < arDatz.size(); ++rend) {
                    try {
                        Method vostics = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                        ArrayList bosn = (ArrayList) vostics.invoke(bisto, arDatz.get(rend), arDatz.get(rend), cape);
                        tab.add((Float) bosn.get(0));
                        taba.add((Float) bosn.get(1));
                        valies.add((String) bosn.get(2));
                        valies.add((String) bosn.get(3));
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    }
                }
            } else if (diffrnm > 7L && diffrnm <= 31L) {
                ArrayList<ArrayList> combisto = visto.datesGap(dimet, dimeta);
                ArrayList<String> arDayz = combisto.get(0);
                ArrayList<Date> arDatz = combisto.get(1);
                int chunk = 7;
                List<List<String>> arDayzmod = new LinkedList<>();
                for (int i = 0; i < arDayz.size(); i += chunk) {
                    arDayzmod.add(arDayz.subList(i, Math.min(i + chunk, arDayz.size())));
                }
                List<List<Date>> arDatzmod = new LinkedList<>();
                for (int j = 0; j < arDatz.size(); j += chunk) {
                    arDatzmod.add(arDatz.subList(j, Math.min(j + chunk, arDatz.size())));
                }
                for (int xuly = 0; xuly < arDayzmod.size(); ++xuly) {
                    String frxud = arDayzmod.get(xuly).get(0);
                    String frxuda = arDayzmod.get(xuly).get(arDayzmod.get(xuly).size() - 1);
                    if (cape.equals("tableu")) {
                        STTL.add(frxud + "\nto\n" + frxuda);
                    } else {
                        STTL.add(frxud + "\nto\n" + frxuda);
                    }
                }
                for (int rend2 = 0; rend2 < arDatzmod.size(); ++rend2) {
                    try {
                        Method vostics = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                        ArrayList bosn2 = (ArrayList) vostics.invoke(bisto, arDatzmod.get(rend2).get(0), arDatzmod.get(rend2).get(arDatzmod.get(rend2).size() - 1), cape);
                        tab.add((Float) bosn2.get(0));
                        taba.add((Float) bosn2.get(1));
                        valies.add((String) bosn2.get(2));
                        valies.add((String) bosn2.get(3));
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex6) {
                    }
                }
            } else if (diffrnm > 31L && diffrnm <= 366L) {
                ArrayList<String> armonthprd = visto.datesGapa(dimet, dimeta);
                Integer flecs = armonthprd.size();
                for (int xuly2 = 0; xuly2 < flecs; ++xuly2) {
                    STTL.add(visto.dateFomac(armonthprd.get(xuly2)));
                }
                Integer decisu = armonthprd.size() - 1;
                try {
                    Method vostics2 = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                    ArrayList bosn3 = (ArrayList) vostics2.invoke(bisto, visto.dateCrux(dimet), visto.gtLstdate(armonthprd.get(0)), cape);
                    tab.add((Float) bosn3.get(0));
                    taba.add((Float) bosn3.get(1));
                    for (int rend3 = 1; rend3 < decisu; ++rend3) {
                        ArrayList bosna = (ArrayList) vostics2.invoke(bisto, visto.gtFstdate(armonthprd.get(rend3)), visto.gtLstdate(armonthprd.get(rend3)), cape);
                        tab.add((Float) bosna.get(0));
                        taba.add((Float) bosna.get(1));
                    }
                    ArrayList bosnb = (ArrayList) vostics2.invoke(bisto, visto.gtFstdate(armonthprd.get(decisu)), visto.dateCrux(dimeta), cape);
                    tab.add((Float) bosnb.get(0));
                    taba.add((Float) bosnb.get(1));
                    valies.add((String) bosnb.get(2));
                    valies.add((String) bosnb.get(3));
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex11) {
                }
            } else if (diffrnm > 366L) {
                ArrayList<String> armonthprd = visto.datesYapa(dimet, dimeta);
                Integer preflecs = armonthprd.size();
                if (preflecs <= 12) {
                    Integer flecs = preflecs;
                    for (Integer xuly3 = 0; xuly3 < flecs; ++xuly3) {
                        STTL.add(armonthprd.get(xuly3));
                    }
                    Integer decisu2 = flecs - 1;
                    try {
                        Method vostics2 = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                        ArrayList bosn = (ArrayList) vostics2.invoke(bisto, visto.dateCrux(dimet), visto.creadzoomixa(armonthprd.get(0)), cape);
                        tab.add((Float) bosn.get(0));
                        taba.add((Float) bosn.get(1));
                        for (Integer rend4 = 1; rend4 < decisu2; ++rend4) {
                            ArrayList bosna2 = (ArrayList) vostics2.invoke(bisto, visto.creadzoomix(armonthprd.get(rend4)), visto.creadzoomixa(armonthprd.get(rend4)), cape);
                            tab.add((Float) bosna2.get(0));
                            taba.add((Float) bosna2.get(1));
                        }
                        ArrayList bosnb2 = (ArrayList) vostics2.invoke(bisto, visto.creadzoomix(armonthprd.get(decisu2)), visto.dateCrux(dimeta), cape);
                        tab.add((Float) bosnb2.get(0));
                        taba.add((Float) bosnb2.get(1));
                        valies.add((String) bosnb2.get(2));
                        valies.add((String) bosnb2.get(3));
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex16) {
                    }
                } else {
                    conges = "high";
                }
            }
        } else if (typacht.equals("vartypa")) {
            if (diffrnm < 2L) {
                conges = "low";
            } else if (diffrnm > 0L) {
                try {
                    Method vostics3 = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                    ArrayList<ArrayList> combiArr = (ArrayList<ArrayList>) vostics3.invoke(bisto, visto.dateCrux(dimet), visto.dateCrux(dimeta), cape);
                    STTL = combiArr.get(0);
                    tab = combiArr.get(1);
                    taba = combiArr.get(2);
                    valies = combiArr.get(3);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex21) {
                }
            } else {
                conges = "low";
            }
        }
        mssg.add(conges);

        Combisto.add(STTL);
        Combisto.add(tab);
        Combisto.add(taba);
        Combisto.add(valies);
        Combisto.add(mssg);
        return Combisto;
    }

    /* Stack chart */
    public ArrayList<ArrayList> stkchycht(String typacht, String dimet, String dimeta, String fncpost, String cape) {
        ArrayList<ArrayList> Combisto = new ArrayList<>();
        ArrayList<String> STTL = new ArrayList<>();
        ArrayList<ArrayList<Float>> tab = new ArrayList<>();
        ArrayList<String> rptypa = new ArrayList<>();
        ArrayList<String> mssg = new ArrayList<>();
        String conges = "cool";
        Long diffrnm = visto.gtDtadif(dimet, dimeta);
        if (typacht.equals("dtatypa")) {
            if (diffrnm < 2L) {
                conges = "low";
            } else if (diffrnm > 0L && diffrnm <= 7L) {
                ArrayList<ArrayList> combisto = visto.datesGap(dimet, dimeta);
                ArrayList<String> arDayz = combisto.get(0);
                ArrayList<Date> arDatz = combisto.get(1);
                for (Integer dix = 0; dix < arDayz.size(); ++dix) {
                    STTL.add(arDayz.get(dix));
                }
                for (Integer rend = 0; rend < arDatz.size(); ++rend) {
                    try {
                        Method vostics = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                        ArrayList<ArrayList> bosn = (ArrayList<ArrayList>) vostics.invoke(bisto, arDatz.get(rend), arDatz.get(rend), cape);
                        tab.add(bosn.get(0));
                        rptypa = bosn.get(1);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    }
                }
            } else if (diffrnm > 7L && diffrnm <= 31L) {
                ArrayList<ArrayList> combisto = visto.datesGap(dimet, dimeta);
                ArrayList<String> arDayz = combisto.get(0);
                ArrayList<Date> arDatz = combisto.get(1);
                int chunk = 7;
                List<List<String>> arDayzmod = new LinkedList<>();
                for (int i = 0; i < arDayz.size(); i += chunk) {
                    arDayzmod.add(arDayz.subList(i, Math.min(i + chunk, arDayz.size())));
                }
                List<List<Date>> arDatzmod = new LinkedList<>();
                for (int j = 0; j < arDatz.size(); j += chunk) {
                    arDatzmod.add(arDatz.subList(j, Math.min(j + chunk, arDatz.size())));
                }
                for (int xuly = 0; xuly < arDayzmod.size(); ++xuly) {
                    String frxud = arDayzmod.get(xuly).get(0);
                    String frxuda = arDayzmod.get(xuly).get(arDayzmod.get(xuly).size() - 1);
                    if (cape.equals("tableu")) {
                        STTL.add(frxud + "\nto\n" + frxuda);
                    } else {
                        STTL.add(frxud + "\nto\n" + frxuda);
                    }
                }
                for (int rend2 = 0; rend2 < arDatzmod.size(); ++rend2) {
                    try {
                        Method vostics = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                        ArrayList<ArrayList> bosn2 = (ArrayList<ArrayList>) vostics.invoke(bisto, arDatzmod.get(rend2).get(0), arDatzmod.get(rend2).get(arDatzmod.get(rend2).size() - 1), cape);
                        tab.add(bosn2.get(0));
                        rptypa = bosn2.get(1);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex6) {
                    }
                }
            } else if (diffrnm > 31L && diffrnm <= 366L) {
                ArrayList<String> armonthprd = visto.datesGapa(dimet, dimeta);
                Integer flecs = armonthprd.size();
                for (int xuly2 = 0; xuly2 < flecs; ++xuly2) {
                    STTL.add(visto.dateFomac(armonthprd.get(xuly2)));
                }
                Integer decisu = armonthprd.size() - 1;
                try {
                    Method vostics2 = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                    ArrayList<ArrayList> bosn3 = (ArrayList<ArrayList>) vostics2.invoke(bisto, visto.dateCrux(dimet), visto.gtLstdate(armonthprd.get(0)), cape);
                    tab.add(bosn3.get(0));
                    for (int rend3 = 1; rend3 < decisu; ++rend3) {
                        ArrayList<ArrayList> bosna = (ArrayList<ArrayList>) vostics2.invoke(bisto, visto.gtFstdate(armonthprd.get(rend3)), visto.gtLstdate(armonthprd.get(rend3)), cape);
                        tab.add(bosna.get(0));
                    }
                    ArrayList<ArrayList> bosnb = (ArrayList<ArrayList>) vostics2.invoke(bisto, visto.gtFstdate(armonthprd.get(decisu)), visto.dateCrux(dimeta), cape);
                    tab.add(bosnb.get(0));
                    rptypa = bosnb.get(1);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex11) {
                }
            } else if (diffrnm > 366L) {
                ArrayList<String> armonthprd = visto.datesYapa(dimet, dimeta);
                Integer preflecs = armonthprd.size();
                if (preflecs <= 12) {
                    Integer flecs = preflecs;
                    for (Integer xuly3 = 0; xuly3 < flecs; ++xuly3) {
                        STTL.add(armonthprd.get(xuly3));
                    }
                    Integer decisu2 = flecs - 1;
                    try {
                        Method vostics2 = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                        ArrayList<ArrayList> bosn = (ArrayList<ArrayList>) vostics2.invoke(bisto, visto.dateCrux(dimet), visto.creadzoomixa(armonthprd.get(0)), cape);
                        tab.add(bosn.get(0));
                        for (Integer rend4 = 1; rend4 < decisu2; ++rend4) {
                            ArrayList<ArrayList> bosna2 = (ArrayList<ArrayList>) vostics2.invoke(bisto, visto.creadzoomix(armonthprd.get(rend4)), visto.creadzoomixa(armonthprd.get(rend4)), cape);
                            tab.add(bosna2.get(0));
                        }
                        ArrayList<ArrayList> bosnb2 = (ArrayList<ArrayList>) vostics2.invoke(bisto, visto.creadzoomix(armonthprd.get(decisu2)), visto.dateCrux(dimeta), cape);
                        tab.add(bosnb2.get(0));
                        rptypa = bosnb2.get(1);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex16) {
                    }
                } else {
                    conges = "high";
                }
            }
        } else if (typacht.equals("vartypa")) {
            if (diffrnm < 2L) {
                conges = "low";
            } else if (diffrnm > 0L) {
                try {
                    Method vostics3 = vClass.getMethod(fncpost, Date.class, Date.class, String.class);
                    ArrayList<ArrayList> combiArr = (ArrayList<ArrayList>) vostics3.invoke(bisto, visto.dateCrux(dimet), visto.dateCrux(dimeta), cape);
                    STTL = combiArr.get(0);
                    rptypa = combiArr.get(1);
                    for (int ne = 0; ne < STTL.size(); ++ne) {
                        ArrayList<Float> vex = new ArrayList<>();
                        tab.add(vex);
                        for (int nea = 0; nea < rptypa.size(); ++nea) {
                            tab.get(ne).add((Float) combiArr.get(nea + 2).get(ne));
                        }
                    }
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex21) {
                }
            } else {
                conges = "low";
            }
        }
        mssg.add(conges);

        Combisto.add(STTL);
        Combisto.add(tab);
        Combisto.add(rptypa);
        Combisto.add(mssg);
        return Combisto;
    }

    /* Three bar and line charts */
    public ArrayList<ArrayList> iiiLinbrcht(String typacht, String dimet, String dimeta, String fncpost, String cape) {
        ArrayList<ArrayList> Combisto = new ArrayList<>();
        ArrayList<String> STTL = new ArrayList<>();
        ArrayList<Float> tab = new ArrayList<>();
        ArrayList<Float> taba = new ArrayList<>();
        ArrayList<Float> tabb = new ArrayList<>();
        ArrayList<String> valies = new ArrayList<>();
        ArrayList<String> mssg = new ArrayList<>();
        String conges = "cool";

        Long diffrnm = visto.gtDtadif(dimet, dimeta);
        if (typacht.equals("dtatypa")) {
            if (diffrnm < 2L) {
                conges = "low";
            } else if (diffrnm > 0L && diffrnm <= 7L) {
                ArrayList<ArrayList> combisto = visto.datesGap(dimet, dimeta);
                ArrayList<String> arDayz = combisto.get(0);
                ArrayList<Date> arDatz = combisto.get(1);
                for (Integer dix = 0; dix < arDayz.size(); ++dix) {
                    STTL.add(arDayz.get(dix));
                }
                for (Integer rend = 0; rend < arDatz.size(); ++rend) {
                    try {
                        Method vostics = vClass.getMethod(fncpost, Date.class, Date.class, Long.class, String.class);
                        ArrayList bosn = (ArrayList) vostics.invoke(bisto, arDatz.get(rend), arDatz.get(rend), diffrnm, cape);
                        tab.add((Float) bosn.get(0));
                        taba.add((Float) bosn.get(1));
                        tabb.add((Float) bosn.get(2));
                        valies.add((String) bosn.get(3));
                        valies.add((String) bosn.get(4));
                        valies.add((String) bosn.get(5));
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    }
                }
            } else if (diffrnm > 7L && diffrnm <= 31L) {
                ArrayList<ArrayList> combisto = visto.datesGap(dimet, dimeta);
                ArrayList<String> arDayz = combisto.get(0);
                ArrayList<Date> arDatz = combisto.get(1);
                int chunk = 7;
                List<List<String>> arDayzmod = new LinkedList<>();
                for (int i = 0; i < arDayz.size(); i += chunk) {
                    arDayzmod.add(arDayz.subList(i, Math.min(i + chunk, arDayz.size())));
                }
                List<List<Date>> arDatzmod = new LinkedList<>();
                for (int j = 0; j < arDatz.size(); j += chunk) {
                    arDatzmod.add(arDatz.subList(j, Math.min(j + chunk, arDatz.size())));
                }
                for (int xuly = 0; xuly < arDayzmod.size(); ++xuly) {
                    String frxud = arDayzmod.get(xuly).get(0);
                    String frxuda = arDayzmod.get(xuly).get(arDayzmod.get(xuly).size() - 1);
                    if (cape.equals("tableu")) {
                        STTL.add(frxud + "\nto\n" + frxuda);
                    } else {
                        STTL.add(frxud + "\nto\n" + frxuda);
                    }
                }
                for (int rend2 = 0; rend2 < arDatzmod.size(); ++rend2) {
                    try {
                        Method vostics = vClass.getMethod(fncpost, Date.class, Date.class, Long.class, String.class);
                        ArrayList bosn2 = (ArrayList) vostics.invoke(bisto, arDatzmod.get(rend2).get(0),
                                arDatzmod.get(rend2).get(arDatzmod.get(rend2).size() - 1), diffrnm, cape);
                        tab.add((Float) bosn2.get(0));
                        taba.add((Float) bosn2.get(1));
                        tabb.add((Float) bosn2.get(2));
                        valies.add((String) bosn2.get(3));
                        valies.add((String) bosn2.get(4));
                        valies.add((String) bosn2.get(5));
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex6) {
                    }
                }
            } else if (diffrnm > 31L && diffrnm <= 366L) {
                ArrayList<String> armonthprd = visto.datesGapa(dimet, dimeta);
                Integer flecs = armonthprd.size();
                for (int xuly2 = 0; xuly2 < flecs; ++xuly2) {
                    STTL.add(visto.dateFomac(armonthprd.get(xuly2)));
                }
                Integer decisu = armonthprd.size() - 1;
                try {
                    Method vostics2 = vClass.getMethod(fncpost, Date.class, Date.class, Long.class, String.class);
                    ArrayList bosn3 = (ArrayList) vostics2.invoke(bisto, visto.dateCrux(dimet),
                            visto.gtLstdate(armonthprd.get(0)), diffrnm, cape);
                    tab.add((Float) bosn3.get(0));
                    taba.add((Float) bosn3.get(1));
                    tabb.add((Float) bosn3.get(2));
                    for (int rend3 = 1; rend3 < decisu; ++rend3) {
                        ArrayList bosna = (ArrayList) vostics2.invoke(bisto, visto.gtFstdate(armonthprd.get(rend3)),
                                visto.gtLstdate(armonthprd.get(rend3)), diffrnm, cape);
                        tab.add((Float) bosna.get(0));
                        taba.add((Float) bosna.get(1));
                        tabb.add((Float) bosna.get(2));
                    }
                    ArrayList bosnb = (ArrayList) vostics2.invoke(bisto, visto.gtFstdate(armonthprd.get(decisu)),
                            visto.dateCrux(dimeta), diffrnm, cape);
                    tab.add((Float) bosnb.get(0));
                    taba.add((Float) bosnb.get(1));
                    tabb.add((Float) bosnb.get(2));
                    valies.add((String) bosnb.get(3));
                    valies.add((String) bosnb.get(4));
                    valies.add((String) bosnb.get(5));
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex11) {
                }
            } else if (diffrnm > 366L) {
                ArrayList<String> armonthprd = visto.datesYapa(dimet, dimeta);
                Integer preflecs = armonthprd.size();
                if (preflecs <= 12) {
                    Integer flecs = preflecs;
                    for (Integer xuly3 = 0; xuly3 < flecs; ++xuly3) {
                        STTL.add(armonthprd.get(xuly3));
                    }
                    Integer decisu2 = flecs - 1;
                    try {
                        Method vostics2 = vClass.getMethod(fncpost, Date.class, Date.class, Long.class, String.class);
                        ArrayList bosn = (ArrayList) vostics2.invoke(bisto, visto.dateCrux(dimet),
                                visto.creadzoomixa(armonthprd.get(0)), diffrnm, cape);
                        tab.add((Float) bosn.get(0));
                        taba.add((Float) bosn.get(1));
                        tabb.add((Float) bosn.get(2));
                        for (Integer rend4 = 1; rend4 < decisu2; ++rend4) {
                            ArrayList bosna2 = (ArrayList) vostics2.invoke(bisto, visto.creadzoomix(armonthprd.get(rend4)),
                                    visto.creadzoomixa(armonthprd.get(rend4)), diffrnm, cape);
                            tab.add((Float) bosna2.get(0));
                            taba.add((Float) bosna2.get(1));
                            tabb.add((Float) bosna2.get(2));
                        }
                        ArrayList bosnb2 = (ArrayList) vostics2.invoke(bisto, visto.creadzoomix(armonthprd.get(decisu2)),
                                visto.dateCrux(dimeta), diffrnm, cape);
                        tab.add((Float) bosnb2.get(0));
                        taba.add((Float) bosnb2.get(1));
                        tabb.add((Float) bosnb2.get(2));
                        valies.add((String) bosnb2.get(3));
                        valies.add((String) bosnb2.get(4));
                        valies.add((String) bosnb2.get(5));
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex16) {
                    }
                } else {
                    conges = "high";
                }
            }
        } else if (typacht.equals("vartypa")) {
            if (diffrnm < 2L) {
                conges = "low";
            } else if (diffrnm > 0L) {
                try {
                    Method vostics3 = vClass.getMethod(fncpost, Date.class, Date.class, Long.class, String.class);
                    ArrayList<ArrayList> combiArr = (ArrayList<ArrayList>) vostics3.invoke(bisto,
                            visto.dateCrux(dimet), visto.dateCrux(dimeta), diffrnm, cape);
                    STTL = combiArr.get(0);
                    tab = combiArr.get(1);
                    taba = combiArr.get(2);
                    tabb = combiArr.get(3);
                    valies = combiArr.get(4);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex21) {
                }
            } else {
                conges = "low";
            }
        }
        mssg.add(conges);

        Combisto.add(STTL);
        Combisto.add(tab);
        Combisto.add(taba);
        Combisto.add(tabb);
        Combisto.add(valies);
        Combisto.add(mssg);
        return Combisto;
    }

}

