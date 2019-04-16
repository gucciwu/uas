package com.mszq.uas.syncdata.job;

import com.mszq.uas.syncdata.oa.api.ISSOChangePassword;
import com.mszq.uas.syncdata.oa.api.ISSOChangePasswordService;
import com.mszq.uas.syncdata.service.LastSyncRemarkService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;
import javax.xml.namespace.QName;
import java.io.*;
import java.net.URL;
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

    private static final QName SERVICE_NAME = new QName("http://sso.kmss.landray.com/", "ISSOChangePasswordService");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(querySQL);
//            statement.setDate(1,new java.sql.Date(lastSyncRemarkService.getLastSyncDate().getTime()));
            statement.setString(1,lastSyncRemarkService.getLastSyncDateStr());
            ResultSet rs = statement.executeQuery();

            Map<String,String> passwordChangeMap = new HashMap<String,String>();
            while(rs.next()){
                passwordChangeMap.put(rs.getString("JOB_NUMBER"),rs.getString("PASSWORD"));
            }
            rs.close();

            ISSOChangePasswordService ss = new ISSOChangePasswordService(new URL("http://1.1.1.14:18080/sys/webservice/SSOChangePassword?wsdl"), SERVICE_NAME);
            ISSOChangePassword port = ss.getISSOChangePasswordPort();
            for(String jobNumber : passwordChangeMap.keySet()){
                String returnCode = port.changePassword(jobNumber, passwordChangeMap.get(jobNumber));
                if(!"2".equals(returnCode)){
                    logger.warn("Failure in updating password for ["+jobNumber+"]");
                }else {
                    logger.trace("Updated password for [" + jobNumber + "]");
                }
            }

            lastSyncRemarkService.remark();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new JobExecutionException(e.getCause());
        } catch (IOException e) {
            e.printStackTrace();
            throw new JobExecutionException(e.getCause());
        } finally {
            try {
                if(statement != null)
                    statement.close();
                if(connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
