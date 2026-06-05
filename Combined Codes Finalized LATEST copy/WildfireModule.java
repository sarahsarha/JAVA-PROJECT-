/**
 * Class    : WildfireModule
 * Creator  : Izz
 * Tester   : Najla
 */

public class WildfireModule extends DisasterModule {

    private final String[] images = {
            "Wildfire Climate Science.png",
            "Wildfire Climate Science.png",
            "Wildfire Key Facts.png",
            "Wildfire Evacuation Protocol.png",
            "Wildfire Evacuation Protocol.png"
    };

    private final String[] descriptions = {

            "MODULE 2: WILDFIRE DEFENSE PROFILE\n\n- Higher temperatures dry vegetation\n- Fires spread faster\n- Fire seasons longer",

            "Wildfire Climate Science:\n\n- 1°C = +600% burned area\n- VPD affects fire intensity",

            "Wildfire Facts:\n\n- 14 mph (forest)\n- 70 mph (grassland)\n- Toxic smoke (PM2.5)\n- Season +78 days",

            "Wildfire Evacuation:\n\n1. Monitor AQI\n2. Prepare go-bag\n3. Close windows\n4. Move flammables\n5. Evacuate immediately\n6. Travel away\n7. Inform others\n8. Wait clearance",

            "Wildfire Safety:\n\n- Defensible space\n- Fire-resistant materials\n- N95 masks\n- Never fight fire\n- Watch debris"
    };

    public String[] getImages() {
        return images;
    }

    public String[] getDescriptions() {
        return descriptions;
    }
}