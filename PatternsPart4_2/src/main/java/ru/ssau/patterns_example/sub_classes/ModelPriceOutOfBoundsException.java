package ru.ssau.patterns_example.sub_classes;

public class ModelPriceOutOfBoundsException extends RuntimeException {
    public String attrName;
    public ModelPriceOutOfBoundsException(String name) {
        super("Цена для \"" + name + "\" задана неверно.");
        attrName = name;
    }
}
