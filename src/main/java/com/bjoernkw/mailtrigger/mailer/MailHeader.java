package com.bjoernkw.mailtrigger.mailer;

public class MailHeader {

    String recipient;
    String from;
    String cc;
    String bcc;
    String subject;

    MailHeader(String recipient) {
        this.recipient = recipient;
    }

    public MailHeader from(String from) {
        this.from = from;
        return this;
    }

    public MailHeader cc(String cc) {
        this.cc = cc;
        return this;
    }

    public MailHeader bcc(String bcc) {
        this.bcc = bcc;
        return this;
    }

    public MailHeader subject(String subject) {
        this.subject = subject;
        return this;
    }
}
