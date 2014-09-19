package es.luismars;
/**
 * Created by Luis on 11/09/2014.
 */
public class CCWeapon {

    int ID;
    Stats stats;
    int cost = 0;

    public CCWeapon(int WP) {
        ID = Math.min(4, WP);
        updateStats();
    }


    public void updateStats() {
        stats.set(stats.AP,3);

        switch (ID) {
            case 0: {
                break;
            }
            case 1: {
                stats.set(stats.S, 1);
                cost += 2;
                break;
            }
            case 2: {
            	stats.set(stats.A, 1);
                cost +=4;
                break;
            }
            case 3: {
            	stats.set(stats.S, 2);
            	stats.set(stats.AP, 4);
                cost += 5;
                break;
            }

            case 4: {
            	stats.set(stats.S, -1);
            	stats.set(stats.I, 1);
            	stats.set(stats.AP, 2);
                cost += 10;
                break;
            }
        }
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        switch (ID) {
            case 0: return "Sword";
            case 1: return "Halberd";
            case 2: return "Falchions";
            case 3: return "Stave";
            case 4: return "Hammer";
            default: return "Invalid weapon";
        }
    }


}
