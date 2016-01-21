package com.minhhop.sms;

import com.jcabi.aspects.Immutable;
import java.io.IOException;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;

/**
 * JSON patchable.
 *
 * @author Giang Le (giangle@minhhop.net)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public interface JsonPatchable {

    /**
     * Patch using this JSON object.
     * @param json JSON object
     * @throws IOException If there is any I/O problem
     */
    void patch(@NotNull(message = "JSON is never NULL") JsonObject json)
            throws IOException;

}