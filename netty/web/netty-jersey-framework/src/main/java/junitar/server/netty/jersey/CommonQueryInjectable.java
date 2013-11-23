package junitar.server.netty.jersey;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MultivaluedMap;
import java.util.EnumSet;
import java.util.List;

/**
 * @author sha1n
 * Date: 3/17/13
 */
public class CommonQueryInjectable extends AbstractHttpContextInjectable<ResourceQuery> {

    private final EnumSet<Param> required;

    public CommonQueryInjectable(CommonQuery commonQuery) {
        if (commonQuery.required().length > 0) {
            this.required = EnumSet.of(commonQuery.required()[0], commonQuery.required());
        } else {
            required = EnumSet.noneOf(Param.class);
        }
    }

    @Override
    public ResourceQuery getValue(HttpContext c) {
        ResourceQuery query = null;
        if (HttpMethod.GET.equals(c.getRequest().getMethod())) {
            MultivaluedMap<String, String> queryParameters = c.getUriInfo().getQueryParameters(true);

            query = new ResourceQuery();
            query.setLayout(getQueryParamValue(queryParameters, Param.LAYOUT));
            query.setFilter(getQueryParamValue(queryParameters, Param.FILTER));
            query.setOrder(getQueryParamValue(queryParameters, Param.ORDER));
            query.setPage(getQueryParamValue(queryParameters, Param.PAGE));
        }

        return query;
    }

    private String getQueryParamValue(MultivaluedMap<String, String> queryParameters, Param params) {
        List<String> list = queryParameters.get(params.paramName());

        if (list == null && required.contains(params)) {
            // TODO: Fail with code 400
            throw new RuntimeException();
        } else {
            return list != null ? list.get(0) : "";
        }
    }
}
