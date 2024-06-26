package burp;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import com.cys4.sensitivediscoverer.MainUI;
import com.cys4.sensitivediscoverer.utils.Utils;

public class BurpExtender implements BurpExtension {
    @Override
    public void initialize(MontoyaApi burpApi) {
        try {
            MainUI mainUI = new MainUI(burpApi);
            mainUI.initializeUI();

            burpApi.extension().setName(mainUI.getExtensionName());
            burpApi.extension().registerUnloadingHandler(() -> mainUI.getScannerOptions().saveToPersistentStorage());

            Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
                burpApi.logging().logToError(throwable);
            });

            burpApi.logging().logToOutput("Extension loaded successfully!%nVersion loaded: %s".formatted(Utils.getExtensionVersion()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
