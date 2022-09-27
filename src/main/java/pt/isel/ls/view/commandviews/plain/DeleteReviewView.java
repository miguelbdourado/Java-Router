package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.DeleteReviewResult;
import pt.isel.ls.view.commandviews.ICommandView;

public class DeleteReviewView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        DeleteReviewResult deleteResult = (DeleteReviewResult) result;

        if (deleteResult.getStatus().equals(CommandStatus.SUCCESSFUL_DELETE)) {
            return "Review Deleted With Success.";
        } else {
            return "Can Not Delete Review.";
        }

    }
}
