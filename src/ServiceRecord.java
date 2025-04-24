public class ServiceRecord {
    private int id;
    private String vehicleNumber;
    private String ownerName;
    private String model;
    private String serviceDetails;
    private String serviceDate;

    public ServiceRecord(int id, String vehicleNumber, String ownerName, String model, String serviceDetails, String serviceDate) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.model = model;
        this.serviceDetails = serviceDetails;
        this.serviceDate = serviceDate;
    }

    public String toString() {
        return "ID: " + id + ", Vehicle: " + vehicleNumber + ", Owner: " + ownerName +
                ", Model: " + model + ", Service: " + serviceDetails + ", Date: " + serviceDate;
    }
}
