package es.luismars.old;
/**
 * Created by Luis on 09/09/2014.
 */

/*
public class TermSquad {
    int ID;
    int size;
    int totalcost;
    TermJust justicar;
    TermSpec specialA;
    TermSpec specialB;
    Term[] termis;

    public TermSquad (int id) {
        ID = id;
        set();
    }

    public String toString() {
        String res = "";
        res += justicar.toString();
        res += "\n";
        res += specialA.toString();
        res += "\n";
        if (size == 10) {
            res += specialB.toString();
            res += "\n";
        }
        for (int i = 0; i <termis.length; i++) {
            res += termis[i].toString();
            res += "\n";
        }
        res += "-------------------------\n" +
                "Total cost: " + totalcost + " points.";
        return res;
    }

    public int getID() {

        String s = "1";

        if(size == 5)
            s += "0";
        else if (size == 10)
            s += "1";
        else return 0;

        s += justicar.getID();

        s += termis[0].getID();

        if(size == 10 && ((specialA.ccw.ID < specialB.ccw.ID && specialA.rw.ID == specialB.rw.ID) || (specialA.rw.ID < specialB.rw.ID && specialA.ccw.ID == specialB.ccw.ID)))
            s += specialB.getID();
        else
            s += specialA.getID();



        if(size == 10) {
            if(termis.length > 3)
                s += termis[3].getID();
            if ((specialA.ccw.ID >= specialB.ccw.ID && specialA.rw.ID == specialB.rw.ID) || (specialA.rw.ID >= specialB.rw.ID && specialA.ccw.ID == specialB.ccw.ID))
                s += specialB.getID();
            else
                s += specialA.getID();
        }

        while (s.length() > 32)
            s += "0";
        return Integer.parseInt(s, 2);
    }

    public void hammerhand(boolean activate) {
        int add = 2;
        if (!activate) add *=-1;
        justicar.stats.S += add;
        justicar.ccw.updateStats(justicar);


        specialA.stats.S += add;
        specialA.ccw.updateStats(specialA);
        if(size == 10) {
            specialB.stats.S += add;
            specialB.ccw.updateStats(specialB);
        }

        for (int i = 0; i < termis.length; i++) {
            termis[i].stats.S += add;
            termis[i].ccw.updateStats(termis[i]);
        }
    }

    public double combat(int turn, Stats s, int init, boolean hammerhand) {
        double wounds = 0;
        if(justicar.ccw.stats.I == init)
            wounds += justicar.combat(turn,s,hammerhand);


        if(specialA.ccw.stats.I == init)
            wounds += specialA.combat(turn,s,hammerhand);
        if(size == 10 && specialB.ccw.stats.I == init)
            wounds += specialB.combat(turn,s,hammerhand);

        for (int i = 0; i < termis.length; i++) {
            if(termis[i].ccw.stats.I == init)
            wounds += termis[i].combat(turn,s,hammerhand);
        }


        return wounds;

    }

    public double shoot(Stats s) {
        double wounds = 0;
        wounds += justicar.shoot(s);
        wounds += specialA.shoot(s);
        if(size==10)
            wounds += specialB.shoot(s);
        for (int i = 0; i < termis.length; i++) {
                wounds += termis[i].shoot(s);
        }
        return wounds;
    }

    public void set () {
        String IDs = Integer.toBinaryString(ID).substring(1);
        while (IDs.length() < 32)
            IDs+="0";
        if(IDs.charAt(0) == '0') {
            size = 5;
            termis = new Term[3];
        }
        else {
            size = 10;
            termis = new Term[7];
        }


            justicar = new TermJust(IDs.substring(1, 9));
            totalcost = justicar.getCost();

            for (int i = 0; i < 3; i++) {
                termis[i] = new Term(IDs.substring(9, 12));
                totalcost += termis[i].getCost();
            }

            specialA = new TermSpec(IDs.substring(12, 17));
            totalcost += specialA.getCost();

            for (int i = 3; i < termis.length; i++) {
                termis[i] = new Term(IDs.substring(17, 20));
                totalcost += termis[i].getCost();
            }

            if (size == 10) {
                specialB = new TermSpec(IDs.substring(20, 25));
                totalcost += specialB.getCost();
            }

    }

    public void reset() {
        set();
    }
}
*/