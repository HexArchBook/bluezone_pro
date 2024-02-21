import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;
import io.github.hexarchbook.bluezone.driven.forobtainingdatetime.actor.testdouble.StubDateTimeService;

module bluezone.driven.forobtainingdatetime.actor.testdouble {

	/*
	 * Modules used by this module
	 */
	requires bluezone.app;
	requires static lombok;
    requires bluezone.lib;

    /*
	 * Packages published to other modules
	 */
	exports io.github.hexarchbook.bluezone.driven.forobtainingdatetime.actor.testdouble;

	/*
	  Java Service Provider
	 */
	provides ForObtainingDateTime
		with StubDateTimeService;

}
