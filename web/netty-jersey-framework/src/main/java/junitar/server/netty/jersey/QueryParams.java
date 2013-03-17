package junitar.server.netty.jersey;

/**
 * @author sha1n
 * Date: 3/17/13
 */
public enum QueryParams {
    LAYOUT("layout"),
    FILTER("filter"),
    ORDER("order"),
    PAGE("page");

    private final String paramName;

    QueryParams(String paramName) {
        this.paramName = paramName;
    }

    public final String paramName() {
        return paramName;
    }
}
