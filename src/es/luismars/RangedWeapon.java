package es.luismars;
/**
 * Created by Luis on 14/09/2014.
 */
public class RangedWeapon {

    int ID;
    int cost;
    SpecialRules spr = new SpecialRules();

    public RangedWeapon(int id) {
        ID = id;
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
                stats.set(Stats.STR, 8);
                stats.set(Stats.APW, 4);
                cost = 5;
                break;
            }
            //Psilencer
            case 2: {
                spr.force = true;
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
            //Heavy incinerator
            case 4: {
                spr.template = true;
                stats.set(Stats.SHOTS, 2); //Average result
                stats.set(Stats.RANGE, 14);
                stats.set(Stats.STR, 6);
                stats.set(Stats.APW, 4);
                cost = 20;
                break;
            }
            //Gatling Psilencer
            case 5: {
                spr.force = true;
                stats.set(Stats.SHOTS, 12);
                stats.set(Stats.RANGE, 24);
                stats.set(Stats.STR, 4);
                stats.set(Stats.APW, 7);
                cost = 30;
                break;
            }
            //Heavy Psycanon
            case 6: {
                //TODO: add area
                stats.set(Stats.SHOTS, 6);
                stats.set(Stats.RANGE, 24);
                stats.set(Stats.STR, 7);
                stats.set(Stats.APW, 4);
                cost = 35;
                break;
            }
            //Nothing
            case 7: {
                stats.set(Stats.SHOTS, 0);
                stats.set(Stats.RANGE, 0);
                stats.set(Stats.STR, 0);
                stats.set(Stats.APW, 0);
                cost = 0;
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
            case 4: {
                res += "Heavy Incinerator ";
                break;
            }
            case 5: {
                res += "Gatling Psilencer ";
                break;
            }
            case 6: {
                res += "Heavy Psycannon";
                break;
            }
        }

        return res;
    }

    public String getID() {
        return Utils.toBinStr(ID, 2);
    }

    public boolean exists() {
        return ID != 7;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RangedWeapon)
            return ID == ((RangedWeapon) obj).ID;
        return false;
    }
}
