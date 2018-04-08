package com.sample.ESHadoop;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.elasticsearch.common.text.Text;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.elasticsearch.action.DocWriteRequest;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.spark_project.guava.collect.ImmutableList;
import org.spark_project.guava.collect.ImmutableMap;

public class searchApi {

	public static void main(String[] args) throws IOException, InterruptedException {

		// Initialization
		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();

		RestHighLevelClient client = new RestHighLevelClient(restClient);

		// SparkSession ss = SparkSession.builder().appName("Spark
		// ES").master("local[*]").getOrCreate();
		SparkConf conf = new SparkConf().setAppName("Spark ES").setMaster("local[*]");

		JavaSparkContext jsc = new JavaSparkContext(conf);

		// Sample 1
		/*
		 * Map<String, ?> numbers = ImmutableMap.of("one", 1, "two", 2);
		 * Map<String, ?> airports = ImmutableMap.of("OTP", "Otopeni", "SFO",
		 * "San Fran");
		 * 
		 * JavaRDD<Map<String, ?>> javaRDD =
		 * jsc.parallelize(ImmutableList.of(numbers, airports));
		 * JavaEsSpark.saveToEs(javaRDD, "spark/docs");
		 */
		// JavaEsSpark.saveToEs(javaRDD, "spark2/docs",
		// ImmutableMap.of("es.mapping.id", "id"));

		// Sample 2

		/*
		 * String json1 = "{\"reason\" : \"business\",\"airport\" : \"SFO\"}";
		 * String json2 = "{\"participants\" : 5,\"airport\" : \"OTP\"}";
		 * 
		 * JavaRDD<String> stringRDD = jsc.parallelize(ImmutableList.of(json1,
		 * json2)); JavaEsSpark.saveJsonToEs(stringRDD, "spark/json-trips");
		 */
		//

		// Sample 3
/*
		Map<String, ?> game = ImmutableMap.of("media_type", "game", "title", "FF VI", "year", "1994");
		Map<String, ?> book = ImmutableMap.of("media_type", "book", "title", "Harry Potter", "year", "2010");
		Map<String, ?> cd = ImmutableMap.of("media_type", "music", "title", "Surfing With The Alien");

		JavaRDD<Map<String, ?>> javaRDD = jsc.parallelize(ImmutableList.of(game, book, cd));
		
		JavaEsSpark.saveToEs(javaRDD, "my-collection/{media_type}");
		//
	*/
		
		// Reading from ES : 
		
		JavaRDD<Map<String, Object>> esRDD =
                JavaEsSpark.esRDD(jsc, "bank/account").values(); 
		
		JavaRDD<Map<String, Object>> filtered = esRDD.filter(doc ->
        doc.containsValue("Jan"));	

		filtered.collect();
		// Close
		jsc.close();
		restClient.close();

	}

}
