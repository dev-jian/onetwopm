package me.devjian.onetwopm.service;

public interface MailService {

    void sendSimpleEmail(String to, String subject, String body);
}
