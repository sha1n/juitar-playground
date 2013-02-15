package org.juitar.commons.exception;

import org.juitar.commons.l10n.Msg;

import java.util.Locale;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class LocalizableRuntimeException extends RuntimeException {

    private final Msg msg;

    public LocalizableRuntimeException(Msg msg) {
        super();
        this.msg = msg;
    }

    public LocalizableRuntimeException(Msg msg, Throwable cause) {
        super(cause);
        this.msg = msg;
    }

    @Override
    public final String getLocalizedMessage() {
        return msg.getLocalizedString();
    }

    @Override
    public final String getMessage() {
        return msg.getLocalizedString(Locale.ENGLISH);
    }
}
