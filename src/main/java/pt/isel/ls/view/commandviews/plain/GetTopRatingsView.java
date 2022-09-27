package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.GetTopRatingsResult;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.PlainTableBuilder;

public class GetTopRatingsView implements ICommandView {

    @Override
    public String render(CommandResult result) {
        GetTopRatingsResult topRatingsResult = (GetTopRatingsResult) result;

        PlainTableBuilder tableBuilder = new PlainTableBuilder();
        tableBuilder.setHeaders("MOVIE ID", "MOVIE TITLE", "RELEASE YEAR", "AVERAGE RATING");

        topRatingsResult.getMovies().forEach(pair ->
                tableBuilder.addRow(String.valueOf(pair.getKey().getId()), pair.getKey().getTitle(),
                        String.valueOf(pair.getKey().getReleaseYear()), String.valueOf(pair.getValue()))
        );

        return tableBuilder.getString();
    }
}
