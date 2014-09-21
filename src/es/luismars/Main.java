package es.luismars;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {


        TerminatorSquad t = new TerminatorSquad(60265363);
        Combat c = new Combat(t);
        c.assault();
        System.out.println(c);

        //stuff();
        //combat("new_no_dups.dat","testcombat.dat");
        //top20("testcombat.dat");

        //generate("new_no_dups.dat");


    }

    public static void top20(String file) {
        List<Results> results = new ArrayList<Results>();

        try {
            //FileOutputStream fos = new FileOutputStream("vsStdTerm.cc");
            //ObjectOutputStream oos = new ObjectOutputStream(fos);


            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);


            System.out.println("Reading...");
            while(true) {

                int ID = ois.readInt();
                int cost = ois.readInt();
                double assault = ois.readDouble();
                double lost = ois.readDouble();
                //boolean hamerhand = ois.readBoolean();
                results.add(new Results(ID, cost, assault, lost));

            }


        } catch (IOException e) {
            System.out.println("End of file");

        }
        System.out.println("Sorting...");
        Collections.sort(results);
        System.out.println("Writing...");


        int n = 0;
        for (int i = results.size() - 1; i > 0 && n < 20; i--) {

            TerminatorSquad t = new TerminatorSquad(results.get(i).ID);
            System.out.println(results.get(i));
            n++;

        }

    }

    public static void combat(String in, String out) {
        int p = 0;
        int n = 0;
        Set list = new HashSet();
        List<Integer> list2= new ArrayList<Integer>();

        try {
            System.out.println("Reading data...");
            FileOutputStream fos = new FileOutputStream(out);
            ObjectOutputStream oos = new ObjectOutputStream(fos);


            FileInputStream fis = new FileInputStream(in);
            ObjectInputStream ois = new ObjectInputStream(fis);



            while(true) {
                p++;

                TerminatorSquad t = new TerminatorSquad(ois.readInt());

                Combat c = new Combat(t);

                oos.writeInt(t.getID());
                oos.writeInt(t.getCost());
                oos.writeDouble(c.a.wounds);
                oos.writeDouble(c.a.lost);
                //oos.writeBoolean(false);

            }


        } catch (IOException e) {
            System.out.println("End of file");
        }
        System.out.println(list2.get(Integer.parseInt("111001001110010011",2)));

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
