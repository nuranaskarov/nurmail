package az.nuran.nurmail.exception;

public class NurmailMissingSectionException extends NurmailException {

    public NurmailMissingSectionException(String sectionName) {
        super("Missing section <%s>. Please provide it.".formatted(sectionName));
    }
}
