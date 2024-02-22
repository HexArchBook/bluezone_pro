import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.ForPaying;
import io.github.hexarchbook.bluezone.driven.forpaying.actor.testdouble.MockPaymentService;

module bluezone.driven.forpaying.actor.testdouble {

	/*
	 * Modules used by this module
	 */
	requires bluezone.app;
	requires static lombok;
    requires bluezone.lib;

    /*
	 * Packages published to other modules
	 */
	exports io.github.hexarchbook.bluezone.driven.forpaying.actor.testdouble;

	/*
	 * Java Service Provider
	 */
	provides ForPaying
		with MockPaymentService;

}
