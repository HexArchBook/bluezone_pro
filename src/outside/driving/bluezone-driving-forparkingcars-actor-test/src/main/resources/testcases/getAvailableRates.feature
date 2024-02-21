Feature: Get available rates
  AS
  a car driver
  I WANT TO
  get the available rates
  SO THAT
  I can choose the rate of the zone I want to park the car at

Scenario: Repository with no rates
  Given there are no rates in the repository
  When I do a 'get available rates' request
  Then I should obtain no rate

Scenario: Repository with rates
  Given the existing rates in the repository are
    | name   | eurosPerHour |
    | Blue   | 0.80         |
    | Green  | 0.85         |
    | Orange | 0.70         |
  When I do a 'get available rates' request
  Then I should obtain these rates
    | name   | eurosPerHour |
    | Blue   | 0.80         |
    | Green  | 0.85         |
    | Orange | 0.70         |
