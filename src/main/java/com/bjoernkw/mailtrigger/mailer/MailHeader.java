package com.bjoernkw.mailtrigger.mailer;

public class MailHeader {

    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return String.format(
                "MailHeader[from=%s, to=%s, cc=%s, bcc=%s, subject=%s]",
                from, to, cc, bcc, subject
        );
    }
}
