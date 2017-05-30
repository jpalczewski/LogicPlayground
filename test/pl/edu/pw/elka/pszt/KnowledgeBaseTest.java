package pl.edu.pw.elka.pszt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pw.elka.pszt.Arguments.ArgumentDictionary;
import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

/**
 * Created by erxyi on 27.05.2017.
 */
class KnowledgeBaseTest {
    ArgumentDictionary ad;
    ArgumentType a,b,c,d;
    LiteralType f1, f2;
    Literal l1,l2;
    Clause c1, c2;
    @BeforeEach
    void setUp() {
        ad = new ArgumentDictionary();
        a = ad.createNewArgument("a");
        b = ad.createNewArgument("b");
        c = ad.createNewArgument("c");
        d = ad.createNewArgument("d");
        f1 = new LiteralType("F");
        f1.addArgument(a);

        f2 = new LiteralType("G");
        f2.addArgument(a);

        l1 = new Literal(f1);
        l2 = new Literal(f2);

        c1 = new Clause();
        c2 = new Clause();

        c1.addLiteral(l1);
        c2.addLiteral(l2);
    }

    @Test
    void substitute() {
        KnowledgeBase kb = new KnowledgeBase();
        l1.setArgumentValue(a,"TEST");
        kb.AddClause(c1);
        kb.AddClause(c2);
        kb.substitute();
        System.out.println(kb.toString());
    }

    @Test
    void HandbookExample()
    {
        ArgumentType x = ad.createNewArgument("x");
        LiteralType la = new LiteralType("A");
        LiteralType lb = new LiteralType("B");
        LiteralType lc = new LiteralType("C");


        la.addArgument(x);
        lb.addArgument(x);
        lc.addArgument(x);

        Literal result1 = new Literal(la);
        Literal result2 = new Literal(lb);
        result1.setArgumentValue(x, "Mn");
        result2.setArgumentValue(x, "Mn");
        Clause resultClause = new Clause();
        resultClause.addLiteral(result1);
        resultClause.addLiteral(result2);

        Literal r1 = new Literal(la);
        Literal r2 = new Literal(lb);
        Literal r3 = new Literal(la);
        Literal r4 = new Literal(lc);
        r3.setArgumentValue(x, "Mn");
        r4.setArgumentValue(x, "Mn");

        Clause c1 = new Clause();
        Clause c2 = new Clause();

        c1.addLiteral(r1);
        c1.addLiteral(r2);
        c2.addLiteral(r3);
        c2.addLiteral(r4);

        KnowledgeBase kb = new KnowledgeBase();
        kb.AddClause(c1);
        kb.AddClause(c2);
        kb.substitute();

        Assertions.assertEquals(true, kb.clauses.contains(resultClause));
    }


}