package org.juitar.commons.exception;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class LocalizableRuntimeExceptionTest {

    @Test
    public void testGetLocalizedMessage() throws Exception {
        LocalizableRuntimeException test1 = new LocalizableRuntimeException(new TestMsg("test1", Locale.ENGLISH).arg(2));

        Assert.assertEquals("The child is 2 years old", test1.getLocalizedMessage());

    }

    @Test
    public void testGetMessage() throws Exception {
        LocalizableRuntimeException test1 = new LocalizableRuntimeException(new TestMsg("test1", Locale.ENGLISH).arg(2));

        Assert.assertEquals("The child is 2 years old", test1.getMessage());

    }

    @Test
    public void testGetLocalizedMessageNoArgs() throws Exception {
        LocalizableRuntimeException test1 = new LocalizableRuntimeException(new TestMsg("testNoArgs", Locale.ENGLISH).arg(2));

        Assert.assertEquals("The child is young", test1.getLocalizedMessage());

    }

    @Test
    public void testGetMessageNoArgs() throws Exception {
        LocalizableRuntimeException test1 = new LocalizableRuntimeException(new TestMsg("testNoArgs", Locale.ENGLISH).arg(2));

        Assert.assertEquals("The child is young", test1.getMessage());

    }

}
