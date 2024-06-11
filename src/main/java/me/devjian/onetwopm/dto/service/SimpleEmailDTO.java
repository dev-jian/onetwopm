package me.devjian.onetwopm.dto.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SimpleEmailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String to;

    private String subject;

    private String body;
}
