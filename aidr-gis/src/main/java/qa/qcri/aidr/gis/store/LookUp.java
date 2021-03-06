package qa.qcri.aidr.gis.store;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 6/3/14
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class LookUp {

    public static String MAP_GEOJSON_URL = "http://10.5.4.51:8084/AIDRTrainerAPI/rest/geo/JSON/geoMap";
    //public static String MAP_GEOJSON_URL = "http://pybossa-dev.qcri.org/AIDRTrainerAPI/esri/getGeoJson";

    public static String DEFAULT_ESRI_GEO_FILE_PATH = "/var/www/esri_data/";
    //public static String DEFAULT_ESRI_GEO_FILE_PATH = "";
    public static String DEFAULT_ESRI_STORY_MAP_FILE_NAME = "esri";
    public static String DEFAULT_ESRI_STORY_MAP_FILE_EXTENSION = ".geojson";

    public static String DEFAULT_ESRI_DATA = "http://clickers.micromappers.com/esri_data/";
}
