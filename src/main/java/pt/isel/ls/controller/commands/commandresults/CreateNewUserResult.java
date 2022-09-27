package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.User;

public class CreateNewUserResult extends CommandResult {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
