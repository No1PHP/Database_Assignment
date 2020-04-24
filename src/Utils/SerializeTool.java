package Utils;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class SerializeTool {
    /**
     *
     * @param obj object to be serialized
     * @param <T> object type that implements Serializable interface
     * @return the serialized byte stream
     * @throws IOException failed to serialize
     */
    public static <T extends Serializable> byte[] serialize(T obj) throws IOException {
        Collection<T> objs = new LinkedList<T>();
        objs.add(obj);
        return serialize(objs)[0];
    }

    /**
     *
     * @param objs collection of objects to be serialized
     * @param <T> object type that implements Serializable interface
     * @return the serialized byte stream
     * @throws IOException failed to serialize
     */
    public static <T extends Serializable> byte[][] serialize(Collection<T> objs) throws IOException {
        byte[][] result = new byte[objs.size()][];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
        int count = 0;
        for (T obj : objs) {
            out.writeObject(obj);
            result[count++] = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
        }
        out.close();
        byteArrayOutputStream.close();
        return result;
    }

    /**
     *
     * @param data byte stream to be deserialize
     * @param <T> object type that implements Serializable interface
     * @return the list of objects
     * @throws IOException failed to deserialize
     * @throws ClassNotFoundException cannot find the corresponding class
     */
    public static <T extends Serializable> List<T> deserialize(byte[] data) throws IOException, ClassNotFoundException {
        List<T> list = new LinkedList<T>();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        while (objectInputStream.available() != 0) {
            list.add((T) objectInputStream.readObject());
        }
        objectInputStream.close();
        byteArrayInputStream.close();
        return list;
    }
}
