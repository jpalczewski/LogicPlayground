package pl.edu.pw.elka.pszt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pw.elka.pszt.Arguments.ArgumentDictionary;
import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by erxyi on 27.05.2017.
 */
class LiteralTest {
    ArgumentDictionary ad;
    ArgumentType a,b,c,d;
    LiteralType f1;
    Literal l1,l2;
    @BeforeEach
    void setUp() {
        ad = new ArgumentDictionary();
        a = ad.createNewArgument("a");
        b = ad.createNewArgument("b");
        c = ad.createNewArgument("c");
        d = ad.createNewArgument("d");
        f1 = new LiteralType("F");
        f1.addArgument(a);

        l1 = new Literal(f1);
        l2 = new Literal(f1);
    }

    @Test
    void equals() {

        l1.setArgumentValue(a, "test");
        l2.setArgumentValue(a, "test");

        Assertions.assertEquals(l1,l2);
    }

    @Test
    void isUnifiable()
    {
        Assertions.assertEquals(true, l1.isUnifiable(l2));
        l1.setArgumentValue(a, "test");
        Assertions.assertEquals(true, l1.isUnifiable(l2));
        l2.setArgumentValue(a, "test");
        Assertions.assertEquals(true, l1.isUnifiable(l2));
        l1.setArgumentValue(a, "fail");
        Assertions.assertEquals(false, l1.isUnifiable(l2));
    }

    @Test
    void Unify()
    {
        l1.setArgumentValue(a, "test");
        Literal l3 = l1.Unify(l2);
        Assertions.assertEquals(l1, l3);
    }

}