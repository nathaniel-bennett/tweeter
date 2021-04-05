package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessException;
import com.nathanielbennett.tweeter.server.model.ResultsPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AmazonDAOTemplate {

    protected class DBIndex {
        private final String indexName;
        private final String indexPrimaryKeyAttr;

        public DBIndex(String indexName, String indexPrimaryKeyAttr) {
            this.indexName = indexName;
            this.indexPrimaryKeyAttr = indexPrimaryKeyAttr;
        }

        public String getIndexName() {
            return indexName;
        }

        public String getIndexPrimaryKeyAttr() {
            return indexPrimaryKeyAttr;
        }
    }

    private static final String REGION = "us-west-1";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
                                                        .standard()
                                                        .withRegion(REGION)
                                                        .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);


    private final String tableName;
    private final String partitionKeyAttr;
    private final String sortKeyAttr;

    public AmazonDAOTemplate(String tableName, String partitionKeyAttr) {
        this.tableName = tableName;
        this.partitionKeyAttr = partitionKeyAttr;
        this.sortKeyAttr = null;
    }

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
        try {
            Table table = dynamoDB.getTable(tableName);

            Item item = objectToDatabaseItem(o);
            table.putItem(item);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("Amazon DynamoDB Exception occurred: " + e.getLocalizedMessage());
        }
    }

    protected void addToTable(List<Object> oList) {
        try {
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
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("Amazon DynamoDB Exception occurred: " + e.getLocalizedMessage());
        }
    }

    protected void updateTable(String partitionKey, String fieldName, Object fieldValue) {
        try {
            AttributeUpdate attributeUpdate = new AttributeUpdate(fieldName)
                    .put(fieldValue);

            Table table = dynamoDB.getTable(tableName);
            table.updateItem(partitionKey, attributeUpdate);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("Amazon DynamoDB Exception occurred: " + e.getLocalizedMessage());
        }
    }



    protected void removeFromTable(String partitionKey) {
        try {
            Table table = dynamoDB.getTable(tableName);
            table.deleteItem(partitionKeyAttr, partitionKey);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("Amazon DynamoDB Exception occurred: " + e.getLocalizedMessage());
        }
    }

    protected void removeFromTable(String partitionKey, String sortKey) {
        try {
            Table table = dynamoDB.getTable(tableName);
            table.deleteItem(partitionKeyAttr, partitionKey, sortKeyAttr, sortKey);
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("Amazon DynamoDB Exception occurred: " + e.getLocalizedMessage());
        }
    }


    protected Object getFromTable(String partitionKey) {
        return getFromTable(partitionKey, null);
    }

    protected Object getFromTable(String partitionKey, String sortKey) {
        List<Map<String, AttributeValue>> items;

        String keyConditionExpression = "#partitionAttr = :partitionValue";
        if (sortKey != null) {
            keyConditionExpression += " and #sortAttr = :sortValue";
        }

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#partitionAttr", partitionKeyAttr);
        attrNames.put("#sortAttr", sortKeyAttr);
        // TODO: do we need to take sortKeyAttr/sortValue out when sortKey is null, or...?

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":partitionValue", new AttributeValue().withS(partitionKey));
        attrValues.put(":sortValue", new AttributeValue().withS(sortKey));

        try {
            QueryRequest queryRequest = new QueryRequest()
                    .withTableName(tableName)
                    .withKeyConditionExpression(keyConditionExpression)
                    .withExpressionAttributeNames(attrNames)
                    .withExpressionAttributeValues(attrValues);

            QueryResult queryResult = amazonDynamoDB.query(queryRequest);
            items = queryResult.getItems();
            if (items == null) {
                return null;
            }

        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("Amazon DynamoDB Exception occurred: " + e.getLocalizedMessage());
        }

        if (items.size() > 1) {
            return new DataAccessException("Multiple items found in database when only one was expected");
        }

        return databaseItemToObject(items.get(0));
    }


    protected List<Object> getAllFromTable(String partitionValue) {
        return getAllFromTable(partitionValue, null);
    }

    protected List<Object> getAllFromTable(String primaryValue, DBIndex index) {
        List<Object> result = new ArrayList<>();
        String primaryAttr;
        if (index != null) {
            primaryAttr = index.getIndexPrimaryKeyAttr();
        } else {
            primaryAttr = partitionKeyAttr;
        }

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#partitionAttr", primaryAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":partitionValue", new AttributeValue().withS(primaryValue));

        try {
            QueryRequest queryRequest = new QueryRequest()
                    .withTableName(tableName)
                    .withKeyConditionExpression("#partitionAttr = :partitionValue")
                    .withExpressionAttributeNames(attrNames)
                    .withExpressionAttributeValues(attrValues);

            if (index != null) {
                queryRequest = queryRequest.withIndexName(index.getIndexName());
            }

            QueryResult queryResult = amazonDynamoDB.query(queryRequest);
            List<Map<String, AttributeValue>> items = queryResult.getItems();
            if (items != null) {
                for (Map<String, AttributeValue> item : items) {
                    result.add(databaseItemToObject(item));
                }
            }

        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("Amazon DynamoDB Exception occurred: " + e.getLocalizedMessage());
        }

        return result;
    }


    protected ResultsPage getPagedFromDatabase(String partitionValue, int pageSize, String lastRetrieved) {
        return getPagedFromDatabase(partitionValue, pageSize, lastRetrieved, null);
    }

    protected ResultsPage getPagedFromDatabase(String partitionValue, int pageSize, String lastRetrieved, DBIndex index) {
        ResultsPage result = new ResultsPage();
        String primaryAttr;
        if (index != null) {
            primaryAttr = index.getIndexPrimaryKeyAttr();
        } else {
            primaryAttr = partitionKeyAttr;
        }

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#primaryAttr", primaryAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":primaryValue", new AttributeValue().withS(partitionValue));

        try {
            QueryRequest queryRequest = new QueryRequest()
                    .withTableName(tableName)
                    .withKeyConditionExpression("#primaryAttr = :primaryValue")
                    .withExpressionAttributeNames(attrNames)
                    .withExpressionAttributeValues(attrValues)
                    .withLimit(pageSize);

            if (index != null) {
                queryRequest = queryRequest.withIndexName(index.getIndexName());
            }

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
        } catch (AmazonDynamoDBException e) {
            throw new DataAccessException("Amazon DynamoDB Exception occurred: " + e.getLocalizedMessage());
        }

        return result;
    }
}
