package dao;

import common.Main;
import entity.ClientEntity;
import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tableview.ClientTableView;
import tableview.UserTableView;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class UserDAO
{

    public static ObservableList<UserTableView> getUserList()
    {
        List<UserEntity> list = null;

        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();

            list = manager.createQuery("select u from UserEntity as u", UserEntity.class).getResultList();
            transaction.commit();
        } catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally
        {
            manager.close();
        }
        ObservableList<UserTableView> observableList = FXCollections.observableArrayList();
        for (UserEntity n : list)
        {
            observableList.add(new UserTableView(n.getUserId(),n.getLogin(), n.getPassword(),n.getAdmin()));
        }
        return observableList;
    }

    public static void addUser(String login, String password, int admin)
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();

            UserEntity userEntity = new UserEntity();
            userEntity.setLogin(login);
            userEntity.setPassword(password);
            userEntity.setAdmin(admin);
            manager.persist(userEntity);
            transaction.commit();
        } catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally
        {
            manager.close();
        }
    }

    public static void editUser(int id, String login, String password, int admin)
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();
            UserEntity userEntity = manager.find(UserEntity.class, id);
            userEntity.setLogin(login);
            userEntity.setPassword(password);
            userEntity.setAdmin(admin);
            manager.persist(userEntity);
            transaction.commit();
        } catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally
        {
            manager.close();
        }
    }

    public static void removeClient(int id)
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();
            UserEntity userEntity = manager.find(UserEntity.class, id);
            manager.remove(userEntity);
            transaction.commit();
        } catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally
        {
            manager.close();
        }
    }
}
