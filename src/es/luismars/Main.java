package es.luismars;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
/*
        Long ID = 3569648784l;*/
        //TermSquad b = new TermSquad("1101001011000010010000100100100");
        //System.out.println(" fdfdfd  ");
        /*Combat c = new Combat(b);
        Results r = new Results(ID, b.totalcost, c.assault(), c.normal(), c.lost);
        System.out.println(r);
        */
        //stuff();
        //combat();
        //top20("vsStdTerm.cc");


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
                double normal = ois.readDouble();
                double lost = ois.readDouble();
                //boolean hamerhand = ois.readBoolean();
                results.add(new Results(ID, cost, assault, normal, lost));

            }


        } catch (IOException e) {
            System.out.println("End of file");

        }
        System.out.println("Sorting...");
        Collections.sort(results);
        System.out.println("Writing...");


        try {
            PrintWriter pw = new PrintWriter("results.txt");

            int n = 0;
            for (int i = results.size() - 1; i > 0 && n < 20; i--) {

                TermSquad t = new TermSquad(results.get(i).ID);
                if (t.size == 5 && t.specialA.rw.ID != 0 /*&& t.specialB.rw.ID != 0*/) {
                    System.out.println(results.get(i));
                    n++;
                }
            }
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void combat() {
        int p = 0;
        int n = 0;
        Set list = new HashSet();
        List<Integer> list2= new ArrayList<Integer>();

        try {
            System.out.println("Reading data...");
            //FileOutputStream fos = new FileOutputStream("results2.dat");
            //ObjectOutputStream oos = new ObjectOutputStream(fos);


            FileInputStream fis = new FileInputStream("squad.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);


            System.out.println("Writing data...");


            while(true) {
                p++;
/*
                TermSquad t = new TermSquad(Long.toBinaryString(ois.readLong()));

                if(t.size == 10) n++;



                if(list.add(t.getID()))
                    oos.writeInt(t.getID());
*/
                int i = ois.readInt();
                list2.add(i);
                //System.out.println(i + " " + Integer.toBinaryString(i));

                /*
                Combat c = new Combat(t);

                oos.writeLong(t.getID());
                oos.writeInt(t.totalcost);
                oos.writeDouble(c.assault());
                oos.writeDouble(c.normal());
                oos.writeDouble(c.lost);
                //oos.writeBoolean(false);
                */
            }


        } catch (IOException e) {
            System.out.println("End of file " + n + " + " + (p - n) + " = " + p);
        }
        System.out.println(list2.get(Integer.parseInt("111001001110010011",2)));

    }

    public static void stuff() {

        //Test window = new Test();

        int max = 62914560;
        int min = 131072;

        System.out.println(max + " " + min);

        Set list = new HashSet();

        long t = System.currentTimeMillis();

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("squad.dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        double total = 480;
        for (int i = min; i <= max; i++) {
            TermSquad ts = new TermSquad(i);
            int s = ts.getID();
            if(i%500000 == 0) {
                double se = (System.currentTimeMillis()-t)/1000;
                double done = (i-min)/1000000;
                double perc = done/total;
                double totalTime = (1.0/perc) * se;
                int sec = (int)(totalTime-se);
                System.out.println((int)(perc*10000)/100 + "% Done     Remaining:" + sec/3600 + "h " + (sec%3600)/60 + "m "  + sec%60 + "s");

            }
            if(list.add(s)) {
                try {
                    //System.out.print(ts);
                    oos.writeInt(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }



        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        double t = System.currentTimeMillis();
        ObjectInputStream ois;
        List<Long> readlist = new ArrayList<Long>();
        try {

            FileInputStream fis = new FileInputStream("terminators.dat");
            ois = new ObjectInputStream(fis);


            while(true) {
                readlist.add(ois.readLong());
            }



        }
        catch (EOFException e) {
            //System.out.println(readlist.size());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis()-t);
        t = System.currentTimeMillis();

        FileOutputStream f;

        try {
            f = new FileOutputStream("termilist.long");
            oos = new ObjectOutputStream(f);

            oos.writeObject(readlist);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //ObjectInputStream ois;
        //List<Long> readlist = new ArrayList<Long>();

        /*
        try {

            FileInputStream fis = new FileInputStream("termilist.long");
            ois = new ObjectInputStream(fis);


            readlist = (List<Long>)ois.readObject();

        }
        catch (EOFException e) {
            System.out.println(readlist.size());
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis()-t);
        readlist.get(15560);*/




/*
        TermSquad test = new TermSquad("1110011100100111001110011100");
        System.out.println(test);
*/


        /*
        test = new TermSquad(Long.toBinaryString(readlist.get(6550166)));
        System.out.println(test);
        test = new TermSquad(Long.toBinaryString(readlist.get(5175560)));
        System.out.println(test);
        */
    }
}
