package ru.ssau.patterns_example.dao;

import ru.ssau.patterns_example.sub_classes.Auto;
import ru.ssau.patterns_example.sub_classes.Moto;
import ru.ssau.patterns_example.sub_classes.TestClass;
import ru.ssau.patterns_example.sub_classes.TransportUtils;

public class MainDao {
    public static void main(String[] args) {

        final Auto auto = TransportUtils.initAuto();
        System.out.println("Printing auto data...");
        TestClass.writeMovable(auto);
        final TransportDao<Auto> dao = TransportDaoFactory.getDao(TransportDaoFactory.DaoType.TEXT, Auto.class);
        System.out.println("<----------TEXT FILE DAO---------->");
        try {
            System.out.println("Writing file...");
            dao.writeTransport(auto);
            System.out.println("File was writing.");
            final Auto readAuto = dao.readTransport();
            TestClass.writeMovable(readAuto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<--------------------------------->");
        final Moto moto = TransportUtils.initMoto();
        System.out.println("Printing moto data...");
        TestClass.writeMovable(moto);
        final TransportDao<Moto> daoMoto = TransportDaoFactory.getDao(TransportDaoFactory.DaoType.SERIALIZE, Moto.class);
        System.out.println("<-------SERIALIZE FILE DAO-------->");
        try {
            System.out.println("Writing file...");
            daoMoto.writeTransport(moto);
            System.out.println("File was writing.");
            final Moto readMoto = daoMoto.readTransport();
            TestClass.writeMovable(readMoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<--------------------------------->");
    }
}
