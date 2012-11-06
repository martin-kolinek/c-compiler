grammar cgrammar;

options {
  language = Java;
  output = AST;
}

@header {
    package grammar.generated;
} 
@lexer::header {package grammar.generated;}

rule: 'a';

