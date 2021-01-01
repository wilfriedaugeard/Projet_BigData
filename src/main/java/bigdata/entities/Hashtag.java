package bigdata.entities;

import java.io.Serializable;

public class Hashtag implements Serializable{
    private String text;

    @Override
    public String toString(){
        return ""+
        "\n\t\t\t - text: "+this.text+
        "\n\t\t\t";
    } 	

}