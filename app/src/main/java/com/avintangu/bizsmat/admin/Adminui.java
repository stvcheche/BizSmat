package com.avintangu.bizsmat.admin;

import android.content.Context;
import android.graphics.Color;
import android.net.ParseException;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.avintangu.bizsmat.R;
import com.avintangu.bizsmat.dbcluzz.Dbcluzz;
import com.avintangu.bizsmat.resources.Resocs;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Adminui {
    private static Context adminContext;
    Resocs resources;
    Adminresocs adminresocs;
    Dbcluzz dbcluzz;

    public Adminui(Context context) {
        adminContext = context;

        resources = new Resocs(adminContext);
        adminresocs = new Adminresocs(adminContext);
        dbcluzz = new Dbcluzz(adminContext);

    }

    /* Set title */
    public String setadmUitrelis(String tredetocs) {
        String title = "";

        switch (tredetocs) {

            case "adminSrecod": {
                title = adminContext.getResources().getString(R.string.admin_str);

                break;
            }

            case "adminSrecoda": {
                title = adminContext.getResources().getString(R.string.admin_stra);

                break;
            }

            case "adminSrecodb":
            case "adminSrecodc":
            case "adminSrecodd":
            case "adminSrecode":
            case "adminSrecodf":
            case "adminSrecodg":
            case "adminSrecodh":
            case "adminSrecodi":
            case "adminSrecodga": {
                title = adminContext.getResources().getString(R.string.admin_strj);

                break;
            }


            default:
                break;
        }

        return title;
    }

    /* Get summary */
    public void admbackrecdlod(ScrollView scroLayout, double eunica, Integer colnum, String mims, ProgressBar progbar) {
        String param = "nada";
        progbar.setIndeterminate(true);
        scroLayout.scrollTo(0, 0);

        new Adminui.admRecodsync(scroLayout, eunica, colnum, mims, progbar).execute(param);
    }

    /* Properties backrecd UI */
    public class admRecodsync extends AsyncTask<String, Integer, String> {
        ScrollView scroLayoutpr;
        double eunicapr;
        Integer colnumpr;
        String mimspr;
        ProgressBar progbarpr;
        LinearLayout usrLinear;
        FlexboxLayout usrFlexbox;

        public admRecodsync(ScrollView scroLayout, double eunica, Integer colnum, String mims, ProgressBar progbar) {
            this.scroLayoutpr = scroLayout;
            this.eunicapr = eunica;
            this.colnumpr = colnum;
            this.mimspr = mims;
            this.progbarpr = progbar;

            LayoutInflater inflater = (LayoutInflater) adminContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (mimspr.equals("adminSrecod")) {
                View Linview = inflater.inflate(R.layout.admintools, null);
                usrLinear = Linview.findViewById(R.id.admintolparent);

            } else if (mimspr.equals("adminSrecoda")) {
                View Linview = inflater.inflate(R.layout.adminrecyle, null);
                usrLinear = Linview.findViewById(R.id.admrecparent);

            } else if (mimspr.equals("adminSrecodb")) {
                View Linview = inflater.inflate(R.layout.usrmanintro, null);
                usrLinear = Linview.findViewById(R.id.usrmanintroparent);

            } else if (mimspr.equals("adminSrecodc")) {
                View Linview = inflater.inflate(R.layout.usrmanassign, null);
                usrLinear = Linview.findViewById(R.id.usrmanassparent);

            } else if (mimspr.equals("adminSrecodd")) {
                View Linview = inflater.inflate(R.layout.usrmanprdct, null);
                usrLinear = Linview.findViewById(R.id.usrmanprdctparent);

            } else if (mimspr.equals("adminSrecode")) {
                View Linview = inflater.inflate(R.layout.usrmansales, null);
                usrLinear = Linview.findViewById(R.id.usrmansaleparent);

            } else if (mimspr.equals("adminSrecodf")) {
                View Linview = inflater.inflate(R.layout.usrmanstks, null);
                usrLinear = Linview.findViewById(R.id.usrmanstksparent);

            } else if (mimspr.equals("adminSrecodg")) {
                View Linview = inflater.inflate(R.layout.usrmandebt, null);
                usrLinear = Linview.findViewById(R.id.usrmanadebtparent);

            } else if (mimspr.equals("adminSrecodh")) {
                View Linview = inflater.inflate(R.layout.usrmanlitics, null);
                usrLinear = Linview.findViewById(R.id.usrmanliticparent);

            } else if (mimspr.equals("adminSrecodi")) {
                View Linview = inflater.inflate(R.layout.usrmanadmin, null);
                usrLinear = Linview.findViewById(R.id.usrmanadminparent);

            } else if (mimspr.equals("adminSrecodga")) {
                View Linview = inflater.inflate(R.layout.usrmanordes, null);
                usrLinear = Linview.findViewById(R.id.usrmanordparent);

            }

        }

        protected String doInBackground(String... param) {
            String response = "nada";

            ArrayList<Integer> bgtxvewidec = null;
            ArrayList<Integer> smtxvewidec = null;

            switch (mimspr) {

                case "adminSrecod": {
                    /* Set up font sizes */
                    ArrayList<Integer> admntxvewidec = new ArrayList<>(Arrays.asList(R.id.admtoltxt, R.id.admtoltxta, R.id.admtoltxtb,
                            R.id.admtoltxtc, R.id.admtoltxtc, R.id.admtoltxtd, R.id.admtoltxte, R.id.admtoltxtdins));

                    /*Set textviews font sizes*/
                    TextView[] Txvewarry = new TextView[admntxvewidec.size()];
                    for (int k = 0; k < admntxvewidec.size(); ++k) {
                        (Txvewarry[k] = usrLinear.findViewById(admntxvewidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                    }

                    ArrayList<Integer> admnbtnidec = new ArrayList<>(Arrays.asList(R.id.admtolbtn, R.id.admtolbtnb,
                            R.id.admtolbtnc));

                    /*Set textviews font sizes*/
                    Button[] Btnarry = new Button[admnbtnidec.size()];
                    for (int k = 0; k < admnbtnidec.size(); ++k) {
                        (Btnarry[k] = usrLinear.findViewById(admnbtnidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                    }

                    /* User values */
                    ArrayList<String> usrvals = dbcluzz.getUsrdetal();

                    /*  Set values */
                    ((TextView) usrLinear.findViewById(R.id.admtoltxta)).setText(usrvals.get(0));
                    ((TextView) usrLinear.findViewById(R.id.admtoltxtc)).setText(usrvals.get(1));

                    LinearLayout patchedres = usrLinear.findViewById(R.id.admtolinesca);
                    for (int ghas = 0; ghas < resources.getAllshopsa().size(); ghas++) {
                        TextView header = new TextView(adminContext);
                        header.setGravity(Gravity.LEFT);
                        header.setTextSize((float) eunicapr + 4.5f);
                        header.setBackgroundResource(R.drawable.listflexcontb);
                        header.setText(resources.getAllshopsa().get(ghas));
                        header.setTextColor(Color.rgb(77, 77, 77));
                        header.setPadding(15, 20, 5, 5);

                        FlexboxLayout.LayoutParams hedlayoutParams = new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.MATCH_PARENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);

                        hedlayoutParams.setFlexBasisPercent(1.0f);
                        hedlayoutParams.setFlexGrow(1.0f);
                        patchedres.addView(header, hedlayoutParams);
                    }

                    if (usrvals.get(5).equals("PAP")) {
                        ((TextView) usrLinear.findViewById(R.id.admtoltxte)).setText(adminContext.getResources().getString(R.string.admin_strn));
                        usrLinear.findViewById(R.id.admtolbtnc).setVisibility(View.GONE);

                    } else {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        long mod = 0L;
                        String switocs = "";

                        try {
                            mod = (simpleDateFormat.parse(usrvals.get(5)).getTime() - date.getTime()) / (24 * 60 * 60 * 1000);

                        } catch (ParseException | java.text.ParseException ex) {

                        }

                        if (mod > 0) {
                            switocs = "Trial version: " + mod + " days remaining";

                        } else {
                            switocs = "Trial version: 0 days remaining";

                        }

                        ((TextView) usrLinear.findViewById(R.id.admtoltxte)).setText(switocs);

                    }

                    /* Back-up data*/
                    usrLinear.findViewById(R.id.admtolbtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adminresocs.bckData("bckdata");

                        }
                    });

                    /* Update app
                    usrLinear.findViewById(R.id.admtolbtna).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adminresocs.bckData("upgradeapp");

                        }
                    });*/

                    /* Refresh user data*/
                    usrLinear.findViewById(R.id.admtolbtnb).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adminresocs.refreshData(dbcluzz.getUsrdetal());

                        }
                    });

                    /* Activate app*/
                    usrLinear.findViewById(R.id.admtolbtnc).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adminresocs.activateApp(dbcluzz.getUsrdetal(), "adminActivate");

                        }
                    });

                    break;
                }

                case "adminSrecoda": {
                    ArrayList<String> mims = new ArrayList<>(Arrays.asList("prdSrecod", "prdSrecoda", "salSrecod",
                            "inventSrecoda", "debtSrecod", "debtSrecoda", "debtSrecodb", "ordeSrecoda"));

                    ArrayList<String> reptheader = new ArrayList<>(Arrays.asList("Products", "Product categories", "Sales",
                            "Inventory", "Customers", "Debt payments", "Debts", "ordesdetal"));

                    String dbess = "";
                    String stexa = "";
                    String state = "Inactive";
                    Integer columnSize = 0;

                    ArrayList<String> Sres;
                    ArrayList<String> Titles = new ArrayList<>();
                    ArrayList<Float> Widoth = new ArrayList<>();
                    ArrayList<Integer> Gravety = new ArrayList<>();

                    Integer adcount = 0;
                    for (int hug = 0; hug < mims.size(); hug++) {
                        ArrayList<ArrayList<String>> Reptvals = new ArrayList<>();

                        switch (mims.get(hug)) {

                            case "prdSrecod": {
                                dbess = "prdctdetaz";
                                stexa = "prdctdetazstate";
                                columnSize = 3;

                                Sres = new ArrayList<>(Arrays.asList("prdctdetazname", "prdctdetazprcc", "prdctdcatego",
                                        "prdctdetazdate", "prdctdetazcode"));
                                Titles = new ArrayList<>(Arrays.asList("Name", "Price", "Category",
                                        "Date created"));
                                Widoth = new ArrayList<>(Arrays.asList(0.30f, 0.15f, 0.25f, 0.30f));
                                Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER,
                                        Gravity.LEFT, Gravity.LEFT));

                                /*  Get data */
                                Reptvals = new com.avintangu.bizsmat.products.Prdctresocs(adminContext).ghAllprdcts(dbess, stexa,
                                        state, Sres, "", mims.get(hug));

                                break;
                            }

                            case "prdSrecoda": {
                                dbess = "prdctdcateri";
                                stexa = "prdctdcateristate";
                                columnSize = 2;

                                Sres = new ArrayList<>(Arrays.asList("prdctdcatego", "prdctdcaterid", "prdctdcateriuniq"));

                                Titles = new ArrayList<>(Arrays.asList("Category", "ID"));
                                Widoth = new ArrayList<>(Arrays.asList(0.6f, 0.4f));
                                Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER));

                                /*  Get data */
                                Reptvals = new com.avintangu.bizsmat.products.Prdctresocs(adminContext).ghAllprdcts(dbess, stexa,
                                        state, Sres, "", mims.get(hug));

                                break;
                            }

                            case "salSrecod": {
                                dbess = "salesrecd";
                                stexa = "salesrecdstate";
                                columnSize = 4;

                                Sres = new ArrayList<>(Arrays.asList("prdctdetazname", "salesrecdprcc", "salesrecdqnty", "salesrecddate",
                                        "salesrecdtotal", "salesrecdrecod"));
                                Titles = new ArrayList<>(Arrays.asList("Product", "Price", "Quantity", "Date"));
                                Widoth = new ArrayList<>(Arrays.asList(0.3f, 0.2f, 0.2f, 0.3f, 0.2f));
                                Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.LEFT, Gravity.CENTER,
                                        Gravity.CENTER, Gravity.CENTER));

                                Reptvals = new com.avintangu.bizsmat.sales.Salesresocs(adminContext).ghAllsales(dbess, stexa,
                                        state, Sres, "", mims.get(hug), "1100-11-11", "5100-11-11");


                                break;
                            }

                            case "inventSrecoda": {
                                dbess = "inventbl";
                                stexa = "inventblstate";
                                columnSize = 3;

                                Sres = new ArrayList<>(Arrays.asList("prdctdetazname", "inventbldate", "inventblqnty", "inventblbprc",
                                        "inventblrecod"));
                                Titles = new ArrayList<>(Arrays.asList("Product", "Date", "Quantity", "Buying price"));
                                Widoth = new ArrayList<>(Arrays.asList(0.3f, 0.3f, 0.2f, 0.2f, 0.3f));
                                Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.LEFT, Gravity.CENTER, Gravity.CENTER));

                                Reptvals = new com.avintangu.bizsmat.inventory.Inventresoc(adminContext).ghAllinvent(dbess, stexa,
                                        state, Sres, "", "1100-11-11", "5100-11-11");

                                break;
                            }

                            case "debtSrecod": {
                                columnSize = 3;

                                Sres = new ArrayList<>(Arrays.asList("custostblname", "custostblphone", "datesumes", "custostblrecod"));
                                Titles = new ArrayList<>(Arrays.asList("Customer", "Phone No.", "Debt"));
                                Widoth = new ArrayList<>(Arrays.asList(0.4f, 0.3f, 0.3f));
                                Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.CENTER, Gravity.CENTER));

                                Reptvals = new com.avintangu.bizsmat.debts.Debtsresoc(adminContext).gtDatsummary(Sres, "", state);

                                break;
                            }

                            case "debtSrecoda": {
                                dbess = "custodatpay";
                                stexa = "custodatpaystate";
                                columnSize = 3;

                                Sres = new ArrayList<>(Arrays.asList("custostblname", "custodatpaydate", "custodatpayamt", "custodatpayrecod"));
                                Titles = new ArrayList<>(Arrays.asList("Customer", "Date", "Amount paid"));
                                Widoth = new ArrayList<>(Arrays.asList(0.4f, 0.3f, 0.3f, 0.3f));
                                Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.LEFT, Gravity.CENTER));

                                Reptvals = new com.avintangu.bizsmat.debts.Debtsresoc(adminContext).gtAllpymnts(dbess, stexa, state,
                                        Sres, "", "1100-11-11", "5100-11-11");

                                break;
                            }

                            case "debtSrecodb": {
                                dbess = "custodates";
                                stexa = "custodatestate";
                                columnSize = 4;
                                String uniqidec = "custodatecuscod";

                                Titles = new ArrayList<>(Arrays.asList("Customer", "Date", "Product", "Quantity", "Total"));
                                Widoth = new ArrayList<>(Arrays.asList(0.3f, 0.3f, 0.2f, 0.2f, 0.2f));
                                Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.LEFT, Gravity.CENTER,
                                        Gravity.CENTER, Gravity.CENTER));


                                Sres = new ArrayList<>(Arrays.asList("custostblname", "custodatdate", "prdctdetazname",
                                        "custodatesqnty", "custodatesval", "debttotal", "custodatesrecod"));

                                Reptvals = new com.avintangu.bizsmat.debts.Debtsresoc(adminContext).gtDatcustos(dbess,
                                        Sres, stexa, uniqidec, "nada");

                                break;
                            }

                            case "ordeSrecoda": {
                                dbess = "ordesdetal";
                                stexa = "ordetalstate";
                                columnSize = 4;
                                String uniqidec = "ordetaluniq";
                                String Kstate = resources.Implodesa(new ArrayList(Arrays.asList("Cancelled")));

                                Titles = new ArrayList<>(Arrays.asList("Date", "Items", "Distributor", "Status"));
                                Widoth = new ArrayList<>(Arrays.asList(0.3f, 0.15f, 0.35f, 0.2f));
                                Gravety = new ArrayList<>(Arrays.asList(Gravity.LEFT, Gravity.LEFT, Gravity.LEFT, Gravity.LEFT));


                                Sres = new ArrayList<>(Arrays.asList("ordetaldta", "prdtotal", "distroscname", "ordetalrecipt",
                                        "ordetaluniq"));

                                Reptvals = new com.avintangu.bizsmat.orders.Ordersresoc(adminContext).gtArecods(dbess, stexa, Kstate, Sres,
                                        "", mims.get(hug), "1100-11-11", "5100-11-11",
                                        "nada");

                                break;
                            }

                            default:
                                break;

                        }


                        if (Reptvals.get(0).size() > 0) {
                            adcount += 1;
                            adnRecadata(reptheader.get(hug), mims.get(hug), usrLinear, eunicapr, Titles, columnSize,
                                    Widoth, Gravety, Reptvals, adcount);
                        }

                    }

                    break;
                }

                case "adminSrecodb": {
                    /* Introduction */
                    /* Set up  Big font sizes */
                    bgtxvewidec = new ArrayList<>(Arrays.asList(R.id.usrmanintro, R.id.usrmanintrg, R.id.usrmanintro10));

                    /* Set up  small font sizes */
                    smtxvewidec = new ArrayList<>(Arrays.asList(R.id.usrmanintroa, R.id.usrmanintrob, R.id.usrmanintroc,
                            R.id.usrmanintrod, R.id.usrmanintroe, R.id.usrmanintrof, R.id.usrmanintrog, R.id.usrmanintrh, R.id.usrmanintri,
                            R.id.usrmanintrj, R.id.usrmanintrl, R.id.usrmanintrm, R.id.usrmanintrog10, R.id.usrmanintrl12));


                    break;
                }

                case "adminSrecodc": {
                    /* Set up  Big font sizes */
                    bgtxvewidec = new ArrayList<>(Arrays.asList(R.id.usrmanassgnusr));

                    /* Set up  small font sizes */
                    smtxvewidec = new ArrayList<>(Arrays.asList(R.id.usrmanassgnusra, R.id.usrmanassgnusrb, R.id.usrmanassgnusrc,
                            R.id.usrmanassgnusrd, R.id.usrmanassgnusre));


                    break;
                }

                case "adminSrecodd": {
                    /* Set up  Big font sizes */
                    bgtxvewidec = new ArrayList<>(Arrays.asList(R.id.usrmanprdcts, R.id.usrmanprdctsf, R.id.usrmanprdctsl,
                            R.id.usrmanprdctso, R.id.usrmanprdctst));

                    /* Set up  small font sizes */
                    smtxvewidec = new ArrayList<>(Arrays.asList(R.id.usrmanprdctsa, R.id.usrmanprdctsb,
                            R.id.usrmanprdctsd, R.id.usrmanprdctse, R.id.usrmanprdctsg, R.id.usrmanprdctsh, R.id.usrmanprdctsi,
                            R.id.usrmanprdctsj, R.id.usrmanprdctsk, R.id.usrmanprdctsm, R.id.usrmanprdctsn, R.id.usrmanprdctsp,
                            R.id.usrmanprdctsq, R.id.usrmanprdctsr, R.id.usrmanprdctss, R.id.usrmanprdctsu, R.id.usrmanprdctsv));

                    break;
                }

                case "adminSrecode": {
                    /* Set up  Big font sizes */
                    bgtxvewidec = new ArrayList<>(Arrays.asList(R.id.saletxtvew, R.id.saletxtvewd, R.id.saletxtvewk));

                    /* Set up  small font sizes */
                    smtxvewidec = new ArrayList<>(Arrays.asList(R.id.saletxtvewa, R.id.saletxtvewb, R.id.saletxtvewc, R.id.saletxtvewe,
                            R.id.saletxtvewf, R.id.saletxtvewg, R.id.saletxtvewh, R.id.saletxtvewi, R.id.saletxtvewj, R.id.saletxtvewl,
                            R.id.saletxtvewm, R.id.saletxtvewn, R.id.saletxtvewo));

                    break;
                }

                case "adminSrecodf": {
                    /* Set up  Big font sizes */
                    bgtxvewidec = new ArrayList<>(Arrays.asList(R.id.stkstxtvew, R.id.stkstxtvewd, R.id.stkstxtvewk));

                    /* Set up  small font sizes */
                    smtxvewidec = new ArrayList<>(Arrays.asList(R.id.stkstxtvewa, R.id.stkstxtvewb, R.id.stkstxtvewd,
                            R.id.stkstxtvewe, R.id.stkstxtvewf, R.id.stkstxtvewg, R.id.stkstxtvewh,
                            R.id.stkstxtvewl, R.id.stkstxtvewl, R.id.stkstxtvewm, R.id.stkstxtvewn));

                    break;
                }

                case "adminSrecodg": {
                    /* Set up  Big font sizes */
                    bgtxvewidec = new ArrayList<>(Arrays.asList(R.id.debtxtvew, R.id.debtxtvewf, R.id.debtxtvewj, R.id.debtxtvewq,
                            R.id.debtxtvewu, R.id.debtxtvewx));

                    /* Set up  small font sizes */
                    smtxvewidec = new ArrayList<>(Arrays.asList(R.id.debtxtvewa, R.id.debtxtvewb, R.id.debtxtvewc,
                            R.id.debtxtvewg, R.id.debtxtvewh, R.id.debtxtvewi, R.id.debtxtvewk, R.id.debtxtvewl,
                            R.id.debtxtvewm, R.id.debtxtvewn, R.id.debtxtvewo, R.id.debtxtvewp, R.id.debtxtvewr, R.id.debtxtvews,
                            R.id.debtxtvewt, R.id.debtxtvewv, R.id.debtxtveww, R.id.debtxtvewz, R.id.debtxtvewaa, R.id.debtxtvewab,
                            R.id.debtxtvewac));

                    break;
                }

                case "adminSrecodh": {
                    /* Set up  Big font sizes */
                    bgtxvewidec = new ArrayList<>(Arrays.asList(R.id.liticstxtvew));

                    /* Set up  small font sizes */
                    smtxvewidec = new ArrayList<>(Arrays.asList(R.id.liticstxtvewa, R.id.liticstxtvewb, R.id.liticstxtvewc, R.id.liticstxtvewd,
                            R.id.liticstxtvewe, R.id.liticstxtvewf, R.id.liticstxtvewg));
                    break;
                }

                case "adminSrecodi": {
                    /* Set up  Big font sizes */
                    bgtxvewidec = new ArrayList<>(Arrays.asList(R.id.admintxtvew, R.id.admintxtvewb, R.id.admintxtvewg, R.id.admintxtvewj));

                    /* Set up  small font sizes */
                    smtxvewidec = new ArrayList<>(Arrays.asList(R.id.admintxtvewa, R.id.admintxtvewc, R.id.admintxtvewd, R.id.admintxtvewe,
                            R.id.admintxtvewf, R.id.admintxtvewh, R.id.admintxtvewi, R.id.admintxtvewk));
                    break;
                }

                case "adminSrecodga": {
                    /* Set up  Big font sizes */
                    bgtxvewidec = new ArrayList<>(Arrays.asList(R.id.ordestxtvew, R.id.ordestxtvewe, R.id.ordestxtvewg, R.id.ordestxtvewn));

                    /* Set up  small font sizes */
                    smtxvewidec = new ArrayList<>(Arrays.asList(R.id.ordestxtvewa, R.id.ordestxtvewb, R.id.ordestxtvewc, R.id.ordestxtvewd,
                            R.id.ordestxtvewf, R.id.ordestxtvewfa, R.id.ordestxtvewh, R.id.ordestxtvewi, R.id.ordestxtvewj, R.id.ordestxtvewk,
                            R.id.ordestxtvewl, R.id.ordestxtvewm, R.id.ordestxtvewma, R.id.ordestxtvewmb, R.id.ordestxtvewo, R.id.ordestxtvewp));
                    break;
                }


                default:
                    break;
            }

            if (mimspr.equals("adminSrecodb") || mimspr.equals("adminSrecodc") || mimspr.equals("adminSrecodd") ||
                    mimspr.equals("adminSrecode") || mimspr.equals("adminSrecodf") || mimspr.equals("adminSrecodg") ||
                    mimspr.equals("adminSrecodh") || mimspr.equals("adminSrecodi") || mimspr.equals("adminSrecodga")) {
                /*Set textviews font sizes*/
                TextView[] Txvewarry = new TextView[bgtxvewidec.size()];
                for (int k = 0; k < bgtxvewidec.size(); ++k) {
                    (Txvewarry[k] = usrLinear.findViewById(bgtxvewidec.get(k))).setTextSize((float) eunicapr + 6f);
                    (Txvewarry[k] = usrLinear.findViewById(bgtxvewidec.get(k))).setTextColor(Color.rgb(77, 77, 77));
                }

                /*Set textviews font sizes*/
                TextView[] Txvewarrya = new TextView[smtxvewidec.size()];
                for (int k = 0; k < smtxvewidec.size(); ++k) {
                    (Txvewarrya[k] = usrLinear.findViewById(smtxvewidec.get(k))).setTextSize((float) eunicapr + 4.5f);
                    (Txvewarrya[k] = usrLinear.findViewById(smtxvewidec.get(k))).setTextColor(Color.rgb(77, 77, 77));
                }
            }


            return response;
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            /* Add to parent */
            scroLayoutpr.removeAllViews();
            scroLayoutpr.addView(usrLinear, new ScrollView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            progbarpr.setIndeterminate(false);


        }

        protected void onProgressUpdate(Integer... array) {
        }
    }

    /*  Add data */
    public void adnRecadata(String reptheader, final String mims, LinearLayout usrLinear, double eunica,
                            ArrayList<String> Titles, Integer columnSize, ArrayList<Float> Widoth, ArrayList<Integer> Gravety,
                            ArrayList<ArrayList<String>> Reptvals, Integer adcount) {

        /*  Report header */
        TextView hedtxtvew = new TextView(adminContext);
        hedtxtvew.setText(reptheader);
        hedtxtvew.setAllCaps(false);
        hedtxtvew.setGravity(Gravity.LEFT);
        hedtxtvew.setTextSize((float) eunica + 5.0f);
        hedtxtvew.setBackgroundResource(R.color.bluu);
        hedtxtvew.setPadding(16, 3, 10, 3);
        hedtxtvew.setTextColor(Color.rgb(255, 255, 255));

        LinearLayout.LayoutParams hedParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        if (adcount > 1) {
            hedParams.topMargin = 70;
        }

        usrLinear.addView(hedtxtvew, hedParams);

        /* Headers */
        FlexboxLayout hedFlexbox = new FlexboxLayout(adminContext);
        hedFlexbox.setDividerDrawable(adminContext.getResources().getDrawable(R.drawable.flexcontdivider));
        hedFlexbox.setShowDividerHorizontal(FlexboxLayout.SHOW_DIVIDER_BEGINNING);
        hedFlexbox.setShowDividerVertical(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
        hedFlexbox.setBackgroundColor(adminContext.getResources().getColor(R.color.bluu));

        for (int ghas = 0; ghas < columnSize; ghas++) {
            TextView header = new TextView(adminContext);
            header.setGravity(Gravity.CENTER);
            header.setTextSize((float) eunica + 6.0f);
            header.setText(Titles.get(ghas));
            header.setTextColor(Color.rgb(255, 255, 255));
            header.setPadding(0, 10, 0, 10);

            FlexboxLayout.LayoutParams hedlayoutParams = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.MATCH_PARENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT);

            hedlayoutParams.setFlexBasisPercent(Widoth.get(ghas));
            hedlayoutParams.setFlexGrow(1.0f);
            hedFlexbox.addView(header, hedlayoutParams);
        }

        usrLinear.addView(hedFlexbox, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        /* Data children */
        ArrayList<Integer> idecs = new ArrayList<>();
        ArrayList<Integer> idecsa = new ArrayList<>();

        if (Reptvals.get(0).size() > 0) {
            for (int bin = 0; bin < Reptvals.get(0).size(); bin++) {
                idecsa.add(Math.abs(resources.generateViewId()));

                FlexboxLayout usrFlexbox = new FlexboxLayout(adminContext);
                usrFlexbox.setFlexDirection(FlexDirection.ROW);
                usrFlexbox.setAlignContent(AlignContent.FLEX_START);
                usrFlexbox.setAlignItems(AlignItems.BASELINE);
                usrFlexbox.setFlexWrap(FlexWrap.NOWRAP);
                usrFlexbox.setPadding(0, 0, 0, 0);
                usrFlexbox.setId(idecsa.get(bin));
                usrFlexbox.setBackgroundResource(R.drawable.listflexcont);

                for (int bina = 0; bina < columnSize; bina++) {
                    idecs.add(Math.abs(resources.generateViewId()));

                    TextView listBtn = new TextView(adminContext);
                    listBtn.setText(Reptvals.get(bina).get(bin));
                    listBtn.setId(idecs.get(bin));
                    listBtn.setAllCaps(false);
                    listBtn.setGravity(Gravety.get(bina));
                    listBtn.setTextSize((float) eunica + 5.0f);
                    listBtn.setBackgroundResource(R.drawable.txtvewlistcomb);
                    listBtn.setPadding(16, 36, 10, 10);
                    listBtn.setTextColor(Color.rgb(77, 77, 77));

                    FlexboxLayout.LayoutParams lfayoutParams = new FlexboxLayout.LayoutParams(
                            FlexboxLayout.LayoutParams.MATCH_PARENT,
                            FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    lfayoutParams.setFlexBasisPercent(Widoth.get(bina));
                    lfayoutParams.setFlexGrow(1.0f);
                    usrFlexbox.addView(listBtn, lfayoutParams);

                    if (bina == 0) {
                        String dbess = "";
                        String stateidec = "";
                        String uniqident = "";
                        String uniqidentval = "";

                        if (mims.equals("prdSrecod")) {
                            dbess = "prdctdetaz";
                            stateidec = "prdctdetazstate";
                            uniqident = "prdctdetazcode";
                            uniqidentval = Reptvals.get(4).get(bin);

                        } else if (mims.equals("prdSrecoda")) {
                            dbess = "prdctdcateri";
                            stateidec = "prdctdcateristate";
                            uniqident = "prdctdcateriuniq";
                            uniqidentval = Reptvals.get(2).get(bin);

                        } else if (mims.equals("salSrecod")) {
                            dbess = "salesrecd";
                            stateidec = "salesrecdstate";
                            uniqident = "salesrecdrecod";
                            uniqidentval = Reptvals.get(5).get(bin);

                        } else if (mims.equals("inventSrecoda")) {
                            dbess = "inventbl";
                            stateidec = "inventblstate";
                            uniqident = "inventblrecod";
                            uniqidentval = Reptvals.get(4).get(bin);

                        } else if (mims.equals("debtSrecod")) {
                            dbess = "custostbl";
                            stateidec = "custostblstate";
                            uniqident = "custostblrecod";
                            uniqidentval = Reptvals.get(3).get(bin);

                        } else if (mims.equals("debtSrecoda")) {
                            dbess = "custodatpay";
                            stateidec = "custodatpaystate";
                            uniqident = "custodatpayrecod";
                            uniqidentval = Reptvals.get(3).get(bin);

                        } else if (mims.equals("debtSrecodb")) {
                            dbess = "custodates";
                            stateidec = "custodatestate";
                            uniqident = "custodatesrecod";
                            uniqidentval = Reptvals.get(6).get(bin);

                        } else if (mims.equals("ordeSrecoda")) {
                            dbess = "ordesdetal";
                            stateidec = "ordetalstate";
                            uniqident = "ordetaluniq";
                            uniqidentval = Reptvals.get(4).get(bin);

                        }


                        final String finalDbess = dbess;
                        final String finalStateidec = stateidec;
                        final String finalUniqident = uniqident;
                        final String finalUniqidentval = uniqidentval;
                        listBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                resources.recdDeltdilogb(finalDbess, finalStateidec, finalUniqident, finalUniqidentval);

                            }
                        });

                    }
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);
                usrLinear.addView(usrFlexbox, layoutParams);

            }
        }
    }
}
