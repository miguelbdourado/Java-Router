package pt.isel.ls.view.utils;

import java.util.Arrays;
import java.util.LinkedList;

public class PlainTableBuilder {

    private LinkedList<String> headers;
    private LinkedList<LinkedList<String>> data;

    public PlainTableBuilder() {
        this.headers = new LinkedList<>();
        this.data = new LinkedList<>();
    }

    public void setHeaders(String... headers) {
        this.headers.clear();
        this.headers.addAll(Arrays.asList(headers));
    }

    public void addRow(String... data) {
        this.data.add(new LinkedList<>(Arrays.asList(data)));
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();

        int[] maxLengths = new int[headers.size()];
        for (int i = 0; i < maxLengths.length; i++) {
            maxLengths[i] = headers.get(i).length();
        }

        for (LinkedList<String> row : data) {
            for (int i = 0; i < row.size(); i++) {
                maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
            }
        }

        for (int maxLength : maxLengths) {
            sb.append("%-").append(maxLength + 2).append("s");
        }
        String format = sb.toString();

        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append(String.format(format, headers.toArray())).append("\n");
        for (LinkedList<String> row : this.data) {
            tableBuilder.append(String.format(format, row.toArray())).append("\n");
        }

        return tableBuilder.toString();
    }
}
