package pt.isel.ls.view.utils;

import static pt.isel.ls.view.utils.HtmlWrapper.table;
import static pt.isel.ls.view.utils.HtmlWrapper.td;
import static pt.isel.ls.view.utils.HtmlWrapper.text;
import static pt.isel.ls.view.utils.HtmlWrapper.th;
import static pt.isel.ls.view.utils.HtmlWrapper.tr;

public class HtmlTableBuilder {

    private Element table;
    private Element currentRow;

    public HtmlTableBuilder() {
        table = table().addAttribute("class", "table");
    }

    public void setHeaders(String... headerNames) {
        Element tableRow = tr();
        for (String header : headerNames) {
            tableRow.addElement(th(header).addAttribute("scope", "col"));
        }
        table.addElement(tableRow);
    }

    public void addRow(String... values) {
        Element tableRow = tr();
        for (String text : values) {
            tableRow.addElement(td(text));
        }
        table.addElement(tableRow);
    }

    public void initRow() {
        this.currentRow = tr();
        this.table.addElement(currentRow);
    }

    public void addColumnToRow(String text) {
        this.currentRow.addElement(td(text(text)));
    }

    public void addColumnToRow(Element element) {
        this.currentRow.addElement(td(element));
    }

    public Element getTable() {
        return table;
    }

}
