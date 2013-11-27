package org.juitar.infra.netty.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sha1n
 * Date: 11/27/13
 */
class HeadersImpl implements Headers {

    private final Map<String, List<String>> headers = new HashMap<>();

    @Override
    public String get(String name) {

        String value = null;
        List<String> headerValues = headers.get(name);

        if (headerValues != null && !headerValues.isEmpty()) {
            value = headerValues.get(0);
        }

        return value;
    }

    @Override
    public List<String> getAll(String name) {

        List<String> headerValues = headers.get(name);
        if (headerValues == null) {
            headerValues = new ArrayList<>();
            headers.put(name, headerValues);
        }

        return headerValues;
    }

    @Override
    public void add(String name, String value) {
        if (value != null) {

            List<String> headerValues = headers.get(name);
            if (headerValues == null) {
                headerValues = new ArrayList<>();
                headers.put(name, headerValues);
            }

            headerValues.add(value);
        }
    }

    @Override
    public void addAll(String name, List<String> values) {
        if (values != null) {

            List<String> headerValues = getAll(name);
            headerValues.addAll(values);

        }
    }

}
