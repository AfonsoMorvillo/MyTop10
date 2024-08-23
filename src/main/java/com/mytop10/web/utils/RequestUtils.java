package com.mytop10.web.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

   public static String getOperation(HttpServletRequest request) {
       String requestUri = request.getRequestURI();
       String contextPath = request.getContextPath();
       int operationStartIndex = requestUri.indexOf(contextPath) + contextPath.length();
       return requestUri.substring(operationStartIndex);
   }
}
