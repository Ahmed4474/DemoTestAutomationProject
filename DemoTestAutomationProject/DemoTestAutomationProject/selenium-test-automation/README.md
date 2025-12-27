\# Demo Test Automation Project

This project is a complete UI Test Automation Framework for automating
end-to-end scenarios on the Swag Labs demo application.

The framework is built using industry best practices with focus on:
readability, scalability, and maintainability.

\-\--

\## Tech Stack

\- Java 21 - Selenium WebDriver 4 - TestNG - Maven - Allure Reports -
Log4j2 - JSON Test Data - Page Object Model (POM)

\-\--

\## Project Structure

src ├── main │ ├── java │ │ └── com.swaglabs │ │ ├── drivers (Browser
initialization & ThreadLocal driver) │ │ ├── listeners (TestNG listeners
& reporting hooks) │ │ └── utils (Reusable utilities) │ └── resources │
├── allure.properties │ ├── environment.properties │ ├──
log4j2.properties │ ├── waits.properties │ └── web.properties │ ├── test
│ ├── java │ │ └── com.swaglabs │ │ ├── pages (Page Object classes) │ │
└── tests (Test scenarios) │ └── resources │ └── test-data.json │
test-outputs ├── allure-results ├── allure-report ├── Logs └──
screenshots

pom.xml README.md

\-\--

\## Application Under Test

Swag Labs https://www.saucedemo.com

\-\--

\## Supported Browsers

\- Chrome - Edge - Firefox

Browser is selected from \`web.properties\`:

browserType=Edge

\-\--

\## Pre-requisites (IMPORTANT)

Before running the project, the following must be installed and
configured:

\### Java - Java 21 must be installed - Verify: java -version

\### Maven - Maven must be installed - Verify: mvn -version

\### Browsers At least one browser must be installed: - Google Chrome -
Microsoft Edge - Mozilla Firefox

\-\--

\## WebDriver Setup (Manual -- Required for now)

Each browser requires its own WebDriver executable.

Chrome -\> ChromeDriver Edge -\> EdgeDriver Firefox -\> GeckoDriver

Steps: 1. Download the WebDriver that matches your browser version 2.
Add the driver path to your system PATH

Example (Windows): C:\\WebDrivers\\

Example (Linux / Mac): /usr/local/bin/

Note: Automatic WebDriver management will be added in future
improvements.

\-\--

\## Configuration Files

\- web.properties Controls browser type and execution mode

\- environment.properties Application URLs and static values

\- waits.properties Explicit wait configuration

\- log4j2.properties Logging configuration and file structure

\- allure.properties Allure report configuration

\-\--

\## Running the Tests

Run all tests using Maven:

mvn clean test

\-\--

\## Allure Reports

\- Results directory: test-outputs/allure-results

\- Generated report: test-outputs/allure-report

If \`openAllureAutomatically=true\`, the report will open automatically
after execution.

\-\--

\## Logging

\- Logging is implemented using Log4j2 - Log files are generated per
execution - Logs location: test-outputs/Logs

\-\--

\## Screenshots

\- Screenshots are captured automatically - Taken after each test based
on result - Attached to Allure report - Stored under:
test-outputs/screenshots

\-\--

\## Test Scenarios Covered

\- Successful login - Add product to cart - Checkout flow - End-to-end
user journey - Single user flow scenario

\-\--

\## Future Enhancements

\- Automatic WebDriver download and setup - Parallel execution - CI/CD
integration - Cross-browser execution improvements - Headless execution
enhancements

\-\--

\## Author

Ahmed Automation Test Engineer

\-\--

\## Notes

This project is designed as a clean automation framework that can be
extended and reused for real-world applications and interview
demonstrations.
