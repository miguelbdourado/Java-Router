package pt.isel.ls.model;

import java.util.HashMap;

public class Headers {

    private final HashMap<String, String> headers;

    public Headers(String fullHeaders) {
        headers = new HashMap<>();
        splitParams(fullHeaders);
    }

    public void splitParams(String fullHeader) {
        String[] splitParameters = fullHeader.split("\\|");
        for (String parameter : splitParameters) {
            int indexOfEqual = parameter.indexOf(":");
            if (indexOfEqual < 0) {
                return;
            }
            String key = parameter.substring(0, indexOfEqual);
            String value = parameter.substring(indexOfEqual + 1);

            headers.put(key, value);
        }
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }
}
