package pl.edu.pw.elka.pszt.Arguments;

/**
 * Created by erxyi on 25.05.2017.
 */
public class ArgumentType {
    ArgumentDictionary owner;
    int id;

    public ArgumentType(ArgumentDictionary owner, int id) {
        this.owner = owner;
        this.id = id;
    }

    @Override
    public String toString() {
        return owner.arguments.get(id);
    }
}
