package vn.edu.aptech.hotelmanager.domain.model;

import io.github.palexdev.materialfx.utils.FXCollectors;
import io.github.palexdev.mfxresources.fonts.IconDescriptor;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeBrands;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeRegular;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeSolid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.IntStream;


public class Model {
    public static final IconDescriptor[] notificationsIcons;
    public static final ObservableList<String> strings;

    public static final ObservableList<Account> accounts = null;

    static {
        notificationsIcons = new IconDescriptor[]{
                FontAwesomeSolid.BELL, FontAwesomeRegular.BELL,
                FontAwesomeSolid.CALENDAR, FontAwesomeSolid.CALENDAR_DAYS,
                FontAwesomeSolid.CHART_PIE, FontAwesomeSolid.CIRCLE, FontAwesomeRegular.CIRCLE,
                FontAwesomeSolid.CIRCLE_EXCLAMATION, FontAwesomeSolid.TRIANGLE_EXCLAMATION,
                FontAwesomeSolid.GEAR, FontAwesomeBrands.GOOGLE_DRIVE, FontAwesomeSolid.HOUSE,
                FontAwesomeSolid.CIRCLE_INFO, FontAwesomeSolid.MUSIC,
                FontAwesomeSolid.USER, FontAwesomeSolid.USERS, FontAwesomeSolid.VIDEO,
                FontAwesomeSolid.CIRCLE_XMARK
        };

        strings = IntStream.rangeClosed(1, 25)
                .mapToObj(i -> "String " + i)
                .collect(FXCollectors.toList());

    }
}