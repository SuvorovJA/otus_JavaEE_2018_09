package ru.otus.sua.L12client;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L12.ejbs.OrderRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginContext;
import java.util.Properties;

@Slf4j

public class RmiClient {
    private static InitialContext ic;

    public static void main(String[] args) throws NamingException, InterruptedException {

        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
//        props.setProperty(Context.SECURITY_PRINCIPAL, "remote");
//        props.setProperty(Context.SECURITY_CREDENTIALS, "remote");



        ic = new InitialContext(props);
        OrderRemote orderRemote = (OrderRemote) ic.lookup("java:global/L12/OrderRemoteMonEJB!ru.otus.sua.L12.ejbs.OrderRemote");
        String prev="";
        while (true) {
            String curr = orderRemote.getLastOrder();
            if (!prev.equals(curr)) {
                log.info(curr);
                prev=curr;
            }
            Thread.sleep(2000);
        }
    }



}


