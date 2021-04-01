package com.nathanielbennett.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.nathanielbennett.tweeter.server.exceptions.DataAccessFailureException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AmazonDAOTemplate {

    private static final String REGION = "us-west-1";


    protected abstract String getTableName();

    protected abstract Map<String, String> getNameMap();
    protected abstract Map<String, Object> getValueMap(Object o);

    protected abstract String getConditionExpression();

    protected abstract String getIndexName();

    protected abstract Object convertItems(List<Item> items) throws DataAccessFailureException;

    protected abstract PrimaryKey getDeletePrimaryKey(Object o);


    protected void addToDatabase(Object o) {

    }

    protected void addToDatabase(List<Object> o) {
        // TODO: use transaction here

    }

    protected Object getFromDatabase(Object o) {
        Table table = getTable();
        Index index = null;

        if (getIndexName() != null) {
            index = table.getIndex(getIndexName());
        }

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression(getConditionExpression())
                .withNameMap(getNameMap())
                .withValueMap(getValueMap(o));

        if (index != null) {
            querySpec = querySpec.withScanIndexForward(false);
        } else {
            querySpec = querySpec.withScanIndexForward(true);
        }

        ItemCollection<QueryOutcome> outcome = null;

        List<Item> items = new ArrayList<>();
        try {
            if (index != null) {
                outcome = index.query(querySpec);
            } else {
                outcome = table.query(querySpec);
            }

            Iterator<Item> iterator = outcome.iterator();

            while (iterator.hasNext()) {
                items.add(iterator.next());
            }
        } catch (AmazonDynamoDBException e) { // TODO: is this the right exception?

            throw new DataAccessFailureException("Database access failed: " + e.getLocalizedMessage());
        }

        return convertItems(items);
    }

    protected void updateDatabase(Object o) {

    }

    protected void removeFromDatabase(Object o) {
        Table table = getTable();

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(getDeletePrimaryKey(o));

        try {
            table.deleteItem(deleteItemSpec);
        } catch (AmazonDynamoDBException e) {

            throw new DataAccessFailureException("Database delete failed: " + e.getLocalizedMessage());
        }
    }


    private Table getTable() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION)
                .build();

        DynamoDB db = new DynamoDB(client);

        return db.getTable(getTableName());
    }

}
