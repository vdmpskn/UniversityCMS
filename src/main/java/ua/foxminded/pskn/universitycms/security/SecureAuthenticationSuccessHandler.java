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
        String username = authentication.getName();

        String role = (authentication.getAuthorities().iterator().next().getAuthority());

        if (role.equals("ROLE_1")) {
            response.sendRedirect("/adminscab?username=" + username);
        }
        if (role.equals("ROLE_3")) {
            response.sendRedirect("/professorscab?username=" + username);
        }
        if (role.equals("ROLE_2")) {
            response.sendRedirect("/studentscab?username=" + username);
        }
        request.getSession().setAttribute("successMessage", "You have successfully logged in as " + " " + username);
    }
}
