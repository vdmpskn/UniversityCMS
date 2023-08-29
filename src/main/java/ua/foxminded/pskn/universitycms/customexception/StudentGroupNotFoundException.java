package ua.foxminded.pskn.universitycms.customexception;

public class StudentGroupNotFoundException extends RuntimeException{
    public StudentGroupNotFoundException(String message){
        super(message);
    }
}
