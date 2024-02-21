module bluezone.driving.forparkingcars.actor.test {

	/**
	 * Modules used by this module
	 */
	requires bluezone.app;
	requires io.cucumber.java;
	requires io.cucumber.datatable;
	requires io.cucumber.core;
	requires org.hamcrest;
	requires static lombok;
    requires bluezone.lib;

    /**
	 * Packages published to other modules
	 */
	exports io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test;
	opens	io.github.hexarchbook.bluezone.driving.forparkingcars.actor.test.stepdefs
	to		io.cucumber.java,
			picocontainer;

}
