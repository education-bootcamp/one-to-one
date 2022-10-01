import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainFormController {
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtCustomerSalary;
    public TableView tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerName;
    public TableColumn colCustomerSalary;

    public void initialize(){
        loadCustomers();
    }

    private void loadCustomers() {

    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
    }
}
