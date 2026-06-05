/**
 * Class    : FloodModule
 * Creator  : Izz
 * Tester   : Annie  
 */

public class FloodModule extends DisasterModule {

    private final String[] images = {
            "Flood Climate Science.png",
            "Flood Climate Science.png",
            "Flood Key Facts.png",
            "Flood Evacuation Protocol.png",
            "Flood Evacuation Protocol.png"
    };

    private final String[] descriptions = {

            "MODULE 3: FLOOD DEFENSE PROFILE\n\n- Warmer air holds more moisture\n- Floods increasing worldwide",

            "Flood Climate Science:\n\n- 1°C = 7% more water vapor\n- Heavy rainfall increases floods",

            "Flood Facts:\n\n- Most common disaster\n- 10–20 ft flash floods\n- 2 inches moves cars\n- 90% disasters involve floods",

            "Flood Evacuation:\n\n1. Monitor alerts\n2. Move valuables up\n3. Turn off electricity\n4. Avoid floodwater\n5. Follow evacuation\n6. Contact help\n7. Wait clearance",

            "Flood Safety:\n\n- Know flood zone\n- Store kit upstairs\n- Flood insurance\n- Evacuate early\n- Avoid contamination"
    };

    public String[] getImages() {
        return images;
    }

    public String[] getDescriptions() {
        return descriptions;
    }
}