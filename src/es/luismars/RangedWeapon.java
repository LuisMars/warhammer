package es.luismars;
/**
 * Created by Luis on 14/09/2014.
 */
public class RangedWeapon {

    int ID;
    int shots;
    int range;
    int S;
    int AP;


    public RangedWeapon () {
        ID = 0;
    }

    public RangedWeapon (int id) {
        ID = id;
    }

    public void updateStats() {
        switch (ID) {
            case 0: {
                shots = 2;
                range = 24;
                S = 4;
                AP = 4;
                break;
            }
            case 1: {
                shots = 6;
                range = 6;
                S = 6;
                AP = 4;
                break;
            }
            case 2: {
                shots = 6;
                range = 24;
                S = 4;
                AP = 7;
                break;
            }
            case 3: {
                shots = 2;
                range = 24;
                S = 7;
                AP = 4;
                break;
            }
        }
    }

}
