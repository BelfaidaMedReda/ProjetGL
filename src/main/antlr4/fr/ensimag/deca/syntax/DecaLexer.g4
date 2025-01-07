lexer grammar DecaLexer;

options {
   language=Java;
   superClass = AbstractDecaLexer;
}

@header{
    import fr.ensimag.deca.syntax.DecaRecognitionException;
}

@members {

}

// Mots réservés
ASM: 'asm';
CLASS: 'class';
EXTENDS: 'extends';
ELSE: 'else';
FALSE: 'false';
IF: 'if';
INSTANCEOF: 'instanceof';
NEW: 'new';
NULL: 'null';
READINT: 'readInt';
READFLOAT: 'readFloat';
PRINT: 'print';
PRINTLN: 'println';
PRINTLNX: 'printlnx';
PRINTX: 'printx';
PROTECTED: 'protected';
RETURN: 'return';
THIS: 'this';
TRUE: 'true';
WHILE: 'while';

// Identificateurs
fragment LETTER: 'a'..'z'|'A'..'Z';
fragment DIGIT: '0'..'9';
IDENT: (LETTER | '$' | '_') (LETTER | DIGIT | '$' | '_')*;

// Symboles et opérateurs
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
MOD: '%';
DOT: '.';
COMMA: ',';
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACK: '[';
RBRACK: ']';
SEMI: ';';
COLON: ':';
QUESTION: '?';
EQ: '=';
EQEQ: '==';
NEQ: '!=';
LT: '<';
GT: '>';
LEQ: '<=';
GEQ: '>=';
AND: '&&';
OR: '||';
NOT: '!';

// Littéraux Entiers
fragment POSITIVE_DIGIT: '1'..'9';
INT: '0' | POSITIVE_DIGIT DIGIT*;

// Littéraux flottants
fragment NUM: DIGIT+;
fragment SIGN: ('+'|'-');
fragment DEC: NUM '.' NUM;
fragment EXP: [Ee] SIGN NUM;
fragment FLOATDEC : (DEC | DEC EXP) ('F' | 'f') ;
fragment DIGITHEX : '0' .. '9' | 'A' .. 'F' | 'a' .. 'f';
fragment NUMHEX: DIGITHEX+;
fragment FLOATHEX: ('0x' | '0X') NUMHEX '.' NUMHEX ([Pp]) SIGN NUM ([Ff])?;
FLOAT : FLOATDEC | FLOATHEX;

// Chaînes de caractères
fragment STRING_CAR : ~('\n'|'"'|'\\') ;
STRING: '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING: '"' (STRING_CAR | '\n' | '\\"' | '\\\\')* '"';

// Commentaires
fragment CLASSICAL_COMMENT : '/*' .*? '*/' ;
fragment MONOLINE_COMMENT : '//' .*? ('\n' | EOF);
fragment COMMENT : (CLASSICAL_COMMENT | MONOLINE_COMMENT);

// Espaces blancs et séparateurs
WS: (' ' | '\r' | '\n' | '\t' | COMMENT){
    skip();
};

// Inclusion de fichier
FILENAME: (LETTER | DIGIT | '.' | '-' | '_')+;
INCLUDE: '#include' [ \t]* '"' FILENAME '"';

DEFAULT : .{
   if(1>0){
        throw new DecaRecognitionException(this, (IntStream) _input);
   }
};