package kr.task1.db.entities;

public class IPAddress extends ProviderEntity{
    private String IPAddress;
    private int clientId;   //-1 if not assigned

    //if clientId==-1 IP is not bounded

    public IPAddress(int id, String IPAddress) {
        this(id, IPAddress, -1);
    }

    public IPAddress(int id, String IPAddress, int clientId) {
        super(id);
        this.IPAddress = IPAddress;
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
