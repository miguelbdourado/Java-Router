package pt.isel.ls.view.utils;

public class HtmlWrapper {
    public static Element html(Element... elements) {
        return new Element("html", elements);
    }

    public static Element body(Element... elements) {
        return new Element("body", elements);
    }

    public static Element head(Element... elements) {
        return new Element("head", elements);
    }

    public static Element div(Element... elements) {
        return new Element("div", elements);
    }

    public static Element table(Element... elements) {
        return new Element("table border=\"1px solid black\"", "table", elements);
    }

    public static Element tr(Element... elements) {
        return new Element("tr", elements);
    }

    public static Element ul(Element... elements) {
        return new Element("ul", elements);
    }

    public static Element li(String text) {
        return new TextElement("li",
                text.replace("<", "&lt;").replace(">", "&gt;"));
    }

    public static Element li(Element element) {
        return new Element("li", element);
    }

    public static Element th(String text) {
        return new TextElement("th", text);
    }

    public static Element td(String text) {
        return new TextElement("td",
                text.replace("<", "&lt;").replace(">", "&gt;"));
    }

    public static Element td(Element element) {
        return new Element("td", element);
    }

    public static Element title(String text) {
        return new TextElement("title", text);
    }

    public static Element text(String text) {
        return new TextElement("p",
                text.replace("<", "&lt;").replace(">", "&gt;"));
    }

    public static Element h1(String text) {
        return new TextElement("h1", text);
    }

    public static Element h2(String text) {
        return new TextElement("h2", text);
    }

    public static Element anch(String text, String href) {
        return new TextElement("a",
                text.replace("<", "&lt;").replace(">", "&gt;"))
                .addAttribute("href", href);
    }

    //TODO: Add bold support
    public static Element bold(String text) {
        return new TextElement("b", text);
    }

    public static Element form(String action, String method) {
        return new Element("form")
                .addAttribute("action", action)
                .addAttribute("method", method);
    }

    public static Element label(String forText, String labelText) {
        return new TextElement("label", labelText).addAttribute("for", forText);
    }

    public static Element input(String type, String id) {
        return new Element("input").addAttribute("type", type).addAttribute("name", id).addAttribute("id", id);
    }

    public static Element input(String type, String id, String min, String max) {
        return new Element("input").addAttribute("type", type).addAttribute("name", id).addAttribute("id", id)
                .addAttribute("min", min).addAttribute("max", max);
    }
}
