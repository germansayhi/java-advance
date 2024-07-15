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

              var account = new Account();
              account.setName("Long");
              account.setEmail("long@gmail.com");
              account.setGroup(group);
              session.persist(account);
            });

            factory.inSession(session -> {
                var hql = "FROM Account";
                var Accounts = session
                        .createSelectionQuery(hql, Account.class)
                        .getResultList();
                for (var Account : Accounts) {
                    System.out.println("👌 Account = " + Account.getName());
                    System.out.println("👌 Account = " + Account.getGroup().getName());
                }
            });
            factory.inSession(session -> {
                var hql = "FROM Group";
                var groups = session
                        .createSelectionQuery(hql, Group.class)
                        .getResultList();
                for (var group : groups) {
                    System.out.println("👉 group = " + group.getName());
                    System.out.println("✨ account = " + group.getAccount().getName());
                }
            });

        }
    }
}
