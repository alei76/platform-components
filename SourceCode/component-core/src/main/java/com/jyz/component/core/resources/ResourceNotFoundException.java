package com.jyz.component.core.resources;

import java.io.IOException;

/**
 * 找不到资源文件
 * @author JoyoungZhang@gmail.com
 *
 */
public class ResourceNotFoundException extends IOException{

    private static final long serialVersionUID = 1L;
    
    public ResourceNotFoundException() {
	super();
    }
    
    public ResourceNotFoundException(String message) {
    	super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
    	super(message, cause);
    }
    
    public ResourceNotFoundException(Throwable cause) {
    	super(cause);
    }

}
