package com.berru.app.bankingapplication.exception;

import org.springframework.http.HttpStatus;
import java.util.List;

public class DestinationAccountNotFoundException extends BaseException {

    public DestinationAccountNotFoundException(String accountNumber) {
        super("Destination account with number " + accountNumber + " does not exist!");
    }

    public DestinationAccountNotFoundException(String accountNumber, List<? extends ErrorDetailDto> errors) {
        super("Destination account with number " + accountNumber + " does not exist!", errors);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
