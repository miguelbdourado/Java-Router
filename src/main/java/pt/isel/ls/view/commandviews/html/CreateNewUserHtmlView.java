package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CreateNewUserResult;
import pt.isel.ls.model.models.User;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import static pt.isel.ls.view.utils.HtmlWrapper.h1;

public class CreateNewUserHtmlView implements ICommandView {

    public String render(CommandResult result) {
        CreateNewUserResult userResult = (CreateNewUserResult) result;
        User user = userResult.getUser();

        HtmlTableBuilder tableBuilder = new HtmlTableBuilder();

        if (user == null) {
            return "";
        }

        tableBuilder.setHeaders("User ID", "Username", "Email");
        tableBuilder.addRow(String.valueOf(user.getId()), user.getName(), user.getEmail());

        HtmlPageBuilder html = new HtmlPageBuilder();
        html.setTitle("New User Created");
        html.addToBody(
                h1("User"),
                tableBuilder.getTable()
        );

        return html.getHtml().toString();
    }
}
