package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.context.ClassDefinition;


// A FAIRE: étendre cette classe pour traiter la partie "avec objet" de Déca
/**
 * Environment containing types. Initially contains predefined identifiers, more
 * classes can be added with declareClass().
 *
 * @author gl50
 * @date 01/01/2025
 */
public class EnvironmentType {
    public EnvironmentType(DecacCompiler compiler) {
        
        envTypes = new LinkedHashMap<Symbol, Definition>();
        
        Symbol intSymb = compiler.createSymbol("int");
        INT = new IntType(intSymb);
        envTypes.put(intSymb, new TypeDefinition(INT, Location.BUILTIN));

        Symbol floatSymb = compiler.createSymbol("float");
        FLOAT = new FloatType(floatSymb);
        envTypes.put(floatSymb, new TypeDefinition(FLOAT, Location.BUILTIN));

        Symbol voidSymb = compiler.createSymbol("void");
        VOID = new VoidType(voidSymb);
        envTypes.put(voidSymb, new TypeDefinition(VOID, Location.BUILTIN));

        Symbol booleanSymb = compiler.createSymbol("boolean");
        BOOLEAN = new BooleanType(booleanSymb);
        envTypes.put(booleanSymb, new TypeDefinition(BOOLEAN, Location.BUILTIN));

        Symbol stringSymb = compiler.createSymbol("string");
        STRING = new StringType(stringSymb);
        envTypes.put(booleanSymb, new TypeDefinition(STRING, Location.BUILTIN));

        Symbol objectSymb = compiler.createSymbol("Object");
        OBJECT = new ClassType(objectSymb);
        envTypes.put(objectSymb, new ClassDefinition(OBJECT,Location.BUILTIN,null));
    }

    private final Map<Symbol, Definition> envTypes;

    public Definition def(Symbol s) {
        return envTypes.get(s);
    }

    public final VoidType    VOID;
    public final IntType     INT;
    public final FloatType   FLOAT;
    public final StringType  STRING;
    public final BooleanType BOOLEAN;
    public final ClassType   OBJECT;

    /**
     * Check if a value of type T2 can be assigned to a variable of type T1.
     *
     * @param T1 the type of the variable.
     * @param T2 the type of the value to assign.
     * @return true if the assignment is compatible, false otherwise.
     */
    public boolean assignCompatible(Type T1, Type T2) {
        // Règle 1 : T1 est float et T2 est int
        if (T1.isFloat() && T2.isInt()) {
            return true;
        }

        // Règle 2 : Vérifier si T2 est un sous-type de T1
        return T2.isSubTypeOf(T1);
    }

}
