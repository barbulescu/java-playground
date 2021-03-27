package com.barbulescu.junit;

import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

public class CustomExecutionListener implements TestExecutionListener {
    @Override
    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
        System.out.println("reported ----> " + entry.getKeyValuePairs());
    }
}
