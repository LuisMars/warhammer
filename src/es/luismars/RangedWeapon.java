package es.luismars;
/**
 * Created by Luis on 14/09/2014.
 */
public class RangedWeapon extends SpecialRules {

    int ID;
    int cost;
    int SHOTS;
    int RANGE;
    int S;
    int AP;

    public RangedWeapon(int id) {
        ID = id;
        updateStats();
    }

    public void updateStats() {
        switch (ID) {
            //Assault bolter
            case 0: {
                SHOTS = 2;
                RANGE = 24;
                S = 4;
                AP = 4;
                cost = 0;
                break;
            }
            //Incinerator
            case 1: {
                template = true;
                SHOTS = 1; //Average result
                RANGE = 6;
                S = 6;
                AP = 4;
                cost = 5;
                break;
            }
            //Psilencer
            case 2: {
                force = true;
                heavy = true;
                SHOTS = 6;
                RANGE = 24;
                S = 4;
                AP = 7;
                cost = 10;
                break;
            }
            //Psycanon
            case 3: {
                salvo = true;
                SHOTS = 4;
                RANGE = 24;
                S = 7;
                AP = 4;
                cost = 15;
                break;
            }
            //Heavy incinerator
            case 4: {
                template = true;
                SHOTS = 1;
                RANGE = 14;
                S = 6;
                AP = 4;
                cost = 20;
                break;
            }
            //Gatling Psilencer
            case 5: {
                force = true;
                SHOTS = 12;
                RANGE = 24;
                S = 4;
                AP = 7;
                cost = 30;
                break;
            }
            //Heavy Psycanon
            case 6: {
                //TODO: add area
                SHOTS = 6;
                RANGE = 24;
                S = 7;
                AP = 4;
                cost = 35;
                break;
            }
            //Nothing
            case 7: {
                SHOTS = 0;
                RANGE = 0;
                S = 0;
                AP = 7;
                cost = 0;
                break;
            }
            //Bolt pistol
            case 8: {
                SHOTS = 1;
                RANGE = 12;
                S = 4;
                AP = 5;
                cost = 0;
                break;
            }
            //bolt gun
            //TODO: Add rapid fire;
            case 9: {
                SHOTS = 1;
                RANGE = 24;
                S = 4;
                AP = 5;
                cost = 0;
                break;
            }
        }
    }

    public double shoot(Stats att, Stats def, boolean moved, int distance, int size, boolean overwatch) {

        double wounds = 0;
        double shoot = overwatch ? 0.16 : Rules.shootRoll(att.get(Stats.BS));
        double wound = Rules.woundRoll(S, def.get(Stats.T));
        double save = Rules.armorSave(AP, def.get(Stats.AS));
        double spSave = Rules.specialSave(def.get(Stats.SS));
        double s = shoot;
        double w = wound;
        double range = RANGE;
        double shots = SHOTS;


        if (moved && att.shootHeavy) {
            if (heavy)
                shots = 0;
            else if (salvo) {
                range /= 2;
                shots /= 2;
            }
        }

        if (range >= distance) {
            if (template) {
                wounds = shoot * wound * save * spSave;
                if (size > 1)
                    wounds *= Math.min(size, 4);
                return wounds;
            }

            for (int i = 0; i < shots; i++) {

                if (rerollOneHit)
                    shoot = Rules.reroll1ofN(s, i);
                else if (rerollHit)
                    shoot = Rules.reroll(s);

                if (rerollOneWound)
                    wound = Rules.reroll1ofN(w, i);
                else
                    wound = Rules.reroll(w);

                if (rending)
                    wounds += shoot * Rules.rending(wound, save * spSave);
                else if (force || distort)
                    wounds += shoot * Rules.force(wound, save * spSave, def.get(Stats.W));
                else
                    wounds += shoot * wound * save * spSave;
            }
        }

        return wounds;
    }

    public int getCost() {
        return cost;
    }

    public String toString() {
        String res = "";
        if (rerollOneHit)
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
            case 8: {
                res += "Bolt pistol";
                break;
            }
            case 9: {
                res += "Bolt gun";
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
