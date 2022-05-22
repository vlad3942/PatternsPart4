package ru.ssau.patterns_example.dao;

import ru.ssau.patterns_example.sub_classes.Transport;

public class TransportDaoFactory {

    public static <T extends Transport> TransportDao<T> getDao(DaoType type, Class<T> clazz) {
        switch (type) {
            case TEXT: return new TransportDaoText<>(clazz);
            case SERIALIZE: return new TransportDaoSerialize<>(clazz);
        }
        throw new IllegalStateException("Unsupported DAO type error.");
    }

    public enum DaoType {
        TEXT, SERIALIZE
    }
}
