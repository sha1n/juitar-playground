package org.juitar.commons.l10n;

/**
 * @author sha1n
 * Date: 2/15/13
 */
@L10nBundle("META-INF/l10n/messages")
public class LocalizedBean {

    private String id;
    private int count;

    @LocalizedValue(key = "name")
    private String name;

    @LocalizedValue(key = "description")
    protected String description;

    @LocalizedValue(resource = "META-INF/l10n/messages", key = "status")
    String status;

    @Override
    public String toString() {
        return "LocalizedBean{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
