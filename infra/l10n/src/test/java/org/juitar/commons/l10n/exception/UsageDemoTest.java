package org.juitar.commons.l10n.exception;

import org.juitar.commons.l10n.LocalizableMessage;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class UsageDemoTest {

    static class HttpCodeException extends LocalizedException {

        public static final String RESOURCE_NAME = "META-INF/l10n/messages";

        enum HttpCode {
            HTTP_401("http_401"),
            HTTP_510("http_510");

            private final String messageKey;

            HttpCode(String messageKey) {
                this.messageKey = messageKey;
            }
        }

        public HttpCodeException(HttpCode code) {
            super(LocalizableMessage.get(code.messageKey, RESOURCE_NAME, Locale.ENGLISH));
        }

    }

    @Test
    public void demoExtendedException() {
        HttpCodeException httpCodeException = new HttpCodeException(HttpCodeException.HttpCode.HTTP_401);

        Assert.assertEquals("Not Authenticated", httpCodeException.getLocalizedMessage());
        Assert.assertEquals("Not Authenticated", httpCodeException.getLocalizedMessage(Locale.UK));
        Assert.assertEquals("Not Authenticated", httpCodeException.getMessage());
    }

    @Test(expected = MissingResourceException.class)
    public void testMissingResourceKey() {
        HttpCodeException httpCodeException = new HttpCodeException(HttpCodeException.HttpCode.HTTP_510);

        Assert.assertEquals("Not Authenticated", httpCodeException.getLocalizedMessage());
        Assert.assertEquals("Not Authenticated", httpCodeException.getLocalizedMessage(Locale.UK));
        Assert.assertEquals("Not Authenticated", httpCodeException.getMessage());
    }

}
