package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erxyi on 25.05.2017.
 */
public class LiteralType {
    String name;
    ArrayList<ArgumentType> arguments;

    public LiteralType(String name, ArrayList<ArgumentType> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public LiteralType(String name) {
        this.name = name;
        arguments = new ArrayList<>();
    }

    public void addArgument(ArgumentType at) {
        arguments.add(at);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append('(');
        for (int i = 0; i < arguments.size(); i++) {
            sb.append(arguments.get(i).toString());
            if(i != arguments.size()-1)
                sb.append(',');
        }
        sb.append(')');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LiteralType that = (LiteralType) o;

        if (!name.equals(that.name)) return false;
        return arguments.equals(that.arguments);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + arguments.hashCode();
        return result;
    }
}
