package ua.foxminded.pskn.universitycms.customexception;

public class UserCreateException extends RuntimeException{
    public UserCreateException(String message){
        super(message);
    }
}
