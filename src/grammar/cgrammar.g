grammar cgrammar;

options {
  language = Java;
  output = AST;
}

@header {
    package grammar;
}
@lexer::header {package grammar;}
rule: 'a';
