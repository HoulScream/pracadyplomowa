package common;

import entity.ConfigEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ConfigurationBuilder {
    public static String hirename;
    public static String hireadress;
    public static String hirepostalcode;
    public static String hirephonenumber;
    public static String hirenip;
    public static int maxrentaltime;
    public static int dailypenality;
    public static String orderconfirmationfolder;
    public static String returnconfirmationfolder;

    public static void loadConfiguration()
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            ConfigEntity config = manager.find(ConfigEntity.class, 1);
            hirename = config.getHirename();
            hireadress = config.getHireadress();
            hirepostalcode = config.getHirepostalcode();
            hirephonenumber = config.getHirephonenumber();
            hirenip = config.getHirenip();
            maxrentaltime = config.getMaxrentaltime();
            dailypenality = config.getDailypenality();
            orderconfirmationfolder = config.getOrderconfirmationfolder();
            returnconfirmationfolder = config.getReturnconfirmationfolder();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }
}
