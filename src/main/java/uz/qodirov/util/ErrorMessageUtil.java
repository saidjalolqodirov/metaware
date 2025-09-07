package uz.qodirov.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorMessageUtil {
    public static String getErrorMessage(Throwable e) {
        return e.getMessage() == null ? "error message is empty!" : (e.getMessage().length() > 512 ? e.getMessage().substring(0, 512) : e.getMessage());
    }

    public static String getErrorMessage(String errorMessage) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(errorMessage);

            if (root.has("error")) {
                JsonNode errorNode = root.get("error");
                if (errorNode.has("message")) {
                    JsonNode msgNode = errorNode.get("message");
                    if (msgNode.has("uz")) return msgNode.get("uz").asText();
                    if (msgNode.has("en")) return msgNode.get("en").asText();
                    if (msgNode.has("ru")) return msgNode.get("ru").asText();
                    return msgNode.toString();
                }
            }

            if (root.has("message")) {
                return root.get("message").asText();
            }

        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            return errorMessage;
        }

        return errorMessage;
    }

}
