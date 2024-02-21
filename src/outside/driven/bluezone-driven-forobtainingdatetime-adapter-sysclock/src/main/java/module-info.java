import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;
import io.github.hexarchbook.bluezone.driven.forobtainingdatetime.adapter.sysclock.SysDateTimeService;

module bluezone.driven.forobtainingdatetime.adapter.sysclock {

	/*
	 * Modules used by this module
	 */
	requires bluezone.app;
	requires static lombok;
    requires bluezone.lib;

    /*
	 * Packages published to other modules
	 */
	exports io.github.hexarchbook.bluezone.driven.forobtainingdatetime.adapter.sysclock;

	/*
	  Java Service Provider
	 */
	provides ForObtainingDateTime
		with SysDateTimeService;

}
