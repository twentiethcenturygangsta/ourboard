package com.twentiethcenturygangsta.ourboard.auth;


/**
 * credentials to receive database setting values for using OurBoard library
 *
 * @author oereo
 * @version 1.0.0
 */
public class UserDatabaseCredentials implements OurBoardCredentials {
    private final String databaseEndPoint;
    private final String databaseId;
    private final String databasePassword;

    /**
     * Constructs a new UserDatabaseCredentials object, with the specified user database
     * endpoint, id and password.
     *
     * @param databaseEndPoint
     *            The user database endpoint.
     * @param databaseId
     *            The user database id.
     * @param databasePassword
     *            The user database password.
     *
     * @exception IllegalArgumentException when there is no value among databaseEndPoint, databaseId, databasePassword
     */
    public UserDatabaseCredentials(String databaseEndPoint, String databaseId, String databasePassword) {
        if (databaseEndPoint == null) {
            throw new IllegalArgumentException("Database endpoint cannot be null.");
        }
        if (databaseId == null) {
            throw new IllegalArgumentException("Database id cannot be null.");
        }
        if (databasePassword == null) {
            throw new IllegalArgumentException("Database password cannot be null.");
        }

        this.databaseEndPoint = databaseEndPoint;
        this.databaseId = databaseId;
        this.databasePassword = databasePassword;
    }

    @Override
    public String getUserDatabaseEndpoint() {
        return databaseEndPoint;
    }

    @Override
    public String getUserDatabaseId() {
        return databaseId;
    }

    @Override
    public String getUserDatabasePassword() {
        return databasePassword;
    }
}
