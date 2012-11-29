grammar expressions;

options {
  language = Java;
  output = AST;
}

tokens {
  BLOCK;
  DECL;
}

@header {
    package grammar.generated;
} 
@lexer::header {package grammar.generated;}

expression: ; //JMK - doplnim

primary_expression:
  identifier  |
  constant_expression  |
//  string-literal  | //JMK - spada pod constant
  '(' expression ')'  
  ;

postfix_expression:  primary_expression postfix_expression_s*|
  '(' type_name ')' '{' initializer_list ','? '}'
  ;

argument_expression_list: assignment_expression |
  assignment_expression ',' argument_expression_list
  ;


postfix_expression_s: '[' expression ']'  |
  '(' argument_expression_list ')'  |
  '.' identifier  |
  '->' identifier |
  '++'  |
  '--'
  ;

unary_expression  :
  primary_expression postfix_expression_s*  |
  '(' type_name ')' '{' initializer_list ','? '}' |
  '++' unary_expression |
  '--' unary_expression |
  sizeof  '++' unary_expression |
  sizeof  '--' unary_expression |
  sizeof  primary_expression postfix_expression_s*  |
  sizeof '(' type_name ')' ('{' initializer_list ','? '}')?
  '&' cast_expression |
  '*' cast_expression |
  '+' cast_expression |
  '-' cast_expression |
  '~' cast_expression |
  '!' cast_expression
  ;

cast_expression : ('(' type_name ')')? unary_expression
  ;
  
multiplicative_expression :
  cast_expression multiplicative_expression_2*
  ;

multiplicative_expression_2 :
  ('/' ('(' type_name ')') unary_expression) |
  ('%' ('(' type_name ')')* unary_expression)  |
  ('*' ('(' type_name ')') unary_expression)
  ;

additive_expression : multiplicative_expression additive_expression2*
  ;

additive_expression2  :  '+' multiplicative_expression |
  '-' multiplicative_expression 
  ;

shift_expression  : additive_expression shift_expression2*
 ;

shift_expression2 : '<<' additive_expression  |
  '>>' additive_expression
  ;

relational_expression : shift_expression  relational_expression2*
 ;

relational_expression2  : '<' shift_expression  |
  '>' shift_expression  |
  '<=' shift_expression |
  '>=' shift_expression
  ;



//unary_expression2  : '(' type_name ')' ('{' initializer_list ','? '}')?;

/*unary-operator cast-expression
sizeof unary-expression
sizeof ( type-name )*/


identifier: ID;

constant_expression : constant  |
//  wide_string literal |
  wide_char_literal
  ;

wide_char_literal : 'L' CHAR
  ;//@TODO + wide_string literal



/*odtialto su skopirovane veci*/

sizeof: SIZEOF ( ID | '(' ID ')' );

initializer_list: initialization (',' initialization)*;

initialization: (designator '=')? initializer;

designator: '.' ID | '[' assignment_expression ']';

initializer: assignment_expression | '{' initializer_list ','? '}';

assignment_expression: rvalue '='^ expression;

rvalue: ID;

constant: INT | FLOAT | STRING | CHAR;

type_name: primitive_type //JMK kvoli expression
  //| iny typ
         ;

primitive_type: LONG | INT_T | DOUBLE | FLOAT_T | VOID | CHAR_T | SHORT | SIGNED | UNSIGNED | BOOL;

LONG: 'long';

INT_T: 'int';

DOUBLE: 'double';

FLOAT_T: 'float';

VOID: 'void';

CHAR_T: 'char';

SHORT: 'short';

SIGNED: 'signed';

UNSIGNED: 'unsigned';

BOOL: '_Bool';

//** PRIMITIVE TYPES END **//

//** KEYWORDS START **//

STATIC : 'static';

EXTERN : 'extern';

REGISTER: 'register';

AUTO: 'auto';

TYPEDEF: 'typedef';

VOLATILE: 'volatile';

CONST: 'const';

RESTRICT: 'restrict';

INLINE: 'inline';

STRICT: 'strict';

IF: 'if';

ELSE: 'else';

FOR: 'for';

DO: 'do';

WHILE: 'while';

SWITCH: 'switch';

CASE: 'case';

SIZEOF: 'sizeof';

DEFAULT: 'default';

ENUM: 'enum';

STRUCT: 'struct';

BREAK: 'break';

CONTINUE: 'continue';

RETURN: 'return';

//** KEYWORDS END **//

/*OPERATOR  : '&' | '*' | '+' | '-' | '~' | '!'
          ;*///JMK - takto to nepojde, lebo kazdy operator vyzaduje generovanie ineho kodu

ID  : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT : '1'..'9' ('0'..'9')* INT_SUFFIX?
    | '0' ('0'..'7')* INT_SUFFIX?
    | HEXA_PREFIX HEXA_SYMBOL+ INT_SUFFIX?
    ;

fragment
HEXA_SYMBOL :'0'..'9' | 'a'..'f' | 'A'..'F';

fragment
HEXA_PREFIX : '0x' | '0X';

fragment
INT_SUFFIX : U_SUFFIX L_SUFFIX? | L_SUFFIX U_SUFFIX?;

fragment
U_SUFFIX: 'u' | 'U';

fragment
L_SUFFIX: 'l' | 'L' | 'LL' | 'll';

FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT? FLOAT_SUFFIX?
    |   '.' ('0'..'9')+ EXPONENT? FLOAT_SUFFIX?
    |   ('0'..'9')+ EXPONENT FLOAT_SUFFIX?
    |   HEXA_PREFIX HEXA_FRAC BIN_EXP? FLOAT_SUFFIX?;

fragment
HEXA_FRAC:
    HEXA_SYMBOL+ '.' HEXA_SYMBOL* |
    '.' HEXA_SYMBOL+ |
    HEXA_SYMBOL+;

fragment
BIN_EXP:('p'|'P') ('+'|'-')? ('0'..'9')+ ;

fragment
FLOAT_SUFFIX : 'f' | 'F' | 'l' | 'L';

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

CHAR:  '\'' ( ESC_SEQ | ~('\''|'\\') ) '\''
    ;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'v'|'a'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    |   HEXA_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;
    
fragment
HEXA_ESC
    :   '\\x' HEXA_SYMBOL+;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;




