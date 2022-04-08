package com.jaab.revature.principal;

import com.jaab.revature.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        String redirectUrl = request.getContextPath();

        if (userDetails.getRole() == Role.ADMIN)
            redirectUrl += "/admin/" + userDetails.getUserId();

        if (userDetails.getRole() == Role.EMPLOYEE)
            redirectUrl += "/employee/" + userDetails.getUserId();

        response.sendRedirect(redirectUrl);
    }
}
