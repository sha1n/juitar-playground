package org.juitar.commons.l10n.exception;

import org.juitar.commons.l10n.LocalizableMessage;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class LocalizedExceptionTest extends AbstractLocalizedExceptionTest {

    @Override
    protected Exception createWithMessage(LocalizableMessage localizableMessage) {
        return new LocalizedException(localizableMessage);
    }

    @Override
    protected Exception createWithMessage(LocalizableMessage localizableMessage, Throwable cause) {
        return new LocalizedException(localizableMessage, cause);
    }
}
