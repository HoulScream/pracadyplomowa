package dao;

import common.Main;
import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tableview.ItemTableView;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ItemDAO
{

    public static ObservableList<ItemTableView> getItemList()
    {
        List<ItemEntity> list = null;

        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();

            list = manager.createQuery("select i from ItemEntity as i", ItemEntity.class).getResultList();
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
        ObservableList<ItemTableView> observableList = FXCollections.observableArrayList();
        for (ItemEntity n : list)
        {
            observableList.add(new ItemTableView(n.getItem_id(), n.getName(), n.getBail(), n.getRentalprice(), n.getCount()));
        }
        return observableList;
    }

    public static void addItem(String name, double bail, double rentalprice, int count)
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();

            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setName(name);
            itemEntity.setBail(bail);
            itemEntity.setRentalprice(rentalprice);
            itemEntity.setCount(count);
            manager.persist(itemEntity);
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

    public static void editItem(Integer id, String name, double bail, double rentalprice, int count)
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();

            ItemEntity itemEntity = manager.find(ItemEntity.class, id);
            itemEntity.setName(name);
            itemEntity.setBail(bail);
            itemEntity.setRentalprice(rentalprice);
            itemEntity.setCount(count);
            manager.persist(itemEntity);
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

    public static void removeItem(Integer id)
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();
            ItemEntity itemEntity = manager.find(ItemEntity.class, id);
            manager.remove(itemEntity);
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
