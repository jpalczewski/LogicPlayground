package pl.edu.pw.elka.pszt.Strategies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.elka.pszt.Arguments.ArgumentDictionary;
import pl.edu.pw.elka.pszt.Arguments.ArgumentType;
import pl.edu.pw.elka.pszt.Clause;
import pl.edu.pw.elka.pszt.KnowledgeBase;
import pl.edu.pw.elka.pszt.Literal;
import pl.edu.pw.elka.pszt.LiteralType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by erxyi on 01.06.2017.
 */
class BreadthFirstStrategyTest {
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
        Assertions.assertEquals(kb.isContradictory(), false);
        BreadthFirstStrategy bfs = new BreadthFirstStrategy(kb);
        bfs.solve();
        Assertions.assertEquals(bfs.steps.get(1).isContradictory(), true);
    }

}