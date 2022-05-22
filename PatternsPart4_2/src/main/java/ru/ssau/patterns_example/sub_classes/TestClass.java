package ru.ssau.patterns_example.sub_classes;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class TestClass {

    //Для 5 лабы
    public static Transport getMovable(String mark, int count, Transport ts) {
        try {
            Class c = ts.getClass();
            Constructor constructor = c.getConstructor(new Class[] {String.class, int.class});
            Transport res = (Transport) constructor.newInstance(mark, count);
            return res;
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static double average(Transport... mv) {
        double res = 0;
        double[] costs;
        int n = 0;
        for (int i = 0; i < mv.length; i++) {
            costs = mv[i].getArrayOfModelsCosts();
            n += mv[i].getModelsLength();
            for(int j = 0; j < mv[i].getModelsLength(); j++) {
                res += costs[j];
            }
        }
        return n == 0 ? 0 : res / n;
    }

    //Среднее арифметическое
    public static double getAverage(Transport mv) {
        double[] doubles = mv.getArrayOfModelsCosts();
        double res = 0;
        for(int i = 0; i < doubles.length; i++){
            res += doubles[i];
        }
        return res/doubles.length;
    }

    //Вывод моделей и цен для заданного транспортного средства
    public static void viewAllModels(Transport mv) {
        try {
            String[] names = mv.getModelsNames();
            for(int i = 0; i < names.length; i++) {
                System.out.println("Имя модели: " + names[i] + " Цена модели: " + mv.getCostOfCurrentModel(names[i]));
            }
        } catch (NoSuchModelNameException ex) {
            System.out.println("Странным образом поймали исключение - " + ex.getLocalizedMessage());
        }
    }
    //Тоже вывод но без Exceptions
    public static void viewAllModels2(Transport mv) {
        String[] names = mv.getModelsNames();
        double[] costs = mv.getArrayOfModelsCosts();
        for (int i = 0; i < costs.length; i++) {
            System.out.println("Имя модели: " + names[i] + " Цена модели: " + costs[i]);
        }
    }

    /**
     * Первый метод:
     * Реализует запись данных из классов, реализующих интерфейс Movable в виде
     * потока байтов в OutputStream
     *
     * @param v
     * @param out
     * @throws IOException
     */
    public static void outputMovable(Transport v, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] temp = v.getMark().getBytes();
        //Записываем int - длинну строки, используем перегрузку метода для int
        dos.writeInt(temp.length);
        //Записываем сторку как массив байтов
        for(int i = 0; i < temp.length; i++) {
            dos.writeByte(temp[i]);
        }
        int modelsLen = v.getModelsLength();
        dos.writeInt(modelsLen);
        String[] names = v.getModelsNames();
        double[] costs = v.getArrayOfModelsCosts();
        int len = 0;
        for(int i = 0; i < modelsLen; i++) {
            //dos.writeInt(names[i].length());
            temp = names[i].getBytes();
            len = temp.length;
            dos.writeInt(len);
            for (int j = 0; j < len; j++) {
                dos.writeByte(temp[j]);
            }
            //dos.write(names[i].getBytes());
            dos.writeDouble(costs[i]);
        }
    }

    /**
     * Возвращает объект, реализующий интерфес типа Movable, заполненного
     * данными считанными из заданного потока, и тип которого определятеся
     * занчением параметра isAuto.
     * @param in
     * @param isAuto
     * @return
     * @throws IOException
     * @throws DuplicateModelNameException
     */
    public static Transport inputMovable(InputStream in, boolean isAuto) throws IOException, DuplicateModelNameException {
        Transport mv = null;
        if(isAuto) {
            mv = new Auto("", 0);
        } else {
            mv = new Moto("", 0);
        }
        DataInputStream dis = new DataInputStream(in);
        int markNameLen = dis.readInt();
        byte[] temp = new byte[markNameLen];
        for(int i = 0; i < markNameLen; i++) {
            temp[i] = dis.readByte();
        }
        mv.setMark(new String(temp));
        int modelsLen = dis.readInt();
        int nameLen = 0;
        int i = 0;
        while(i < modelsLen && dis.available() > 0) {
            nameLen = dis.readInt();
            temp = new byte[nameLen];
            for(int j = 0; j < nameLen; j++) {
                temp[j] = dis.readByte();
            }
            mv.addModel(new String(temp), dis.readDouble());
            i++;
        }
        return mv;
    }

    public static void writeMovable(Transport v, Writer out) throws IOException {
        PrintWriter pw = new PrintWriter(out);
        pw.println(v.getMark());
        int len = v.getModelsLength();
        pw.println(len);
        String[] names = v.getModelsNames();
        double[] costs = v.getArrayOfModelsCosts();
        for(int i = 0; i < len; i++) {
            pw.println(names[i]);
            pw.println(costs[i]);
        }
        out.flush();
    }

    public static Transport readMovable(Reader in, boolean isAuto) throws IOException, DuplicateModelNameException {
        BufferedReader br = new BufferedReader(in);
        Transport mv = null;
        if(isAuto) {
            mv = new Auto("", 0);
        } else {
            mv = new Moto("", 0);
        }
        mv.setMark(br.readLine());
        int len = Integer.parseInt(br.readLine());
        for(int i = 0; i < len; i++) {
            mv.addModel(br.readLine(), Double.parseDouble(br.readLine()));
        }
        return mv;
    }

    public static <T extends Transport> Transport readMovable(Reader in, T transport) throws IOException, DuplicateModelNameException {
        if (transport == null) throw new IllegalArgumentException("Transport is not null");
        BufferedReader br = new BufferedReader(in);
        Transport mv = transport;
        mv.setMark(br.readLine());
        int len = Integer.parseInt(br.readLine());
        for(int i = 0; i < len; i++) {
            mv.addModel(br.readLine(), Double.parseDouble(br.readLine()));
        }
        return mv;
    }

    public static void writeMovable(Transport v)  {
        System.out.printf("Марка: %s\n", v.getMark());
        int len = v.getModelsLength();
        System.out.printf("Число моделей: %d\n", len);
        String[] names = v.getModelsNames();
        double[] costs = v.getArrayOfModelsCosts();
        for(int i = 0; i< len; i++) {
            System.out.printf("Модель: %s Цена: %f\n", names[i], costs[i]);
        }
    }
    public static Transport readMovable() throws ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Экземпляр какого класса вы хотите создать? \n");
        String cl = sc.next();
        Class current_class = Class.forName(cl);
        System.out.print("Марка: ");
        String mark = sc.next();
        System.out.print("Число моделей: ");
        int len = sc.nextInt();
        Transport res = null;
        String name;
        double cost;
        try {
            res = (Transport) current_class.getConstructor(String.class, int.class).newInstance(mark, 0);
            for (int i = 0; i < len; i++) {
                System.out.print("Модель: ");
                name = sc.next();
                System.out.print("Цена: ");
                cost = sc.nextDouble();
                res.addModel(name, cost);
            }
        } catch (DuplicateModelNameException ex) {
            System.out.println("Модель с таким именем уже была добавлена!");
            System.out.println("Процесс ввода остановлен.");
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Что-то пошло не так!");
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public static void writeInLine(final Transport tr, final String fileName) {
        final File file = new File(fileName);
        try (final FileWriter fw = new FileWriter(file)) {
            StringBuilder sb = new StringBuilder();
            sb.append(tr.getMark())
                    .append(" models amount: ")
                    .append(tr.getModelsLength())
                    .append(" -> ");
            final String[] names = tr.getModelsNames();
            for (String name : names) {
                sb.append("\t");
                sb.append(name);
                sb.append(" = ");
                sb.append(tr.getCostOfCurrentModel(name));
            }
            fw.write(sb.toString());
        } catch (IOException | NoSuchModelNameException e) {
            e.printStackTrace();
        }
    }

    public static void writeInColumn(final Transport tr, final String fileName) {
        final File file = new File(fileName);
        try (final FileWriter fw = new FileWriter(file)) {
            StringBuilder sb = new StringBuilder();
            sb.append(tr.getMark())
                    .append(" models amount: ")
                    .append(tr.getModelsLength());
            final String[] names = tr.getModelsNames();
            for (String name : names) {
                sb.append("\n");
                sb.append(name);
                sb.append(" = ");
                sb.append(tr.getCostOfCurrentModel(name));
            }
            fw.write(sb.toString());
        } catch (IOException | NoSuchModelNameException e) {
            e.printStackTrace();
        }
    }
}
