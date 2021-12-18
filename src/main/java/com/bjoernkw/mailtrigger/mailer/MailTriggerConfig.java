package com.bjoernkw.mailtrigger.mailer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailTriggerConfig {

    @Value("${mailTrigger.host}")
    private String host;

    @Value("${mailTrigger.port}")
    private Integer port = 2525;

    @Value("${mailTrigger.username}")
    private String username;

    @Value("${mailTrigger.password}")
    private String password;

    @Value("${mailTrigger.mail-template-directory}")
    private String mailTemplateDirectory;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailTemplateDirectory() {
        return mailTemplateDirectory;
    }

    public void setMailTemplateDirectory(String mailTemplateDirectory) {
        this.mailTemplateDirectory = mailTemplateDirectory;
    }

    @Override
    public String toString() {
        return String.format(
                "MailTriggerConfig[host=%s, port=%s, username=%s, mailTemplateDirectory=%s]",
                host, port, username, mailTemplateDirectory
        );
    }
}
