import entity.*;
import util.HibernateUtil;


public class Program {
    public static void main(String[] args) {
        try (var factory = HibernateUtil.buildSessionFactory()) {
            factory.inTransaction(session -> {
                var circle = new Circle();
                circle.setRadius(5);
                circle.setColor("red");
                session.persist(circle);

                var rectangle = new Rectangle();
                rectangle.setWidth(3);
                rectangle.setHeight(4);
                rectangle.setColor("blue");
                session.persist(rectangle);

            });

            factory.inSession(session -> {
                var hql = "FROM Circle";
                var shapes = session
                        .createSelectionQuery(hql, Circle.class)
                        .getResultList();
                for (var shape : shapes) {
                    System.out.println("👉 shape = " + shape.getColor());

                }
            });

        }
    }
}
