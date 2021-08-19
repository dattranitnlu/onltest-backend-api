package vn.onltest.util;

public class PreprocessingUtils {
    public static String getGenderWithProp(String gender) {
        gender = gender.trim();
        if(gender.compareTo("") == 0 || gender == null)
            return "N/A";
        return (gender == "m") ? "Male": "Female";
    }
}
