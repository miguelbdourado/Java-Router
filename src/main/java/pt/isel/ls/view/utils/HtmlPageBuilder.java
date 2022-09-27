package pt.isel.ls.view.utils;

public class HtmlPageBuilder {

    private Element html;
    private Element head;
    private Element body;

    public HtmlPageBuilder() {
        this.html = HtmlWrapper.html();
        this.head = HtmlWrapper.head();
        this.body = HtmlWrapper.body();

        html.addElements(head, body);
    }

    public void setTitle(String title) {
        head.addElement(HtmlWrapper.title(title));
    }

    public void addToBody(Element... elements) {
        body.addElements(elements);
    }

    public Element getHtml() {
        return html;
    }
}
