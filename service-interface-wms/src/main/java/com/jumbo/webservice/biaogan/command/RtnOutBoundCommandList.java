package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "outputBacks")
public class RtnOutBoundCommandList implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4671767700309005880L;

    public List<RtnOutBoundCommand> outputBack;

    public List<RtnOutBoundCommand> getOutputBack() {
        return outputBack;
    }

    public void setOutputBack(List<RtnOutBoundCommand> outputBack) {
        this.outputBack = outputBack;
    }


}
