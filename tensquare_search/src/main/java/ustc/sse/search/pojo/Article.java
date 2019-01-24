package ustc.sse.search.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.search.pojo
 * @date 2019/1/22 15:50
 * @description God Bless, No Bug!
 */
@Document(indexName = "tensquare_article",type = "article")
@Setter@Getter
public class Article implements Serializable {

    @Id
    private String id;

    // 是否索引,表示该域是否能被搜索
    // 是否分词,表示搜索时全部匹配还是分词匹配
    // 是否存储,表示是否在页面展示,在这里定义的属性就表示要存储
    // analyzer--存储使用的分词器,searchAnalyzer--搜索使用的分词器
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;

    private String state;
}
