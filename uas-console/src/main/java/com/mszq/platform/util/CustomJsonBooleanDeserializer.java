package com.mszq.platform.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class CustomJsonBooleanDeserializer extends JsonDeserializer<Integer> {  
	  
    @Override  
    public Integer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {  
                String data = jp.getText(); 
                if("true".equals(data)){
                	return 1;
                }else{
                	return 0;
                }
    }  
  
}  