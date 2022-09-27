package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.model.path.PathTemplate;
import pt.isel.ls.controller.commands.commandresults.OptionResult;
import pt.isel.ls.view.commandviews.ICommandView;

import java.util.HashMap;

public class OptionView implements ICommandView {

    private HashMap<PathTemplate, String> descriptions;


    public String render(OptionResult result, HashMap<PathTemplate, String> descriptions) {
        this.descriptions = descriptions;
        return render(result);
    }

    @Override
    public String render(CommandResult result) {
        StringBuilder sb = new StringBuilder();
        descriptions.forEach((pathTemplate, description) -> sb.append(pathTemplate.getFullPath())
                .append(" ").append(description).append("\n\n"));

        return sb.toString();
    }

}
