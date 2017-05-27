package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by erxyi on 27.05.2017.
 */
public class KnowledgeBase {
    HashSet<Clause> clauses;

    public KnowledgeBase(HashSet<Clause> clauses) {
        this.clauses = clauses;
    }

    public KnowledgeBase() {
        clauses = new HashSet<>();
    }

    public void AddClause(Clause c)
    {
        if(clauses.contains(c))
            return;

        clauses.add(c);
    }

    public void substitute()
    {
        HashSet<Clause> newClauses = new HashSet<>();
        boolean modified = true;
        while(modified) {
            modified = false;
            for (Clause c :
                    clauses) {
                for (int i = 0; i < c.literals.size(); i++) {
                    Literal parsedLiteral = c.literals.get(i);
                    for (int j = 0; j < parsedLiteral.argumentValues.length; j++) {
                        if (parsedLiteral.argumentValues[j] == null)
                            continue;
                        ArgumentType parsedArgumentType = parsedLiteral.type.arguments.get(j);

                        for (Clause d : clauses) {
                            for (Literal l : d.literals) {
                                if (l.equals(parsedLiteral))
                                    continue;

                                boolean canBeSubstituted = l.type.arguments.contains(parsedArgumentType);
                                if (!canBeSubstituted)
                                    continue;

                                int position = l.type.arguments.indexOf(parsedArgumentType);
                                if (l.argumentValues[position] != null)
                                    continue;

                                Clause newD = new Clause(d);
                                newD.literals.get(newD.literals.indexOf(l)).argumentValues[position] = parsedLiteral.argumentValues[j];
                                if(!newClauses.contains(newD))
                                {
                                    newClauses.add(newD);
                                    modified = true;
                                }


                            }
                        }
                    }
                }
            }
            clauses.addAll(newClauses);
            //newClauses.clear();
        }
    }
}
