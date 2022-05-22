package ru.ssau.patterns_example.dao;

import ru.ssau.patterns_example.sub_classes.TestClass;
import ru.ssau.patterns_example.sub_classes.Transport;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class TransportDaoSerialize<T extends Transport> implements TransportDao<T> {

    private static final String filePath = "sTransport.txt";

    private final Class<T> typeClass;

    public TransportDaoSerialize(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public T readTransport() throws Exception {
        if (!Files.exists(Path.of(filePath))) {
            Files.createFile(Path.of(filePath));
        }
        final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
        T tr = (T) ois.readObject();
        return tr;
    }

    @Override
    public void writeTransport(T transport) throws IOException {
        if (!Files.exists(Path.of(filePath))) {
            Files.createFile(Path.of(filePath));
        }
        final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        oos.writeObject(transport);
    }
}
