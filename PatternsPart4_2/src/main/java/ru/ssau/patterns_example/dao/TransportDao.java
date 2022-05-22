package ru.ssau.patterns_example.dao;

import ru.ssau.patterns_example.sub_classes.Transport;

import java.io.IOException;

public interface TransportDao<T extends Transport> {
    T readTransport() throws Exception;
    void writeTransport(T transport) throws IOException;
}
