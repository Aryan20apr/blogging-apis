//package com.aryan.blogging.bloggingapis.config;
//
//import java.io.IOException;
//import java.util.Enumeration;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequestWrapper;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class JsonRequestHeaderFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper((HttpServletRequest) request) {
//            @Override
//            public Enumeration<String> getHeaders(String name) {
//                if (name.equals("Accept")) {
//                    Set<String> customHeaders = new HashSet<String>();
//                    Enumeration<String> curHeaders = super.getHeaders(name);
//                    while (curHeaders.hasMoreElements()) {
//                        String header = curHeaders.nextElement();
//                        customHeaders.add(MediaType.APPLICATION_JSON_VALUE.concat(";").concat(header));
//                    }
//
//                    return Collections.enumeration(customHeaders);
//                }
//                return super.getHeaders(name);
//            }
//        };
//
//        chain.doFilter(requestWrapper, response);
//    }
//}