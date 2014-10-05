package es.luismars;
/**
 * Created by Luis on 14/09/2014.
 */
public class RangedWeapon {

    int ID;
    int cost;
    SpecialRules spr = new SpecialRules();

    public RangedWeapon () {
        ID = 0;
    }

    public RangedWeapon(int id, boolean MC, boolean DW) {
        ID = id;
        spr.rerollOneHit = MC;
        spr.rerollOneWound = DW;
    }

    public void updateStats(Stats stats) {
        switch (ID) {
            //Assault bolter
            case 0: {
                stats.set(Stats.SHOTS, 2);
                stats.set(Stats.RANGE, 24);
                stats.set(Stats.STR, 4);
                stats.set(Stats.APW, 4);
                cost = 0;
                break;
            }
            //Incinerator
            case 1: {
                spr.template = true;
                stats.set(Stats.SHOTS, 2); //Average result
                stats.set(Stats.RANGE, 6);
                stats.set(Stats.STR, 6);
                stats.set(Stats.APW, 4);
                cost = 5;
                break;
            }
            //Psilencer
            case 2: {
                spr.heavy = true;
                stats.set(Stats.SHOTS, 6);
                stats.set(Stats.RANGE, 24);
                stats.set(Stats.STR, 4);
                stats.set(Stats.APW, 7);
                cost = 10;
                break;
            }
            //Psycanon
            case 3: {
                spr.salvo = true;
                stats.set(Stats.SHOTS, 4);
                stats.set(Stats.RANGE, 24);
                stats.set(Stats.STR, 7);
                stats.set(Stats.APW, 4);
                cost = 15;
                break;
            }
        }
    }

    public int getCost() {
        return cost;
    }

    public String toString() {
        String res = "";
        if (spr.rerollOneHit)
            res += "Master-crafted ";
        switch (ID) {
            case 0: {
                res += "Assault Bolter ";
                break;
            }
            case 1: {
                res += "Incinerator ";
                break;
            }
            case 2: {
                res += "Psilencer ";
                break;
            }
            case 3: {
                res += "Psycannon ";
                break;
            }
        }

        return res;
    }

    public String getID() {
        return Utils.toBinStr(ID, 2);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RangedWeapon)
            return ID == ((RangedWeapon) obj).ID;
        return false;
    }
}
