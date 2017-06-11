package pl.edu.pw.elka.pszt.Strategies;

import pl.edu.pw.elka.pszt.Clause;
import pl.edu.pw.elka.pszt.KnowledgeBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by erxyi on 30.05.2017.
 */
public abstract class Strategy {

    public ArrayList<KnowledgeBase> getSteps() {
        return steps;
    }

    ArrayList<KnowledgeBase> steps;

    public void setSteps(ArrayList<KnowledgeBase> steps) {
        this.steps = steps;
    }

    public Clause getHipotesis() {
        return hipotesis;
    }

    public void setHipotesis(Clause hipotesis) {
        this.hipotesis = hipotesis;
    }

    Clause hipotesis;

    public Strategy(KnowledgeBase knownFacts) {
        steps = new ArrayList<>();
        steps.add(knownFacts);
    }

    private Predicate<Clause> getAtoms() {
        return c -> c.getLiteralsSize()==1;
    }
    public abstract int runOneStep();

    public void solve() {
        int addedInLastStep = runOneStep();
        while(
                !steps.get(steps.size()-1).isContradictory()
                        &&
                        addedInLastStep != 0
                )
            addedInLastStep = runOneStep();

        if(isLastStepContradictory())
            markSolutionPath();

    }

    public boolean isLastStepContradictory() {
        return steps.get(steps.size()-1).isContradictory();
    }

    private void markSolutionPath()
    {
        if(!isLastStepContradictory())
            throw new RuntimeException("Solution doesn't exist!");

        List<Clause> finalAtomList = steps.get(steps.size()-1).clauses.parallelStream()
                .filter( getAtoms())
                .distinct()
                .collect(Collectors.toList());

        Clause left = new Clause(), right = new Clause(), temp_left, temp_right;
        for (int i = 0; i < finalAtomList.size(); i++) {
            temp_left = finalAtomList.get(i);
            for (int j = 0; j < finalAtomList.size(); j++) {
                temp_right = finalAtomList.get(j);
                if(i==j) continue;
                if(
                        temp_left.getLiterals().get(0).isNegated() != temp_right.getLiterals().get(0).isNegated() &&
                                temp_left.getLiterals().get(0).isUnifiable(temp_right.getLiterals().get(0)) &&
                                temp_left.getLiterals().get(0).getType()==temp_right.getLiterals().get(0).getType()
                        )
                {
                    left = temp_left;
                    right = temp_right;
                    break;
                }
                if(left.getLiteralsSize()>0)
                    break;
            }
        }

        left.setPartOfSolution(true);
        right.setPartOfSolution(true);
        ArrayList<Clause> nowParsing = new ArrayList<>(), nextParsing = new ArrayList<>();
        nowParsing.add(left.getLeftAncestor());
        nowParsing.add(left.getRightAncestor());
        nowParsing.add(right.getLeftAncestor());
        nowParsing.add(right.getRightAncestor());
        for (int i = steps.size()-2; i >= 0; i--) {
            HashSet<Clause> clauseHashSet = steps.get(i).clauses;

            for (int j = 0; j < nowParsing.size(); j++) {
                Clause x = nowParsing.get(j);
                if(clauseHashSet.contains(x))
                {
                    nextParsing.add(x.getLeftAncestor());
                    nextParsing.add(x.getRightAncestor());
                    clauseHashSet.remove(x);
                    x.setPartOfSolution(true);
                    clauseHashSet.add(x);
                }
            }

            nowParsing = nextParsing;
            nextParsing = new ArrayList<>();
        }
    }
}
