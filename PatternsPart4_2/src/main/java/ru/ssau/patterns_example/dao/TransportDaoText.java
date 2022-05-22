package ru.ssau.patterns_example.dao;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.TestClass;
import ru.ssau.patterns_example.sub_classes.Transport;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class TransportDaoText<T extends Transport> implements TransportDao<T> {

    public static final String filePath = "transport.txt";

    private final Class<T> typeClass;

    public TransportDaoText(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public T readTransport() throws Exception {
        T t = typeClass.getConstructor(String.class, int.class).newInstance("", 0);
        if (!Files.exists(Path.of(filePath))) {
            Files.createFile(Path.of(filePath));
        }
        TestClass.readMovable(new FileReader(filePath), t);
        return t;
    }

    @Override
    public void writeTransport(T transport) throws IOException {
        TestClass.writeMovable(transport, new FileWriter(filePath));
    }
}
