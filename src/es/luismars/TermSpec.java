package es.luismars;
/**
 * Created by Luis on 09/09/2014.
 */
public class TermSpec extends Term {
    RangedWeapon rw;
    int SpecWeapon;
    int cost;
    public TermSpec(String ID) {
        if (ID.length() == 5)
            extract(Integer.parseInt(ID.substring(0, 3), 2), Integer.parseInt(ID.substring(3, 5), 2));

        cost = super.getCost();

        switch (SpecWeapon) {
            case 0: break;
            case 1: { cost += 5; break;}
            case 2: { cost += 10; break;}
            case 3: { cost += 15; break;}

        }
        rw = new RangedWeapon(SpecWeapon);
    }

    public void extract (int CC, int SW) {
        init(CC);
        SpecWeapon = SW;
    }

    public String toString () {
        String res = "";

        res += "Terminator with " + ccw + "(" + getCost() + ")";
        switch (SpecWeapon) {
            case 0: break;
            case 1: { res += " and Incinerator"; break;}
            case 2: { res += " and Psilencer"; break;}
            case 3: { res += " and Psycannon"; break;}

        }

        return res;
    }

    @Override
    public String getID() {
        String res = "";
        res += super.getID();
        res += Utils.toBinStr(SpecWeapon,2);
        return res;
    }
    @Override
    public int getCost() {
        return cost;
    }
}
