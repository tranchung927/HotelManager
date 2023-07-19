package vn.edu.aptech.hotelmanager;

import java.io.InputStream;
import java.net.URL;

public class HMResourcesLoader {
    private HMResourcesLoader() {
    }

    public static URL loadURL(String path) {
        return HMResourcesLoader.class.getResource(path);
    }

    public static String load(String path) {
        return loadURL(path).toString();
    }

    public static InputStream loadStream(String name) {
        return HMResourcesLoader.class.getResourceAsStream(name);
    }
}
