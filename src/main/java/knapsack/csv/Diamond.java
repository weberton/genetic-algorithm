package knapsack.csv;

import java.util.Objects;

import com.opencsv.bean.CsvBindByPosition;

public class Diamond {
    @CsvBindByPosition(position = 0)
    private int id;
    @CsvBindByPosition(position = 1)
    private double weight;
    @CsvBindByPosition(position = 7)
    private double price;

    public Diamond() {
    }

    public Diamond(int id, double weight, double price) {
        this.id = id;
        this.weight = weight;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight * 200; //alteração do peso do diamante para miligrama
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diamond diamond = (Diamond) o;
        return id == diamond.id && Double.compare(weight, diamond.weight) == 0 && Double.compare(price, diamond.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weight, price);
    }
}
