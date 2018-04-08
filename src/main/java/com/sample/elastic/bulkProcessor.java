package com.sample.elastic;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.action.DocWriteRequest;


public class bulkProcessor {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//Initialization
		RestClient restClient = RestClient.builder(
		        new HttpHost("localhost", 9200, "http"),
		        new HttpHost("localhost", 9201, "http")).build();
		
		RestHighLevelClient client =
			    new RestHighLevelClient(restClient);
		
		
		ThreadPool threadPool = new ThreadPool(Settings.EMPTY); 

		BulkProcessor.Listener listener = new BulkProcessor.Listener() { 
		    @Override
		    public void beforeBulk(long executionId, BulkRequest request) {
		    	
		    	  int numberOfActions = request.numberOfActions(); 
		          System.out.println("Executing bulk [{}] with {} requests"+ executionId+" ++  "+ numberOfActions);
		        
		    }

		    @Override
		    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
		        
		    	if (response.hasFailures()) { 
		    		System.out.println("Bulk [{}] executed with failures"+ executionId);
		        } else {
		        	System.out.println("Bulk [{}] completed in {} milliseconds"+ executionId+ " ++"+ response.getTook().getMillis());
		        }
		    }

		    @Override
		    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
		        
		    }
		};

		BulkProcessor bulkProcessor = new BulkProcessor.Builder(client::bulkAsync, listener, threadPool)
		        .build(); 
		
		IndexRequest one = new IndexRequest("posts", "proc", "1").
		        source(XContentType.JSON, "title", "In which order are my Elasticsearch queries executed?");
		IndexRequest two = new IndexRequest("posts", "proc", "2")
		        .source(XContentType.JSON, "title", "Current status and upcoming changes in Elasticsearch");
		IndexRequest three = new IndexRequest("posts", "proc", "3")
		        .source(XContentType.JSON, "title", "The Future of Federated Search in Elasticsearch");

		bulkProcessor.add(one);
		bulkProcessor.add(two);
		bulkProcessor.add(three);
		
		
		
		boolean terminated = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
		
		System.out.println(terminated);
		bulkProcessor.close();
	}

}
