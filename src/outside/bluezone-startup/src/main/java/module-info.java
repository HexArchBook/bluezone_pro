import io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata.ForStoringData;
import io.github.hexarchbook.bluezone.app.ports.driven.forpaying.ForPaying;
import io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime.ForObtainingDateTime;

module bluezone.startup {

	/*
	 * Modules used by this module
	 */
	requires bluezone.app;
	requires bluezone.driving.forparkingcars.actor.test;
	requires bluezone.driving.forparkingcars.adapter.webui;
	requires bluezone.driving.forissuingfines.actor.test;
	requires bluezone.driving.forissuingfines.adapter.cli;
	requires bluezone.driven.forstoringdata.actor.testdouble;
	requires bluezone.driven.forstoringdata.adapter.mongodb;
	requires bluezone.driven.forpaying.actor.testdouble;
	requires bluezone.driven.forobtainingdatetime.actor.testdouble;
	requires bluezone.lib;
	requires static lombok;

	/*
	 * Java Services
	 */
	uses ForStoringData;
	uses ForPaying;
	uses ForObtainingDateTime;

}
