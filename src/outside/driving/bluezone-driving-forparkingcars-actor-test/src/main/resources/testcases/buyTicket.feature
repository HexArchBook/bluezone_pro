Feature: Buy ticket
  AS
  a car driver
  I WANT TO
  buy a ticket
  SO THAT
  I can park the car without being fined

  Scenario: Ticket valid for 2 hours
    Given the existing rates in the repository are
      | name  | eurosPerHour |
      | Green | 0.85         |
    And next ticket code is "1234567890"
    And current date-time is "2024/01/02 17:00"
    And no error occurs while paying
    When this 'buy ticket' request is done
      | carPlate | rateName | euros | card                        |
      | 6989GPJ  | Green    | 1.70  | 1234567890123456-123-062027 |
    Then this 'pay' request should have been done
      | euros | card                        |
      | 1.70  | 1234567890123456-123-062027 |
    And this ticket with the 'pay' response as 'payment id' should have been stored
      | ticketCode | carPlate | rateName | startingDateTime | endingDateTime   | price |
      | 1234567890 | 6989GPJ  | Green    | 2024/01/02 17:00 | 2024/01/02 19:00 | 1.70  |
    And the 'buy ticket' response should be the ticket stored with code "1234567890"

  Scenario: Error while paying
    Given the existing rates in the repository are
      | name   | eurosPerHour |
      | Orange | 0.75         |
    And next ticket code is "9876543210"
    And current date-time is "2024/01/03 10:30"
    And an error occurs while paying
    When this 'buy ticket' request is done
      | carPlate | rateName | euros | card                        |
      | 8755KTV  | Orange   | 0.75  | 1111222233334444-555-042028 |
    Then this 'pay' request should have been done
      | euros | card                        |
      | 0.75  | 1111222233334444-555-042028 |
    And a PayErrorException with the error code that occurred should have been thrown
    And no ticket with code "9876543210" should have been stored
