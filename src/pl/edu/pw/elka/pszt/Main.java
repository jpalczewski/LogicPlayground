package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.Arguments.ArgumentDictionary;
import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

public class Main {

    public static void main(String[] args) {
        ArgumentDictionary ad = new ArgumentDictionary();

        ArgumentType firstType = ad.createNewArgument("x");
        ArgumentType secondType = ad.createNewArgument("y");

        LiteralType firstLiteralType = new LiteralType("F");
        firstLiteralType.addArgument(firstType);
        firstLiteralType.addArgument(secondType);

        System.out.println(firstLiteralType);

        Literal p = new Literal(firstLiteralType);
        p.setArgumentValue(firstType, "RAZ");
        System.out.println(p);

        Clause xyz = new Clause();
        xyz.addLiteral(p);
        xyz.addLiteral(p);
        System.out.println(xyz);
    }
}
