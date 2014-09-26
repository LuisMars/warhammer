package es.luismars;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {

/*
        TerminatorSquad t = new TerminatorSquad(133136);
        Combat c = new Combat(t);
        c.assault();
        System.out.println(c  + "\n" + t);
*/
        //stuff();
        combat("new_no_dups.dat", "testcombat.dat");
        top20("testcombat.dat");

        //generate("new_no_dups.dat");


    }

    public static void top20(String file) {
        List<Results> results = new ArrayList<Results>();

        try {
            //FileOutputStream fos = new FileOutputStream("vsStdTerm.cc");
            //ObjectOutputStream oos = new ObjectOutputStream(fos);


            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);


            System.out.println("Processing...");
            while(true) {

                int ID = ois.readInt();
                int cost = ois.readInt();
                double wounds = ois.readDouble();
                double lost = ois.readDouble();
                int turn = ois.readInt();
                double catching = ois.readDouble();
                double retreat = ois.readDouble();
                //boolean hamerhand = ois.readBoolean();

                results.add(new Results(ID, cost, wounds, lost, turn, catching, retreat));

            }


        } catch (IOException e) {
            System.out.println("End of file");

        }
        System.out.println("Sorting...\n");
        Collections.sort(results);


        int n = 0;
        for (int i = results.size() - 1; i > 0 && n < 10; i--) {
            TerminatorSquad t = new TerminatorSquad(results.get(i).ID);
            if (true) {
                System.out.println(results.get(i));
                n++;
            }

        }

    }

    public static void combat(String in, String out) {

        try {
            System.out.println("Reading data...");
            FileOutputStream fos = new FileOutputStream(out);
            ObjectOutputStream oos = new ObjectOutputStream(fos);


            FileInputStream fis = new FileInputStream(in);
            ObjectInputStream ois = new ObjectInputStream(fis);



            while(true) {
                TerminatorSquad t = new TerminatorSquad(ois.readInt());

                Combat c = new Combat(t);
                c.assault();
                oos.writeInt(t.getID());
                oos.writeInt(t.getCost());
                oos.writeDouble(c.a.wounds);
                oos.writeDouble(c.a.lost);
                oos.writeInt(c.turn);
                oos.writeDouble(c.a.catching);
                oos.writeDouble(c.d.catching);
                //oos.writeBoolean(false);

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

            for (int i = 131072; i <= 169875; i++) {
                TerminatorSquad t = new TerminatorSquad(i);
                if (list.add(t.getID2()))
                    oos.writeInt(t.getID2());
            }

            for (int i = 50331648; i <= 60265363; i++) {
                TerminatorSquad t = new TerminatorSquad(i);
                if (list.add(t.getID2()))
                    oos.writeInt(t.getID2());
            }
            oos.close();
            System.out.println(list.size());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
