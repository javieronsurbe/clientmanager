package es.mdef.clientmanager.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 3/05/15
 * Time: 0:09
 */
public class HttpsEnforcer implements Filter{
    private FilterConfig filterConfig;

    public static final String X_FORWARDED_PROTO = "x-forwarded-proto";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //TODO Hay que configurar el puerto segun entorno
        int sslPort=443;
        if(request.getServerPort()==8080){
            sslPort=8443;
        }

        if(request.getServerPort()==80||request.getServerPort()==8080){
            response.sendRedirect("https://" + request.getServerName()+ ":"+ sslPort + request.getPathInfo());
            return;
        }
 /*       if (request.getHeader(X_FORWARDED_PROTO) != null) {
            if (request.getHeader(X_FORWARDED_PROTO).indexOf("https") != 0) {
                response.sendRedirect("https://" + request.getServerName() + request.getPathInfo());
                return;
            }
        }
*/
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // nothing
    }
}
