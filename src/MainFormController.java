import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MainFormController {
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtCustomerSalary;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerName;
    public TableColumn colCustomerSalary;

    public void initialize(){
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        loadCustomers();
    }

    private void loadCustomers() {
        ObservableList<CustomerTm> tmList= FXCollections.observableArrayList();
        try(Session session=HibernateUtil.createSession()){
            Query from_customer =
                    session.createQuery("FROM Customer");
            List<Customer> list = from_customer.list();
            for (Customer c: list
                 ) {
                tmList.add(new CustomerTm(c.getId(),c.getName(),c.getAddress(),c.getSalary()));
            }
            tblCustomer.setItems(tmList);
        }
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
        Customer c1= new Customer(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                Double.parseDouble(txtCustomerSalary.getText())
        );

        try(Session session=HibernateUtil.createSession()){
            Transaction transaction = session.beginTransaction();
            session.save(c1);
            transaction.commit();
            new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
            loadCustomers();
        }

    }
}
