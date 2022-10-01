public class VehicleTm {
    private String id;
    private String brand;
    private String type;
    private String color;
    private String customerId;
    private String customerName;

    public VehicleTm() {
    }

    public VehicleTm(String id, String brand, String type, String color, String customerId, String customerName) {
        this.id = id;
        this.brand = brand;
        this.type = type;
        this.color = color;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
