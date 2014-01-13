package org.juitar.commons.l10n.exception;

import org.juitar.commons.l10n.LocalizableMessage;

import java.util.Locale;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class LocalizedException extends Exception {

    private final LocalizableMessage localizableMessage;

    public LocalizedException(LocalizableMessage localizableMessage) {
        super();
        this.localizableMessage = localizableMessage;
    }

    public LocalizedException(LocalizableMessage localizableMessage, Throwable cause) {
        super(cause);
        this.localizableMessage = localizableMessage;
    }

    @Override
    public final String getLocalizedMessage() {
        return localizableMessage.getLocalized();
    }

    public final String getLocalizedMessage(Locale locale) {
        return localizableMessage.getLocalized(locale);
    }


    @Override
    public final String getMessage() {
        return localizableMessage.getLocalized(Locale.ENGLISH);
    }
}
