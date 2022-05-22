package ru.ssau.patterns_example.sub_classes;

public class NoSuchModelNameException extends  Exception{
    public String attrName;
    public NoSuchModelNameException(String name) {
        super("Атрибут с именем \"" + name + "\" не найден.");
        attrName = name;
    }
}
