package me.gamercoder215.quantumpen;

public class TypeManager {
    
    public static boolean isPremium() {
        try {
            Class.forName("me.gamercoder215.quantumpen.premium.PremiumLicense");

            return true;
        } catch (Exception err) {
            return false;
        }
    }
    
}
