package org.juitar.commons.exception;

import org.juitar.commons.l10n.Msg;

import java.util.Locale;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class TestMsg extends Msg {

    public static final String RESOURCE_NAME = "META-INF/l10n/test_error";

    public TestMsg(String key, Locale locale) {
        super(key, RESOURCE_NAME, locale);
    }

}
