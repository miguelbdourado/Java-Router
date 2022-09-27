package pt.isel.ls;

import org.junit.Assert;
import org.junit.Test;
import pt.isel.ls.model.Headers;

public class HeadersTest {

    @Test
    public void two_headers_test() {
        Headers headers = new Headers("accept:text/plain|file-name:movies.txt");

        Assert.assertEquals("text/plain", headers.getHeader("accept"));
        Assert.assertEquals("movies.txt", headers.getHeader("file-name"));
    }
}
