package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.SubmitNewRatingResult;
import pt.isel.ls.view.commandviews.ICommandView;

public class SubmitNewRatingView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        SubmitNewRatingResult newRatingResult = (SubmitNewRatingResult) result;

        StringBuilder sb = new StringBuilder();

        if (newRatingResult.getStatus() == CommandStatus.SUCCESSFUL_POST) {
            sb.append("Rating submitted successfully to movie ").append(newRatingResult.getMovieid()).append("\n");
            sb.append("Rating: ").append(newRatingResult.getRating()).append("\n");
        }

        return sb.toString();
    }


}
