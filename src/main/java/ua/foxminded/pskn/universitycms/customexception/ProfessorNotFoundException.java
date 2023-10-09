package ua.foxminded.pskn.universitycms.customexception;

public class ProfessorNotFoundException extends RuntimeException{

    public ProfessorNotFoundException(String message){
        super(message);
    }

}
