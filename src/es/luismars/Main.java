package es.luismars;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Toolkit.getDefaultToolkit().beep();
        long iniTime = System.currentTimeMillis();

        GreyKnightSquad a = new GreyKnightSquad("DreadKnight", 218);

        GreyKnightSquad d = new GreyKnightSquad("Terminator", 59540352);
/*
        Combat c = new Combat(a,d);
        c.assault();
        System.out.println(c  + "\n" + a + "\n" + d);
*/

        //combat("DreadKnight", a, "dreadknight.dat", "testcombat.dat");
        combat("Strike", a, "defaultGKsquad.dat", "testcombat.dat");
        top20("testcombat.dat");

        //generate("dreadknight.dat");
        System.out.println("Completed in " + Utils.time(System.currentTimeMillis() - iniTime));

        Toolkit.getDefaultToolkit().beep();
    }

    public static void top20(String file) {
        List<Results> results = new ArrayList<Results>();
        String type = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            type = ois.readUTF();
            int enemySize = ois.readInt();
            System.out.println("Processing...");
            while(true) {
                int size = ois.readInt();
                int ID = ois.readInt();
                int cost = ois.readInt();

                double wounds = ois.readDouble();
                double lost = ois.readDouble();

                int turn = ois.readInt();

                double defRetreats = ois.readDouble();
                double attRetreats = ois.readDouble();

                double attSweeps = ois.readDouble();
                double defSweeps = ois.readDouble();

                results.add(new Results(type, ID, size, cost, wounds, lost, turn, defRetreats, attRetreats, attSweeps, defSweeps, enemySize));

            }


        } catch (IOException e) {
            System.out.println("End of file");

        }
        System.out.println("Sorting...\n");
        Collections.sort(results);


        int n = 0;
        for (int i = results.size() - 1; i > 0 && n < 10; i--) {
            GreyKnightSquad t = new GreyKnightSquad(type, results.get(i).ID);
            if (!t.squad[0].spr.mBombs && !t.squad[0].spr.TPH) {
                System.out.println(results.get(i));
                n++;
            }

        }

    }

    public static void combat(String type, Squad enemy, String in, String out) {

        try {
            System.out.println("Reading data...");
            FileOutputStream fos = new FileOutputStream(out);
            ObjectOutputStream oos = new ObjectOutputStream(fos);


            FileInputStream fis = new FileInputStream(in);
            ObjectInputStream ois = new ObjectInputStream(fis);


            oos.writeUTF(type);
            oos.writeInt(enemy.getWoundSize());

            while(true) {
                GreyKnightSquad t = new GreyKnightSquad(type, ois.readInt());

                oos.writeInt(t.getWoundSize());
                oos.writeInt(t.getID());
                oos.writeInt(t.getCost());

                Combat c = new Combat(t, enemy);
                c.assault();

                oos.writeDouble(c.a.wounds);
                oos.writeDouble(c.a.lost);

                oos.writeInt(c.turn);

                oos.writeDouble(c.a.defRetreats);
                oos.writeDouble(c.d.defRetreats);

                oos.writeDouble(c.a.canSweep);
                oos.writeDouble(c.d.canSweep);
                enemy.reset();
            }


        } catch (IOException e) {
            System.out.println("End of file");
        }

    }

    public static void generate(String file) {
        // min5: 131072, max5: 169875, min10:50331648 , max10:60265363
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            Set list = new HashSet();
            for (int i = 0; i <= 100000; i++) {
                GreyKnightSquad t = new GreyKnightSquad("Dreadknight", i);
                if (list.add(t.getID())) {
                    oos.writeInt(t.getID());
                    //System.out.println(t.getID());
                }
            }

/*
            for (int i = 50331648; i <= 60265363; i++) {
                GreyKnightSquad t = new GreyKnightSquad("T", i);
                if (list.add(t.getID()))
                    oos.writeInt(t.getID());
            }
*/
            oos.close();
            System.out.println(list.size());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void army() {

        /*
        1 -
        Troops: Max 100 000
        Elites Max 11 00 00
        Fast Attack Max 10 00 00
        Heavy Support Max 11 11 11
         */
    }
}