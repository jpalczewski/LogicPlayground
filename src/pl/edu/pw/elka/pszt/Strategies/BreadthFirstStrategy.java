package pl.edu.pw.elka.pszt.Strategies;

import pl.edu.pw.elka.pszt.Clause;
import pl.edu.pw.elka.pszt.KnowledgeBase;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by erxyi on 30.05.2017.
 */
public class BreadthFirstStrategy extends Strategy {
    public BreadthFirstStrategy(KnowledgeBase knownFacts) {
        super(knownFacts);
    }

    @Override
    public void solve() {
        KnowledgeBase lastKB = steps.get(steps.size()-1);
        ArrayList<Clause> initialList;
        HashSet<Clause> added = new HashSet<>();
        initialList = new ArrayList<>(lastKB.clauses);
        added = new HashSet<>();
        for (int i = 0; i < initialList.size() - 1; i++) {
            for (int j = i; j < initialList.size(); j++) {
                Clause result = initialList.get(i).resolve(initialList.get(j));
                if((result.getLiteralsSize()==0 && result.isAtom()) || result.getLiteralsSize()>0)
                    if(!lastKB.clauses.contains(result))
                        added.add(result);
            }
        }
        KnowledgeBase newKB = new KnowledgeBase(lastKB);
        newKB.clauses.addAll(added);
        steps.add(newKB);
    }
}
