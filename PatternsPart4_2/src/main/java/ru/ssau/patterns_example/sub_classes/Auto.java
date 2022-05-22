package ru.ssau.patterns_example.sub_classes;


import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Auto implements Transport, Serializable, Cloneable {
    private String mark;
    private Model[] models;

    public Auto(String mark, int numberOfModels) {
        this.mark = mark;
        if(numberOfModels >= 0) {
            models = new Model[numberOfModels];
            for (int i = 0; i < numberOfModels; i++) {
                models[i] = new Model(i + "", 0.0);
            }
        } else {
            models = new Model[0];
        }
    }

    public String getMark() {
        return this.mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String[] getModelsNames() {
        if(models != null) {
            int len = models.length;
            String[] res = new String[len];
            for(int i = 0; i < len; i++) {
                res[i] = models[i].getName();
            }
            return res;
        }
        return null;
    }

    public double getCostOfCurrentModel(String name) throws NoSuchModelNameException {
        if(models != null){
            int i = 0;
            int len = models.length;
            while((i < len) && (!models[i].getName().equals(name)))
                i++;
            if(i >= len) {
                throw new NoSuchModelNameException(name);
            } else {
                return models[i].getCost();
            }
        }
        return 0;
    }

    public void setCostOfCurrentModel(String name, double cost) throws NoSuchModelNameException {
        if((cost < 0) || (Double.isNaN(cost))){
            throw new ModelPriceOutOfBoundsException(name);
        }
        if(models != null){
            int i = 0;
            int len = models.length;
            while((i < len) && (!models[i].getName().equals(name)))
                i++;
            if(i < len) {
                models[i].setCost(cost);
            } else {
                throw new NoSuchModelNameException(name);
            }
        }
    }

    public double[] getArrayOfModelsCosts() {
        if(models != null) {
            int len = models.length;
            double[] res = new double[len];
            for(int i = 0; i < len; i++) {
                res[i] = models[i].getCost();
            }
            return res;
        }
        return null;
    }

    public void addModel(String name, double cost) throws DuplicateModelNameException {
        if((cost < 0) || (Double.isNaN(cost))) {
            throw new ModelPriceOutOfBoundsException(name);
        }
        if(models != null) {
            String[] strs = this.getModelsNames();
            for (String str:strs) {
                if(str.equals(name))
                    throw new DuplicateModelNameException(name);
            }
            int newLen = models.length + 1;
            models = Arrays.copyOf(models, newLen);
            models[newLen - 1] = new Model(name, cost);
        }
    }

    public void delModel(String name) throws NoSuchModelNameException {
        if(models != null) {
            int len = models.length;
            int i = 0;
            while ((i < len) && (!models[i].name.equals(name)))
                i++;
            if (i < len) {
                if (len - 1 - i >= 0) System.arraycopy(models, i + 1, models, i, len - 1 - i);
                models = Arrays.copyOf(models, len - 1);
            } else {
                throw new NoSuchModelNameException(name);
            }
        }
    }

    public int getModelsLength() {
        if(models != null) {
            return models.length;
        }
        return -1;
    }

    public void setModelName(String name1, String name2) throws NoSuchModelNameException, DuplicateModelNameException {
        if(models != null) {
            String[] strs = getModelsNames();
            for (String str:strs) {
                if(name2.equals(str))
                    throw new DuplicateModelNameException(name2);
            }
            int i = 0;
            int len = models.length;
            while((i < len) && (!models[i].getName().equals(name1))){
                i++;
            }
            if(i < len) {
                models[i].setName(name2);
            } else {
                throw new NoSuchModelNameException(name1);
            }
        }
    }


    public java.util.Iterator<Model> iterator() {
        return new AutoIterator<Model>(this.models);
    }

    public synchronized AutoMemento createMemento() {
        final AutoMemento autoMemento = new AutoMemento();
        autoMemento.setAuto(this);
        return autoMemento;
    }

    //lab4
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getMark());
        sb.append("\n");
        int len = getModelsLength();
        for (int i = 0; i < len; i++) {
            sb.append(this.models[i].getName());
            sb.append("\n");
            sb.append(this.models[i].getCost());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj != this && obj instanceof Transport) {
            Transport m = (Transport) obj;
            boolean res = m.getMark().equals(this.getMark());
            int len = getModelsLength();
            res &= len == m.getModelsLength();
            String[] names = m.getModelsNames();
            double[] costs = m.getArrayOfModelsCosts();
            for(int i = 0; i < len && res; i++) {
                res &= names[i].equals(models[i].getName());
                res &= costs[i] == models[i].getCost();
            }
            return res;
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Auto res = null;
        res = (Auto) super.clone();
        int len = getModelsLength();
        res.models = new Model[len];
        for (int i = 0; i < len; i++) {
            res.models[i] = (Model) models[i].clone();
        }
        return res;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mark == null) ? 0 : mark.hashCode());
        for(Model m : models) {
            result = prime * result + ((m == null) ? 0 : m.hashCode());
        }
        return result;
    }

    static class Model implements Serializable, Cloneable {
        private String name;
        private double cost;

        public Model() {
            //Ни чего не будет - name = null; cost = 0.0;
        }
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

        @Override
        public Object clone() throws CloneNotSupportedException {
            Model res = null;
            res = (Model) super.clone();
            return res;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((cost == 0.0) ? 0 : Double.valueOf(cost).hashCode());
            return result;
        }

        @Override
        public String toString() {
            return this.name + " " + this.cost;
        }
    }

    private class AutoIterator<T> implements java.util.Iterator<T> {

        private int currentIndex = 0;
        private T[] models;

        public AutoIterator(final T[] models) {
            this.models = models;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < models.length;
        }

        @Override
        public T next() {
            int i = currentIndex;
            if (i >= models.length) {
                throw new NoSuchElementException();
            }
            currentIndex++;
            return models[i];
        }
    }

    public static class AutoMemento {
        private byte[] autoBytes;

        private void setAuto(final Auto auto) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(auto);
                this.autoBytes = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private Auto getAuto() {
            if (autoBytes == null) {
                throw new IllegalStateException("Inner state of memento is null");
            }
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(autoBytes))) {
                Auto auto = (Auto) ois.readObject();
                if (auto == null) {
                    throw new IllegalStateException("Object Reading Error");
                }
                return auto;
            } catch (ClassNotFoundException | IOException e) {
                throw new IllegalStateException("Something was wrong.", e);
            }
        }
    }
}
