# bluezone_pro

Private repo for developing the BlueZone application for the book.

------------------------------------------------------------------

# BlueZone
## An example application implementing Hexagonal Architecture

![BlueZone: Hexagonal Application Figure](bluezone.png)

__BlueZone__ allows car drivers to pay remotely for parking cars at regulated zones in a city, instead of paying with coins using parking meters.

- Users (driving actors) of the application are _car drivers_ and _parking inspectors_.

  - Car drivers will access the application using a Web UI (User Interface), and they can do the following:
    
    - Ask for the available rates in the city, in order to choose the one of the zone he wants to park the car at.
    - Buy a ticket for parking the car during a period of time at a regulated zone. This period starts at current date-time. The ending date-time is calculated from the paid amount, according to the rate of the zone.

  - Parking inspectors will access the application using a terminal with a CLI (Command Line Interface), and they can do the following:
  
    - Check whether a car is illegally parked at a zone. This will happen if there is no valid ticket for the car and the rate of the zone. A ticket is valid if current date-time is between the starting and ending date-time of the ticket period.
    
- Driven actors needed by the application are:

  - Repository with the data (rates and tickets) used in the application.

  - Payment service.

  - Date-time service.

### Development environment:

- Java 11 (version "11.0.15.1" 2022-04-22 LTS)

- Maven 3.8.6

- IntelliJ IDEA 2021.3.3 (Community Edition)

- Ubuntu 20.04.4 LTS (Linux 5.13.0-40-generic)

### Instructions:

- Download and extract this GitHub repo to a local directory on your computer ( `<bluezone_dir>` )

- Compile all modules (you need to do this just the first time before running):

    ~~~
    cd <bluezone_dir>
    ./scripts/build.sh
    ~~~

- Select the adapters to be plugged-in at each port, editing the "ports-adapters.properties" file, located in the "<bluezone_dir>/scripts" directory.


- Run the entry point to the app:

    ~~~
    cd <bluezone_dir>
    ./scripts/run_bluezone.sh
    ~~~
