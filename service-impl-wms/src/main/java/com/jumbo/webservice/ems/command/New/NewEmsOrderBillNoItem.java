package com.jumbo.webservice.ems.command.New;

import java.util.List;

public class NewEmsOrderBillNoItem {

    private String requestno;
    private int count;
    private List<String> mailnums;

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getMailnums() {
        return mailnums;
    }

    public void setMailnums(List<String> mailnums) {
        this.mailnums = mailnums;
    }



}
