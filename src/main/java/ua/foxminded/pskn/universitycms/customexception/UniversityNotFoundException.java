package ua.foxminded.pskn.universitycms.customexception;

public class UniversityNotFoundException extends RuntimeException {

    public UniversityNotFoundException(String message) {
        super(message);
    }
}
