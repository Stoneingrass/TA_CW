package kr.task1;

import kr.task1.db.DBConsoleInterface;
import kr.task1.db.ProviderDB;
import kr.task1.db.entities.Client;
import kr.task1.db.entities.ProviderEntity;
import kr.task2.automatons.MealyAutomaton;
import kr.task2.automatons.MooreAutomaton;
import kr.task2.automatons.enums.X;

public class Main1 {
    public static void main(String[] args) {

        ProviderDB db = new ProviderDB();
        DBConsoleInterface console = new DBConsoleInterface(db);

        try {
            db.addClient("Свиридов Ілля Ігорович", "+380671234567", "Одеса", -1, -1);
            db.addClient("Мкртичян Альберт Батькович", "+380671111111", "Пастера", -1, -1);
            db.addClient("Кобилінська Єлизавета", "+380000000000", "Харків", -1, -1);
            db.addClient("Привалов Андрій", "+111111111111111", "не знаю", -1, -1);
            db.addClient("Геральт з Рівії", "+????????", "Рівія", -1, -1);

            db.addIPAddress("123.142.0.1", 0);
            db.addIPAddress("124.142.0.1", 1);
            db.addIPAddress("125.142.0.1", 2);
            db.addIPAddress("126.142.0.1", 3);
            db.addIPAddress("127.142.0.1", 4);
            db.addIPAddress("128.142.0.1", -1);
            db.addIPAddress("129.142.255.254", -1);
            db.addIPAddress("129.142.255.255", -1);

            db.addTariffPlan("good", 13, "normal mode");
            db.addTariffPlan("cool", 123, "it is really cool");
            db.addTariffPlan("best", 13000, "feel like a king");

            db.addClientToTariffPlan(0, 1);
            db.addClientToTariffPlan(1, 2);
            db.changeClientsIpAddress(2, 1);
            db.changeClientsIpAddress(4, 0);

            //testing
            System.out.println("Testing");
            long time;
            time=System.nanoTime();
            db.addClient("test", "+3806", "Одеса", 1, 1);
            System.out.println("Input: " + (System.nanoTime()-time) + " ns");
            time=System.nanoTime();
            db.editClient(5, "test", "+3806", "Одеса", 1, 1);
            System.out.println("Edit: " + (System.nanoTime()-time) + " ns");
            time=System.nanoTime();
            db.searchClient(5);
            System.out.println("Search: " + (System.nanoTime()-time) + " ns");
            time=System.nanoTime();
            db.deleteClient(5);
            System.out.println("Delete: " + (System.nanoTime()-time) + " ns");

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        console.mainMenu();
    }
}