package com.platzipizzeria.service.exception;

public class EmailApiEception extends RuntimeException{
    public EmailApiEception(){
        super("error sending email");
    }

}
