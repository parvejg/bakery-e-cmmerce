package DB;import java.sql.Connection;import java.sql.DriverManager;import java.sql.PreparedStatement;import java.sql.ResultSet;import java.util.ArrayList;import java.util.concurrent.locks.Lock;import java.util.concurrent.locks.ReentrantLock;import Entity.*;import File.FileGetter;/** * @author Yaacov */public class getterDB {    private final Lock lock = new ReentrantLock();    private static Connection connect;    private static PreparedStatement preparedStatement;    private static ResultSet resultSet;    private static String TABLE_HOUSE_NAME = "folies.house";    private static String TABLE_PERMITED_VIEW_NAME = "folies.permissions_view";    private static String TABLE_HOUSE_LANGUAGE_NAME = "folies.dictionary";    private static String TABLE_USERS_NAME = "folies.users";    private static String TABLE_FOODNAME = "folies.food";    private static String DATA_BASE_USER_NAME = "root";    private static String DATA_BASE_PASSWORD_NAME = "sh123456";    ELocationKind eLocationKind = ELocationKind.WHITE;    public getterDB() {    }    /***     * Get all the user name of the users in all groups     * @return a list with all users name     */    public ArrayList<String> getUserNames() {        lock.lock();        ArrayList<String> userNames = new ArrayList<>();        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/folies?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select * from " + TABLE_USERS_NAME);            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get User-Names");            // Looping over the result user-names            while ((resultSet.next()) && (!resultSet.isClosed())) {                userNames.add(resultSet.getString("user_name"));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());            ;        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return userNames;    }    public String getUserNameById(int nUserId) {        lock.lock();        String szUserNameToReturn = null;        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select * from " + TABLE_USERS_NAME + " WHERE user_id=" + nUserId);            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get User Name for id: " + nUserId);            // Looping over the result user-names            while ((resultSet.next()) && (!resultSet.isClosed())) {                szUserNameToReturn = resultSet.getString("user_name");            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());            ;        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return szUserNameToReturn;    }    public int getUserIdByName(String szUserName) {        lock.lock();        int nUserId = -1;        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select user_id from " + TABLE_USERS_NAME + " WHERE user_name=" + "\'" + szUserName + "\'");            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get user-id by user-name");            // Looping over the result users id            while ((resultSet.next()) && (!resultSet.isClosed())) {                nUserId = resultSet.getInt("user_id");            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());            ;        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return nUserId;    }    public ArrayList<String> getEmails() {        lock.lock();        ArrayList<String> emails = new ArrayList<>();        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select * from " + TABLE_USERS_NAME);            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get Emails");            // Looping over the result user-names            while (resultSet.next()) {                emails.add(resultSet.getString("email"));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());            ;        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return emails;    }    public ArrayList<User> getUsers() {        lock.lock();        ArrayList<User> usersLst = new ArrayList<>();        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=root&password=sh123456");            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select * from " + TABLE_USERS_NAME);            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get Users");            // Looping over the result user-names            while (resultSet.next()) {                usersLst.add(new User(resultSet.getString("username"),                        null, resultSet.getString("email"), resultSet.getString("password"),                        null, null, null));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());            ;        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return usersLst;    }    public ArrayList<FoodEntity> getFoodEntitys() {        lock.lock();        ArrayList<FoodEntity> foodEntities = new ArrayList<>();        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=root&password=sh123456");            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select * from " + TABLE_FOODNAME);            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get Food Entitys");            // Looping over the result food entitys            while (resultSet.next()) {                foodEntities.add(new FoodEntity(resultSet.getInt("foodId"),                        resultSet.getString("foodName"),                        resultSet.getInt("price"),                        FoodEntity.setBoolean(resultSet.getInt("isParve")),                        FoodEntity.setBoolean(resultSet.getInt("isHalavi")),                        FoodEntity.setBoolean(resultSet.getInt("isBassari")),                        FoodEntity.setBoolean(resultSet.getInt("isVegetarian")),                        FoodEntity.setBoolean(resultSet.getInt("isVegan")),                        FoodEntity.setBoolean(resultSet.getInt("isInKeytring")),                        FoodEntity.setBoolean(resultSet.getInt("isInSucurSal")),                        resultSet.getString("descryption")));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return foodEntities;    }    public FoodEntity getFoodEntityById(int id) {        lock.lock();        FoodEntity foodEntitieToReturn = new FoodEntity();        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=root&password=sh123456");            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select * from " + TABLE_FOODNAME+ " WHERE foodId = "+id);            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get Food Entitys");            // Looping over the result food entitys            while (resultSet.next()) {                foodEntitieToReturn = new FoodEntity(resultSet.getInt("foodId"),                        resultSet.getString("foodName"),                        resultSet.getInt("price"),                        FoodEntity.setBoolean(resultSet.getInt("isParve")),                        FoodEntity.setBoolean(resultSet.getInt("isHalavi")),                        FoodEntity.setBoolean(resultSet.getInt("isBassari")),                        FoodEntity.setBoolean(resultSet.getInt("isVegetarian")),                        FoodEntity.setBoolean(resultSet.getInt("isVegan")),                        FoodEntity.setBoolean(resultSet.getInt("isInKeytring")),                        FoodEntity.setBoolean(resultSet.getInt("isInSucurSal")),                        resultSet.getString("descryption"));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return foodEntitieToReturn;    }    /***     *  Get all user information by the user - id     * @param nUserId - the id of a user     * @return a user     */    public User getUser(int nUserId) {        lock.lock();        User userToReturn = null;        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select * from " + TABLE_USERS_NAME + " WHERE user_id=\'" + nUserId + "\'");            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get User id: " + nUserId);            // Looping over the result user-names            while (resultSet.next()) {                userToReturn = new User(resultSet.getString("user_name"),                        resultSet.getString("telephone"), resultSet.getString("email"), resultSet.getString("password"),                        resultSet.getString("user_id"), resultSet.getString("permission_manager"), resultSet.getString("permission_view"));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return userToReturn;    }    // Closing the resultSet    private static void close() {        try {            if (resultSet != null) {                resultSet.close();            }            if (preparedStatement != null) {                preparedStatement.close();            }            if (connect != null) {                connect.close();            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());            ;        }    }    public Dictionary getHouseLanguageByLanguage(String szHouseLanguage) {        lock.lock();        Dictionary dictionaryToReturn = null;        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select * from " + TABLE_HOUSE_LANGUAGE_NAME + " WHERE house_language= \"" + szHouseLanguage + "\"");            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get House Language, For Language : " + szHouseLanguage);            // Looping over the result user-names            while (resultSet.next()) {                dictionaryToReturn = new Dictionary();                dictionaryToReturn.setHouseLanguage(resultSet.getString("house_language"));                dictionaryToReturn.setHouseId(resultSet.getString("house_id"));                dictionaryToReturn.setAddress(resultSet.getString("address"));                dictionaryToReturn.setState(resultSet.getString("state"));                dictionaryToReturn.setCity(resultSet.getString("city"));                dictionaryToReturn.setStreet(resultSet.getString("street"));                dictionaryToReturn.setHouseNumber(resultSet.getString("house_number"));                dictionaryToReturn.setHouseKind(resultSet.getString("house_kind"));                dictionaryToReturn.setNumberOfRooms(resultSet.getString("number_of_rooms"));                dictionaryToReturn.setNumberOfLivingRooms(resultSet.getString("number_of_living_rooms"));                dictionaryToReturn.setNumberOfKitchens(resultSet.getString("number_of_kitchens"));                dictionaryToReturn.setNumberOfBedrooms(resultSet.getString("number_of_bedrooms"));                dictionaryToReturn.setNumberOfBathrooms(resultSet.getString("number_of_bathrooms"));                dictionaryToReturn.setLocationKind(resultSet.getString("location_kind"));                dictionaryToReturn.setScore(resultSet.getString("score"));                dictionaryToReturn.setComments(resultSet.getString("comments"));                dictionaryToReturn.setPurchasePrice(resultSet.getString("purchase_price"));                dictionaryToReturn.setTreatmentFees(resultSet.getString("treatment_fees"));                dictionaryToReturn.setRenovationFeesForSale(resultSet.getString("renovation_fees_for_sale"));                dictionaryToReturn.setRenovationFeesForRenting(resultSet.getString("renovation_fees_for_renting"));                dictionaryToReturn.setGeneralHouseDetailes(resultSet.getString("general_house_details"));                dictionaryToReturn.setFinancialHouseDetailes(resultSet.getString("financial_house_details"));                dictionaryToReturn.setDiversFees(resultSet.getString("divers_fees"));                dictionaryToReturn.setUploadHouseFiles(resultSet.getString("upload_house_files"));                dictionaryToReturn.setDragOrDropFilesHere(resultSet.getString("drag_or_drop_files_here"));                dictionaryToReturn.setHouseTable(resultSet.getString("house_table"));                dictionaryToReturn.setMenu(resultSet.getString("menu"));                dictionaryToReturn.setInformation(resultSet.getString("information"));                dictionaryToReturn.setHouse(resultSet.getString("house"));                dictionaryToReturn.setManageHouses(resultSet.getString("manage_houses"));                dictionaryToReturn.setNewHouse(resultSet.getString("new_house"));                dictionaryToReturn.setCopyright(resultSet.getString("copyright"));                dictionaryToReturn.setWellcomToYourAccount(resultSet.getString("wellcom_to_your_account"));                dictionaryToReturn.setSelectAHouse(resultSet.getString("select_a_house"));                dictionaryToReturn.setAreaOnTheMap(resultSet.getString("area_on_the_map"));                dictionaryToReturn.setHousesList(resultSet.getString("houses_list"));                dictionaryToReturn.setSave(resultSet.getString("save"));                dictionaryToReturn.setSendMeThisHouseToMyMail(resultSet.getString("send_me_this_house_to_my_mail"));                dictionaryToReturn.setPageDirection(resultSet.getString("direction_html"));                dictionaryToReturn.setLogout(resultSet.getString("logout"));                dictionaryToReturn.setSystemManager(resultSet.getString("system_manager"));                dictionaryToReturn.setHouseProfilePicture(resultSet.getString("upload_house_profile_picture"));                dictionaryToReturn.setUploadHousePictures(resultSet.getString("upload_house_pictures"));                dictionaryToReturn.setBackToTheCatalog(resultSet.getString("back_to_the_catalog"));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return dictionaryToReturn;    }    public ArrayList<String> getListOFExistingLanguage() {        lock.lock();        FileGetter fileGetter = new FileGetter();        DATA_BASE_PASSWORD_NAME = fileGetter.getDataBasePassword();        ArrayList<String> lstExistingLanguageToReturn = new ArrayList<>();        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("select * from " + TABLE_HOUSE_LANGUAGE_NAME);            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get List Of Existing Language");            // Looping over the result user-names            while (resultSet.next()) {                lstExistingLanguageToReturn.add(resultSet.getString("house_language"));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return lstExistingLanguageToReturn;    }    public ArrayList<House> getHouseesAdressPermitedByUserId(int nUserId) {        ArrayList<House> lstHousesAdress = new ArrayList<>();        lock.lock();        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("SELECT house_id,state,city,street,house_number FROM " + TABLE_HOUSE_NAME + " WHERE house_id in (SELECT house_id FROM " + TABLE_PERMITED_VIEW_NAME + " WHERE user_id = " + nUserId + ") ORDER BY house_id");            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get list of permited house for user id : " + nUserId);            // Looping over the result houses            while (resultSet.next()) {                lstHousesAdress.add(new House(resultSet.getInt("house_id"), resultSet.getString("state"), resultSet.getString("city"), resultSet.getString("street"), resultSet.getInt("house_number")));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return lstHousesAdress;    }    public ArrayList<Integer> getListOfPermitedToView() {        ArrayList<Integer> permissionsViews = new ArrayList<>();        lock.lock();        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("SELECT * FROM " + TABLE_PERMITED_VIEW_NAME);            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get list of permited to view");            // Looping over the result user-names            while (resultSet.next()) {                permissionsViews.add(resultSet.getInt("user_id"));            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return permissionsViews;    }    public boolean getisPermitedToViewDocumentForThisHouse(int nUserId, int nHouseId) {        boolean bIsPermissionFoundedToReturn = false;        lock.lock();        try {            Class.forName("com.mysql.jdbc.Driver").newInstance();            connect = DriverManager.getConnection("jdbc:mysql://localhost/shamayim?user=" + DATA_BASE_USER_NAME                    + "&password=" + DATA_BASE_PASSWORD_NAME);            // Statements allow to issue SQL queries to the database            preparedStatement = connect.prepareStatement("SELECT * FROM " + TABLE_PERMITED_VIEW_NAME + " WHERE user_id = " + nUserId + " AND house_id=" + nHouseId);            // Result set get the result of the SQL query            resultSet = preparedStatement.executeQuery();            // INFO            play.Logger.info("<DATA_BASE> Get if permited view house documents for user id : " + nUserId + " And House-Id : " + nHouseId);            // Looping over the result houses            while (resultSet.next()) {                bIsPermissionFoundedToReturn = true;            }        } catch (Exception e) {            e.printStackTrace();            play.Logger.error(e.getMessage());        } finally {            // Closing the resultSet            close();        }        lock.unlock();        return bIsPermissionFoundedToReturn;    }}