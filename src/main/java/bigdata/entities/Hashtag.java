package bigdata.entities;

import java.io.Serializable;

public class Hashtag implements Serializable{
    private String text;

    public String getText() {
        return this.text;
    }

    @Override
    public String toString(){
        return this.text;
    } 	

}