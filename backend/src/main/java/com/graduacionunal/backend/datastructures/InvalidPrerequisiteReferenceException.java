package com.graduacionunal.backend.datastructures;

import java.util.Collections;
import java.util.List;

/**
 * Se lanza cuando una relacion de prerequisito referencia materias
 * que no se encuentran en la lista proporcionada al calculador.
 */
public class InvalidPrerequisiteReferenceException extends RuntimeException {

    private final List<String> invalidReferences;

    public InvalidPrerequisiteReferenceException(List<String> invalidReferences) {
        super(buildMessage(invalidReferences));
        this.invalidReferences = Collections.unmodifiableList(invalidReferences);
    }

    public List<String> getInvalidReferences() {
        return invalidReferences;
    }

    private static String buildMessage(List<String> invalidReferences) {
        return "Se encontraron prerequisitos con referencias invalidas: " + String.join("; ", invalidReferences);
    }
}
