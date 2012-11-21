grammar cgrammar;

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

program: block* EOF;

block: '{' statement* '}' -> ^(BLOCK statement*);

statement:
  block |
  expression ';'! |
  declaration ';'! |
  if_stat | switch_stat | while_stat | for_stat | dowhile_stat;

expression: 
  assignment_expression |
  rvalue |
  constant;
  
const_expression: constant |
  sizeof |
  const_arithmetic_expression;
  //@TODO: Address constants
  
const_arithmetic_expression: '5 + 5'; //@TODO: const arithmetic expressions

assignment_expression: rvalue '='^ expression;

rvalue: ID;

constant: INT | FLOAT | STRING | CHAR;

sizeof: SIZEOF ( ID | '(' ID ')' );

//** CONTROL STATEMENTS START **//

if_stat: IF '(' expression ')' statement (ELSE statement)?;

switch_stat: SWITCH '(' expression ')' '{' (CASE const_expression ':' statement*)+ (DEFAULT ':' statement*)? '}';

while_stat: WHILE '(' expression ')' statement;

for_stat: FOR '(' expression ';' expression ';' expression ')' statement;

dowhile_stat: DO statement WHILE '(' expression ')' ';';

//** CONTROL STATEMENTS END **//

//** DECLARATION START **//

declaration: variable_declaration | function_declaration;

variable_declaration: var_declaration_specifier+ init_declarator;

var_declaration_specifier: storage_class_specifier | type_specifier | type_qualifier;

storage_class_specifier : STATIC | EXTERN | REGISTER | AUTO;

type_specifier: ID | STRUCT ID | ENUM ID;

type_qualifier: RESTRICT | VOLATILE | CONST;

function_specifier: INLINE;

init_declarator: declarator ( '=' initializer)?;

declarator: pointer* non_pointer_declarator;

pointer: '*' type_qualifier*;

non_pointer_declarator : direct_declarator ('[' assignment_expression ']')?;

direct_declarator: ID | '(' declarator ')' ;

function_declaration: type_specifier pointer* ID '(' ')';

fun_declaration_specifier: function_specifier | type_specifier;

fun_declarator: pointer? ID '(' ')';

initializer: INT;

//** DECLARATION END **/

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

//** KEYWORDS END **//

ID  : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT : '0'..'9'+
    ;

FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;

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
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;

