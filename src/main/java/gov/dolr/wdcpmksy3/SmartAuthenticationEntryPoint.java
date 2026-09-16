package gov.dolr.wdcpmksy3;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.List;

/**
 * Default Spring Security behaviour redirects EVERY unauthenticated,
 * non-permitAll request to /login — including URLs that don't map to
 * any real controller (typos, random paths). That's misleading: a
 * typo'd URL should show a 404 page, not a login prompt.
 *
 * This entry point checks whether the request actually resolves to a
 * real @Controller/@RequestMapping handler. If it doesn't, it forwards
 * to /error (404) instead of redirecting to /login.
 *
 * IMPORTANT: we only check RequestMappingHandlerMapping here, NOT every
 * registered HandlerMapping. Spring Boot also registers a static
 * resource handler mapped to "/**" (for classpath:/static, /public,
 * etc.) — that mapping matches ANY path at resolution time (it only
 * fails later, inside the handler, if the file doesn't exist). If we
 * checked that mapping too, every URL would "resolve", defeating the
 * whole point of this class.
 */
@Component
public class SmartAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final List<RequestMappingHandlerMapping> handlerMappings;

    public SmartAuthenticationEntryPoint(List<RequestMappingHandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void commence(HttpServletRequest request,
                          HttpServletResponse response,
                          AuthenticationException authException)
            throws IOException, ServletException {

        if (resolvesToRealHandler(request)) {
            // Real, protected controller route -> normal login flow
            response.sendRedirect("/login");
        } else {
            // No matching @Controller route at all -> treat as a genuine 404
            request.setAttribute("jakarta.servlet.error.status_code", 404);
            request.getRequestDispatcher("/error").forward(request, response);
        }
    }

    private boolean resolvesToRealHandler(HttpServletRequest request) {
        for (RequestMappingHandlerMapping mapping : handlerMappings) {
            try {
                HandlerExecutionChain chain = mapping.getHandler(request);
                if (chain != null) {
                    return true;
                }
            } catch (Exception ignored) {
                // this mapping couldn't resolve it — try the next one
            }
        }
        return false;
    }
}