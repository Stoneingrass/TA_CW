package kr.task1.db;

import kr.task1.db.entities.Client;
import kr.task1.db.entities.IPAddress;
import kr.task1.db.entities.ProviderEntity;
import kr.task1.db.entities.TariffPlan;

public class ProviderDB {
    private final HashTable<Client> clients;
    private final HashTable<IPAddress> ipAddresses;
    private final HashTable<TariffPlan> tariffPlans;

    public ProviderDB() {
        clients = new HashTable<>();
        ipAddresses = new HashTable<>();
        tariffPlans = new HashTable<>();
    }

    public ProviderDB(int clientsCapacity, int ipAddressesCapacity, int tariffPlansCapacity) {
        clients = new HashTable<>(clientsCapacity);
        ipAddresses = new HashTable<>(ipAddressesCapacity);
        tariffPlans = new HashTable<>(tariffPlansCapacity);
    }

    public HashTable<Client> getClients() {
        return clients;
    }

    public HashTable<IPAddress> getIpAddresses() {
        return ipAddresses;
    }

    public HashTable<TariffPlan> getTariffPlans() {
        return tariffPlans;
    }


    //insert
    public void addClient(String FIO, String phone, String address, int tariffPlanId, int ipAddress) throws Exception {
        clients.insert(new Client(clients.getIdCounter(), FIO, phone, address, tariffPlanId, ipAddress));

        addClientToTariffPlan(clients.getIdCounter() - 1, tariffPlanId);

        changeClientsIpAddress(clients.getIdCounter() - 1, ipAddress);
    }

    public void addIPAddress(String IPAddress, int clientId) throws Exception {
        ipAddresses.insert(new IPAddress(ipAddresses.getIdCounter(), IPAddress, clientId));
        changeClientsIpAddress(ipAddresses.getIdCounter() - 1, clientId);
    }

    public void addTariffPlan(String name, double price, String description) throws Exception {
        tariffPlans.insert(new TariffPlan(tariffPlans.getIdCounter(), name, price, description));
    }

    //delete
    public void deleteClient(int id) throws Exception {
        clients.remove(id);
    }

    public void deleteIpaddress(int id) throws Exception {
        ipAddresses.remove(id);
    }

    public void deleteTariffPlan(int id) throws Exception {
        tariffPlans.remove(id);
    }

    //search
    public Client searchClient(int id) {
        return (Client) clients.search(id);
    }

    public IPAddress searchIpAddress(int id) {
        return (IPAddress) ipAddresses.search(id);
    }

    public TariffPlan searchTariffPlan(int id) {
        return (TariffPlan) tariffPlans.search(id);
    }

    //edit
    public void editClient(int id, String FIO, String phone, String address, int tariffPlanId, int ipAddress) throws Exception {
        Client client = searchClient(id);

        if (client==null) {
            throw new Exception("Incorrect id!");
        }

        client.setFIO(FIO);
        client.setPhone(phone);
        client.setAddress(address);

        changeClientsIpAddress(id, ipAddress);
        addClientToTariffPlan(id,tariffPlanId);
    }

    public void editIpAddress(int id, String IPAddress, int clientId) throws Exception {
        IPAddress ipAddress = searchIpAddress(id);

        if (ipAddress==null) {
            throw new Exception("Incorrect id!");
        }

        ipAddress.setIPAddress(IPAddress);

        changeClientsIpAddress(clientId, id);
    }

    public void editTariffPlan(int id, String name, double price, String description) throws Exception {
        TariffPlan tariffPlan = searchTariffPlan(id);
        if (tariffPlan==null) {
            throw new Exception("Incorrect id!");
        }

        tariffPlan.setName(name);
        tariffPlan.setPrice(price);
        tariffPlan.setDescription(description);
    }

    //other
    //change both client's IP and IP's client
    public void changeClientsIpAddress(int clientId, int ipAddressId) {
        Client client = searchClient(clientId);
        IPAddress ipAddress = searchIpAddress(ipAddressId);

        if(client!=null) {
            client.setIPAddressId(ipAddressId);
        }
        if(ipAddress!=null) {
            ipAddress.setClientId(clientId);
        }
    }

    //change both client's tariff plan and tariff plan's clients list
    public void addClientToTariffPlan(int clientId, int tariffPlanId) {
        Client client = searchClient(clientId);
        TariffPlan tariffPlan = searchTariffPlan(tariffPlanId);

        if(client != null) {
            client.setTariffPlanId(tariffPlanId);
        }
        if(tariffPlan!=null) {
            tariffPlan.getClientsID().add(clientId);
        }
    }

    //delete both client's tariff plan and tariff plan's clients list
    public void deleteClientFromTariffPlan(int clientId, int tariffPlanId) {
        Client client = searchClient(clientId);
        TariffPlan tariffPlan = searchTariffPlan(tariffPlanId);

        if(client != null) {
            client.setTariffPlanId(-1);
        }
        if(tariffPlan!=null) {
            tariffPlan.getClientsID().remove((Integer) clientId);
        }
    }
}
