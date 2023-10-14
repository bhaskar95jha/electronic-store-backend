package com.bhaskar.store.management.exceptions;

public class BadApiRequest extends RuntimeException{

    public BadApiRequest(){
        super("Bad Api Request Extenstion");
    }
    public BadApiRequest(String message){
        super(message);
    }
}
