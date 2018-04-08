package com.sample.elastic;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.action.DocWriteRequest;


public class bulkSample {
	
	
	public static void main(String[] args) throws IOException {
		
		//Initialization
		RestClient restClient = RestClient.builder(
		        new HttpHost("localhost", 9200, "http"),
		        new HttpHost("localhost", 9201, "http")).build();
		
		RestHighLevelClient client =
			    new RestHighLevelClient(restClient);
		
		BulkRequest request = new BulkRequest(); 
		
		request.add(new IndexRequest("posts", "doc", "1")  
		        .source(XContentType.JSON,"field", "foo"));
		
		request.add(new IndexRequest("posts", "doc", "2")  
		        .source(XContentType.JSON,"field", "bar"));
		
		request.add(new IndexRequest("posts", "doc", "3")  
		        .source(XContentType.JSON,"field", "baz"));
		
		request.add(new DeleteRequest("posts", "doc", "3")); 
		
		request.add(new UpdateRequest("posts", "doc", "2") 
		        .doc(XContentType.JSON,"other", "test"));
		request.add(new IndexRequest("posts", "doc", "4")  
		        .source(XContentType.JSON,"field", "baz"));
		
		BulkResponse bulkResponse = client.bulk(request);
		
		for (BulkItemResponse bulkItemResponse : bulkResponse) { 
		    DocWriteResponse itemResponse = bulkItemResponse.getResponse(); 

		    if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX
		            || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) { 
		        IndexResponse indexResponse = (IndexResponse) itemResponse;
		        System.out.println(indexResponse.getResult());

		    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) { 
		        UpdateResponse updateResponse = (UpdateResponse) itemResponse;
		        System.out.println(updateResponse.getGetResult().sourceAsString());
		    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) { 
		        DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
		    }
		}
	}

}
