package bigdata.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tweet{
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

    public boolean isAvailable(){
        return this.created_at != null;
    } 

    @Override
    public String toString(){
        return "Tweet {"+
        "\n\t- created_at: "+this.created_at+ 
        "\n\t- id: "+this.id_str+
        "\n\t- text: "+this.text+
        "\n\t- in_reply_to_status_id: "+this.in_reply_to_status_id_str+
        "\n\t- in_reply_to_user_id: "+this.in_reply_to_user_id_str+
        "\n\t- in_reply_to_screen_name: "+this.in_reply_to_screen_name+
        "\n\t- user: "+this.user+
        "\n\t- favorited: "+this.favorited+
        "\n\t- retweeted: "+this.retweeted+
        "\n\t- lang: "+this.lang+
        "\n\t- entities: "+this.entities+
        "\n\t}";
    } 	 
} 