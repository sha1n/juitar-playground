package org.juitar.commons.l10n;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class Msg {

    public static Msg get(String key, String resourceName, Locale locale) {
        return new Msg(key, resourceName, locale);
    }

    private final String resourceName;
    private final String key;
    private List<Object> args = Collections.emptyList();
    private final Locale locale;

    protected Msg(String key, String resourceName, Locale locale) {
        this.key = key;
        this.resourceName = resourceName;
        this.locale = locale;
    }

    public final String getLocalizedString() {
        String msg;
        if (args.isEmpty()) {
            msg = getBundle(locale).getString(key);
        } else {
            msg = getFormattedMessage(getBundle(locale));
        }

        return msg;
    }

    public final String getLocalizedString(Locale locale) {
        String msg;
        ResourceBundle bundle = getBundle(locale);
        if (bundle.getLocale().equals(locale)) {
            msg = getLocalizedString();
        } else {
            ResourceBundle localeResourceBundle = ResourceBundle.getBundle(resourceName, locale);
            msg = getFormattedMessage(localeResourceBundle);
        }

        return msg;
    }

    public final Msg arg(Object arg) {
        if (arg != null) {
            addArgument(arg);
        } else {
            addArgument("null");
        }

        return this;
    }

    private String getFormattedMessage(ResourceBundle bundle) {
        String msg;
        if (args.isEmpty()) {
            msg = bundle.getString(key);
        } else {
            msg = MessageFormat.format(bundle.getString(key), args.toArray());
        }
        return msg;

    }

    private void addArgument(Object arg) {
        if (args.isEmpty()) {
            args = new ArrayList<>();
        }

        args.add(arg);
    }

    protected final ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle(resourceName, locale);
    }

}
