import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
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
    public TextField txtVehicleId;
    public TextField txtVehicleBrand;
    public TextField txtVehicleType;
    public TextField txtVehicleColor;
    public TableView<VehicleTm> tblVehicles;
    public TableColumn colVehicleId;
    public TableColumn colVehicleBrand;
    public TableColumn colVehicleType;
    public TableColumn colCustomerIdOfVehicle;
    public TableColumn colVehicleColor;
    public TableColumn colCustomerNameOfVehicle;
    public TextField txtOrderId;
    public DatePicker txtOrderDate;
    public TextField txtOrderCost;
    public TableView<OrderTm> tblOrders;
    public TableColumn colOrderId;
    public TableColumn colOrderDate;
    public TableColumn colOrderCost;
    public TableColumn colCustomerIdOfOrder;
    public TableColumn colCustomerNameOfOrder;
    public ComboBox<String> cmbItemCodes;
    public TableView<CartTm> tblCart;
    public TextField txtQty;
    public TableColumn colItemCode;
    public TableColumn colItemQty;

    public void initialize() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVehicleBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colVehicleColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colCustomerIdOfVehicle.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerNameOfVehicle.setCellValueFactory(new PropertyValueFactory<>("customerName"));


        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colCustomerIdOfOrder.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerNameOfOrder.setCellValueFactory(new PropertyValueFactory<>("customerName"));


        colItemCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadCustomers();
        loadVehicles();
        loadOrders();
        loadAllItemCodes();


        //========
        tblCustomer.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedCustomer = newValue;
                });
    }

    private void loadAllItemCodes() {
        try (Session session = HibernateUtil.createSession()) {
            Query from_item =
                    session.createQuery("FROM Item");
            List<Item> list = from_item.list();
            for (Item i : list
            ) {
                cmbItemCodes.getItems().add(i.getCode());
            }
        }
    }

    private void loadCustomers() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.createSession()) {
            Query from_customer =
                    session.createQuery("FROM Customer");
            List<Customer> list = from_customer.list();
            for (Customer c : list
            ) {
                tmList.add(new CustomerTm(c.getId(), c.getName(), c.getAddress(), c.getSalary()));
            }
            tblCustomer.setItems(tmList);
        }
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
        Customer c1 = new Customer(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                Double.parseDouble(txtCustomerSalary.getText())
        );

        try (Session session = HibernateUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(c1);
            transaction.commit();
            new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
            loadCustomers();
        }

    }

    CustomerTm selectedCustomer = null;

    public void saveVehicleOnAction(ActionEvent actionEvent) {
        if (selectedCustomer == null) {
            new Alert(Alert.AlertType.WARNING, "Select a Customer").show();
            return;
        }

        Vehicle v1 = new Vehicle(
                txtVehicleId.getText(),
                txtVehicleBrand.getText(),
                txtVehicleType.getText(), txtVehicleColor.getText()
        );
        v1.setCustomer(new Customer(
                selectedCustomer.getId(),
                selectedCustomer.getName(),
                selectedCustomer.getAddress(),
                selectedCustomer.getSalary()
        ));

        try (Session session = HibernateUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(v1);
            transaction.commit();
            new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
            loadVehicles();
        }

    }

    private void loadVehicles() {
        ObservableList<VehicleTm> tmList = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.createSession()) {
            Query from_vehicle =
                    session.createQuery("FROM Vehicle");
            List<Vehicle> list = from_vehicle.list();

            for (Vehicle v : list
            ) {
                tmList.add(new VehicleTm(
                        v.getVehicleId(),
                        v.getBrand(), v.getType(), v.getColor(),
                        v.getCustomer().getId(), v.getCustomer().getName()));
            }
            tblVehicles.setItems(tmList);

        }
    }

    public void saveOrderOnAction(ActionEvent actionEvent) {
        if (selectedCustomer == null) {
            new Alert(Alert.AlertType.WARNING, "Select a Customer").show();
            return;
        }
        Order o= new Order(txtOrderId.getText(),txtOrderDate.getValue().toString(),
                Double.parseDouble(txtOrderCost.getText()));
        o.setCustomer(new Customer(
                selectedCustomer.getId(),
                selectedCustomer.getName(),
                selectedCustomer.getAddress(),
                selectedCustomer.getSalary()
        ));

        try (Session session = HibernateUtil.createSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(o);
            transaction.commit();
            for (CartTm tm: itemCartList
            ) {
                saveItemDetails(o, tm);
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
            loadOrders();
        }

    }

    private void saveItemDetails(Order o, CartTm tm) {
        try(Session session= HibernateUtil.createSession()){
            Item i =session.get(Item.class,tm.getId());

            OrderDetails od= new OrderDetails(0,tm.getQty(),i.getUnitPrice());
            od.setItem(i);
            od.setOrder(o);

            Transaction transaction = session.beginTransaction();
            session.save(od);
            transaction.commit();
        }
    }

    private void loadOrders() {
        ObservableList<OrderTm> tmList = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.createSession()) {
            Query from_orders =
                    session.createQuery("FROM Order");
            List<Order> list = from_orders.list();

            for (Order o : list
            ) {
                tmList.add(new OrderTm(
                        o.getId(),
                        o.getOrderDate(), o.getCost(),
                        o.getCustomer().getId(), o.getCustomer().getName()));
            }
            tblOrders.setItems(tmList);

        }
    }
ObservableList<CartTm> itemCartList=FXCollections.observableArrayList();
    public void addToCartOnAction(ActionEvent actionEvent) {
        if (cmbItemCodes.getValue()==null){
            new Alert(Alert.AlertType.WARNING, "Select an Item").show();
            return;
        }
        itemCartList.add(new CartTm(cmbItemCodes.getValue().toString()
                ,Integer.parseInt(txtQty.getText())));
        tblCart.setItems(itemCartList);
    }
}
