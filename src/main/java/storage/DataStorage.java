package storage;

import java.io.*;

public class DataStorage {
    private static DataStorage instance;

    private DataStorage() {
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void saveObject(String fileName, Object object) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(object);
        out.close();
    }

    public Object readObject(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        Object object = in.readObject();
        in.close();
        return object;
    }
}
