package org.juitar.commons.l10n;

import org.junit.Test;

import java.util.Locale;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class LocalizedMessageInjectorTest {

    @Test
    public void testInject() throws Exception {
        LocalizedMessageInjector injector = new LocalizedMessageInjector(Locale.ENGLISH);

        LocalizedBean bean = new LocalizedBean();

        injector.inject(bean);

        System.out.println(bean);
    }

    @Test
    public void testInjectExt() throws Exception {
        LocalizedMessageInjector injector = new LocalizedMessageInjector(Locale.ENGLISH);

        LocalizedBean bean = new LocalizedBeanExt();

        injector.inject(bean);

        System.out.println(bean);
    }

    @Test
    public void testInjectExt2() throws Exception {
        LocalizedMessageInjector injector = new LocalizedMessageInjector(Locale.ENGLISH);

        LocalizedBean bean = new LocalizedBeanExt2();

        injector.inject(bean);

        System.out.println(bean);
    }

}
