package com.intellij.smartcity;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ruan0408 on 30/01/2016.
 */
public class SimulatorRunnerTest extends LightCodeInsightFixtureTestCase {

    private SimulatorRunner runner;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        runner = new SimulatorRunner();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testIsSimulatorRunning() {
        //if (myFixture == null) System.out.println("fixture is null");
        myFixture.testAction(runner);
        assertTrue(runner.isSimulationRunning());
    }
}