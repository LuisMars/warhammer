package es.luismars;

/**
 * Created by luis on 27/12/14.
 */
public class ChaosSquad extends Squad {

    public ChaosSquad(String type) {
        switch (type.charAt(0)) {
            case 'B': {
                TYPE = type;
                size = 10;
                squad = new Stats[size];

                squad[0] = new Stats(new int[]{5, 4, 4, 4, 1, 4, 3, 9, 3, 7, 0, 0, 0, 0, 0, 6});
                squad[0].ccw.add(new CCWeapon(squad[0], 6));
                squad[0].ccw.add(new CCWeapon(squad[0], 7));
                squad[0].rw.add(new RangedWeapon(9));
                squad[0].updateStats();
                for (int i = 1; i < squad.length; i++) {
                    squad[i] = new Stats(new int[]{5, 4, 4, 4, 1, 4, 3, 9, 3, 7, 0, 0, 0, 0, 0, 6});
                    squad[i].ccw.add(new CCWeapon(squad[i], 8));
                    squad[i].rw.add(new RangedWeapon(8));
                    squad[i].updateStats();
                }
                setWoundSize();
                spr.counterAttack = true;
                spr.rage = true;
                break;
            }
            case 'C': {
                TYPE = type;
                size = 30;
                squad = new Stats[size];

                squad[0] = new Stats(new int[]{3, 3, 3, 3, 1, 3, 1, 8, 6, 7, 0, 0, 0, 0, 0, 6});
                squad[0].ccw.add(new CCWeapon(squad[0], 9));
                squad[0].rw.add(new RangedWeapon(7));
                squad[0].updateStats();
                for (int i = 1; i < squad.length; i++) {
                    squad[i] = new Stats(new int[]{3, 3, 3, 3, 1, 3, 1, 7, 6, 7, 0, 0, 0, 0, 0, 6});
                    squad[i].ccw.add(new CCWeapon(squad[i], 9));
                    squad[i].rw.add(new RangedWeapon(7));
                    squad[i].updateStats();
                }
                setWoundSize();
                break;
            }
            case 'D': {
                TYPE = type;
                size = 2;
                squad = new Stats[size];

                squad[0] = new Stats(new int[]{9, 5, 6, 7, 4, 8, 5, 10, 2, 2, 0, 0, 0, 0, 0, 12});
                squad[0].ccw.add(new CCWeapon(squad[0], 9));
                squad[0].updateStats();

                squad[1] = new Stats(new int[]{9, 5, 6, 7, 4, 8, 5, 10, 2, 2, 0, 0, 0, 0, 0, 12});
                squad[1].ccw.add(new CCWeapon(squad[1], 9));
                squad[1].updateStats();

                setWoundSize();
                break;
            }
            case 'M': {
                TYPE = type;
                size = 10;
                squad = new Stats[size];

                squad[0] = new Stats(new int[]{4, 4, 4, 5, 1, 4, 2, 9, 3, 7, 0, 0, 0, 0, 0, 6});
                squad[0].ccw.add(new CCWeapon(squad[0], 0));
                squad[0].rw.add(new RangedWeapon(9));
                squad[0].updateStats();
                for (int i = 1; i < squad.length; i++) {
                    squad[i] = new Stats(new int[]{4, 4, 4, 5, 1, 4, 1, 8, 3, 7, 0, 0, 0, 0, 0, 6});
                    squad[i].ccw.add(new CCWeapon(squad[i], 0));
                    squad[i].rw.add(new RangedWeapon(9));
                    squad[i].updateStats();
                }
                setWoundSize();
                spr.counterAttack = true;
                spr.rage = true;
                break;
            }
        }
    }

}
