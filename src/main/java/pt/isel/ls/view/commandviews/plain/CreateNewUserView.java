package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CreateNewUserResult;
import pt.isel.ls.model.models.User;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class CreateNewUserView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        StringBuilder sb = new StringBuilder();
        CreateNewUserResult userResult = (CreateNewUserResult) result;
        User user = userResult.getUser();

        if (user == null) {
            sb.append("Error creating user.");
            return sb.toString();
        }

        PlainTableBuilder tableBuilder = new PlainTableBuilder();

        tableBuilder.setHeaders("User ID", "Username", "Email");
        tableBuilder.addRow(String.valueOf(user.getId()), user.getName(), user.getEmail());

        sb.append(tableBuilder.getString());
        return sb.toString();
    }
}
