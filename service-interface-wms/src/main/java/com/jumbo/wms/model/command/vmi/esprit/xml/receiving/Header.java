package com.jumbo.wms.model.command.vmi.esprit.xml.receiving;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"fromGLN", "toGLN", "fromNode", "toNode", "sequenceNumber", "numberOfRecords", "generationDate", "generationTime"})
@XmlRootElement(name = "header")
public class Header implements Serializable {



    private static final long serialVersionUID = 4138526111463440326L;
    @XmlElement(required = true)
    public String fromGLN;
    @XmlElement(required = true)
    public String toGLN;
    @XmlElement(required = true)
    public String fromNode;
    @XmlElement(required = true)
    public String toNode;
    @XmlElement(required = true)
    public String sequenceNumber;
    @XmlElement(required = true)
    public String numberOfRecords;
    @XmlElement(required = true)
    public String generationDate;
    @XmlElement(required = true)
    public String generationTime;

    public String getFromGLN() {
        return fromGLN;
    }

    public void setFromGLN(String value) {
        this.fromGLN = value;
    }

    public String getToGLN() {
        return toGLN;
    }

    public void setToGLN(String value) {
        this.toGLN = value;
    }

    public String getFromNode() {
        return fromNode;
    }

    public void setFromNode(String value) {
        this.fromNode = value;
    }

    public String getToNode() {
        return toNode;
    }

    public void setToNode(String value) {
        this.toNode = value;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String value) {
        this.sequenceNumber = value;
    }

    public String getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(String value) {
        this.numberOfRecords = value;
    }

    public String getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(String value) {
        this.generationDate = value;
    }

    public String getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(String value) {
        this.generationTime = value;
    }

}
