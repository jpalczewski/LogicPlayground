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
 * Created by erxyi on 01.06.2017.
 */
class ShortestFirstStrategyTest {
    @Test
    void solve() {
        ArgumentDictionary ad = new ArgumentDictionary();
        ArgumentType x = ad.createNewArgument("x");
        LiteralType a = new LiteralType("A");
        a.addArgument(x);
        LiteralType c = new LiteralType("C");
        c.addArgument(x);
        Literal l1 = new Literal(a);
        Literal l2 = new Literal(a);
        Literal l3 = new Literal(c);
        Literal l4 = new Literal(c);

        l1.setArgumentValue(x, "Mn");
        l1.negate();
        l4.negate();

        Clause c1 = new Clause(l1);
        Clause c2 = new Clause(l2, l3);
        Clause c3 = new Clause(l4);
        KnowledgeBase kb = new KnowledgeBase(c1,c2,c3);
        kb.substitute();
        Assertions.assertEquals(kb.isContradictory(), false);
        //BreadthFirstStrategy bfs = new BreadthFirstStrategy(kb);
        ShortestFirstStrategy sfs = new ShortestFirstStrategy(kb);
        sfs.solve();
        Assertions.assertEquals(sfs.isLastStepContradictory(), true);
    }

    @Test
    void multipleSteps() {
        //Av~B, BvC, C~, ~A
        ArgumentDictionary ad = new ArgumentDictionary();
        ArgumentType x = ad.createNewArgument("x");
        LiteralType a = new LiteralType("A");
        LiteralType b = new LiteralType("B");
        LiteralType c = new LiteralType("C");
        a.addArgument(x);
        b.addArgument(x);
        c.addArgument(x);
        Literal la1 = new Literal(a);
        Literal la2 = new Literal(a);
        Literal lb1 = new Literal(b);
        Literal lb2 = new Literal(b);
        Literal lc1 = new Literal(c);
        Literal lc2 = new Literal(c);
        lb2.negate();
        lc2.negate();
        la2.negate();
        Clause c1 = new Clause(la1,lb2);
        Clause c2 = new Clause(lb1, lc1);
        Clause c3 = new Clause(lc2);
        Clause c4 = new Clause(la2);
        KnowledgeBase kb = new KnowledgeBase(c1,c2,c3,c4);
        Assertions.assertEquals(kb.isContradictory(), false);
        ShortestFirstStrategy sfs = new ShortestFirstStrategy(kb);
        sfs.solve();
        Assertions.assertEquals(true, sfs.isLastStepContradictory());
    }
}