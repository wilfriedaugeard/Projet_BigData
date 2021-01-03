package bigdata.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tweet implements Serializable{
    private String created_at;
    private String id_str;
    private String text;
    private String in_reply_to_status_id_str;
    private String in_reply_to_user_id_str;
    private String in_reply_to_screen_name;
    private User user;
    private boolean favorited;
    private boolean retweeted;
    private String lang;
    private Entities entities; 

    public Entities getEntities() {
        return this.entities;
    }

    public User getUser() {
        return this.user;
    }

    public String getLang() {
        return this.lang;
    }

    public boolean isAvailable(){
        return this.created_at != null;
    } 

    @Override
    public String toString(){
        return "Tweet {"+
        "\n - created_at: "+this.created_at+ 
        "\n - id: "+this.id_str+
        "\n - text: "+this.text+
        "\n - in_reply_to_status_id: "+this.in_reply_to_status_id_str+
        "\n - in_reply_to_user_id: "+this.in_reply_to_user_id_str+
        "\n - in_reply_to_screen_name: "+this.in_reply_to_screen_name+
        "\n - user: "+this.user+
        "\n - favorited: "+this.favorited+
        "\n - retweeted: "+this.retweeted+
        "\n - lang: "+this.lang+
        "\n - entities: "+this.entities+
        "\n}";
    } 	 
} 