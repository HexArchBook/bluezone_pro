package io.github.hexarchbook.bluezone.driven.forstoringdata.adapter.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoDb {

        public static MongoDatabase fromConnection ( String propertiesFile ) {
                try {
                        InputStream mongodbPropertiesStream = MongodbAdapter.class.getModule().getResourceAsStream(propertiesFile);
                        Properties mongodbProperties = new Properties();
                        mongodbProperties.load(mongodbPropertiesStream);
                        String hostName = mongodbProperties.getProperty("host");
                        String port = mongodbProperties.getProperty("port");
                        String databaseName = mongodbProperties.getProperty("database");
                        String connection = String.format(
                                "mongodb://%s:%s/%s",
                                hostName,
                                port,
                                databaseName
                        );
                        MongoClient mongoClient = MongoClients.create(connection);
                        return mongoClient.getDatabase(databaseName);
                } catch (IOException e) {
                        throw new RuntimeException("Error getting the database from the properties file",e);
                }
        }

}
