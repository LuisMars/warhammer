package es.luismars;
/**
 * Created by Luis on 09/09/2014.
 */
public class Term extends Rules{


    public Stats stats;
    public CCWeapon ccw;
    public RangedWeapon rw = new RangedWeapon();

    public Term() {}

    public Term(String ID) {
        if(ID.length() == 3) {
            init(ID);
        }
    }

    public void init(String ID) {
        stats = new Stats();
        setWeapon(Integer.parseInt(ID, 2));
    }

    public void init(int ID) {
        stats = new Stats();
        setWeapon(ID);
    }

    public void setWeapon(int WP) {
        ccw = new CCWeapon(Math.min(4, WP), this);
    }

    public String toString() {
        return "Terminator with " + ccw.toString() + "(" + getCost() + ")";
    }

    public int getCost() {
        return 24 + ccw.getCost();
    }

    public String getID() {
        return Utils.toBinStr(ccw.ID,3);
    }

    public String statsToString() {
        return ccw.toString();
    }

    public double shoot(Stats s) {
        double wounds = 0;
        for (int i = 0; i < rw.shots; i++) {
            double w = 0;
            if(rw.ID == 1)
                w++;
            else
                w +=shoot(stats.BS);
            w += wound(rw.S, s.T);
            if (rw.AP > s.AS)
                w *= armorSave(s.AS);
            else
                w *= specialSave(s.SS);
            wounds += w;
        }
        return wounds;
    }

    public double combat(int turn, Stats s, boolean hammerhand) {
        int extra = 0;
        if(turn == 0)
            extra = 1;

        int hhand = 0;
        if(hammerhand) hhand = 2;

        double wounds = 0;
        for (int i = 0; i < ccw.stats.A+extra; i++) {
            double w;
            w = hit(ccw.stats.WS, s.WS);

            w *= wound(ccw.stats.S+hhand, s.T);
            if(ccw.stats.AP > s.AS)
                w *= armorSave(s.AS);
            else
                w *= specialSave(s.SS);
            wounds += w;
        }

        return wounds;
    }

}
