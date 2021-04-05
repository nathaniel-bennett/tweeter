package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.model.ResultsPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AmazonDAOTemplate {

    private static final String REGION = "us-west-1";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
                                                        .standard()
                                                        .withRegion(REGION)
                                                        .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);


    private final String tableName;
    private final String partitionKeyAttr;
    private final String sortKeyAttr;


    // Strings that need to be defined
    public AmazonDAOTemplate(String tableName, String partitionKeyAttr, String sortKeyAttr) {
        this.tableName = tableName;
        this.partitionKeyAttr = partitionKeyAttr;
        this.sortKeyAttr = sortKeyAttr;
    }

    // Functions that need to be implemented--implementations can be left empty if not used
    protected abstract Object databaseItemToObject(Map<String, AttributeValue> item) throws DataAccessException;
    protected abstract Item objectToDatabaseItem(Object o);


    protected void addToTable(Object o) {
        Table table = dynamoDB.getTable(tableName);

        Item item = objectToDatabaseItem(o);
        table.putItem(item);
    }

    protected void addToTable(List<Object> oList) {
        TableWriteItems tableWriteItems = new TableWriteItems(tableName);

        for (Object o : oList) {
            Item item = objectToDatabaseItem(o);
            tableWriteItems.addItemToPut(item);
        }

        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
        if (outcome.getUnprocessedItems().size() > 0) {
            // TODO: log error here
            throw new DataAccessException("Failed to add some items to table during batched add.");
        }
    }

    protected void updateTable(String partitionKey, String fieldName, Object fieldValue) {
        AttributeUpdate attributeUpdate = new AttributeUpdate(fieldName)
                .put(fieldValue);

        Table table = dynamoDB.getTable(tableName);
        table.updateItem(partitionKey, attributeUpdate);
    }



    protected void removeFromTable(String partitionKey) {
        Table table = dynamoDB.getTable(tableName);
        table.deleteItem(partitionKeyAttr, partitionKey);
    }

    protected void removeFromTable(String partitionKey, String sortKey) {
        Table table = dynamoDB.getTable(tableName);
        table.deleteItem(partitionKeyAttr, partitionKey, sortKeyAttr, sortKey);
    }



    protected ResultsPage getPagedFromDatabase(String partitionValue, int pageSize, String lastRetrieved) {
        ResultsPage result = new ResultsPage();

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#partitionAttr", partitionKeyAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":partitionValue", new AttributeValue().withS(partitionValue));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withKeyConditionExpression("#partitionAttr = :partitionValue")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(pageSize);

        if (lastRetrieved != null && !lastRetrieved.isEmpty()) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(partitionKeyAttr, new AttributeValue().withS(partitionValue));
            startKey.put(sortKeyAttr, new AttributeValue().withS(lastRetrieved));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                result.addValue(databaseItemToObject(item));
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            result.setLastKey(lastKey.get(sortKeyAttr).getS());
        }

        return result;
    }
}
