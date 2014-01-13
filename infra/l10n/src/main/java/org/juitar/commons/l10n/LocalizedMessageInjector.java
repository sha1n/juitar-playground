package org.juitar.commons.l10n;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;

/**
 * @author sha1n
 * Date: 2/15/13
 */
public class LocalizedMessageInjector {

    private final Locale locale;

    public LocalizedMessageInjector(Locale locale) {
        this.locale = locale;
    }

    public void inject(final Object obj) {
        injectRecursively(obj, obj.getClass());
    }

    private void injectRecursively(final Object obj, Class<?> clazz) {

        // Process class level annotation.
        L10nBundle l10nBundle = clazz.getAnnotation(L10nBundle.class);
        String classLevelResourceName = null;
        if (l10nBundle != null) {
            classLevelResourceName = l10nBundle.value();
        }

        Field[] declaredFields = clazz.getDeclaredFields();

        // Inject annotated fields.
        for (final Field field : declaredFields) {
            final LocalizedValue localizedValue = field.getAnnotation(LocalizedValue.class);
            if (localizedValue != null) {
                String resource = localizedValue.resource();
                resource = !resource.isEmpty() ? resource : classLevelResourceName;

                if (resource == null) {
                    throw new IllegalArgumentException("Resource must be specified either on the class level or on the field level.");
                }

                final String localizedStirng = LocalizableMessage.get(localizedValue.key(), resource, locale).getLocalized();

                AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        boolean unlock = (field.getModifiers() & Modifier.PRIVATE) != 0;
                        try {
                            if (unlock) {
                                field.setAccessible(true);
                            }
                            field.set(obj, localizedStirng);
                        } catch (IllegalAccessException e) {
                            throw new SecurityException(e);
                        } finally {
                            if (unlock) {
                                field.setAccessible(false);
                            }
                        }
                        return null;
                    }
                });
            }
        }

        // Dig into super class.
        Class<?> superclass = clazz.getSuperclass();
        if (!superclass.equals(Object.class)) {
            injectRecursively(obj, superclass);
        }

    }

}
