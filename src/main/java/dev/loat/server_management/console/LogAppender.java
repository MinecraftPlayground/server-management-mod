package dev.loat.server_management.console;

import dev.loat.server_management.api.ApiServer;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LogAppender extends AbstractAppender {

    private final ApiServer apiServer;

    public LogAppender(ApiServer apiServer) {
        super(
            "ServerManagementLogAppender",
            null,
            PatternLayout.createDefaultLayout(),
            false,
            null
        );
        this.apiServer = apiServer;
    }

    @Override
    public void append(LogEvent event) {
        this.apiServer.broadcast(new LogMessage(event));
    }
}
