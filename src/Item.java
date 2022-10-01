import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
    @Id
    @Column(name = "item_code")
    private String code;
    private DescriptionData description;
    @Column(name = "unit_price")
    private double unitPrice;
    @Column(name = "total_cost")
    private int qtyOnHand;

    public Item() {
    }

    public Item(String code, DescriptionData description, double unitPrice, int qtyOnHand) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DescriptionData getDescription() {
        return description;
    }

    public void setDescription(DescriptionData description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotalCost() {
        return qtyOnHand;
    }

    public void setTotalCost(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }
}
