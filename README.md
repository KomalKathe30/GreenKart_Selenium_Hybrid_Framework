# GreenKart Selenium Hybrid Automation Framework

A production-style **Hybrid Test Automation Framework** for web UI testing using:

- **Selenium WebDriver**
- **TestNG**
- **Maven**
- **Page Object Model (POM)**
- **Keyword-driven execution**
- **Excel data-driven test steps**
- **ExtentReports + screenshots**
- **Parallel test execution (ThreadLocal-safe)**

---

## 1) Project Objective

This project automates the GreenKart demo application and demonstrates how to build a framework that is:

- Easy to maintain when UI changes
- Easy to scale with new test cases
- Friendly for non-developers to define steps in Excel
- Ready for CI/CD execution with parallel support

Application under test:
- `https://rahulshettyacademy.com/seleniumPractise/#/`

---

## 2) Framework Type

This is a **Hybrid Framework** combining:

1. **POM** (`pages.HomePage`) to encapsulate locators and actions.
2. **Keyword-driven engine** (`keywords.KeywordEngine`) to interpret actions from test data.
3. **Data-driven design** (`utils.ExcelUtils`) reading test steps from `data/testdata.xlsx`.
4. **TestNG orchestration** (`tests.SearchTest`, listeners, suite XML).

---

## 3) High-Level Architecture

```text
TestNG @Test
   ↓
SearchTest
   ↓
ExcelUtils.getTestSteps("TCx")
   ↓
KeywordEngine.executeSteps(...)
   ↓
HomePage action methods (Selenium WebDriver)
   ↓
TestListener (Extent report + screenshots)
```

Parallel handling:
- `ThreadLocal<WebDriver>` in `BaseTest`
- `ThreadLocal<ExtentTest>` in `TestListener`

This design prevents test-thread collisions during parallel runs.

---

## 4) Project Structure

```text
src/
├── main/java
│   ├── keywords/
│   │   └── KeywordEngine.java
│   ├── pages/
│   │   └── HomePage.java
│   └── utils/
│       ├── ExcelUtils.java
│       ├── ExtentManager.java
│       ├── ScreenshotUtils.java
│       └── WaitUtils.java
└── test/java
    ├── Listener/
    │   └── TestListener.java
    ├── base/
    │   └── BaseTest.java
    └── tests/
        └── SearchTest.java

data/
└── testdata.xlsx

pom.xml
testng.xml
README.md
PROJECT_DEEP_DIVE.md
PROJECT_CODE_WALKTHROUGH.md
PROJECT_CODE_WALKTHROUGH.pdf
```

---

## 5) Core Components Explained

### `base.BaseTest`
- Creates and manages browser per method using TestNG lifecycle hooks.
- Uses `ThreadLocal<WebDriver>` for thread-safe parallel execution.
- Opens base URL and maximizes browser.

### `tests.SearchTest`
- Contains scenario-level tests:
  - `SearchTestCase`
  - `AddToCartTestCase`
  - `TopDealsTestCase`
- Each test maps to a test case key in Excel (`TC1`, `TC2`, `TC3`).

### `keywords.KeywordEngine`
- Interprets keyword + data rows and dispatches them to page methods.
- Supported keywords include:
  - `searchItem`
  - `clickAddToCart`
  - `clickTopDeals`
  - `switchWindow`
  - `selectDropdown`

### `pages.HomePage`
- Stores locators and encapsulates browser actions.
- Uses explicit waits for robustness.
- Handles:
  - Search input
  - Add to cart by dynamic XPath
  - Top deals navigation
  - Child-window switching
  - Dropdown selection

### `utils.ExcelUtils`
- Reads `data/testdata.xlsx` and builds a list of executable steps.
- Handles empty rows and numeric/text data normalization.

### `Listener.TestListener`
- TestNG listener for:
  - Start/success/failure/skip hooks
  - Screenshot capture
  - Extent report status logging
  - Report flush on suite finish

### `utils.ExtentManager` and `utils.ScreenshotUtils`
- Build HTML report at `reports/index.html`
- Capture timestamped screenshots in `/screenshots`

---

## 6) OOP Concepts Demonstrated

- **Encapsulation**: page internals hidden behind methods in `HomePage`.
- **Inheritance**: `SearchTest extends BaseTest`.
- **Abstraction**: tests call keywords instead of raw Selenium statements.
- **Polymorphism**: `TestListener` implements and overrides `ITestListener` methods.

---

## 7) Selenium Concepts Demonstrated

- WebDriver lifecycle management
- Locator strategies: className, cssSelector, id, linkText, xpath
- Explicit waits (`WebDriverWait`, `ExpectedConditions`)
- Multi-window switching
- Dropdown interaction using `Select`
- Screenshot capture using `TakesScreenshot`

---

## 8) TestNG Concepts Demonstrated

- `@BeforeMethod`, `@AfterMethod`, `@Test`, `@Listeners`
- Method-level parallel execution (`parallel="methods"`, `thread-count="3"`)
- Listener-based reporting and evidence capture

---

## 9) Maven Dependencies

Main libraries:
- `selenium-java`
- `testng`
- `webdrivermanager`
- `extentreports`
- `poi-ooxml`
- `log4j-api`, `log4j-core`
- `lombok` (provided)

Build file: `pom.xml`

---

## 10) How to Run

### Prerequisites
- Java 8+ (recommended modern LTS JDK)
- Maven
- Chrome browser

### Run all tests
```bash
mvn test
```

### Run with TestNG suite
```bash
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

---

## 11) Reports & Artifacts

After execution:
- Extent report: `reports/index.html`
- Screenshots: `screenshots/*.png`

---

## 12) CI/CD (Jenkins-Ready Flow)

1. Checkout repository
2. Setup JDK + Maven
3. Execute test suite
4. Publish `reports/index.html`
5. Archive screenshots
6. Notify result

> You can add a `Jenkinsfile` for full pipeline-as-code automation.

---

## 13) Design Strengths

- Clear separation of concerns
- Parallel-ready thread isolation
- Reusable page and keyword layers
- Data externalization via Excel
- Built-in reporting and screenshots

---

## 14) Recommended Enhancements (SDET-level)

- External config management (URL/browser/timeout/env)
- Assertion layer improvements
- Enum-based keyword contracts (avoid typo-based dispatch)
- Enhanced logging and failure diagnostics
- CI pipeline definition (`Jenkinsfile`)
- Browser matrix + headless execution profiles

---

## 15) Additional Documentation

- `PROJECT_DEEP_DIVE.md`: concise architecture summary
- `PROJECT_CODE_WALKTHROUGH.md`: deep class-by-class walkthrough
- `PROJECT_CODE_WALKTHROUGH.pdf`: portable PDF version of walkthrough

---

## 16) Author Note

This project is intentionally structured to demonstrate **real-world automation engineering principles** expected from an SDET role: modular architecture, test scalability, data abstraction, and CI-ready reporting.
