package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.Arguments.ArgumentDictionary;
import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

public class Main {

    public static void main(String[] args) {
        ArgumentDictionary ad = new ArgumentDictionary();

        ArgumentType firstType = ad.createNewArgument("x");
        ArgumentType secondType = ad.createNewArgument("y");

        PredicateType firstPredicateType = new PredicateType("F");
        firstPredicateType.addArgument(firstType);
        firstPredicateType.addArgument(secondType);

        System.out.println(firstPredicateType);

        Predicate p = new Predicate(firstPredicateType);
        p.setArgumentValue(firstType, "RAZ");
        System.out.println(p);
    }
}
