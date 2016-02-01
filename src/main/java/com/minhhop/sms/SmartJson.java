package com.minhhop.sms;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import netscape.javascript.JSObject;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.io.IOException;

/**
 * Smart JSON (supplementary help class).
 *
 * @author Giang Le (giangle@minhhop.net)
 * @version $Id$
 * @since 0.1
 */
@ToString
@EqualsAndHashCode(of = "object")
final class SmartJson {

    /**
     * Encapsulated JSON object.
     */
    private final transient JsonReadable object;

    /**
     * Public ctor.
     *
     * @param obj Readable object
     */
    SmartJson(final JsonReadable obj) {
        this.object = obj;
    }

    /**
     * Get its property as string.
     *
     * @param name Name of the property
     * @return Value
     * @throws java.io.IOException If there is any I/O problem
     */
    public String text(final String name) throws IOException {
        return this.value(name, JsonString.class).getString();
    }

    /**
     * Get its property as number.
     *
     * @param name Name of the property
     * @return Value
     * @throws IOException If there is any I/O problem
     */
    public int number(final String name) throws IOException {
        return this.value(name, JsonNumber.class).intValue();
    }

    /**
     * Get its property as custom type.
     *
     * @param name Name of the property
     * @param type Type of result expected
     * @param <T>  Type expected
     * @return Value
     * @throws IOException If there is any I/O problem
     */
    public <T> T value(final String name, final Class<T> type) throws IOException {
        final JsonObject json = this.object.json();
        if (!json.containsKey(name)) {
            throw new IllegalStateException(
                    String.format(
                            "'%s' is absent in JSON: %s", name, json
                    )
            );
        }
        final JsonValue value = json.get(name);
        if (value == null) {
            throw new IllegalStateException(
                    String.format(
                            "'%s' is NULL in %s", name, json
                    )
            );
        }
        if (value.getClass().isAssignableFrom(type)) {
            throw new IllegalStateException(
                    String.format(
                            "'%s' is not of type %s", name, type
                    )
            );
        }
        return type.cast(value);
    }
}
