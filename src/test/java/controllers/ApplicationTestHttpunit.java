package controllers;

/*
* Httpunit works with javax, not jakarta. With jakarta you'll get error #500
* Before start testing:
* - you'll have to download an archive from https://sourceforge.net/projects/httpunit/files/latest/download
* - you'll have to set a variable in environment variable with path to unzipped archive
* - you'll have to add js.jar to dependencies  in modules of project structure
* */


import org.junit.Test;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import org.xml.sax.SAXException;

import java.io.IOException;
public class ApplicationTestHttpunit {

    @Test
    public void doTestHttpUnit() throws IOException, SAXException {
        //Make working environment for a servlet
        ServletRunner sr = new ServletRunner();
        //Registration the servlet in environment
        sr.registerServlet("service", Application.class.getName());
        //Making a client
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new GetMethodWebRequest("http://localhost/service");
        //getting information about simulated server
        WebResponse response = sc.getResponse(request);
        //Print out information
        System.out.println(response.getText());

    }



}