package utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResponseUtil {
    private static final Gson gson = new Gson();

    public static void sendJson(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = gson.toJson(data);
        response.getWriter().write(json);
    }

    public static void sendSuccess(HttpServletResponse response, Object data) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        sendJson(response, Map.of(
            "success", true,
            "data", data
        ));
    }

    public static void badRequest(HttpServletResponse response, String message) throws IOException {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    public static void unauthorized(HttpServletResponse response, String message) throws IOException {
        sendError(response, HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    public static void forbidden(HttpServletResponse response, String message) throws IOException {
        sendError(response, HttpServletResponse.SC_FORBIDDEN, message);
    }

    public static void notFound(HttpServletResponse response, String message) throws IOException {
        sendError(response, HttpServletResponse.SC_NOT_FOUND, message);
    }

    public static void serverError(HttpServletResponse response, String message) throws IOException {
        sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }

    public static void sendError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = gson.toJson(Map.of(
            "success", false,
            "error", message,
            "status", statusCode
        ));
        response.getWriter().write(json);
    }
}
