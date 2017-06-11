package pl.edu.pw.elka.pszt.Strategies;

import pl.edu.pw.elka.pszt.Clause;
import pl.edu.pw.elka.pszt.KnowledgeBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by erxyi on 30.05.2017.
 */
public class ShortestFirstStrategy extends Strategy {
    public ShortestFirstStrategy(KnowledgeBase knownFacts) {
        super(knownFacts);
    }

    @Override
    public int runOneStep() {
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
        if(!added.isEmpty()) {
            KnowledgeBase newKB = new KnowledgeBase(lastKB);
            int shortestProduced = added.stream().map(Clause::getLiteralsSize).min(Integer::compare).get();
            List<Clause> minimizedList = added.stream().filter(clause -> clause.getLiteralsSize() == shortestProduced).collect(Collectors.toList());
            newKB.clauses.addAll(minimizedList);
            newKB.substitute();
            steps.add(newKB);
        }
        return added.size();
    }
}
