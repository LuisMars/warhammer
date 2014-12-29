package es.luismars;


public class CCWeapon extends SpecialRules {

    int ID;

    int S;
    int A;
    int AP;

    int I;


    int cost = 0;

    public CCWeapon(Stats s, int WP) {
        ID = WP;
        updateStats(s);
    }
    public CCWeapon(int WP) {
        ID = WP;
    }

    public void updateStats(Stats s) {

        switch (ID) {
            //Nemesis Sword
            case 0: {
                S = s.get(Stats.S);
                A = s.get(Stats.A);
                AP = 3;
                I = s.get(Stats.I);

                force = true;
                break;
            }
            //Halberd
            case 1: {
                S = s.get(Stats.S) + 1;
                A = s.get(Stats.A);
                AP = 3;
                I = s.get(Stats.I);
                force = true;
                cost += 2;
                break;
            }
            //Falchions
            case 2: {
                S = s.get(Stats.S);
                A = s.get(Stats.A) + 1;
                AP = 3;
                I = s.get(Stats.I);
                force = true;
                cost +=4;
                break;
            }
            //Stave
            case 3: {

                S = s.get(Stats.S) + 2;
                A = s.get(Stats.A);
                AP = 4;
                I = s.get(Stats.I);

                force = true;
                cost += 5;
                break;
            }
            //Hammer
            case 4: {
                S = s.get(Stats.S) * 2;
                A = s.get(Stats.A);
                AP = 2;
                I = 1;

                force = true;
                concussive = true;
                cost += 10;
                break;
            }
            //Great Sword
            case 5: {

                S = s.get(Stats.S) * 2;
                A = s.get(Stats.A);
                AP = 2;
                I = s.get(Stats.I);

                force = true;
                rerollOneHit = true;
                cost += 10;
                break;
            }
            //Power fists
            case 6: {
                S = s.get(Stats.S) * 2;
                A = s.get(Stats.A);
                AP = 2;
                I = s.get(Stats.I);
                unwieldy = true;
                specialist = true;
                break;
            }
            //Lightning claw
            case 7: {
                S = s.get(Stats.S);
                A = s.get(Stats.A);
                AP = 3;
                I = s.get(Stats.I);
                specialist = true;
                break;
            }
            //Chainaxe
            case 8: {
                S = s.get(Stats.S);
                A = s.get(Stats.A);
                AP = 4;
                I = s.get(Stats.I);
                break;
            }
            //None
            case 9: {
                S = s.get(Stats.S);
                A = s.get(Stats.A);
                AP = 7;
                I = s.get(Stats.I);
            }

        }

        S = Math.min(S, 10);

    }

    public double attack(Stats att, Stats def, boolean assault, boolean rage) {

        double wounds = 0;
        double hit = Rules.hitRoll(att.get(Stats.WS), def.get(Stats.WS));
        double wound = Rules.woundRoll(S, def.get(Stats.T));
        double save = Rules.armorSave(AP, def.get(Stats.AS));
        double spSave = Rules.specialSave(def.get(Stats.SS));
        double s = hit;
        double w = wound;

        int i = 0;
        if (assault)
            i--;
        if (rage)
            i--;

        for (; i < A; i++) {

            if (rerollOneHit)
                hit = Rules.reroll1ofN(s, i);
            else if (rerollHit)
                hit = Rules.reroll(s);

            if (rerollOneWound)
                wound = Rules.reroll1ofN(w, i);
            else
                wound = Rules.reroll(w);

            if (rending)
                wounds += hit * Rules.rending(wound, save * spSave);
            else if (force || distort)
                wounds += hit * Rules.force(wound, save * spSave, def.get(Stats.W));
            else
                wounds += hit * wound * save * spSave;
        }


        return wounds;
    }


    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        String res = "";
        if (rerollOneHit)
            res += "Master-crafted ";

        switch (ID) {
            case 0: {
                res += "Sword";
                break;
            }
            case 1: {
                res += "Halberd";
                break;
            }
            case 2: {
                res += "Falchions";
                break;
            }
            case 3: {
                res += "Stave";
                break;
            }
            case 4: {
                res += "Daemon Hammer";
                break;
            }
            case 5: {
                res += "Great Sword";
                break;
            }
            case 6: {
                res += "Power fists";
                break;
            }
            case 7: {
                res += "Lightning Claw";
                break;
            }
            case 8: {
                res += "Chainaxe";
                break;
            }
            case 9: {
                res += "Close Combat Weapon";
                break;
            }
            default:
                res += "Invalid weapon";
        }

        return res;
    }

    public String getID() {
        return Utils.toBinStr(ID, 3);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CCWeapon && ID == ((CCWeapon) obj).ID;
    }
}
