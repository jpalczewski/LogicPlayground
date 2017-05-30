package pl.edu.pw.elka.pszt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pw.elka.pszt.Arguments.ArgumentDictionary;
import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by erxyi on 30.05.2017.
 */
class ClauseTest {
    ArgumentDictionary ad;
    ArgumentType x,y;
    LiteralType f1, f2;
    Literal l1,l2,r1,r2;
    Clause c1,c2;
    @BeforeEach
    void setUp() {
        ad = new ArgumentDictionary();
        x = ad.createNewArgument("x");
        y = ad.createNewArgument("y");
        f1 = new LiteralType("F1");
        f1.addArgument(x);
        f1.addArgument(y);
        f2 = new LiteralType("F2");
        f2.addArgument(y);
        l1 = new Literal(f1);
        l2 = new Literal(f2);
        r1 = new Literal(f1);
        r2 = new Literal(f2);
        l1.negate();
        l1.setArgumentValue(x, "A");
        r1.setArgumentValue(y,"A");
        r2.negate();
        c1 = new Clause();
        c2 = new Clause();
        c1.addLiteral(l1);
        c1.addLiteral(l2);
        c2.addLiteral(r1);
        c2.addLiteral(r2);
    }

    @Test
    void resolveEmpty() {
        Clause result = c1.resolve(c2);
        Assertions.assertEquals(true, result.isEmpty());
    }

    @Test
    void resolveNonEmpty() {
        r2.negate();
        Clause result = c1.resolve(c2);
        Assertions.assertEquals(false, result.isEmpty());
    }

}