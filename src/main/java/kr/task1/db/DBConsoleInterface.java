package kr.task1.db;

import kr.task1.db.entities.Client;
import kr.task1.db.entities.IPAddress;
import kr.task1.db.entities.TariffPlan;

import java.util.Scanner;

public class DBConsoleInterface {
    private final ProviderDB db;

    public DBConsoleInterface(ProviderDB db) {
        this.db = db;
    }

    public void printClientsTable() {

        if(db.getClients().isEmpty()) {
            System.out.println("The table is empty!");
            return;
        }
        Client client;
        for (int i = 0; i < db.getClients().getCapacity(); i++) {
            client = (Client) (db.getClients().getTable()[i]);
            if (client != null) {
                System.out.print("(hash=" + i + ") ");
                printClientInfo(client);
                System.out.println();
            }
        }
    }

    public void printTariffPlansTable() {
        if(db.getTariffPlans().isEmpty()) {
            System.out.println("The table is empty!");
            return;
        }
        TariffPlan tariffPlan;
        for (int i = 0; i < db.getTariffPlans().getCapacity(); i++) {
            tariffPlan = (TariffPlan) (db.getTariffPlans().getTable()[i]);
            if (tariffPlan != null) {
                System.out.print("(hash=" + i + ") ");
                printTariffPlanInfo(tariffPlan);
                System.out.println();
            }
        }
    }

    public void printIpAddressesTable() {

        if(db.getIpAddresses().isEmpty()) {
            System.out.println("The table is empty!");
            return;
        }
        IPAddress ipAddress;
        for (int i = 0; i < db.getIpAddresses().getCapacity(); i++) {
            ipAddress = (IPAddress) (db.getIpAddresses().getTable()[i]);
            if (ipAddress != null) {
                System.out.print("(hash=" + i + ") ");
                printIPAddressInfo(ipAddress);
                System.out.println();
            }
        }
    }

    private void printClientInfo(Client client) {
        System.out.print(client.getId() + " | " + client.getFIO() + " | " + client.getPhone() + " | " +
                client.getAddress() + " | ");

        //ip address output
        try {
            System.out.print(db.searchIpAddress(client.getIPAddressId()).getIPAddress());
        } catch (Exception e) {
            System.out.print("incorrect_IP_address");
        }

        System.out.print(" | ");

        //tariff plan output
        if (db.getTariffPlans().ifIdExist(client.getTariffPlanId())) {
            System.out.print(db.searchTariffPlan(client.getTariffPlanId()).getName());
        } else {
            System.out.print("incorrect_tariff_plan");
        }
    }

    private void printIPAddressInfo(IPAddress ipAddress) {
        System.out.print(ipAddress.getId() + " | " + ipAddress.getIPAddress() + " | client: ");

        if (db.getClients().ifIdExist(ipAddress.getClientId())) {
            System.out.print(db.searchClient(ipAddress.getClientId()).getFIO());
        } else {
            System.out.print("incorrect_client_id");
        }
    }

    private void printTariffPlanInfo(TariffPlan tariffPlan) {
        System.out.print(tariffPlan.getId() + " | " + tariffPlan.getName() + " | " + tariffPlan.getPrice() + " | " +
                tariffPlan.getDescription() + " | clients: ");

        if (tariffPlan.getClientsID().size() == 0) {
            System.out.print("none");
            return;
        }

        //client list output
        for (Integer id : tariffPlan.getClientsID()) {
            if (!db.getClients().ifIdExist(id)) {
                System.out.print("incorrect_client_id");
            }
            else {
                System.out.print(db.searchClient(id).getFIO());
            }

            System.out.print("(" + id + ")");
            System.out.print(", ");
        }
        System.out.print("\b\b");
    }


    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean exit = false;
        do {
            System.out.println("\nWelcome to provider DB!");
            System.out.println("Choose an option:");
            System.out.println("1. Clients");
            System.out.println("2. IPAddresses");
            System.out.println("3. Tariff plans");
            System.out.println("4. Show all");
            System.out.println("0. Exit");

            input = scanner.nextLine();

            switch (input) {
                case "1" -> clientMenu();
                case "2" -> ipAddressesMenu();
                case "3" -> tariffPlansMenu();
                case "4" -> showAll();
                case "0" -> exit = true;
            }
        } while (!exit);

        System.out.println("Goodbye!");
    }

    private void clientMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean exit = false;
        do {
            System.out.println("\nClients");
            System.out.println("Choose an option:");
            System.out.println("1. Add a client");
            System.out.println("2. Edit a client");
            System.out.println("3. Delete a client");
            System.out.println("4. Search a client (by id)");
            System.out.println("5. Change IP address (by id)");
            System.out.println("6. Change tariff plan (by id)");
            System.out.println("7. Show all");
            System.out.println("0. Main menu");

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.println("Name (FIO):");
                    String name = scanner.nextLine();
                    System.out.println("Phone:");
                    String phone = scanner.nextLine();
                    System.out.println("Address:");
                    String address = scanner.nextLine();
                    System.out.println("Tariff plan id:");
                    int tp = -1;
                    try {
                        tp = Integer.parseInt(scanner.nextLine());
                    } catch (Exception ignored) {

                    }
                    System.out.println("IP address id:");
                    int ip = -1;
                    try {
                        ip = Integer.parseInt(scanner.nextLine());
                    } catch (Exception ignored) {

                    }

                    try {
                        db.addClient(name, phone, address, tp, ip);
                        System.out.println("Done!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "2" -> {
                    int id = -1;
                    do {
                        System.out.println("Input client id to edit:");
                        try {
                            if(db.getClients().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            id = Integer.parseInt(scanner.nextLine());
                            if (!db.getClients().ifIdExist(id)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);
                    if (db.getClients().isEmpty()) {
                        continue;
                    }

                    System.out.println("Name (FIO):");
                    String name = scanner.nextLine();
                    System.out.println("Phone:");
                    String phone = scanner.nextLine();
                    System.out.println("Address:");
                    String address = scanner.nextLine();
                    System.out.println("Tariff plan id:");
                    int tp = -1;
                    try {
                        tp = Integer.parseInt(scanner.nextLine());
                    } catch (Exception ignored) {

                    }
                    System.out.println("IP address id:");
                    int ip = -1;
                    try {
                        ip = Integer.parseInt(scanner.nextLine());
                    } catch (Exception ignored) {

                    }

                    try {
                        db.editClient(id, name, phone, address, tp, ip);
                        System.out.println("Done!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "3" -> {
                    int id = -1;
                    do {
                        System.out.println("Input client id to delete:");
                        try {
                            if(db.getClients().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            id = Integer.parseInt(scanner.nextLine());
                            if (!db.getClients().ifIdExist(id)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    if(db.getClients().ifIdExist(id)) {
                        try {
                            db.deleteClient(id);
                            System.out.println("Done!");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case "4" -> {
                    int id = -1;
                    do {
                        System.out.println("Input client id to search:");
                        try {
                            if(db.getClients().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            id = Integer.parseInt(scanner.nextLine());
                            if (!db.getClients().ifIdExist(id)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);


                    if (db.getClients().ifIdExist(id)) {
                        System.out.println("The client:");
                        printClientInfo(db.searchClient(id));
                        System.out.println();
                    }
                }
                case "5" -> {
                    if(db.getClients().isEmpty()) {
                        System.out.println("The table is empty!");
                        continue;
                    }

                    int clientId = -1;
                    do {
                        System.out.println("Input client id:");
                        try {
                            clientId = Integer.parseInt(scanner.nextLine());
                            if (!db.getClients().ifIdExist(clientId)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    int ipId = -1;
                    do {
                        System.out.println("Input ip address id:");
                        try {
                            ipId = Integer.parseInt(scanner.nextLine());
                            if (!db.getIpAddresses().ifIdExist(ipId)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    db.changeClientsIpAddress(clientId, ipId);
                    System.out.println("Done!");
                }
                case "6" -> {

                    if(db.getClients().isEmpty()) {
                        System.out.println("The table is empty!");
                        continue;
                    }
                    int clientId = -1;
                    do {
                        System.out.println("Input client id:");
                        try {
                            clientId = Integer.parseInt(scanner.nextLine());
                            if (!db.getClients().ifIdExist(clientId)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    int planId = -1;
                    do {
                        System.out.println("Input ip tariff plan id:");
                        try {
                            planId = Integer.parseInt(scanner.nextLine());
                            if (!db.getTariffPlans().ifIdExist(planId)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    db.addClientToTariffPlan(clientId, planId);
                    System.out.println("Done!");

                }
                case "7" -> printClientsTable();
                case "0" -> exit = true;
            }
        } while (!exit);
    }

    private void ipAddressesMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean exit = false;
        do {
            System.out.println("\nIP addresses");
            System.out.println("Choose an option:");
            System.out.println("1. Add an IP address");
            System.out.println("2. Edit an IP address");
            System.out.println("3. Delete an IP address");
            System.out.println("4. Search an IP address (by id)");
            System.out.println("5. Change client (by id)");
            System.out.println("6. Show all");
            System.out.println("0. Main menu");

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.println("IP address:");
                    String ip = scanner.nextLine();
                    System.out.println("Client id:");
                    int clientId = -1;
                    try {
                        clientId = Integer.parseInt(scanner.nextLine());
                    } catch (Exception ignored) {

                    }

                    try {
                        db.addIPAddress(ip, clientId);
                        System.out.println("Done!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "2" -> {
                    int id = -1;
                    do {
                        System.out.println("Input IP address id to edit:");
                        try {
                            if(db.getIpAddresses().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            id = Integer.parseInt(scanner.nextLine());
                            if (!db.getIpAddresses().ifIdExist(id)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    if (db.getIpAddresses().isEmpty()) {
                        continue;
                    }

                    System.out.println("IP address:");
                    String ip = scanner.nextLine();
                    System.out.println("Client id:");
                    int clientId = -1;
                    try {
                        clientId = Integer.parseInt(scanner.nextLine());
                    } catch (Exception ignored) {

                    }

                    try {
                        db.addIPAddress(ip, clientId);
                        System.out.println("Done!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
                case "3" -> {

                    int id = -1;
                    do {
                        System.out.println("Input IP address id to delete:");
                        try {
                            if(db.getIpAddresses().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            id = Integer.parseInt(scanner.nextLine());
                            if (!db.getIpAddresses().ifIdExist(id)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    if (db.getIpAddresses().ifIdExist(id)) {
                        try {
                            db.deleteIpaddress(id);
                            System.out.println("Done!");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case "4" -> {
                    int id = -1;
                    do {
                        System.out.println("Input IP address id to search:");
                        try {
                            if(db.getIpAddresses().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            id = Integer.parseInt(scanner.nextLine());
                            if (!db.getIpAddresses().ifIdExist(id)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    if (db.getIpAddresses().ifIdExist(id)) {
                        System.out.println("That IP address:");
                        printIPAddressInfo(db.searchIpAddress(id));
                        System.out.println();
                    }
                }
                case "5" -> {

                    if(db.getIpAddresses().isEmpty()) {
                        System.out.println("The table is empty!");
                        continue;
                    }
                    int ipId = -1;
                    do {
                        System.out.println("Input ip address id:");
                        try {
                            ipId = Integer.parseInt(scanner.nextLine());
                            if (!db.getIpAddresses().ifIdExist(ipId)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    int clientId = -1;
                    do {
                        System.out.println("Input client id:");
                        try {
                            clientId = Integer.parseInt(scanner.nextLine());
                            if (!db.getClients().ifIdExist(clientId)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    db.changeClientsIpAddress(clientId, ipId);
                    System.out.println("Done!");
                }
                case "6" -> printIpAddressesTable();
                case "0" -> exit = true;
            }
        } while (!exit);
    }

    private void tariffPlansMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean exit = false;
        do {
            System.out.println("\nTariff plans");
            System.out.println("Choose an option:");
            System.out.println("1. Add a plan");
            System.out.println("2. Edit a plan");
            System.out.println("3. Delete a plan");
            System.out.println("4. Search a plan (by id)");
            System.out.println("5. Add client to plan (by id)");
            System.out.println("6. Delete client from plan (by id)");
            System.out.println("7. Show all");
            System.out.println("0. Main menu");

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.println("Name:");
                    String name = scanner.nextLine();
                    System.out.println("Price:");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.println("Description:");
                    String description = scanner.nextLine();

                    try {
                        db.addTariffPlan(name, price, description);
                        System.out.println("Done!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "2" -> {
                    int id = -1;
                    do {
                        System.out.println("Input tariff plan id to edit:");
                        try {
                            if(db.getTariffPlans().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            id = Integer.parseInt(scanner.nextLine());
                            if (!db.getTariffPlans().ifIdExist(id)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);


                    if (db.getTariffPlans().isEmpty()) {
                        continue;
                    }

                    System.out.println("Name:");
                    String name = scanner.nextLine();
                    System.out.println("Price:");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.println("Description:");
                    String description = scanner.nextLine();

                    try {
                        db.addTariffPlan(name, price, description);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    try {
                        db.editTariffPlan(id, name, price, description);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Done!");
                }
                case "3" -> {
                    int id = -1;
                    do {
                        System.out.println("Input tariff plan id to delete:");
                        try {
                            if(db.getTariffPlans().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            id = Integer.parseInt(scanner.nextLine());
                            if (!db.getTariffPlans().ifIdExist(id)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    if(db.getTariffPlans().ifIdExist(id)) {
                        try {
                            db.deleteTariffPlan(id);
                            System.out.println("Done!");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case "4" -> {

                    int id = -1;
                    do {
                        System.out.println("Input tariff plan id to search:");
                        try {
                            if(db.getTariffPlans().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            id = Integer.parseInt(scanner.nextLine());
                            if (!db.getTariffPlans().ifIdExist(id)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);


                    if (db.getTariffPlans().ifIdExist(id)) {
                        System.out.println("The tariff plan:");
                        printTariffPlanInfo(db.searchTariffPlan(id));
                        System.out.println();
                    }
                }
                case "5" -> {

                    if(db.getTariffPlans().isEmpty()) {
                        System.out.println("The table is empty!");
                        continue;
                    }
                    int planId = -1;
                    do {
                        System.out.println("Input tariff plan id to add client:");
                        try {
                            if(db.getTariffPlans().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            planId = Integer.parseInt(scanner.nextLine());
                            if (!db.getTariffPlans().ifIdExist(planId)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);


                    if(db.getTariffPlans().isEmpty()) {
                        continue;
                    }

                    int id = -1;
                    if (db.getTariffPlans().ifIdExist(planId)) {
                        do {
                            System.out.println("Input client id to add:");
                            try {
                                if(db.getClients().isEmpty()) {
                                    System.out.println("The table is empty!");
                                    break;
                                }
                                id = Integer.parseInt(scanner.nextLine());
                                if (!db.getClients().ifIdExist(id)) {
                                    throw new Exception();
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("Incorrect or not exist id!");
                            }
                        } while (true);
                    }

                    if (db.getClients().ifIdExist(id) && db.getTariffPlans().ifIdExist(planId)) {
                        db.addClientToTariffPlan(id, planId);
                        System.out.println("Done!");
                    }
                }
                case "6" -> {

                    if(db.getTariffPlans().isEmpty()) {
                        System.out.println("The table is empty!");
                        continue;
                    }

                    int planId = -1;
                    do {
                        System.out.println("Input tariff plan id to delete client:");
                        try {
                            if(db.getTariffPlans().isEmpty()) {
                                System.out.println("The table is empty!");
                                break;
                            }
                            planId = Integer.parseInt(scanner.nextLine());
                            if (!db.getTariffPlans().ifIdExist(planId)) {
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect or not exist id!");
                        }
                    } while (true);

                    int id = -1;
                    if (db.getTariffPlans().ifIdExist(planId)) {
                        do {
                            System.out.println("Input client id to delete:");
                            try {
                                if(db.getClients().isEmpty()) {
                                    System.out.println("The table is empty!");
                                    break;
                                }
                                id = Integer.parseInt(scanner.nextLine());
                                if (!db.getClients().ifIdExist(id)) {
                                    throw new Exception();
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("Incorrect or not exist id!");
                            }
                        } while (true);
                    }

                    if (db.getClients().ifIdExist(id) && db.getTariffPlans().ifIdExist(planId)) {
                        db.deleteClientFromTariffPlan(id, planId);
                        System.out.println("Done!");
                    }
                }
                case "7" -> printTariffPlansTable();
                case "0" -> exit = true;
            }
        } while (!exit);
    }

    private void showAll() {
        System.out.println("Clients:");
        printClientsTable();
        System.out.println("\nIP addresses:");
        printIpAddressesTable();
        System.out.println("\nTariff plans:");
        printTariffPlansTable();
        System.out.println();
    }
}