import Database.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serverTools.ResponseSender;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResponseProducer implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ResponseProducer.class);
    private final HashMap<UserData, ByteArrayOutputStream> responseMap;

    private final ResponseSender responseSender;
    ExecutorService serviceResponder = Executors.newCachedThreadPool();

    public ResponseProducer(ResponseSender responseSender, HashMap<UserData, ByteArrayOutputStream> responseMap) {
        this.responseMap =responseMap;
        this.responseSender = responseSender;
    }

    @Override
    public void run() {
        while (true){
            synchronized (responseMap){
                try {
                    while (responseMap.isEmpty()){
                        responseMap.wait();
                    }
                    //responseMap.forEach(this::sendResponse);
                    UserData[] responseKeys;
                    ByteArrayOutputStream[] responseValues;
                    ArrayList<Thread> responseThread = new ArrayList<>();
                    synchronized (responseMap) {
                        responseKeys = responseMap.keySet().toArray(new UserData[0]);
                        responseValues = responseMap.values().toArray(new ByteArrayOutputStream[0]);
                    }
                    for (int i = 0; i < responseKeys.length; i++) {
                        this.sendResponse(responseKeys[i], responseValues[i]);
                    }
                }catch (InterruptedException e){
                    System.out.println("Произошла ошибка при отправке " + e.getMessage());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendResponse(UserData userData, ByteArrayOutputStream byteArrayOutputStream) {
        synchronized (responseMap) {
            responseMap.remove(userData, byteArrayOutputStream);
        }
        logger.info("Отправка для " + userData.getPort() + " началась.");
        serviceResponder.submit(new ResponseSender(responseSender.getSocket(), userData.getInetAddress(), userData.getPort(),
                byteArrayOutputStream.toByteArray()));
    }
}
