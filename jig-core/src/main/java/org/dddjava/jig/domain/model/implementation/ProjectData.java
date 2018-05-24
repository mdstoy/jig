package org.dddjava.jig.domain.model.implementation;

import org.dddjava.jig.domain.model.characteristic.CharacterizedTypes;
import org.dddjava.jig.domain.model.declaration.field.FieldDeclarations;
import org.dddjava.jig.domain.model.declaration.method.MethodDeclarations;
import org.dddjava.jig.domain.model.implementation.bytecode.MethodUsingFields;
import org.dddjava.jig.domain.model.implementation.datasource.Sqls;
import org.dddjava.jig.domain.model.implementation.relation.ImplementationMethods;
import org.dddjava.jig.domain.model.implementation.relation.MethodRelations;
import org.dddjava.jig.domain.model.networks.TypeDependencies;
import org.dddjava.jig.domain.model.values.ValueTypes;

public class ProjectData {

    private MethodDeclarations mapperMethods;
    private ImplementationMethods implementationMethods;
    private MethodRelations methodRelations;
    private Sqls sqls;
    private MethodUsingFields methodUsingFields;
    private ValueTypes valueTypes;
    private FieldDeclarations staticFieldDeclarations;
    private CharacterizedTypes characterizedTypes;
    private TypeDependencies typeDependencies;
    private FieldDeclarations fieldDeclarations;

    public void setMapperMethods(MethodDeclarations mapperMethods) {
        this.mapperMethods = mapperMethods;
    }

    public void setImplementationMethods(ImplementationMethods implementationMethods) {
        this.implementationMethods = implementationMethods;
    }

    public void setMethodRelations(MethodRelations methodRelations) {
        this.methodRelations = methodRelations;
    }

    public void setSqls(Sqls sqls) {
        this.sqls = sqls;
    }

    public void setMethodUsingFields(MethodUsingFields methodUsingFields) {
        this.methodUsingFields = methodUsingFields;
    }

    public void setValueTypes(ValueTypes valueTypes) {
        this.valueTypes = valueTypes;
    }

    public void setStaticFieldDeclarations(FieldDeclarations staticFieldDeclarations) {
        this.staticFieldDeclarations = staticFieldDeclarations;
    }

    public void setCharacterizedTypes(CharacterizedTypes characterizedTypes) {
        this.characterizedTypes = characterizedTypes;
    }

    public void setTypeDependencies(TypeDependencies typeDependencies) {
        this.typeDependencies = typeDependencies;
    }

    public void setFieldDeclarations(FieldDeclarations fieldDeclarations) {
        this.fieldDeclarations = fieldDeclarations;
    }

    public MethodDeclarations mapperMethods() {
        return mapperMethods;
    }

    public ImplementationMethods implementationMethods() {
        return implementationMethods;
    }

    public MethodRelations methodRelations() {
        return methodRelations;
    }

    public Sqls sqls() {
        return sqls;
    }

    public CharacterizedTypes characterizedTypes() {
        return characterizedTypes;
    }

    public TypeDependencies typeDependencies() {
        return typeDependencies;
    }

    public FieldDeclarations fieldDeclarations() {
        return fieldDeclarations;
    }

    public FieldDeclarations staticFieldDeclarations() {
        return staticFieldDeclarations;
    }

    public ValueTypes valueTypes() {
        return valueTypes;
    }

    public MethodUsingFields methodUsingFields() {
        return methodUsingFields;
    }
}