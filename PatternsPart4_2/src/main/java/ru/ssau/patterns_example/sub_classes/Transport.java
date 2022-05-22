package ru.ssau.patterns_example.sub_classes;

public interface Transport {

    String getMark();

    void setMark(String mark);

    String[] getModelsNames();

    double getCostOfCurrentModel(String name) throws NoSuchModelNameException;

    void setCostOfCurrentModel(String name, double cost) throws NoSuchModelNameException;

    double[] getArrayOfModelsCosts();

    void addModel(String name, double cost) throws DuplicateModelNameException;

    void delModel(String name) throws NoSuchModelNameException;

    int getModelsLength();

    void setModelName(String name, String name1) throws NoSuchModelNameException, DuplicateModelNameException;

}
