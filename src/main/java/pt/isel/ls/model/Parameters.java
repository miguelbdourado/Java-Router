package pt.isel.ls.model;

import java.util.HashMap;
import java.util.Map;

public class Parameters {

    private final HashMap<String, String> parameters;

    public Parameters(String fullParameters) {
        this();
        splitParams(fullParameters);
    }

    public Parameters(Map<String, String[]> parameters) {
        this();
        //TODO HANDLE BETTER
        parameters.forEach((s, s2) -> {
            this.parameters.put(s, s2[0]);
        });
    }

    public Parameters() {
        this.parameters = new HashMap<>();
    }

    public void splitParams(String fullParameters) {
        String[] splitParameters = fullParameters.split("&");
        for (String parameter : splitParameters) {
            int indexOfEqual = parameter.indexOf("=");
            if (indexOfEqual < 0) {
                //Invalid parameters
                return;
            }
            String key = parameter.substring(0, indexOfEqual);
            String value = parameter.substring(indexOfEqual + 1).replace("+", " ");

            parameters.put(key, value);
        }
    }

    public String getParameter(String parameterName) {
        return parameters.get(parameterName);
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }
}
