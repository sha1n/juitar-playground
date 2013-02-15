package org.juitar.commons.exception;

import org.juitar.commons.l10n.Msg;

import java.util.Locale;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class TestMsg extends Msg {

    public TestMsg(String key, Locale locale) {
        super(key, "META-INF/l10n/test_error", locale);
    }

    @Override
    protected final String getResourceName() {
        return "META-INF/l10n/test_error";
    }
}
