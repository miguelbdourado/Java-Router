package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.SubmitNewRatingResult;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.Element;
import pt.isel.ls.view.utils.HtmlPageBuilder;

import static pt.isel.ls.view.utils.HtmlWrapper.li;
import static pt.isel.ls.view.utils.HtmlWrapper.text;
import static pt.isel.ls.view.utils.HtmlWrapper.ul;

public class SubmitNewRatingHtmlView implements ICommandView {

    public String render(CommandResult result) {
        SubmitNewRatingResult newRatingResult = (SubmitNewRatingResult) result;

        if (!(newRatingResult.getStatus() == CommandStatus.SUCCESSFUL_POST)) {
            return "";
        }

        Element list = ul(
                li("MOVIE ID: " + newRatingResult.getMovieid()),
                li("RATING: " + newRatingResult.getRating())
        );

        Element text = text("Rating submited with success!");

        HtmlPageBuilder html = new HtmlPageBuilder();

        html.setTitle("Review");
        html.addToBody(
                text,
                list
        );

        return html.getHtml().toString();
    }
}
