package es.luismars;

public class GreyKnight extends Stats {

    String items;

    public GreyKnight(int[] s, CCWeapon ccw, RangedWeapon rw, int id) {
        super(s, ccw, rw);

        ID += ccw.getID();
        if (id == 2)
            ID += rw.getID();
    }

    //For justicars
    public GreyKnight(int[] s, CCWeapon ccw, RangedWeapon rw, boolean digital, boolean mBombs, boolean TPH) {
        super(s, ccw, rw);
        ID = ccw.getID();
        ID += ccw.spr.rerollOneHit ? "01" : rw.spr.rerollOneHit ? "10" : "00";
        ID += (digital ? "1" : "0") + (mBombs ? "1" : "0") + (TPH ? "1" : "0");
    }

    public String toString() {
        String res = "";
        if (ID.length() == 8)
            res += " Justicar";
        res += " with:\t\t\t(" + getCost() + ")\n\t" + ccw.toString() + rw.toString() + (items == null ? "" : items);

        return res;
    }

    public String getID() {
        return ID;
    }
}
