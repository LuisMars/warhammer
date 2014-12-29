package es.luismars;

public class GreyKnight extends Stats {

    String items;

    //For specials and troops
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
        ID += ccw.rerollOneHit ? "01" : rw.rerollOneHit ? "10" : "00";
        ID += (digital ? "1" : "0") + (mBombs ? "1" : "0") + (TPH ? "1" : "0");
    }

    //For dreadknights
    public GreyKnight(int[] s, CCWeapon ccw, RangedWeapon rw1, RangedWeapon rw2, boolean PT) {
        super(s, ccw, rw1, rw2);
        if (ccw.ID == 4)
            ccw.cost -= 5;
        ID = ccw.getID();
        ID += (rw1.ID == 4 || rw2.ID == 4) ? "1" : "0";
        ID += (rw1.ID == 5 || rw2.ID == 5) ? "1" : "0";
        ID += (rw1.ID == 6 || rw2.ID == 6) ? "1" : "0";
        ID += PT ? "1" : "0";
        if (PT) {
            set(Stats.M, 12);
        }

        set(Stats.I, getBase(Stats.I));
    }

    public String toString() {
        String res = "";
        if (ID.length() == 8)
            res += " Justicar";
        res += super.toString() +
                (get(Stats.M) == 12 ? (getBase(Stats.M) == 6 ? ("\n\tPersonal Teleporter") : "") : "") +
                (items == null ? "" : items);

        return res;
    }
}
