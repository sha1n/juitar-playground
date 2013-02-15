package org.juitar.commons.l10n;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public abstract class Msg {

    private final ResourceBundle bundle;
    private final String resourceName;
    private final String key;
    private List<Object> args = Collections.emptyList();

    protected Msg(String key, String resourceName, Locale locale) {
        this.key = key;
        this.resourceName = resourceName;
        this.bundle = ResourceBundle.getBundle(resourceName, locale);
    }

    public final String getString() {
        String msg;
        if (args.isEmpty()) {
            msg = bundle.getString(key);
        } else {
            msg = getFormattedMessage(bundle);
        }

        return msg;
    }

    public final String getString(Locale locale) {
        String msg;
        if (bundle.getLocale().equals(locale)) {
            msg = getString();
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

    protected abstract String getResourceName();

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

}
