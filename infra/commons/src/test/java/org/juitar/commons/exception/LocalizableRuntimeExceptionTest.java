package org.juitar.commons.exception;

import org.juitar.commons.l10n.Msg;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class LocalizableRuntimeExceptionTest {

    public static final String RESOURCE_NAME = "META-INF/l10n/test_error";

    @Test
    public void testGetLocalizedMessage() throws Exception {
        Msg msg = Msg.get("test1", RESOURCE_NAME, Locale.ENGLISH).arg(2);
        LocalizableRuntimeException test1 = new LocalizableRuntimeException(msg);

        Assert.assertEquals("The child is 2 years old", test1.getLocalizedMessage());

    }

    @Test
    public void testGetMessage() throws Exception {
        Msg msg = Msg.get("test1", RESOURCE_NAME, Locale.ENGLISH).arg(2);
        LocalizableRuntimeException test1 = new LocalizableRuntimeException(msg);

        Assert.assertEquals("The child is 2 years old", test1.getMessage());

    }

    @Test
    public void testGetLocalizedMessageNoArgs() throws Exception {
        Msg msg = Msg.get("testNoArgs", RESOURCE_NAME, Locale.ENGLISH);
        LocalizableRuntimeException test1 = new LocalizableRuntimeException(msg);

        Assert.assertEquals("The child is young", test1.getLocalizedMessage());

    }

    @Test
    public void testGetMessageNoArgs() throws Exception {
        Msg msg = Msg.get("testNoArgs", RESOURCE_NAME, Locale.ENGLISH);
        LocalizableRuntimeException test1 = new LocalizableRuntimeException(msg);

        Assert.assertEquals("The child is young", test1.getMessage());

    }

}
