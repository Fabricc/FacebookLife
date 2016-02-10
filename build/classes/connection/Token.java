/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import org.eclipse.swt.*;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class Token{

private static final String APP_ID = "175000752694085";
private static final String PERMISSIONS =
        "user_status,read_stream,user_photos,user_friends";
private static String access_token;
private long expirationTimeMillis;
/**
 * Implements facebook's authentication flow to obtain an access token. This
 * method displays an embedded browser and defers to facebook to obtain the
 * user's credentials.
 * According to facebook, the request as we make it here should return a
 * token that is valid for 60 days. That means this method should be called
 * once every sixty days.
 */
public Token() {
    Display display = new Display();
    Shell shell = new Shell(display);
    final Browser browser;
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns=3;
    shell.setLayout(gridLayout);
    

    try {
        browser = new Browser(shell, SWT.NONE);
    } catch (SWTError e){
        System.err.println("Could not instantiate Browser: " + e.getMessage());
        display.dispose();
        display = null;
        return;
    }
    browser.setJavascriptEnabled(true);

    GridData data = new GridData();
    data.horizontalAlignment = GridData.FILL;
    data.verticalAlignment = GridData.FILL;
    data.horizontalSpan = 3;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = true;
    browser.setLayoutData(data);

    final ProgressBar progressBar = new ProgressBar(shell, SWT.MOZILLA);
    data = new GridData();
    data.horizontalAlignment = GridData.END;
    progressBar.setLayoutData(data);

    /* Event Handling */
    browser.addProgressListener(new ProgressListener(){
        public void changed(ProgressEvent event){
            if(event.total == 0) return;
            int ratio = event.current * 100 / event.total;
            progressBar.setSelection(ratio);
        }
        public void completed(ProgressEvent event) {
            progressBar.setSelection(0);
        }
    });
    browser.addLocationListener(new LocationListener(){
        public void changed(LocationEvent e){
            // Grab the token if the browser has been redirected to
            // the login_success page
            String s = e.location;
            System.out.println("urltoken==>");
            System.out.println(s);
            String token_identifier = "access_token=";
            if(s.contains("https://www.facebook.com/connect/login_success.html?#access_token=")||
               s.contains("https://www.facebook.com/connect/login_success.html#access_token=")){
                access_token = s.substring(s.lastIndexOf(token_identifier)+token_identifier.length(),s.lastIndexOf('&'));
                System.out.println("solo token==>");
                System.out.println(access_token);
                String expires_in = s.substring(s.lastIndexOf('=')+1);
                expirationTimeMillis = System.currentTimeMillis() + (Integer.parseInt(expires_in) * 1000);
                browser.setUrl("https://www.facebook.com/logout.php?access_token="+access_token+"&confirm=1");
                browser.close();
            }
        }
        public void changing(LocationEvent e){}
    });

    if(display != null){
        shell.open();
        browser.setUrl("https://www.facebook.com/dialog/oauth?"
                + "client_id=" + APP_ID
                + "&redirect_uri=https://www.facebook.com/connect/login_success.html?"
                + "&scope=" + PERMISSIONS
                + "&response_type=token");
        
        System.out.println("https://www.facebook.com/dialog/oauth?"
                + "client_id=" + APP_ID
                + "&redirect_uri=https://www.facebook.com/connect/login_success.html"
                + "&scope=" + PERMISSIONS
                + "&response_type=token");
                
        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()){
                display.sleep();
                if(access_token != null && !access_token.isEmpty()){
                    try{ Thread.sleep(3000);}catch(Exception e){}
                    shell.dispose();
                }
            }
        }
        display.dispose();
       
    }
}

public String getToken(){
    
    System.out.println("acces token-->"+access_token);
    return access_token;
}



}