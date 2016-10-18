package ca.digitala.app1.resources;

import ca.digitala.app1.api.Saying;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Resource class that exposes hello-world endpoints.
 * @author: digitala
 */
@Api
@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    /**
     * Hello World endpoints.
     */
    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

    /**
     * all endpoint returns an Iterable.
     */
    @GET
    @Timed
    @Path("/all")
    public Iterable<Saying> sayHellos() {
        LinkedList<Saying> sayings = new LinkedList<Saying>();

        sayings.add(new Saying(counter.incrementAndGet(), "Austin Powers"));
        sayings.add(new Saying(counter.incrementAndGet(), "Dr. Evil"));
        return sayings;
    }

}