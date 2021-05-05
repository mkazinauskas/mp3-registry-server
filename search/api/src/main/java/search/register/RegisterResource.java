package search.register;

public interface RegisterResource {
    RegisterEntryResponse latest(String uniqueId);

    RegisterEntryResponse first();
}
