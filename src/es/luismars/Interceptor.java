package es.luismars;

/**
 * Created by Luis on 20/09/2014.
 */
public class Interceptor extends Stats {

    String items;

    public Interceptor(int[] s, CCWeapon ccw, RangedWeapon rw, int id) {
        super(s, ccw, rw);

        ID += ccw.getID();
        if (id == 2)
            ID += rw.getID();
    }

    //For justicars
    public Interceptor(int[] s, CCWeapon ccw, RangedWeapon rw, boolean digital, boolean mBombs, boolean TPH) {
        super(s, ccw, rw);
        ID = ccw.getID();
        ID += ccw.spr.rerollOneHit ? "01" : rw.spr.rerollOneHit ? "10" : "00";
        ID += (digital ? "1" : "0") + (mBombs ? "1" : "0") + (TPH ? "1" : "0");
    }

    public String toString() {
        String res = "Interceptor ";
        if (ID.length() == 8)
            res += "Justicar ";
        res += "with: " + ccw.toString() + rw.toString() + (items == null ? "" : items) + "(" + getCost() + ")";

        return res;
    }

    public String getID() {
        return ID;
    }
}
