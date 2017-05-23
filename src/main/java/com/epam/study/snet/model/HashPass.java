package com.epam.study.snet.model;

import org.apache.commons.codec.digest.DigestUtils;

public class HashPass {
    String hash;

    public String getHash(String pass) {
        return DigestUtils.md5Hex(pass);
    }
}
