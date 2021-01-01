package bigdata.entities;

import java.io.Serializable;

public class Mention implements Serializable{
    private String name;
    private String screen_name;
    private String id_str;

    @Override
    public String toString(){
        return ""+
        "\n\t\t\t - name: "+this.name+
        "\n\t\t\t - screen_name: "+this.screen_name+
        "\n\t\t\t - id_str: "+this.id_str+
        "\n\t\t\t";
    } 	
} 
