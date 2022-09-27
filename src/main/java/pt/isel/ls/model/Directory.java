package pt.isel.ls.model;

public class Directory {
    private String name;
    private String value;
    private boolean isVariable;

    public Directory(String name, boolean isVariable) {
        this.name = name;
        this.isVariable = isVariable;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isVariable() {
        return isVariable;
    }

    public void setToVariable() {
        this.isVariable = true;
    }

    public void reset() {
        if (this.value != null) {
            this.name = value;
        }
        this.value = null;
        this.isVariable = false;
    }

    public void setName(String name) {
        this.name = name;
    }
}