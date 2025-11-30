package dev.loat.server_management.config.files;

import dev.loat.server_management.config.components.Level;
import dev.loat.server_management.config.parser.annotation.Comment;

public class ServerManagementConfigFile {
    @Comment("The Port to use for the API server.")
    public int port = 8080;

    @Comment("""
    The LogLevel to use for the API server.
    
    Possible Values:
    - "DEBUG"
    - "INFO"
    - "WARN"
    - "ERROR"
    """)
    public String logLevel = Level.INFO;
}
