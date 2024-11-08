package planing.poker.common;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ErrorStatusFilter implements Filter {

    private final String attributeStatusCode;

    @Autowired
    public ErrorStatusFilter(@Value("${error.status.code.attribute}") final String attributeStatusCode) {
        this.attributeStatusCode = attributeStatusCode;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        try {
            chain.doFilter(request, response);
        } finally {
            if (httpResponse.getStatus() >= 400) {
                httpRequest.setAttribute(attributeStatusCode, httpResponse.getStatus());
            }
        }
    }
}
