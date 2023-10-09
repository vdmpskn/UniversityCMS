package ua.foxminded.pskn.universitycms.customexception;

public class UserUpdateException extends RuntimeException {
    public UserUpdateException(String message) {
        super(message);
    }
}
