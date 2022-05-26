import entities.*;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.h2.command.dml.Set;
import org.hibernate.Transaction;
import org.hibernate.Session;
import util.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Set.of;

public class App {
    public static void main(String[] args) {
        Person p1 = new Employee("Max", "Dubakov", LocalDateTime.now().minusYears(20), "CEO");
        Person p2 = new Resident("Leha", "Shkap", LocalDateTime.now().minusYears(23), 12);

        HousingComplex hc = new HousingComplex(LocalDate.now().minusYears(3));
        House h1 = new House("house1", LocalDateTime.now(), new HouseAddress("Konowaltsya", 1), of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), hc);
        House h2 = new House("house2", LocalDateTime.now(), new HouseAddress("Konowaltsya", 2), of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), hc);

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(p1);
            session.persist(p2);

            session.persist(hc);
            session.persist(h1);
            session.persist(h2);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Person> people = session.createQuery("from Employee", Person.class).list();
            people.addAll(session.createQuery("from Resident", Person.class).list());

            people.forEach(System.out::println);

            List<HousingComplex> housingComplexes = session.createQuery("from HousingComplex", HousingComplex.class).list();
            housingComplexes.forEach(System.out::println);

            List<House> houses = session.createQuery("from House", House.class).list();
            houses.forEach(System.out::println);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(h1);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            List<HousingComplex> housingComplexes = session.createQuery("from HousingComplex", HousingComplex.class).list();
            housingComplexes.forEach(System.out::println);


            List<House> houses = session.createQuery("from House", House.class).list();
            houses.forEach(System.out::println);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
