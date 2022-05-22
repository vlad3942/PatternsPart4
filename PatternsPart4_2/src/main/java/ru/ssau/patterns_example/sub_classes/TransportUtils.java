package ru.ssau.patterns_example.sub_classes;

public final class TransportUtils {
    public static Auto initAuto() {
        final Auto auto = new Auto("BMW", 0);
        try {
            auto.addModel("X5", 6.04);
            auto.addModel("Series 5", 4.25);
            auto.addModel("X3", 4.52);
        } catch (DuplicateModelNameException e) {
            e.printStackTrace();
        }
        return auto;
    }

    public static Moto initMoto() {
        final Moto moto = new Moto("Yamaha", 0);
        try {
            moto.addModel("MT-03", 0.519);
            moto.addModel("MT-07", 0.78);
            moto.addModel("MT-10 SP", 1.617);
        } catch (DuplicateModelNameException e) {
            e.printStackTrace();
        }
        return moto;
    }
}
