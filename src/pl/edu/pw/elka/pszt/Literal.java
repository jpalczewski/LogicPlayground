package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

import java.util.Arrays;

/**
 * Created by erxyi on 25.05.2017.
 */
public class Literal {


    LiteralType type;
    String[] argumentValues;
    private boolean negated;

    public Literal(Literal l)
    {
        type = l.type;
        argumentValues = l.argumentValues.clone();
        negated = l.negated;
    }

    public Literal(LiteralType type) {
        this.type = type;
        this.argumentValues = new String[type.arguments.size()];
        this.negated = false;
    }

    public void setArgumentValue(ArgumentType at, String value)
    {
        int typeLocation = type.arguments.indexOf(at);
        if(typeLocation == -1)
            throw new RuntimeException("Type not found in predicate!");

        argumentValues[typeLocation] = value;
    }

    public void negate()
    {
        negated = !negated;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(negated)
            sb.append('~');
        sb.append(type.name);
        sb.append('(');
        for (int i = 0; i < type.arguments.size(); i++) {
            String s = argumentValues[i];
            if(s==null)
                sb.append(type.arguments.get(i).toString());
            else
                sb.append(s);
            if(i != type.arguments.size()-1)
                sb.append(',');
        }
        sb.append(')');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        if (negated != literal.negated) return false;
        if (!type.equals(literal.type)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(argumentValues, literal.argumentValues);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + Arrays.hashCode(argumentValues);
        result = 31 * result + (negated ? 1 : 0);
        return result;
    }

    public boolean isUnifiable(Literal l)
    {
        if(this == l) return true;
        if(l == null) return false;

        if(this.type.arguments.size()!= l.type.arguments.size())
            return false;

        for (int i = 0; i < type.arguments.size(); i++) {
            if(l.argumentValues[i] != null && this.argumentValues[i] != null)
                if(!l.argumentValues[i].equals(this.argumentValues[i]))
                    return false;
        }

        return true;
    }

    public Literal Unify(Literal l)
    {
        if(!l.isUnifiable(this))
            throw new RuntimeException("Clauses aren't unifiable!");

        Literal result = new Literal(l.type);

        for (int i = 0; i < result.type.arguments.size(); i++) {
            if(this.argumentValues[i] == null)
                result.argumentValues[i] = l.argumentValues[i];
            else
                result.argumentValues[i] = argumentValues[i];
        }

        return result;
    }
}
