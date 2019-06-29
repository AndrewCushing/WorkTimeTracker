package Businessware;

public class Config {

    public final static String LOG_FILE_PATH = "C:\\Users\\Andy\\Documents\\myTestLogs.txt";
    public final static String DBLOCATION = "jdbc:mysql://localhost:3306/work_time_tracker?serverTimezone=UTC";
    public final static int SERVERPORT = 3000;
    public final static String GET_PROJECTS_PATH = "/api/getProjects";
    public final static String ADD_ENTRY_PATH = "/api/addEntry";
    public final static String LOGIN_PATH = "/api/login";
    public final static String REGISTER_PATH = "/api/register";
    public final static String SUMMARY_PATH = "/api/summaryByProject";
    public final static String GET_ALL_ENTRIES = "/api/getAllEntries";
    public final static String UPDATE_ENTRY = "/api/updateEntry";
    public final static String DELETE_ENTRY = "/api/deleteEntry";

}
