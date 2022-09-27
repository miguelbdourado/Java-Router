package pt.isel.ls.view;

import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.utils.HtmlPageBuilder;

import static pt.isel.ls.view.utils.HtmlWrapper.anch;
import static pt.isel.ls.view.utils.HtmlWrapper.h1;
import static pt.isel.ls.view.utils.HtmlWrapper.li;
import static pt.isel.ls.view.utils.HtmlWrapper.ul;

public class MainView implements ICommandView {
    public String render(CommandResult result) {
        HtmlPageBuilder html = new HtmlPageBuilder();

        html.setTitle("Movie Manager");

        html.addToBody(
                h1("Welcome"),
                ul(
                        li(anch("Users", "/users")),
                        li(anch("Movies", "/movies?skip=0&top=5")),
                        li(anch("Top Rated Movies", "/tops/ratings?n=5&average=highest&min=0"))
                )
        );

        return html.getHtml().toString();
    }
}
