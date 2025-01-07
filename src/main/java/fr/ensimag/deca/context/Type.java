package fr.ensimag.deca.context;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

/**
 * Deca Type (internal representation of the compiler)
 *
 * @author gl50
 * @date 01/01/2025
 */

public abstract class Type {


    /**
     * True if this and otherType represent the same type (in the case of
     * classes, this means they represent the same class).
     */
    public abstract boolean sameType(Type otherType);

    private final Symbol name;

    public Type(Symbol name) {
        this.name = name;
    }

    public Symbol getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName().toString();
    }

    public boolean isClass() {
        return false;
    }

    public boolean isInt() {
        return false;
    }

    public boolean isFloat() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isVoid() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isClassOrNull() {
        return false;
    }

    /**
     * Returns the same object, as type ClassType, if possible. Throws
     * ContextualError(errorMessage, l) otherwise.
     *
     * Can be seen as a cast, but throws an explicit contextual error when the
     * cast fails.
     */
    public ClassType asClassType(String errorMessage, Location l)
            throws ContextualError {
        throw new ContextualError(errorMessage, l);
    }

    public boolean isSubTypeOf(Type otherType) {
        if(otherType.isNull()){
            return isNull();
        }
        if(otherType instanceof ClassType && this instanceof ClassType){
            return ((ClassType) this).isSubClassOf(((ClassType) otherType));
        }
        return false;
    }

    public boolean isUnaryOpSupported(String operator) {
        // Vérification si l'opérateur est supporté
        if (!operator.equals("-") && !operator.equals("!")) {
            return false; // Opérateur non supporté
        }

        // Traitement pour l'opérateur "-"
        if (operator.equals("-")) {
            return (isInt() || isFloat());
        }

        // Traitement pour l'opérateur "!"
        if (operator.equals("!")) {
            return isBoolean();
        }

        // Par défaut, retourne false (même si normalement on ne devrait pas arriver ici)
        return false;
    }

    public boolean isBinaryOpSupported(String operator, Type otherType) {
        // Vérification des opérateurs arithmétiques : +, -, *, /
        if (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/")) {
            return (isInt() || isFloat()) &&
                    (otherType.isInt() || otherType.isFloat());
        }

        // Vérification pour l'opérateur mod : %
        if (operator.equals("%")) {
            return (isInt()) && (otherType.isInt());
        }

        // Vérification des opérateurs de comparaison : ==, !=, <, >, <=, >=
        if (operator.equals("==") || operator.equals("!=") || operator.equals("<") || operator.equals(">") ||
                operator.equals("<=") || operator.equals(">=")) {
            if (isInt() || isFloat()) {
                return otherType.isInt() || otherType.isFloat();
            } else if (operator.equals("==") || operator.equals("!=")) {
                return (isClassOrNull()) && (otherType.isClassOrNull());
            }
        }

        // Vérification des opérateurs logiques : &&, ||, ==, !=
        if (operator.equals("&&") || operator.equals("||") || operator.equals("==") || operator.equals("!=")) {
            return (isBoolean()) && (otherType.isBoolean());
        }

        // Si aucun cas ne correspond, l'opération n'est pas supportée
        return false;
    }




}
