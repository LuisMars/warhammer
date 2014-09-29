package es.luismars;

import java.util.List;

/**
 * Created by Luis on 20/09/2014.
 */
public class InterceptorSquad extends Squad {

    public InterceptorSquad(String id) {
        this(Integer.parseInt(id, 2));
    }

    public InterceptorSquad(int id) {
        super();
        ID = id;
        set();
        setID();
        setCost();
    }

    public void set() {
        String IDs = Integer.toBinaryString(ID).substring(1);
        while (IDs.length() < 32)
            IDs += "0";
        if (IDs.charAt(0) == '0') {
            squad = new Interceptor[5];
            size = 5;
        } else {
            squad = new Interceptor[10];
            size = 10;
        }


        squad[0] = setJusticar(Integer.parseInt(IDs.substring(1, 4), 2),
                Integer.parseInt(IDs.substring(4, 6), 2),
                Integer.parseInt(IDs.substring(6, 7), 2) == 1,
                Integer.parseInt(IDs.substring(7, 8), 2) == 1,
                Integer.parseInt(IDs.substring(8, 9), 2) == 1);

        for (int i = 1; i < 4; i++)
            squad[i] = setInterceptor(Integer.parseInt(IDs.substring(9, 12), 2));

        squad[4] = setSpecial(Integer.parseInt(IDs.substring(12, 15), 2), Integer.parseInt(IDs.substring(15, 17), 2));

        for (int i = 5; i < squad.length - 1; i++)
            squad[i] = setInterceptor(Integer.parseInt(IDs.substring(17, 20), 2));

        if (squad.length == 10)
            squad[9] = setSpecial(Integer.parseInt(IDs.substring(20, 23), 2), Integer.parseInt(IDs.substring(23, 25), 2));

    }

    public Interceptor setJusticar(int WP, int MC, boolean digital, boolean mBombs, boolean TPH) {
        //TODO: working meltabombs and teleport homer
        int[] s = {4, 4, 4, 4, 1, 4, 2, 9, 3, 7, 0, 0, 0, 0, 0, 12};
        CCWeapon ccw = new CCWeapon(WP, MC == 1, digital);
        RangedWeapon rw = new RangedWeapon(0, MC >= 2, digital);
        Interceptor t = new Interceptor(s, ccw, rw, digital, mBombs, TPH);
        t.setCost(34 + (digital ? 10 : 0) + (MC > 0 ? 10 : 0) + (mBombs ? 5 : 0) + (TPH ? 10 : 0) + ccw.getCost());

        t.items = "(" + (digital ? "Digital weapons, " : "") + (mBombs ? "Melta bombs, " : "") + (TPH ? "Teleport homer" : "") + ") ";
        return t;
    }

    public Interceptor setInterceptor(int WP) {
        int[] s = {4, 4, 4, 4, 1, 4, 1, 8, 3, 7, 0, 0, 0, 0, 0, 12};
        CCWeapon ccw = new CCWeapon(WP, false, false);
        RangedWeapon rw = new RangedWeapon(0, false, false);
        Interceptor t = new Interceptor(s, ccw, rw, 1);
        t.setCost(24 + ccw.getCost());
        return t;
    }

    public Interceptor setSpecial(int WP, int RW) {
        int[] s = {4, 4, 4, 4, 1, 4, 1, 8, 3, 7, 0, 0, 0, 0, 12};
        CCWeapon ccw = new CCWeapon(WP, false, false);
        RangedWeapon rw = new RangedWeapon(RW, false, false);
        Interceptor t = new Interceptor(s, ccw, rw, 2);
        t.setCost(24 + ccw.getCost() + (RW > 0 ? (rw.getCost()) : 0));
        return t;
    }

    public void setCost() {
        cost = 0;
        for (Stats t : squad)
            cost += t.getCost();
    }


    public void setID() {

        String s = "1";

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

    @Override
    public String toString() {
        String s = "";
        s += "Cost: " + getCost() + "\n";
        for (Stats t : squad)
            s += t.toString() + "\n";

        return s;
    }
}
