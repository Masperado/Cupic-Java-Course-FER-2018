package hr.fer.zemris.java.hw17;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents PictureDB. It is used as a simple database for pictures.
 */
@WebListener
public class PictureDB implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initializePictures(servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    /**
     * List of pictures.
     */
    private static List<Picture> listOfPictures = new ArrayList<>();

    /**
     * This method is used for initializing list of pictures.
     *
     * @param context {@link ServletContext}
     */
    private void initializePictures(ServletContext context) {

        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(context.getRealPath("/WEB-INF/opisnik.txt")));
        } catch (IOException e) {
            System.out.println("Ne mogu učitati slike!");
            System.exit(0);
        }

        for (int i = 0; i < lines.size(); i = i + 3) {
            String path = lines.get(i);
            String name = lines.get(i + 1);
            String[] tags = lines.get(i + 2).split(" ");
            listOfPictures.add(new Picture(path, name, tags));
        }

    }

    /**
     * This method is used for getting all available tags.
     *
     * @return List of all tags.
     */
    public static List<String> getTags() {
        List<String> tagList = new ArrayList<>();

        for (Picture picture : listOfPictures) {
            String[] tags = picture.getTags();
            for (String tag : tags) {
                if (!tagList.contains(tag)) {
                    tagList.add(tag);
                }
            }

        }

        tagList.sort((o1, o2) -> (o1.compareTo(o2)));

        return tagList;
    }

    /**
     * This method is used for getting all pictures with specific tag.
     *
     * @param tag tag
     * @return List of pictures with specific tag
     */
    public static List<Picture> forTag(String tag) {
        List<Picture> tagged = new ArrayList<>();

        for (Picture picture : listOfPictures) {
            String[] pictureTags = picture.getTags();
            for (String s : pictureTags) {
                if (s.equals(tag)) {
                    tagged.add(picture);
                    break;
                }
            }
        }

        return tagged;
    }

    /**
     * This method is used for getting pictures with given path.
     *
     * @param path path
     * @return picture
     */
    public static Picture forPath(String path) {
        for (Picture picture : listOfPictures) {
            if (picture.getPath().equals(path)) {
                return picture;
            }
        }
        return null;
    }

}