package qa.qcri.aidr.gis.servlet;

import org.apache.log4j.Logger;
import qa.qcri.aidr.gis.store.LookUp;
import qa.qcri.aidr.gis.util.Communicator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jlucas
 * Date: 5/18/14
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ESRIService extends HttpServlet {
    protected static Logger logger = Logger.getLogger("ESRIService");

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    @SuppressWarnings("unchecked")
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            System.out.println("esri: processRequest:  ");
            String name = "qdate";
            String value = request.getParameter(name);
            String url = LookUp.MAP_GEOJSON_URL;
           /* if (value != null) {
                if(isValidDateFormat(value)){
                  //  url = "http://localhost:8084/AIDRTrainerAPI/rest/geo/JSON/geoMap/qdate/" + value;
                    //System.out.println(url);
                }
            }  */

            Communicator com = new Communicator();
            com.sendGet(url);
            //String returnValue =  com.requestGet(url,"application/json");
            String returnValue =  com.sendGet(url);

            final byte[] content = returnValue.getBytes("UTF-8");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setContentLength(content.length);

            final OutputStream out = response.getOutputStream();
            out.write(content);

            System.out.println("esri: output:  " + returnValue);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("esri: output error:  " + e.getMessage());
            logger.error("processRequest : error found " + e.getMessage());
        }

    }

    private boolean isValidDateFormat(String lastupdated){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //String dateInString = "2014-01-26 13:44:48";
            Date queryDate = sdf.parse(lastupdated);
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }


}
