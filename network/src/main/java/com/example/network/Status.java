package com.example.network;

public class Status {
    public static final int OK = 0; // Status code for response OK
    public static final int FALSE = 1; // Status code for response code 1
    public static final int PARTIALLY_OK = 7; // Status code when response contains partial result
    public static final int TIME_OUT = -1;
    private int code; // int - result. 0 is success.
    private String message; // string - error message on failure. otherwise success

    /**
     * Status code for response
     * @return
     */
    public int getStatusCode()
    {
        return code;
    }

    /**
     * Returns message containing error message or 'success message'
     * @return
     */
    public String getMessage()
    {
        return message;
    }

    /**
     *
     * @param code
     */
    public void setStatusCode(int code)
    {
        this.code = code;
    }

    /**
     * Set error /success message
     * @param message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() { return "{ code : " + code + " , message : " + message + " }"; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;

        Status status = (Status) o;

        if (code != status.code) return false;
        return !(message != null ? !message.equals(status.message) : status.message != null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}

