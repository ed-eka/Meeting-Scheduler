package com.metrodata.clientapp.util;

import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BasicHeader {

    public static String createBasicToken(String username, String password) {
        String token = username + ":" + password;
        byte[] encode = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return new String(encode);
    }

    // public static HttpHeaders createHeaders() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     String token = createBasicToken(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
    //     return new HttpHeaders() {{
    //         set("Authorization", "Basic " + token);
    //     }};
    // }
}
