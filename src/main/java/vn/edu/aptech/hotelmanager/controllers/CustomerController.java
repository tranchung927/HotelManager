package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.filter.EnumFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.common.BaseController;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;
import vn.edu.aptech.hotelmanager.domain.model.GENDER_TYPE;
import vn.edu.aptech.hotelmanager.domain.repo.ICustomerRepo;

import java.net.URL;
import java.util.*;
import java.util.function.Function;

public class CustomerController extends BaseController implements Initializable {

    @FXML
    private MFXButton addNewButton;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private MFXPaginatedTableView<CustomerDTO> customerTableView;
    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;
    private Integer selectedIndex;
    public CustomerController(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ownerNode = rootAnchorPane;
        setupUI();
        customerTableView.autosizeColumnsOnInitialization();
        When.onChanged(customerTableView.currentPageProperty())
                .then((oldValue, newValue) -> customerTableView.autosizeColumns())
                .listen();
        getData();
    }

    private void setupUI() {
        DoubleBinding maxWidthColumn = customerTableView.widthProperty().multiply(0.2);
        // FirstName Column
        MFXTableColumn<CustomerDTO> firstNameColumn = new MFXTableColumn<>("First Name", false,
                Comparator.comparing(o -> o.getCustomer().getFirstName()));
        Function<CustomerDTO, String> mapFirstName = u -> u.getCustomer().getFirstName();
        firstNameColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(mapFirstName));

        // Last Name Column
        MFXTableColumn<CustomerDTO> lastNameColumn = new MFXTableColumn<>("Last Name", false,
                Comparator.comparing(o -> o.getCustomer().getLastName()));
        Function<CustomerDTO, String> mapLastName = u -> u.getCustomer().getLastName();
        lastNameColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(mapLastName) {{
                setAlignment(Pos.CENTER);
		}});
        lastNameColumn.prefWidthProperty().bind(maxWidthColumn);

        // Email Column
        MFXTableColumn<CustomerDTO> emailColumn = new MFXTableColumn<>("Email", false);
        Function<CustomerDTO, String> mapEmail = u -> u.getCustomer().getEmail();
        emailColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(mapEmail));

        // Phone Column
        MFXTableColumn<CustomerDTO> phoneColumn = new MFXTableColumn<>("Phone", false);
        Function<CustomerDTO, String> mapPhone = u -> u.getCustomer().getPhoneNumber();
        phoneColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(mapPhone));

        // DOB Column
        MFXTableColumn<CustomerDTO> dobColumn = new MFXTableColumn<>("Birthday", false);
        Function<CustomerDTO, String> mapDOB = u -> u.getCustomer().getDob().toString();
        dobColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(mapDOB));

        // Gender Column
        MFXTableColumn<CustomerDTO> genderColumn = new MFXTableColumn<>("Gender", false);
        Function<CustomerDTO, String> mapGenderTitle = u -> u.getCustomer().getGender().toString();
        genderColumn.setRowCellFactory(dto -> new MFXTableRowCell<>(mapGenderTitle));

        // Add Column
        List<MFXTableColumn<CustomerDTO>> tableColumnList = Arrays.asList(
                firstNameColumn, lastNameColumn,
                emailColumn, phoneColumn,
                dobColumn, genderColumn
        );
        tableColumnList.forEach(col -> col.prefWidthProperty().bind(customerTableView.widthProperty().multiply(0.167)));
        customerTableView.getTableColumns().addAll(tableColumnList);

        // Filter
        Function<CustomerDTO, GENDER_TYPE> mapGender = u -> u.getCustomer().getGender();
        EnumFilter<CustomerDTO, GENDER_TYPE> genderFilter = new EnumFilter<>("Gender", mapGender, GENDER_TYPE.class);
        customerTableView.getFilters().add(genderFilter);
        customerTableView.getSelectionModel().selectionProperty().addListener((MapChangeListener<Integer, CustomerDTO>) change -> {
            if (!change.getMap().values().isEmpty()) {
                Integer currentIndex = (Integer) change.getMap().keySet().toArray()[0];
                if (Objects.equals(currentIndex, selectedIndex)) {
                    return;
                }
                CustomerDTO customerDTO = (CustomerDTO) change.getMap().values().toArray()[0];
                selectedIndex = currentIndex;
                showCustomerDetail(customerDTO);
            }
        });
    }
    private void getData() {
        ICustomerRepo customerRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.CUSTOMER);
        List<CustomerDTO> customerDTOList = customerRepo.getListCustomer("");
//        customerDTOList.get(0).getCustomer().setLastName("asoidjasoidjsioadjasiojdsaiodjasoi");
        ObservableList<CustomerDTO> customers = FXCollections.observableArrayList(customerDTOList);
        customerTableView.setRowsPerPage(15);
        customerTableView.setItems(customers);
    }

    private void saveCustomer(CustomerDTO customerDTO) {
        boolean isUpdate = customerDTO.getCustomer().getId() > 0;
        ICustomerRepo customerRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.CUSTOMER);
        try {
            CustomerDTO result = customerRepo.createOrUpdate(customerDTO);
            if (isUpdate) {
                customerTableView.getItems().set(selectedIndex, result);
            } else {
                customerTableView.getItems().add(result);
            }
            customerTableView.update();
            hiddenCustomerDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void showCustomerDetail(CustomerDTO customerDTO) {
        Parent root = null;
        CustomerDetailController customerDetailController = new CustomerDetailController(customerDTO);
        try {
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/CustomerDetail.fxml"));
            loader.setControllerFactory(c -> customerDetailController);
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (root == null) {
            return;
        }
        boolean isUpdate = customerDTO.getCustomer().getId() > 0;
        this.dialogContent = MFXGenericDialogBuilder.build()
                .makeScrollable(true)
                .setHeaderIcon(null)
                .setShowAlwaysOnTop(false)
                .setHeaderText(isUpdate ? "Customer Detail" : "Add New Customer")
                .setContent(root)
                .get();
        dialogContent.addActions(
                Map.entry(new MFXButton(isUpdate ? "Update" : "Create"), event -> {
                    if (customerDetailController.validate()) {
                        saveCustomer(customerDTO);
                    }
                }),
                Map.entry(new MFXButton("Cancel"), event -> {
                    hiddenCustomerDialog();
                })
        );
        this.dialogContent.setMaxSize(800, 800);
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(false)
                .setTitle("Dialogs")
                .setOwnerNode(rootAnchorPane)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();
        this.dialog.setOnHidden(e -> {
            this.dialog = null;
            this.dialogContent = null;
            this.customerTableView.getSelectionModel().clearSelection();
            this.selectedIndex = null;
        });
        this.dialog.showDialog();
    }
    private void hiddenCustomerDialog() {
        if (this.dialogContent == null || this.dialog == null) {
            return;
        }
        this.dialog.close();
    }

    @FXML
    private void onClickedAddNewCustomer(ActionEvent event) {
        showCustomerDetail(new CustomerDTO());
    }
}