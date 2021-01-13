package bigdata.entities;

import java.io.Serializable;

public class User extends BigDataObject implements IBigDataObject {
    private String id_str;
    private String name;
    private String screen_name;
    private String location;
    private String verified;
    private long followers_count;
    private long friends_count;
    private String created_at;
    private long reply_count;
    private long favorite_count;


    public String getId() {
        return this.id_str;
    }

    public long getFollowers() {
        return this.followers_count;
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