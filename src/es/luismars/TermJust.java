package es.luismars;
/**
 * Created by Luis on 09/09/2014.
 */
public class TermJust extends Term {

    int masterCr;
    boolean digitalW, meltaBombs, tpHomer;
    int cost;
    public TermJust(String ID) {
        if (ID.length() == 8) {
            extract(Integer.parseInt(ID.substring(0, 3), 2),
                    Integer.parseInt(ID.substring(3, 5), 2),
                    Integer.parseInt(ID.substring(5, 6),2 )== 1,
                    Integer.parseInt(ID.substring(6, 7),2) == 1,
                    Integer.parseInt(ID.substring(7, 8),2) == 1);
        }
        cost = super.getCost();
        stats.A++;

        if (masterCr != 0)
            cost +=  10;

        if(digitalW)
            cost += 10;
        if(meltaBombs)
            cost += 5;
        if(tpHomer)
            cost += 10;


    }

    public void extract(int WP, int MC, boolean digital, boolean mBombs, boolean TPH) {
        init(WP);
        masterCr = Math.min(2,MC);
        digitalW =  digital;
        meltaBombs = mBombs;
        tpHomer = TPH;
    }

    public String toString() {
        String res = "";

        res += "Justicar with ";
        if(masterCr == 1)
            res += "Master-crafted ";
        res += ccw;
        if (masterCr >= 2)
            res += " and Master-crafted Assault bolter";

        if(digitalW || meltaBombs || tpHomer) {
            res += "(";
            if(digitalW)
                res += " Digital Weapons";
            if(meltaBombs)
                res += " Melta Bombs";
            if(tpHomer)
                res += " Teleport Homer";
            res += " ) (" + getCost() + ")" ;
        }

        //res += "\n" + ccw.stats;
        return res;
    }

    @Override
    public String getID() {
        String res = "";
        res += super.getID();
        res += Utils.toBinStr(masterCr,2);
        res += digitalW?1:0;
        res += meltaBombs?1:0;
        res += tpHomer?1:0;
        return res;
    }
    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public double shoot(Stats s) {
        double wounds = 0;
        for (int i = 0; i < rw.shots; i++) {
            double w;
            if (masterCr == 2)
                w = reroll1ofN(shoot(stats.BS), i);
            else
                w = shoot(stats.BS);
            if (digitalW)
                w *= reroll1ofN(wound(rw.S, s.T), i);
            else
                w *= wound(rw.S, s.T);
            if (rw.AP > s.AS)
                w *= armorSave(s.AS);
            else
                w *= specialSave(s.SS);
            wounds += w;
        }
        return wounds;
    }

    @Override
    public double combat(int turn, Stats s, boolean hammerhand) {
        int extra = 0;
        if(turn == 0)
            extra = 1;

        int hhand = 0;
        if(hammerhand) hhand = 2;
        double wounds = 0;
        for (int i = 0; i < ccw.stats.A+extra; i++) {
            double w;

            if(masterCr == 1)
                w = reroll1ofN(hit(ccw.stats.WS, s.WS),i);
            else
                w = hit(ccw.stats.WS, s.WS);


            if (digitalW)
                w *= reroll1ofN(wound(ccw.stats.S+hhand, s.T),i);
            else
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
