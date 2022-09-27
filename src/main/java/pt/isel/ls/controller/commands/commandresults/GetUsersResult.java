package pt.isel.ls.controller.commands.commandresults;

import pt.isel.ls.model.models.User;

import java.util.ArrayList;

public class GetUsersResult extends CommandResult {

    private ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
