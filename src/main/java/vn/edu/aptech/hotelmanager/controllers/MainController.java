package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoader;
import io.github.palexdev.materialfx.utils.others.loader.MFXLoaderBean;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static vn.edu.aptech.hotelmanager.HMResourcesLoader.loadURL;

public class MainController implements Initializable {

    private final Stage stage;
    private double xOffset;
    private double yOffset;
    private final ToggleGroup toggleGroup;

    @FXML
    private HBox windowHeader;

    @FXML
    private MFXFontIcon closeIcon;

    @FXML
    private MFXFontIcon minimizeIcon;

    @FXML
    private MFXFontIcon alwaysOnTopIcon;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private MFXScrollPane scrollPane;

    @FXML
    private VBox navBar;

    @FXML
    private StackPane contentPane;

    @FXML
    private StackPane logoContainer;

    public MainController(Stage stage) {
        this.stage = stage;
        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean newVal = !stage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
            stage.setAlwaysOnTop(newVal);
        });

        windowHeader.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        windowHeader.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        initializeLoader();
//
        ScrollUtils.addSmoothScrolling(scrollPane);
//        // The only way to get a fucking smooth image in this shitty framework
//        Image image = new Image(HMResourcesLoader.load("logo_alt.png"), 64, 64, true, true);
//        ImageView logo = new ImageView(image);
//        Circle clip = new Circle(30);
//        clip.centerXProperty().bind(logo.layoutBoundsProperty().map(Bounds::getCenterX));
//        clip.centerYProperty().bind(logo.layoutBoundsProperty().map(Bounds::getCenterY));
//        logo.setClip(clip);
//        logoContainer.getChildren().add(logo);
    }

    private void initializeLoader() {
        MFXLoader loader = new MFXLoader();
        loader.addView(MFXLoaderBean.of("ROOMS", loadURL("fxml/Room.fxml"))
                .setBeanToNodeMapper(() -> createToggle("fas-hotel", "Danh sách phòng"))
                .setDefaultRoot(true)
                .setControllerFactory(c -> new RoomController(stage))
                .get());
        loader.addView(MFXLoaderBean.of("CUSTOMERS", loadURL("fxml/Customer.fxml"))
                .setBeanToNodeMapper(() -> createToggle("fas-users", "Danh sách khách hàng"))
                .setControllerFactory(c -> new CustomerController(stage))
                .get());
        loader.addView(MFXLoaderBean.of("SALES", loadURL("fxml/Sales.fxml"))
                .setBeanToNodeMapper(() -> createToggle("fas-cart-plus", "Quản lý bán hàng"))
                .get());
        loader.addView(MFXLoaderBean.of("WAREHOUSES", loadURL("fxml/Warehouse.fxml"))
                .setBeanToNodeMapper(() -> createToggle("fas-store", "Quản lý kho"))
                .get());
        loader.addView(MFXLoaderBean.of("REPORTS", loadURL("fxml/Report.fxml")
        ).setBeanToNodeMapper(() -> createToggle("fas-chart-pie", "Báo cáo"))
                .get());
        loader.setOnLoadedAction(beans -> {
            List<ToggleButton> nodes = beans.stream()
                    .map(bean -> {
                        ToggleButton toggle = (ToggleButton) bean.getBeanToNodeMapper().get();
                        toggle.setOnAction(event -> contentPane.getChildren().setAll(bean.getRoot()));
                        if (bean.isDefaultView()) {
                            contentPane.getChildren().setAll(bean.getRoot());
                            toggle.setSelected(true);
                        }
                        return toggle;
                    })
                    .toList();
            navBar.getChildren().setAll(nodes);
        });
        loader.start();
    }
    private ToggleButton createToggle(String icon, String text) {
        return createToggle(icon, text, 0);
    }

    private ToggleButton createToggle(String icon, String text, double rotate) {
        MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);
        MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
        toggleNode.setAlignment(Pos.CENTER_LEFT);
        toggleNode.setMaxWidth(Double.MAX_VALUE);
        toggleNode.setToggleGroup(toggleGroup);
        if (rotate != 0) wrapper.getIcon().setRotate(rotate);
        return toggleNode;
    }
}