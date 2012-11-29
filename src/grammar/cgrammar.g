grammar cgrammar;

options {
  language = Java;
  output = AST;
  backtrack=true;
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


primary_expression:
  identifier  |
  constant_expression  |
//  string-literal  | //JMK - spada pod constant
  '(' expression ')'  
  ;

postfix_expression:  primary_expression postfix_expression_s*|
  '(' type_name ')' '{' initializer_list ','? '}'
  ;

argument_expression_list:// assignment_expression |
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
  ('/' ('(' type_name ')')* unary_expression) |
  ('%' ('(' type_name ')')* unary_expression)  |
  ('*' ('(' type_name ')')* unary_expression)
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

equality_expression : relational_expression equality_expression2*
  ;

equality_expression2  : '==' relational_expression  |
  '!=' relational_expression
  ;


and_expression  : equality_expression ('&' equality_expression)*
  ;

exclusive_or_expression : and_expression  ('^' and_expression)*
  ;

inclusive_or_expression : exclusive_or_expression ('|' exclusive_or_expression)*
  ;

logical_and_expression  : inclusive_or_expression '&&' inclusive_or_expression
  ;

logical_or_expression : logical_and_expression  ('||' logical_and_expression)*
  ;

conditional_expression  : logical_or_expression ('?' expression ':'  logical_or_expression)*
  ;

assignment_expression : conditional_expression  assignment_expression2* //TODO conditional expression musi byt unary ak za nou ide assigmant expression2
  ;

expression  : assignment_expression (',' assignment_expression)*  
  ;

assignment_expression2  : '=' conditional_expression  | 
  '*='  conditional_expression  |
  '/='  conditional_expression  |
  '%='  conditional_expression  |
  '+='  conditional_expression  |
  '-='  conditional_expression  |
  '<<=' conditional_expression  |
  '>>=' conditional_expression  |
  '&='  conditional_expression  |
  '^='  conditional_expression  |
  '|='  conditional_expression
  ;

identifier: ID;

//constant_expression : conditional_expression  ;

constant_expression : constant  |
  //conditional_expression  | //moze sa optimalizovat
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

//assignment_expression: rvalue '='^ expression;

rvalue  : ID;

constant  : INT | FLOAT | STRING | CHAR;

/*type_name : //primitive_type |
  identifier//JMK kvoli expression
  //| iny typ
         ;
*/

type_name :  spec_qual_list (abstract_declarator)?
  ;

abstract_declarator :  pointer direct_abstract_declarator?  |
  direct_abstract_declarator
  ;

direct_abstract_declarator  : ('(' abstract_declarator ')') direct_abstract_declarator2*  |
  direct_abstract_declarator2+
  ;
direct_abstract_declarator2 : '[' type_qualifier?  assignment_expression? ']'  |
  '[' STATIC type_qualifier?  assignment_expression ']' |
  '[' type_qualifier STATIC assignment_expression ']' |
  '[' '*' ']' |
  '(' parameter_list? ')'
  ;






//sadly this is required because otherwise we get an error from antlr
external_declaration: decl_specs declarator (block | ( '=' initializer))?;

block: '{' in_block* '}';

in_block: 
  (ID ';') => statement | //tu bude treba osetrit take, ze ten ID je typ  
  declaration;
   
statement:
  block |
  expression? ';' |
  if_stat | switch_stat | while_stat | for_stat | dowhile_stat | jmp_stat;

//expressions by JMK

/* 
//END expressions 

constant: INT | FLOAT | STRING | CHAR; //chceme mat string ako constant? nechceme ho nahodou vediet adresovat a zliepat atd. ?

sizeof: SIZEOF ( ID | '(' ID ')' );
*/
//** CONTROL STATEMENTS START **//

if_stat: IF '(' expression ')' statement 
  ( (ELSE)=> ELSE statement
    | ( ) // nothing
  );

switch_stat: SWITCH '(' expression ')' '{' (CASE conditional_expression ':' statement*)* (DEFAULT ':' statement*)? '}';

while_stat: WHILE '(' expression ')' statement;

for_stat: FOR '(' expression ';' expression ';' expression ')' statement;

dowhile_stat: DO statement WHILE '(' expression ')' ';';

jmp_stat: BREAK ';' | CONTINUE ';' | RETURN expression? ';';
//** CONTROL STATEMENTS END **//

//** DECLARATION START **//

declaration: decl_specs (init_declarator (',' init_declarator)* )? ';';

function_definition: decl_specs declarator block;

decl_specs: declaration_specifier decl_specs | ID declaration_specifier*;

spec_qual_list: spec_qual spec_qual_list | ID spec_qual*;

spec_qual: type_specifier | type_qualifier;

declaration_specifier: storage_class_specifier | type_specifier | type_qualifier | function_specifier;

storage_class_specifier : STATIC | EXTERN | REGISTER | AUTO | TYPEDEF;

type_specifier: primitive_type | struct_specifier | enum_specifier;

struct_specifier: STRUCT ID? '{' struct_declaration* '}'
  | STRUCT ID;

struct_declaration: spec_qual_list declarator (',' declarator)* ';';

enum_specifier: ENUM ID? '{' enumerator (',' enumerator)* ','? '}' 
  | ENUM ID;

enumerator: ID ('=' conditional_expression)?;

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

parameter_declaration: decl_specs param_declarator;

param_declarator: pointer* direct_param_declarator;

direct_param_declarator: param_declarator_suffix | simple_param_declarator param_declarator_suffix?; 

simple_param_declarator: ID | '(' param_declarator ')';

param_declarator_suffix: 
  '[' STATIC? type_qualifier* assignment_expression ']' |
  '[' type_qualifier+ STATIC assignment_expression ']' |
  '[' type_qualifier* '*' ']' ;
/*
initializer: assignment_expression | '{' initializer_list ','? '}';

initializer_list: initialization (',' initialization)*;

initialization: (designator '=')? initializer;

designator: '.' ID | '[' assignment_expression ']';

//to ci je to naozaj iba typ osetrime semantickymi pravidlami
type_name: parameter_declaration; 
*/
//** DECLARATION END **// 

//** PRIMITIVE TYPES START **//

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

