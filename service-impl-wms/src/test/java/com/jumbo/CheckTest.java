package com.jumbo;

import com.jumbo.wms.webservice.client.PdaClient;
import com.jumbo.wms.webservice.pda.AuthenticationFailedException;
import com.jumbo.wms.webservice.pda.LoginResponse;

public class CheckTest {
    public static void main(String[] args) throws AuthenticationFailedException {
        String userName = "jingkai.sun";
        String passWord = "";

        System.out.println("check pda login start");
        LoginResponse rs = PdaClient.login(userName, passWord);
        try {
            if ("1".equals(rs.getBaseResponseBody().getStatus())) {
                System.out.println("login success");
            } else {
                System.out.println("login failed");
            }
        } catch (Exception e) {
            System.out.println("pda login error");
        }
        System.out.println("check pda login end");
    }
}
