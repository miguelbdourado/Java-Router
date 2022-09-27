package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetUsersResult;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class GetUsersView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        GetUsersResult usersResult = (GetUsersResult) result;

        PlainTableBuilder tableBuilder = new PlainTableBuilder();

        tableBuilder.setHeaders("USER ID", "NAME", "EMAIL");
        usersResult.getUsers().forEach(user ->
                tableBuilder.addRow(String.valueOf(user.getId()), user.getName(), user.getEmail())
        );

        return tableBuilder.getString();
    }


}
