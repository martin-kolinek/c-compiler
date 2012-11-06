// $ANTLR 3.4 cgrammar__.g 2012-11-06 10:51:50
package grammar;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class cgrammarLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__4=4;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public cgrammarLexer() {} 
    public cgrammarLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public cgrammarLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "cgrammar__.g"; }

    // $ANTLR start "T__4"
    public final void mT__4() throws RecognitionException {
        try {
            int _type = T__4;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // cgrammar__.g:9:6: ( 'a' )
            // cgrammar__.g:9:8: 'a'
            {
            match('a'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__4"

    public void mTokens() throws RecognitionException {
        // cgrammar__.g:1:8: ( T__4 )
        // cgrammar__.g:1:10: T__4
        {
        mT__4(); 


        }


    }


 

}