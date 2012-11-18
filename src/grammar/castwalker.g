tree grammar castwalker;

options {
  language = Java;
  tokenVocab = cgrammar;
  ASTLabelType = CommonTree;
}

@header {
    package grammar.generated;
} 

block: ^(BLOCK assignment*);

assignment: ^('=' ID INT);
