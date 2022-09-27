package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetUserResult;
import pt.isel.ls.model.models.User;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class GetUserView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        GetUserResult userResult = (GetUserResult) result;

        User user = userResult.getUser();

        if (user == null) {
            return "User does not exist.";
        }

        PlainTableBuilder tableBuilder = new PlainTableBuilder();

        tableBuilder.setHeaders("USER ID", "NAME", "EMAIL");
        tableBuilder.addRow(String.valueOf(user.getId()), user.getName(), user.getEmail());

        return tableBuilder.getString();
    }


}
