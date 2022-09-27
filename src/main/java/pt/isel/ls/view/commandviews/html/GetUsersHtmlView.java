package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetUsersResult;
import pt.isel.ls.model.models.User;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.FormBuilder;
import pt.isel.ls.view.utils.HtmlPageBuilder;
import pt.isel.ls.view.utils.HtmlTableBuilder;

import java.util.ArrayList;

import static pt.isel.ls.view.utils.HtmlWrapper.anch;
import static pt.isel.ls.view.utils.HtmlWrapper.h1;

public class GetUsersHtmlView implements ICommandView {

    public String render(CommandResult result) {
        GetUsersResult usersResult = (GetUsersResult) result;
        ArrayList<User> users = usersResult.getUsers();

        HtmlPageBuilder html = new HtmlPageBuilder();
        HtmlTableBuilder tableBuilder = new HtmlTableBuilder();
        tableBuilder.setHeaders("USER ID", "NAME", "EMAIL");

        users.forEach(user -> {
            int id = user.getId();

            tableBuilder.initRow();
            tableBuilder.addColumnToRow(
                    anch(String.valueOf(id), "/users/" + id)
            );
            tableBuilder.addColumnToRow(user.getName());
            tableBuilder.addColumnToRow(user.getEmail());
        });

        FormBuilder formBuilder = new FormBuilder("", "POST")
                .addInput("Name:", "name", "text")
                .addInput("Email:", "email", "email")
                .setSubmitButton("Create users");

        html.setTitle("Users List");
        html.addToBody(
                anch("Home", "/"),
                h1("Users"),
                tableBuilder.getTable().addStyle("margin-bottom", "25px"),
                formBuilder.getFormElement()
        );

        return html.getHtml().toString();
    }
}
