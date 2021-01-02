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

   @Override
   public boolean equals(Object o){
       if(o == this){
           return true;
       } 
       if(!(o instanceof Hashtag)){
           return false;
       } 
       Hashtag h = (Hashtag) o;
       return this.text.toLowerCase().equals(h.getText().toLowerCase());
   } 

  @Override
  public int hashCode(){
      int result = 17;
      result = 31 * result + this.text.toLowerCase().hashCode();
      return result;
  }  

}