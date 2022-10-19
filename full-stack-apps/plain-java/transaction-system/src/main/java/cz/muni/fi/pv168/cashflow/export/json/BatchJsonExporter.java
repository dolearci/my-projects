package cz.muni.fi.pv168.cashflow.export.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.Batch;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BatchJsonExporter implements BatchExporter {

    private static final JsonMapper JSON_MAPPER = new JsonMapper();
    private static final Format FORMAT = new Format("JSON", List.of("json"));

    public BatchJsonExporter() {
        JSON_MAPPER.findAndRegisterModules();
        JSON_MAPPER.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd"));
        JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static void exportToJson(Object data, String fileName) throws IOException {
        ObjectNode outerObject = JSON_MAPPER.createObjectNode();
        outerObject.putPOJO("transactions", data);
        JSON_MAPPER.writeValue(new File(fileName), outerObject);
    }

    private static Transaction filterOutGuid(Transaction transaction) {
        transaction.setGuid(null);
        transaction.getCategory().setGuid(null);
        transaction.getCurrency().setGuid(null);
        return transaction;
    }

    @Override
    public void exportBatch(Batch batch, String filePath) {
        try {
            exportToJson(batch.transactions().stream().map(BatchJsonExporter::filterOutGuid).collect(Collectors.toList()), filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Format getFormat() {
        return FORMAT;
    }
}
