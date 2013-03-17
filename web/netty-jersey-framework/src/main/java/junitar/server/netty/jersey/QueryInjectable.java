package junitar.server.netty.jersey;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;

import javax.ws.rs.core.MultivaluedMap;
import java.util.EnumSet;
import java.util.List;

/**
 * @author sha1n
 * Date: 3/17/13
 */
public class QueryInjectable extends AbstractHttpContextInjectable<ResourceQuery> {

    private final Query query;
    private final EnumSet<QueryParams> required;

    public QueryInjectable(Query query) {
        this.query = query;
        if (query.failIfEmpty().length > 0) {
            this.required = EnumSet.of(query.failIfEmpty()[0], query.failIfEmpty());
        } else {
            required = EnumSet.noneOf(QueryParams.class);
        }
    }

    @Override
    public ResourceQuery getValue(HttpContext c) {
        MultivaluedMap<String, String> queryParameters = c.getUriInfo().getQueryParameters(true);

        ResourceQuery query = new ResourceQuery();
        query.setLayout(getQueryParamValue(queryParameters, QueryParams.LAYOUT));
        query.setFilter(getQueryParamValue(queryParameters, QueryParams.FILTER));
        query.setOrder(getQueryParamValue(queryParameters, QueryParams.ORDER));
        query.setPage(getQueryParamValue(queryParameters, QueryParams.PAGE));

        return query;
    }

    private String getQueryParamValue(MultivaluedMap<String, String> queryParameters, QueryParams params) {
        List<String> list = queryParameters.get(params.paramName());

        if (list == null && required.contains(params)) {
            // TODO: Fail with code 400
            throw new RuntimeException();
        } else {
            return list != null ? list.get(0) : "";
        }
    }
}
