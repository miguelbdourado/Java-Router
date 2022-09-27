package pt.isel.ls.view.utils;

public class TextElement extends Element {

    private String text;

    public TextElement(String openingTag, String closingTag, String text) {
        super(openingTag, closingTag);
        this.text = text;
    }

    public TextElement(String openingTag, String text) {
        super(openingTag, openingTag);
        this.text = text;
    }

    @Override
    public void appendChildrenContent(StringBuilder sb, int tabs) {
        for (int i = 0; i < tabs; i++) {
            sb.append("\t");
        }
        sb.append(text).append("\n");
    }
}