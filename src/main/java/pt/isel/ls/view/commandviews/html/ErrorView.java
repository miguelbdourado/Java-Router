package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.HtmlPageBuilder;

import static pt.isel.ls.view.utils.HtmlWrapper.text;

public class ErrorView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        HtmlPageBuilder html = new HtmlPageBuilder();
        html.setTitle("Error");
        html.addToBody(text("Error: " + result.getErrorMessage()));

        return html.getHtml().toString();
    }
}
