package az.nuran.nurmail.exception;

public class NurmailMissingVariableException extends NurmailException {

    public NurmailMissingVariableException(String varName) {
        super("Missing variable '%s'. Please provide it.".formatted(varName));
    }
}
