package ua.foxminded.pskn.universitycms.customexception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
