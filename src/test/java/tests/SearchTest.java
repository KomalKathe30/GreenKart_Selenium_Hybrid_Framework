package tests;

import org.testng.annotations.*;
import Listener.TestListener;
import base.BaseTest;
import keywords.KeywordEngine;
import utils.ExcelUtils;

@Listeners(TestListener.class)
public class SearchTest extends BaseTest {

    KeywordEngine engine;

    @BeforeMethod
    public void init(){
        engine = new KeywordEngine(getDriver());
    }

    @Test
    public void SearchTestCase(){
        engine.executeSteps(ExcelUtils.getTestSteps("TC1"));
    }

    @Test
    public void AddToCartTestCase(){
        engine.executeSteps(ExcelUtils.getTestSteps("TC2"));
    }

    @Test
    public void TopDealsTestCase(){
        engine.executeSteps(ExcelUtils.getTestSteps("TC3"));
    }
}