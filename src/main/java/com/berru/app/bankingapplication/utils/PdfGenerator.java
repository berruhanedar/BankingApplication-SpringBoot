package com.berru.app.bankingapplication.utils;
import com.berru.app.bankingapplication.entity.Transaction;
import com.berru.app.bankingapplication.entity.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Slf4j
public class PdfGenerator {

    public static void generateBankStatement(List<Transaction> transactions, User user,
                                             String startDate, String endDate, OutputStream outputStream)
            throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();
        log.info("PDF document opened");

        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("The Bank"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);
        bankInfoTable.addCell(bankName);

        PdfPCell bankAddress = new PdfPCell(new Phrase("Istanbul, Turkey"));
        bankAddress.setBorder(0);
        bankInfoTable.addCell(bankAddress);
        document.add(bankInfoTable);

        PdfPTable statementInfo = new PdfPTable(2);
        statementInfo.addCell(new PdfPCell(new Phrase("Start Date: " + startDate)));
        statementInfo.addCell(new PdfPCell(new Phrase("Statement of Account")));
        statementInfo.addCell(new PdfPCell(new Phrase("End Date: " + endDate)));
        statementInfo.addCell(new PdfPCell(new Phrase("Customer Name: " + user.getFirstName() + " " + user.getLastName())));
        statementInfo.addCell(new PdfPCell(new Phrase("Customer Address: " + user.getAddress())));
        document.add(statementInfo);

        PdfPTable transactionsTable = new PdfPTable(4);
        transactionsTable.addCell(createHeaderCell("DATE"));
        transactionsTable.addCell(createHeaderCell("TRANSACTION TYPE"));
        transactionsTable.addCell(createHeaderCell("TRANSACTION AMOUNT"));
        transactionsTable.addCell(createHeaderCell("STATUS"));

        for (Transaction t : transactions) {
            transactionsTable.addCell(new Phrase(t.getCreatedAt().toString()));
            transactionsTable.addCell(new Phrase(t.getTransactionType()));
            transactionsTable.addCell(new Phrase(t.getAmount().toString()));
            transactionsTable.addCell(new Phrase(t.getStatus()));
        }

        document.add(transactionsTable);
        document.close();
        log.info("PDF document closed");
    }

    private static PdfPCell createHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setBorder(0);
        return cell;
    }
}
