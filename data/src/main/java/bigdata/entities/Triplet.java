package bigdata.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bigdata.entities.Hashtag;

public class Triplet extends BigDataObject implements IBigDataObject {
    private List<Hashtag> triplet;

    public Triplet() {
        this.triplet = new ArrayList<>();
    }

    public void add(Hashtag h) {
        if (this.triplet.isEmpty()) {
            this.triplet.add(h);
            return;
        }
        this.triplet.add(h);
        Map<String, Hashtag> map = new HashMap<>();
        this.triplet.forEach(hashtag -> map.put(hashtag.getText(), hashtag));
        List<String> list = new ArrayList<>(map.keySet());
        Collections.sort(list);
        this.triplet.clear();
        list.forEach(hashtag -> this.triplet.add(map.get(hashtag)));
    }


    public List<Hashtag> getTriplet() {
        return this.triplet;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Triplet)) {
            return false;
        }
        Triplet t = (Triplet) o;
        if (t.getTriplet().size() != this.triplet.size()) {
            return false;
        }
        for (int i = 0; i < t.getTriplet().size(); i++) {
            if (!t.getTriplet().get(i).equals(this.triplet.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        for (Hashtag h : this.triplet) {
            result = result * 31 + h.hashCode();
        }
        return result;
    }
}
