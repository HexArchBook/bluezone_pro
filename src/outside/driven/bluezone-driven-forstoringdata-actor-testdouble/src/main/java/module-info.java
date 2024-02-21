import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.driven.forstoringdata.actor.testdouble.FakeDataRepository;

module bluezone.driven.forstoringdata.actor.testdouble {

	/*
	 * Modules used by this module
	 */
	requires bluezone.app;
//	requires static lombok;
    requires bluezone.lib;

    /*
	 * Packages published to other modules
	 */
	exports io.github.hexarchbook.bluezone.driven.forstoringdata.actor.testdouble;

	/*
	  Java Service Provider
	 */
	provides ForStoringData
		with FakeDataRepository;

}
