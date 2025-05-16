package fh.bswe.statusserver.helper.portupdate;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ServerPortListener {

    private int port;
    private Consumer<Integer> onPortReadyCallback;

    @EventListener
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
        System.out.println("Tatsächlich zugewiesener Port: " + port);

        if (onPortReadyCallback != null) {
            onPortReadyCallback.accept(port);
        }
    }

    public int getPort() {
        return port;
    }

    public void setOnPortReadyCallback(Consumer<Integer> callback) {
        this.onPortReadyCallback = callback;
    }
}
