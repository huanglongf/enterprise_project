package com.jumbo.webservice.outsourcingWH.command;

import java.io.Serializable;

/**
 * 保存须要发送的店铺单据
 * 
 * @author lingyun.dai
 * 
 */
public class Node implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7012282256708367170L;
    private String owner;
    private Node next;

    public Node(String owner, Node next) {
        this.owner = owner;
        this.next = next;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
