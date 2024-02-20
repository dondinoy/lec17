package com.example.myblogproject.error;

import java.util.function.Supplier;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String entityName,String property,Object value){
        super("Entity %s with %s=%s not Found".formatted(entityName,property,value));
    }
    public static Supplier<RuntimeException>newInstance(String entityName,String property,Object value ){
        return()-> new ResourceNotFoundException(entityName, property, value);
    }
}
