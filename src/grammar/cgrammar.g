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
    import expression.binop.*;
    import expression.unop.*;
    import expression.binop.*;
    import expression.constant.*;
    import statements.*;
    import toplevel.*;
} 
@lexer::header {package grammar.generated;}

program returns [Program ret]
@init {
$ret = new Program();
}
  : (ed=external_declaration {$ret.declarations.add($ed.ret);})* EOF;

primary_expression returns [Expression ret]
@init{
  $ret=null;
}
  : ID {$ret = new IDExpression($ID.getText());} |
  con=constant {$ret = $con.ret;} | 
  (ID {$ret = new FunctionCallExpression($ID.getText());})? 
    '(' exp=expression ')' {
      if ($ret != null){
        ((FunctionCallExpression)$ret).addExp($exp.ret);
      }
      else {
        $ret=$exp.ret;
      }
    } 
  ;

postfix_expression returns [Expression ret]
  : exp=primary_expression {$ret = $exp.ret;} 
    (postexp=postfix_expression_s[$ret] {$ret = $postexp.ret;} )* 
  ;

postfix_expression_s [Expression e] returns [Expression ret]
  : '[' e2=expression ']' {$ret=new IndexingExpression($e, $e2.ret);} |
  '.' id=ID {$ret=new MemberAccessExpression($e, $id.getText());} | 
  '->' id=ID {$ret=new MemberDereferenceExpression($e, $id.getText());} |
  '++' {$ret=new UnaryExpression($e, UnaryOperator.POST_INC);} |
  '--' {$ret=new UnaryExpression($e, UnaryOperator.POST_DEC);} 
  ;

unary_expression returns [Expression ret] 
  : ('(' type_name ')' unary_expression)=>  c=cast {$ret=$c.ret;} |
  pexp=postfix_expression {$ret=$pexp.ret;}|
  '++' exp=unary_expression {$ret=new UnaryExpression(); ((UnaryExpression)$ret).exp=$exp.ret; ((UnaryExpression)$ret).op=UnaryOperator.PRE_INC;} | //toto treba semanticky osetrit, ze ta unary expression nesmie byt cast
  '--' exp=unary_expression {$ret=new UnaryExpression(); ((UnaryExpression)$ret).exp=$exp.ret; ((UnaryExpression)$ret).op=UnaryOperator.PRE_DEC;} | //toto treba semanticky osetrit, ze ta unary expression nesmie byt cast
  SIZEOF soexp=sizeof_arg {$ret=$soexp.ret;} | 
  op=unary_operator exp=unary_expression {$ret=new UnaryExpression();  ((UnaryExpression)$ret).exp=$exp.ret; ((UnaryExpression)$ret).op=$op.ret;} ;
  
cast returns [CastExpression ret] 
  : '(' td=type_name')' exp=unary_expression {$ret=new CastExpression(); $ret.typedecl=$td.ret; $ret.exp=$exp.ret;} ;

//tu treba potom osetrit, ze ak type_name moze byt aj expression (napr. ID
//alebo ID [ expreesion ], tak treba vyskusat aj to
sizeof_arg returns [Expression ret]
  : ('(' type_name ')') => '(' td=type_name ')' {$ret=new SizeofType(); ((SizeofType)$ret).typedecl=$td.ret;} |
   exp=unary_expression {$ret=new SizeofExpression(); ((SizeofExpression)$ret).exp=$exp.ret;};

unary_operator returns [UnaryOperator ret] :
  '&' {$ret=UnaryOperator.ADDR;} |
  '*' {$ret=UnaryOperator.PTR;}|
  '+' {$ret=UnaryOperator.PLUS;}|
  '-' {$ret=UnaryOperator.MINUS;}|
  '~' {$ret=UnaryOperator.COMP;}|
  '!' {$ret=UnaryOperator.NOT;}
  ;
  
multiplicative_expression returns [Expression ret] :
  u=unary_expression {$ret = $u.ret;} 
    (o=multiplicative_operator u2=unary_expression {
      $ret = new BinaryExpression($ret, $o.ret, $u2.ret);
    })*
  ;

multiplicative_operator returns [BinaryOperator ret]:
 '*' {$ret=BinaryOperator.MULT;}|
 '/' {$ret=BinaryOperator.DIV;}|
 '%' {$ret=BinaryOperator.MOD;}
 ;

additive_expression returns [Expression ret]:
 m=multiplicative_expression {$ret=$m.ret;} 
 (o=additive_operator m2=multiplicative_expression{
  $ret=new BinaryExpression($ret,$o.ret,$m2.ret);
 })*
  ;

additive_operator returns [BinaryOperator ret] :  
  '+' {$ret=BinaryOperator.PLUS;}|
  '-' {$ret=BinaryOperator.MINUS;} 
  ;

shift_expression  returns [Expression ret]:
  a=additive_expression {$ret=$a.ret;}
   (o=shift_operator a2=additive_expression{
    $ret=new BinaryExpression($ret,$o.ret,$a2.ret);
   })*
  ;

shift_operator returns [BinaryOperator ret]:
  '<<' {$ret=BinaryOperator.BSLEFT;}|
  '>>' {$ret=BinaryOperator.BSRIGHT;}
  ;

relational_expression returns [Expression ret]:
 s=shift_expression {$ret=$s.ret;} 
  (o=relational_operator s2=shift_expression{
  $ret=new BinaryExpression($ret,$o.ret,$s2.ret);
  })*
 ;

relational_operator returns [BinaryOperator ret] : 
  '<' {$ret=BinaryOperator.LT;}|
  '>' {$ret=BinaryOperator.GT;}|
  '<='{$ret=BinaryOperator.LET;}|
  '>='{$ret=BinaryOperator.GET;} 
  ;

equality_expression returns [Expression ret]:
 r=relational_expression {$ret=$r.ret;} 
 (o=equality_operator r2=relational_expression{
  $ret=new BinaryExpression($ret,$o.ret,$r2.ret);
 })*
  ;

equality_operator returns [BinaryOperator ret] : 
  '==' {$ret=BinaryOperator.EQ;}|
  '!=' {$ret=BinaryOperator.NEQ;}
  ;


and_expression  returns [Expression ret]:
 e=equality_expression {$ret=$e.ret;}
 ('&' 
 e2=equality_expression{
  $ret=new BinaryExpression($ret,BinaryOperator.BAND,$e2.ret);
 })*
  ;

exclusive_or_expression returns [Expression ret]:
 a=and_expression  {$ret=$a.ret;}
 ('^'
 a2=and_expression{
    $ret=new BinaryExpression($ret,BinaryOperator.BXOR,$a2.ret);
  })*
  ;

inclusive_or_expression returns [Expression ret]:
 e=exclusive_or_expression 
 ('|' 
 e2=exclusive_or_expression{
  $ret=new BinaryExpression($ret,BinaryOperator.BOR,$e2.ret);
 })*
  ;

logical_and_expression  returns [Expression ret]:
 e=inclusive_or_expression 
 ('&&' 
 e2=inclusive_or_expression{
  $ret=new BinaryExpression($ret,BinaryOperator.AND,$e2.ret);
 })*
  ;

logical_or_expression returns [Expression ret]:
 e=logical_and_expression
 ('||' 
 e2=logical_and_expression{
  $ret=new BinaryExpression($ret,BinaryOperator.OR,$e2.ret);
 })*
  ;

//TODO
conditional_expression returns [Expression ret] :
 cond=logical_or_expression  
 ('?' ontrue=expression ':'  onfalse=conditional_expression)?
 {$ret=new TernaryExpression($cond.ret, $ontrue.ret, $onfalse.ret);}
  ;

//tu treba semantickymi pravidlami skontrolovat, ze ak je tam assignment operator, 
//tak conditional expression musi byt unary - ale to nam asi vypadne z toho, ze nalavo od 
//priradovacieho operatora musi byt vec do ktorej sa da priradit
//TODO
assignment_expression returns [Expression ret] 
  : e=conditional_expression {$ret=$e.ret;}
    (o=assignment_operator e2=assignment_expression{
      $ret = new BinaryExpression($ret, $o.ret, $e2.ret); 
    })? 
  ;

//TODO
expression returns [Expression ret] 
  : assignment_expression (',' assignment_expression)*  
  ;

//TODO
assignment_operator returns [BinaryOperator ret] : 
  '='  {$ret=BinaryOperator.ASSIG;}| 
  '*='  {$ret=BinaryOperator.AMULT;}|
  '/='  {$ret=BinaryOperator.ADIV;}|
  '%='  {$ret=BinaryOperator.AMOD;}|
  '+='  {$ret=BinaryOperator.APLUS;}|
  '-='  {$ret=BinaryOperator.AMINUS;}|
  '<<=' {$ret=BinaryOperator.ABSLEFT;}|
  '>>=' {$ret=BinaryOperator.ABSRIGHT;}|
  '&='  {$ret=BinaryOperator.ABAND;}|
  '^='  {$ret=BinaryOperator.ABXOR;}|
  '|='  {$ret=BinaryOperator.ABOR;}
  ;

constant returns [Expression ret]
  : INT {$ret = new IntConstantExpression(); ((IntConstantExpression)$ret).value = Integer.parseInt($INT.getText());} |
   FLOAT {$ret = new FloatConstantExpression(); ((FloatConstantExpression)$ret).value = Float.parseFloat($FLOAT.getText());}| 
   STRING {$ret = new StringConstantExpression(); ((StringConstantExpression)$ret).value = $STRING.getText();}| 
   CHAR {$ret = new CharConstantExpression(); ((CharConstantExpression)$ret).value = $CHAR.getText().charAt(0);};
         
//sadly this is required because otherwise we get an error from antlr
external_declaration returns [InBlock ret]
: ds=decl_specs 
    ((declarator '{')=> d=declarator b=block {
          $ret=new FunctionDefinition();
          ((FunctionDefinition)$ret).declaration = new Declaration();
          ((FunctionDefinition)$ret).declaration.declSpecs=$ds.ret;
          ((FunctionDefinition)$ret).declaration.declarators.add(new InitDeclarator($d.ret));
          ((FunctionDefinition)$ret).body=$b.ret; 
        } 
      | {$ret = new Declaration($ds.ret);} 
        (i1=init_declarator {((Declaration)$ret).declarators.add($i1.ret);} 
          ( ',' i2=init_declarator {((Declaration)$ret).declarators.add($i2.ret);})*)? ';');

block returns [BlockStatement ret]
: '{' {$ret=new BlockStatement();} (ib=in_block {$ret.inBlock.add($ib.ret);})* '}';

//tu treba osetrit ak deklaracia moze byt aj statement (narp. ID alebo ID[expression])
in_block returns [InBlock ret]: 
  (decl_specs declarator) => decl=declaration {$ret=$decl.ret;} |
  (decl_specs ';') => decl=declaration {$ret=$decl.ret;} | 
  stat=statement {$ret=$stat.ret;} ; 
  
   
statement returns [Statement ret]:
  bl=block {$ret=$bl.ret;} |
  {$ret=new OneexpressionStatement();} (exp=expression {((OneexpressionStatement)$ret).exp=$exp.ret;})? ';' |
  ifs=if_stat {$ret=$ifs.ret;} | switchs=switch_stat {$ret=$switchs.ret;} | whiles=while_stat {$ret=$whiles.ret;} |
  fors=for_stat {$ret=$fors.ret;} | dws=dowhile_stat {$ret=$dws.ret;} | jmps=jmp_stat {$ret=$jmps.ret;};


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

declaration returns [Declaration ret]
  : {$ret = new Declaration();} 
    ds=decl_specs {$ret.declSpecs = $ds.ret;} 
    (i1=init_declarator {$ret.declarators.add($i1.ret);} 
      (',' i2=init_declarator {$ret.declarators.add($i2.ret);})* )? ';' ; 

decl_specs returns [ArrayList<DeclarationSpecifier> ret]
@init {
  $ret = new ArrayList<DeclarationSpecifier>();
}
: (s=decl_spec_no_type {$ret.add($s.ret);})* 
    (i=ID {$ret.add(new IDDeclarationSpecifier($i.getText()));}
      | ts=type_specifier {$ret.add($ts.ret);}) 
    (ds=declaration_specifier {$ret.add($ds.ret);})*;

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
  PointerUtil u = new PointerUtil();
}
  : (p=pointer { u.addPointer($p.ret);})* 
     dd=direct_declarator { $ret = u.getDecl($dd.ret);};

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

declarator_suffix [Declarator decl] returns [Declarator ret]
  : pd=param_declarator_suffix[$decl] {$ret=$pd.ret;} 
  | '(' pl=parameter_list[$decl] {$ret=$pl.ret;} ')'
  | '(' ')' {$ret = new FunctionDeclarator($decl);};

parameter_list [Declarator decl] returns [Declarator ret]
  : pd1=parameter_declaration {$ret=new FunctionDeclarator($decl); ((FunctionDeclarator)$ret).parameters.add($pd1.ret);} 
      (',' pd2=parameter_declaration {((FunctionDeclarator)$ret).parameters.add($pd2.ret);})* 
      (',' '...' {((FunctionDeclarator)$ret).variadic=true;})? 
  | '...';

parameter_declaration returns [Declaration ret]
  : {$ret = new Declaration();} 
      ds=decl_specs {$ret.declSpecs=$ds.ret;} 
      (pd=param_declarator {$ret.declarators.add(new InitDeclarator($pd.ret));})?;

param_declarator returns [Declarator ret]
@init {
  PointerUtil u = new PointerUtil();
}
  : (p=pointer {u.addPointer($p.ret);})* 
    d=direct_param_declarator {$ret=u.getDecl($d.ret);};

direct_param_declarator returns [Declarator ret]
  : {$ret = null;} (d=param_declarator_suffix[$ret] {$ret = $d.ret;})+ 
  | s=simple_param_declarator {$ret = $s.ret;} (d=param_declarator_suffix[$ret] {$ret=$d.ret;})*; 

simple_param_declarator returns [Declarator ret]
  : ID {$ret = new IDDeclarator($ID.getText());}
  | '(' p=param_declarator ')' {$ret = $p.ret;};

param_declarator_suffix [Declarator decl] returns [Declarator ret] 
  : '[' ']' {$ret = new ArrayDeclarator($decl, false, false);} 
  | {$ret = new ArrayDeclarator($decl, false, false);} 
      '[' (STATIC {((ArrayDeclarator)$ret).stat=true;})? 
          (tq=type_qualifier {((ArrayDeclarator)$ret).qualifiers.add($tq.ret);})* 
          e=assignment_expression {((ArrayDeclarator)$ret).expression=$e.ret;} ']' 
  | {$ret = new ArrayDeclarator($decl, false, true);}
      '[' (tq=type_qualifier {((ArrayDeclarator)$ret).qualifiers.add($tq.ret);})+ 
          STATIC ae=assignment_expression {((ArrayDeclarator)$ret).expression=$ae.ret;} ']' 
  | {$ret = new ArrayDeclarator($decl, true, false);} 
      '[' (tq=type_qualifier {((ArrayDeclarator)$ret).qualifiers.add($tq.ret);})* '*' ']' ;

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
type_name returns [Declaration ret]: decl=parameter_declaration {$ret=$decl.ret;};

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

