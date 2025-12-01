package dev.loat.server_management.console;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;
import org.json.JSONArray;
import org.json.JSONObject;

public class LogMessage {
    public final long timestamp;
    public final String threadName;
    public final Level logLevel;
    public final String className;
    public final Message message;
    public final long threadId;
    public final ThreadContext.ContextStack stackStraceSource;

    public LogMessage(LogEvent event) {
        this.timestamp = event.getTimeMillis();
        this.threadName = event.getThreadName();
        this.threadId = event.getThreadId();
        this.logLevel = event.getLevel();
        this.className = event.getLoggerName();
        this.message = event.getMessage();
        this.stackStraceSource = event.getContextStack();
    }

    public JSONObject toJson() {
        JSONObject data = new JSONObject();
        Object simpleClassName = JSONObject.NULL;

        try {
            simpleClassName = Class.forName(this.className).getSimpleName();
        } catch (ClassNotFoundException ignored) {}

        data.put("data",
            new JSONObject()
                .put("timestamp", this.timestamp)
                .put("threadName", this.threadName)
                .put("threadId", this.threadId)
                .put("logLevel", this.logLevel)
                .put("className", this.className)
                .put("simpleClassName", simpleClassName)
        );
        data.put("message", this.message.getFormattedMessage());
        data.put("errors", new JSONArray().put(this.stackStraceSource));

        return data;
    }
}
