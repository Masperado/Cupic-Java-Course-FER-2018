package hr.fer.zemris.java.hw17.rest;

import com.google.gson.Gson;
import hr.fer.zemris.java.hw17.Picture;
import hr.fer.zemris.java.hw17.PictureDB;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class represents REST points for our web application.
 */
@Path("/gallery")
public class GalleryJSON {

    /**
     * Servlet context.
     */
    @Context
    private ServletContext context;

    /**
     * This method is used for getting all available tags.
     *
     * @return {@link Response} containing tags
     */
    @GET
    @Produces("application/json")
    public Response getTags() {
        List<String> tags = PictureDB.getTags();

        Gson gson = new Gson();

        String jsonText = gson.toJson(tags);

        return Response.status(Status.OK).entity(jsonText).build();
    }

    /**
     * This method is used for getting thumbnails for given tag.
     *
     * @param tag Tag
     * @return {@link Response} containing thumbnails
     * @throws IOException Input output exception
     */
    @Path("{tag}")
    @GET
    @Produces("application/json")
    public Response getThumbnailForTag(@PathParam("tag") String tag) throws IOException {

        java.nio.file.Path thumbNailFolder = Paths.get(context.getRealPath("/WEB-INF/thumbnails"));

        if (!Files.exists(thumbNailFolder)) {
            Files.createDirectory(thumbNailFolder);
        }

        List<Picture> thumbnails = PictureDB.forTag(tag);

        for (Picture picture : thumbnails) {
            java.nio.file.Path fullPath = Paths.get(context.getRealPath("/WEB-INF/slike"), picture.getPath());
            java.nio.file.Path thumbPath = Paths.get(context.getRealPath("/WEB-INF/thumbnails"), picture.getPath());

            if (!Files.exists(thumbPath)) {
                BufferedImage bi = ImageIO.read(fullPath.toFile());
                BufferedImage img = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
                img.createGraphics().drawImage(bi.getScaledInstance(150, 150, Image.SCALE_SMOOTH), 0, 0, null);
                ImageIO.write(img, "jpg", thumbPath.toFile());
            }
        }


        Gson gson = new Gson();
        String jsonText = gson.toJson(thumbnails);

        return Response.status(Status.OK).entity(jsonText).build();

    }

    /**
     * This method is used for getting picture for given path.
     *
     * @param path Path
     * @return {@link Response} containing picture
     */
    @Path("/picture/{path}")
    @GET
    @Produces("application/json")
    public Response getPictureForPath(@PathParam("path") String path) {

        Picture picture = PictureDB.forPath(path);

        Gson gson = new Gson();
        String jsonText = gson.toJson(picture);

        return Response.status(Status.OK).entity(jsonText).build();

    }

}
