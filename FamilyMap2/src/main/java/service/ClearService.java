package service;

import dao.*;
import request.ClearRequest;
import response.ClearResponse;

import java.sql.Connection;

public class ClearService {
    private AuthTokenDao authTokenDao;
    private EventDao eventDao;
    private PersonDao personDao;
    private UserDao userDao;
    private Database db;
    private ClearResponse response;

    /**
     * Empty constructor
     */
    public ClearService() {
        response = new ClearResponse();
    }

    /**
     * Delete ALL data from the database, including user accounts, auth tokens, and
     * generated person and event data.
     * @return ClearResponse object as response
     */
    public ClearResponse clear(ClearRequest request) {
        try {
            // Connect and make a new Dao
            db = new Database();
            Connection conn = db.openConnection();

            authTokenDao = new AuthTokenDao(conn);
            eventDao = new EventDao(conn);
            personDao = new PersonDao(conn);
            userDao = new UserDao(conn);

            // Delete all information from the database
            authTokenDao.deleteAllAuthTokens();
            eventDao.deleteAllEvents();
            personDao.deleteAllPersons();
            userDao.deleteAllUsers();

            db.closeConnection(true);
            response.setMessage("Clear succeeded.");
            response.setSuccess(true);
            return response;
        }
        catch (Exception e) {
            System.out.println(e.toString());
            try {
                db.closeConnection(false);
            }
            catch (Exception error) {
                System.out.println(error.toString());
            }
            response.setMessage(e.toString());
            response.setSuccess(false);
            return response;
        }
    }
}
