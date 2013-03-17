package junitar.server.netty.jersey;

/**
 * @author sha1n
 * Date: 3/17/13
 */
public class ResourceQuery {

    private String layout;
    private String filter;
    private String order;
    private String page;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "ResourceQuery{" +
                "layout='" + layout + '\'' +
                ", filter='" + filter + '\'' +
                ", order='" + order + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
