package bigdata.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
    public static final Gson create() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
