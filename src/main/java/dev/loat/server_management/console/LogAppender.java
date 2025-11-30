package dev.loat.server_management.console;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LogAppender extends AbstractAppender {

    public LogAppender() {
        super(
            "ServerManagementLogAppender",
            null,
            PatternLayout.createDefaultLayout(),
            false,
            null
        );

    }

    @Override
    public void append(LogEvent event) {

    }
}
