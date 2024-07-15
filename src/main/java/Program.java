import entity.Account;
import entity.Department;
import entity.Group;
import entity.GroupAccount;
import util.HibernateUtil;

public class Program {
    public static void main(String[] args) {
        try (var factory = HibernateUtil.buildSessionFactory()) {
            factory.inTransaction(session -> {
              var group = new Group();
              group.setName("Hibernate");
              session.persist(group);

              var account1 = new Account();
              account1.setName("Long");
              account1.setEmail("long@gmail.com");
              account1.setGroup(group);
              session.persist(account1);

                var account2= new Account();
                account2.setName("Thảo");
                account2.setEmail("thảo@gmail.com");
                account2.setGroup(group);
                session.persist(account2);
            });

            factory.inSession(session -> {
                var hql = "FROM Group";
                var groups = session
                        .createSelectionQuery(hql, Group.class)
                        .getResultList();
                for (var group : groups) {
                    System.out.println("👉 group = " + group.getName());
                    var accounts = group.getAccounts();
                    for( var account : accounts) {
                        System.out.println("✨ account = " + account.getName());
                    }
                }
            });

        }
    }
}
