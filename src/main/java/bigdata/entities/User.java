package bigdata.entities;

import java.io.Serializable;

public class User implements Serializable{
    private String id_str;
    private String name;
    private String screen_name;
    private String location;
    private String verified;
    private long followers_count;
    private long friends_count;
    private String created_at;
    private long reply_count;
    private long retweet_count;
    private long favorite_count;
    

    @Override
    public String toString(){
        return "{"+
        "\n\t\t- id: "+this.id_str+
        "\n\t\t- name: "+this.name+
        "\n\t\t- screen_name: "+this.screen_name+
        "\n\t\t- location: "+this.location+
        "\n\t\t- verified: "+this.verified+
        "\n\t\t- created_at: "+this.created_at+
        "\n\t\t- followers_count: "+this.followers_count+
        "\n\t\t- friends_count: "+this.friends_count+
        "\n\t\t- reply_count: "+this.reply_count+
        "\n\t\t- retweet_count: "+this.retweet_count+
        "\n\t\t- favorite_count: "+this.favorite_count+
        "\n\t\t}";
    } 	 

} 