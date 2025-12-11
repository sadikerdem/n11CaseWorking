package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.PlaywrightManager;

public class Hooks {

    @Before
    public void setUp() {
        PlaywrightManager.start();
    }

    @After
    public void tearDown() {
        PlaywrightManager.close();
    }
}