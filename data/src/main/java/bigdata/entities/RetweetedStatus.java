package bigdata.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class RetweetedStatus extends BigDataObject {
    private long retweet_count;

    public long getRetweet_count(){
        return this.retweet_count;
    }
}