package com.mszq.uas.syncdata.job;

import com.mszq.uas.syncdata.service.LastSyncRemarkService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PasswordChangeCatcher implements Job{

    private static final Logger logger = LoggerFactory.getLogger(PasswordChangeCatcher.class);

    @Autowired
    LastSyncRemarkService lastSyncRemarkService;
    @Autowired
    DataSource dataSource;

    @Value("${password.change.query.sql}")
    String querySQL;

    static final String REMARK_FILE = "remark";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(querySQL);
            statement.setDate(1,new java.sql.Date(lastSyncRemarkService.getLastSyncDate().getTime()));
            ResultSet rs = statement.executeQuery();

            Map<String,String> passwordChangeMap = new HashMap<String,String>();
            while(rs.next()){
                passwordChangeMap.put(rs.getString("JOB_NUMBER"),rs.getString("PASSWORD"));
            }

            for(String jobNumber : passwordChangeMap.keySet()){
                logger.trace("Update password for ["+jobNumber+"]");
                //TODO
                System.out.println("update password for ["+jobNumber+"]");
            }

            lastSyncRemarkService.remark();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new JobExecutionException(e.getCause());
        } catch (IOException e) {
            e.printStackTrace();
            throw new JobExecutionException(e.getCause());
        }
    }
}
