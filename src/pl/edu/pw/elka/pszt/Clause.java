package pl.edu.pw.elka.pszt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by erxyi on 27.05.2017.
 */
public class Clause {
    ArrayList<Literal> literals;
    boolean isAtom;
    boolean atomValue;

    public boolean isAtom() {
        return isAtom;
    }

    public void setAtom(boolean atom) {
        isAtom = atom;
    }

    public boolean getAtomValue() {
        return atomValue;
    }

    public void setAtomValue(boolean atomValue) {
        this.atomValue = atomValue;
    }


    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    public Clause() {
        this.literals = new ArrayList<>();
    }

    public Clause(Clause c) {
        this.literals = new ArrayList<>();
        for(Literal l : c.literals)
        {
            this.literals.add(new Literal(l));
        }
    }

    public void addLiteral(Literal l)
    {
        this.literals.add(l);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(literals.size()==0)
            sb.append("(empty clause)");
        else
            for (int i = 0; i < literals.size(); i++) {
                sb.append(literals.get(i).toString());
                if(i  < literals.size()-1)
                    sb.append(" v ");
            }
        return sb.toString();
    }

    public boolean isEmpty()
    {
        return literals.size()==0;
    }

    public Clause resolve(Clause c)
    {
        Clause result = new Clause(this);
        Clause right = new Clause(c);
        boolean isResolvable = false;
        int leftPtr = 0, rightPtr = 0;
        for (int i = 0; i < result.literals.size(); i++) {
            Literal negatedLiteral = new Literal(this.literals.get(i));
            negatedLiteral.negate();
            for (int j = 0; j < c.literals.size(); j++) {
                Literal rightLiteral = c.literals.get(j);
                if((negatedLiteral.type.equals(rightLiteral.type)) && (negatedLiteral.isUnifiable(rightLiteral)) && (negatedLiteral.isNegated()==rightLiteral.isNegated()))
                {
                    leftPtr = i;
                    rightPtr = j;
                    isResolvable = true;
                    break;

                }
            }
            if(isResolvable)
                break;

        }

        if(isResolvable)
        {
            result.literals.remove(leftPtr);
            right.literals.remove(rightPtr);
            result.concatenate(right);
            result.simplify();
        }
        else
        {
            result.literals = new ArrayList<>();
        }


        return result;
    }

    public void simplify() {
        removeDuplicates();
        ArrayList<Literal> copy = new ArrayList<>(literals);
        for (int i = 0; i < literals.size(); i++) {
            Literal negatedLiteral = new Literal(literals.get(i));
            negatedLiteral.negate();
            if(copy.contains(literals.get(i)) && copy.contains(negatedLiteral))
            {
                copy.remove(literals.get(i));
                copy.remove(negatedLiteral);
            }
            if(copy.size()==0)
                break;
        }
        literals = copy;
    }

    private void removeDuplicates() {
        Set<Literal> s = new HashSet<>(literals);
        literals = new ArrayList<>(s);
    }

    private void concatenate(Clause right) {
        this.literals.addAll(right.literals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clause clause = (Clause) o;

        return literals.equals(clause.literals);
    }

    @Override
    public int hashCode() {
        return literals.hashCode();
    }
}
