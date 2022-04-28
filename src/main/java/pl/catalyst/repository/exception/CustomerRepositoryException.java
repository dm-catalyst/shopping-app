package pl.catalyst.repository.exception;

public class CustomerRepositoryException extends RuntimeException{
    public CustomerRepositoryException(String message) {
        super(message);
    }
}
