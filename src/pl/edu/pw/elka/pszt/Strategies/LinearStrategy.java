package pl.edu.pw.elka.pszt.Strategies;

import pl.edu.pw.elka.pszt.Clause;
import pl.edu.pw.elka.pszt.KnowledgeBase;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by erxyi on 11.06.2017.
 */
public class LinearStrategy extends Strategy {
    public LinearStrategy(KnowledgeBase knownFacts) {
        super(knownFacts);
    }

    @Override
    public int runOneStep() {
        if(hipotesis == null)
            throw new RuntimeException("Linear strategy failed - hipotesis not set!");


        ArrayList<Clause> list = new ArrayList<>(steps.get(steps.size()-1).clauses);
        for (int i = 0; i < list.size(); i++) {
            Clause step = hipotesis.resolve(list.get(i));
            if(!step.isAtom()  && step.getLiteralsSize()==0)
                continue;
            else
            {
                if(!step.isAtom() && list.contains(step))
                    continue;

                if((step.isAtom() && !step.getAtomValue()) || !step.isAtom())
                {
                    step.setLeftAncestor(list.get(i));
                    step.setRightAncestor(hipotesis);

                    list.add(step);
                    hipotesis = step;
                    steps.add(new KnowledgeBase(new HashSet<>(list)));
                    return 1;
                }

            }
        }
        return 0;
    }



}
