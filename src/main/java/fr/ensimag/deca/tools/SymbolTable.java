package fr.ensimag.deca.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage unique symbols.
 * 
 * A Symbol contains the same information as a String, but the SymbolTable
 * ensures the uniqueness of a Symbol for a given String value. Therefore,
 * Symbol comparison can be done by comparing references, and the hashCode()
 * method of Symbols can be used to define efficient HashMap (no string
 * comparison or hashing required).
 * 
 * @author gl50
 * @date 01/01/2025
 */
public class SymbolTable {
    private Map<String, Symbol> map = new HashMap<String, Symbol>();

    /**
     * Create or reuse a symbol.
     * 
     * If a symbol already exists with the same name in this table, then return
     * this Symbol. Otherwise, create a new Symbol and add it to the table.
     */
    public Symbol create(String name) {
        Symbol symbol = map.computeIfAbsent(name, Symbol::new);
        // create a new Symbol and add it to the table
        return symbol;
    }

    public boolean contains(String name) {
        return map.containsKey(name);
    }


    public static class Symbol {
        // Constructor is private, so that Symbol instances can only be created
        // through SymbolTable.create factory (which thus ensures uniqueness
        // of symbols).
        private Symbol(String name) {
            super();
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }


        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true; // Same reference, so they are equal
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false; // Null or different class, so not equal
            }
            Symbol other = (Symbol) obj; // Safe cast since the class matches
            return name.equals(other.name); // Compare the names
        }

        @Override
        public int hashCode() {
            return name.hashCode(); // Use the hash code of the name
        }

        private String name;
    }
}
