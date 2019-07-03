package com.mszq.platform.util;

public class ExpressionException extends RuntimeException{  
  
    private static final long serialVersionUID = 1L;  
  
    public ExpressionException() {  
        super();  
    }  
  
    public ExpressionException(String msg) {  
        super(msg);  
    }  
      
    public ExpressionException(String msg, Throwable cause) {  
        super(msg,cause);  
    }  
      
    public ExpressionException(Throwable cause) {  
        super(cause);  
    }  
}
