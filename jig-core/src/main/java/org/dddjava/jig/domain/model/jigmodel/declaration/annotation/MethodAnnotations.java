package org.dddjava.jig.domain.model.jigmodel.declaration.annotation;

import org.dddjava.jig.domain.model.jigmodel.declaration.method.MethodDeclaration;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * メソッドにつけられたアノテーション一覧
 */
public class MethodAnnotations {

    List<MethodAnnotation> list;

    public MethodAnnotations(List<MethodAnnotation> list) {
        this.list = list;
    }

    public List<MethodAnnotation> list() {
        return list;
    }

    public Annotations annotations() {
        return new Annotations(list.stream().map(e -> e.annotation).collect(toList()));
    }

    public MethodAnnotations filter(MethodDeclaration methodDeclaration) {
        return new MethodAnnotations(
                list.stream()
                        .filter(a -> a.methodDeclaration.sameIdentifier(methodDeclaration))
                        .collect(toList())
        );
    }
}