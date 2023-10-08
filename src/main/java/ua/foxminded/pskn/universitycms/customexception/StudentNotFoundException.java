package ua.foxminded.pskn.universitycms.customexception;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String message){
        super(message);
    }
}
