package animals.petstore.store;

public class DuplicatePetStoreRecordException extends RuntimeException {

    public DuplicatePetStoreRecordException(String message) {
        super(message);
    }

    public DuplicatePetStoreRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}