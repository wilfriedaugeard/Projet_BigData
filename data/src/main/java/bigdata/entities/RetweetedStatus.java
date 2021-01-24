package bigdata.entities;

import java.io.Serializable;

public class RetweetedStatus extends BigDataObject implements IBigDataObject{
    private long retweet_count;

    public long getRetweet_count(){
        return this.retweet_count;
    }

    @Override
    public boolean equals(Object o){
	    if(o == this) {
		    return true;
	    }
	    if(! (o instanceof RetweetedStatus)){
		    return false;
	    }
	    RetweetedStatus r = (RetweetedStatus) o;
	    return this.retweet_count == r.getRetweet_count();
    }

	@Override
	public int hashCode(){
		int result = 17;
	    result = 31 * result + (int)this.retweet_count;
	    return result;
	}
}
