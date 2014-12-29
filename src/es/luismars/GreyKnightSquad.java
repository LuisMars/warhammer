package es.luismars;

import java.util.*;

public class GreyKnightSquad extends Squad {

    int[] justStats;
    int[] stats;
    int unitCost;
    int justCost;
    int extraRWcost;

    public GreyKnightSquad(String type, String id) {
        this(type, Integer.parseInt(id, 2));
    }

    public GreyKnightSquad(String type, int id) {
        super();
        TYPE = type;
        ID = id;
        set();
        setID();
        setCost();
    }

    public void set() {

        setBase();

        String IDs = Integer.toBinaryString(ID).substring(1);

        if (TYPE.charAt(0) == 'D') {
            loadDreadKnight(IDs);
        } else {
            loadDefaultSquad(IDs);
        }
        setWoundSize();
    }

    private void loadDreadKnight(String IDs) {
        while (IDs.length() < 32)
            IDs += "0";
        size = 1;
        squad = new GreyKnight[size];

        boolean a = Utils.read(IDs, 3, 4) == 1;
        boolean b = Utils.read(IDs, 4, 5) == 1;
        boolean c = Utils.read(IDs, 5, 6) == 1;

        int rw1 = 7;
        int rw2 = 7;

        if (a) {
            rw1 = 4;
            if (b)
                rw2 = 5;
            else if (c)
                rw2 = 6;
        } else if (b) {
            rw1 = 5;
            if (c)
                rw2 = 6;
        } else if (c)
            rw1 = 6;


        squad[0] = setDreadknight(Utils.read(IDs, 0, 3), rw1, rw2, Utils.read(IDs, 6, 7) == 1);
        spr.shootHeavy = true;
    }

    private void loadDefaultSquad(String IDs) {
        while (IDs.length() < 32)
            IDs += "0";
        if (IDs.charAt(0) == '0') {
            squad = new GreyKnight[5];
            size = 5;
        } else {
            squad = new GreyKnight[10];
            size = 10;
        }


        squad[0] = setJusticar(Utils.read(IDs, 1, 4),
                Utils.read(IDs, 4, 6),
                Utils.read(IDs, 6, 7) == 1,
                Utils.read(IDs, 7, 8) == 1,
                Utils.read(IDs, 8, 9) == 1);

        for (int i = 1; i < 4; i++)
            squad[i] = setTroop(Utils.read(IDs, 9, 12));

        squad[4] = setSpecial(Utils.read(IDs, 12, 15), Utils.read(IDs, 15, 17));

        for (int i = 5; i < squad.length - 1; i++)
            squad[i] = setTroop(Utils.read(IDs, 17, 20));

        if (squad.length == 10)
            squad[9] = setSpecial(Utils.read(IDs, 20, 23), Utils.read(IDs, 23, 25));

        //for (Stats s : squad) {
            if (TYPE.charAt(0) == 'T') {
                spr.shootHeavy = true;
                spr.SweepingAdvance = false;
            }
            if (TYPE.charAt(0) == 'D') {
                spr.shootHeavy = true;
            }
        //}
    }

    public GreyKnight setDreadknight(int WP, int RW1, int RW2, boolean PT) {

        WP = Math.max(4, Math.min(6, WP));

        CCWeapon ccw = new CCWeapon(WP);

        RangedWeapon rw = new RangedWeapon(RW1);

        RangedWeapon rw2 = new RangedWeapon(RW2);

        GreyKnight d = new GreyKnight(stats, ccw, rw, rw2, PT);

        ccw.updateStats(d);

        d.setCost(unitCost + d.ccw.get(0).getCost() + rw.getCost() + rw2.getCost() + (PT ? 30 : 0));


        return d;

    }

    public GreyKnight setJusticar(int WP, int MC, boolean digital, boolean mBombs, boolean TPH) {
        //TODO: working meltabombs and teleport homer

        CCWeapon ccw = new CCWeapon(WP);
        ccw.rerollOneHit = MC == 1;
        ccw.rerollOneWound = digital;

        RangedWeapon rw = new RangedWeapon(0);
        rw.rerollOneHit = MC > 1;
        rw.rerollOneWound = digital;

        GreyKnight t = new GreyKnight(justStats, ccw, rw, digital, mBombs, TPH);

        ccw.updateStats(t);

        t.setCost(justCost + (digital ? 10 : 0) + (MC > 0 ? 10 : 0) + (mBombs ? 5 : 0) + (TPH ? 10 : 0) + ccw.getCost());

        if (mBombs) t.mBombs = true;
        if (TPH) t.TPH = true;

        t.items = (digital ? "\n" +
                "\tDigital weapons" : "") + (mBombs ? "\n" +
                "\tMelta bombs" : "") + (TPH ? "\n" +
                "\tTeleport homer" : "");
        return t;
    }

    public GreyKnight setTroop(int WP) {

        CCWeapon ccw = new CCWeapon(WP);

        RangedWeapon rw = new RangedWeapon(0);

        GreyKnight t = new GreyKnight(stats, ccw, rw, 1);

        t.setCost(unitCost + ccw.getCost());

        return t;
    }

    public GreyKnight setSpecial(int WP, int RW) {

        CCWeapon ccw = new CCWeapon(WP);

        RangedWeapon rw = new RangedWeapon(RW);

        GreyKnight t = new GreyKnight(stats, ccw, rw, 2);

        t.setCost(unitCost + ccw.getCost() + (RW > 0 ? (rw.getCost() + extraRWcost) : 0));

        return t;
    }

    public void setCost() {
        cost = 0;
        for (Stats t : squad)
            cost += t.getCost();
    }

    public void setID() {

        String s = "1";
        if (squad.length != 1) {
            if (squad.length == 5)
                s += "0";
            else if (squad.length == 10)
                s += "1";
            else ID = 0;

            s += squad[0].getID();

            s += squad[1].getID();

            if (squad.length == 10 && ((squad[4].ccw.get(0).ID < squad[9].ccw.get(0).ID && squad[4].rw.get(0).ID == squad[9].rw.get(0).ID) || (squad[4].rw.get(0).ID < squad[9].rw.get(0).ID && squad[4].ccw.get(0).ID == squad[9].ccw.get(0).ID)))
                s += squad[9].getID();
            else
                s += squad[4].getID();


            if (squad.length == 10) {
                s += squad[5].getID();
                if ((squad[4].ccw.get(0).ID >= squad[9].ccw.get(0).ID && squad[4].rw.get(0).ID == squad[9].rw.get(0).ID) || (squad[4].rw.get(0).ID >= squad[9].rw.get(0).ID && squad[4].ccw.get(0).ID == squad[9].ccw.get(0).ID))
                    s += squad[9].getID();
                else
                    s += squad[4].getID();
            }

            //while (s.length() < 31)
            //    s += "0";
        } else {
            s += squad[0].getID();
        }

        ID = Integer.parseInt(s, 2);
    }


    private void setBase() {
        switch (TYPE.charAt(0)) {
            //Interceptor
            case 'I': {
                justStats = new int[]{4, 4, 4, 4, 1, 4, 2, 9, 3, 7, 0, 0, 0, 0, 0, 12};
                justCost = 34;

                stats = new int[]{4, 4, 4, 4, 1, 4, 1, 8, 3, 7, 0, 0, 0, 0, 0, 12};
                unitCost = 24;

                extraRWcost = 0;
                break;
            }
            //Terminator
            case 'T': {
                justStats = new int[]{4, 4, 4, 4, 1, 4, 2, 9, 2, 5, 0, 0, 0, 0, 0, 6};
                justCost = 34;

                stats = new int[]{4, 4, 4, 4, 1, 4, 2, 9, 2, 5, 0, 0, 0, 0, 0, 6};
                unitCost = 33;

                extraRWcost = 5;
                break;
            }
            //Strike
            case 'S': {
                justStats = new int[]{4, 4, 4, 4, 1, 4, 2, 9, 3, 7, 0, 0, 0, 0, 0, 6};
                justCost = 30;

                stats = new int[]{4, 4, 4, 4, 1, 4, 1, 8, 3, 7, 0, 0, 0, 0, 0, 6};
                unitCost = 20;

                extraRWcost = 0;
                break;
            }
            case 'D': {
                stats = new int[]{5, 4, 6, 6, 4, 4, 4, 10, 2, 5, 2, 0, 0, 0, 0, 6};
                unitCost = 130;
            }
        }
    }

}
