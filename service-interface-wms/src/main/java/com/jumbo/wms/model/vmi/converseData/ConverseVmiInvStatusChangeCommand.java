package com.jumbo.wms.model.vmi.converseData;


public class ConverseVmiInvStatusChangeCommand extends ConverseVmiInvStatusChange {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8460359171601189788L;

    private String orgi;

    private String dest;

    public String getOrgi() {
        return orgi;
    }

    public void setOrgi(String orgi) {
        this.orgi = orgi;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

}
