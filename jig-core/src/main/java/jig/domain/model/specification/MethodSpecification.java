package jig.domain.model.specification;

import jig.domain.model.identifier.field.FieldIdentifier;
import jig.domain.model.identifier.method.MethodIdentifier;
import jig.domain.model.identifier.method.MethodSignature;
import jig.domain.model.identifier.type.TypeIdentifier;
import org.objectweb.asm.Type;

import java.util.*;
import java.util.stream.Collectors;

public class MethodSpecification {

    public final MethodIdentifier identifier;
    private Set<TypeIdentifier> useTypes = new HashSet<>();
    private final TypeIdentifier returnType;

    public MethodSpecification(TypeIdentifier classTypeIdentifier, String name, String descriptor, String[] exceptions) {
        this.returnType = new TypeIdentifier(Type.getReturnType(descriptor).getClassName());
        List<TypeIdentifier> argumentTypes = Arrays.stream(Type.getArgumentTypes(descriptor))
                .map(Type::getClassName)
                .map(TypeIdentifier::new)
                .collect(Collectors.toList());
        this.identifier = new MethodIdentifier(classTypeIdentifier, new MethodSignature(name, argumentTypes));

        this.useTypes.add(this.returnType);
        this.useTypes.addAll(argumentTypes);

        if (exceptions != null) {
            for (String exception : exceptions) {
                this.useTypes.add(new TypeIdentifier(exception));
            }
        }
    }

    public final List<FieldIdentifier> usingFieldTypeIdentifiers = new ArrayList<>();
    public final List<MethodIdentifier> usingMethodIdentifiers = new ArrayList<>();

    public TypeIdentifier getReturnTypeName() {
        return returnType;
    }

    public void addFieldInstruction(String owner, String name, String descriptor) {
        // 使っているフィールドの型がわかればOK
        Type type = Type.getType(descriptor);
        TypeIdentifier fieldType = new TypeIdentifier(type.getClassName());
        usingFieldTypeIdentifiers.add(new FieldIdentifier(name, fieldType));
    }

    public void addMethodInstruction(String owner, String name, String descriptor) {
        // 使ってるメソッドがわかりたい
        TypeIdentifier ownerTypeIdentifier = new TypeIdentifier(owner);
        List<TypeIdentifier> arguments = Arrays.stream(Type.getArgumentTypes(descriptor))
                .map(Type::getClassName)
                .map(TypeIdentifier::new)
                .collect(Collectors.toList());
        MethodIdentifier methodIdentifier = new MethodIdentifier(ownerTypeIdentifier, new MethodSignature(name, arguments));
        usingMethodIdentifiers.add(methodIdentifier);
        useTypes.add(ownerTypeIdentifier);
    }

    public Set<TypeIdentifier> useTypes() {
        return useTypes;
    }
}
