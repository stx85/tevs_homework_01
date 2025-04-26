package fh.bswe.statusserver.helper;


import com.netflix.appinfo.InstanceInfo;
import org.springframework.stereotype.Component;
import com.netflix.appinfo.ApplicationInfoManager;
import jakarta.annotation.PostConstruct;

@Component
public class EurekaRegistrationUpdater {

    private final ServerPortListener serverPortListener;

    private final ApplicationInfoManager applicationInfoManager;

    public EurekaRegistrationUpdater(ServerPortListener serverPortListener, ApplicationInfoManager applicationInfoManager) {
        this.serverPortListener = serverPortListener;
        this.applicationInfoManager = applicationInfoManager;

        this.serverPortListener.setOnPortReadyCallback(this::updateEurekaRegistration);
    }

    private void updateEurekaRegistration(int port) {
        System.out.println("Update Eureka Registrierung mit Port: " + port);

        InstanceInfo instanceInfo = applicationInfoManager.getInfo();
        InstanceInfo.Builder builder = new InstanceInfo.Builder(instanceInfo)
                .setPort(port);

        applicationInfoManager.registerAppMetadata(builder.build().getMetadata());
    }
}
