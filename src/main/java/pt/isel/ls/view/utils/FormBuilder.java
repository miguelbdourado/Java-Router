package pt.isel.ls.view.utils;

import static pt.isel.ls.view.utils.HtmlWrapper.div;
import static pt.isel.ls.view.utils.HtmlWrapper.form;
import static pt.isel.ls.view.utils.HtmlWrapper.input;
import static pt.isel.ls.view.utils.HtmlWrapper.label;

public class FormBuilder {

    Element formElement;

    public FormBuilder(String action, String method) {
        formElement = form(action, method);
    }

    public FormBuilder addInput(String labelText, String id, String type) {
        Element div = div(
                label(id, labelText),
                input(type, id)
        );
        formElement.addElement(div);
        return this;
    }

    public FormBuilder addInput(String labelText, String id, String type, String min, String max) {
        Element div = div(
                label(id, labelText),
                input(type, id, min, max)
        );
        formElement.addElement(div);
        return this;
    }

    public FormBuilder setSubmitButton(String value) {
        Element div = div(
                input("submit", "").addAttribute("value", value)
        );
        formElement.addElement(div);
        return this;
    }

    public Element getFormElement() {
        return formElement;
    }
}
