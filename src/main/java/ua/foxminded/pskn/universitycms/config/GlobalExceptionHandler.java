package ua.foxminded.pskn.universitycms.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ua.foxminded.pskn.universitycms.customexception.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "An error occurred: " + ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UniversityNotFoundException.class)
    public ModelAndView handleUniversityNotFoundException(UniversityNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "University not found: " + ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException(NullPointerException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "An error occurred: " + ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    public ModelAndView handleNullPointerException(FacultyNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "FacultyNotFoundException: " + ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UniversityEditException.class)
    public ModelAndView handleUniversityEditException(UniversityEditException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "UniversityEditException: " + ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(StudentGroupEditException.class)
    public ModelAndView handleUniversityEditException(StudentGroupEditException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "UniversityEditException: " + ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(StudentGroupNotFoundException.class)
    public ModelAndView handleUniversityEditException(StudentGroupNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "UniversityEditException: " + ex.getMessage());
        return modelAndView;
    }

}

