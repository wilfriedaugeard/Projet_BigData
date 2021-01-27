package bigdata.entities;

import java.io.Serializable;

import bigdata.util.GsonFactory;
import scala.Tuple2;

public class User extends BigDataObject implements IBigDataObject {
    private String id_str;
    private String name;
    private String screen_name;
    private String location;
    private String verified;
    private String created_at;
    private long followers_count;
    private long friends_count;
    private long reply_count;
    private long favorite_count;
    private String profile_image_url_https;

    public String getId() {
        return this.id_str;
    }

    public long getFollowers() {
        return this.followers_count;
    }

    public String getUserInfo() {
        Tuple2 tuple = new Tuple2(this.id_str, this.name);
        return GsonFactory.create().toJson(tuple);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User h = (User) o;
        return this.id_str.equals(h.getId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.id_str.hashCode();
        return result;
    }

}

