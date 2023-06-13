package kr.task1.db.entities;

public class Client extends ProviderEntity{
    private String FIO;
    private String phone;
    private String address;
    private int tariffPlanId;
    private int IPAddressId;

    public Client(int id, String FIO, String phone, String address, int tariffPlanId, int ipAddress) {
        super(id);
        this.FIO = FIO;
        this.phone = phone;
        this.address = address;
        this.tariffPlanId = tariffPlanId;       //-1 if not defined
        this.IPAddressId = ipAddress;           //-1 if not defined
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTariffPlanId() {
        return tariffPlanId;
    }

    public void setTariffPlanId(int tariffPlanId) {
        this.tariffPlanId = tariffPlanId;
    }

    public int getIPAddressId() {
        return IPAddressId;
    }

    public void setIPAddressId(int IPAddressId) {
        this.IPAddressId = IPAddressId;
    }
}
