package org.juitar.commons.l10n;

/**
 * @author sha1n
 * Date: 2/15/13
 */
@L10nBundle("META-INF/l10n/messages_ext")
public class LocalizedBeanExt extends LocalizedBean {

    @LocalizedValue(resource = "META-INF/l10n/messages_ext", key = "color")
    private String color;

    @LocalizedValue(key = "bg")
    private String background;


    @Override
    public String toString() {
        return super.toString() + " LocalizedBeanExt{" +
                "color='" + color + '\'' +
                ", background='" + background + '\'' +
                '}';
    }

}
