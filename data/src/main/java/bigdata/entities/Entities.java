package bigdata.entities;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Entities extends BigDataObject {
    private List<Hashtag> hashtags;
    private List<Mention> user_mentions;


    public List<Hashtag> getHashtags() {
        return (!this.hashtags.isEmpty() && this.hashtags != null) ? this.hashtags : new ArrayList<>();
    }
} 