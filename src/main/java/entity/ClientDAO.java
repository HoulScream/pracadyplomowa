package entity;

import common.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ClientDAO
{

    public static ObservableList<Client> getClientList()
    {
        List<ClientEntity> list = null;

        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();

            list = manager.createQuery("select c from ClientEntity as c", ClientEntity.class).getResultList();
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
        ObservableList<Client> observableList = FXCollections.observableArrayList();
        for (ClientEntity n : list)
        {
            observableList.add(new Client(n.getClient_id(), n.getName(), n.getAddress(), n.getPostalcode(), n.getPhonenumber(), n.getNip()));
        }
        return observableList;
    }

    public static void addClient(String name, String addresss, String postalcode, String phonenumber, String nip)
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setName(name);
            clientEntity.setAddress(addresss);
            clientEntity.setPostalcode(postalcode);
            clientEntity.setPhonenumber(phonenumber);
            clientEntity.setNip(nip);
            manager.persist(clientEntity);
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

    public static void editClient(Integer id, String name, String address, String postalcode, String phonenumber, String nip)
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();
            ClientEntity client = manager.find(ClientEntity.class, id);
            client.setName(name);
            client.setAddress(address);
            client.setPostalcode(postalcode);
            client.setPhonenumber(phonenumber);
            client.setNip(nip);
            manager.persist(client);
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

    public static void removeClient(Integer id)
    {
        EntityManager manager = Main.emf.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = manager.getTransaction();
            transaction.begin();
            ClientEntity client = manager.find(ClientEntity.class, id);
            manager.remove(client);
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
