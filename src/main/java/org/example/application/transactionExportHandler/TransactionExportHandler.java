package org.example.application.transactionExportHandler;

import com.opencsv.CSVWriter;
import org.example.model.TransactionModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionExportHandler {

    public void handle(List<TransactionModel> transactionList) {
        File file = new File(String.format("ExportedTransaction%s.csv", LocalDateTime.now()));
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = {"ID", "From IBAN", "To IBAN", "Amount", "Date", "Time"};
            writer.writeNext(header);
            List<String[]> data = new ArrayList<>();
            transactionList.forEach(t -> data.add(new String[]{String.valueOf(t.getId()), t.getFromAccount(), t.getToAccount(), t.getAmount().toString(), t.getDate().toString(), t.getTime().toString(),}));
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
