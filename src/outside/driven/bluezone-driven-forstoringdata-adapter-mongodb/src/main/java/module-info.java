import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.driven.forstoringdata.adapter.mongodb.MongodbAdapter;

module bluezone.driven.forstoringdata.adapter.mongodb {

	/*
	 * Modules used by this module
	 */
	requires bluezone.app;
	requires static lombok;
	requires org.mongodb.driver.core;
	requires org.mongodb.driver.sync.client;
	requires org.mongodb.bson;
    requires bluezone.lib;

    /*
	 * Packages published to other modules
	 */
	exports io.github.hexarchbook.bluezone.driven.forstoringdata.adapter.mongodb;

	/*
	  Java Service Provider
	 */
	provides ForStoringData
		with MongodbAdapter;

}
