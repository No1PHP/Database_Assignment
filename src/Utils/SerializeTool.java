package Utils;

import java.io.*;

public abstract class SerializeTool {
    public static <T extends Serializable> byte[] serialize(T obj) throws IOException {
        byte[] result = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(obj);
            result = byteArrayOutputStream.toByteArray();
            out.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            throw e;
        }
        return result;
    }

    public static <T extends Serializable> T deserialize(byte[] data) throws IOException, ClassNotFoundException {
        T result = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            result = (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw e;
        }
        return result;
    }
}
