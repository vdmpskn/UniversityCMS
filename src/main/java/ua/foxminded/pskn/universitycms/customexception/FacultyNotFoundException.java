package ua.foxminded.pskn.universitycms.customexception;

public class FacultyNotFoundException extends RuntimeException {

    public FacultyNotFoundException(String message) {
        super(message);
    }
}
