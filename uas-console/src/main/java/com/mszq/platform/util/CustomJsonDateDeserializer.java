package com.mszq.platform.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {  
	  
    @Override  
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {  
        SimpleDateFormat format = null;
                String date = jp.getText();  
                    if(date.length()==8){
                    	format=new SimpleDateFormat("yyyyMMdd");
                    }
                    else if(date.length()==19){
	                	format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	                }else{
	                	format=new SimpleDateFormat("yyyy-MM-dd");
	                }
                    try {
						return format.parse(date);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw new IOException(e.getMessage(),e);
					}  
    }  
  
}  