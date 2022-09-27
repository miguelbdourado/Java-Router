package pt.isel.ls.view.utils;

import pt.isel.ls.utils.Pair;

import java.util.Arrays;
import java.util.LinkedList;

public class Element {

    private LinkedList<Element> children;
    private String openingTag;
    private String closingTag;
    private LinkedList<Pair<String, String>> style;
    private LinkedList<Pair<String, String>> attributes;

    public Element() {
        this.attributes = new LinkedList<>();
        this.style = new LinkedList<>();
        this.children = new LinkedList<>();
    }

    public Element(String tag, Element... elements) {
        this();
        setTags(tag, tag);
        children.addAll(Arrays.asList(elements));
    }

    public Element(String openingTag, String closingTag, Element... elements) {
        this();
        setTags(openingTag, closingTag);
        if (elements != null) {
            children.addAll(Arrays.asList(elements));
        }
    }

    public void addElement(Element element) {
        children.add(element);
    }

    public void addElements(Element... elements) {
        this.children.addAll(Arrays.asList(elements));
    }

    public void setTags(String openingTag, String closingTag) {
        this.openingTag = openingTag;
        this.closingTag = closingTag;
    }

    public void toStringA(StringBuilder sb, int tabs) {
        sb.append(getOpeningTag()).append("\n");
        ++tabs;

        appendChildrenContent(sb, tabs);

        //place tabs but one less because it's closing tabs
        for (int i = 0; i < tabs - 1; i++) {
            sb.append("\t");
        }
        sb.append(getClosingTag()).append("\n");
    }

    public void appendChildrenContent(StringBuilder sb, int tabs) {
        for (Element child :
                children) {
            //place tabs
            for (int i = 0; i < tabs; i++) {
                sb.append("\t");
            }
            child.toStringA(sb, tabs);
        }
    }

    public Element addStyle(String key, String value) {
        style.add(new Pair<>(key, value));
        return this;
    }

    public Element addAttribute(String key, String value) {
        attributes.add(new Pair<>(key, value));
        return this;
    }

    public String getOpeningTag() {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(openingTag);

        String style = buildStyle();
        if (!style.isEmpty()) {
            sb.append(" ").append(style);
        }

        String attributes = buildAttributes();
        if (!attributes.isEmpty()) {
            sb.append(" ").append(attributes);
        }

        sb.append(">");
        return sb.toString();
    }

    private String buildStyle() {
        if (style.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("style=\"");
        for (Pair<String, String> pair : this.style) {
            sb.append(pair.getKey()).append(":").append(pair.getValue()).append(";");
        }
        sb.append("\"");
        return sb.toString();
    }

    //TODO: Fix bug get value empty
    private String buildAttributes() {
        if (attributes.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Pair<String, String> pair : attributes) {
            sb.append(pair.getKey()).append("=").append("\"").append(pair.getValue()).append("\"");
        }
        return sb.toString();
    }

    public String getClosingTag() {
        return "</" + closingTag + ">";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringA(sb, 0);
        return sb.toString();
    }
}
