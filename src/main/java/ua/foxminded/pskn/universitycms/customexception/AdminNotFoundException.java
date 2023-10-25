package ua.foxminded.pskn.universitycms.customexception;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(String message){
        super(message);
    }
}
