package io.github.hexarchbook.bluezone.startup;

import io.github.hexarchbook.bluezone.lib.hexagonal.DrivingActor;
import io.github.hexarchbook.bluezone.lib.javautils.FileUtils;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Entry point to the system.
 * It takes 3 args:
 *      - Path of a file specifying the adapter/actor to select for each port.
 *      - Path of a file specifying the values to init driven actors with.
 *      - The driving port whose selected adapter/actor will be run.
 */
public class BlueZoneRunner {

    private static final String LOG_FILE_KEY = "bluezone.log.file";
    private static final String LOG_FILE = System.getProperty("user.home") + System.getProperty("file.separator") + "bluezone" + System.getProperty("file.separator") + "logs" + System.getProperty("file.separator") + "bluezone.log";

    public static void main (String[] args ) {
        System.setProperty(LOG_FILE_KEY, LOG_FILE);
        System.out.println("Starting-up 'BlueZone' system... Log messages will be output to file '"+System.getProperty(LOG_FILE_KEY)+"'");
        if (args==null || args.length!=3 ) {
            System.out.println("Wrong arguments. Usage: " + BlueZoneRunner.class.getSimpleName() + " ports_adapters_file driven_actors_file driving_port_name");
            return;
        }
        Properties portsAdaptersProperties = FileUtils.propertiesFromFile ( Paths.get(args[0]) );
        Properties drivenActorsProperties = FileUtils.propertiesFromFile ( Paths.get(args[1]) );
        String drivingPortToRun = args[2];

        Configurator configurator = new Configurator(portsAdaptersProperties,drivenActorsProperties);

        DrivingActor drivingActor = configurator.buildDrivingActor(drivingPortToRun);

        drivingActor.run();

    }

}

//    public static void main (String[] args ) {
//        if (args==null || args.length!=3 ) {
//            System.out.println("Wrong arguments. Usage: " + _BlueZoneRunner.class.getSimpleName() + " ports_adapters_file driven_actors_file driving_port_name");
//            return;
//        }
//        Properties portsAdaptersProperties = FileUtils.propertiesFromFile ( Paths.get(args[0]) );
//        Properties drivenActorsProperties = FileUtils.propertiesFromFile ( Paths.get(args[1]) );
//        String drivingPortToRun = args[2];
//
//        Configurator configurator = new Configurator(portsAdaptersProperties,drivenActorsProperties);
//
//        BlueZoneApp blueZoneApp = configurator.createApp();
//
//        if ( configurator.drivingActorIsNotTest(drivingPortToRun) ) {
//            configurator.init(blueZoneApp);
//        }
//
//        DrivingActor drivingActor = configurator.buildDrivingActor(drivingPortToRun);
//
//        drivingActor.run();
//
//    }
