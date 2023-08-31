package ua.foxminded.pskn.universitycms.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecureAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String username = authentication.getName();

        if (role.equals("admin")) {
            response.sendRedirect("/adminscab?username=" + username);
        }
        if (role.equals("professor")) {
            response.sendRedirect("/professorscab?username=" + username);
        }
        if (role.equals("student")) {
            response.sendRedirect("/studentscab?username=" + username);
        }
        request.getSession().setAttribute("successMessage", "You have successfully logged in as " + role + " " + username);
    }
}
