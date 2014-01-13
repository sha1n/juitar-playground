package org.juitar.commons.l10n.exception;

import org.juitar.commons.l10n.LocalizableMessage;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public abstract class AbstractLocalizedExceptionTest {

    public static final String RESOURCE_NAME = "META-INF/l10n/test_error";

    protected abstract Throwable createWithMessage(LocalizableMessage localizableMessage);

    protected abstract Throwable createWithMessage(LocalizableMessage localizableMessage, Throwable cause);

    @Test
    public void testGetLocalizedMessage() {
        LocalizableMessage localizableMessage = LocalizableMessage.get("testArgs", RESOURCE_NAME, Locale.ENGLISH).arg(2);
        Throwable throwable = createWithMessage(localizableMessage);

        Assert.assertEquals("The child is 2 years old", throwable.getLocalizedMessage());

    }

    @Test
    public void testGetMessage() {
        LocalizableMessage localizableMessage = LocalizableMessage.get("testArgs", RESOURCE_NAME, Locale.ENGLISH).arg(2);
        Throwable throwable = createWithMessage(localizableMessage);

        Assert.assertEquals("The child is 2 years old", throwable.getMessage());

    }

    @Test
    public void testGetLocalizedMessageNoArgs() {
        LocalizableMessage localizableMessage = LocalizableMessage.get("testNoArgs", RESOURCE_NAME, Locale.ENGLISH);
        Throwable throwable = createWithMessage(localizableMessage);

        Assert.assertEquals("The child is young", throwable.getLocalizedMessage());

    }

    @Test
    public void testGetMessageNoArgs() {
        LocalizableMessage localizableMessage = LocalizableMessage.get("testNoArgs", RESOURCE_NAME, Locale.ENGLISH);
        Throwable throwable = createWithMessage(localizableMessage);

        Assert.assertEquals("The child is young", throwable.getMessage());

    }

    @Test
    public void testWithCause() {
        LocalizableMessage localizableMessage = LocalizableMessage.get("testNoArgs", RESOURCE_NAME, Locale.ENGLISH);
        NullPointerException cause = new NullPointerException();
        Throwable throwable = createWithMessage(localizableMessage, cause);

        Assert.assertEquals("The child is young", throwable.getMessage());
        Assert.assertSame(cause, throwable.getCause());
    }


}
