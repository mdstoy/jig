package org.dddjava.jig.infrastructure.configuration;

import org.dddjava.jig.domain.model.parts.annotation.Annotation;
import org.dddjava.jig.domain.model.parts.class_.type.TypeIdentifier;
import org.dddjava.jig.domain.model.sources.jigfactory.Architecture;
import org.dddjava.jig.domain.model.sources.jigfactory.TypeFact;

import java.util.List;
import java.util.regex.Pattern;

public class PropertyArchitectureFactory {

    JigProperties jigProperties;

    public PropertyArchitectureFactory(JigProperties jigProperties) {
        this.jigProperties = jigProperties;
    }

    public Architecture architecture() {
        Pattern compilerGeneratedClassPattern = Pattern.compile(".+\\$\\d+");
        Pattern businessRulePattern = Pattern.compile(jigProperties.getBusinessRulePattern());

        return new Architecture() {

            @Override
            public boolean isRepositoryImplementation(TypeFact typeFact) {
                // TODO インタフェース実装を見てない
                // DataSourceは Repositoryインタフェースが実装され @Repository のついた infrastructure/datasource のクラス
                List<Annotation> typeAnnotations = typeFact.listAnnotations();
                TypeIdentifier repositoryAnnotation = new TypeIdentifier("org.springframework.stereotype.Repository");
                return typeAnnotations.stream()
                        .anyMatch(annotation -> annotation.is(repositoryAnnotation));
            }

            @Override
            public boolean isBusinessRule(TypeFact typeFact) {
                String fqn = typeFact.typeIdentifier().fullQualifiedName();
                return businessRulePattern.matcher(fqn).matches()
                        && !compilerGeneratedClassPattern.matcher(fqn).matches();
            }
        };
    }
}
