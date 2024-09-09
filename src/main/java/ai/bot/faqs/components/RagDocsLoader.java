package ai.bot.faqs.components;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RagDocsLoader {

    private static final Logger log = Logger.getLogger(RagDocsLoader.class.getName());
    private final JdbcClient jdbcClient;
    private final VectorStore vectorStore;
    @Value("classpath:/docs/faqs.pdf")
    private Resource faqsPdfResource;

    public RagDocsLoader(JdbcClient jdbcClient, VectorStore vectorStore) {
        this.jdbcClient = jdbcClient;
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void init() {
        Integer count = jdbcClient.sql("select count(*) from faqs_vector_store")
                .query(Integer.class)
                .single();
        log.info("Current count of the vector store is: " + count);

        if(count == 0){
            log.info("Vector store is empty, Loading the vector store from referenced pdf");
            var config = PdfDocumentReaderConfig.builder()
                    .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder()
                            .withNumberOfBottomTextLinesToDelete(0)
                            .withNumberOfTopPagesToSkipBeforeDelete(0)
                            .build())
                    .withPagesPerDocument(1).build();
            var pdfReader = new PagePdfDocumentReader(faqsPdfResource, config);
            var splitter = new TokenTextSplitter();
            vectorStore.accept(splitter.apply(pdfReader.get()));
            log.info("Vector store loaded successfully");
        }
    }

}
