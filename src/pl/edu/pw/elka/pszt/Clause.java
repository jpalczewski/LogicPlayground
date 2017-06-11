package pl.edu.pw.elka.pszt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by erxyi on 27.05.2017.
 */
public class Clause {
    public ArrayList<Literal> getLiterals() {
        return literals;
    }

    ArrayList<Literal> literals;
    private boolean isAtom;
    private boolean atomValue;

    public boolean isPartOfSolution() {
        return partOfSolution;
    }

    private boolean partOfSolution;

    private Clause leftAncestor;
    private Clause rightAncestor;


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
    public Integer getLiteralsSize() {return literals.size();}
    public boolean containsClause(Clause c){return literals.contains(c);}
    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
        this.isAtom = false;
        this.atomValue = false;
    }

    public Clause() {
        this.literals = new ArrayList<>();
        this.isAtom = false;
        this.atomValue = false;
    }

    public Clause(Literal... manyLiterals) {
        this.isAtom = false;
        this.atomValue = false;
        this.literals = new ArrayList<>();
        literals.addAll(Arrays.asList(manyLiterals));

    }

    public Clause(Clause c) {
        this.isAtom = c.isAtom;
        this.atomValue = c.atomValue;
        this.literals = new ArrayList<>();
        this.leftAncestor = c.leftAncestor;
        this.rightAncestor = c.rightAncestor;
        for(Literal l : c.literals)
        {
            this.literals.add(new Literal(l));
        }
    }

    public Clause(boolean atomValue)
    {
        isAtom = true;
        atomValue = false;
    }

    public void addLiteral(Literal l)
    {
        this.literals.add(l);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isAtom)
        {
            sb.append("(Atom: ");
            sb.append(atomValue);
            sb.append(")");
        }
        else {
            if (literals.size() == 0)
                sb.append("(empty clause)");
            else
                for (int i = 0; i < literals.size(); i++) {
                    sb.append(literals.get(i).toString());
                    if (i < literals.size() - 1)
                        sb.append(" v ");
                }

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
            if(result.getLiteralsSize()==0)
            {
                result.setAtom(true);
                result.setAtomValue(false);
            }
        }
        else
        {
            result.literals = new ArrayList<>();
        }


        return result;
    }

    public void simplify() {
        removeDuplicates();
        removeNullLiterals();
    }

    private void removeNullLiterals() {
        ArrayList<Literal> copy = new ArrayList<>(literals);
        for (int i = 0; i < literals.size(); i++) {
            Literal negatedLiteral = new Literal(literals.get(i));
            negatedLiteral.negate();
            if(copy.contains(literals.get(i)) && copy.contains(negatedLiteral))
            {
                copy.remove(literals.get(i));
                copy.remove(negatedLiteral);
            }
            if(copy.size()==0) {
                isAtom = true;
                atomValue = true;
                break;
            }
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

        if (isAtom != clause.isAtom) return false;
        if (atomValue != clause.atomValue) return false;
        return literals != null ? literals.equals(clause.literals) : clause.literals == null;
    }

    @Override
    public int hashCode() {
        int result = literals != null ? literals.hashCode() : 0;
        result = 31 * result + (isAtom ? 1 : 0);
        result = 31 * result + (atomValue ? 1 : 0);
        return result;
    }

    public void setPartOfSolution(boolean partOfSolution) {
        this.partOfSolution = partOfSolution;
    }

    public Clause getLeftAncestor() {
        return leftAncestor;
    }

    public void setLeftAncestor(Clause leftAncestor) {
        this.leftAncestor = leftAncestor;
    }

    public Clause getRightAncestor() {
        return rightAncestor;
    }

    public void setRightAncestor(Clause rightAncestor) {
        this.rightAncestor = rightAncestor;
    }
}
