package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.DeleteReviewResult;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.HtmlPageBuilder;

import static pt.isel.ls.view.utils.HtmlWrapper.h1;

public class DeleteReviewHtmlView implements ICommandView {

    public String render(CommandResult result) {
        DeleteReviewResult deleteResult = (DeleteReviewResult) result;

        HtmlPageBuilder html = new HtmlPageBuilder();
        html.setTitle("Delete Review Result");
        html.addToBody((deleteResult.getStatus().equals(CommandStatus.SUCCESSFUL_DELETE))
                ? h1("Review Deleted With Success.") :
                h1("Can Not Delete Review."));

        System.out.println(html);

        return html.getHtml().toString();
    }
}
