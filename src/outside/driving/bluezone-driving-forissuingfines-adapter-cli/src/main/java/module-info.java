module bluezone.driving.forissuingfines.adapter.cli {

	/*
	 * Modules used by this module
	 */
	requires bluezone.app;
	requires static lombok;
    requires bluezone.lib;
	requires org.slf4j;

    /*
	 * Packages published to other modules
	 */
	exports io.github.hexarchbook.bluezone.driving.forissuingfines.adapter.cli;

}
