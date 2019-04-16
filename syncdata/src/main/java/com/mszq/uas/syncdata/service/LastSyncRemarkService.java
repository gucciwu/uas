package com.mszq.uas.syncdata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LastSyncRemarkService {
    private static final Logger logger = LoggerFactory.getLogger(LastSyncRemarkService.class);
    private static final String REMARK_FILE = "remark";
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Date lastSyncDate = null;

    public String getLastSyncDateStr(){
        return df.format(getLastSyncDate());
    }

    public Date getLastSyncDate() {

        if(lastSyncDate == null) {
            try {
                File remark = new File(REMARK_FILE);

                Date date = null;
                BufferedReader br = null;

                br = new BufferedReader(new FileReader(remark));

                String line = null;
                while ((line = br.readLine()) != null) {

                    try {
                        lastSyncDate = df.parse(line);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                br.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(lastSyncDate == null)
            lastSyncDate = new Date();

        return lastSyncDate;
    }

    public void remark() throws IOException {
        Date now = new Date();

        File remark = new File(REMARK_FILE);
        if(!remark.exists()){
            remark.createNewFile();
        }
        PrintWriter writer = new PrintWriter(remark);
        writer.println(df.format(now));
        writer.flush();
        writer.close();

        lastSyncDate = now;
    }
}
