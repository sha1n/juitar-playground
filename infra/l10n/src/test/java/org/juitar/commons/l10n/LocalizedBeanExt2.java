package org.juitar.commons.l10n;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class LocalizedBeanExt2 extends LocalizedBean {

    String unlocalized = "English";

    @LocalizedValue(resource = "META-INF/l10n/messages", key = "serial")
    private String serial;

    @Override
    public String toString() {
        return super.toString() + " LocalizedBeanExt{" +
                "serial='" + serial + '\'' +
                ", unlocalized='" + unlocalized + '\'' +
                '}';
    }

}
