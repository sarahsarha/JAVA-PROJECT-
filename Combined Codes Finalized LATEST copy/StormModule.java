/**
 * Class    : StormModule
 * Creator  : Izz
 * Tester   : Annie
 */
public class StormModule extends DisasterModule {

    private final String[] images = {
            "Storm Climate Science.png",
            "Storm Climate Science.png",
            "Storm Key Facts.png",
            "Storm Evacuation Protocol.png",
            "Storm Evacuation Protocol.png"
    };

    private final String[] descriptions = {

            "MODULE 1: STORM DEFENSE PROFILE\n\nStorms (Hurricanes, Tornadoes, Thunderstorms)\n\n- Warmer oceans increase storm strength\n- Climate change causes storms to stall\n- More Category 4 & 5 hurricanes",

            "Storm Climate Science\n\n- Temperature > 26°C fuels storms\n- Hurricane season is expanding",

            "Storm Facts:\n\n- Intensity +8% per decade\n- Energy = 10,000 nuclear bombs\n- Storm surge causes most deaths\n- Tornado winds > 300 mph",

            "Storm Evacuation:\n\n1. Monitor alerts\n2. Secure property\n3. Store water\n4. Charge devices\n5. Identify shelter\n6. Evacuate early\n7. Stay indoors\n8. Avoid hazards",

            "Storm Safety:\n\n- Watch vs Warning\n- Go to lowest room\n- Avoid overpasses\n- Use emergency radio\n- Avoid floodwater"
    };

    public String[] getImages() {
        return images;
    }

    public String[] getDescriptions() {
        return descriptions;
    }
}
