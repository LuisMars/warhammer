package es.luismars;

import java.util.*;

/**
 * Created by Luis on 20/09/2014.
 */
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

        if (TYPE.charAt(0) == 'D') {
            size = 1;
            squad = new GreyKnight[size];
            squad[0] = setDreadknight();
            return;
        }

        String IDs = Integer.toBinaryString(ID).substring(1);
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

        for (Stats s : squad) {
            if (TYPE.charAt(0) == 'T') {
                s.spr.shootHeavy = true;
                s.spr.SweepingAdvance = false;
            }
        }
    }

    public GreyKnight setDreadknight() {
        return new GreyKnight(justStats, new CCWeapon(), new RangedWeapon(), 0);

    }

    public GreyKnight setJusticar(int WP, int MC, boolean digital, boolean mBombs, boolean TPH) {
        //TODO: working meltabombs and teleport homer

        CCWeapon ccw = new CCWeapon(WP, MC == 1, digital);

        RangedWeapon rw = new RangedWeapon(0, MC >= 2, digital);

        GreyKnight t = new GreyKnight(justStats, ccw, rw, digital, mBombs, TPH);

        t.setCost(justCost + (digital ? 10 : 0) + (MC > 0 ? 10 : 0) + (mBombs ? 5 : 0) + (TPH ? 10 : 0) + ccw.getCost());

        if (mBombs) t.spr.mBombs = true;
        if (TPH) t.spr.TPH = true;

        t.items = (digital ? "\n" +
                "\tDigital weapons" : "") + (mBombs ? "\n" +
                "\tMelta bombs" : "") + (TPH ? "\n" +
                "\tTeleport homer" : "");
        return t;
    }

    public GreyKnight setTroop(int WP) {

        CCWeapon ccw = new CCWeapon(WP, false, false);

        RangedWeapon rw = new RangedWeapon(0, false, false);

        GreyKnight t = new GreyKnight(stats, ccw, rw, 1);

        t.setCost(unitCost + ccw.getCost());

        return t;
    }

    public GreyKnight setSpecial(int WP, int RW) {

        CCWeapon ccw = new CCWeapon(WP, false, false);

        RangedWeapon rw = new RangedWeapon(RW, false, false);

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

            if (squad.length == 10 && ((squad[4].ccw.ID < squad[9].ccw.ID && squad[4].rw.ID == squad[9].rw.ID) || (squad[4].rw.ID < squad[9].rw.ID && squad[4].ccw.ID == squad[9].ccw.ID)))
                s += squad[9].getID();
            else
                s += squad[4].getID();


            if (squad.length == 10) {
                s += squad[5].getID();
                if ((squad[4].ccw.ID >= squad[9].ccw.ID && squad[4].rw.ID == squad[9].rw.ID) || (squad[4].rw.ID >= squad[9].rw.ID && squad[4].ccw.ID == squad[9].ccw.ID))
                    s += squad[9].getID();
                else
                    s += squad[4].getID();
            }

            //while (s.length() < 31)
            //    s += "0";
            ID = Integer.parseInt(s, 2);
        }
    }

    @Override
    public String toString() {

        String s = "-----------------------------------------------\n";

        //for (Stats t : squad)
        //    s += TYPE + t.toString() + "\n";
        List<Stats> asList = Arrays.asList(squad);

        HashSet<Stats> set = new HashSet<Stats>(asList);
        List<Stats> sorted = new ArrayList<Stats>(set);
        Collections.sort(sorted);
        for (Stats t : sorted) {
            s += Collections.frequency(asList, t) + "x " + TYPE + t.toString() + "\n";
        }

        s += "-----------------------------------------------\n";
        s += "\tCost: " + getCost() + "\n";
        s += "-----------------------------------------------\n";
        return s;
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
                justStats = new int[]{5, 4, 10, 6, 4, 4, 4, 10, 2, 5, 2, 0, 0, 0, 0, 12};
                unitCost = 130;
            }
        }
    }
}
