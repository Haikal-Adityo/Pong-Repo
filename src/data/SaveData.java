package data;

import main.History;

import java.io.*;
import java.util.Arrays;

public class SaveData {
    public DataStorage ds;

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));

            ds = new DataStorage();
            ds.winnerList = History.winnerList;
            ds.count = History.count;

            oos.writeObject(ds);
            oos.close();
        } catch (Exception e) {
            System.out.println("Save Exception: " + e.getMessage());
        }
    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            DataStorage ds = (DataStorage) ois.readObject();
            ois.close();

            History.winnerList = ds.winnerList;
            History.count = ds.count;

            System.out.println("===========================");
            System.out.println(Arrays.toString(History.winnerList)); //! FOR TESTING
            System.out.println(ds); //! FOR TESTING
            for (String txt : ds.winnerList) {
                System.out.println(txt);
            }
            System.out.println("===========================");

        } catch (Exception e) {
            System.out.println("Load Exception: " + e.getMessage());
        }
    }


}
