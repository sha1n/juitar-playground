package org.juitar.commons.exception;

import org.juitar.commons.l10n.Msg;

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
        return msg.getString();
    }

    @Override
    public final String getMessage() {
        return msg.getString();
    }
}
