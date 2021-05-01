package org.dddjava.jig.domain.model.parts.class_.field;

import org.dddjava.jig.domain.model.parts.class_.type.TypeIdentifier;

/**
 * フィールド定義
 */
public class FieldDeclaration {

    TypeIdentifier declaringType;
    FieldType fieldType;
    String name;

    public FieldDeclaration(TypeIdentifier declaringType, FieldType fieldType, String name) {
        this.declaringType = declaringType;
        this.name = name;
        this.fieldType = fieldType;
    }

    public TypeIdentifier typeIdentifier() {
        return fieldType.nonGenericTypeIdentifier();
    }

    public String nameText() {
        return name;
    }

    public String signatureText() {
        return String.format("%s %s", typeIdentifier().asSimpleText(), name);
    }

    public TypeIdentifier declaringType() {
        return declaringType;
    }

    public FieldType fieldType() {
        return fieldType;
    }
}