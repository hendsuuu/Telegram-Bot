/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bottelegram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import org.apache.commons.io.FileUtils;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


/**
 *
 * @author sapyy
 */
public class BotTelegram extends TelegramLongPollingBot {

    Connection Con;
    ResultSet RsCommand;
    ResultSet RsUser;
    ResultSet RsSearch;
    Statement stm,stm2;
    Boolean ada = false;
    Boolean isRemove = false;
    Boolean makeArt = false;
    private Object[][] dataTable = null;
    BotTelegram(){
        open_db();
    }
    private void open_db() {
        try{
            KoneksiMysql kon = new KoneksiMysql("localhost","root","","chatbot");
            Con = kon.getConnection();
        //System.out.println("Berhasil ");
        }catch (Exception e) {
            System.out.println("Error : "+e);
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        try {

            SendMessage message = new SendMessage();
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String command = update.getMessage().getText().toLowerCase();
            long id_user = update.getMessage().getFrom().getId();
            String username = update.getMessage().getFrom().getUserName();
            String name = update.getMessage().getFrom().getFirstName();
            LocalDateTime datetime = LocalDateTime.now();
            
            System.out.println(String.valueOf(makeArt));
            if(makeArt){
                if(update.hasMessage()){
                    Hotpot(command,update.getMessage().getChatId().toString());
                    SendPhoto sendPhotoRequest = new SendPhoto();
                    sendPhotoRequest.setChatId(update.getMessage().getChatId().toString());
                    sendPhotoRequest.setPhoto(new InputFile(new File("images/foto.jpg")));
                }
                makeArt = false;
            }
                                
            if (update.getMessage().hasPhoto()) {
                
            } else {
                
                RsUser = stm.executeQuery("select * from users where user_id='"+id_user+"'");
            
                int rowCount = 0;
                while (RsUser.next()) {
                    rowCount++;
                }
                
                
                if(command.equals("/start")){
                    //                stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    RsCommand = stm.executeQuery("select * from command");
                    String body = "" ;
                    while(RsCommand.next()) {
                        body += RsCommand.getString("command").toLowerCase() +" \n- "+ RsCommand.getString("deskripsi")+"\n";
                    }
                    message.setChatId(update.getMessage().getChatId().toString());
                    message.setText("Haloo "+name+"\nBerikut Adalah Beberapa Command Yang Tersedia \n\n"+body+"\n\nSelamat Mencoba");
                    try {
                        execute(message);
                    } catch (TelegramApiException ex) {
                        Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.print(update.getMessage().getChatId().toString());
//                    stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+message.getText()+"','"+datetime+"','keluar')");
                }

                else if(command.equals("/member")){
                    try {
                        if(username.equals("")){
                            message.setChatId(update.getMessage().getChatId().toString());
                            message.setText("Maaf Gagal Menjadi Member Mohon Set Username Telegram Anda Terlebih Dahulu");
                            try {
                                execute(message);
                            } catch (TelegramApiException ex) {
                                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if(rowCount > 0){
                            message.setChatId(update.getMessage().getChatId().toString());
                            message.setText("Anda Sudah Terdaftar Sebagai Member");
                            try {
                                execute(message);
                            } catch (TelegramApiException ex) {
                                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                            stm.executeUpdate("INSERT into users VALUES(NULL,'"+id_user+"','"+username+"','member')");
                            message.setChatId(update.getMessage().getChatId().toString());
                            message.setText("Terima Kasih "+name+" Kamu Berhasil Menjadi Member");
                            try {
                                execute(message);
                            } catch (TelegramApiException ex) {
                                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
//                        stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+message.getText()+"','"+datetime+"','keluar')");

                    } catch (SQLException ex) {
                        Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(command.equals("/stopmember")){
                    try {
                        if(rowCount > 0){
                            stm.executeUpdate("DELETE from users where user_id="+id_user+"");
                            message.setChatId(update.getMessage().getChatId().toString());
                            message.setText("Terima Kasih "+name+" Anda Keluar Dari Member Lonely Bot");
                            try {
                                execute(message);
                            } catch (TelegramApiException ex) {
                                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                            message.setChatId(update.getMessage().getChatId().toString());
                            message.setText("Maaf Anda Belum Menjadi Member");
                            try {
                                execute(message);
                            } catch (TelegramApiException ex) {
                                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
//                        stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+message.getText()+"','"+datetime+"','keluar')");

                    } catch (SQLException ex) {
                        Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //                    daftarMember(id_user,username);

                }
                else if(rowCount == 0){
                    message.setChatId(update.getMessage().getChatId().toString());
                    message.setText("Maaf Anda belum Terdaftar Sebagai Member\n Silahkan Ketik Command Berikut : /member");
                    //                System.out.print(command);
                    try {
                        execute(message);
                    } catch (TelegramApiException ex) {
                        Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+message.getText()+"','"+datetime+"','keluar')");
                }
                else if(rowCount > 0){
                    stm2 = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    RsCommand = stm2.executeQuery("select * from command");

                    while(RsCommand.next()) {
                        if(command.equals(RsCommand.getString("command").toLowerCase())){
                            // Create a SendMessage object with mandatory fields
                            message.setChatId(update.getMessage().getChatId().toString());
                            message.setText(RsCommand.getString("response"));
                            System.out.print(command);
                            try {
                                execute(message);
                            } catch (TelegramApiException ex) {
                                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            ada=true;
                        }

                        
                    }
                    if(command.equals("/joke")){
                        System.out.print("masuk joke");
                        String joke = Joke(update.getMessage().getChatId().toString());
                        if(!joke.isEmpty()){
                            stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+joke+"','"+datetime+"','keluar')");
                        }
                        else{
                            message.setChatId(update.getMessage().getChatId().toString());
                            message.setText("Maaf Joke sedang tidak tersedia Karena Keterbatasan Owner Yang tidak mau mengeluarkan uang sepeser pun untuk membeli API");
                            System.out.print(command);
                            try {
                                execute(message);
                            } catch (TelegramApiException ex) {
                                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    if(command.equals("/gif")){
                        System.out.println("masuk gif");
                        
                        String url = getGiphyUrl();
                        System.out.print(url);
                        sendGif(update.getMessage().getChatId(),url);
                        stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+url+"','"+datetime+"','keluar')");
                        
                    }
                    else if(command.equals("/makeart") && !makeArt){
                        makeArt=true;
                        message.setChatId(update.getMessage().getChatId().toString());
                        message.setText("Kirim perintah untuk generate Gambar");
                        try {
                            execute(message);
                        } catch (TelegramApiException ex) {
                            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                        }
    //                        System.out.print(update.getMessage().getChatId().toString());
    //                    stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+message.getText()+"','"+datetime+"','keluar')");
                    }
                    if(!ada){
                        message.setChatId(update.getMessage().getChatId().toString());
                        message.setText("Maaf Perintah Tidak Ada dalam Daftar Kami");
                        try {
                            execute(message);
                        } catch (TelegramApiException ex) {
                            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                        }
    //                    stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+message.getText()+"','"+datetime+"','keluar')");
                    }
                }
                
                else if(command.equals("/removebg")){
                    isRemove = true;
                    System.out.println(isRemove);
                    message.setChatId(update.getMessage().getChatId().toString());
                    message.setText("Kirim Gambar yang ingin anda remove");
                    try {
                        execute(message);
                    } catch (TelegramApiException ex) {
                        Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                        System.out.print(update.getMessage().getChatId().toString());
                    
                }
                
            }
            
            if(!update.getMessage().getText().isEmpty()){    
                stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+update.getMessage().getText()+"','"+datetime+"','masuk')");  
            }
            if(!message.getText().isEmpty()){
                stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+message.getText()+"','"+datetime+"','keluar')");
            }
  
        } catch (SQLException ex) {
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "llonelly04_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "6039054547:AAG-L-pKwI9VoZQtovxYWGtgV6Tx3A7K0yE";
    }

    public static String convertToString(List<PhotoSize> photoSizes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (PhotoSize photoSize : photoSizes) {
            stringBuilder.append("File ID: ").append(photoSize.getFileId())
                    .append(", Width: ").append(photoSize.getWidth())
                    .append(", Height: ").append(photoSize.getHeight())
                    .append(", File Size: ").append(photoSize.getFileSize())
                    .append("\n");
        }

        return stringBuilder.toString();
    }
    
    public void broadcast(String msg){
        try {
            LocalDateTime datetime = LocalDateTime.now();
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            RsUser = stm.executeQuery("select * from users");
            SendMessage message = new SendMessage();
            while(RsUser.next()){
                long id = RsUser.getInt("user_id");
                message.setChatId(id);
                message.setText(msg);
                try {
                    execute(message);
                } catch (TelegramApiException ex) {
                    Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
//            stm.executeUpdate("INSERT into message VALUES(NULL,'"+id+"','"+message.getText()+"','"+datetime+"','keluar')");
        } catch (SQLException ex) {
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public DefaultListModel<String> Search(String search){
            DefaultListModel<String> hasil = new DefaultListModel<>();
        try {
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            RsSearch = stm.executeQuery("select m.id_user,m.message,m.datetime,m.status,u.user_id,u.username from message m join users u on u.user_id=m.id_user where u.role='member' and (m.id_user LIKE '%"+search+"%' OR m.message LIKE '%"+search+"%' OR u.username LIKE '%"+search+"%'  OR u.username LIKE '%"+search+"%')  order by m.datetime desc");
            RsSearch.beforeFirst();
            while(RsSearch.next()) {

                String Name = "";
                if(RsSearch.getString("status").equals("masuk")){
                    Name = RsSearch.getString("username");
                }   
                else{
                    Name = "Lonely Bot";
                }
                hasil.addElement(RsSearch.getString("datetime")+" || "+Name+" : "+RsSearch.getString("message"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }
    
    public List<String> convertPhotoSizeToList(List<PhotoSize> photoSizes) {
        List<String> fileIds = new ArrayList<>();
        for (PhotoSize photoSize : photoSizes) {
            fileIds.add(photoSize.getFileId());
        }
        return fileIds;
    }
    private void savePhoto(String fileId) throws TelegramApiException{
        try {
            LocalDate currentDate = LocalDate.now();
            GetFile getFile = new GetFile(fileId);
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);

            // Download the photo file
            URL fileUrl = new URL("https://api.telegram.org/file/bot" + getBotToken() + "/" + file.getFilePath());
            try (InputStream inputStream = fileUrl.openStream();
                 FileOutputStream outputStream = new FileOutputStream("images/foto.jpg")) {

                // Save the photo file
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                System.out.print("berhasil simpan Gambar");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void Hotpot(String text,String id){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            SendMessage message = new SendMessage();
            HttpPost request = new HttpPost("https://api.hotpot.ai/make-art");
            request.addHeader("Authorization","yMHw4UidZM1Hha82AZtMjI50bYCfC3sdX7vvB" );

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addTextBody("inputText", text)
                    .build();

            request.setEntity(entity);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        try (InputStream inputStream = responseEntity.getContent();
                             FileOutputStream outputStream = new FileOutputStream("images/foto.jpg")) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                            System.out.println("File berhasil disimpan di: " + "images/foto.jpg");
//                            stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+outputFile+"','"+datetime+"','masuk')");
                        }
                    }
                } else {
                    message.setChatId(id);
                    message.setText("Maaf Owner Bot ini tidak mempunyai  uang untuk membeli Api");
                    try {
                        execute(message);
                    } catch (TelegramApiException ex) {
                        Logger.getLogger(BotTelegram.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.err.println("Gagal menyimpan respon . Kode respons: " + statusCode);
                }

                EntityUtils.consumeQuietly(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    private String Joke(String id) {
        String responseMsg = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            SendMessage message = new SendMessage();
            HttpGet request = new HttpGet("https://v2.jokeapi.dev/joke/Programming?lang=en");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        try (InputStream jsonResponse = responseEntity.getContent()) {
                            StringBuilder textBuilder = new StringBuilder();
                            try (Reader reader = new BufferedReader(new InputStreamReader(jsonResponse, StandardCharsets.UTF_8))) {
                                int c = 0;
                                while ((c = reader.read()) != -1) {
                                    textBuilder.append((char) c);
                                }
                            }
                            System.out.println(textBuilder);
                            JSONObject jsonObject = new JSONObject(textBuilder.toString());
                            if(jsonObject.getString("type").equals("twopart")){
                                message.setChatId(id);
                                message.setText(jsonObject.getString("setup"));
//                                responseMsg = jsonObject.getString("joke");
                                try {
                                    execute(message);
                                } catch (TelegramApiException ex) {
                                    Logger.getLogger(BotTelegram.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                message.setChatId(id);
                                message.setText(jsonObject.getString("delivery"));
                                responseMsg = jsonObject.getString("setup")+"\n"+jsonObject.getString("delivery");
                                try {
                                    execute(message);
                                } catch (TelegramApiException ex) {
                                    Logger.getLogger(BotTelegram.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }else{
                                message.setChatId(id);
                                message.setText(jsonObject.getString("joke"));
                                responseMsg = jsonObject.getString("joke");
                                try {
                                    execute(message);
                                } catch (TelegramApiException ex) {
                                    Logger.getLogger(BotTelegram.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            
                        }
                    }
                } else {
                    System.err.println("Gagal menyimpan respon. Kode respons: " + statusCode);
                }

                EntityUtils.consumeQuietly(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMsg;
    }
    
    private void removeBg(String inputFile,String outputFile,String color){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("https://api.remove.bg/v1.0/removebg");
            request.addHeader("X-Api-Key","4j8THgsAuEb2M5BGu8F22Qq5" );

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("image_file", new File(inputFile))
                    .addTextBody("size", "auto")
                    .addTextBody("bg_color", color)
                    .build();

            request.setEntity(entity);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        try (InputStream inputStream = responseEntity.getContent();
                             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                            System.out.println("File berhasil disimpan di: " + outputFile);
//                            stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+outputFile+"','"+datetime+"','masuk')");
                        }
                    }
                } else {
                    System.err.println("Gagal menghapus latar belakang. Kode respons: " + statusCode);
                }

                EntityUtils.consumeQuietly(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void removeBg(String inputFile,String outputFile){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("https://api.remove.bg/v1.0/removebg");
            request.addHeader("X-Api-Key","D8ED2YAryiFiJ94KyBjENAEB" );

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("image_file", new File(inputFile))
                    .addTextBody("size", "auto")
                    .build();

            request.setEntity(entity);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        try (InputStream inputStream = responseEntity.getContent();
                             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                            System.out.println("File berhasil disimpan di: " + outputFile);
//                            stm.executeUpdate("INSERT into message VALUES(NULL,'"+id_user+"','"+outputFile+"','"+datetime+"','masuk')");
                        }
                    }
                } else {
                    System.err.println("Gagal menghapus latar belakang. Kode respons: " + statusCode);
                }

                EntityUtils.consumeQuietly(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    private void sendGif(Long chatId,String url) {
        SendAnimation sendAnimation = new SendAnimation();
        
        sendAnimation.setChatId(chatId);
        sendAnimation.setAnimation(new InputFile(url));

        try {
            execute(sendAnimation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        
    }
    
    private String getGiphyUrl() {
        String url = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            
            HttpGet request = new HttpGet("https://api.giphy.com/v1/gifs/random?api_key=wfX8vPfLHwY9g2ZRSyqjWcuguZqTAX8k&tag=&rating=g");
//            request.addHeader("api_key","wfX8vPfLHwY9g2ZRSyqjWcuguZqTAX8k" );


            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        try (InputStream jsonResponse = responseEntity.getContent();) {
                            StringBuilder textBuilder = new StringBuilder();
                            try (Reader reader = new BufferedReader(new InputStreamReader(jsonResponse, StandardCharsets.UTF_8))) {
                                int c = 0;
                                while ((c = reader.read()) != -1) {
                                    textBuilder.append((char) c);
                                }
                            }

                            JSONObject jsonObject = new JSONObject(textBuilder.toString());
                            JSONObject data = (JSONObject) jsonObject.get("data");
                            JSONObject image = (JSONObject) data.get("images");
                            JSONObject original = (JSONObject) image.get("original");
                            url = (String) original.get("url");
                            System.out.println("URL: " + url);
                            
                   }
                    }
                } else {
                    System.err.println("Gagal menghapus latar belakang. Kode respons: " + statusCode);
                }

                EntityUtils.consumeQuietly(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
    
    private void sendSticker(String chatId, String stickerFile) throws TelegramApiException {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(chatId);
        sendSticker.setSticker(new InputFile(new File("images/gif.gif")));
        execute(sendSticker);
    }

    private void assertEquals(String toString, String originalString) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
