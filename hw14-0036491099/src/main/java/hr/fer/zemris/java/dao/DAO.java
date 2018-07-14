package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.Result;
import hr.fer.zemris.java.model.Song;

import java.util.List;

/**
 * This interface is used for communicating with database.
 */
public interface DAO {

    /**
     * This method is used for getting all available polls.
     *
     * @return List of polls
     */
    List<Poll> getPolls();

    /**
     * This method is used for getting all songs for given poll.
     *
     * @param pollIdLong Poll id
     * @return List of songs
     */
    List<Song> getSongs(long pollIdLong);

    /**
     * This method is used for getting all results for given poll.
     *
     * @param pollIdLong Poll id
     * @return List of results
     */
    List<Result> getResults(long pollIdLong);

    /**
     * This method is used for voting for one voting option.
     *
     * @param pollIdLong Poll id
     * @param id         Id of voting option
     */
    void voteFor(long pollIdLong, long id);
}
