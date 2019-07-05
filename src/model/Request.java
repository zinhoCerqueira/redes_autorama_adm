package model;

import java.io.Serializable;
/**
 *
 * @author jader
 */
public class Request implements Serializable{
    private static final long serialVersionUID = 1L;
    private String tag;
    private Object params;
    
    public Request(String tag, Object params){
        this.setTag(tag);
        this.setParams(params);
    }
    
    public Request(String tag){
        this.setTag(tag);
    }
    
    public String getTag(){
        return tag;
    }
    
    public void setTag(String tag){
        this.tag = tag;
    }
    
    public Object getParams(){
        return params;
    }
    
    public void setParams(Object params){
        this.params = params;
    }
}

