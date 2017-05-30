package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

import java.util.ArrayList;
import java.util.HashSet;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KnowledgeBase that = (KnowledgeBase) o;

        return clauses.equals(that.clauses);
    }

    @Override
    public int hashCode() {
        return clauses.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(clauses.isEmpty())
            sb.append("(empty KB)");
        else
        {
            sb.append("( ");
            ArrayList<Clause> cl = new ArrayList<>(clauses);
            for (int i = 0; i < cl.size(); i++) {
                sb.append(cl.get(i));
                if(i <  cl.size()-1)
                    sb.append(" ) ^ ( ");

            }
            sb.append(" )");
        }

        return sb.toString();
    }
    //TODO: to jest ewidentnie niedokończone
    public boolean isContradictory()
    {
        ArrayList<Clause> c = new ArrayList<>(clauses);
        for (int i = 0; i < c.size()-1; i++) {
            Clause clause = c.get(i);
            if(clause.isAtom() && (!clause.getAtomValue()))
                return false;
            if(clause.literals.size()!=1)
                continue;
            //Mamy atom C, szukamy atomu ~C
            for (int j = i+1; j < c.size(); j++) {
                Clause clause2 = c.get(j);
                if(clause2.isAtom() && (!clause2.getAtomValue()))
                    return false;
                if(clause.literals.size()!=1)
                    continue;


            }
        }

        return false;
    }
}
