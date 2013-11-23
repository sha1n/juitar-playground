package junitar.server.netty.jersey;

/**
 * @author sha1n
 * Date: 3/17/13
 */
public enum Param {
    LAYOUT("layout"),
    FILTER("filter"),
    ORDER("order"),
    PAGE("page");

    private final String paramName;

    Param(String paramName) {
        this.paramName = paramName;
    }

    public final String paramName() {
        return paramName;
    }
}
