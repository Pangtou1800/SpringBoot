package pt.joja;

import com.alibaba.fastjson.JSON;
import net.minidev.json.JSONArray;
import org.assertj.core.data.Index;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.MetaDataIndexTemplateService;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pt.joja.entity.Book;
import pt.joja.repository.BookRepository;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticTest {

    @Autowired
    RestHighLevelClient highLevelClient;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Test
    public void test01() {
        IndexRequest request = new IndexRequest("joja", "book");
        request.id("book01");
        Book book = new Book(1,"DoraAmon","Fujiko.F.Fujio");
        request.source(JSON.toJSON(book), XContentType.JSON);
        try {

            highLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02() {
        Book book = new Book(2,"Coca-cola","TFS");
        IndexQuery indexQuery = new IndexQueryBuilder().withId("book02").withObject(book).build();
        IndexCoordinates indexCoordinates = IndexCoordinates.of("joja");
    }
}
