package pl.edu.pw.elka.pszt.Strategies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.elka.pszt.Arguments.ArgumentDictionary;
import pl.edu.pw.elka.pszt.Arguments.ArgumentType;
import pl.edu.pw.elka.pszt.Clause;
import pl.edu.pw.elka.pszt.KnowledgeBase;
import pl.edu.pw.elka.pszt.Literal;
import pl.edu.pw.elka.pszt.LiteralType;

/**
 * Created by erxyi on 11.06.2017.
 */
public class StrategyTest {

    @Test
    void bookExampleViaSFS() {
        ArgumentDictionary ad = new ArgumentDictionary();
        ArgumentType x = ad.createNewArgument("x");
        LiteralType c = new LiteralType("C", x);
        LiteralType w = new LiteralType("W", x);
        LiteralType o = new LiteralType("O", x);
        LiteralType r = new LiteralType("R", x);
        Literal l11 = new Literal(c);
        Literal l12 = new Literal(w);
        l11.negate();

        Literal l21 = new Literal(c);
        Literal l22 = new Literal(r);
        l21.negate();

        Literal l31 = new Literal(c);
        l31.setArgumentValue(x, "A");

        Literal l41 = new Literal(o);
        l41.setArgumentValue(x, "A");

        Literal l51 = new Literal(o);
        Literal l52 = new Literal(r);
        l51.negate();
        l52.negate();

        Clause c1 = new Clause(l11, l12),
                c2 = new Clause(l21, l22),
                c3 = new Clause(l31),
                c4 = new Clause(l41),
                c5 = new Clause(l51, l52);

        KnowledgeBase kb = new KnowledgeBase(c1, c2, c3, c4, c5);

        //  LinearStrategy ls = new LinearStrategy(kb);
        //ls.setHipotesis(c5);
        ShortestFirstStrategy sfs = new ShortestFirstStrategy(kb);
        sfs.solve();
        Assertions.assertEquals(true, sfs.isLastStepContradictory());
    }
}
