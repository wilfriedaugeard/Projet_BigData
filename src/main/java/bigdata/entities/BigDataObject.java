package bigdata.entities;

import java.io.Serializable;
import bigdata.util.GsonFactory;

public abstract class BigDataObject implements Serializable {

    @Override
    public String toString() {
        return GsonFactory.create().toJson(this);
    }

    public abstract String Save();
}
