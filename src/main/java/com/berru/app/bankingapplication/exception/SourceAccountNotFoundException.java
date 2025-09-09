package com.berru.app.bankingapplication.exception;

import org.springframework.http.HttpStatus;
import java.util.List;

public class SourceAccountNotFoundException extends BaseException {

    public SourceAccountNotFoundException(String accountNumber) {
        super("Source account with number " + accountNumber + " does not exist!");
    }

    public SourceAccountNotFoundException(String accountNumber, List<? extends ErrorDetailDto> errors) {
        super("Source account with number " + accountNumber + " does not exist!", errors);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
