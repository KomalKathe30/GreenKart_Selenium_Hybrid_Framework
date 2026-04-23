package keywords;

import org.openqa.selenium.WebDriver;
import pages.HomePage;

import java.util.List;

public class KeywordEngine {

    WebDriver driver;
    HomePage home;

    // Constructor
    public KeywordEngine(WebDriver driver){
        this.driver = driver;
        this.home = new HomePage(driver);
    }

    // ✅ Single step execution
    public void perform(String keyword, String data){

        switch(keyword){

            case "searchItem":
                home.searchItem(data);
                break;

            case "clickAddToCart":
                home.clickAddToCart(data);
                break;

            case "clickTopDeals":
                home.clickTopDeals();
                break;

            case "switchWindow":
                home.switchToChildWindow();
                break;

            case "selectDropdown":
                home.selectTopDealsDropdown(data);
                break;

            default:
                System.out.println("❌ Invalid keyword: " + keyword);
        }
    }

    // ✅ Execute full test steps from Excel
    public void executeSteps(List<String[]> steps){

        for(String[] step : steps){

            String keyword = step[0];
            String data = step[1];

            System.out.println("➡ Executing: " + keyword + " | Data: " + data);

            perform(keyword, data);
        }
    }
}