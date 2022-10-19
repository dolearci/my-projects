package cz.muni.fi.pv168.cashflow.export.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.Batch;
import cz.muni.fi.pv168.cashflow.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.cashflow.business.service.export.format.Format;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BatchXmlExporter implements BatchExporter {

    private static final XmlMapper XML_MAPPER = new XmlMapper();
    private static final Format FORMAT = new Format("XML", List.of("xml"));

    public BatchXmlExporter() {
        XML_MAPPER.findAndRegisterModules();
        XML_MAPPER.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd"));
        XML_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static void exportToXml(Object data, String fileName) throws IOException {
        ObjectWriter writer = XML_MAPPER.writer().withRootName("transactions");
        ObjectNode rootNode = XML_MAPPER.createObjectNode();
        rootNode.putPOJO("transaction", data);
        writer.writeValue(new File(fileName), rootNode);
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
            exportToXml(batch.transactions().stream().map(BatchXmlExporter::filterOutGuid).collect(Collectors.toList()), filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Format getFormat() {
        return FORMAT;
    }
}
