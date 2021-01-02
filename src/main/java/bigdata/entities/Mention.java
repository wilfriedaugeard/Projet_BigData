package bigdata.entities;

import java.io.Serializable;

public class Mention implements Serializable{
    private String name;
    private String screen_name;
    private String id_str;

    @Override
    public String toString(){
        return
        "\n   - name: "+this.name+
        "\n   - screen_name: "+this.screen_name+
        "\n   - id_str: "+this.id_str+
        "\n";
    } 	
} 
