package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.repo.dao.CityDAO;
import vn.edu.aptech.hotelmanager.repo.dao.DAOFactory;
import vn.edu.aptech.hotelmanager.repo.dao.DAO_TYPE;
import vn.edu.aptech.hotelmanager.repo.entity.CityEntity;

import java.util.Map;

public class RoomController {
    private final IRoomRepo roomRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);
    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;
    private final Stage stage;

    @FXML
    private GridPane grid;
    public RoomController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onClickedTapMe(ActionEvent event) {
        CityDAO cityDAO = DAOFactory.getInstance().getDAO(DAO_TYPE.CITY);
        try {
//            CityEntity city = new CityEntity();
//            city.setName("Ha Noi");
//            cityDAO.save(city);
            cityDAO.findAll().forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        String title = "Cơn Mưa Ngang Qua";
//        String message = "Hoh\n" +
//                "Cơn mưa ngang qua (cơn mưa ngang qua)\n" +
//                "Cơn mưa đi ngang qua (cơn mưa đi ngang qua)\n" +
//                "Đừng làm rơi thêm thêm thêm nhiều giọt lệ\n" +
//                "Còn đâu đây bao câu ca anh tặng em (uh ah uh ah)\n" +
//                "Tình yêu em mang cuốn lấp đi bao nhiêu câu ca\n" +
//                "Và còn lại đây đôi môi đau thương trong màn đêm (i a i a i a i a)\n" +
//                "Phải lẻ loi gồng mình bước qua niềm đau khi em rời xa\n" +
//                "Cơn mưa rồi lại thêm lại thêm lại thêm\n" +
//                "Xé đi không gian ngập tràn nụ cười\n" +
//                "Nhìn lại nơi đây bao kỉ niệm giờ chìm xuống dưới hố sâu vì em\n" +
//                "Chính em đã đổi thay\n" +
//                "Và đôi bàn tay ấm êm ngày nào còn lại giữ\n" +
//                "Vì em vì em (i a i a i a i a)\n" +
//                "Vì em đã xa rồi tình anh chìm trong màn đêm\n" +
//                "Là vì em đã quên rồi tình anh chỉ như giấc mơ\n" +
//                "Em bước đi rồi ôi bao cơn mưa\n" +
//                "Em bước đi rồi xin hãy xua tan đi em\n" +
//                "Bóng dáng hình em em đã mãi rời xa\n" +
//                "My girl em đã quên đi bao nhiêu\n" +
//                "My girl em đã quên đi bao lâu\n" +
//                "My girl em đã quên đi cuộc tình mà anh trao em\n" +
//                "Thôi thôi em đi đi đã hết rồi\n" +
//                "My girl em đã quên đi bao nhiêu\n" +
//                "My girl em đã quên đi bao lâu\n" +
//                "My girl em quên đi cuộc tình\n" +
//                "Em quên quên quên quên quên quên quyên quên quên\n" +
//                "Cơn mưa ngang qua mang em đi xa\n" +
//                "Cơn mưa ngang qua khiến em nhạt nhòa\n" +
//                "Chẳng một lời chào người vội rời bỏ đi không chia li\n" +
//                "Cho con tim anh thêm bao yếu mềm\n" +
//                "Cơn mưa ngang qua cuốn đi bao yêu thương\n" +
//                "Cơn mưa ngang qua khiến con tim mất phương hướng\n" +
//                "Cơn mưa kia nặng hạt\n" +
//                "Ôi mưa thêm nặng hạt\n" +
//                "Em đã rời xa đôi bàn tay trong con tim của anh\n" +
//                "Em đi em đi rời xa bên tôi người ơi\n" +
//                "Và buông lơi giấc mơ em cho\n" +
//                "Em cho con tim tôi đau biết mấy\n" +
//                "Thôi cũng đã đến hồi kết thật rồi mà người\n" +
//                "Thôi cũng đã đến hồi kết đừng nhìn làm gì\n" +
//                "Anh sẽ quên đi một ai ai ai và rồi làm ngơ ngơ ngơ\n" +
//                "Vì em đã xa rồi tình anh chìm trong màn đêm\n" +
//                "Là vì em đã quên rồi tình anh chỉ như giấc mơ\n" +
//                "Em bước đi rồi ôi bao cơn mưa\n" +
//                "Em bước đi rồi xin hãy xua tan đi em\n" +
//                "Bóng dáng hình em em đã mãi rời xa\n" +
//                "My girl em đã quên đi bao nhiêu\n" +
//                "My girl em đã quên đi bao lâu\n" +
//                "My girl em đã quên đi cuộc tình mà anh trao em\n" +
//                "Thôi thôi em đi đi đã hết rồi\n" +
//                "Em đã quên đi bao nhiêu\n" +
//                "My girl em đã quên đi bao lâu\n" +
//                "My girl em quên đi cuộc tình\n" +
//                "My girl em quên quên quên quên quên quên quên quên quên\n" +
//                "Và rồi em đi qua bước qua\n" +
//                "Ở lại chốn đây với bao u buồn\n" +
//                "Để rồi tháng trôi qua rồi năm trôi qua thoáng qua hoh\n" +
//                "Nụ cười em đang ở đâu người ơi (ở đâu)\n" +
//                "Và bờ môi em đang ở đâu anh tìm\n" +
//                "Lục tìm mà không thấy nụ cười em\n" +
//                "Người hãy nói trả lời đi\n" +
//                "Vì sao em đi đi\n" +
//                "Quên bao nhiêu giấc mơ bên anh xưa kia (hoh)\n" +
//                "Cơn mưa vẫn rơi (rơi rơi)\n" +
//                "Cơn cơn mưa vẫn rơi (rơi rơi) (i ya)\n" +
//                "Cơn cơn mưa vẫn rơi rơi rơi (rơi)\n" +
//                "Cơn cơn mưa vẫn rơi rơi rơi rơi (yeh)\n" +
//                "Cơn mưa vẫn rơi (eh)\n" +
//                "Cơn cơn mưa vẫn rơi (rơi rơi)\n" +
//                "Cơn cơn mưa vẫn rơi (rơi rơi)\n" +
//                "Cơn cơn mưa vẫn\n" +
//                "Cơn cơn cơn cơn cơn cơn mưa vẫn rơi (ngoài kia)\n" +
//                "My girl em đã quên đi bao nhiêu\n" +
//                "My girl em đã quên đi bao lâu\n" +
//                "My girl em đã quên đi cuộc tình mà anh trao em (quên đi)\n" +
//                "Thôi thôi em đi đi đã hết rồi\n" +
//                "My girl em đã quên đi bao nhiêu\n" +
//                "My girl em đã quên đi bao lâu\n" +
//                "My girl em quên đi cuộc tình\n" +
//                "My girl quên quên quên quên quên quên quyên quên quên (em quên mất rồi)\n" +
//                "Em quên mất rồi";
//        showDialog(title, message, Map.entry(new MFXButton("Confirm"), e -> {
//                    // Handle when tap confirm
//                }),
//                Map.entry(new MFXButton("Cancel"), e -> {
//                    this.hiddenDialog();
//                })
//        );
    }
    @SafeVarargs
    private void showDialog(String title, String message, Map.Entry<Node, EventHandler<MouseEvent>>... actions) {
        this.dialogContent = MFXGenericDialogBuilder.build()
                .makeScrollable(true)
                .setHeaderIcon(null)
                .setShowAlwaysOnTop(false)
                .setHeaderText(title)
                .setContentText(message)
                .addActions(actions)
                .get();
        this.dialogContent.setMaxSize(400, 600);
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(false)
                .setTitle("Dialogs")
                .setOwnerNode(grid)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();
        this.dialog.showDialog();
    }
    private void hiddenDialog() {
        if (this.dialogContent == null || this.dialog == null) {
            return;
        }
        this.dialog.setOnHidden(e -> {
            this.dialog = null;
            this.dialogContent = null;
        });
        this.dialog.close();
    }
    private void convertDialogTo(String styleClass) {
        dialogContent.getStyleClass().removeIf(
                s -> s.equals("mfx-info-dialog") || s.equals("mfx-warn-dialog") || s.equals("mfx-error-dialog")
        );

        if (styleClass != null)
            dialogContent.getStyleClass().add(styleClass);
    }
}
