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
    

    public String getId() {
        return this.id_str;
    }

    @Override
    public String toString(){
        return 
        "\n  {"+
        "\n   - id: "+this.id_str+
        "\n   - name: "+this.name+
        "\n   - screen_name: "+this.screen_name+
        "\n   - location: "+this.location+
        "\n   - verified: "+this.verified+
        "\n   - created_at: "+this.created_at+
        "\n   - followers_count: "+this.followers_count+
        "\n   - friends_count: "+this.friends_count+
        "\n   - reply_count: "+this.reply_count+
        "\n   - retweet_count: "+this.retweet_count+
        "\n   - favorite_count: "+this.favorite_count+
        "\n  }";
    } 	 

} 