package ru.ssau.patterns_example.sub_classes;

public class DuplicateModelNameException extends  Exception {
    public String attrName;
    public DuplicateModelNameException(String name) {
        super("Атрибут с именем \"" + name + "\" уже существует.");
        attrName = name;
    }
}
