package com.jumbo.wms.exception;

import java.io.Serializable;

public class SyncException extends RuntimeException implements Serializable{
	private static final long serialVersionUID = -1759195793615247507L;
	protected String errorCode = "-1";
    protected String message = null;
    protected Throwable cause = null;

    public SyncException() {
        super();
    }

    public SyncException(String message, Throwable cause, String errCode) {
        super(message, cause);
        this.errorCode = errCode;
        this.message = message;
        this.cause = cause;
    }

    public SyncException(String message, String errCode) {
        super(message);
        this.errorCode = errCode;
        this.message = message;
    }

    public SyncException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        SyncException other = (SyncException) obj;
        if (errorCode != other.errorCode) {return false;}
        if (message == null) {
            if (other.message != null) {return false;}
        } else if (!message.equals(other.message)) {return false;}
        return true;
    }

    @Override
    public String toString() {
        return "BhException [errorCode=" + errorCode + ", message=" + message + "]";
    }

}
