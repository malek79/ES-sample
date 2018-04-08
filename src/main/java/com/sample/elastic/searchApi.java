package com.sample.elastic;

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


public class searchApi {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//Initialization
		RestClient restClient = RestClient.builder(
		        new HttpHost("localhost", 9200, "http")).build();
		
		RestHighLevelClient client =
			    new RestHighLevelClient(restClient);		
		
		//
		
		SearchRequest searchRequest = new SearchRequest("bank");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
//		searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 

	
//		TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_state")
//		        .field("state.keyword");
//		aggregation.subAggregation(AggregationBuilders.avg("average_age")
//		        .field("age"));
//		
//		searchSourceBuilder.aggregation(aggregation);
		//searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
		
		
		// AGGS : 
		
//		Aggregations aggregations = searchResponse.getAggregations();
//		
//		Terms byStateAggregation = aggregations.get("by_state"); 
//		Bucket elasticBucket = byStateAggregation.getBuckets().get(0); 
//		Avg averageAge = elasticBucket.getAggregations().get("average_age"); 
//		double avg = averageAge.getValue();
		
//		System.out.println(avg);
//		for (Bucket b : byStateAggregation.getBuckets()) {
//			System.out.println(b.getKey());
//		}
//		System.out.println(byStateAggregation.getBuckets().iterator().next().getKey());
//		SearchHits hits = searchResponse.getHits();
		
//		SearchHit[] searchHits = hits.getHits();
		
//		for (SearchHit hit : searchHits) {
//			
////			System.out.println(hit.getId());
////			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
////		
////			System.out.println( sourceAsMap.get("firstname"));
//		
//		}
	
		//Suggestions : 
		
		SuggestionBuilder termSuggestionBuilder =
		    SuggestBuilders.termSuggestion("firstname").text("Rachelle"); 
		SuggestBuilder suggestBuilder = new SuggestBuilder();
		suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder); 
		searchSourceBuilder.suggest(suggestBuilder);
searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest);
		Suggest suggest = searchResponse.getSuggest(); 
		TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user"); 
		for (TermSuggestion.Entry entry : termSuggestion.getEntries()) { 
		    for (TermSuggestion.Entry.Option option : entry) { 
		        String suggestText = option.getText().string();
		        System.out.println(suggestText);
		    }
		}
				//////another one 
		/*MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", "Elasticsearch"); 
		
		matchQueryBuilder.fuzziness(Fuzziness.AUTO); 
		
		searchSourceBuilder.query(matchQueryBuilder);
		
		searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
		
		searchSourceBuilder.sort(new FieldSortBuilder("_uid").order(SortOrder.ASC));  

*/
		
		restClient.close();
		
	}

}
