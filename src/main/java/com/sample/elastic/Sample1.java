package com.sample.elastic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.SingletonMap;
import org.apache.derby.impl.sql.catalog.SYSROUTINEPERMSRowFactory;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

public class Sample1 {

	public static void main(String[] args) throws IOException {

		// Initialization
		RestClient restClient = RestClient
				.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201, "http")).build();

		RestHighLevelClient client = new RestHighLevelClient(restClient);

		// GetRequest getRequest = new GetRequest(
		// "posts",
		// "doc",
		// "2");

		IndexRequest request = new IndexRequest("testarray", "aa", "1");
List<Map<String, String>> arrayobj = new ArrayList<>(); 
		Map<String, String> newObject = new HashMap<String, String>();
		newObject.put("label1", "key1");
		newObject.put("value", "test3");
		Map<String, String> newObject1 = new HashMap<String, String>();
		newObject1.put("label1", "key2");
		newObject1.put("value", "test4");
		arrayobj.add(newObject);
		arrayobj.add(newObject1);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("array", arrayobj);
		jsonMap.put("desc", "malek");

		request.source(jsonMap, XContentType.JSON);

		IndexResponse indexResponse = client.index(request);
		
		
		System.out.println(indexResponse.getType());
		// Update response

		// UpdateRequest updaterequest = new UpdateRequest("posts", "doc", "2");
		// String jsonString = "{" +
		// "\"updated\":\"2017-01-01\"," +
		// "\"reason\":\"daily update\"" +
		// "}";
		// updaterequest.doc(jsonString, XContentType.JSON);

		// Map<String, Object> jsonMap = new HashMap<>();
		//
		// jsonMap.put("updated", new Date());
		// jsonMap.put("reason", "daily update");
		//
		// UpdateRequest updaterequest = new UpdateRequest("posts", "doc", "2")
		// .doc(jsonMap);
		// updaterequest.fetchSource(true);
		//
		// UpdateResponse updateResponse = client.update(updaterequest);
		//
		// GetResult result = updateResponse.getGetResult();
		// if (result.isExists()) {
		// String sourceAsString = result.sourceAsString();
		// Map<String, Object> sourceAsMap = result.sourceAsMap();
		// byte[] sourceAsBytes = result.source();
		// System.out.println(sourceAsString);
		// } else {
		// System.out.println("no results");
		// }
		//
		// // Delete response
		// /*DeleteRequest delrequest = new DeleteRequest(
		// "posts",
		// "doc",
		// "1");
		// DeleteResponse deleteResponse = client.delete(delrequest);
		//
		// if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND)
		// {
		// System.out.println("result not found ");
		// } else {
		// System.out.println("doc deleted");
		// }*/
		// // Get response
		// GetResponse response = client.get(getRequest);
		//
		// String index = response.getIndex();
		// String type = response.getType();
		// String id = response.getId();
		//
		// if (response.isExists()) {
		// long version = response.getVersion();
		// String sourceAsString = response.getSourceAsString();
		// Map<String, Object> sourceAsMap = response.getSourceAsMap();
		// byte[] sourceAsBytes = response.getSourceAsBytes();
		// System.out.println(sourceAsString);
		// System.out.println(response.getSourceAsMap().get("message").toString());
		// } else {
		// System.out.println("not existing");
		// }

	}

}
