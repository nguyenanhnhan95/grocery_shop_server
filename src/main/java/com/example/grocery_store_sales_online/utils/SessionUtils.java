package com.example.grocery_store_sales_online.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Base64;
import java.util.Optional;

import static com.example.grocery_store_sales_online.utils.CommonConstants.SESSION_ATTEMPT_LOGIN;

public class SessionUtils {

    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(org.springframework.util.SerializationUtils.serialize(object));
    }


    public static Object deserialize(String base64EncodedObject) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(base64EncodedObject);
        return SerializationUtils.deserialize(decodedBytes);
    }


    public static Optional<Object> getSessionAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return Optional.ofNullable(session.getAttribute(name));
        }
        return Optional.empty();
    }

    public static void addSessionAttribute(HttpServletRequest request, String name, Object value) {
        HttpSession session = request.getSession(true); // Tạo session nếu chưa có
        session.setAttribute(name, value);
    }

    public static void removeSessionAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false); // Không tạo session nếu chưa có
        if (session != null) {
            session.removeAttribute(name);
        }
    }
    public static void memoryAttemptLoginSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer loginAttempts = (Integer) session.getAttribute(SESSION_ATTEMPT_LOGIN);
        if (loginAttempts == null) {
            loginAttempts = 0;
        }
        session.setAttribute(SESSION_ATTEMPT_LOGIN, loginAttempts + 1);
    }
    public static boolean hasSessionAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(name) != null;
    }
}
