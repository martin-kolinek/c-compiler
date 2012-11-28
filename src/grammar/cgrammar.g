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

program: external_declaration* EOF;

//sadly this is required because otherwise we get an error from antlr
external_declaration: decl_specs declarator (block | ( '=' initializer))?;

block: '{' in_block* '}';

in_block: declaration | statement;

statement:
  block |
  expression? ';' |
  if_stat | switch_stat | while_stat | for_stat | dowhile_stat | jmp_stat;

//expressions by JMK

expression: 
  assignment_expression |
  //rvalue | //naco nam je rvalue? JMK
  //constant |
  primary_expression;

primary_expression:
  ID |
  const_expression | //constant |
  STRING |
  '(' expression ')';

const_expression: constant |
  sizeof |
  const_arithmetic_expression;
  //@TODO: Address constants
  
postfix_expression:
  primary_expression postfix_expression2 | 
  '(' type_name ')' '{' initializer_list '}' postfix_expression2  |
  '(' type_name ')' '{' initializer_list ',' '}' postfix_expression2 ;
  
postfix_expression2: //JMK - odstranenie lavej rekurzie
  |
  '[' expression ']' postfix_expression2 |
  '(' argument_expression_list ')' postfix_expression2 |
  '.' ID postfix_expression2  |
  '->' ID  postfix_expression2  |
  '++' postfix_expression2  |
  '--' postfix_expression2  |
  ;  
  
argument_expression_list:
  assignment_expression |
  argument_expression_list ',' assignment_expression ;  
  


unary_expression:
  postfix_expression  |
  '++' unary_expression |
  '--' unary_expression |
  '&' cast_expression | 
  '*' cast_expression | 
  '+' cast_expression | 
  '-' cast_expression | 
  '~' cast_expression | 
  '!' cast_expression |
  sizeof unary_expression |
  sizeof '(' type_name ')';

cast_expression:
  unary_expression  |
  '(' type_name ')' cast_expression;
  
//unary_operator: OPERATOR;
 
//END expressions 
  
const_arithmetic_expression: '5 + 5'; //@TODO: const arithmetic expressions - toto by sme nemali robit v gramatike

assignment_expression: rvalue '='^ expression;

rvalue: ID;

constant: INT | FLOAT | STRING | CHAR;

sizeof: SIZEOF ( ID | '(' ID ')' );

//** CONTROL STATEMENTS START **//

if_stat: IF '(' expression ')' statement 
  ( (ELSE)=> ELSE statement
    | ( ) // nothing
  );

switch_stat: SWITCH '(' expression ')' '{' (CASE const_expression ':' statement*)* (DEFAULT ':' statement*)? '}';

while_stat: WHILE '(' expression ')' statement;

for_stat: FOR '(' expression ';' expression ';' expression ')' statement;

dowhile_stat: DO statement WHILE '(' expression ')' ';';

jmp_stat: BREAK ';' | CONTINUE ';' | RETURN expression? ';';
//** CONTROL STATEMENTS END **//

//** DECLARATION START **//

declaration: decl_specs init_declarator ';';

function_definition: decl_specs declarator block;

decl_specs: declaration_specifier decl_specs | ID declaration_specifier*;

declaration_specifier: storage_class_specifier | type_specifier | type_qualifier | function_specifier;

storage_class_specifier : STATIC | EXTERN | REGISTER | AUTO | TYPEDEF;

type_specifier: primitive_type | STRUCT ID | ENUM ID;

type_qualifier: RESTRICT | VOLATILE | CONST;

function_specifier: INLINE;

init_declarator: declarator ( '=' initializer)?;

declarator: pointer* direct_declarator;

pointer: '*' type_qualifier*;

direct_declarator: simple_declarator declarator_suffix?;

simple_declarator: ID | '(' declarator ')';

declarator_suffix: param_declarator_suffix |
  '(' parameter_list ')';

parameter_list: parameter_declaration (',' parameter_declaration )* (',' '...')? | '...';

parameter_declaration: declaration_specifier+ param_declarator;

param_declarator: param_declarator_suffix | direct_param_declarator param_declarator_suffix?;

direct_param_declarator: pointer* simple_param_declarator; 

simple_param_declarator: ID | '(' param_declarator ')';

param_declarator_suffix: 
  '[' STATIC? type_qualifier* assignment_expression ']' |
  '[' type_qualifier+ STATIC assignment_expression ']' |
  '[' type_qualifier* '*' ']' ;

initializer: assignment_expression | '{' initializer_list ','? '}';

initializer_list: initialization (',' initialization)*;

initialization: (designator '=')? initializer;

designator: '.' ID | '[' assignment_expression ']';

//** DECLARATION END **// 

//** PRIMITIVE TYPES START **//


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

