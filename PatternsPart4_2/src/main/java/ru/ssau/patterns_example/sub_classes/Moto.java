package ru.ssau.patterns_example.sub_classes;

import java.io.Serializable;

public class Moto implements Transport, Serializable, Cloneable {

    private Model head = new Model();
    {
        head.prev = head;
        head.next = head;
    }
    private int size = 0;

    // далее код по заданию
    private String mark;

    public Moto(String mark, int numberOfModels) {
        this.mark = mark;
        int i = 0;
        Model md;
        while(i < numberOfModels) {
            md = new Model(i + "", 0.0);
            md.prev = head.prev;
            md.next = head.next;
            head.prev.next = md;
            head.prev = md;
            size++;
        }
        md = null;
    }

    public String getMark() {
        return this.mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getModelsLength() {
        return size;
    }

    //готов
    public String[] getModelsNames() {
        int len = getModelsLength();
        String[] res = new String[len];
        Model q = head.next;
        for(int i = 0; i < len; i++) {
             res[i] = q.getName();
             q = q.next;
        }
        return res;
    }

    //Готов
    public double getCostOfCurrentModel(String name) throws NoSuchModelNameException {
        Model q = head.next;
        while((q != head) && (!q.getName().equals(name)))
            q = q.next;
        if(q == head) {
            throw new NoSuchModelNameException(name);
        } else {
            return q.getCost();
        }
    }

    //Готов
    public void setCostOfCurrentModel(String name, double cost) throws NoSuchModelNameException {
        if((cost < 0) || (Double.isNaN(cost))) {
            throw new ModelPriceOutOfBoundsException(name);
        }
        Model q = head.next;
        while((q != head) && (!q.getName().equals(name)))
            q = q.next;
        if(q != head) {
            q.setCost(cost);
        } else {
            throw new NoSuchModelNameException(name);
        }
    }

    //готов
    public double[] getArrayOfModelsCosts() {
        int len = getModelsLength();
        double[] res = new double[len];
        Model q = head.next;
        for(int i = 0; i < len; i++) {
            res[i] = q.getCost();
            q = q.next;
        }
        return res;
    }

    //готов
    public void addModel(String name, double cost) throws DuplicateModelNameException {
        Model model = head.next;
        while((model != head) && (!model.name.equals(name))) {
            model = model.next;
        }
        if(model != head) {
            throw new DuplicateModelNameException(name);
        } else {
            if((cost < 0) || (Double.isNaN(cost))) {
                throw new ModelPriceOutOfBoundsException(name);
            }
            model = new Model(name, cost);
            model.prev = head.prev;
            model.next = head;
            head.prev.next = model;
            head.prev = model;
            model = null;
            size++;
        }
    }

    //Готов
    public void delModel(String name) throws NoSuchModelNameException {
        Model q = head.next;
        while((q != head) && (!q.getName().equals(name)))
            q = q.next;
        if(q != head) {
            q.prev.next = q.next;
            q.next.prev = q.prev;
            q = null;
            size--;
        } else {
            throw new NoSuchModelNameException(name);
        }
    }

    public void setModelName(String name1, String name2) throws NoSuchModelNameException, DuplicateModelNameException {
        Model p = head.next;
        while((p != head) && (!p.getName().equals(name2))) {
            p = p.next;
        }
        if(p != head) {
            throw new DuplicateModelNameException(name2);
        } else {
            p = head.next;
            while((p != head) && (!p.getName().equals(name1))) {
                p = p.next;
            }
            if(p != head) {
                p.setName(name2);
            } else {
                throw new NoSuchModelNameException(name1);
            }
        }
    }

    //lab4
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getMark());
        sb.append("\n");
        int len = getModelsLength();
        Model p = head.next;
        while(p != head)
        for (int i = 0; i < len; i++) {
            sb.append(p.getName());
            sb.append("\n");
            sb.append(p.getCost());
            sb.append("\n");
            p = p.next;
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if(obj != null && obj != this && obj instanceof Transport) {
            Transport m = (Transport) obj;
            boolean res = m.getMark().equals(this.getMark());
            int len = getModelsLength();
            res &= len == m.getModelsLength();
            String[] names = m.getModelsNames();
            double[] costs = m.getArrayOfModelsCosts();
            String[] names2 = this.getModelsNames();
            double[] costs2 = this.getArrayOfModelsCosts();
            for(int i = 0; i < len && res; i++) {
                res &= names[i].equals(names2[i]);
                res &= costs[i] == costs2[i];
            }
            return res;
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        Moto res = null;
        try {
            res = (Moto) super.clone();
            res.size = 0;
            int len = getModelsLength();
            res.head = new Model();
            res.head.next = res.head;
            res.head.prev = res.head;
            Model p = head.next;
            for (int i = 0; i < len && p != head; i++) {
                res.addModel(p.getName(), p.getCost());
                p = p.next;
            }
        } catch (DuplicateModelNameException ex) {}
        return res;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mark == null) ? 0 : mark.hashCode());
        Model p = head.next;
        while (p != head) {
            result = prime * result + ((p == null) ? 0 : p.hashCode());
            p = p.next;
        }
        return result;
    }

    //Внутренний класс
    private class Model implements Serializable, Cloneable {
        private String name = null;
        private double cost = Double.NaN;
        private Model prev = null;
        private Model next = null;

        public Model() {}
        public Model(String name) {
            this.name = name;
        }
        public Model(double cost) {
            this.cost = cost;
        }
        public Model(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((cost == 0.0) ? 0 : Double.valueOf(cost).hashCode());
            return result;
        }
    }
}
