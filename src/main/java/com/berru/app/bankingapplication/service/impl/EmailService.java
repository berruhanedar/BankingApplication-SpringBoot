package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
