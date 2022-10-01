import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "item",cascade = {
            CascadeType.ALL
    })
    private List<OrderDetails> details= new ArrayList<>();

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
