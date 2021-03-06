package org.dddjava.jig.infrastructure.asm;

import org.dddjava.jig.domain.model.sources.file.binary.ClassSource;
import org.dddjava.jig.domain.model.sources.file.binary.ClassSources;
import org.dddjava.jig.domain.model.sources.jigfactory.TypeFact;
import org.dddjava.jig.domain.model.sources.jigfactory.TypeFacts;
import org.dddjava.jig.domain.model.sources.jigreader.FactReader;
import org.objectweb.asm.ClassReader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * ASMを使用したFactFactoryの実装
 *
 * ClassSourceを読み取るので、JVM言語なら使用できると思います。
 */
@Repository
public class AsmFactReader implements FactReader {

    @Override
    public TypeFacts readTypeFacts(ClassSources classSources) {
        List<TypeFact> list = new ArrayList<>();
        for (ClassSource source : classSources.list()) {
            TypeFact typeFact = typeByteCode(source);
            list.add(typeFact);
        }
        return new TypeFacts(list);
    }

    TypeFact typeByteCode(ClassSource classSource) {
        AsmClassVisitor asmClassVisitor = new AsmClassVisitor(classSource);

        ClassReader classReader = new ClassReader(classSource.value());
        classReader.accept(asmClassVisitor, ClassReader.SKIP_DEBUG);

        return asmClassVisitor.typeFact();
    }
}