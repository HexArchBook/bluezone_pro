open module bluezone.driving.forparkingcars.adapter.webui {
// Opened because Thymeleaf needs access to templates resources at runtime

	/*
	 * Modules used by this module
	 */
	requires bluezone.app;
	// Common libs
	requires bluezone.lib;
	requires lombok;
	requires org.slf4j;
	// Specific adapter dependencies
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.boot.starter.web;
	requires spring.boot.starter.thymeleaf;
	requires spring.core;
	requires spring.context;
	requires spring.beans;
	requires spring.web;
    requires spring.webmvc;
	requires thymeleaf;
	requires thymeleaf.extras.java8time;
	requires thymeleaf.spring5;
	requires org.apache.tomcat.embed.core;
	requires bootstrap;

	/*
	 * Packages published to other modules
	 */
	exports io.github.hexarchbook.bluezone.driving.forparkingcars.adapter.webui;

}
