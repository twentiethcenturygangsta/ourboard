package com.twentiethcenturygangsta.jamboard.auth;

public interface JamBoardCredentials {
    /**
     * Returns the Database Endpoint for this credentials object.
     *
     * @return The Database Endpoint for this credentials object.
     */
    public String getUserDatabaseEndpoint();

    /**
     * Returns the Database User id for this credentials object.
     *
     * @return The Database User id for this credentials object.
     */
    public String getUserDatabaseId();

    /**
     * Returns the Database User password for this credentials object.
     *
     * @return The Database User password for this credentials object.
     */
    public String getUserDatabasePassword();
}
