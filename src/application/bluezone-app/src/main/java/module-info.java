/**
 * Module for the business logic
 * The application (Hexagon)
 */
module bluezone.app {
	/**
	 * Modules used by this module.
	 * "static": Module used at compile time, not needed at runtime.
	 */
	requires bluezone.lib;
	requires static lombok;
	requires org.slf4j;

	/**
	 * Packages published to other modules
	*/
	exports io.github.hexarchbook.bluezone.app.ports;
	// driving ports
	exports io.github.hexarchbook.bluezone.app.ports.driving;
	exports io.github.hexarchbook.bluezone.app.ports.driving.forparkingcars;
	exports io.github.hexarchbook.bluezone.app.ports.driving.forissuingfines;
	exports io.github.hexarchbook.bluezone.app.ports.driving.foradministering;
	// driven ports
	exports io.github.hexarchbook.bluezone.app.ports.driven.forstoringdata;
	exports io.github.hexarchbook.bluezone.app.ports.driven.forpaying;
	exports io.github.hexarchbook.bluezone.app.ports.driven.forobtainingdatetime;

}
