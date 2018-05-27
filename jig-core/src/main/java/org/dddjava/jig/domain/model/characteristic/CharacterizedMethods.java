package org.dddjava.jig.domain.model.characteristic;

import org.dddjava.jig.domain.model.declaration.method.MethodDeclaration;
import org.dddjava.jig.domain.model.declaration.method.MethodDeclarations;
import org.dddjava.jig.domain.model.implementation.bytecode.MethodByteCode;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharacterizedMethods {

    List<CharacterizedMethod> list;

    public CharacterizedMethods(List<MethodByteCode> methodByteCodes, CharacterizedTypes characterizedTypes) {
        list = new ArrayList<>();
        for (MethodByteCode methodByteCode : methodByteCodes) {
            CharacterizedType characterizedType = characterizedTypes.stream().pickup(methodByteCode.methodDeclaration.declaringType());
            list.add(new CharacterizedMethod(methodByteCode, characterizedType));
        }
    }

    private CharacterizedMethods(List<CharacterizedMethod> list) {
        this.list = list;
    }

    public MethodDeclarations serviceMethods() {
        return methodsOf(MethodCharacteristic.SERVICE_METHOD);
    }

    public MethodDeclarations repositoryMethods() {
        return methodsOf(MethodCharacteristic.REPOSITORY_METHOD);
    }

    public MethodDeclarations mapperMethods() {
        return methodsOf(MethodCharacteristic.MAPPER_METHOD);
    }

    private MethodDeclarations methodsOf(MethodCharacteristic methodCharacteristic) {
        return list.stream()
                .filter(characterizedMethod -> {
                    return characterizedMethod.has(methodCharacteristic);
                })
                .map(CharacterizedMethod::methodDeclaration)
                .collect(MethodDeclarations.collector());
    }

    public MethodDeclarations decisionMethods() {
        return list.stream()
                .filter(CharacterizedMethod::hasDecision)
                .map(CharacterizedMethod::methodDeclaration)
                .collect(MethodDeclarations.collector());
    }

    public MethodCharacteristics characteristicsOf(MethodDeclaration methodDeclaration) {
        return list.stream()
                .filter(characterizedMethod -> characterizedMethod.methodDeclaration().equals(methodDeclaration))
                .findFirst()
                .map(CharacterizedMethod::characteristics)
                .orElseGet(() -> new MethodCharacteristics(Collections.emptyList()));
    }

    public MethodDeclarations modelBoolQueryMethods() {
        return list.stream()
                .filter(characterizedMethod -> characterizedMethod.has(MethodCharacteristic.MODEL_METHOD))
                .filter(characterizedMethod -> characterizedMethod.has(MethodCharacteristic.BOOL_QUERY))
                .map(CharacterizedMethod::methodDeclaration)
                .collect(MethodDeclarations.collector());
    }

}