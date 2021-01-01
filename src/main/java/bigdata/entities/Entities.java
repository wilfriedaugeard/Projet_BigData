package bigdata.entities;

import java.io.Serializable;
import java.util.List;

public class Entities implements Serializable{
    private List<Hashtag>  hashtags;
    private List<Mention> user_mentions; 
    

    @Override
    public String toString(){
        return "{"+
        "\n\t\t - hashtags: "+this.hashtags+
        "\n\t\t - user_mentions: "+this.user_mentions+
        "\n\t\t}";
    } 	 
} 