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
    import declaration.specifiers.*;
    import declaration.declarator.*;
    import declaration.initializer.*;
    import declaration.*;
    import expression.*;
    import statements.*;
} 
@lexer::header {package grammar.generated;}

program: external_declaration* EOF;

primary_expression:  
  ID |
  constant |
  ID? '(' expression ')' 
  ;

postfix_expression:
  primary_expression postfix_expression_s*
  ;

argument_expression_list:
  assignment_expression (',' assignment_expression)*
  ;


postfix_expression_s: '[' expression ']'  |
  '.' identifier  |
  '->' identifier |
  '++'  |
  '--'
  ;

unary_expression  :
  ('(' type_name ')' unary_expression)=>  cast |
  postfix_expression |
  '++' unary_expression | //toto treba semanticky osetrit, ze ta unary expression nesmie byt cast
  '--' unary_expression | //toto treba semanticky osetrit, ze ta unary expression nesmie byt cast
  SIZEOF sizeof_arg | 
  unary_operator unary_expression ;
  
cast : '(' type_name')' unary_expression ;

//tu treba potom osetrit, ze ak type_name moze byt aj expression (napr. ID
//alebo ID [ expreesion ], tak treba vyskusat aj to
sizeof_arg : ('(' type_name ')') => '(' type_name ')' | unary_expression;

unary_operator :
  '&' |
  '*' |
  '+' |
  '-' |
  '~' |
  '!' ;
  
multiplicative_expression :
  unary_expression (multiplicative_operator unary_expression)*
  ;

multiplicative_operator: '*' | '/' | '%';

additive_expression : multiplicative_expression (additive_operator multiplicative_expression)*
  ;

additive_operator  :  '+' |
  '-'  
  ;

shift_expression  : additive_expression (shift_operator additive_expression)*
 ;

shift_operator : '<<' |
  '>>' 
  ;

relational_expression : shift_expression (relational_operator shift_expression)*
 ;

relational_operator  : 
  '<' |
  '>' |
  '<='|
  '>=' 
  ;

equality_expression : relational_expression (equality_operator relational_expression)*
  ;

equality_operator  : '==' | '!=';


and_expression  : equality_expression ('&' equality_expression)*
  ;

exclusive_or_expression : and_expression  ('^' and_expression)*
  ;

inclusive_or_expression : exclusive_or_expression ('|' exclusive_or_expression)*
  ;

logical_and_expression  : inclusive_or_expression ('&&' inclusive_or_expression)*
  ;

logical_or_expression : logical_and_expression  ('||' logical_and_expression)*
  ;

//TODO
conditional_expression returns [Expression ret] 
  : logical_or_expression ('?' expression ':'  conditional_expression)?
  ;

//tu treba semantickymi pravidlami skontrolovat, ze ak je tam assignment operator, 
//tak conditional expression musi byt unary - ale to nam asi vypadne z toho, ze nalavo od 
//priradovacieho operatora musi byt vec do ktorej sa da priradit
//TODO
assignment_expression returns [Expression ret] : conditional_expression (assignment_operator assignment_expression)? 
  ;

//TODO
expression returns [Expression ret] : assignment_expression (',' assignment_expression)*  
  ;

assignment_operator  : 
  '='  | 
  '*=' |
  '/=' |
  '%=' |
  '+=' |
  '-=' |
  '<<='|
  '>>='|
  '&=' |
  '^=' |
  '|=' 
  ;

identifier: ID;

constant: INT | FLOAT | STRING | CHAR;
         
//sadly this is required because otherwise we get an error from antlr
external_declaration: decl_specs ((declarator '{')=> declarator block | (declarator ('=' initializer)?)? ';');

block: '{' in_block* '}';

in_block:
  (decl_specs declarator) => declaration |
  (decl_specs ';') => declaration | 
  statement ; //toto je zle  
  
   
statement returns [Statement ret]:
  block |
  expression? ';' |
  if_stat | switch_stat | while_stat | for_stat | dowhile_stat | jmp_stat;


//** CONTROL STATEMENTS START **//

if_stat returns [IfStatement ret]
  : IF '(' cond=expression ')' ontrue=statement {$ret=new IfStatement(); $ret.cond=$cond.ret; $ret.ontrue=$ontrue.ret;} 
  ( (ELSE)=> ELSE onfalse=statement {$ret.onfalse=$onfalse.ret;}
    | ( ) // nothing
  );

switch_stat returns [SwitchStatement ret]
  : SWITCH {$ret=new SwitchStatement();} 
  '(' expr=expression {$ret.expr=$expr.ret;} ')' 
  '{' (cc=case_clause {$ret.cases.add($cc.ret);})* 
  (DEFAULT ':'  
  (st=statement {$ret.def.add($st.ret);})* )? '}';

case_clause returns [Case ret]
  : CASE cond=conditional_expression ':' {$ret=new Case(); $ret.cond=$cond.ret;}
  (st=statement {$ret.statements.add($st.ret);})*;

while_stat returns [WhileStatement ret]
  : WHILE '(' cond=expression ')' body=statement 
  {
    $ret=new WhileStatement();
    $ret.condition=$cond.ret; 
    $ret.body=$body.ret; 
  };

for_stat returns [ForStatement ret]
  : FOR {$ret=new ForStatement();} 
  '(' ((declaration) => decl=declaration {$ret.decl=$decl.ret;} | ) 
  (init=expression {$ret.init=init.ret;})? ';' 
  (cond=expression {$ret.cond=cond.ret;})? ';' 
  (after=expression {$ret.after=after.ret;})? ')' 
  body=statement {$ret.body=body.ret;};

dowhile_stat returns [DowhileStatement ret]
  : DO body=statement WHILE '(' cond=expression ')' ';'
  {
    $ret=new DowhileStatement(); 
    $ret.body=$body.ret; 
    $ret.condition=$cond.ret; 
  };

jmp_stat returns [Statement ret]
  : BREAK{$ret=new BreakStatement();} ';' 
  | CONTINUE{$ret=new ContinueStatement();} ';' 
  | RETURN{$ret=new ReturnStatement();} (e=expression {((ReturnStatement)$ret).exp=$e.ret;})? ';';
//** CONTROL STATEMENTS END **//

//** DECLARATION START **//

declaration returns [Declaration ret]: decl_specs (init_declarator (',' init_declarator)* )? ';' ; 

decl_specs returns [ArrayList<DeclarationSpecifier> ret]
@init {
  $ret = new ArrayList<DeclarationSpecifier>();
}
: (s=decl_spec_no_type {$ret.add($s.ret);})* 
    (i=ID {$ret.add(new IDDeclarationSpecifier($i.getText()));}
      | ts=type_specifier {$ret.add($ts.ret);}) 
    (ds=declaration_specifier {$ret.add($ds.ret);})*;

///**/
//
spec_qual_list returns [ArrayList<DeclarationSpecifier> ret]
@init{
  $ret = new ArrayList<DeclarationSpecifier>();
} 
:(tq=type_qualifier {$ret.add($tq.ret);})* 
  (ID {$ret.add(new IDDeclarationSpecifier($ID.getText()));} 
  | ts=type_specifier {$ret.add($ts.ret);}) (sq=spec_qual {$ret.add($sq.ret);})*;

spec_qual returns [DeclarationSpecifier ret]: 
    type_specifier {$ret = $type_specifier.ret;} 
  | type_qualifier {$ret = $type_qualifier.ret;} ;

declaration_specifier returns [DeclarationSpecifier ret]: 
  a=decl_spec_no_type {$ret = $a.ret;} | b=type_specifier {$ret = $b.ret;};

decl_spec_no_type returns [DeclarationSpecifier ret]: 
  a=storage_class_specifier {$ret = $a.ret;} | 
  b=type_qualifier {$ret=$b.ret;}| 
  c=function_specifier {$ret=$c.ret;};

storage_class_specifier returns [DeclarationSpecifier ret] 
  : STATIC {$ret = StorageClassSpecifier.STATIC;}
  | EXTERN {$ret = StorageClassSpecifier.EXTERN;}
  | REGISTER {$ret = StorageClassSpecifier.REGISTER;}
  | AUTO {$ret = StorageClassSpecifier.AUTO;} 
  | TYPEDEF {$ret = new TypedefDeclarationSpecifier();};

type_specifier returns [DeclarationSpecifier ret]
  : a=primitive_type {$ret=$a.ret;} 
  | b=struct_specifier {$ret=$b.ret;}
  | c=enum_specifier {$ret=$c.ret;};

struct_specifier returns [StructSpecifier ret]
  : STRUCT {$ret=new StructSpecifier(); } 
    (ID {$ret.tag=$ID.getText();})? 
    '{' (sd=struct_declaration {$ret.memberDecls.add($sd.ret);})* '}'
  | STRUCT ID {$ret=new StructSpecifier($ID.getText());}; //mozno tu odlisit tieto dve moznosti na urovni typu StructSpecifieru

struct_declaration returns [Declaration ret]
@init {
  $ret = new Declaration();
}
  : sq=spec_qual_list d1=declarator {$ret.declSpecs=$sq.ret; $ret.addDeclarator($d1.ret);} 
    (',' d2=declarator {$ret.addDeclarator($d2.ret);})* ';';

enum_specifier returns [EnumSpecifier ret]
  : ENUM {$ret = new EnumSpecifier();} 
    (ID {$ret.tag=$ID.getText();})? 
    '{' e1=enumerator {$ret.enumerators.add($e1.ret);} (',' e2=enumerator {$ret.enumerators.add($e2.ret);})* ','? '}' 
  | ENUM ID {$ret = new EnumSpecifier($ID.getText());};

enumerator returns [Enumerator ret]
  : ID {$ret = new Enumerator($ID.getText());} ('=' ce=conditional_expression {$ret.expression=$ce.ret;})?;

type_qualifier returns [TypeQualifier ret]
  : RESTRICT {$ret=TypeQualifier.RESTRICT;} 
  | VOLATILE {$ret=TypeQualifier.VOLATILE;} 
  | CONST {$ret=TypeQualifier.CONST;};

function_specifier returns [DeclarationSpecifier ret]: INLINE {$ret = new InlineDeclarationSpecifier();};

init_declarator returns [InitDeclarator ret]
: d=declarator {$ret = new InitDeclarator($d.ret);} 
    ( '=' i=initializer {$ret.initializer = $i.ret;})?;
 
declarator returns [Declarator ret]
@init {
  PointerDeclarator lastPtr = null;
  $ret=null;
}
  : (p=pointer 
      {
        //zabalime direct_declarator do pointerov
        if($ret==null) {
          $ret=lastPtr=new PointerDeclarator($p.ret);
        } 
        else {
          PointerDeclarator tmp = new PointerDeclarator($p.ret);
          lastPtr.declarator = tmp;
          lastPtr=tmp;
        }
      })* 
     dd=direct_declarator {
        if(lastPtr==null)
          $ret=$dd.ret;
        else
          lastPtr.declarator = $dd.ret;
     };

pointer returns [ArrayList<TypeQualifier> ret]
@init {
  $ret = new ArrayList<TypeQualifier>();
}
  : '*' (t=type_qualifier {$ret.add($t.ret);})*;

direct_declarator returns [Declarator ret]
  : s=simple_declarator {$ret = $s.ret;} 
    (d=declarator_suffix[$ret] {$ret = $d.ret;})*;

simple_declarator returns [Declarator ret]
  : ID {$ret = new IDDeclarator($ID.getText());} 
  | '(' d=declarator ')' {$ret=$d.ret;};

//TODO
declarator_suffix [Declarator decl] returns [Declarator ret]
  : param_declarator_suffix |
  '(' parameter_list? ')';

parameter_list: parameter_declaration (',' parameter_declaration )* (',' '...')? | '...';

parameter_declaration: decl_specs param_declarator?;

param_declarator: pointer* direct_param_declarator;

direct_param_declarator: param_declarator_suffix+ | simple_param_declarator param_declarator_suffix*; 

simple_param_declarator: ID | '(' param_declarator ')';

param_declarator_suffix: 
  '[' ']' |
  '[' STATIC? type_qualifier* assignment_expression ']' |
  '[' type_qualifier+ STATIC assignment_expression ']' |
  '[' type_qualifier* '*' ']' ;

initializer returns [Initializer ret]
  : e=assignment_expression {$ret = new ExpressionInitializer($e.ret);} 
  | '{' il=initializer_list ','? '}' {$ret = $il.ret;};

initializer_list returns [CompoundInitializer ret]
@init{
  $ret = new CompoundInitializer();
}
  : i1=initialization {$ret.initializers.add($i1.ret);} 
      (',' i2=initialization {$ret.initializers.add($i2.ret);})*;

initialization returns [DesignatedInitializer ret]
@init {
  $ret=new DesignatedInitializer();
}
  : (d=designator '=' {$ret.designator = $d.ret;})? 
      i=initializer {$ret.initializer=$i.ret;};

designator returns [Designator ret]
  : '.' ID {$ret = new Designator($ID.getText());}  
  | '[' e=assignment_expression ']' {$ret=new Designator($e.ret);} ;

//to ci je to naozaj iba typ osetrime semantickymi pravidlami
type_name: parameter_declaration;

//** DECLARATION END **// 

//** PRIMITIVE TYPES START **//

primitive_type returns [DeclarationSpecifier ret]
  : LONG {$ret = PrimitiveTypeSpecifier.LONG;} 
  | INT_T {$ret = PrimitiveTypeSpecifier.INT;}
  | DOUBLE {$ret = PrimitiveTypeSpecifier.DOUBLE;}
  | FLOAT_T {$ret = PrimitiveTypeSpecifier.FLOAT;}
  | VOID {$ret = PrimitiveTypeSpecifier.VOID;}
  | CHAR_T {$ret = PrimitiveTypeSpecifier.CHAR;}
  | SHORT {$ret = PrimitiveTypeSpecifier.SHORT;}
  | SIGNED {$ret = PrimitiveTypeSpecifier.SIGNED;}
  | UNSIGNED {$ret = PrimitiveTypeSpecifier.UNSIGNED;};

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

