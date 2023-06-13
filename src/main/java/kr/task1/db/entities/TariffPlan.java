package kr.task1.db.entities;

import java.util.ArrayList;
import java.util.List;

public class TariffPlan extends ProviderEntity{
    private String name;
    private double price;
    private String description;
    private final List<Integer> clientsID;

    public TariffPlan(int id, String name, double price, String description) {
        super(id);
        this.name = name;
        this.price = price;
        this.description = description;
        this.clientsID = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getClientsID() {
        return clientsID;
    }
}