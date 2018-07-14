package hr.fer.zemris.java;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import hr.fer.zemris.java.model.Result;
import hr.fer.zemris.java.model.Song;

/**
 * This class represents initialization listener. It is used for initalizing database and connections to databases
 * once application is run. It also terminates connection to database once application is shutdowned.
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {


        Properties prop = new Properties();
        try {
            prop.load(sce.getServletContext().getResourceAsStream("/WEB-INF/dbsettings.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Error while loading database properties!");
        }


        if (!(prop.containsKey("host") && prop.containsKey("port") && prop.containsKey("name") && prop.containsKey
                ("user") && prop.containsKey("password"))) {
            throw new RuntimeException("Error while loading database properties!");
        }

        String dbName = prop.getProperty("name");
        String connectionURL = "jdbc:derby://" + prop.getProperty("host") + ":" + prop.getProperty("port")
                + "/" + dbName + "; user=" + prop.getProperty("user") + ";" + "password=" + prop.getProperty
                ("password") + ";create=true";

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Error while initalizing poll.", e1);
        }
        cpds.setJdbcUrl(connectionURL);


        try (Connection dbConnection = cpds.getConnection()) {
            DatabaseMetaData dbmd = dbConnection.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "%", null);
            List<String> tables = new ArrayList<>();
            while (rs.next()) {
                tables.add(rs.getString(3));
            }

            if (!tables.contains("POLLS") || !tables.contains("POLLOPTIONS")) {

                boolean containsPools = tables.contains("POLLS");
                boolean containsOptions = tables.contains("POLLOPTIONS");

                List<Result> results1;
                List<Result> results2;

                try {
                    results1 = getResults(sce, "glasanje-definicija1.txt", "glasanje-rezultati1.txt");
                    results2 = getResults(sce, "glasanje-definicija2.txt", "glasanje-rezultati2.txt");
                } catch (IOException e) {
                    throw new RuntimeException("Error while loading pool options!");
                }

                createTables(dbConnection, results1, results2, containsPools, containsOptions);
            } else if (noVotingPool(dbConnection)) {
                List<Result> results1;
                List<Result> results2;

                try {
                    results1 = getResults(sce, "glasanje-definicija1.txt", "glasanje-rezultati1.txt");
                    results2 = getResults(sce, "glasanje-definicija2.txt", "glasanje-rezultati2.txt");
                } catch (IOException e) {
                    throw new RuntimeException("Error while loading pool options!");
                }

                createVotingPool(dbConnection, results1, "Glasanje za omiljeni bend:", "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
                createVotingPool(dbConnection, results2, "Glasanje za omiljenu pjesmu:", "Od sljedećih pjesama, koji " +
                        "Vam je pjsemsa najdraži? Kliknite na link kako biste glasali!");
            }

            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Error while creating database!", ex);
        }


        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
    }

    /**
     * This method is used for creating voting pool used in this web application.
     *
     * @param con     Connection to database
     * @param results Voting choices
     * @param name    Name of pool
     * @param message Message of pool
     * @throws SQLException SQL exception
     */
    private void createVotingPool(Connection con, List<Result> results, String name, String message) throws
            SQLException {

        PreparedStatement pst = con.prepareStatement("INSERT INTO POLLS (title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, name);
        pst.setString(2, message);
        pst.executeUpdate();
        ResultSet rset = pst.getGeneratedKeys();

        rset.next();
        long poolId = rset.getLong(1);

        pst = con.prepareStatement("INSERT INTO POLLOPTIONS (optionTitle, optionLink, pollID, votesCount) values (?," +
                "?,?,?)");
        for (Result result : results) {
            pst.setString(1, result.getName());
            pst.setString(2, result.getLink());
            pst.setLong(3, poolId);
            pst.setInt(4, Integer.parseInt(result.getResult()));
            pst.executeUpdate();
        }

    }

    /**
     * This method is used for checking if there is any pool to vote for.
     *
     * @param con Connection to database
     * @return True if no pools exists, false otherwise
     * @throws SQLException SQL exception
     */
    private boolean noVotingPool(Connection con) throws SQLException {
        PreparedStatement pst = con.prepareStatement("SELECT * FROM POLLS");

        ResultSet rset = pst.executeQuery();

        return !(rset != null && rset.next());
    }

    /**
     * This method is used for creating database tables used in this application.
     *
     * @param con             Connection to database
     * @param results         Voting options 1
     * @param results2        Voting options 2
     * @param containsPools
     * @param containsOptions
     * @throws SQLException SQL exception
     */
    private void createTables(Connection con, List<Result> results, List<Result> results2, boolean containsPools, boolean containsOptions) throws SQLException {

        PreparedStatement pst;

        if (!containsPools) {
            pst = con.prepareStatement("CREATE TABLE Polls\n" +
                    " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                    " title VARCHAR(150) NOT NULL,\n" +
                    " message CLOB(2048) NOT NULL\n" +
                    ")");

            pst.executeUpdate();
        }


        if (!containsOptions) {
            pst = con.prepareStatement("CREATE TABLE PollOptions\n" +
                    " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                    " optionTitle VARCHAR(100) NOT NULL,\n" +
                    " optionLink VARCHAR(150) NOT NULL,\n" +
                    " pollID BIGINT,\n" +
                    " votesCount BIGINT,\n" +
                    " FOREIGN KEY (pollID) REFERENCES Polls(id)\n" +
                    ")");

            pst.executeUpdate();
        }


        createVotingPool(con, results, "Glasanje za omiljeni bend:", "Od sljedećih bendova, koji Vam je bend najdraži? " +
                "Kliknite na link kako biste glasali!");
        createVotingPool(con, results2, "Glasanje za omiljenu pjesmu:", "Od sljedećih pjesama, koji " +
                "Vam je pjsemsa najdraži? Kliknite na link kako biste glasali!");


    }

    /**
     * This method is used for loading voting options from voting poll.
     *
     * @param sce ServletContextEvent
     * @return List of voting options
     * @throws IOException Input Output exception
     */
    private static List<Result> getResults(ServletContextEvent sce, String definicija, String rezultat) throws
            IOException {
        String fileNameSongs = sce.getServletContext().getRealPath("/WEB-INF/" + definicija);
        List<String> lines = Files.readAllLines(Paths.get(fileNameSongs));

        List<Song> songs = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\t");
            songs.add(new Song(parts[0], parts[1], parts[2]));
        }


        String fileNameResults = sce.getServletContext().getRealPath("/WEB-INF/" + rezultat);


        if (!Files.exists(Paths.get(fileNameResults))) {
            Files.createFile(Paths.get(fileNameResults));
        }

        lines = Files.readAllLines(Paths.get(fileNameResults));

        List<Result> results = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\t");
            int result = Integer.parseInt(parts[1]);
            String name = "";
            String link = "";
            for (Song song : songs) {
                if (song.getId().equals(parts[0])) {
                    name = song.getName();
                    link = song.getLink();
                }
            }
            results.add(new Result(parts[0], name, link, String.valueOf(result)));
        }


        results.sort((o1, o2) -> o2.getResult().compareTo(o1.getResult()));

        return results;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}