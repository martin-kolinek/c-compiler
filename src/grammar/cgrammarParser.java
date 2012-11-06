// $ANTLR 3.4 /home/k/kolinek1/workspace/Compiler/src/grammar/cgrammar.g 2012-11-06 10:51:49

    package grammar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class cgrammarParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "'a'"
    };

    public static final int EOF=-1;
    public static final int T__4=4;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public cgrammarParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public cgrammarParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return cgrammarParser.tokenNames; }
    public String getGrammarFileName() { return "/home/k/kolinek1/workspace/Compiler/src/grammar/cgrammar.g"; }


    public static class rule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rule"
    // /home/k/kolinek1/workspace/Compiler/src/grammar/cgrammar.g:12:1: rule : 'a' ;
    public final cgrammarParser.rule_return rule() throws RecognitionException {
        cgrammarParser.rule_return retval = new cgrammarParser.rule_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token char_literal1=null;

        Object char_literal1_tree=null;

        try {
            // /home/k/kolinek1/workspace/Compiler/src/grammar/cgrammar.g:12:5: ( 'a' )
            // /home/k/kolinek1/workspace/Compiler/src/grammar/cgrammar.g:12:7: 'a'
            {
            root_0 = (Object)adaptor.nil();


            char_literal1=(Token)match(input,4,FOLLOW_4_in_rule45); 
            char_literal1_tree = 
            (Object)adaptor.create(char_literal1)
            ;
            adaptor.addChild(root_0, char_literal1_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "rule"

    // Delegated rules


 

    public static final BitSet FOLLOW_4_in_rule45 = new BitSet(new long[]{0x0000000000000002L});

}