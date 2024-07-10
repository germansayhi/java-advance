import entity.Department;
import util.HibernateUtil;

public class Program {
    public static void main(String[] args) {
        try(var factory = HibernateUtil.buildSessionFactory()){
            factory.inTransaction(session -> {
                var department = new Department();
                department.setName("Giám Đốc");
                session.persist(department);
            });
            factory.inTransaction(session -> {
                var department = new Department();
                department.setName("Bảo Vệ");
                session.persist(department);
            });

            factory.inSession(session -> {
                var hpl = "FROM Department";
                var departments = session
                        .createSelectionQuery(hpl, Department.class)
                        .getResultList();
                for (var department : departments) {
                    System.out.println("🎈department = " + department);
                }
            });

            factory.inSession(session -> {
                var department = session.get(Department.class, 1);
                System.out.println("🐕‍🦺department = " + department);
            });

            factory.inSession(session -> {
                var hpl = "FROM Department WHERE name = :name";
                var departments = session
                        .createSelectionQuery(hpl, Department.class )
                        .setParameter("name", "Bảo vệ")
                        .uniqueResult();
                System.out.println("❌department = " + departments);
            });

            factory.inTransaction(session -> {
                var department = session.get(Department.class, 2);
                department.setName("Kinh doanh");
                session.merge(department);
            });

            factory.inTransaction(session -> {
                var department =session.get(Department.class, 1);
                session.remove(department);
            });
        }
    }
}
