package com.yubin.cat.configuration;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * CAT相关的Filter
 *
 * @Author YUBIN
 * @create 2018-12-16
 */
public class CatServletFilter implements Filter {
    private String[] urlPatterns = new String[0];

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String patterns = filterConfig.getInitParameter("CatHttpModuleUrlPatterns");
        if (patterns != null) {
            patterns = patterns.trim();
            this.urlPatterns = patterns.split(",");

            for(int i = 0; i < this.urlPatterns.length; ++i) {
                this.urlPatterns[i] = this.urlPatterns[i].trim();
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI().toString();
        String[] urlPatterns = this.urlPatterns;
        int length = urlPatterns.length;

        for(int i = 0; i < length; ++i) {
            String urlPattern = urlPatterns[i];
            if (uri.startsWith(urlPattern)) {
                uri = urlPattern;
            }
        }

        Transaction t = Cat.newTransaction("Service", uri);

        try {
            PropertyContext propertyContext = new PropertyContext();
            propertyContext.addProperty("_catRootMessageId", request.getHeader("X-CAT-ROOT-ID"));
            propertyContext.addProperty("_catParentMessageId", request.getHeader("X-CAT-PARENT-ID"));
            propertyContext.addProperty("_catChildMessageId", request.getHeader("X-CAT-CHILD-ID"));
            Cat.logRemoteCallServer(propertyContext);
            Cat.logEvent("Service.method", request.getMethod(), Message.SUCCESS, request.getRequestURL().toString());
            Cat.logEvent("Service.client", request.getRemoteHost());
            String clientApp = request.getHeader("X-PPD-CAT-APP");
            Cat.logEvent("Service.app", clientApp == null ? "unknown" : clientApp);
            filterChain.doFilter(servletRequest, servletResponse);
            t.setStatus(Message.SUCCESS);
        } catch (Exception ex) {
            t.setStatus(ex);
            Cat.logError(ex);
            throw ex;
        } finally {
            t.complete();
        }
    }

    @Override
    public void destroy() {

    }

    private static class PropertyContext implements Cat.Context {
        private Map<String, String> properties = new HashMap();

        PropertyContext() {
        }

        public void addProperty(String key, String value) {
            this.properties.put(key, value);
        }

        public String getProperty(String key) {
            return (String)this.properties.get(key);
        }
    }
}
